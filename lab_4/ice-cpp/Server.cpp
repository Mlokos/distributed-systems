#include <Ice/Ice.h>
#include <Domotics.h>

#include "HeaterImpl.cpp"
#include "StaticCameraImpl.cpp"
#include "DynamicCameraImpl.cpp"
#include "LampImpl.cpp"
#include "RGBLampImpl.cpp"

int main(int argc, char* argv[]) {
    try {
        Ice::CommunicatorHolder ich(argc, argv);
        auto adapter = ich->createObjectAdapterWithEndpoints("DomoticsAdapter", "default -p 10000");
        
        auto servantEyeholeCamera = std::make_shared<StaticCameraImpl>();
        adapter->add(servantEyeholeCamera, Ice::stringToIdentity("EyeholeCamera"));
        
        auto servantFrontDoorCamera = std::make_shared<DynamicCameraImpl>();
        adapter->add(servantFrontDoorCamera, Ice::stringToIdentity("FrontDoorCamera"));
        auto servantGarageCamera = std::make_shared<DynamicCameraImpl>();
        adapter->add(servantGarageCamera, Ice::stringToIdentity("GarageCamera"));
        auto servantYardCamera = std::make_shared<DynamicCameraImpl>();
        adapter->add(servantYardCamera, Ice::stringToIdentity("YardCamera"));
        
        auto servantGarageLamp = std::make_shared<LampImpl>();
        adapter->add(servantGarageLamp, Ice::stringToIdentity("GarageLamp"));
        auto servantYardLamp = std::make_shared<LampImpl>();
        adapter->add(servantYardLamp, Ice::stringToIdentity("YardLamp"));
        
        auto servantSaloonRGBLamp = std::make_shared<RGBLampImpl>();
        adapter->add(servantSaloonRGBLamp, Ice::stringToIdentity("SaloonRGBLamp"));
        auto servantBedroomRGBLamp = std::make_shared<RGBLampImpl>();
        adapter->add(servantBedroomRGBLamp, Ice::stringToIdentity("BedroomRGBLamp"));
        auto servantKitchenRGBLamp = std::make_shared<RGBLampImpl>();
        adapter->add(servantKitchenRGBLamp, Ice::stringToIdentity("KitchenRGBLamp"));
        
        adapter->activate();
        ich->waitForShutdown();
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << std::endl;
        return 1;
    }
    return 0;
}