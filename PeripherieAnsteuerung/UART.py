# Create an UART-Connection and sends the value between 000 and 170
# to speed up the fly wheel
#
# Author : Team 37
# -----------------------
# -----------------------
# Import required Python libraries

import serial
import sys

UART = serial.Serial("/dev/ttyAMA0", 38400)

string = str(sys.argv[1])
UART.open()
UART.write(string)
UART.close()

