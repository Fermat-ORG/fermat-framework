package com.bitdubai.android.layer._3_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;

import com.bitdubai.wallet_platform_api.layer._3_os.file_system.CantOpenDatabaseException;
import com.bitdubai.wallet_platform_api.layer._3_os.database_system.Database;
import com.bitdubai.wallet_platform_api.layer._3_os.database_system.PluginDatabaseSystem;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.UUID;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidPluginDatabaseSystem extends SQLiteOpenHelper implements PluginDatabaseSystem{

    Context mContext;
    private static String mDatabaseName = "";
    String mDatabaseSchema;
    String mTableName;
    String mTableSchema;
    SQLiteDatabase mDatabase;
    private static final int DATABASE_VERSION = 1;

    public AndroidPluginDatabaseSystem(Context context) {
        super(context, mDatabaseName, null, DATABASE_VERSION);
        mContext = context;
    }



    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
        return new AndroidDatabase();

    }

    @Override
    public Database createDatabase(UUID ownerId, String databaseName) {

        mDatabase = SQLiteDatabase.openOrCreateDatabase(mContext.getFilesDir() +"/" + databaseName, null, null);
        return new AndroidDatabase();
    }


    public SQLiteDatabase getDatabase() {
        return mDatabase;

    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(mTableSchema);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);

        // Create tables again
        onCreate(db);
    }
}
