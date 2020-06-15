#include <Ice/Ice.h>
#include <Domotics.h>

class HeaterImpl : public Domotics::Heater {
    int temperature;

public:
    HeaterImpl() {
        temperature = 20;
    }

    int getTemperature(const Ice::Current&) override {
        return temperature;
    }
    void setTemperature(int _temperature, const Ice::Current&) override {
        if(_temperature < 15 || _temperature > 30) {
            throw Domotics::ImproperValue("Set a value between 15 and 30");
        } else {
            temperature = _temperature;
        }
    }
};