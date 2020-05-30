package clientserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

	            	Connection c = null;
	                Statement stmt = null;
	                
	                try {
	                   Class.forName("org.sqlite.JDBC");
	                   c = DriverManager.getConnection("jdbc:sqlite:shopping_system.db");

	                   stmt = c.createStatement();
	                   String sql = "CREATE TABLE QUERIES_QUAN " +
	                                  "(NAME           TEXT    NOT NULL, " + 
	                                  " QUAN           INT     NOT NULL)"; 
	                   stmt.executeUpdate(sql);
	                   stmt.close();
	                   c.close();
	                } catch ( Exception e ) {
	                	/** Swallow - db already exists */
	                }
	            	
	                try {
	                	Class.forName("org.sqlite.JDBC");
	                	c = DriverManager.getConnection("jdbc:sqlite:shopping_system.db");
	                    c.setAutoCommit(false);
	                	stmt = c.createStatement();
	                	
	                    ResultSet rs = stmt.executeQuery( "SELECT * FROM QUERIES_QUAN WHERE NAME = '" + query + "';" );
	                	if(!rs.next()) {
		                	String sql = "INSERT INTO QUERIES_QUAN (NAME,QUAN) " +
		                            "VALUES ('" + query + "', 1 );"; 
		                	stmt.executeUpdate(sql);
		                	c.commit();
		                	
		                	/** Update response */
		                	sr.setOccurence(0);
	                	} else {
	                		int currQuan = rs.getInt("QUAN");
	                		sr.setOccurence(currQuan);
	                		
		                	String sql = "UPDATE QUERIES_QUAN set QUAN = " + (currQuan + 1) + " where NAME = '" + query + "';"; 
		                	stmt.executeUpdate(sql);
		                	c.commit();
		                	
	                	}
	                    rs.close();
	                    stmt.close();
	                    c.close();
	                	
	                } catch ( Exception e ) {
	                	System.out.println(e);
	                	/** Swallow */
	                }

					context()
						.actorSelection("/user/client")
						.tell(sr, getSelf());
					
					clientCounter += 1;
				})
				.matchAny(o -> log.info("Received non-String message"))
				.build();
	}
}
