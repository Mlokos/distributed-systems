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
import static akka.pattern.Patterns.pipe;

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

					CompletableFuture<Object> future1 =
							ask(context().child("server_shop_to_client_worker" + clientCounter + "0").get(), query, Duration.ofMillis(300)).toCompletableFuture();
					CompletableFuture<Object> future2 =
							ask(context().child("server_shop_to_client_worker" + clientCounter + "1").get(), query, Duration.ofMillis(300)).toCompletableFuture();
					CompletableFuture<Object> future3 =
							ask(context().child("server_shop_to_client_worker" + clientCounter + "2").get(), query, Duration.ofMillis(300)).toCompletableFuture();
					
					Thread t1 = new Thread() {
						public void run() {
							try {
								ShopResponse x1 = (ShopResponse) future1.get();
				            	shopResponseList.add(x1);
								System.out.println("*");
							} catch (InterruptedException | ExecutionException e) { /** Swallow */ }
						}
					};
					t1.start();
					
					Thread t2 = new Thread() {
						public void run() {
							try {
								ShopResponse x2 = (ShopResponse) future2.get();
				            	shopResponseList.add(x2);
								System.out.println("**");
							} catch (InterruptedException | ExecutionException e) { /** Swallow */ }
						}
					};
					t2.start();
					
					Thread t3 = new Thread() {
						public void run() {
							try {
								ShopResponse x3 = (ShopResponse) future3.get();
				            	shopResponseList.add(x3);
								System.out.println("***");
							} catch (InterruptedException | ExecutionException e) { /** Swallow */ }
						}
					};
					t3.start();
					
					t1.join();
					t2.join();
					t3.join();
	            	
	            	for(ShopResponse sr : shopResponseList) {
						System.out.println(sr.getValue());
					}

					clientCounter += 1;
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
