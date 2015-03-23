package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 23.12.14.
 */
public class AndroidDatabase  implements Database, DatabaseFactory {

    private Context Context;
    private String databaseName;
    private UUID ownerId;
    
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
            this.Database = SQLiteDatabase.openDatabase(this.Context.getFilesDir() +"/" + ownerId + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
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


    /**
     * Database interface implementation.
     */
    @Override
    public void executeQuery() {

    }


    /**
     * DatabaseFactory interface implementation.
     */

    /**
     * This method creates a new table into the database based on the definition received.
     */
    @Override
    public void createTable(UUID ownerId, DatabaseTableFactory table) throws InvalidOwnerId{

        /**
         * I check that the owner id is the same I currently have..
         */
        if (this.ownerId != ownerId) {
            throw new InvalidOwnerId();
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public Database createDatabase(UUID ownerId, String databaseName) {

        this.Database = SQLiteDatabase.openOrCreateDatabase(this.Context.getFilesDir() +"/" + databaseName, null, null);
        return new AndroidDatabase(this.Context, ownerId, databaseName);
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
