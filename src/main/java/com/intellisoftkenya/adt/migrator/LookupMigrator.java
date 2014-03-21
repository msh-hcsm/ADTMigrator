package com.intellisoftkenya.adt.migrator;

import com.intellisoftkenya.adt.migrator.domain.fdt.Dosage;
import com.intellisoftkenya.adt.migrator.domain.fdt.Nameable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gitahi
 */
public class LookupMigrator {

    private final AdtSqlExecutor ase = AdtSqlExecutor.getInstance();
    private final Connection connection = FdtSqlExcecutor.connection();

    /**
     * Migrate all lookup objects.
     */
    public void migrateLookups() {
        migrateDosage();
        migrateNameable(new NameableMeta("tblClientSupportDetails", "ClientSupportDesciption", "supporting_organization", "name"));
        migrateNameable(new NameableMeta("tblGenericName", "GeneriCName", "generic_name", "name"));
        migrateNameable(new NameableMeta("tblRegimenCategory", "CategoryName", "regimen_type", "name"));
        migrateNameable(new NameableMeta("tblSourceOfClient", "SourceOfClient", "patient_source", "name"));
        migrateNameable(new NameableMeta("tblTypeOfService", "TypeOfService", "service_type", "name"));
        migrateNameable(new NameableMeta("tblUnit", "Unit", "dispensing_unit", "name"));
        migrateNameable(new NameableMeta("tblUnit", "Unit", "dispensing_unit", "name"));
        migrateNameable(new NameableMeta("tblVisitTransaction", "VisitTranName", "visit_type", "name"));
        //close both connections once done?
    }

    private void migrateDosage() {
        ResultSet rs = ase.executeQuery("SELECT dose, value, frequency FROM tblDose");
        if (rs != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement pStmt = connection.prepareCall("INSERT INTO "
                        + "dosage(uuid, name, value, frequency, created_by, created_on) "
                        + "VALUES(?, ?, ?, ?, ?, ?)");
                while (rs.next()) {
                    Dosage dosage = new Dosage();
                    dosage.setName(rs.getString("dose"));
                    dosage.setValue(rs.getBigDecimal("value"));
                    dosage.setFrequency(rs.getInt("frequency"));
                    Util.fleshFdtDomainObject(dosage);

                    pStmt.setString(1, dosage.getUuid());
                    pStmt.setString(2, dosage.getName());
                    pStmt.setBigDecimal(3, dosage.getValue());
                    pStmt.setInt(4, dosage.getFrequency());
                    pStmt.setInt(5, dosage.getCreatedBy().getId());
                    pStmt.setDate(6, new java.sql.Date(dosage.getCreatedOn().getTime()));
                    pStmt.executeUpdate();
                }
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ase.close(rs);
            }
        }
    }

    private void migrateNameable(NameableMeta nm) {
        String query = "SELECT " + nm.getAdtColumnName() + " FROM " + nm.getAdtTableName();
        ResultSet rs = ase.executeQuery(query);
        if (rs != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement pStmt = connection.prepareCall("INSERT INTO "
                        + nm.getFdtTableName() + "(uuid, " + nm.getFdtColumnName() + ", created_by, created_on) "
                        + "VALUES(?, ?, ?, ?)");
                while (rs.next()) {
                    Nameable nameable = new Nameable();
                    nameable.setName(rs.getString(nm.getAdtColumnName()));
                    Util.fleshFdtDomainObject(nameable);

                    if (nameable.getName() != null) {
                        pStmt.setString(1, nameable.getUuid());
                        pStmt.setString(2, nameable.getName());
                        pStmt.setInt(3, nameable.getCreatedBy().getId());
                        pStmt.setDate(4, new java.sql.Date(nameable.getCreatedOn().getTime()));
                        pStmt.executeUpdate();
                    }
                }
                connection.commit();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ase.close(rs);
            }
        }
    }
}
