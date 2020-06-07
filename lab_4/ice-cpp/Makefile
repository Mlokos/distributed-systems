generateIceInterface:
	slice2cpp ice-interface/Printer.ice

generateIceServer:
	c++ -I. -DICE_CPP11_MAPPING -c Printer.cpp Server.cpp
generateIceClient:
	c++ -I. -DICE_CPP11_MAPPING -c Printer.cpp Client.cpp

compileIceServer:
	c++ Server.o Printer.o -lIce++11 -lpthread -o bin/server.out
compileIceClient:
	c++ Client.o Printer.o -lIce++11 -lpthread -o bin/client.out

cleanAll:
	rm *.o *.out