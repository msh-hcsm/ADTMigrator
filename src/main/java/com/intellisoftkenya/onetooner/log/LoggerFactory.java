package com.intellisoftkenya.onetooner.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author gitahi
 */
public class LoggerFactory {

    private static final int LIMIT = 102400;
    private static final int COUNT = 1;
    private static final String FILE_NAME = "C:\\Users\\gitahi\\Documents\\logs\\onetooner%u.log";

    public static Logger getLoger(String name) {
        Logger logger = Logger.getLogger(name);
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(FILE_NAME, LIMIT, COUNT, false);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            return logger;
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(LoggerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
