package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseAggregateFunction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantSelectRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class define methods to execute query and transactions on database
 * And method to get a database table definition
 * <p/>
 * <p/>
 * Created by ciencias on 23.12.14.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 27/08/2015.
 */

public class AndroidDatabase implements Database, DatabaseFactory, Serializable {

    /**
     * database Interface member variables.
     */
    private String path;
    private String databaseName;
    private UUID ownerId;


    public AndroidDatabase() {
    }

    /**
     * <p>Plugin implementation constructor
     *
     * @param path         android path
     * @param ownerId      PlugIn owner id
     * @param databaseName name database using
     */
    public AndroidDatabase(final String path,
                           final UUID ownerId,
                           final String databaseName) {

        this.path = path;
        this.ownerId = ownerId;
        this.databaseName = databaseName;
    }

    /**
     * <p>Platform implementation constructor
     *
     * @param path         Android path
     * @param databaseName name database using
     */
    public AndroidDatabase(final String path,
                           final String databaseName) {

        this.path = path;
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * database interface implementation.
     */
    @Override
    public DatabaseFactory getDatabaseFactory() {
        return this;
    }

    /**
     * <p> This method execute a string query command in database
     */
    @Override
    public void executeQuery(String query) throws CantExecuteQueryException {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            database.execSQL(query);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            throw new CantExecuteQueryException();
        } finally {
            if (database != null)
                database.close();
        }
    }

    /**
     * <p>Return a new DatabaseTransaction definition
     *
     * @return DatabaseTransaction object
     */
    @Override
    public DatabaseTransaction newTransaction() {
        return new AndroidDatabaseTransaction(this);
    }

    /**
     * <p>Return a DatabaseTable definition
     *
     * @param tableName name database table using
     * @return DatabaseTable Object
     */
    @Override
    public DatabaseTable getTable(String tableName) {
        return new AndroidDatabaseTable(this, tableName);
    }

    /**
     * <p>Execute a transaction in database
     *
     * @param transaction DatabaseTransaction object to contain definition of operations to update and insert
     * @throws DatabaseTransactionFailedException
     */
    @Override
    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException {

        if (transaction == null) {
            String context = "Transaction: null";
            String possibleReason = "The passed transaction can't be null";
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        List<AndroidVariable> variablesResult = new ArrayList<>();

        List<DatabaseTable> selectTables = transaction.getTablesToSelect();
        List<DatabaseTable> insertTables = transaction.getTablesToInsert();
        List<DatabaseTable> updateTables = transaction.getTablesToUpdate();
        List<DatabaseTableRecord> updateRecords = transaction.getRecordsToUpdate();
        List<DatabaseTableRecord> selectRecords = transaction.getRecordsToSelect();
        List<DatabaseTableRecord> insertRecords = transaction.getRecordsToInsert();
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();

            database.beginTransaction();

            if (selectTables != null)
                for (int i = 0; i < selectTables.size(); ++i)
                    variablesResult = selectTransactionRecord(database, selectTables.get(i), selectRecords.get(i));

            if (updateTables != null)
                for (int i = 0; i < updateTables.size(); ++i)
                    updateTransactionRecord(database, updateTables.get(i), updateRecords.get(i), variablesResult);

            if (insertTables != null)
                for (int i = 0; i < insertTables.size(); ++i)
                    insertTransactionRecord(database, insertTables.get(i), insertRecords.get(i), variablesResult);

            database.setTransactionSuccessful();

        } catch (Exception exception) {
            /**
             * for error not complete transaction
             */
            String context = new StringBuilder().append("Database Name: ").append(databaseName).toString();
            context += DatabaseTransactionFailedException.CONTEXT_CONTENT_SEPARATOR;
            context += transaction.toString();
            String possibleReason = "The most reasonable thing to do here is check the cause as this is a triggered exception that can come from many situations";
            throw new DatabaseTransactionFailedException(DatabaseTransactionFailedException.DEFAULT_MESSAGE, exception, context, possibleReason);

        } finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
    }

    public List<AndroidVariable> selectTransactionRecord(final SQLiteDatabase database,
                                                         final DatabaseTable table,
                                                         final DatabaseTableRecord record) throws CantSelectRecordException {

        List<AndroidVariable> variablesResult = new ArrayList<>();
        Cursor c = null;
        try {

            StringBuilder strRecords = new StringBuilder("");

            StringBuilder query = new StringBuilder("");

            List<DatabaseRecord> records = record.getValues();

            List<DatabaseAggregateFunction> tableSelectOperator = table.getTableAggregateFunction();

            //check if declared operators to apply on select or only define some fields

            if (tableSelectOperator != null) {

                for (int i = 0; i < tableSelectOperator.size(); ++i) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");

                    switch (tableSelectOperator.get(i).getType()) {
                        case SUM:
                            strRecords.append(" SUM (")
                                    .append(tableSelectOperator.get(i).getColumn())
                                    .append(") AS ")
                                    .append(tableSelectOperator.get(i).getAliasColumn());
                            break;
                        case COUNT:
                            strRecords.append(" COUNT (")
                                    .append(tableSelectOperator.get(i).getColumn())
                                    .append(") AS ")
                                    .append(tableSelectOperator.get(i).getAliasColumn());
                            break;
                        default:
                            strRecords.append(" ");
                            break;
                    }
                }
            } else {
                for (int i = 0; i < records.size(); ++i) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");
                    strRecords.append(records.get(i).getName());

                }
            }

            query.append("SELECT ")
                    .append(strRecords)
                    .append(" FROM ")
                    .append(table.getTableName())
                    .append(table.makeFilter());

            c = database.rawQuery(query.toString(), null);
            int columnsCant = 0;

            if (c.moveToFirst()) {
                do {
                    /**
                     * Get columns name to read values of files
                     *
                     */
                    AndroidVariable variable = new AndroidVariable(
                            new StringBuilder().append("@").append(c.getColumnName(columnsCant)).toString(),
                            c.getString(columnsCant)
                    );

                    variablesResult.add(variable);
                    columnsCant++;
                } while (c.moveToNext());
            }
            c.close();

            return variablesResult;
        } catch (Exception exception) {
            if (c != null)
                c.close();
            throw new CantSelectRecordException(CantSelectRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        }
    }


    public SQLiteDatabase getWritableDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {
        String databasePath = getDatabasePath();

        if (!(new File(databasePath)).exists()) {
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        try {
            return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE, null);
        } catch (SQLiteException exception) {
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, exception, context, possibleReason);
        } catch (Exception e) {
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause for this error as we have already checked that the database exists");
        }
    }

    public SQLiteDatabase getReadableDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {
        String databasePath = getDatabasePath();

        if (!(new File(databasePath)).exists()) {
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        try {
            return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY, null);
        } catch (SQLiteException exception) {
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, exception, context, possibleReason);
        } catch (Exception e) {
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause for this error as we have already checked that the database exists");
        }
    }

    /**
     * Private methods implementation.
     */

    /**
     * Open specific database file
     * if used by a plugin, method use plugin id to define directory path name
     *
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    @Override
    public void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {

        String databasePath = getDatabasePath();

        if (!(new File(databasePath)).exists()) {
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        try {
            SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE, null).close();
        } catch (SQLiteException exception) {

            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(exception);
            String context = new StringBuilder().append("database Constructed Path: ").append(databasePath).toString();
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        } catch (Exception e) {
            throw new CantOpenDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "Check the cause for this error as we have already checked that the database exists");
        }

    }

    @Override
    public void closeDatabase() {

    }

    /**
     * te a specific database file
     * if used by a plugin, It use plugin id to define directory path name
     *
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */
    public void deleteDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {

        String databasePath = getDatabasePath();

        File databaseFile = new File(databasePath);

        if (SQLiteDatabase.deleteDatabase(databaseFile))
            return;

        /**
         * unexpected error deleting the database
         * * *
         */
        String message = "SOMETHING UNEXPECTED HAS HAPPENED";
        String context = new StringBuilder().append("Constructed database Path: ").append(databasePath).toString();
        String possibleCause = "The most probable reason is that the database path could not be found";
        throw new DatabaseNotFoundException(message, null, context, possibleCause);
    }


    /**
     * DatabaseFactory interface implementation.
     */


    /**
     * This method creates a new database file
     *
     * @param databaseName name database to create
     * @throws CantCreateDatabaseException
     */
    @Override
    public void createDatabase(String databaseName) throws CantCreateDatabaseException {

        File storagePath = new File(getPath());

        if (!storagePath.mkdirs())
            if (!storagePath.exists())
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, null, "The directory couldn't be created.", "");

        /**
         * Hash data base name
         */
        File databaseFile = new File(new StringBuilder().append(storagePath.getPath()).append("/").append(databaseName.replace("-", "")).append(".db").toString());

        if (databaseFile.exists()) {
            String context = new StringBuilder().append("database File: ").append(databaseFile.getPath()).toString();
            String possibleReasons = "This happens if the database has already been created";
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, null, context, possibleReasons);
        }


        /**
         * This call opens or creates the database, it doesn't throw a determined exception, but we'll try to emulate one in the tests
         */
        try {
            SQLiteDatabase.openOrCreateDatabase(databaseFile, null).close();
        } catch (SQLiteException ex) {
            String context = new StringBuilder().append("Storage Path: ").append(storagePath.getPath()).toString();
            context += CantCreateDatabaseException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("database Name: ").append(databaseName).toString();
            String possibleReasons = "This can happen if the database File where we wanted to create the database can't be created";
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, ex, context, possibleReasons);
        } catch (Exception e) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "Check the cause for this error as we have already checked that the database exists");
        }
    }

    /**
     * build path of databases
     * if owner id if null
     * because it comes from platformDatabase
     *
     * @return string path of databases
     */
    private String getPath() {
        if (ownerId != null)
            return new StringBuilder().append(path).append("/databases/").append(ownerId.toString()).toString();
        else
            return new StringBuilder().append(path).append("/databases/").toString();
    }

    /**
     * build full path of the android database
     * if owner id if null
     * because it comes from platformDatabase
     *
     * @return string full path of database
     */
    private String getDatabasePath() {
        return new StringBuilder().append(getPath()).append("/").append(databaseName.replace("-", "")).append(".db").toString();
    }

    /**
     * This method creates a new table into the database based on the definition received.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
     * @param table   DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(UUID ownerId, DatabaseTableFactory table) throws InvalidOwnerIdException, CantCreateTableException {
        /**
         * I check that the owner id is the same I currently have..
         */
        StringBuilder query = new StringBuilder("");

        if (this.ownerId != ownerId) {
            String context = new StringBuilder().append("database Owner Id: ").append(ownerId).toString();
            context += InvalidOwnerIdException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("Owner Id in the method invocation: ").append(ownerId).toString();
            String possibleReason = "The owner Id passed in the Invocation doesn't belong to the Android database Owner, maybe this was a passed object";
            throw new InvalidOwnerIdException(InvalidOwnerIdException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        if (table == null) {
            String context = new StringBuilder().append("Owner Id : ").append(ownerId.toString()).toString();
            String possibleReason = "DatabaseTableFactory can't be null.";
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        /**
         * Get the columns of the table and write the query to create it
         */
        try {
            List<String> primaryKey = new ArrayList<>();

            query.append("CREATE TABLE IF NOT EXISTS ");
            query.append(table.getTableName());
            query.append("(");

            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                query.append(tableColumns.get(i).getName());
                query.append(" ");
                query.append(tableColumns.get(i).getDataType().name());

                if (tableColumns.get(i).getDataType() == DatabaseDataType.STRING) {
                    query.append("(");
                    query.append(String.valueOf(tableColumns.get(i).getDataTypeSize()));
                    query.append(")");
                }


                if (tableColumns.get(i).isPrimaryKey())
                    primaryKey.add(tableColumns.get(i).getName());

                if (i < tableColumns.size() - 1)
                    query.append(",");
            }

            /**
             * add primary key
             */
            if (!primaryKey.isEmpty()) {
                query.append(", PRIMARY KEY (");
                query.append(StringUtils.join(primaryKey, ","));
                query.append(") ");
            }


            query.append(")");

            executeQuery(query.toString());

            /**
             * get index column
             */
            query = new StringBuilder("");
            List<List<String>> indexes = table.listIndexes();
            for (List<String> indexColumns : indexes) {

                query.append(" CREATE INDEX IF NOT EXISTS ");
                query.append(table.getTableName());
                query.append("_");
                query.append(StringUtils.join(indexColumns, "_"));
                query.append("_idx ON ");
                query.append(table.getTableName());
                query.append(" (");
                query.append(StringUtils.join(indexColumns, ","));
                query.append(")");

                executeQuery(query.toString());
            }
        } catch (Exception ex) {
            String context = new StringBuilder().append("Owner Id : ").append(ownerId.toString()).toString();
            context += CantCreateTableException.CONTEXT_CONTENT_SEPARATOR;
            context += new StringBuilder().append("DatabaseTableFactory Info: ").append(table.toString()).toString();
            String possibleReason = "Check the cause for the reason we are getting this error.";
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, ex, context, possibleReason);
        }
    }

    /**
     * This method creates a new table into the database based on the definition received.
     * Create  primary keys and index if defined
     *
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(DatabaseTableFactory table) throws CantCreateTableException {
        try {
            createTable(ownerId, table);
        } catch (InvalidOwnerIdException ex) {
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, ex, new StringBuilder().append("database Owner Id: ").append(ownerId).toString(), "This error is strange and shouldn't ever happen");
        } catch (Exception e) {
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "We couldn't open the Database, you should checkout the cause");
        }
    }


    /**
     * This method provides the caller with a Table Structure object.
     * verify plugin owner id
     *
     * @param ownerId   Plugin owner id
     * @param tableName table name to use
     * @return DatabaseTableFactory Object
     * @throws InvalidOwnerIdException
     */
    @Override
    public DatabaseTableFactory newTableFactory(UUID ownerId, String tableName) throws InvalidOwnerIdException {

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerIdException();
        }

        return new AndroidDatabaseTableFactory(tableName);
    }

    /**
     * This method provides the caller with a Table Structure object.
     *
     * @param tableName table name to use
     * @return DatabaseTableFactory object
     */
    @Override
    public DatabaseTableFactory newTableFactory(String tableName) {
        return new AndroidDatabaseTableFactory(tableName);
    }

    private void updateTransactionRecord(SQLiteDatabase database, DatabaseTable table, DatabaseTableRecord record, List<AndroidVariable> variablesResult) throws CantUpdateRecordException {

        try {
            List<DatabaseRecord> records = record.getValues();
            StringBuilder strRecords = new StringBuilder();
            StringBuilder query = new StringBuilder("");

            for (DatabaseRecord dbRecord : records) {

                if (dbRecord.isChange()) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");

                    if (dbRecord.isUseOfVariable()) {
                        for (int j = 0; j < variablesResult.size(); ++j) {

                            if (variablesResult.get(j).getName().equals(dbRecord.getValue())) {
                                strRecords.append(dbRecord.getName())
                                        .append(" = '")
                                        .append(variablesResult.get(j).getValue())
                                        .append("'");
                            }
                        }
                    } else {
                        strRecords.append(dbRecord.getName())
                                .append(" = '")
                                .append(dbRecord.getValue())
                                .append("'");
                    }
                }
            }

            query.append("UPDATE ")
                    .append(table.getTableName())
                    .append(" SET ")
                    .append(strRecords)
                    .append(" ")
                    .append(table.makeFilter());

            database.execSQL(query.toString());

        } catch (Exception exception) {
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        }
    }

    private void insertTransactionRecord(SQLiteDatabase database, DatabaseTable table, DatabaseTableRecord record, List<AndroidVariable> variableResultList) throws CantInsertRecordException {

        try {
            StringBuilder strRecords = new StringBuilder("");
            StringBuilder strValues = new StringBuilder("");
            StringBuilder query = new StringBuilder("");
            List<DatabaseRecord> records = record.getValues();


            for (int i = 0; i < records.size(); ++i) {

                if (strRecords.length() > 0)
                    strRecords.append(",");
                strRecords.append(records.get(i).getName());

                if (strValues.length() > 0)
                    strValues.append(",");

                if (records.get(i).isUseOfVariable()) {
                    for (AndroidVariable variableResult : variableResultList) {

                        if (variableResult.getName().equals(records.get(i).getValue())) {
                            strValues.append("'")
                                    .append(variableResult.getValue())
                                    .append("'");
                        }
                    }
                } else {
                    strValues.append("'")
                            .append(records.get(i).getValue())
                            .append("'");
                }
            }

            query.append("INSERT INTO ")
                    .append(table.getTableName())
                    .append("( ")
                    .append(strRecords)
                    .append(") VALUES (")
                    .append(strValues)
                    .append(")");

            database.execSQL(query.toString());

        } catch (Exception exception) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        }
    }
}