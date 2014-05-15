package com.intellisoftkenya.onetooner;

import com.intellisoftkenya.onetooner.business.OneToOneMigrator;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Main class.
 *
 * @author gitahi
 */
public class Main {

    public static void main(String[] args) {
        try {
            new OneToOneMigrator().migrate();
        } catch (SQLException | RuntimeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
