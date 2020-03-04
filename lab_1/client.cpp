#include <iostream>
#include <vector>
#include <regex>

#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <thread>

#define MESSAGE_LENGTH 256

class ClientTCP {
public:
    static int start_client_session() {
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

    static void client_read_message(int sockfd) {
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
};

class ClientUDP {
public:
    static int start_client_session() {
        /* File descriptors for server and client */
        int server_connection_sock_fd;

        server_connection_sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
        if(server_connection_sock_fd < 0) { 
            throw "socket creation failed";
        } 

        return server_connection_sock_fd;
    }

    static void client_read_message(int sockfd) {
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
};

class ClientParser {
public:
    static void client_send_message(int tcp_sockfd, int udp_sockfd, std::string nickname) {
        int udp_server_port_nr { 32042 };
        struct sockaddr_in udp_server_addr;

        udp_server_addr.sin_family = AF_INET;
        udp_server_addr.sin_port = htons(udp_server_port_nr);
        udp_server_addr.sin_addr.s_addr = INADDR_ANY; 

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
                /* TODO; implement code */
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
};

class Client {
public:
    static std::string generate_nickname() {
        srand(time(NULL));
        std::string user_nickname = "guest" + std::to_string(rand() % (int) 10e5);
        
        return user_nickname;
    }
    
    static std::string generate_nickname(char * nickname) {
        return nickname;
    }
};

int main(int argc, char * argv[]) {
    std::string user_nickname = Client::generate_nickname();

    if(argc > 1) {
        user_nickname = Client::generate_nickname(argv[1]);
    } else {
        user_nickname = Client::generate_nickname();
    }

    int tcp_sockfd = ClientTCP::start_client_session();
    int udp_sockfd = ClientUDP::start_client_session();

    std::thread t1(&ClientTCP::client_read_message, tcp_sockfd);
    std::thread t2(&ClientUDP::client_read_message, udp_sockfd);
    std::thread t3(&ClientParser::client_send_message, tcp_sockfd, udp_sockfd, user_nickname);

    t1.join();
    t2.join();
    t3.join();

    return 0;
}
