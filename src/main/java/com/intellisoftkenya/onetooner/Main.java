package com.intellisoftkenya.onetooner;

import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Main class.
 *
 * @author gitahi
 */
public class Main {
    
     private static final Logger LOGGER = LoggerFactory.getLoger(Main.class.getName());

    public static void main(String[] args) {
        try {
            new OneToOneMigrator().migrate();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Migration error!.", ex);
        }
    }
}
