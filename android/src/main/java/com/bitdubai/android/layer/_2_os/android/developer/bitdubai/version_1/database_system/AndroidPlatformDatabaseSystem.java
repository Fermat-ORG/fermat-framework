package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

/**
 * Created by Natalia on 31/03/2015.
 */
public class AndroidPlatformDatabaseSystem implements PlatformDatabaseSystem {

    /**
     * PlatformDatabaseSystem Interface member variables.
     */
    private Context context;


    /**
     * PlatformDatabaseSystem Interface implementation.
     */

    @Override
    public Database openDatabase(String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException{
        AndroidDatabase database;
        database = new AndroidDatabase(this.context, databaseName);

        database.openDatabase(databaseName);

        return database;
    }

    @Override
    public Database createDatabase (String databaseName) throws CantCreateDatabaseException{
        AndroidDatabase database;
        database = new AndroidDatabase(this.context, databaseName);

        database.createDatabase(databaseName);

        return database;
    }

    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }
}
