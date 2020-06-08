# https://stackoverflow.com/questions/4383571/importing-files-from-different-folder
import sys
sys.path.append('./gen')

import Ice
import Domotics
import UserInterface as UI

with Ice.initialize(sys.argv) as communicator:
    # Get connection
    baseEyeholeCamera = communicator.stringToProxy("EyeholeCamera:default -p 10000")

    baseFrontDoorCamera = communicator.stringToProxy("FrontDoorCamera:default -p 10000")
    baseGarageCamera = communicator.stringToProxy("GarageCamera:default -p 10000")
    baseYardCamera = communicator.stringToProxy("YardCamera:default -p 10000")

    baseGarageLamp = communicator.stringToProxy("GarageLamp:default -p 10000")
    baseYardLamp = communicator.stringToProxy("YardLamp:default -p 10000")

    baseSaloonRGBLamp = communicator.stringToProxy("SaloonRGBLamp:default -p 10000")
    baseBedroomRGBLamp = communicator.stringToProxy("BedroomRGBLamp:default -p 10000")
    baseKitchenRGBLamp = communicator.stringToProxy("KitchenRGBLamp:default -p 10000")

    baseHeater = communicator.stringToProxy("Heater:default -p 10000")

    # Specify object
    EyeholeCamera = Domotics.StaticCameraPrx.checkedCast(baseEyeholeCamera)

    FrontDoorCamera = Domotics.DynamicCameraPrx.checkedCast(baseFrontDoorCamera)
    GarageCamera = Domotics.DynamicCameraPrx.checkedCast(baseGarageCamera)
    YardCamera = Domotics.DynamicCameraPrx.checkedCast(baseYardCamera)

    GarageLamp = Domotics.LampPrx.checkedCast(baseGarageLamp)
    YardLamp = Domotics.LampPrx.checkedCast(baseYardLamp)

    SaloonRGBLamp = Domotics.RGBLampPrx.checkedCast(baseSaloonRGBLamp)
    BedroomRGBLamp = Domotics.RGBLampPrx.checkedCast(baseBedroomRGBLamp)
    KitchenRGBLamp = Domotics.RGBLampPrx.checkedCast(baseKitchenRGBLamp)

    Heater = Domotics.HeaterPrx.checkedCast(baseHeater)

    if not EyeholeCamera:
        raise RuntimeError("Invalid proxy. StaticCamera issue")

    if not FrontDoorCamera or not GarageCamera or not YardCamera:
        raise RuntimeError("Invalid proxy. DynamicCamera issue")

    if not GarageLamp or not YardLamp:
        raise RuntimeError("Invalid proxy. Simple lamp issue")

    if not SaloonRGBLamp or not BedroomRGBLamp or not KitchenRGBLamp:
        raise RuntimeError("Invalid proxy. RGB lamp issue")

    if not Heater:
        raise RuntimeError("Invalid proxy. Heater issue")

    # Below is just an user interface
    device = None
    UI.menu()
    while True:
        command = input()
        if(command == 'e' or command == 'eyehole'):
            device = EyeholeCamera
        elif(command == 'fc' or command == 'frontCamera'):
            device = FrontDoorCamera
        elif(command == 'gc' or command == 'garageCamera'):
            device = GarageCamera
        elif(command == 'yc' or command == 'yardCamera'):
            device = YardCamera
        elif(command == 'gl' or command == 'garageLamp'):
            device = GarageLamp
        elif(command == 'yl' or command == 'yardLamp'):
            device = YardLamp
        elif(command == 'sl' or command == 'saloonLamp'):
            device = SaloonRGBLamp
        elif(command == 'bl' or command == 'bedroomLamp'):
            device = BedroomRGBLamp
        elif(command == 'kl' or command == 'kitchenLamp'):
            device = KitchenRGBLamp
        elif(command == 'h' or command == 'heater'):
            device = Heater
        elif(command == 'm' or command == 'menu'):
            UI.menu()
        elif(command == 'q' or command == 'quit'):
            exit()
        else:
            UI.wrong_command()

        if(device):
            if(device == EyeholeCamera):
                UI.static_camera_interface()
                command = input()
                if(command == 'cz' or command == 'checkZoom'):
                    print(device.getZoom())
                elif(command == 'cr' or command == 'checkRecording'):
                    print(device.isRecording())
                elif(command == 'sz' or command == 'setZoom'):
                    UI.enter_value()
                    command = input()
                    device.setZoom(int(command))
                    UI.change_confirmation()
                elif(command == 'sr' or command == 'setRecording'):
                    UI.enter_value()
                    command = input()
                    device.setRecording(bool(command))
                    UI.change_confirmation()
                elif(command == 'm' or command == 'menu'):
                    UI.menu()
                elif(command == 'q' or command == 'quit'):
                    exit()
            elif(device == FrontDoorCamera or device == GarageCamera or device == YardCamera):
                UI.dynamic_camera_interface()
                command = input()
                if(command == 'cz' or command == 'checkZoom'):
                    print(device.getZoom())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'cr' or command == 'checkRecording'):
                    print(device.isRecording())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'cp' or command == 'checkPosition'):
                    print(device.getAngle())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'sz' or command == 'setZoom'):
                    UI.enter_value()
                    command = input()
                    device.setZoom(int(command))
                    UI.change_confirmation()
                elif(command == 'sr' or command == 'setRecording'):
                    UI.enter_value()
                    command = input()
                    device.setRecording(bool(command))
                    UI.change_confirmation()
                elif(command == 'sp' or command == 'setPosition'):
                    UI.enter_value()
                    command = input()
                    device.setAngle(int(command))
                    UI.change_confirmation()
                elif(command == 'm' or command == 'menu'):
                    UI.menu()
                elif(command == 'q' or command == 'quit'):
                    exit()
            elif(device == GarageLamp or device == YardLamp):
                UI.basic_lamp_interface()
                command = input()
                if(command == 'cb' or command == 'checkBrightness'):
                    print(device.getBrightness())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'sb' or command == 'setBrightness'):
                    UI.enter_value()
                    command = input()
                    device.setBrightness(int(command))
                    UI.change_confirmation()
                elif(command == 'm' or command == 'menu'):
                    UI.menu()
                elif(command == 'q' or command == 'quit'):
                    exit()
            elif(device == SaloonRGBLamp or device == BedroomRGBLamp or device == KitchenRGBLamp):
                UI.RGB_lamp_interface()
                command = input()
                if(command == 'cb' or command == 'checkBrightness'):
                    print(device.getBrightness())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'cc' or command == 'checkColor'):
                    print(device.getColor())
                    print("Type 'menu' (m) to go to menu")
                elif(command == 'sb' or command == 'setBrightness'):
                    UI.enter_value()
                    command = input()
                    device.setBrightness(int(command))
                    UI.change_confirmation()
                elif(command == 'sc' or command == 'setColor'):
                    UI.enter_value()
                    print("[RED] [GREEN] [BLUE]")
                    command = input()
                    colorList = command.split(' ')
                    if len(colorList) == 3:
                        device.setColor(Domotics.colorRGB(red=int(colorList[0]), green=int(colorList[1]), blue=int(colorList[2])))
                        UI.change_confirmation()
                    else:
                        print("Type 3 numbers delimited by a space")
                        print("Type 'menu' (m) to go to menu")
                elif(command == 'm' or command == 'menu'):
                    UI.menu()
                elif(command == 'q' or command == 'quit'):
                    exit()
            elif(device == Heater):
                UI.heater_interface()
                command = input()
                if(command == 'ct' or command == 'checkTemperature'):
                    print("Type 'menu' (m) to go to menu")
                    print(device.getTemperature())
                elif(command == 'st' or command == 'setTemperature'):
                    UI.enter_value()
                    command = input()
                    device.setTemperature(int(command))
                    UI.change_confirmation()
                elif(command == 'm' or command == 'menu'):
                    UI.menu()
                elif(command == 'q' or command == 'quit'):
                    exit()

        device = None
