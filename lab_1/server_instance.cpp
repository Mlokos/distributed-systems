#include "server.hpp"

ServerSubjectTCP * ServerSubjectTCP::s_instance = 0;
ServerSubjectUDP * ServerSubjectUDP::s_udp_instance = 0;

int main(int argc, char * argv[]) {
    std::thread tcp_service(&ServerTCP::server_listener_service);
    std::thread udp_service(&ServerUDP::server_listener_service);

    tcp_service.join();
    udp_service.join();

    return 0;
}