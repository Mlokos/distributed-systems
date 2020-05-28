package clientserver;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ServerAPI {
	public static void main(String[] args) throws InterruptedException {
		final ActorSystem system = ActorSystem.create("shopping_system");
		final ActorRef serverActor = system.actorOf(Props.create(ServerActor.class), "server_actor");
	
		/** DEBUG */
		while(true) {
			serverActor.tell("hi", null);
			Thread.sleep(2000);
		}
	}
}
