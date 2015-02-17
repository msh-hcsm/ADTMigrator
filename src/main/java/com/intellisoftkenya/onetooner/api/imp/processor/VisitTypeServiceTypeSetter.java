package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.data.Parameter;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sets the service type for all visit types to ART.
 *
 * @author gitahi
 */
public class VisitTypeServiceTypeSetter implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(VisitUpdater.class.getName());

    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        int artServiceId = readArtServiceId();
        if (artServiceId != -1) {
            List<Parameter> params = new ArrayList<>();
            params.add(new Parameter(artServiceId, Types.INTEGER));
            int affected = dse.executeUpdate("UPDATE `visit_type` SET `service_type_id` = ?;", params, false);
            LOGGER.log(Level.INFO, "Set {0} visit types to ART service type.",
                    new Object[]{affected});
        } else {
            LOGGER.log(Level.WARNING, "NO ART service type. ART service type not set for visit types.");
        }
    }

    private int readArtServiceId() throws SQLException {
        int id = -1;
        ResultSet rs = dse.executeQuery("SELECT `id` FROM `service_type` WHERE `name` = 'ART'");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
}
