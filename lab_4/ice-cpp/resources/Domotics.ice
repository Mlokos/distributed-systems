module Domotics {
    interface Heater {
        // int temperature;

        int getTemperature();
        void setTemperature(int temperature);
    };

    interface StaticCamera {
        // int zoom;
        // bool record;

        int getZoom();
        void setZoom(int zoom);

        bool isRecording();
        void setRecording(bool record);
    };

    interface DynamicCamera extends StaticCamera {
        // int angle;

        int getAngle();
        void setAngle(int angle);
    };

    interface Lamp {
        // int brightness;

        int getBrightness();
        void setBrightness(int brightness);
    };

    struct colorRGB {
        int red;
        int green;
        int blue;
    }

    interface RGBLamp extends Lamp {
        // struct color lamp_color;

        colorRGB getColor();
        void setColor(colorRGB color);
    };
}