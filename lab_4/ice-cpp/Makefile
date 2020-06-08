generateIceInterface:
	slice2cpp ice-interface/Domotics.ice

generateIceServer:
	c++ -I. -DICE_CPP11_MAPPING -c Server.cpp Domotics.cpp
	c++ -I. -DICE_CPP11_MAPPING -c HeaterImpl.cpp Domotics.cpp
	c++ -I. -DICE_CPP11_MAPPING -c StaticCameraImpl.cpp Domotics.cpp
	c++ -I. -DICE_CPP11_MAPPING -c DynamicCameraImpl.cpp Domotics.cpp
	c++ -I. -DICE_CPP11_MAPPING -c LampImpl.cpp Domotics.cpp
	c++ -I. -DICE_CPP11_MAPPING -c RGBLampImpl.cpp Domotics.cpp
generateIceClient:
	c++ -I. -DICE_CPP11_MAPPING -c Client.cpp Domotics.cpp

compileIceServer:
	c++ Server.o HeaterImpl.o StaticCameraImpl.o DynamicCameraImpl.o Domotics.o LampImpl.o RGBLampImpl.o -lIce++11 -lpthread -o bin/server.out
compileIceClient:
	c++ Client.o Domotics.o -lIce++11 -lpthread -o bin/client.out

cleanAll:
	rm *.o ./bin/*.out

cleanObjectFiles:
	rm *.o

server:
	c++ -I. -DICE_CPP11_MAPPING -c Server.cpp Domotics.cpp
