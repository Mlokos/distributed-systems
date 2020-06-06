# https://stackoverflow.com/questions/4383571/importing-files-from-different-folder
import sys
sys.path.append('./gen')

import grpc

import gen.weatherSubscription_pb2
import gen.weatherSubscription_pb2_grpc

channel = grpc.insecure_channel('localhost:9000')
stub = gen.weatherSubscription_pb2_grpc.SubscriptionStub(channel)

location1 = gen.weatherSubscription_pb2.Location(latitude=10, longitude=10)
location2 = gen.weatherSubscription_pb2.Location(latitude=20, longitude=50)

locationList = gen.weatherSubscription_pb2.LocationList(location=[location1, location2])
feature = stub.subscribeForWeather(locationList)

for weather in feature:
    print('Latitude: {}'.format(weather.location.latitude))
    print('Longitude: {}'.format(weather.location.longitude))
    print('Date: {}-{}-{}'.format(weather.date.year, weather.date.month, weather.date.day))
    print('Time: {}:{}:{}'.format(weather.date.hour, weather.date.minute, weather.date.second))
    print('Weather: {}'.format(gen.weatherSubscription_pb2.WeatherType.Name(weather.weather)))
    print()