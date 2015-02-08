package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class UnnecessaryARTServiceTypeRemover implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(TransactionVisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        String select = "SELECT `id` FROM `service_type` WHERE `name` = 'ART';";
        ResultSet rs = dse.executeQuery(select);
        int count = 0;
        int last = -1;
        while (rs.next()) {
            last = rs.getInt("id");
            count++;
        }
        if (count > 1) {
            String delete = "DELETE FROM `service_type` WHERE `id` = ?;";
            List<Parameter> params = new ArrayList<>();
            params.add(new Parameter(last, Types.INTEGER));
            dse.executeUpdate(delete, params, false);
        }
    }

}
