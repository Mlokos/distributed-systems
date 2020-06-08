#include <Ice/Ice.h>
#include <Domotics.h>

class RGBLampImpl : public Domotics::RGBLamp {
    int brightness;
    Domotics::colorRGB color;

public:
    RGBLampImpl() {
        brightness = 100;
        color = { 255, 255, 255 };
    }

    int getBrightness(const Ice::Current&) override {
        return brightness;
    }
    void setBrightness(int _brightness, const Ice::Current&) override {
        brightness = _brightness;
    }

    Domotics::colorRGB getColor(const Ice::Current&) override {
        return color;
    }
    void setColor(Domotics::colorRGB _color, const Ice::Current&) override {
        color = _color;
    }
};