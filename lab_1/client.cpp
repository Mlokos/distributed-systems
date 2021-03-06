#include "client.hpp"

int ClientTCP::start_client_session() {
    /* File descriptors for server and client */
    int server_connection_sock_fd;

    /* Server & client addresses */
    struct sockaddr_in server_addr;
    int server_port_nr { 32042 };

    struct hostent * server;

    server_connection_sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_connection_sock_fd < 0) {
        throw "ERROR opening socket";
    }

    server = gethostbyname("localhost");
    if (server == NULL) {
        throw "ERROR, no such host";
    }

    bzero((char *) &server_addr, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    bcopy((char *)server -> h_addr, (char *)&server_addr.sin_addr.s_addr, server -> h_length);
    server_addr.sin_port = htons(server_port_nr);
    if (connect(server_connection_sock_fd, (struct sockaddr *) &server_addr, sizeof(server_addr)) < 0) {
        throw "ERROR connecting";
    }

    return server_connection_sock_fd;
}

void ClientTCP::client_read_message(int sockfd) {
    char message_buffer[256];
    int current_message_length;

    while(true) {
        bzero(message_buffer, 256);

        current_message_length = read(sockfd, message_buffer, 255);
        if (current_message_length < 0) {
            throw "ERROR reading from socket";
        }

        printf("%s", message_buffer);
    }
}

int ClientUDP::start_client_session() {
    /* File descriptors for server and client */
    int server_connection_sock_fd;

    server_connection_sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    if(server_connection_sock_fd < 0) { 
        throw "socket creation failed";
    } 

    return server_connection_sock_fd;
}

void ClientUDP::client_read_message(int sockfd) {
    int server_port_nr { 32042 };
    struct sockaddr_in servaddr;
    socklen_t client_addr_len;

    servaddr.sin_family = AF_INET; 
    servaddr.sin_port = htons(server_port_nr); 
    servaddr.sin_addr.s_addr = INADDR_ANY; 

    char message_buffer[256];
    int current_message_length;

    while(true) {
        bzero(message_buffer, 256);
        
        current_message_length = recvfrom(sockfd, (char *)message_buffer, MESSAGE_LENGTH, MSG_WAITALL, (struct sockaddr *) &servaddr, &client_addr_len); 
        message_buffer[current_message_length] = '\0';

        printf("%s", message_buffer);
    }
}

int ClientMulticast::start_client_session() {
    /* File descriptors for server and client */
    int multicast_connection_sock_fd;

    /* Server & client addresses */
    struct sockaddr_in multicast_addr;
    int multicast_port_nr { 32142 };
    
    struct ip_mreq multicast_request;
    u_int yes = 1;

    /* create what looks like an ordinary UDP socket */
    multicast_connection_sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    if(multicast_connection_sock_fd < 0) {
        throw "ERROR opening socket";
    }

    /* allow multiple sockets to use the same PORT number */
    if(setsockopt(multicast_connection_sock_fd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes)) < 0) {
        throw "Reusing ADDR failed";
    }

    /* set up destination address */
    memset(&multicast_addr, 0, sizeof(multicast_addr));
    multicast_addr.sin_family = AF_INET;
    multicast_addr.sin_addr.s_addr = htonl(INADDR_ANY); /* N.B.: differs from sender */
    multicast_addr.sin_port = htons(multicast_port_nr);

    /* bind to receive address */
    if(bind(multicast_connection_sock_fd, (struct sockaddr *) &multicast_addr, sizeof(multicast_addr)) < 0) {
        throw "ERROR on binding";
    }
    
    // /* use setsockopt() to request that the kernel join a multicast group */
    // inet_pton(AF_INET, "localhost", &multicast_request.imr_multiaddr.s_addr);
    // // multicast_request.imr_multiaddr.s_addr = inet_addr("localhost");
    // multicast_request.imr_interface.s_addr = htonl(INADDR_ANY);
    // if (setsockopt(multicast_connection_sock_fd, IPPROTO_IP, IP_ADD_MEMBERSHIP, &multicast_request, sizeof(multicast_request)) < 0) {
    //     throw "ERROR on multicast kernel request";
    // }

    return multicast_connection_sock_fd;
}

void ClientMulticast::client_read_message(int sockfd) {
    int server_port_nr { 32142 };
    struct sockaddr_in multicast_addr;
    socklen_t multicast_addr_len;

    multicast_addr.sin_family = AF_INET; 
    multicast_addr.sin_port = htons(server_port_nr); 
    multicast_addr.sin_addr.s_addr = INADDR_ANY; 

    char message_buffer[256];
    int current_message_length;

    while(true) {
        bzero(message_buffer, 256);

        current_message_length = recvfrom(sockfd, message_buffer, MESSAGE_LENGTH, 0, (struct sockaddr *) &multicast_addr, &multicast_addr_len);
        if (current_message_length < 0) {
            throw "ERROR reading from socket";
        }

        printf("%s", message_buffer);
    }
}

void ClientParser::client_send_message(int tcp_sockfd, int udp_sockfd, int multicast_sockfd, std::string nickname) {
    int udp_server_port_nr { 32042 };
    struct sockaddr_in udp_server_addr;

    udp_server_addr.sin_family = AF_INET;
    udp_server_addr.sin_port = htons(udp_server_port_nr);
    udp_server_addr.sin_addr.s_addr = INADDR_ANY;

    int multicast_port_nr { 32142 };
    struct sockaddr_in multicast_addr;

    multicast_addr.sin_family = AF_INET;
    multicast_addr.sin_port = htons(multicast_port_nr);
    multicast_addr.sin_addr.s_addr = INADDR_ANY; 

    char message_buffer[256];
    int current_message_length;

    std::regex udp_message("/u .*");
    std::regex multicast_message("/m .*");

    std::string user_nickname = '[' + nickname + "] ";
    std::string text_container;
    
    while(true) {
        bzero(message_buffer, 256);
        fgets(message_buffer, 255, stdin);

        text_container = message_buffer;

        if(std::regex_search(message_buffer, udp_message)) {
            text_container.erase(0, 3);
            text_container = user_nickname + text_container;
            bzero(message_buffer, 256);
            strcpy(message_buffer, text_container.c_str());

            sendto(udp_sockfd, (const char *)message_buffer, strlen(message_buffer), MSG_CONFIRM, (const struct sockaddr *) &udp_server_addr, sizeof(udp_server_addr));
        } else if (std::regex_search(message_buffer, multicast_message)) {
            text_container.erase(0, 3);
            text_container = user_nickname + text_container;
            bzero(message_buffer, 256);
            strcpy(message_buffer, text_container.c_str());

            if(sendto(multicast_sockfd, (const char *)message_buffer, strlen(message_buffer), 0, (struct sockaddr *) &multicast_addr, sizeof(multicast_addr)) < 0) {
                throw "ERROR writing to socket";
            }
        } else {
            text_container = user_nickname + text_container;
            bzero(message_buffer, 256);
            strcpy(message_buffer, text_container.c_str());

            current_message_length = write(tcp_sockfd, message_buffer, strlen(message_buffer));
            if (current_message_length < 0) {
                throw "ERROR writing to socket";
            }
        }
    }
}

std::string Client::generate_nickname() {
    srand(time(NULL));
    std::string user_nickname = "guest" + std::to_string(rand() % (int) 10e5);
    
    return user_nickname;
}
    
std::string Client::generate_nickname(char * nickname) {
    return nickname;
}