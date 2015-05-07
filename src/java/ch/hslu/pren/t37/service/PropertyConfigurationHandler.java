/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.service;

import ch.hslu.pren.t37.logic.PropertyFileHandler;
import java.io.FileNotFoundException;

/**
 *
 * @author Severin
 */
public class PropertyConfigurationHandler {

    enum ValueType {

        TURRET_DIST_MIDDLE,
        PIXEL_TO_STEP_CONVERSION,
        DC_STOP_SIGNAL,
        BALL_COUNTER,
        dcSPEED,
        LogLevel,
        SLEEPTIME,
        STEPPS_RELEASE_BALLS
    }

    public String setValue(String message) {
        try {
            String feedback = "";
            String[] splittedMessage = message.split(";");
            ValueType valueType = ValueType.valueOf(splittedMessage[0]);
            
            switch (valueType) {
                case TURRET_DIST_MIDDLE:
                case PIXEL_TO_STEP_CONVERSION:
                case DC_STOP_SIGNAL:
                case BALL_COUNTER:
                case dcSPEED:
                case LogLevel:
                case SLEEPTIME:
                case STEPPS_RELEASE_BALLS:
                    PropertyFileHandler.setPropertyFile(splittedMessage[0], splittedMessage[1]);
                    feedback = splittedMessage[0] + " erfolgreich geändert zu: " + splittedMessage[1];
                    break;
                default:
                    feedback = "Invalid Configuration Input";
                    break;
            }
            return feedback;
        } catch (FileNotFoundException ex) {
            return "Error";
        }
    }
}
