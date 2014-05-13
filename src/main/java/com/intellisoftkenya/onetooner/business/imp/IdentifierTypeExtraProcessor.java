package com.intellisoftkenya.onetooner.business.imp;

import com.intellisoftkenya.onetooner.business.AuditValues;
import com.intellisoftkenya.onetooner.business.Constants;
import com.intellisoftkenya.onetooner.business.api.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An {@link ExtraProcessor} for creating initial identifier types if they
 * do not exist.
 *
 * @author gitahi
 */
public class IdentifierTypeExtraProcessor implements ExtraProcessor {

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) {
        try {
            AuditValues auditValues = new AuditValues();

            boolean addArtId = true;
            boolean addOpdIpdId = true;
            ResultSet rs = dse.executeQuery("SELECT id FROM identifier_type WHERE id IN ("
                    + Constants.ART_IDENTIFIER_TYPE_ID + ", "
                    + Constants.OPIP_IDENTIFIER_TYPE_ID + ")");
            while (rs.next()) {
                if (rs.getInt("id") == Constants.ART_IDENTIFIER_TYPE_ID) {
                    addArtId = false;
                }
                if (rs.getInt("id") == Constants.OPIP_IDENTIFIER_TYPE_ID) {
                    addOpdIpdId = false;
                }
            }
            if (addArtId) {
                dse.executeUpdate("INSERT INTO identifier_type"
                        + "(id, `name`, `uuid`, created_by, created_on) "
                        + "VALUES(" + Constants.ART_IDENTIFIER_TYPE_ID + ", 'ART ID', '"
                        + auditValues.uuid() + "', " + auditValues.createdBy() + ", '"
                        + auditValues.createdOn() + "');",
                        false);
            }
            if (addOpdIpdId) {
                dse.executeUpdate("INSERT INTO identifier_type"
                        + "(id, `name`, `uuid`, created_by, created_on) "
                        + "VALUES(" + Constants.OPIP_IDENTIFIER_TYPE_ID + ", 'OPDIPD ID', '"
                        + auditValues.uuid() + "', " + auditValues.createdBy() + ", '"
                        + auditValues.createdOn() + "');",
                        false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IdentifierTypeExtraProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
