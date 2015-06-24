package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotOpenException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerId;
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
        }catch(Exception e)
        {
            /**
             * for error not complete transaction
             */
            throw new DatabaseTransactionFailedException();
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
        try {
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

            this.Database = SQLiteDatabase.openDatabase(databasePath,null,0,null);



          }
        catch (Exception exception) {
        
            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */

            throw new DatabaseNotFoundException("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaargh#");
            //TODO: NATALIA; Revisa si devuelve la misma exception cuando la base de datos no existe que cuando simplement no la puede abrir por otra razon. Y avisame el resultado de la investigacion esta.
        }

    }
    /**
     * Close connection
     * if used by a plugin, method close active connection
     *
     * @throws DatabaseNotOpenException
     */

    public void closeDatabase()throws DatabaseNotOpenException{  //throws CantOpenDatabaseException, DatabaseNotFoundException {

        /**
         * First I try to open the database.
         */
            if(!Database.isOpen()) throw new DatabaseNotOpenException();

        this.Database.close();

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
            File databaseFile = new File(databasePath);

             SQLiteDatabase.deleteDatabase(databaseFile);

        }
        catch (Exception exception) {

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


    /**
     * This method creates a new database file
     *
     * @param databaseName name database to create
     * @throws CantCreateDatabaseException
     */
    @Override
    public void createDatabase(String databaseName) throws CantCreateDatabaseException {


        /**
         * First I try to open the database.
         */
        try {



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
            if (!storagePath.exists() && storagePath.mkdirs()){
                	storagePath = null;
            }

            /**
             * Hash data base name
             */

            databasePath += "/" + databaseName.replace("-","") + ".db";
            File databaseFile = new File(databasePath);
            this.Database = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);


        }
        catch (Exception exception) {


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
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerId
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(UUID ownerId, DatabaseTableFactory table) throws InvalidOwnerId, CantCreateTableException {

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerId();
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


            executeQuery();

            /**
             * get index column
             */
            if(table.getIndex() != null && !table.getIndex().isEmpty ()) {
                this.query = " CREATE INDEX IF NOT EXISTS " + table.getIndex() + "_idx ON " + table.getTableName() + " (" + table.getIndex() + ")";

                executeQuery();
            }

        }catch (Exception e)
        {
            //e.printStackTrace();
        	throw new CantCreateTableException();
        }


    }

    /**
     * This method creates a new table into the database based on the definition received.
     * Create  primary keys and index if defined
     *
     * @param table DatabaseTableFactory object containing the definition of the table
     * @throws InvalidOwnerId
     * @throws CantCreateTableException
     */
    @Override
    public void createTable(DatabaseTableFactory table) throws CantCreateTableException {

         /**
         * Get the columns of the table and write the query to create it
         */
        try
        {
            String primaryKey = "";
            this.query ="CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (";
            ArrayList<DatabaseTableColumn> tableColumns = table.getColumns();

            for (int i = 0; i < tableColumns.size(); i++) {

                this.query += tableColumns.get(i).getName() +" " +  tableColumns.get(i).getType().name();

                if(tableColumns.get(i).getType() == DatabaseDataType.STRING)
                    this.query +="("+ String.valueOf(tableColumns.get(i).getDataTypeSize()) + ")";

                if(tableColumns.get(i).getPrimaryKey())
                    primaryKey = tableColumns.get(i).getName();
                if(i < tableColumns.size()-1)
                    this.query +=",";


            }

            /**
             * add primary key
             */
            if(!primaryKey.isEmpty())
                this.query += ", PRIMARY KEY ("+primaryKey+") ";

            this.query += ")";
            executeQuery();
            /**
             * get index column
             */
            if(table.getIndex() != null && !table.getIndex().isEmpty ()) {
                this.query = " CREATE INDEX IF NOT EXISTS " + table.getIndex() + "_idx ON " + table.getTableName() + " (" + table.getIndex() + ")";
                executeQuery();
            }



        }catch (Exception e)
        {
            //e.printStackTrace();
        	throw new CantCreateTableException();
        }


    }


    /**
     * This method provides the caller with a Table Structure object.
     * verify plugin owner id
     *
     * @param ownerId Plugin owner id
     * @param tableName table name to use
     * @return DatabaseTableFactory Object
     * @throws InvalidOwnerId
     */
    @Override
    public DatabaseTableFactory newTableFactory(UUID ownerId, String tableName) throws InvalidOwnerId {

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerId();
        }

        return new AndroidDatabaseTableFactory(tableName);
    }

    /**
     * This method provides the caller with a Table Structure object.
     *
     * @param tableName table name to use
     * @return DatabaseTableFactory object
     * @throws InvalidOwnerId
     */
    @Override
    public DatabaseTableFactory newTableFactory(String tableName) {

             return new AndroidDatabaseTableFactory(tableName);
    }


}
