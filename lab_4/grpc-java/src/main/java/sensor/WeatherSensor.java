package sensor;

import java.util.Random;

import gen.grpc.Location;
import gen.grpc.WeatherType;

public class WeatherSensor {
	private Location location;
	
	private WeatherType weather;
	/**
	 * Increase every time an object is called
	 * Change the weather when a ratio is big enough
	 */
	private int weatherChangePossibility;
	
	public WeatherSensor(Location location) {
		this.location = location;
		
		/** Get random weather at the start */
		Random rand = new Random();
		/** '-1' due to last argument being 'UNRECOGNIZED' */
		this.weather = WeatherType.valueOf(rand.nextInt(WeatherType.values().length - 1));
		
		this.weatherChangePossibility = 0;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public WeatherType getWeather() {		
		/** Increase every time an object is called */
		Random rand = new Random();
		weatherChangePossibility += rand.nextInt(10);

		/** Change the weather when a ratio is big enough */
		if(weatherChangePossibility >= 15) {
			weatherChangePossibility = 0;
			/** '-1' due to last argument being 'UNRECOGNIZED' */
			this.weather = WeatherType.valueOf(rand.nextInt(WeatherType.values().length - 1));
		}
		
		return this.weather;
	}
}
