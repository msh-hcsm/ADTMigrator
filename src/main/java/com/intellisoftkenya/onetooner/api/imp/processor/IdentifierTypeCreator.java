package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.business.AuditValues;
import com.intellisoftkenya.onetooner.business.Constants;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An {@link ExtraProcessor} for creating initial identifier types if they do
 * not exist.
 *
 * @author gitahi
 */
public class IdentifierTypeCreator implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(IdentifierTypeCreator.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    private final AuditValues auditValues = new AuditValues();

    /**
     * Checks if the requisite identifier types exist and creates them if they
     * don't.
     */
    @Override
    public void process(OneToOne oto) throws Exception {

        boolean addArtId = true;
        boolean addOpdIpdId = true;

        String sql = "SELECT id FROM identifier_type WHERE id IN (?, ?)";

        List<Parameter> params = new ArrayList<>();
        params.add(new Parameter(Constants.ART_IDENTIFIER_TYPE_ID, Types.INTEGER));
        params.add(new Parameter(Constants.OPIP_IDENTIFIER_TYPE_ID, Types.INTEGER));

        ResultSet rs = dse.executeQuery(sql, params);
        while (rs.next()) {
            if (rs.getInt("id") == Constants.ART_IDENTIFIER_TYPE_ID) {
                addArtId = false;
            }
            if (rs.getInt("id") == Constants.OPIP_IDENTIFIER_TYPE_ID) {
                addOpdIpdId = false;
            }
        }
        String update = "INSERT INTO `identifier_type`"
                + "(`id`, `name`, `uuid`, `created_by`, `created_on`) "
                + "VALUES(?, ?, ?, ?, ?)";
        if (addArtId) {
            params = new ArrayList<>();
            params.add(new Parameter(Constants.ART_IDENTIFIER_TYPE_ID, Types.INTEGER));
            params.add(new Parameter("ART ID", Types.VARCHAR));
            params.add(new Parameter(auditValues.uuid(), Types.VARCHAR));
            params.add(new Parameter(auditValues.createdBy(), Types.INTEGER));
            params.add(new Parameter(auditValues.createdOn(), Types.DATE));
            dse.executeUpdate(update, params, false);

            LOGGER.log(Level.FINE, "Created ART ID identifier type.");
        }
        if (addOpdIpdId) {
            params = new ArrayList<>();
            params.add(new Parameter(Constants.OPIP_IDENTIFIER_TYPE_ID, Types.INTEGER));
            params.add(new Parameter("OPDIPD ID", Types.VARCHAR));
            params.add(new Parameter(auditValues.uuid(), Types.VARCHAR));
            params.add(new Parameter(auditValues.createdBy(), Types.INTEGER));
            params.add(new Parameter(auditValues.createdOn(), Types.DATE));
            dse.executeUpdate(update, params, false);

            LOGGER.log(Level.FINE, "Created OPDIPD ID identifier type.");
        }
    }
}
