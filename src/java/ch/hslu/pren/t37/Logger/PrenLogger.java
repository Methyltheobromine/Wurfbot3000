/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.pren.t37.Logger;

/**
 *
 * @author Severin
 */
public class PrenLogger {

    private static LogLevel currentLogLevel;

    public enum LogLevel {
        DEBUG,
        WARN,
        ERROR
    }

    public static void log(LogLevel loglevel, String msg) {
        if (currentLogLevel.ordinal() <= loglevel.ordinal()) {
            System.out.println(msg);
        }
    }
    public static void setCurrentLoglevel(LogLevel loglevel){
        currentLogLevel = loglevel;
    }

}
