package trash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ShopMock {
	Map<String, Integer> products = new HashMap<String, Integer>();
	
	public ShopMock(List<String> product_names) {
		Random rand = new Random();
		
		for(String name : product_names) {
			products.put(name, rand.nextInt(100));
		}
	}
	
	public Integer get_product(String name) {
		// Simulating delay
		Random rand = new Random();
		try {
			Thread.sleep(100 + rand.nextInt(400));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return products.get(name);
	}
}
