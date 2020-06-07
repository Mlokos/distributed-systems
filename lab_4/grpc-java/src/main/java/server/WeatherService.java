package server;

import gen.grpc.SubscriptionGrpc.SubscriptionImplBase;
import gen.grpc.LocationList;
import gen.grpc.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import gen.grpc.Date;
import gen.grpc.Location;
import gen.grpc.WeatherType;
import io.grpc.stub.StreamObserver;
import sensor.WeatherSensor;

public class WeatherService extends SubscriptionImplBase {
	final private List<Location> clientLocationList = new ArrayList<Location>();
	final private List<WeatherSensor> weatherSensorList = new ArrayList<WeatherSensor>();
	
	@Override
	public void subscribeForWeather(LocationList request, StreamObserver<Weather> responseObserver) {
		for(Location l : request.getLocationList()) {
			clientLocationList.add(l);
		}
		
		/**
		 * There is an assumption that for every specified location
		 * is adequate WeatherStation
		 */
		for(Location l : clientLocationList) {
			weatherSensorList.add(new WeatherSensor(l));
		}
		
		/**
		 * TODO: Better solution - change implementation to asynchronous
		 * Requirements: grpc documentation, simple java threads are not working
		 */
		while(true) {
			for(int i = 0; i < clientLocationList.size(); ++i) {
				LocalDate currentDate = java.time.LocalDate.now();
				LocalTime currentTime = java.time.LocalTime.now();
				
				Date date = Date
						.newBuilder()
						.setYear(currentDate.getYear())
						.setMonth(currentDate.getMonthValue())
						.setDay(currentDate.getDayOfMonth())
						.setHour(currentTime.getHour())
						.setMinute(currentTime.getMinute())
						.setSecond(currentTime.getSecond())
						.build();
				
				Location location = clientLocationList.get(i);
				
				WeatherType weatherType = weatherSensorList.get(i).getWeather();
				
				Weather weather = Weather
						.newBuilder()
						.setLocation(location)
						.setDate(date)
						.setWeather(weatherType)
						.build();

				responseObserver.onNext(weather);
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}
