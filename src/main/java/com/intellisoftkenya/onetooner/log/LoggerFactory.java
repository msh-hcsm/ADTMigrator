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
    private static final Level level = Level.parse(PropertyManager.getProperty("logging.level"));

    public static void addHandler(Handler handler) {
        if (handlers == null) {
            handlers = new ArrayList<>();
        }
        handlers.add(handler);
    }

    public static Logger getLoger(String name) {
        Logger logger = Logger.getLogger(name);
//        logger.setUseParentHandlers(false);
        logger.setLevel(level);
        if (handlers != null && !handlers.isEmpty()) {
            for (Handler handler : handlers) {
                logger.addHandler(handler);
            }
        }
        return logger;
    }

    public static Level getLevel() {
        return level;
    }
}
