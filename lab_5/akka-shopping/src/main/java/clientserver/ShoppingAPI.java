package clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ShoppingAPI {
	public static void main(String[] args) throws InterruptedException, IOException {
		final ActorSystem system = ActorSystem.create("shopping_system");
		final ActorRef serverActor = system.actorOf(Props.create(ServerActor.class), "server_actor");

		/** WORKAROUND - server and client should be split into two separate files; however, there was issues with actors communication */
        final ActorRef clientActor = system.actorOf(Props.create(ClientActor.class));
        
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            clientActor.tell(line, null);
        }

        system.terminate();
	}
}
