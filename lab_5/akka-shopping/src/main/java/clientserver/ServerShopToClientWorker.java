package clientserver;

import java.util.Random;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ServerShopToClientWorker extends AbstractActor {
	/** Logs */
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	/** Message handler */
	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(String.class, query -> {
					Random rand = new Random();
					/** Sleep 100 to 500 ms*/
					Thread.sleep(100 + rand.nextInt(400));
					
					/** Send a number in range 0 - 9 */
					getSender().tell(new ShopResponse(rand.nextInt(10)), getSelf());
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
