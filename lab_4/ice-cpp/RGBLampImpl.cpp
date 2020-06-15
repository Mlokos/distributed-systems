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
        if(_brightness < 0 || _brightness > 100) {
            throw Domotics::ImproperValue("Set a value between 0 and 100");
        } else {
            brightness = _brightness;
        }
    }

    Domotics::colorRGB getColor(const Ice::Current&) override {
        return color;
    }
    void setColor(Domotics::colorRGB _color, const Ice::Current&) override {
        if(_color.red < 0 || _color.red > 255 || _color.green < 0 || _color.green > 255 || _color.blue < 0 || _color.blue > 255) {
            throw Domotics::ImproperValue("Set each color value between 0 and 255");
        } else {
            color = _color;
        }
    }
};