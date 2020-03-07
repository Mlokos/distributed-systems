#ifndef CLIENT_H
#define CLIENT_H

/**
 * Gratitude to:
 * http://ntrg.cs.tcd.ie/undergrad/4ba2/multicast/antony/example.html
 * 
 * Also, make sure you have disabled your firewall to be able to receive multicast messages
 */
#include <iostream>
#include <vector>
#include <regex>

#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <thread>
#include <arpa/inet.h>

#define MESSAGE_LENGTH 256

class ClientTCP {
public:
    static int start_client_session();
    static void client_read_message(int sockfd);
};

class ClientUDP {
public:
    static int start_client_session();
    static void client_read_message(int sockfd);
};

class ClientMulticast {
public:
    static int start_client_session();
    static void client_read_message(int sockfd);
};

class ClientParser {
public:
    static void client_send_message(int tcp_sockfd, int udp_sockfd, int multicast_sockfd, std::string nickname);
};

class Client {
public:
    static std::string generate_nickname();
    static std::string generate_nickname(char * nickname);
};

#endif