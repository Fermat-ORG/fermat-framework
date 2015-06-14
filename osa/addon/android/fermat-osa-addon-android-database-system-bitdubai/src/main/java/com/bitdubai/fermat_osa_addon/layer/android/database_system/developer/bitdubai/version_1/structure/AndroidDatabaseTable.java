package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015.
 */
/**
 * This class define methods to manage a DatabaseTable object
 * Set filters and orders, and load records to memory.
 *
 * *
 */

public class AndroidDatabaseTable implements  DatabaseTable {

    /**
     * DatabaseTable Member Variables.
     */
    Context context;
    String tableName;
    SQLiteDatabase database;

    private List<DatabaseTableFilter> tableFilter;
    private  List<DatabaseTableRecord> records;
    private  DatabaseTableRecord tableRecord;
    private List<DataBaseTableOrder> tableOrder;
    private String top = "";
    private DatabaseTableFilterGroup tableFilterGroup;

    // Public constructor declarations.

    /**
     * <p>DatabaseTable implementation constructor
     *
     * @param context Android Context Object
     * @param database name database to use
     * @param tableName name table to use
     */

    public AndroidDatabaseTable (Context context,SQLiteDatabase database, String tableName){
        this.tableName = tableName;
        this.context = context;
        this.database = database;
    }

    /**
     * DatabaseTable interface implementation.
     */

    public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
     * <p>This method return a new empty instance of DatabaseTableColumn object
     *
     * @return DatabaseTableColumn object
     */
    @Override
    public DatabaseTableColumn newColumn(){
        return new AndroidDatabaseTableColumn();
        }

    /**
     * <p>This method return a list of table columns names
     *
     * @return List<String> of columns names
     */

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

    /**
     * <p>This method return a list of Database Table Record objects
     *
     * @return List<DatabaseTableRecord> List of DatabaseTableRecord objects
     */
    @Override
    public List<DatabaseTableRecord> getRecords()
    {
        return this.records;

    }

    /**
     *  <p>This method return a new empty instance of DatabaseTableRecord object
     *
     * @return DatabaseTableRecord object
     */
    @Override
    public DatabaseTableRecord getEmptyRecord()
    {
        return new AndroidDatabaseRecord() ;

    }

    /**
     * <p>This method clean Filter object
     */
    @Override
    public void clearAllFilters()
    {
        this.tableFilter = null;
    }

    /**
     * <p>This method return a list of DatabaseTableFilter objects
     *
     * @return List<DatabaseTableFilter> object
     */
    @Override
    public List<DatabaseTableFilter> getFilters()
    {
       return this.tableFilter;
    }

    /**
     * <p>This method return a DatabaseTableFilterGroup objects
     *
     * @return DatabaseTableFilterGroup object
     */
    @Override
    public DatabaseTableFilterGroup getFilterGroup()
    {
        return this.tableFilterGroup;
    }


    /**
     * <p>This method update a table record in the database
     *
     * @param record DatabaseTableRecord object to update
     * @throws CantUpdateRecord
     */
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


        this.database.update(tableName, recordUpdateList, makeFilter().replace("WHERE",""), null);

       }
        catch (Exception exception)
        {
            throw new CantUpdateRecord();
        }
    }

    /**
     * <p>This method inserts a new record in the database
     *
     * @param record DatabaseTableRecord to insert
     * @throws CantInsertRecord
     */
    @Override
    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecord {

        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
        	StringBuffer strRecords = new StringBuffer ("");
            StringBuffer strValues  = new StringBuffer ("");

             List<DatabaseRecord> records =  record.getValues();


            ContentValues initialValues = new ContentValues();

            for (int i = 0; i < records.size(); ++i) {
                initialValues.put(records.get(i).getName(),records.get(i).getValue());

                if(strRecords.length() > 0 )
                    strRecords.append (",");
                strRecords.append(records.get(i).getName ());

                if(strValues.length() > 0 )
                    strValues.append (",");

                strValues.append ("'" + records.get(i).getValue() + "'");
            }

           // this.database.insert(tableName, null, initialValues);

            this.database.execSQL("INSERT INTO " + tableName + "(" + strRecords + ")" + " VALUES (" +  strValues + ")");
        }
        catch (Exception exception) {
            throw new CantInsertRecord();
          }


    }

    /**
     * <p>This method load all table records in a List of DatabaseTableRecord object
     * <p>Then use the method getRecords() to to retrieve.
     *
     * @throws CantLoadTableToMemory
     */
    @Override
    public void loadToMemory() throws CantLoadTableToMemory {


        this.tableRecord  = new AndroidDatabaseRecord();

        List<DatabaseRecord>  recordValues = new ArrayList<DatabaseRecord>();
        this.records = new ArrayList<DatabaseTableRecord>() ;


        String topSentence = "";
        try {


        if(!this.top.isEmpty())
            topSentence = " LIMIT " + this.top ;

            Cursor c = this.database.rawQuery("SELECT  * FROM " + tableName + makeFilter() + makeOrder() + topSentence , null);

            List<String> columns = getColumns();

            if (c.moveToFirst()) {
                do {
                    /**
                     * Get columns name to read values of files
                     *
                     */

                    for (int i = 0; i < columns.size(); ++i) {
                        DatabaseRecord recordValue = new AndroidRecord();
                        recordValue.setName(columns.get(i).toString());
                        recordValue.setValue(c.getString(c.getColumnIndex(columns.get(i).toString())));
                        recordValue.setChange(false);
                        recordValues.add(recordValue);
                    }

                } while (c.moveToNext());
            }


        } catch (Exception e) {
            throw new CantLoadTableToMemory();
        }

        tableRecord.setValues(recordValues);
          this.records.add(this.tableRecord);

    }

    /**
     * <p>Check if the set will table in tableName variable exists
     *
     * @return boolean
     */
    @Override
    public boolean isTableExists() {

        Cursor cursor = this.database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ this.tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     *<p>Sets the filter on a string field
     *
     * @param columName column name to filter
     * @param value value to filter
     * @param type DatabaseFilterType object
     */
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

    /**
     *<p>Sets the filter on a UUID field
     *
     * @param columName column name to filter
     * @param value value to filter
     * @param type DatabaseFilterType object
     */
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

    /**
     * <p>Sets the order in which filtering field shown in ascendent or descending
     *
     * @param columnName    Name of the column to sort
     * @param direction  DatabaseFilterOrder object
     */
    @Override
    public void setFilterOrder(String columnName, DatabaseFilterOrder direction){

        if(this.tableOrder == null)
            this.tableOrder = new ArrayList<DataBaseTableOrder>();

        DataBaseTableOrder order = new AndroidDatabaseTableOrder();

        order.setColumName(columnName);
        order.setDirection(direction);


        this.tableOrder.add(order);
    }

    /**
     *<p>Sets the number of records to be selected in query
     *
     * @param top number of records to select (in string)
     */
    @Override
    public void setFilterTop(String top){
        this.top = top;
    }

    /**
     *<p>Sets the filter and subgroup to filter for queries with grouped where
     *
     * @param filters list of DatabaseTableFilter object
     * @param subGroups list of DatabaseTableFilterGroup objects
     * @param operator DatabaseFilterOperator enumerator
     */
    @Override
    public void setFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator operator){

        DatabaseTableFilterGroup filterGroup = new AndroidDatabaseTableFilterGroup();

        filterGroup.setFilters(filters);
        filterGroup.setSubGroups(subGroups);
        filterGroup.setOperator(operator);

        this.tableFilterGroup = filterGroup;
    }

    /**
     * DatabaseTable interface private void.
     */

    /**
     * Sets the context for access to the device memory
     * @param context Android Context Object
     */

    public void setContext(Object context) {
        this.context = (Context) context;
    }

    private String makeFilter(){

        // I check the definition for the filter object, filter type, filter columns names
        // and build the WHERE statement
        String filter = "";
        StringBuffer strFilter = new StringBuffer();

        if(this.tableFilter != null)
        {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter.append(tableFilter.get(i).getColumn());

                switch (tableFilter.get(i).getType()) {
                    case EQUAL:
                        strFilter.append(" ='" + tableFilter.get(i).getValue() + "'");
                        break;
                    case GRATER_THAN:
                        strFilter.append( " > " + tableFilter.get(i).getValue());
                        break;
                    case LESS_THAN:
                        strFilter.append( " < " + tableFilter.get(i).getValue());
                        break;
                    case LIKE:
                        strFilter.append(" Like '%" + tableFilter.get(i).getValue() + "%'");
                        break;
                    default:
                        strFilter.append(" ");
                        break;
                }

                if(i < tableFilter.size()-1)
                    strFilter.append(" AND ");

            }
        }

        filter = strFilter.toString();
        if(strFilter.length() > 0 ) filter = " WHERE " + filter;

        return filter;
    }

    private String makeOrder(){

        // I check the definition for the oder object, order direction, order columns names
        // and build the ORDER BY statement
        String order= "";
        StringBuffer strOrder = new StringBuffer();

        if(this.tableOrder != null)
        {
            for (int i = 0; i < tableOrder.size(); ++i) {

                switch (tableOrder.get(i).getDirection()) {
                    case DESCENDING:
                        strOrder.append(tableOrder.get(i).getColumName() + " DESC ");
                        break;
                    case ASCENDING:
                        strOrder.append(tableOrder.get(i).getColumName());
                        break;
                    default:
                        strOrder.append(" ");
                        break;

                }
                    if(i < tableOrder.size()-1)
                        strOrder.append(" , ");

            }
        }

        order = strOrder.toString();
        if(strOrder.length() > 0 ) order = " ORDER BY " + order;

        return order;
    }


    private String makeInternalCondition(DatabaseTableFilter filter){

        StringBuffer strFilter = new StringBuffer();

       strFilter.append(filter.getColumn());

        switch (filter.getType()) {
            case EQUAL:
                strFilter.append(" ='" + filter.getValue() + "'");
                break;
            case GRATER_THAN:
                strFilter.append(" > " + filter.getValue());
                break;
            case LESS_THAN:
                strFilter.append(" < " + filter.getValue());
                break;
            case LIKE:
                strFilter.append(" Like '%" + filter.getValue() + "%'");
                break;
            default:
                strFilter.append(" ");
        }
        return strFilter.toString();
    }

    private String makeInternalConditionGroup(List<DatabaseTableFilter> filters, DatabaseFilterOperator operator){

        StringBuffer strFilter = new StringBuffer();

        for (DatabaseTableFilter filter : filters){
            switch (operator) {
                case AND:
                    if(strFilter.length() > 0)
                        strFilter.append(" AND ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                case OR:
                    if(strFilter.length() > 0)
                        strFilter.append(" OR ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                default:
                    strFilter.append(" ");
            }

        }
        return strFilter.toString();
    }

    public String makeGroupFilters(DatabaseTableFilterGroup databaseTableFilterGroup){

        StringBuffer strFilter = new StringBuffer();
        String filter = "";

        if(databaseTableFilterGroup != null && (databaseTableFilterGroup.getFilters().size() > 0 || databaseTableFilterGroup.getSubGroups().size() > 0)) {
                strFilter.append("(");
                strFilter.append(makeInternalConditionGroup(databaseTableFilterGroup.getFilters(), databaseTableFilterGroup.getOperator()));

                int ix = 0;
                for(DatabaseTableFilterGroup subGroup : databaseTableFilterGroup.getSubGroups()){
                    if (subGroup.getFilters().size() > 0 || ix > 0){
                        switch (databaseTableFilterGroup.getOperator()) {
                            case AND:
                                strFilter.append(" AND ");
                                break;
                            case OR:
                                strFilter.append(" OR ");
                                break;
                            default:
                                strFilter.append(" ");
                        }
                    }
                    strFilter.append("(");
                    strFilter.append(makeGroupFilters(subGroup));
                    strFilter.append(")");
                    ix++;
                }
                strFilter.append(")");
        }

        filter = strFilter.toString();
        if(strFilter.length() > 0 ) filter = " WHERE " + filter;

        return filter;
    }


}

