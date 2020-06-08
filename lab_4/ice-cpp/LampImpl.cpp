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
        brightness = _brightness;
    }
};