package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.bitdubai.fermat_api.layer._2_os.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015.
 */
public class AndroidDatabaseTable implements  DatabaseTable {
    Context context;

    String tableName;
    SQLiteDatabase database;

    private List<DatabaseTableFilter> tableFilter;
    private  List<DatabaseTableRecord> records;
    private  DatabaseTableRecord tableRecord;
    private List<DataBaseTableOrder> tableOrder;

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



    @Override
    public void clearAllFilters()
    {
        this.tableFilter = null;
    }

    public List<DatabaseTableFilter> getFilters()
    {
       return this.tableFilter;
    }

    @Override
    public void updateRecord (DatabaseTableRecord record) throws CantUpdateRecord
    {

        try
        {

            List<DatabaseRecord> records =  record.getValues();

            ContentValues recordUpdateList = new ContentValues();

            /**
             * I update only the fields marked as modified
             *
             */

        for (int i = 0; i < records.size(); ++i) {

            if(records.get(i).getChange())
                recordUpdateList.put(records.get(i).getName(), records.get(i).getValue());
        }


        this.database.update(tableName, recordUpdateList, makeFilter() + makeOrder(), null);

       }
        catch (Exception exception) {

            System.err.println("CantNotUpdateRecord: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantUpdateRecord();
        }
    }


    public void setContext(Object context) {
        this.context = (Context) context;
    }



    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecord {

        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
            List<DatabaseRecord> records =  record.getValues();

            ContentValues initialValues = new ContentValues();

            for (int i = 0; i < records.size(); ++i) {
                initialValues.put(records.get(i).getName(),records.get(i).getValue());
            }

            this.database.insert(tableName, null, initialValues);
        }
        catch (Exception exception) {

            System.err.println("CantNotInsertRecord: " + exception.getMessage());
            exception.printStackTrace();
            throw new CantInsertRecord();
          }


    }

    @Override
    public void loadToMemory() throws CantLoadTableToMemory {


        this.tableRecord  = new AndroidDatabaseRecord();

        List<DatabaseRecord>  recordValues = new ArrayList<DatabaseRecord>();
        this.records = new ArrayList<DatabaseTableRecord>() ;


        try {
            List<String> columns = getColumns();
            Cursor c = this.database.rawQuery("SELECT * FROM " + tableName + makeFilter() + makeOrder(), null);

            if (c.moveToFirst()) {
                do {
                    /**
                     * Get columns name to read values of files
                     *
                     */

                    for (int i = 0; i < columns.size(); ++i) {
                        DatabaseRecord recordValue = new AndroidRecord();
                        recordValue = new AndroidRecord();
                        recordValue.setName(columns.get(i).toString());
                        recordValue.setValue(c.getString(c.getColumnIndex(columns.get(i).toString())));
                        recordValue.setChange(false);
                        recordValues.add(recordValue);
                    }

                } while (c.moveToNext());
            }


        } catch (Exception e) {
            System.err.println("CantNotLoadTableToMemory: " + e.getMessage());
            e.printStackTrace();
            throw new CantLoadTableToMemory();
        }


        this.tableRecord.setValues(recordValues);
        this.records.add(this.tableRecord);

    }

    @Override
    public void setStringFilter(String columName, String value,DatabaseFilterType type){

        if(this.tableFilter == null)
            this.tableFilter = new ArrayList<DatabaseTableFilter>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value);
        filter.setType(type);

        this.tableFilter.add(filter);
    }

    @Override
    public void setUUIDFilter(String columName, UUID value,DatabaseFilterType type){

        if(this.tableFilter == null)
            this.tableFilter = new ArrayList<DatabaseTableFilter>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value.toString());
        filter.setType(type);

        this.tableFilter.add(filter);

    }

    @Override
    public void setFilterOrder(String columnName, DatabaseFilterOrder direction){

        if(this.tableOrder == null)
            this.tableOrder = new ArrayList<DataBaseTableOrder>();

        DataBaseTableOrder order = new AndroidDatabaseTableOrder();

        order.setColumName(columnName);
        order.setDirection(direction);


        this.tableOrder.add(order);
    }

    private String makeFilter(){

        String  strFilter = "";


        if(this.tableFilter != null)
        {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter += tableFilter.get(i).getColumn();
                switch (tableFilter.get(i).getType()) {
                    case EQUAL:
                        strFilter += " ='" + tableFilter.get(i).getValue() + "'";
                        break;
                    case GRATER_THAN:
                        strFilter += " > " + tableFilter.get(i).getValue();
                        break;
                    case LESS_THAN:
                        strFilter += " < " + tableFilter.get(i).getValue();
                        break;
                    case LIKE:
                        strFilter += " Like '%" + tableFilter.get(i).getValue() + "%'";
                        break;
                }

                if(i < tableFilter.size()-1)
                    strFilter +=  " AND ";

            }
        }
        if(strFilter.length() > 0 ) strFilter = " WHERE " + strFilter;

        return strFilter;
    }


    private String makeOrder(){

        String  strOrder = "";

        if(this.tableOrder != null)
        {
            for (int i = 0; i < tableOrder.size(); ++i) {

                strOrder += tableOrder.get(i).getColumName();
                switch (tableOrder.get(i).getDirection()) {
                    case DESCENDING:
                        strOrder +=  tableOrder.get(i).getColumName() + " DESC ";
                        break;
                    case ASCENDING:
                        strOrder += tableOrder.get(i).getColumName();
                        break;

                }
                    if(i < tableOrder.size()-1)
                        strOrder +=  " , ";

            }
        }
        if(strOrder.length() > 0 ) strOrder = " ORDER BY " + strOrder;

        return strOrder;
    }
}

