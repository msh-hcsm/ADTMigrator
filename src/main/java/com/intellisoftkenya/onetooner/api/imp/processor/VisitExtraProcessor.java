package com.intellisoftkenya.onetooner.api.imp.processor;

import com.intellisoftkenya.onetooner.Main;
import com.intellisoftkenya.onetooner.api.processor.ExtraProcessor;
import com.intellisoftkenya.onetooner.dao.DestinationSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SourceSqlExecutor;
import com.intellisoftkenya.onetooner.dao.SqlExecutor;
import com.intellisoftkenya.onetooner.data.OneToOne;
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
 * An {@link ExtraProcessor} for ADT visits.
 *
 * @author gitahi
 */
public class VisitExtraProcessor implements ExtraProcessor {

    private final SqlExecutor sse = SourceSqlExecutor.getInstance();
    private final SqlExecutor dse = DestinationSqlExecutor.getInstance();

    @Override
    public void process(OneToOne oto) {
        try {
            Map<String, Map<String, Object>> patientMap = loadPatients();

            String update = "UPDATE "
                    + "visit "
                    + "SET "
                    + "pregnant = ?, "
                    + "other_drugs = ?, "
                    + "tb_confirmed = ?, "
                    + "patient_status_id = ?, "
                    + "next_appointment_date = ? "
                    + "WHERE "
                    + "legacy_pk = ?";
            PreparedStatement pStmt = dse.createPreparedStatement(update);

            int counter = 0;
            for (Map<String, Object> patient : patientMap.values()) {
                counter++;
                List<Map<String, Object>> visits = (List<Map<String, Object>>) patient.get("visits");
                if (visits != null) {
                    Map<String, Object> lastVisit = visits.get(visits.size() - 1);
                    if (lastVisit != null) {
                        pStmt.setBoolean(1, (Boolean) patient.get("Pregnant"));
                        pStmt.setString(2, (String) patient.get("OtherDrugs"));
                        pStmt.setBoolean(3, (Boolean) patient.get("TB"));
                        pStmt.setInt(4, 232);
                        Date date = (Date) patient.get("DateOfNextAppointment");
                        pStmt.setDate(5, date == null ? null : new java.sql.Date(date.getTime()));
                        pStmt.setInt(6, (Integer) lastVisit.get("PatientTranNo"));
                        pStmt.addBatch();
                    }
                }
                if (counter % SqlExecutor.TRANSACTION_BATCH_SIZE == 0) {
                    dse.executeBatch(pStmt);
                    Logger.getLogger(Main.class.getName()).log(Level.INFO, "Commited transaction batch #{0}.",
                            new Object[]{counter});
                }
            }
            dse.executeBatch(pStmt);

            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Post processed {0} row(s)",
                    new Object[]{counter});

        } catch (SQLException ex) {
            Logger.getLogger(VisitExtraProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Map<String, Object>> loadPatients() throws SQLException {
        Map<String, Map<String, Object>> patientMap = new HashMap<>();
        ResultSet rs = sse.executeQuery("SELECT "
                + "ArtID, "
                + "Pregnant, "
                + "OtherDrugs, "
                + "TB, "
                + "CurrentStatus, "
                + "DateOfNextAppointment, "
                + "StartHeight, "
                + "CurrentHeight, "
                + "startBSA, "
                + "currentBSA "
                + "FROM "
                + "tblARTPatientMasterInformation");
        while (rs.next()) {
            Map<String, Object> patient = new HashMap<>();
            patient.put("ArtID", rs.getString("ArtID"));
            patient.put("Pregnant", rs.getBoolean("Pregnant"));
            patient.put("OtherDrugs", rs.getString("OtherDrugs"));
            patient.put("TB", rs.getBoolean("TB"));
            patient.put("CurrentStatus", rs.getInt("CurrentStatus"));
            patient.put("DateOfNextAppointment", rs.getDate("DateOfNextAppointment"));
            patient.put("StartHeight", rs.getBigDecimal("StartHeight"));
            patient.put("CurrentHeight", rs.getBigDecimal("CurrentHeight"));
            patient.put("startBSA", rs.getBigDecimal("startBSA"));
            patient.put("currentBSA", rs.getBigDecimal("currentBSA"));
            patientMap.put((String) patient.get("ArtID"), patient);
        }
        loadVisits(patientMap);
        return patientMap;
    }

    private void loadVisits(Map<String, Map<String, Object>> patientMap) throws SQLException {
        ResultSet rs = sse.executeQuery("SELECT ARTID, PatientTranNo, DateofVisit FROM tblARTPatientTransactions ORDER BY DateofVisit ASC");
        while (rs.next()) {
            Map<String, Object> visit = new HashMap<>();
            visit.put("PatientTranNo", rs.getInt("PatientTranNo"));
            visit.put("ARTID", rs.getString("ARTID"));
            visit.put("DateofVisit", rs.getDate("DateofVisit"));
            Map<String, Object> patient = patientMap.get((String) visit.get("ARTID"));
            addVisitToPatient(patient, visit);
        }
    }

    private void addVisitToPatient(Map<String, Object> patient, Map<String, Object> visit) {
        List<Map<String, Object>> visitList = (List<Map<String, Object>>) patient.get("visits");
        if (visitList == null) {
            visitList = new ArrayList<>();
        }
        visitList.add(visit);
        patient.put("visits", visitList);
    }
}
