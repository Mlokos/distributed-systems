package clientserver;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import static akka.pattern.Patterns.ask;

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
					}
					
					List<CompletableFuture<Object>> compeltableFutureList = new LinkedList<CompletableFuture<Object>>();
					for(int i = 0; i < SHOPS_QUAN; ++i) {
						compeltableFutureList.add(ask(context().child("server_shop_to_client_worker" + clientCounter + "" + i).get(), query, Duration.ofMillis(300)).toCompletableFuture());
					}

					List<Thread> threadList = new LinkedList<Thread>();
					for(CompletableFuture<Object> cf : compeltableFutureList) {
						threadList.add(
							new Thread() {
								public void run() {
									try {
						            	shopResponseList.add((ShopResponse) cf.get());
									} catch (InterruptedException | ExecutionException e) { /** Swallow */ }
								}
							}
						);
					}
					
					for(Thread t : threadList) {
						t.start();
					}
					
					for(Thread t : threadList) {
						t.join();
					}
	            	
					/** WORKAROUND - java comparator does not work for me */
					int lowestValue = 10;
	            	for(ShopResponse sr : shopResponseList) {
	            		if(sr.getValue() < lowestValue) {
	            			lowestValue = sr.getValue();
	            		}
					}
	            	
	            	ShopResponse sr = new ShopResponse(lowestValue);

					context()
						.actorSelection("/user/client")
						.tell(sr, getSelf());
	            	
					clientCounter += 1;
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
