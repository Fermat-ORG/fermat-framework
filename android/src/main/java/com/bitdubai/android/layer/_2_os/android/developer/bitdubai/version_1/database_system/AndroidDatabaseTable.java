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
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseTable implements  DatabaseTable {
    Context context;

    String tableName;
    SQLiteDatabase database;
    AndroidDatabaseTableFilter tableFilter;
    private  List<DatabaseTableRecord> records;


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
        dbPlugIn.setContext(this.context);

        List<String> columns = new ArrayList<String>();
        Cursor c = this.database.rawQuery("SELECT * FROM "+ tableName, null);
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
        dbPlugIn.setContext(this.context);

        //filter
        String  strFilter = getFilter();

        if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        try {
            Cursor c = this.database.rawQuery("SELECT * FROM "+ tableName + strFilter, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                values.add(c.getString(i));
                c.moveToNext();
            }
        } catch (Exception e) {
            throw  e;
        }

        this.database.close();
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
        this.tableFilter = new AndroidDatabaseTableFilter();
        AndroidDatabaseTableColumn column = new AndroidDatabaseTableColumn();

        this.tableFilter.setColumn(filter.getColumn());
        this.tableFilter.setType(filter.getType());
        this.tableFilter.setValue(filter.getValue());
    }

    public void clearAllFilters()
    {
        this.tableFilter = null;
    }

    public List<DatabaseTableFilter> getFilters()
    {
        List<DatabaseTableFilter> filter = new ArrayList<DatabaseTableFilter>();
        filter.add(this.tableFilter);

        return null;
    }

    @Override
    public void updateRecord (DatabaseTableRecord record)
    {
        
        // IMPORTANTE SOLO ACTUALIZAR LOS CAMPOS MARCADOS COMO QUE CAMBIARON; NO TODOS.
        
        try
        {


        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(this.context);

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        //filter
        String  strFilter = getFilter();

            if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        ContentValues recordUpdateList = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            recordUpdateList.put(columns.get(i), records.get(i));
        }


        this.database.update(tableName, recordUpdateList, strFilter, null);
            this.database.close();

       }catch (Exception e)
        {
            throw e;
        }
    }


    public void setContext(Object context) {
        this.context = (Context) context;
    }



    public void insertRecord(DatabaseTableRecord record) {

        AndroidPluginDatabaseSystem dbPlugIn = new AndroidPluginDatabaseSystem();
        dbPlugIn.setContext(this.context);
      //  mDatabase = dbPlugIn.getDatabase();

        List<String> records =  record.getValues();
        List<String> columns = this.getColumns();

        ContentValues initialValues = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            initialValues.put(columns.get(i), records.get(i));
        }


        this.database.insert(tableName,null,initialValues);

       this.database.close();

    }

    public void loadToMemory(){
        this.records = getRecords();
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

}

