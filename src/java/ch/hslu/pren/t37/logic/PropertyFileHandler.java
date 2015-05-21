package ch.hslu.pren.t37.logic;

import java.io.File;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Class is responsible for reading the configuration file.
 *
 * @author Team 37
 */
public class PropertyFileHandler {

    private static String fileName = "/home/pi/Wurfbot/config.properties";

    /**
     * Private Constructor.
     */
    private PropertyFileHandler() {
    }

    /**
     * This Method reads the Configuration-File and returns the object which was addressed.
     *
     * @param key the key which represents the property in the config.property File.
     * @return the corresponding value of the property specified by the key.
     * Or the value "Error" if an {@link IOException} occurred.
     */
    public static String getPropertyFile(String key) {
        try {
            Properties prop = new Properties();
            File f = new File(fileName);
            prop.load(new FileReader(f));
            return (prop.getProperty(key));
        } catch (IOException ex) {
            return "Error";
        }

    }
    
    /**
     * This Method sets the value of a given property represented by a key and stores it persistent inside the configuration-file.
     * 
     * @param key the key which represents the property in the config.property File
     * @param value the new value to be set.
     * @throws FileNotFoundException  if the configuration file wasn't found.
     */
    public static void setPropertyFile(String key, String value) throws FileNotFoundException {
        try {
            Properties prop = new Properties();
            File f = new File(fileName);
            prop.load(new FileReader(f));
            prop.setProperty(key, value);
            OutputStream output = new FileOutputStream(f);
            prop.store(output, "");
        } catch (IOException ex) {
            Logger.getLogger(PropertyFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
