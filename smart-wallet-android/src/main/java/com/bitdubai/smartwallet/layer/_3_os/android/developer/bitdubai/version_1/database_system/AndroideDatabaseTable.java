package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTable;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableFilter;
import com.bitdubai.wallet_platform_api.layer._3_os.DatabaseTableRecord;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroideDatabaseTable implements  DatabaseTable {
    Context mContext;

    String mTableName;
    SQLiteDatabase mDatabase;

    public void setContext(Object context) {
        mContext = (Context) context;
    }

    public void setTableName(String tablename) {
        mTableName = tablename;
    }


    public void insertRecord(DatabaseTableRecord record) {

        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);
        mDatabase = dbPlugIn.getDatabase();

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        ContentValues initialValues = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            initialValues.put(columns.get(i), records.get(i));
        }


        mDatabase.insert(mTableName,null,initialValues);

        mDatabase.close();

    }

    @Override
    public List<String> getColumns()
    {
        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);
        mDatabase = dbPlugIn.getDatabase();

        List<String> columns = new ArrayList<String>();
        Cursor c = mDatabase.rawQuery("SELECT * FROM "+ mTableName, null);
        String[] columnNames = c.getColumnNames();

        for (int i = 0; i < columnNames.length; ++i) {
            columns.add(columnNames[i].toString());
        }

        return columns;
    }
    @Override
    public List<DatabaseTableRecord> getRecords()
    {

        List<DatabaseTableRecord> records ;
        return null;
    }
    @Override
    public DatabaseTableRecord getEmptyRecord()
    {
        return null;
    }

    public void addFilter (DatabaseTableFilter filter)
    {

    }

    public void clearAllFilters()
    {

    }

    public List<DatabaseTableFilter> getFilters()
    {
        List<DatabaseTableFilter> filter ;

        return null;
    }

    @Override
    public void updateRecord (DatabaseTableRecord record)
    {
        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem(mContext);
        mDatabase = dbPlugIn.getDatabase();

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        ContentValues recordUpdateList = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            recordUpdateList.put(columns.get(i), records.get(i));
        }

        List<DatabaseTableFilter> filter = getFilters();

        mDatabase.update(mTableName, recordUpdateList, columns.get(0) +"=" + records.get(0), null);
        mDatabase.close();
    }




    public  ArrayList<Row> getAllRows(String databaseName, String tableName) {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() + "/" + databaseName, null, SQLiteDatabase.OPEN_READWRITE);

        ArrayList<Row>  rec = new  ArrayList<Row>();
        try {
            Cursor c = mDatabase.rawQuery("SELECT * FROM "+ tableName, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                c.getColumnCount();
                for (int j = 0; j < numRows; ++j) {
                    row.name = c.getColumnName(j);
                    row.value = c.getString(j);
                    //row.value_type = c.getType(j);
                    rec.add(row);
                }

                c.moveToNext();
            }
        } catch (Exception e) {
            throw  e;
        }

        mDatabase.close();
        return rec;
    }

    public  ArrayList<Row> getRows(String databaseName, String tableName, String conditions) {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);

        ArrayList<Row>  rec = new  ArrayList<Row>();
        try {
            Cursor c = mDatabase.rawQuery("SELECT * FROM "+ tableName + " WHERE " + conditions, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Row row = new Row();
                c.getColumnCount();
                for (int j = 0; j < numRows; ++j) {
                    row.name = c.getColumnName(j);
                    row.value = c.getString(j);
                    //row.value_type = c.getType(j);
                    rec.add(row);
                }

                c.moveToNext();
            }
        } catch (Exception e) {
            throw  e;
        }

        mDatabase.close();
        return rec;
    }

    public void deleteRow(String databaseName, String tableName, String keyId, long keyValue) {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);

        mDatabase.delete(tableName, keyId + keyValue, null);
        mDatabase.close();
    }



    public class Row implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;
        public String name;
        public String value;
        public String value_type;
    }
}


//ownerId id del plugind: cada pugin tiene su juego de archivos
// a la libreria le llega el id, ver de agregar al nombre de la base de datos el id del plug in
// para el caso de los archivos 