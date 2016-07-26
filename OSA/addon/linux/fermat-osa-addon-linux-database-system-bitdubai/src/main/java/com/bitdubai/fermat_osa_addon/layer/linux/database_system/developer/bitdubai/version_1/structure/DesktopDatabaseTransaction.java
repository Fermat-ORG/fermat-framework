package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.DesktopDatabaseBridge;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTransaction</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTransaction implements DatabaseTransaction {

    private List<DatabaseTable> updateTables;
    private List<DatabaseTableRecord> updateRecords;

    private List<DatabaseTable> insertTables;
    private List<DatabaseTableRecord> insertRecords;

    private List<DatabaseTable> deleteTables;
    private List<DatabaseTableRecord> deleteRecords;

    private DesktopDatabaseBridge database;

    public DesktopDatabaseTransaction(final DesktopDatabaseBridge database) {

        this.database = database;
    }

    @Override
    public void execute() throws DatabaseTransactionFailedException {

        Connection connection = null;

        try {

            synchronized (database.getConnectionPool()) {

                connection = database.getConnectionPool().getConnection();

                connection.setAutoCommit(false);

                if (updateTables != null)
                    for (int i = 0; i < updateTables.size(); ++i)
                        updateTransactionRecord(connection, updateTables.get(i), updateRecords.get(i));

                if (insertTables != null)
                    for (int i = 0; i < insertTables.size(); ++i)
                        insertTransactionRecord(connection, insertTables.get(i), insertRecords.get(i));

                if (deleteTables != null)
                    for (int i = 0; i < deleteTables.size(); ++i)
                        deleteTransactionRecord(connection, deleteTables.get(i), deleteRecords.get(i));

                connection.commit();

                connection.setAutoCommit(true);

            }

        } catch (CantInsertRecordException | CantUpdateRecordException | CantDeleteRecordException exception) {

            exception.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new DatabaseTransactionFailedException(
                    exception,
                    this.toString(),
                    "Error executing a statement of the transaction. Transaction being rollback"
            );
        } catch (SQLException exception) {

            exception.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new DatabaseTransactionFailedException(
                    exception,
                    this.toString(),
                    "There was an error in database while trying to execute the transaction. Transaction being rollback"
            );
        } catch (Exception exception) {

            exception.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new DatabaseTransactionFailedException(
                    exception,
                    this.toString(),
                    "There was an unhandled error while trying to execute the transaction. Transaction being rollback"
            );

        } finally {

            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * DatabaseTransaction interface implementation.
     */
    @Override
    public void addRecordToUpdate(DatabaseTable table,
                                  DatabaseTableRecord record) {

        if (updateTables == null)
            updateTables = new ArrayList<>();

        if (updateRecords == null)
            updateRecords = new ArrayList<>();

        updateTables.add(table);
        updateRecords.add(record);
    }

    @Override
    public void addRecordToInsert(DatabaseTable table,
                                  DatabaseTableRecord record) {

        if (insertTables == null)
            insertTables = new ArrayList<>();

        if (insertRecords == null)
            insertRecords = new ArrayList<>();

        insertTables.add(table);
        insertRecords.add(record);
    }

    @Override
    public void addRecordToSelect(DatabaseTable selectTable,
                                  DatabaseTableRecord selectRecord) {

        throw new NotImplementedException();
    }

    @Override
    public void addRecordToDelete(DatabaseTable deleteTable,
                                  DatabaseTableRecord deleteRecord) {

        if (deleteTables == null)
            deleteTables = new ArrayList<>();

        if (deleteRecords == null)
            deleteRecords = new ArrayList<>();

        deleteTables.add(deleteTable);
        deleteRecords.add(deleteRecord);
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToUpdate() {

        return updateRecords;
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToInsert() {

        return insertRecords;
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToSelect() {

        throw new NotImplementedException();
    }

    public List<DatabaseTableRecord> getRecordsToDelete() {

        return this.deleteRecords;
    }

    @Override
    public List<DatabaseTable> getTablesToUpdate() {
        return this.updateTables;
    }

    public List<DatabaseTable> getTablesToDelete() {
        return this.deleteTables;
    }

    @Override
    public List<DatabaseTable> getTablesToInsert() {

        return insertTables;
    }

    @Override
    public List<DatabaseTable> getTablesToSelect() {

        throw new NotImplementedException();
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("INSERT TABLES:");

        if (insertTables == null)
            insertTables = new ArrayList<>();
        for (DatabaseTable table : insertTables)
            builder.append(" ").append(table.toString());
        builder.append("\n");
        builder.append("INSERT RECORDS:");

        if (insertRecords == null)
            insertRecords = new ArrayList<>();
        for (DatabaseTableRecord record : insertRecords)
            builder.append(" ").append(record.toString());
        builder.append("\n");
        builder.append("UPDATE TABLES:");

        if (updateTables == null)
            updateTables = new ArrayList<>();
        for (DatabaseTable table : updateTables)
            builder.append(" ").append(table.toString());
        builder.append("\n");
        builder.append("UPDATE RECORDS:");

        if (updateRecords == null)
            updateRecords = new ArrayList<>();
        for (DatabaseTableRecord record : updateRecords)
            builder.append(" ").append(record.toString());

        builder.append("DELETE TABLES:");
        if (deleteTables == null)
            deleteTables = new ArrayList<>();
        for (DatabaseTable table : deleteTables)
            builder.append(" ").append(table.toString());
        builder.append("\n");

        builder.append("DELETE RECORDS:");
        if (deleteRecords == null)
            deleteRecords = new ArrayList<>();
        for (DatabaseTableRecord record : deleteRecords)
            builder.append(" ").append(record.toString());

        return builder.toString();
    }

    private void updateTransactionRecord(final Connection connection,
                                         final DatabaseTable table,
                                         final DatabaseTableRecord record) throws CantUpdateRecordException {


        List<DatabaseRecord> records = record.getValues();
        StringBuilder strRecords = new StringBuilder();

        List<String> strValues = new ArrayList<>();

        for (DatabaseRecord dbRecord : records) {

            if (strRecords.length() > 0)
                strRecords.append(",");

            strRecords.append(dbRecord.getName())
                    .append(" = ? ");

            strValues.add(dbRecord.getValue());

        }

        String SQL_QUERY = "UPDATE " + table.getTableName() + " SET " + strRecords + " " + table.makeFilter();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {

            for (int i = 0; i < strValues.size(); i++)
                preparedStatement.setString(i + 1, strValues.get(i));

            preparedStatement.execute();

        } catch (SQLException exception) {
            throw new CantUpdateRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "There was an error in database while trying to update a record of the transaction."
            );
        } catch (Exception exception) {
            throw new CantUpdateRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "Unhandled error while trying to update a record in the transaction."
            );
        }
    }

    private void insertTransactionRecord(final Connection connection,
                                         final DatabaseTable table,
                                         final DatabaseTableRecord record) throws CantInsertRecordException {

        List<String> strRecords = new ArrayList<>();
        List<String> strValues = new ArrayList<>();
        List<String> strSigns = new ArrayList<>();

        List<DatabaseRecord> records = record.getValues();

        for (DatabaseRecord databaseRecord : records) {
            strRecords.add(databaseRecord.getName());
            strValues.add(databaseRecord.getValue());
            strSigns.add("?");
        }

        String SQL_QUERY = new StringBuilder().append("INSERT INTO ").append(table.getTableName()).append("(").append(StringUtils.join(strRecords, ",")).append(")").append(" VALUES (").append(StringUtils.join(strSigns, ",")).append(")").toString();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {

            for (int i = 0; i < strSigns.size(); i++)
                preparedStatement.setString(i + 1, strValues.get(i));

            preparedStatement.execute();

        } catch (SQLException exception) {
            throw new CantInsertRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "There was an error in database while trying to insert a record of the transaction."
            );
        } catch (Exception exception) {
            throw new CantInsertRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "Unhandled error while trying to insert a record in the transaction."
            );
        }
    }

    private void deleteTransactionRecord(final Connection connection,
                                         final DatabaseTable table,
                                         final DatabaseTableRecord record) throws CantDeleteRecordException {

        String SQL_QUERY = "DELETE FROM " + table.getTableName() + table.makeFilter();

        try (Statement statement = connection.createStatement()) {

            statement.execute(SQL_QUERY);

        } catch (SQLException exception) {
            throw new CantDeleteRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "There was an error in database while trying to delete a record of the transaction."
            );
        } catch (Exception exception) {
            throw new CantDeleteRecordException(
                    exception,
                    "table: " + table + " - record: " + record,
                    "Unhandled error while trying to delete a record in the transaction."
            );
        }
    }

}
