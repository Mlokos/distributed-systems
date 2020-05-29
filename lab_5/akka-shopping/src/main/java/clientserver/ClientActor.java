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
					context()
						.actorSelection("/user/server_actor")
						.tell(query, getSelf());
				})
				.match(ShopResponse.class, sr -> {
					String response;
	            	if(sr.getValue() == 10) {
	            		response = "No response from shops";
	            	} else {
	            		response = "Best price: " + sr.getValue();
	            	}
	            	System.out.println(response);
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
