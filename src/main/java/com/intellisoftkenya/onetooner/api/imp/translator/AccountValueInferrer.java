package com.intellisoftkenya.onetooner.api.imp.translator;

import com.intellisoftkenya.onetooner.api.translator.ValueInferrer;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Supports inference of account values from misspelt or obsolete accounts by
 * mapping such accounts to known accounts.
 *
 * @author gitahi
 */
public class AccountValueInferrer implements ValueInferrer {

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();
    private final Map<String, Integer> cache = new HashMap<>();

    @Override
    public Integer infer(String value) {
        if ("1".equalsIgnoreCase(value)) {
            return readId("KEMSA");
        } else if ("2".equalsIgnoreCase(value)) {
            return readId("KENYA PHARMA");
        } else if ("3".equalsIgnoreCase(value)) {
            return readId("DRUG STORE");
        } else if ("4".equalsIgnoreCase(value)) {
            return readId("PATIENT RETURNS");
        } else if ("ARV Bulk store".equalsIgnoreCase(value)) {
            return readId("BULK STORE");
        } else if ("PATIENT RETURNS".equalsIgnoreCase(value)) {
            return readId("PATIENT RETURNS");
        } else if ("SATILITE SITES".equalsIgnoreCase(value)) {
            return readId("SATELLITE SITES");
        } else if ("99".equalsIgnoreCase(value)) {
            return readId("STOCK TAKE");
        }
        return null;
    }

    /**
     * Read the FDT integer database id for this name value in the account
     * table.
     */
    private Integer readId(String value) {
        Integer ret = cache.get(value);
        if (ret == null) {
            String select = "SELECT id FROM account WHERE name = '" + value + "'";
            ResultSet rs = null;
            try {
                rs = dse.executeQuery(select);
                if (rs.next()) {
                    ret = rs.getInt("id");
                    cache.put(value, ret);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountValueInferrer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AccountValueInferrer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return ret;
    }
}
