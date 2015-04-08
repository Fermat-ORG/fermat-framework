package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.File;
/**
 * Created by ciencias on 23.12.14.
 */
public class AndroidDatabase  implements Database, DatabaseFactory {

    private Context context;
    private String databaseName;
    private UUID ownerId;
    private String query;
    private SQLiteDatabase Database;
    private DatabaseTransaction databaseTransaction;
    private DatabaseTable databaseTable;

    /**
     *
     *Constructor
     */
    public AndroidDatabase(Context context, UUID ownerId, String databaseName) {
        this.context = context;
        this.ownerId = ownerId;
        this.databaseName = databaseName;
    }

    public AndroidDatabase(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    /**
     * AndroidDatabase interface implementation.
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
                databasePath =  this.context.getFilesDir().getPath() +  "/" +  ownerId.toString();
            else
                databasePath =  this.context.getFilesDir().getPath();

            databasePath += "/" + databaseName.replace("-","") + ".db";
            File databaseFile = new File(databasePath);

            this.Database = SQLiteDatabase.openDatabase(databasePath,null,0,null);

          }
        catch (Exception exception) {
        
            /**
             * Probably there is no distinctions between a database that it can not be opened and a one that doesn't not exist.
             * We will assume that if it didn't open it was because it didn't exist.
             * * *
             */
            System.err.println("Exception: " + exception.getMessage());
            exception.printStackTrace();
            throw new DatabaseNotFoundException();
            //TODO: NATALIA; Revisa si devuelve la misma exception cuando la base de datos no existe que cuando simplement no la puede abrir por otra razon. Y avisame el resultado de la investigacion esta.
        }

    }

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
             databasePath =  this.context.getFilesDir().getPath() +  "/" +  ownerId.toString();
           else
                databasePath =  this.context.getFilesDir().getPath();

            File storagePath = new File(databasePath);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
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
            System.err.println("Exception: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantCreateDatabaseException();
        }

    }
    /**
     * Database interface implementation.
     */
    @Override
    public void executeQuery() {
        Database.execSQL(query);
    }


    @Override
    public DatabaseTransaction newTransaction(){

        return databaseTransaction = new AndroidDatabaseTransaction();
    }

    @Override
    public DatabaseTable getTable(String tableName){

        databaseTable = new AndroidDatabaseTable(this.context,this.Database, tableName);

        return databaseTable;
    }

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
            System.err.println("DatabaseTransactionFailedException: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseTransactionFailedException();
        } 
    }

    /**
     * DatabaseFactory interface implementation.
     */


    /**
     * This method creates a new table into the database based on the definition received.
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
            if(table.getIndex() != "")
                this.query = " CREATE INDEX IF NOT EXISTS "+ table.getIndex() +"_idx ON " + table.getTableName() + " ("+ table.getIndex() +")";

            executeQuery();
        }catch (Exception e)
        {
            System.err.println("CantCreateTableException: " + e.getMessage());
            e.printStackTrace();
            throw new CantCreateTableException();
        }


    }

    @Override
    public void createTable(DatabaseTableFactory table) throws InvalidOwnerId, CantCreateTableException {

         /**
         * Get the columns of the table and write the query to create it
         */
        try
        {
            this.query ="CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (";
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
            if(table.getIndex() != "")
                this.query = " CREATE INDEX IF NOT EXISTS "+ table.getIndex() +"_idx ON " + table.getTableName() + " ("+ table.getIndex() +")";
            executeQuery();

        }catch (Exception e)
        {
            System.err.println("CantCreateTableException: " + e.getMessage());
            e.printStackTrace();
            throw new CantCreateTableException();
        }


    }


    /**
     * This method provides the caller with a Table Structure object.
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

    @Override
    public DatabaseTableFactory newTableFactory(String tableName) throws InvalidOwnerId {

             return new AndroidDatabaseTableFactory(tableName);
    }


}
