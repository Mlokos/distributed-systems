package clientserver;

import java.util.LinkedList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ServerClientWorker extends AbstractActor {
	private List<ShopResponse> shopResponseList = new LinkedList<ShopResponse>();
	
	/** The amount of shops */
	private final int SHOPS_QUAN = 3;
	
	/** To make unique actors */
	private static int clientCounter = 0;
	
	/** Logs */
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	/** Message handler */
	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.match(String.class, query -> {
					for(int i = 0; i < SHOPS_QUAN; ++i) {
						/** Create unique actor */
						context().actorOf(Props.create(ServerShopToClientWorker.class), "server_shop_to_client_worker" + clientCounter + "" + i);
						context().child("server_shop_to_client_worker" + clientCounter + "" + i).get().tell(query, getSelf());
						clientCounter += 1;
					}
				})
				.match(ShopResponse.class, shopResponse -> {
					shopResponseList.add(shopResponse);
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
