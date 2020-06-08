#include <Ice/Ice.h>
#include <Domotics.h>

class StaticCameraImpl : public Domotics::StaticCamera {
    int zoom;
    bool recording;

public:
    StaticCameraImpl() {
        zoom = 1;
        recording = false;
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
};