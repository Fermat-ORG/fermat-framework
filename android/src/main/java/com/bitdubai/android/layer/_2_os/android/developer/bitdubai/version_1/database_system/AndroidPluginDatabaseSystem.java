package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidPluginDatabaseSystem  implements PluginDatabaseSystem{

    private Context Context;
    private SQLiteDatabase Database;
    private static final int DATABASE_VERSION = 1;


    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException {
        this.Database = SQLiteDatabase.openDatabase(this.Context.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
        return new AndroidDatabase(this.Context);

    }

    @Override
    public Database createDatabase(UUID ownerId, String databaseName) {

        this.Database = SQLiteDatabase.openOrCreateDatabase(this.Context.getFilesDir() +"/" + databaseName, null, null);
        return new AndroidDatabase(this.Context);
    }


    public SQLiteDatabase getDatabase() {
        return this.Database;

    }

    @Override
    public void setContext(Object context) {
        this.Context = (Context) context;
    }




}
