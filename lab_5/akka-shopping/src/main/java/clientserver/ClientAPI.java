package clientserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ClientAPI {
	public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("client_shopping_system");
        final ActorRef actor = system.actorOf(Props.create(ClientActor.class));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            actor.tell(line, null);
        }

        system.terminate();
    }
}
