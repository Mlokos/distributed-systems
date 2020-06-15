module Domotics {
    exception ImproperValue {
        string message;
    };

    interface Heater {
        // int temperature;

        int getTemperature();
        void setTemperature(int temperature) throws ImproperValue;
    };

    interface StaticCamera {
        // int zoom;
        // bool record;

        int getZoom();
        void setZoom(int zoom) throws ImproperValue;

        bool isRecording();
        void setRecording(bool record) throws ImproperValue;
    };

    interface DynamicCamera extends StaticCamera {
        // int angle;

        int getAngle();
        void setAngle(int angle) throws ImproperValue;
    };

    interface Lamp {
        // int brightness;

        int getBrightness();
        void setBrightness(int brightness) throws ImproperValue;
    };

    struct colorRGB {
        int red;
        int green;
        int blue;
    }

    interface RGBLamp extends Lamp {
        // struct color lamp_color;

        colorRGB getColor();
        void setColor(colorRGB color) throws ImproperValue;
    };
}