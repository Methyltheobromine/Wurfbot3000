# This Script is responsable for the Initialization
#
# Author : Team 37
# -----------------------
# -----------------------
# Import required Python libraries

import time
import sys
import RPi.GPIO as GPIO 
  
GPIO.setmode(GPIO.BCM)  	
 
DIR = 19
STEP = 26

GPIO.setup(DIR, GPIO.OUT)
GPIO.setup(STEP, GPIO.OUT) 
 
GPIO.setup(5, GPIO.IN, pull_up_down=GPIO.PUD_UP)#evt Up durch Down oder Up ersetzen

GPIO.output(DIR, True) #evt durch False ersetzen gibt nur die richtung an

#print GPIO.input(5)

for i in range(0,5):
  GPIO.output(STEP, True)
  time.sleep(0.005)
  GPIO.output(STEP, False)
  time.sleep(0.005)
  #print i

#time.sleep(0.05)

while GPIO.input(5)==1:
  GPIO.output(STEP, True)
  time.sleep(0.005)
  GPIO.output(STEP, False)
  #time.sleep(0.005)

#time.sleep(0.5)

for i in range(0,5):
  GPIO.output(STEP, True)
  time.sleep(0.005)
  GPIO.output(STEP, False)
  time.sleep(0.005)
  #print i

#time.sleep(0.5)

while GPIO.input(5)==0:
  GPIO.output(STEP, True)
  time.sleep(0.005)
  GPIO.output(STEP, False)
  time.sleep(0.005) 
  
print 'Ready'
GPIO.cleanup()


                        
