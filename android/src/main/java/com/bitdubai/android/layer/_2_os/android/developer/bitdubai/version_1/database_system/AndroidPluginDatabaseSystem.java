package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidPluginDatabaseSystem  implements PluginDatabaseSystem{

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    private Context Context;


    /**
     * PluginDatabaseSystem Interface implementation.
     */
    
    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        
        AndroidDatabase database;
        database = new AndroidDatabase(this.Context, ownerId, databaseName);

        database.openDatabase(databaseName);
        
        return database;
    }

    @Override
    public Database createDatabase(UUID ownerId, String databaseName) {
        return new AndroidDatabase(this.Context, ownerId, databaseName);
    }


    @Override
    public void setContext(Object context) {
        this.Context = (Context) context;
    }




}
