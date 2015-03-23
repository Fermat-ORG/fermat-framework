package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import android.os.Environment;
import java.io.File;
/**
 * Created by ciencias on 23.12.14.
 */
public class AndroidDatabase  implements Database, DatabaseFactory {

    private Context Context;
    private String databaseName;
    private UUID ownerId;
    private String query;
    private SQLiteDatabase Database;
    private int DATABASE_VERSION = 1;

    public AndroidDatabase(Context context, UUID ownerId, String databaseName) {
        this.Context = context;
        this.ownerId = ownerId;
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
            String databasePath =  this.Context.getFilesDir().getPath() +  "/" +  ownerId.toString();

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
            throw new DatabaseNotFoundException();
            //TODO: NATALIA; Revisa si devuelve la misma exception cuando la base de datos no existe que cuando simplement no la puede abrir por otra razon. Y avisame el resultado de la investigacion esta.
        }

    }


    public void createDatabase(String databaseName) throws CantCreateDatabaseException {

        /**
         * First I try to open the database.
         */
        try {
            String databasePath =  this.Context.getFilesDir().getPath() +  "/" +  ownerId.toString();

            File storagePath = new File(databasePath);
            if (!storagePath.exists()) {
                storagePath.mkdirs();
            }

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
        
        // TODO: NATALIA: Leer los campos de la nueva tabla (quizas falta un metodo que los devuelva) y efectevitamente crear la tabla con esos campos en la base de datos. Si por algo no se puede, hay que disparar CantCreateTableException

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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    





    public DatabaseTable newTable(String tableName){
        return new AndroidDatabaseTable(tableName);
    }

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }


    public void createTable() {

      //  mTableSchema = tableSchema;
       // this.Database = SQLiteDatabase.openDatabase(this.Context.getFilesDir() + "/" + databaseName, null, SQLiteDatabase.OPEN_READWRITE);
       // onCreate(this.Database);


    }

    // Creating Tables

    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        //String CREATE_TABLE = "CREATE TABLE " + this.TableName + "("
         //       +KEY_1 + " INTEGER PRIMARY KEY)";
       // db.execSQL(CREATE_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
