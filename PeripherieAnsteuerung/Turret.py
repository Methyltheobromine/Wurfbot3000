# This Script is responsable for the turn of the turret
# 0 stands for left-turn
# 1 stands for right turn
#
# Author : Team 37
# -----------------------
# -----------------------
# Import required Python libraries

import time
import sys
import RPi.GPIO as GPIO # always needed with RPi.GPIO  
  
GPIO.setmode(GPIO.BCM)  # GPIO Nummer Verwenden!

DIR = 16
STEP = 20
  
GPIO.setup(DIR, GPIO.OUT)
GPIO.setup(STEP, GPIO.OUT)

stepps = int (sys.argv[1]) #number of stepps to turn around
direction = int(sys.argv[2]) #Direction: (1 or 0)

if direction:
  GPIO.output(DIR, True)
else:
  GPIO.output(DIR, False)
  
for i in range(0,stepps):
  GPIO.output(STEP, True)
  time.sleep(0.01)
  GPIO.output(STEP, False)
  time.sleep(0.01)
GPIO.cleanup()

                        
