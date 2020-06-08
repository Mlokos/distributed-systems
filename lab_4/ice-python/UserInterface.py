import os


def menu():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Welcome in your intelligent home.
----------------------------------
Please, write a name of the device you want to select:
'eyehole' (e)
'frontCamera' (fc)
'garageCamera' (gc)
'yardCamera' (yc)
'garageLamp' (gl)
'yardLamp' (yl)
'saloonLamp' (sl)
'bedroomLamp' (bl)
'kitchenLamp' (kl)
'heater' (h)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def wrong_command():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Wrong command
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def static_camera_interface():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Interface:
'checkZoom' (cz)
'checkRecording' (cr)
'setZoom' (sz)
'setRecording' (sr)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def dynamic_camera_interface():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Interface:
'checkZoom' (cz)
'checkRecording' (cr)
'checkPosition' (cp)
'setZoom' (sz)
'setRecording' (sr)
'setPosition' (sp)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def basic_lamp_interface():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Interface:
'checkBrightness' (cb)
'setBrightness' (sb)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def RGB_lamp_interface():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Interface:
'checkBrightness' (cb)
'checkColor' (cc)
'setBrightness' (sb)
'setColor' (sc)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def heater_interface():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Interface:
'checkTemperature' (ct)
'setTemperature' (st)
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def enter_value():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Please enter new value
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


def change_confirmation():
    os.system('cls' if os.name == 'nt' else 'clear')
    print("""----------------------------------
Value has been set
----------------------------------
To return to menu type 'menu' (m)
To quit type 'quit' (q)""")


# def change_error():
#     os.system('cls' if os.name == 'nt' else 'clear')
#     print("""----------------------------------
# This value could not be set
# ----------------------------------
# To return to menu type 'menu' (m)
# To quit type 'quit' (q)""")
