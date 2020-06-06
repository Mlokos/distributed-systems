package server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class WeatherServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = ServerBuilder.forPort(9000).addService( new WeatherService()).build();
	
		server.start();
		
		System.out.println("Server started at " + server.getPort());
		
		server.awaitTermination();
	}
}
