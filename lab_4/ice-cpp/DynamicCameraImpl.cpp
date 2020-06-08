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
        zoom = _zoom;
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
        angle = _angle;
    }
};