package clientserver;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ServerActor extends AbstractActor {
	/** To make unique actors */
	private static int clientCounter = 0;
	
	/** Logs */
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	/** Message handler */
	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(String.class, query -> {					
					/** Create unique actor */
					context().actorOf(Props.create(ServerClientWorker.class), "server_client_worker" + clientCounter);
					context().child("server_client_worker" + clientCounter).get().tell(query, getSender());
					clientCounter += 1;
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}