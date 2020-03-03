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
        int server_port_nr { 32142 };
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
    static void client_send_message(int tcp_sockfd, int udp_sockfd) {
        int udp_server_port_nr { 32142 };
        struct sockaddr_in udp_server_addr;

        udp_server_addr.sin_family = AF_INET;
        udp_server_addr.sin_port = htons(udp_server_port_nr);
        udp_server_addr.sin_addr.s_addr = INADDR_ANY; 

        char message_buffer[256];
        int current_message_length;

        std::regex udp_message("/u .*");
        std::regex multicast_message("/m .*");
        
        while(true) {
            bzero(message_buffer, 256);
            fgets(message_buffer, 255, stdin);

            if(std::regex_search(message_buffer, udp_message)) {
                sendto(udp_sockfd, (const char *)message_buffer, strlen(message_buffer), MSG_CONFIRM, (const struct sockaddr *) &udp_server_addr, sizeof(udp_server_addr));
            } else if (std::regex_search(message_buffer, multicast_message)) {
                /* TODO; implement code */
            } else {
                current_message_length = write(tcp_sockfd, message_buffer, strlen(message_buffer));
                if (current_message_length < 0) {
                    throw "ERROR writing to socket";
                }
            }
        }
    }
};

int main(int argc, char * argv[]) {
    int tcp_sockfd = ClientTCP::start_client_session();
    int udp_sockfd = ClientUDP::start_client_session();

    std::thread t1(&ClientTCP::client_read_message, tcp_sockfd);
    std::thread t2(&ClientUDP::client_read_message, udp_sockfd);
    std::thread t3(&ClientParser::client_send_message, tcp_sockfd, udp_sockfd);

    t1.join();
    t2.join();
    t3.join();

    return 0;
}
