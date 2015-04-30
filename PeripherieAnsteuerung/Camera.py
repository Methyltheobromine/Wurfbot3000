# Takes a Photo with the Raspberry Pi Camera Module and saves it
#
# Author : Team 37
# -----------------------
# -----------------------
# Import required picamera libraries

import picamera

camera = picamera.PiCamera()
camera.resolution = (640,480)
camera.capture('/home/pi/git/PREN/camera.jpg')

