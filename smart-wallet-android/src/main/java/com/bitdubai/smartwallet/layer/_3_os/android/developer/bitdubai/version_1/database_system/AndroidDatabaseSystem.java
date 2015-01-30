package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import com.bitdubai.wallet_platform_api.layer._3_os.Database;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseSystem;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidDatabaseSystem extends SQLiteOpenHelper implements DatabaseSystem {

    Context mContext;
    private static String mDatabaseName = "";
    String mDatabaseSchema;
    String mTableName;
    String mTableSchema;
    SQLiteDatabase mDatabase;
    private static final int DATABASE_VERSION = 1;

    public AndroidDatabaseSystem(Context context) {
        super(context, mDatabaseName, null, DATABASE_VERSION);
        mContext = context;
    }



    @Override
    public Database getDatabase(String databaseName) {
        return null;
    }

    @Override
    public Database createDatabase(String databaseName) {

         mDatabase = SQLiteDatabase.openOrCreateDatabase(mContext.getFilesDir() +"/" + databaseName, null, null);

        return null;
    }

    @Override
    public Database createDatabase(String databaseName, String databaschema) {

        return null;
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

    public void insertRecord(String databaseName, String tableName, ContentValues recordList) {

        mTableName = tableName;
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);

        mDatabase.insert(tableName,null,recordList);

    }

    public void updateRow(String databaseName, String tableName, String keyId, long keyValue, ContentValues recordUpdateList) {

        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);
        mDatabase.update(tableName, recordUpdateList, keyId +"=" + keyValue, null);
    }

   /* public List<Row> fetchAllRows() {
        ArrayList<Row> ret = new ArrayList<Row>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE, new String[] {
                            "_id", "code", "name"}, null, null, null, null, null);
            int numRows = c.count();
            c.first();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                row._Id = c.getLong(0);
                row.code = c.getString(1);
                row.name = c.getString(2);
                ret.add(row);
                c.next();
            }
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }*/

    public void deleteRow(String databaseName, String tableName, String keyId, long keyValue) {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);

        mDatabase.delete(tableName, keyId + keyValue, null);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(mTableSchema);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);

        // Create tables again
        onCreate(db);
    }
}
