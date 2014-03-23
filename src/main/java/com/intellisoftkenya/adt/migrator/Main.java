package com.intellisoftkenya.adt.migrator;

import com.intellisoftkenya.adt.migrator.business.OneToOneMigrator;
import com.intellisoftkenya.adt.migrator.exceptions.UnsupportedDataTypeException;
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
            new OneToOneMigrator().migrateOneToOnes();
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
