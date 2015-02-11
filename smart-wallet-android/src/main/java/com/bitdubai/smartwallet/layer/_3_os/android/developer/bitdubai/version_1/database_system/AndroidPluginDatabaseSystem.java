package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import com.bitdubai.wallet_platform_api.layer._3_os.CantOpenDatabaseException;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTable;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableColumn;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableFilter;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableRecord;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginDatabase;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginDatabaseSystem;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.SQLException;



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
    public PluginDatabase openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
        return null;
    }

    @Override
    public PluginDatabase createDatabase(UUID ownerId, String databaseName) {

        mDatabase = SQLiteDatabase.openOrCreateDatabase(mContext.getFilesDir() +"/" + databaseName, null, null);

        return null;
    }


    public SQLiteDatabase getDatabase() {
        return mDatabase;

    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }



    public void createTable(String databaseName, String tableSchema) {

        mTableSchema = tableSchema;
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
        onCreate(mDatabase);

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
