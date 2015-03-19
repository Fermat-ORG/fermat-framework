package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bitdubai.fermat_api.layer._2_os.database_system.Database;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciencias on 23.12.14.
 */
public class AndroidDatabase extends SQLiteOpenHelper implements Database {

   private Context Context;
    private SQLiteDatabase Database;
    private String TableName;
    private static String DatabaseName;
    private static int DATABASE_VERSION = 1;

    public AndroidDatabase(Context context) {
        super(context, DatabaseName, null, DATABASE_VERSION);
        this.Context = context;
    }

    @Override
    public DatabaseTable newTable(){
        return new AndroidDatabaseTable();
    }

    public  List<String> getLocalPersonalUsersIds() {

        List<String> LocalPersonalUsersIds = new ArrayList<String>();
        LocalPersonalUsersIds.add ("1");

        return LocalPersonalUsersIds;
    }

    @Override
    public void createTable(String tableName) {
        this.TableName = tableName;
      //  mTableSchema = tableSchema;
       // this.Database = SQLiteDatabase.openDatabase(this.Context.getFilesDir() + "/" + databaseName, null, SQLiteDatabase.OPEN_READWRITE);
       // onCreate(this.Database);


    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        //String CREATE_TABLE = "CREATE TABLE " + this.TableName + "("
         //       +KEY_1 + " INTEGER PRIMARY KEY)";
       // db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
