/**
 * Gratitude to:
 * https://www.linuxhowtos.org/C_C++/socket.htm
 * https://sourcemaking.com/design_patterns/observer/cpp/3
 * 
 * https://stackoverflow.com/questions/14027426/stdthread-for-class-instances#14027482
 */
#include <iostream>
#include <vector>

#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <thread>

#define MESSAGE_LENGTH 256

class ServerSubject {
    std::vector <class Observer *> views;
    char value_message_buffer[MESSAGE_LENGTH];
    
	ServerSubject() {}
	static ServerSubject * s_instance;	
public:
    static ServerSubject * get_instance() {
        if(NULL == s_instance) {
            s_instance = new ServerSubject();
        }
		return s_instance;      
	}

    void attach(Observer * obs) {
        views.push_back(obs);
    }
    void set_val(char * val) {
        strcpy(value_message_buffer, val);
        notify();
    }
    char * get_val() {
        return value_message_buffer;
    }
    void notify();
};

ServerSubject * ServerSubject::s_instance = 0;

class Observer {
    ServerSubject * model;
    int client_sock_fd;
            
  public:
    Observer(ServerSubject * constr_model, int constr_client_sock_fd) {
        model = constr_model;
        model -> attach(this);
        client_sock_fd = constr_client_sock_fd;
    }
    void update() {
        char * message = model -> get_val();
        int current_message_length;

        current_message_length = write(client_sock_fd, message, strlen(message));
        if (current_message_length < 0) {
            throw "ERROR writing to socket";
        }
    }
};

void ServerSubject::notify() {
  for (int i = 0; i < views.size(); i++)
    views[i] -> update();
};

class Server {
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

    static void server_client_connection_service(int client_sock_fd) {
        Observer obs(ServerSubject::get_instance(), client_sock_fd);

        char message_buffer[MESSAGE_LENGTH];

        while(true) {
            int current_message_length = read(client_sock_fd, message_buffer, 255);
            if (current_message_length < 0) {
                throw "ERROR reading from socket";
            }
            ServerSubject::get_instance() -> set_val(message_buffer);
            printf("Here is the message: %s\n",message_buffer);
        }
    }
public:
    Server() = default;
    Server(int port_nr) :server_port_nr{ port_nr } {}

    static void server_listener_service() {
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
            bzero(message_buffer, 256);

            client_sock_fd = accept(server_sock_fd, (struct sockaddr *) &client_addr, &client_addr_len);
            if (client_sock_fd < 0) {
                throw "ERROR on accept";
            }

            std::thread client_connection_thread(&server_client_connection_service, client_sock_fd);
            client_connection_thread.detach();
        }
    }
};

int main(int argc, char *argv[]) {
    std::thread new_thread(&Server::server_listener_service);

    new_thread.join();
}
