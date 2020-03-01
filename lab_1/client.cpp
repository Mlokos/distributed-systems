#include <iostream>
#include <vector>

#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <thread>

#define MESSAGE_LENGTH 256

class Client {
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
        bcopy((char *)server -> h_addr,
            (char *)&server_addr.sin_addr.s_addr,
            server -> h_length);
        server_addr.sin_port = htons(server_port_nr);
        if (connect(server_connection_sock_fd,(struct sockaddr *) &server_addr, sizeof(server_addr)) < 0) {
            throw "ERROR connecting";
        }

        return server_connection_sock_fd;
    }
    static void client_send_message(int sockfd) {
        char message_buffer[256];
        int current_message_length;
        while(true) {
            bzero(message_buffer, 256);
            fgets(message_buffer, 255, stdin);

            current_message_length = write(sockfd,message_buffer, strlen(message_buffer));
            if (current_message_length < 0) {
                throw "ERROR writing to socket";
            }
        }
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

int main(int argc, char * argv[]) {
    int sockfd = Client::start_client_session();

    std::thread t1(&Client::client_send_message, sockfd);
    std::thread t2(&Client::client_read_message, sockfd);

    t1.join();
    t2.join();

    return 0;
}
