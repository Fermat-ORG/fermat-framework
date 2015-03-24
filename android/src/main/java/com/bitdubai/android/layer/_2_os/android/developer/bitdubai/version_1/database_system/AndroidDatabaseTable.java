package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseTable implements  DatabaseTable {
    Context context;

    String tableName;
    SQLiteDatabase database;

    List<DatabaseTableFilter> tableFilter;
    private  List<DatabaseTableRecord> records;
    private  DatabaseTableRecord tableRecord;

    public AndroidDatabaseTable (Context context,SQLiteDatabase database, String tableName){
        this.tableName = tableName;
        this.context = context;
        this.database = database;
    }
    
    
    @Override
    public DatabaseTableColumn newColumn(String name){
        return new AndroidDatabaseTableColumn();
        }

    @Override
    public List<String> getColumns()
    {
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
        return this.records;

    }
    @Override
    public DatabaseTableRecord getEmptyRecord()
    {
        DatabaseTableRecord record = new AndroidDatabaseRecord() ;
        return record;
    }

    public void addFilter (DatabaseTableFilter filter)
    {
        tableFilter.add(filter);
    }

    public void clearAllFilters()
    {
        this.tableFilter = null;
    }

    public List<DatabaseTableFilter> getFilters()
    {
       return this.tableFilter;
    }

    @Override
    public void updateRecord (DatabaseTableRecord record) throws CantNotUpdateRecord
    {
        
        // IMPORTANTE SOLO ACTUALIZAR LOS CAMPOS MARCADOS COMO QUE CAMBIARON; NO TODOS.
        
        try
        {

         Map<String,String> records =  record.getValues();
        List<String> columns = this.getColumns();

        //filter
        String  strFilter = "";

            //filter
            if(tableFilter != null)
            {
                for (int i = 0; i < tableFilter.size(); ++i) {

                    strFilter += tableFilter.get(i).getColumn();
                    switch (tableFilter.get(i).getType()) {
                        case EQUAL:
                            strFilter += " =" + tableFilter.get(i).getValue();
                            break;
                        case GRATER_THAN:
                            strFilter += " > " + tableFilter.get(i).getValue();
                            break;
                        case LESS_THAN:
                            strFilter += " < " + tableFilter.get(i).getValue();
                            break;
                        case LIKE:
                            strFilter += " Like " + tableFilter.get(i).getValue();
                            break;
                    }

                    if(i < tableFilter.size()-1)
                        strFilter +=  " AND ";

                }
            }
            if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        ContentValues recordUpdateList = new ContentValues();

        for (int i = 0; i < columns.size(); ++i) {
            recordUpdateList.put(columns.get(i), records.get(i));
        }


        this.database.update(tableName, recordUpdateList, strFilter, null);

       }
        catch (Exception exception) {

            System.err.println("CantNotUpdateRecord: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantNotUpdateRecord();
        }
    }


    public void setContext(Object context) {
        this.context = (Context) context;
    }



    public void insertRecord(DatabaseTableRecord record) throws CantNotInsertRecord  {

        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
            Map<String,String> recordsValues =  record.getValues();

            ContentValues initialValues = new ContentValues();

            Iterator<Map.Entry<String, String>> evalue = recordsValues.entrySet().iterator();

            while (evalue.hasNext()) {
                Map.Entry<String, String> valueEntry = evalue.next();
                initialValues.put(valueEntry.getKey(),valueEntry.getValue());

            }

            this.database.insert(tableName, null, initialValues);
        }
        catch (Exception exception) {

            System.err.println("CantNotInsertRecord: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantNotInsertRecord();
          }


    }

    public void loadToMemory(){

        String strFilter = "";
        this.tableRecord  = new AndroidDatabaseRecord();
        Map<String,String> values = new HashMap<String, String>();

        this.records = new ArrayList<DatabaseTableRecord>() ;

        //filter
        if(tableFilter != null)
        {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter += tableFilter.get(i).getColumn();
                switch (tableFilter.get(i).getType()) {
                    case EQUAL:
                        strFilter += " =" + tableFilter.get(i).getValue();
                        break;
                    case GRATER_THAN:
                        strFilter += " > " + tableFilter.get(i).getValue();
                        break;
                    case LESS_THAN:
                        strFilter += " < " + tableFilter.get(i).getValue();
                        break;
                    case LIKE:
                        strFilter += " Like " + tableFilter.get(i).getValue();
                        break;
                }

                if(i < tableFilter.size()-1)
                    strFilter +=  " AND ";

            }
        }

        if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        try {
            Cursor c = this.database.rawQuery("SELECT * FROM " + tableName + strFilter, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                values.put(c.getColumnName(i), c.getString(i));
                c.moveToNext();
            }
        } catch (Exception e) {
            throw  e;
        }


        tableRecord.setValues(values);
        this.records.add(tableRecord);

    }



}

