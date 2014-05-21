package com.intellisoftkenya.onetooner.api.imp.translator;

import com.intellisoftkenya.onetooner.api.translator.ValueInferrer;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private static final Logger LOGGER = LoggerFactory.getLoger(AccountValueInferrer.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();
    private final SqlExecutor sse = SourceSqlExecutor.getInstance();

    private final Map<String, Integer> idCache = new HashMap<>();
    private final Map<String, String> nameCache = new HashMap<>();

    @Override
    public Integer infer(String value) throws Exception {
        String name = readName(value);
        if (name != null) {
            return new LookupValueReader().readId("account", "legacy_pk", value);
        }
        return null;
    }

    private String readName(String value) throws SQLException {
        String ret = nameCache.get(value);
        if (ret == null) {
            Integer intValue;
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                LOGGER.log(Level.WARNING, "The value " + value
                        + " cannot be inferred since it is not an integer. Returning null.", ex);
                return null;
            }
            String select = "SELECT MIN(SDNo) AS SDNo_, MIN(SourceorDestination) AS SourceorDestination_\n"
                    + "FROM tblARVStockTranSourceorDestination WHERE SourceorDestination IS NOT NULL\n"
                    + "AND SDNo = ? GROUP BY SDNo";
            Map<Object, Integer> params = new LinkedHashMap<>();
            params.put(intValue, Types.INTEGER);
            ResultSet rs = null;
            try {
                rs = sse.executeQuery(select, params);
                if (rs.next()) {
                    ret = rs.getString("SourceorDestination_");
                    nameCache.put(value, ret);
                }
            } finally {
                dse.close(rs);
            }
        }
        return ret;
    }
}
