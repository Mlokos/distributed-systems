#ifndef SERVER_H
#define SERVER_H

/**
 * Gratitude to:
 * https://www.linuxhowtos.org/C_C++/socket.htm
 * https://sourcemaking.com/design_patterns/observer/cpp/3
 * https://www.cs.unc.edu/~jeffay/dirt/FAQ/comp249-001-F99/mcast-socket.html
 * 
 * https://stackoverflow.com/questions/14027426/stdthread-for-class-instances#14027482
 */
#include <iostream>
#include <vector>
#include <set>
#include <algorithm>

#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <thread>

#define MESSAGE_LENGTH 256

class ServerSubjectTCP {
    std::vector <class ObserverTCP *> views;
    char message_buffer[MESSAGE_LENGTH];
    
	ServerSubjectTCP() {}
	static ServerSubjectTCP * s_instance;	
public:
    static ServerSubjectTCP * get_instance();

    void attach(ObserverTCP * observer);

    void set_message(char * message, int msg_sock_fd);
    char * get_message();

    void notify(int msg_sock_fd);
};

class ServerSubjectUDP {
    std::vector <class ObserverUDP *> views;
    char message_buffer[MESSAGE_LENGTH];
    
	ServerSubjectUDP() {}
	static ServerSubjectUDP * s_udp_instance;	
public:
    static ServerSubjectUDP * get_instance();

    void attach(ObserverUDP * observer);

    void set_message(char * message, struct sockaddr_in client_addr);
    char * get_message();

    void notify(struct sockaddr_in client_addr);
};

class ObserverTCP {
    ServerSubjectTCP * model;
    int client_sock_fd;
            
public:
    ObserverTCP(ServerSubjectTCP * constr_model, int constr_client_sock_fd);
    void update(int msg_sock_fd);
};

class ObserverUDP {
    ServerSubjectUDP * model;
    int client_sock_fd;
    struct sockaddr_in client_addr;
            
  public:
    ObserverUDP(ServerSubjectUDP * constr_model, int constr_client_sock_fd, struct sockaddr_in constr_client_addr);
    void update(struct sockaddr_in msg_client_addr);
};

class ServerTCP {
    static void server_client_connection_service(int client_sock_fd);
public:
    static void server_listener_service();
};

class ServerUDP {
public:
    static void server_listener_service();
};

#endif