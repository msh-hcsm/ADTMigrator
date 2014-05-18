package com.intellisoftkenya.onetooner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages application properties. Supports reading and writing to an external
 * properties file as well as restoring default values.
 * 
 * @author gitahi
 */
public class PropertyManager {

    private static final String propertiesFile = "settings.properties";
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    /**
     * Gets the property value given it's key. Optionally may be instructed to
     * return the default instead of the current value.
     * 
     * @param name the property key
     * @param def if true, the default value rather that the current property
     * value is returned
     *
     * @return the property value
     */
    public static String getProperty(String name, boolean def) {
        if (!def) {
            return properties.getProperty(name);
        }
        if (name != null) {
            switch (name) {
                case "source.driver":
                    return "sun.jdbc.odbc.JdbcOdbcDriver";
                case "source.url":
                    return "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)}; "
                            + "DBQ=C:\\ARVDatabase\\Datafiles\\ARVDispensingDatabase_be.mdb";
                case "source.username":
                    return "";
                case "source.password":
                    return "";
                case "destination.driver":
                    return "com.mysql.jdbc.Driver";
                case "destination.url":
                    return "jdbc:mysql://localhost:3306/fdt";
                case "destination.username":
                    return "root";
                case "destination.password":
                    return "2806";
                case "logging.level":
                    return "INFO";
            }
        }
        return "";
    }

    /**
     * Gets the property value given it's key.
     * 
     * @param name the property key
     * 
     * @return the property value.
     */
    public static String getProperty(String name) {
        return getProperty(name, false);
    }

    /**
     * Sets the property given it's key and value.
     * 
     * @param key the property key
     * @param value the property value
     */
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Persists the current property setting to a properties file.
     */
    public static void saveProperties() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(propertiesFile);
            properties.store(outputStream, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Loads all property values from a properties file.
     */
    private static void loadProperties() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(PropertyManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
