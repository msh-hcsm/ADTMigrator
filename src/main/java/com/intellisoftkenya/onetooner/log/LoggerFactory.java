package com.intellisoftkenya.onetooner.log;

import com.intellisoftkenya.onetooner.PropertyManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class LoggerFactory {

    private static List<Handler> handlers;
    
    public static void addHandler(Handler handler) {
        if (handlers == null) {
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
    }
    
    public static void setHandlers(List<Handler> handlers) {
        if (handlers != null) {
            LoggerFactory.handlers = handlers;
        }
    }

    public static Logger getLoger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(Level.parse(PropertyManager.getProperty("logging.level")));
        if (handlers != null && !handlers.isEmpty()) {
            for (Handler handler : handlers) {
                logger.addHandler(handler);
            }
        }
        return logger;

    }
}
