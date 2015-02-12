package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Marks as voided = false in FDT all the drugs that are marked as InUse = Yes
 * in ADT.
 *
 * @author gitahi
 */
public class DrugsInUseProcessor implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(VisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        String update = "UPDATE drug SET voided = !voided WHERE standard = 0";
        int affected = dse.executeUpdate(update, false);
        LOGGER.log(Level.FINE, "Un-voided {0} non-standard drug(s) that are flagged as InUse in ADT.",
                new Object[]{affected});
    }
}
