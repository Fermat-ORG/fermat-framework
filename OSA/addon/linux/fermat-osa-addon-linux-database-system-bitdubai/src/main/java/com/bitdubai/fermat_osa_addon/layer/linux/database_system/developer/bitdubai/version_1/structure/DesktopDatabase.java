package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.DesktopDatabaseBridge;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.EnvironmentVariables;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabase</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabase implements Database, DatabaseFactory {

    /**
     * Database Interface member variables.
     */
    private String databaseName;
    private UUID ownerId;
    private String query;
    private DesktopDatabaseBridge database;

    // Public constructor declarations.
    public DesktopDatabase(UUID ownerId, String databaseName) {
        this.ownerId = ownerId;
        this.databaseName = databaseName;
    }

    /**
     * <p>Platform implementation constructor
     *
     * @param databaseName name database using
     */
    public DesktopDatabase(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Database interface implementation.
     */

    /**
     * <p> This method execute a string query command in database
     */
    @Override
    public void executeQuery(String query) throws CantExecuteQueryException {

        try {
            database.execSQL(query);
        } catch (SQLException exception) {
            throw new CantExecuteQueryException(exception);
        }
    }


    /**
     * <p>Return a new DatabaseTransaction definition
     *
     * @return DatabaseTransaction object
     */
    @Override
    public DatabaseTransaction newTransaction() {

        return new DesktopDatabaseTransaction(database);
    }

    /**
     * <p>Return a DatabaseTable definition
     *
     * @param tableName name database table using
     * @return DatabaseTable Object
     */
    @Override
    public DatabaseTable getTable(String tableName) {

        return new DesktopDatabaseTable(this.database, tableName);
    }

    /**
     * <p>Execute a transaction in database
     *
     * @param transaction DatabaseTransaction object to contain definition of operations to update and insert
     * @throws DatabaseTransactionFailedException
     */
    @Deprecated
    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException {

        throw new RuntimeException("Deprecated method. Use DatabaseTransaction.execute() method instead.");
    }

    @Override
    public DatabaseFactory getDatabaseFactory() {
        return this;
    }

    @Override
    public void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException {

        String databasePath = buildDatabasePath();

        if (!(new File(databasePath)).exists()) {
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        try {
            database = DesktopDatabaseBridge.openDatabase(databasePath, null, 0, null);
        } catch (Exception exception) {

            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(exception);
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }

    }

    @Override
    public void closeDatabase() {

    }


    public void openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

        String databasePath = buildDatabasePath(databaseName);

        if (!(new File(databasePath)).exists()) {
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, context, possibleReason);
        }

        try {
            database = DesktopDatabaseBridge.openDatabase(databasePath, null, 0, null);
        } catch (Exception exception) {

            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(exception);
            String context = "database Constructed Path: " + databasePath;
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }
    }

    /**
     * Delete a specific database file
     * if used by a plugin, It use plugin id to define directory path name
     *
     * @param databaseName name of database to deleted
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */


    public void deleteDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {


        try {
            // determine directry path name
            String databasePath = buildDatabasePath(databaseName);

            File databaseFile = new File(databasePath);

            DesktopDatabaseBridge.deleteDatabase(databaseFile);

        } catch (Exception exception) {

            /**
             * unexpected error deleting the database
             * * *
             */

            throw new DatabaseNotFoundException();
        }

    }

    /**
     * DatabaseFactory interface implementation.
     */
    @Override
    public void createDatabase(String databaseName) throws CantCreateDatabaseException {

        /**
         * First I try to open the database.
         */
        try {

            String databasePath = buildStoragePath();

            File storagePath = new File(databasePath);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }


            File databaseFile = new File(buildDatabasePath(databaseName));
            this.database = DesktopDatabaseBridge.openOrCreateDatabase(databaseFile, null);


        } catch (Exception exception) {


            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            throw new CantCreateDatabaseException();
        }

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
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerIdException();
        }

        /**
         * Get the columns of the table and write the query to create it
         */
        try {
            List<String> primaryKey = new ArrayList<>();
            this.query = "CREATE TABLE IF NOT EXISTS " + table.getTableName() + "(";
            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                this.query += tableColumns.get(i).getName() + " " + tableColumns.get(i).getDataType().name();
                if (tableColumns.get(i).getDataType() == DatabaseDataType.STRING)
                    this.query += "(" + String.valueOf(tableColumns.get(i).getDataTypeSize()) + ")";

                if (tableColumns.get(i).isPrimaryKey())
                    primaryKey.add(tableColumns.get(i).getName());

                if (i < tableColumns.size() - 1)
                    this.query += ",";
            }
            /**
             * add primary key
             */
            if (!primaryKey.isEmpty())
                this.query += ", PRIMARY KEY (" + StringUtils.join(primaryKey, ",") + ") ";

            this.query += ")";

            System.out.println("Create table Query: " + query);

            executeQuery(this.query);

            /**
             * get index column
             */

            List<List<String>> indexes = table.listIndexes();
            for (List<String> indexColumns : indexes) {
                query = " CREATE INDEX IF NOT EXISTS " + table.getTableName() + "_" + StringUtils.join(indexColumns, "_") + "_idx ON " + table.getTableName() + " (" + StringUtils.join(indexColumns, ",") + ")";
                executeQuery(this.query);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CantCreateTableException(e);
        }


    }

    /**
     * This method creates a new table into the database based on the definition received.
     * Create  primary keys and index if defined
     *
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(DatabaseTableFactory table) throws CantCreateTableException {

        /**
         * Get the columns of the table and write the query to create it
         */
        try {
            List<String> primaryKey = new ArrayList<>();
            this.query = "CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (";
            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                this.query += tableColumns.get(i).getName() + " " + tableColumns.get(i).getDataType().name();

                if (tableColumns.get(i).getDataType() == DatabaseDataType.STRING)
                    this.query += "(" + String.valueOf(tableColumns.get(i).getDataTypeSize()) + ")";

                if (tableColumns.get(i).isPrimaryKey())
                    primaryKey.add(tableColumns.get(i).getName());
                if (i < tableColumns.size() - 1)
                    this.query += ",";
            }

            /**
             * add primary key
             */
            if (!primaryKey.isEmpty())
                this.query += ", PRIMARY KEY (" + StringUtils.join(primaryKey, ",") + ") ";

            this.query += ")";

            System.out.println("Create table Query: " + query);
            executeQuery(this.query);
            /**
             * get index column
             */
            List<List<String>> indexes = table.listIndexes();
            for (List<String> indexColumns : indexes) {
                query = " CREATE INDEX IF NOT EXISTS " + table.getTableName() + "_" + StringUtils.join(indexColumns, "_") + "_idx ON " + table.getTableName() + " (" + StringUtils.join(indexColumns, ",") + ")";
                executeQuery(this.query);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CantCreateTableException(e);
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

        return new DesktopDatabaseTableFactory(tableName);
    }

    /**
     * This method provides the caller with a Table Structure object.
     *
     * @param tableName table name to use
     * @return DatabaseTableFactory object
     * @throws InvalidOwnerIdException
     */
    @Override
    public DatabaseTableFactory newTableFactory(String tableName) {

        return new DesktopDatabaseTableFactory(tableName);
    }


    private String buildStoragePath() {
        String databasePath;
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if (ownerId != null)
            databasePath = EnvironmentVariables.getExternalStorageDirectory() + "/" + ownerId.toString();
        else
            databasePath = String.valueOf(EnvironmentVariables.getExternalStorageDirectory());

        return databasePath;
    }

    private String buildDatabasePath() {
        String databasePath;
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if (ownerId != null)
            databasePath = EnvironmentVariables.getExternalStorageDirectory() + "/" + ownerId.toString();
        else
            databasePath = String.valueOf(EnvironmentVariables.getExternalStorageDirectory());

        databasePath += "/" + databaseName.replace("-", "") + ".db";

        return databasePath;
    }

    private String buildDatabasePath(String databaseName) {
        String databasePath;
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if (ownerId != null)
            databasePath = EnvironmentVariables.getExternalStorageDirectory() + "/" + ownerId.toString();
        else
            databasePath = String.valueOf(EnvironmentVariables.getExternalStorageDirectory());

        databasePath += "/" + databaseName.replace("-", "") + ".db";

        return databasePath;
    }
}
