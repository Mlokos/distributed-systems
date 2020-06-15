#include <Ice/Ice.h>
#include <Domotics.h>

class LampImpl : public Domotics::Lamp {
    int brightness;

public:
    LampImpl() {
        brightness = 100;
    }

    int getBrightness(const Ice::Current&) override {
        return brightness;
    }
    void setBrightness(int _brightness, const Ice::Current&) override {
        if(_brightness < 0 || _brightness > 100) {
            throw Domotics::ImproperValue("Set a value between 0 and 100");
        } else {
            brightness = _brightness;
        }
    }
};