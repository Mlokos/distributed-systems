#include "client.hpp"

int main(int argc, char * argv[]) {
    std::string user_nickname = Client::generate_nickname();

    if(argc > 1) {
        user_nickname = Client::generate_nickname(argv[1]);
    } else {
        user_nickname = Client::generate_nickname();
    }

    int tcp_sockfd = ClientTCP::start_client_session();
    int udp_sockfd = ClientUDP::start_client_session();
    int multicast_sockfd = ClientMulticast::start_client_session();

    std::thread t1(&ClientTCP::client_read_message, tcp_sockfd);
    std::thread t2(&ClientUDP::client_read_message, udp_sockfd);
    std::thread t3(&ClientMulticast::client_read_message, multicast_sockfd);
    std::thread t4(&ClientParser::client_send_message, tcp_sockfd, udp_sockfd, multicast_sockfd, user_nickname);

    t1.join();
    t2.join();
    t3.join();
    t4.join();

    return 0;
}
