package ch.hslu.pren.t37.logic;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This Class is responsable for the readout of the config file
 *
 * @author Team 37
 */
public class ReadPropertyFile {

    /**
     * Constructor.
     */
    private ReadPropertyFile() {
    }

    /**
     * This Method reads the config File and returns the hole object
     *
     * @return prop config-File as a Properties-Objekt
     */
    public static Properties getProperties() {
        try {
            Properties prop = new Properties();

            // Property-File laden
            try (
                    InputStream input = new FileInputStream("config.properties")) {
                prop.load(input);
            }

            return prop;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
