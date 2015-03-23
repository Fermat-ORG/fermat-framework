package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by toshiba on 09/02/2015.
 */
public class AndroidDatabaseTable implements  DatabaseTable {
    Context mContext;

    String tableName;
    SQLiteDatabase mDatabase;
    AndroidDatabaseTableFilter mTableFilter;


    public AndroidDatabaseTable (String tableName){
        this.tableName = tableName;
    }
    
    
    @Override
    public DatabaseTableColumn newColumn(String name){
        return new AndroidDatabaseTableColumn();
        }

    @Override
    public List<String> getColumns()
    {
        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(mContext);
      //  mDatabase = dbPlugIn.getDatabase();

        List<String> columns = new ArrayList<String>();
        Cursor c = mDatabase.rawQuery("SELECT * FROM "+ tableName, null);
        String[] columnNames = c.getColumnNames();

        for (int i = 0; i < columnNames.length; ++i) {
            columns.add(columnNames[i].toString());
        }

        return columns;
    }
    @Override
    public List<DatabaseTableRecord> getRecords()
    {
        AndroidDatabaseRecord record  = new AndroidDatabaseRecord();
        List<String> values = new ArrayList<String>();

        List<DatabaseTableRecord> records = new ArrayList<DatabaseTableRecord>() ;
        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(mContext);
     //   mDatabase = dbPlugIn.getDatabase();

        //filter
        String  strFilter = getFilter();

        if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        try {
            Cursor c = mDatabase.rawQuery("SELECT * FROM "+ tableName + strFilter, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                values.add(c.getString(i));
                c.moveToNext();
            }
        } catch (Exception e) {
            throw  e;
        }

        mDatabase.close();
        record.setValues(values);
        records.add(record);
        return null;
    }
    @Override
    public DatabaseTableRecord getEmptyRecord()
    {
        DatabaseTableRecord record = new AndroidDatabaseRecord() ;
        return record;
    }

    public void addFilter (DatabaseTableFilter filter)
    {
        mTableFilter = new AndroidDatabaseTableFilter();
        AndroidDatabaseTableColumn column = new AndroidDatabaseTableColumn();

        mTableFilter.setColumn(filter.getColumn());
        mTableFilter.setType(filter.getType());
        mTableFilter.setValue(filter.getValue());
    }

    public void clearAllFilters()
    {
        mTableFilter = null;
    }

    public List<DatabaseTableFilter> getFilters()
    {
        List<DatabaseTableFilter> filter = new ArrayList<DatabaseTableFilter>();
        filter.add(mTableFilter);

        return null;
    }

    @Override
    public void updateRecord (DatabaseTableRecord record)
    {
        try
        {


        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(mContext);
       // mDatabase = dbPlugIn.getDatabase();

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        //filter
        String  strFilter = getFilter();

            if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        ContentValues recordUpdateList = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            recordUpdateList.put(columns.get(i), records.get(i));
        }


        mDatabase.update(tableName, recordUpdateList, strFilter, null);
        mDatabase.close();

       }catch (Exception e)
        {
            throw e;
        }
    }


    public void setContext(Object context) {
        mContext = (Context) context;
    }



    public void insertRecord(DatabaseTableRecord record) {

        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(mContext);
      //  mDatabase = dbPlugIn.getDatabase();

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        ContentValues initialValues = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            initialValues.put(columns.get(i), records.get(i));
        }


        mDatabase.insert(tableName,null,initialValues);

        mDatabase.close();

    }

 /*   public  ArrayList<Row> getAllRows(String databaseName, String tableName) {
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
    }*/

    public void deleteRow(String databaseName, String keyId, long keyValue) {
        mDatabase = SQLiteDatabase.openDatabase(mContext.getFilesDir() +"/" + databaseName,null,SQLiteDatabase.OPEN_READWRITE);

        mDatabase.delete(this.tableName, keyId + keyValue, null);
        mDatabase.close();
    }


public String getFilter()
{
    List<DatabaseTableFilter> filters = getFilters();

    String  strFilter = "";
    for (int j = 0; j < filters.size(); ++j) {

        DatabaseTableColumn filterColumn  = filters.get(j).getColumn();
        String filterType = filters.get(j).getType().name();

//STRING, INTEGER, MONEY

        if( filterColumn.getName() != ""){

            switch (filterType){
                case "EQUAL":
                    filterType = "=";
                    break;
                case "GRATER_THAN":
                    filterType = ">";
                    break;
                case "LESS_THAN":
                    filterType = "<";
                    break;
                case "LIKE":
                    filterType = "LIKE";
                    break;
            }

            if (strFilter.length() > 0) strFilter += " AND " ;
            strFilter =  filterColumn.getName() + filterType + filters.get(j).getValue();

        }

    }
    return strFilter;
}
    public class Row implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;
        public String name;
        public String value;
        public String value_type;
    }
}

