package clientserver;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends AbstractActor {
	/** Logs */
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    
	/** Message handler */
	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(String.class, query -> {
					System.out.println(self());
					context()
						.actorSelection("akka://shopping_system/user/server_actora")
						.tell("hello", getSelf());
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
