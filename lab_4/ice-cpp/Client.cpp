#include <Ice/Ice.h>
#include <Domotics.h>
#include <stdexcept>
 
using namespace std;
using namespace Domotics;
 
int
main(int argc, char* argv[])
{
    try
    {
        Ice::CommunicatorHolder ich(argc, argv);
        auto base1 = ich->stringToProxy("EyeholeCamera:default -p 10000");
        auto simpleCamera = Ice::checkedCast<StaticCameraPrx>(base1);
        auto base2 = ich->stringToProxy("FrontDoorCamera:default -p 10000");
        auto dynamicCamera = Ice::checkedCast<DynamicCameraPrx>(base2);
        auto base3 = ich->stringToProxy("KitchenRGBLamp:default -p 10000");
        auto lamp = Ice::checkedCast<RGBLampPrx>(base3);
        if(!simpleCamera || !dynamicCamera || !lamp)
        {
            throw std::runtime_error("Invalid proxy");
        }

        printf("%d\n", simpleCamera->getZoom());
        printf("%d\n", dynamicCamera->getZoom());
        printf("%d\n", lamp->getColor().blue);
        simpleCamera->setZoom(simpleCamera->getZoom() + 1);
        dynamicCamera->setZoom(dynamicCamera->getZoom() - 1);
        lamp->setColor(Domotics::colorRGB({
            lamp->getColor().red,
            lamp->getColor().green,
            lamp->getColor().blue - 1}));
    }
    catch(const std::exception& e)
    {
        cerr << e.what() << endl;
        return 1;
    }
    return 0;
}