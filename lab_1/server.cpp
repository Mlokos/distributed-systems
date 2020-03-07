#include "server.hpp"


ServerSubjectTCP * ServerSubjectTCP::get_instance() {
    if(NULL == s_instance) {
        s_instance = new ServerSubjectTCP();
    }
    return s_instance;      
}

void ServerSubjectTCP::attach(ObserverTCP * observer) {
    views.push_back(observer);
}

void ServerSubjectTCP::set_message(char * message, int msg_sock_fd) {
    strcpy(message_buffer, message);
    notify(msg_sock_fd);
}

char * ServerSubjectTCP::get_message() {
    return message_buffer;
}

void ServerSubjectTCP::notify(int msg_sock_fd) {
  for (int i = 0; i < views.size(); i++)
    views[i] -> update(msg_sock_fd);
}

ServerSubjectUDP * ServerSubjectUDP::get_instance() {
    if(NULL == s_udp_instance) {
        s_udp_instance = new ServerSubjectUDP();
    }
    return s_udp_instance;      
}

void ServerSubjectUDP::attach(ObserverUDP * observer) {
    views.push_back(observer);
}

void ServerSubjectUDP::set_message(char * message, struct sockaddr_in client_addr) {
    strcpy(message_buffer, message);
    notify(client_addr);
}

char * ServerSubjectUDP::get_message() {
    return message_buffer;
}

void ServerSubjectUDP::notify(struct sockaddr_in client_addr) {
  for (int i = 0; i < views.size(); i++)
    views[i] -> update(client_addr);
}

ObserverTCP::ObserverTCP(ServerSubjectTCP * constr_model, int constr_client_sock_fd) {
    model = constr_model;
    model -> attach(this);
    client_sock_fd = constr_client_sock_fd;
}

void ObserverTCP::update(int msg_sock_fd) {
    /* do not send a message to sender */
    if(!(client_sock_fd == msg_sock_fd)) {
        char * message = model -> get_message();
        int current_message_length;

        current_message_length = write(client_sock_fd, message, strlen(message));
        if (current_message_length < 0) {
            throw "ERROR writing to socket";
        }
    }
}

ObserverUDP::ObserverUDP(ServerSubjectUDP * constr_model, int constr_client_sock_fd, struct sockaddr_in constr_client_addr) {
    model = constr_model;
    model -> attach(this);
    client_sock_fd = constr_client_sock_fd;
    client_addr = constr_client_addr;
}

void ObserverUDP::update(struct sockaddr_in msg_client_addr) {
    /* do not send a message to sender */
    if(!((msg_client_addr.sin_addr.s_addr == client_addr.sin_addr.s_addr) && (msg_client_addr.sin_port == client_addr.sin_port))) {
        char * message = model -> get_message();
        int current_message_length;

        current_message_length = sendto(client_sock_fd, message, strlen(message), MSG_CONFIRM, (const struct sockaddr *) &client_addr, sizeof(client_addr));
        if (current_message_length < 0) {
            throw "ERROR writing to socket";
        }
    }
}


void ServerTCP::server_client_connection_service(int client_sock_fd) {
    ObserverTCP obs(ServerSubjectTCP::get_instance(), client_sock_fd);

    char message_buffer[MESSAGE_LENGTH];

    while(true) {
        bzero(message_buffer, MESSAGE_LENGTH);

        int current_message_length = read(client_sock_fd, message_buffer, 255);
        if (current_message_length < 0) {
            throw "ERROR reading from socket";
        }

        ServerSubjectTCP::get_instance() -> set_message(message_buffer, client_sock_fd);
        printf("TCP message: %s", message_buffer);
    }
}

void ServerTCP::server_listener_service() {
    /* File descriptors for server and client */
    int server_sock_fd;
    int client_sock_fd;

    /* Server & client addresses */
    struct sockaddr_in server_addr;
    struct sockaddr_in client_addr;
    int server_port_nr { 32042 };

    /* Message related variables */
    socklen_t client_addr_len;
    char message_buffer[MESSAGE_LENGTH];

    server_sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if(server_sock_fd < 0) {
        throw "ERROR opening socket";
    }

    bzero((char *) &server_addr, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(server_port_nr);

    if (bind(server_sock_fd, (struct sockaddr *) &server_addr, sizeof(server_addr)) < 0) {
        throw "ERROR on binding";
    }

    listen(server_sock_fd, 5);

    while(true) {
        client_addr_len = sizeof(client_addr);
        bzero(message_buffer, MESSAGE_LENGTH);

        client_sock_fd = accept(server_sock_fd, (struct sockaddr *) &client_addr, &client_addr_len);
        if (client_sock_fd < 0) {
            throw "ERROR on accept";
        }

        std::thread client_connection_thread(&server_client_connection_service, client_sock_fd);
        client_connection_thread.detach();
    }
}

void ServerUDP::server_listener_service() {
    /* File descriptors for server and client */
    int server_sock_fd;

    /* Server & client addresses */
    struct sockaddr_in server_addr;
    struct sockaddr_in client_addr;
    int server_port_nr { 32042 };

    /* Message related variables */
    socklen_t client_addr_len;
    char message_buffer[MESSAGE_LENGTH];

    /* List of known UDP client addresses */
    std::vector<struct sockaddr_in> client_addr_list;

    server_sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    if(server_sock_fd < 0) {
        throw "ERROR opening socket";
    }

    bzero((char *) &server_addr, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(server_port_nr);

    if (bind(server_sock_fd, (struct sockaddr *) &server_addr, sizeof(server_addr)) < 0) {
        throw "ERROR on binding";
    }

    int current_message_length;

    bool is_found;
    while(true) {
        client_addr_len = sizeof(client_addr);
        bzero(message_buffer, MESSAGE_LENGTH);

        current_message_length = recvfrom(server_sock_fd, (char *)message_buffer, MESSAGE_LENGTH, MSG_WAITALL, ( struct sockaddr *) &client_addr, &client_addr_len); 
        if (current_message_length < 0) {
            throw "ERROR reading from socket";
        }
        message_buffer[current_message_length] = '\0';

        is_found = false;
        for(std::vector<struct sockaddr_in>::iterator it = client_addr_list.begin(); it != client_addr_list.end(); ++it) {
            if((it -> sin_addr.s_addr == client_addr.sin_addr.s_addr) && (it -> sin_port == client_addr.sin_port)) {
                is_found = true;
            }
        }

        if(is_found == false) {
            client_addr_list.push_back(client_addr);
            new ObserverUDP(ServerSubjectUDP::get_instance(), server_sock_fd, client_addr);
        }

        ServerSubjectUDP::get_instance() -> set_message(message_buffer, client_addr);
        printf("UDP message: %s", message_buffer);
    }
}
