package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.business.LookupValueReader;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
import com.intellisoftkenya.onetooner.log.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Migrates patient service data to the correct junction table (patient_service)
 * in FDT.
 *
 * @author gitahi
 */
public class PatientServiceTypeProcessor implements ExtraProcessor {

    private static final Logger LOGGER = LoggerFactory.getLoger(PatientServiceTypeProcessor.class.getName());

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) throws Exception {
        savePatientServiceTypes(mapPatientsToServiceTypes());
    }

    private Map<Integer, List<ServiceType>> mapPatientsToServiceTypes() throws SQLException {
        Map<Integer, List<ServiceType>> patientServiceMap = new HashMap<>();
        String query = "SELECT ArtID, TypeOfService, DateStartedonART "
                + "FROM tblARTPatientMasterInformation ORDER BY ArtID";
        ResultSet rs = sse.executeQuery(query);
        LookupValueReader lvr = new LookupValueReader();
        while (rs.next()) {
            Integer patientId = lvr.readId("patient", "id", "legacy_pk", rs.getString("ArtID"));
            List<ServiceType> serviceTypes = new ArrayList<>();
            if (patientServiceMap.containsKey(patientId)) {
                serviceTypes = patientServiceMap.get(patientId);
            } else {
                patientServiceMap.put(patientId, serviceTypes);
            }
            Integer serviceTypeId = lvr.readId("service_type", "id", "legacy_pk", rs.getString("TypeOfService"));
            if (serviceTypeId == null) {
                throw new NullPointerException("Patient ID " + patientId + " has no TypeOfService indicated in ADT.");
            }
            serviceTypes.add(new ServiceType(serviceTypeId, rs.getDate("DateStartedonART")));
        }
        return patientServiceMap;
    }

    private void savePatientServiceTypes(Map<Integer, List<ServiceType>> patientServiceMap) throws SQLException {
        String insert = "INSERT INTO `patient_service_type`"
                + "(`patient_id`, `service_type_id`, `start_date`) VALUES(?, ?, ?)";

        PreparedStatement pStmt = dse.createPreparedStatement(insert);
        int rowCount = 0;
        int batchNo = 1;

        for (Integer patientId : patientServiceMap.keySet()) {
            List<ServiceType> serviceTypes = patientServiceMap.get(patientId);
            for (ServiceType serviceType : serviceTypes) {
                rowCount++;
                pStmt.setInt(1, patientId);
                pStmt.setInt(2, serviceType.getId());
                pStmt.setObject(3, serviceType.getStartDate());
                pStmt.addBatch();
                dse.logPreparedStatement(pStmt);
                if (rowCount != 0 && rowCount % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(pStmt);
                    LOGGER.log(Level.FINER, "Commited transaction batch #{0}.",
                            new Object[]{batchNo});
                    batchNo++;
                }
            }
        }

        dse.executeBatch(pStmt);
        pStmt.clearBatch();
        LOGGER.log(Level.FINE, "Created {0} patient-to-service type associations.",
                new Object[]{rowCount});
    }

    private class ServiceType {

        private final Integer id;
        private final Date startDate;

        public ServiceType(Integer id, Date startDate) {
            this.id = id;
            this.startDate = startDate;
        }

        public Integer getId() {
            return id;
        }

        public Date getStartDate() {
            return startDate;
        }
    }
}
