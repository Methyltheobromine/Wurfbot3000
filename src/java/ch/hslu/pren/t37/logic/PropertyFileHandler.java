package ch.hslu.pren.t37.logic;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Class is responsable for the readout of the config file
 *
 * @author Team 37
 */
public class PropertyFileHandler {

    private static String fileName = "/home/pi/Wurfbot/config.properties";

    /**
     * Constructor.
     */
    private PropertyFileHandler() {
    }

    /**
     * This Method reads the config File and returns the hole object
     *
     * @return prop config-File as a Properties-Objekt
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

    public static void setPropertyFile(String key, String value) throws FileNotFoundException {

        try {
            Properties prop = new Properties();
            File f = new File(fileName);
            System.out.println(f.getAbsolutePath());
            prop.load(new FileReader(f));
            prop.setProperty(key, value);
            OutputStream output = new FileOutputStream(f);
            prop.store(output, "");
        } catch (IOException ex) {
            Logger.getLogger(PropertyFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
