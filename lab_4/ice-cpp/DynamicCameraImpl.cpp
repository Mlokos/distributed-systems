#include <Ice/Ice.h>
#include <Domotics.h>

class DynamicCameraImpl : public Domotics::DynamicCamera {
    int zoom;
    bool recording;
    int angle;

public:
    DynamicCameraImpl() {
        zoom = 1;
        recording = false;
        angle = 0;
    }

    int getZoom(const Ice::Current&) override {
        return zoom;
    }
    void setZoom(int _zoom, const Ice::Current&) override {
        if(_zoom < 0 || _zoom > 10) {
            throw Domotics::ImproperValue("Set a value between 0 and 10");
        } else {
            zoom = _zoom;
        }
    }

    bool isRecording(const Ice::Current&) override {
        return recording;
    }
    void setRecording(bool _recording, const Ice::Current&) override {
        recording = _recording;
    }

    int getAngle(const Ice::Current&) override {
        return angle;
    }
    void setAngle(int _angle, const Ice::Current&) override {
        if(_angle < 0 || _angle > 180) {
            throw Domotics::ImproperValue("Set a value between 0 and 180");
        } else {
            angle = _angle;
        }
    }
};