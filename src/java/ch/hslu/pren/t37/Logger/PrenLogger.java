package ch.hslu.pren.t37.Logger;

/**
 * Class to log Messages.
 * 
 * @author Team 37
 */
public class PrenLogger {

    private static LogLevel currentLogLevel;

    /**
     * Enumeration of available Log Levels.
     */
    public enum LogLevel {
        DEBUG,
        WARN,
        ERROR
    }

    /**
     * Logs a Message if the given log level matches.
     * @param loglevel {@link LogLevel}
     * @param msg to be logged.
     */
    public static void log(LogLevel loglevel, String msg) {
        if (currentLogLevel.ordinal() <= loglevel.ordinal()) {
            System.out.println(msg);
        }
    }
    
    /**
     * Sets the current log level.
     * @param loglevel to be set.
     */
    public static void setCurrentLoglevel(LogLevel loglevel){
        currentLogLevel = loglevel;
    }

}
