package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;

/**
 * Created by ciencias on 23.12.14.
 */
/**
 * This class define methods to execute query and transactions on Database
 * And method to get a Database table definition
 *
 * *
 */

public class AndroidDatabase  implements Database, DatabaseFactory {

    /**
     * Database Interface member variables.
     */

    private Context context;
    public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public DatabaseTransaction getDatabaseTransaction() {
		return databaseTransaction;
	}

	public void setDatabaseTransaction(DatabaseTransaction databaseTransaction) {
		this.databaseTransaction = databaseTransaction;
	}

	private String databaseName;
    private UUID ownerId;
    private String query;
    private SQLiteDatabase Database;
    private DatabaseTransaction databaseTransaction;
    private DatabaseTable databaseTable;


    public AndroidDatabase(){
	}
    // Public constructor declarations.

    /**
     * <p>Plugin implementation constructor
     *
     * @param context Android Context Object
     * @param ownerId PlugIn owner id
     * @param databaseName name database using
     */
    public AndroidDatabase(Context context, UUID ownerId, String databaseName) {
        this.context = context;
        this.ownerId = ownerId;
        this.databaseName = databaseName;
    }

    /**
     * <p>Platform implementation constructor
     *
     * @param context Android Context Object
     * @param databaseName name database using
     */
    public AndroidDatabase(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    /**
     * Database interface implementation.
     */

    /**
     * <p> This method execute a string query command in database
     */
    @Override
    public void executeQuery() {
        Database.execSQL(query);
    }


    /**
     * <p>Return a new DatabaseTransaction definition
     *
     * @return DatabaseTransaction object
     */
    @Override
    public DatabaseTransaction newTransaction(){

        return new AndroidDatabaseTransaction();
    }

    /**
     * <p>Return a DatabaseTable definition
     *
     * @param tableName name database table using
     * @return DatabaseTable Object
     */
    @Override
    public DatabaseTable getTable(String tableName){

        databaseTable = new AndroidDatabaseTable(this.context,this.Database, tableName);

        return databaseTable;
    }

    /**
     * <p>Execute a transaction in database
     *
     * @param transaction DatabaseTransaction object to contain definition of operations to update and insert
     * @throws DatabaseTransactionFailedException
     */
    @Override
    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException{

        /**
         * I get tablets and records to insert or update
         * then make sql sentences
         */

        List<DatabaseTable> insertTables = transaction.getTablesToInsert();
        List<DatabaseTable> updateTables = transaction.getTablesToUpdate();
        List<DatabaseTableRecord> updateRecords = transaction.getRecordsToUpdate();
        List<DatabaseTableRecord> insertRecords = transaction.getRecordsToInsert();
        try{
            if(!this.Database.isOpen())
                openDatabase(databaseName);

            this.Database.beginTransaction(); // EXCLUSIVE

            //update
            if(updateTables != null)
                for (int i = 0; i < updateTables.size(); ++i){
                    updateTables.get(i).updateRecord(updateRecords.get(i));
                }

            //insert
            if(insertTables != null)
                for (int i = 0; i < insertTables.size(); ++i){
                    insertTables.get(i).insertRecord(insertRecords.get(i));
                }

            this.Database.setTransactionSuccessful();
            this.Database.endTransaction();
        }catch(Exception exception) {

            /**
             * for error not complete transaction
             */
            String message = DatabaseTransactionFailedException.DEFAULT_MESSAGE;
            FermatException cause = exception instanceof FermatException ? (FermatException) exception : FermatException.wrapException(exception);
            String context = "Dabatase Name: " + databaseName;
            context += DatabaseTransactionFailedException.CONTEXT_CONTENT_SEPARATOR;
            context += transaction.toString();
            String possibleReason = "The most reasonable thing to do here is check the cause as this is a triggered exception that can come from many situations";

            throw new DatabaseTransactionFailedException(message, cause, context, possibleReason);
        }
    }


    /**
     * Private methods implementation.
     */

    /**
     * Open specific database file
     * if used by a plugin, method use plugin id to define directory path name
     *
     * @param databaseName name of database to open
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */

    public void openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        /**
         * First I try to open the database.
         */
        String databasePath ="";
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if(ownerId != null)
            databasePath =  this.context.getFilesDir().getPath() +  "/databases/" +  ownerId.toString();
        else
            databasePath =  this.context.getFilesDir().getPath() + "/databases/";

        databasePath += "/" + databaseName.replace("-","") + ".db";

        if(!(new File(databasePath)).exists()){
            String message = DatabaseNotFoundException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Database Constructed Path: " + databasePath;
            String possibleReason = "Check if the constructed path is valid";
            throw new DatabaseNotFoundException(message, cause, context, possibleReason);
        }

        try {
            this.Database = SQLiteDatabase.openDatabase(databasePath,null,0,null);
        } catch (SQLiteException exception) {
        
            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            String message = CantOpenDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(exception);
            String context = "Database Constructed Path: " + databasePath;
            String possibleReason = "Check the cause for this error as we have already checked that the database exists";
            throw new CantOpenDatabaseException(message, cause, context, possibleReason);
        }

    }

    /**
     * 

te a specific database file
     * if used by a plugin, It use plugin id to define directory path name
     *
     * @param databaseName name of database to deleted
     * @throws CantOpenDatabaseException
     * @throws DatabaseNotFoundException
     */


    public void deleteDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

        // determine directry path name
        String databasePath ="";

        if(ownerId != null)
            databasePath =  this.context.getFilesDir().getPath() +  "/databases/" +  ownerId.toString();
        else
            databasePath =  this.context.getFilesDir().getPath() + "/databases/";

        databasePath += "/" + databaseName.replace("-","") + ".db";


            /**
             * if owner id if null
             * because it comes from platformdatabase
             */
        File databaseFile = new File(databasePath);

        if(SQLiteDatabase.deleteDatabase(databaseFile))
            return;

        /**
         * unexpected error deleting the database
         * * *
         */
        String message = "SOMETHING UNEXPECTED HAS HAPPENED";
        FermatException cause = null;
        String context = "Constructed Database Path: " + databasePath;
        String possibleCause = "The most probable reason is that the database path could not be found";
        throw new DatabaseNotFoundException(message, cause, context, possibleCause);

    }

    public void closeDatabase(){
        this.Database.close();
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

        String databasePath ="";
        /**
         * if owner id if null
         * because it comes from platformdatabase
         */
        if(ownerId != null)
            databasePath =  this.context.getFilesDir().getPath() +   "/databases/" +   ownerId.toString();
        else
            databasePath =  this.context.getFilesDir().getPath() +  "/databases/" ;

        File storagePath = new File(databasePath);
        if (!storagePath.exists())
            storagePath.mkdirs();

        /**
         * Hash data base name
         */
        File databaseFile = new File(storagePath.getPath() + "/" + databaseName.replace("-","") + ".db");

        if(databaseFile.exists()){
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Database File: " + databaseFile.getPath();
            String possibleReasons = "This happens if the Database has already been created";
            throw new CantCreateDatabaseException(message, cause, context, possibleReasons);
        }
        /**
         * This call opens or creates the Database, it doesn't throw a determined exception, but we'll try to emulate one in the tests
         */
        try{
            this.Database = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);
        } catch (SQLiteException ex){
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(ex);
            String context = "Storage Path: " + storagePath.getPath();
            context += CantCreateDatabaseException.CONTEXT_CONTENT_SEPARATOR;
            context += "Database Name: " + databaseName;
            String possibleReasons = "This can happen if the Database File where we wanted to create the Database can't be created";
            throw new CantCreateDatabaseException(message, cause, context, possibleReasons);
        }
    }

    /**
     * This method creates a new table into the database based on the definition received.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerIdException
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(UUID ownerId, DatabaseTableFactory table) throws InvalidOwnerIdException, CantCreateTableException {

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            String message = InvalidOwnerIdException.DEFAULT_MESSAGE;
            String context = "Database Owner Id: " + this.ownerId;
            FermatException cause = null;
            context += InvalidOwnerIdException.CONTEXT_CONTENT_SEPARATOR;
            context += "Owner Id in the method invocation: " + ownerId;
            String possibleReason = "The owner Id passed in the Invocation doesn't belong to the Android Database Owner, maybe this was a passed object";
            throw new InvalidOwnerIdException(message, cause, context, possibleReason);
        }

        if(table == null){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = null;
            String context = "Owner Id : " + ownerId.toString();
            String possibleReason = "DatabaseTableFactory can't be null.";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }

        if(getTable(table.getTableName()).isTableExists()){
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause =null;
            String context = "Owner Id : " + ownerId.toString();
            context += CantCreateTableException.CONTEXT_CONTENT_SEPARATOR;
            context += "DatabaseTableFactory Info: " + table.toString();
            String possibleReason = "The table already exists.";
            throw new CantCreateTableException(message, cause, context, possibleReason);
        }
        
         /**
         * Get the columns of the table and write the query to create it
         */
        try
        {
            this.query ="CREATE TABLE IF NOT EXISTS " + table.getTableName() + "(";
            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                this.query += tableColumns.get(i).getName() +" " +  tableColumns.get(i).getType().name();
                if(tableColumns.get(i).getType() == DatabaseDataType.STRING)
                    this.query +="("+ String.valueOf(tableColumns.get(i).getDataTypeSize()) + ")";

                if(i < tableColumns.size()-1)
                    this.query +=",";
            }

            this.query += ")";

            if(!Database.isOpen())
                openDatabase(databaseName);

            executeQuery();

            /**
             * get index column
             */
            if(table.getIndex() != null && !table.getIndex().isEmpty ()) {
                this.query = " CREATE INDEX IF NOT EXISTS " + table.getIndex() + "_idx ON " + table.getTableName() + " (" + table.getIndex() + ")";
                executeQuery();
            }

        }catch (Exception ex) {
            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = ex instanceof FermatException ? (FermatException) ex : FermatException.wrapException(ex);
            String context = "Owner Id : " + ownerId.toString();
            context += CantCreateTableException.CONTEXT_CONTENT_SEPARATOR;
            context += "DatabaseTableFactory Info: " + table.toString();
            String possibleReason = "Check the cause for the reason we are getting this error.";
            throw new CantCreateTableException(message, cause, context, possibleReason);
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
        try{
            createTable(ownerId, table);
        } catch (InvalidOwnerIdException ex){
            throw new CantCreateTableException(CantCreateDatabaseException.DEFAULT_MESSAGE, ex, "Database Owner Id: " + this.ownerId, "This error is strange and shouldn't ever happen");
        }
    }


    /**
     * This method provides the caller with a Table Structure object.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
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
     * @throws InvalidOwnerIdException
     */
    @Override
    public DatabaseTableFactory newTableFactory(String tableName) {
             return new AndroidDatabaseTableFactory(tableName);
    }


}
