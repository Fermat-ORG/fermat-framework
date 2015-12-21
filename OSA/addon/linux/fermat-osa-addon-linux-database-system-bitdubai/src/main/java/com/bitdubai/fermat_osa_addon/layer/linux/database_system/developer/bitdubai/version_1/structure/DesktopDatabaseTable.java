/*
* @#DesktopDatabaseTable.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator.AND;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator.OR;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder.ASCENDING;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder.DESCENDING;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType.EQUAL;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType.GREATER_THAN;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType.LESS_THAN;
import static com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType.LIKE;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseSelectOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.DesktopDatabaseBridge;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTable</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTable implements  DatabaseTable{


    /**
     * DatabaseTable Member Variables.
     */
    String tableName;
    DesktopDatabaseBridge database;

    private List<DatabaseTableFilter> tableFilter;
    private  List<DatabaseTableRecord> records;
    private  DatabaseTableRecord tableRecord;
    private List<DataBaseTableOrder> tableOrder;
    private String top = "";
    private String offset = "";
    private DatabaseTableFilterGroup tableFilterGroup;
    private List<DatabaseSelectOperator> tableSelectOperator;

    // Public constructor declarations.
    public DesktopDatabaseTable (DesktopDatabaseBridge database, String tableName){
        this.tableName = tableName;
        //this.context = context;
        this.database = database;
    }

    @Override
    public List<DatabaseTableRecord> customQuery(String query, boolean customResult) throws CantLoadTableToMemoryException {
        return null;
    }

    /**
     * DatabaseTable interface implementation.
     */

    /*
     * <p>This method return a new empty instance of DatabaseTableColumn object
     *
     * @return DatabaseTableColumn object
     */
    @Override
    public DatabaseTableColumn newColumn(){
        return new DesktopDatabaseTableColumn();
    }

    /**
     * <p>This method return a list of table columns names
     *
     * @return List<String> of columns names
     */


    public List<String> getColumns()
    {
        List<String> columns = new ArrayList<String>();
        ResultSet rs = this.database.rawQuery("SELECT * FROM "+ tableName, null);
        /*
        Obtener el nombre de las columnas
        *(
        */
        try{
            ResultSetMetaData rsmd = rs.getMetaData();
            //List<String> lstColumnNames=new ArrayList<String>();
            for(int i=0;i<rsmd.getColumnCount();i++){
                columns.add(rsmd.getColumnName(i));
            }
            //fin
            /*String[] columnNames = new String[lstColumnNames.size()];

            columnNames = lstColumnNames.toArray(columnNames);

            for (int i = 0; i < columnNames.length; ++i) {
                columns.add(columnNames[i].toString());
            }*/
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
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
        DatabaseTableRecord record = new DesktopDatabaseRecord() ;
        return record;
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

    @Override
    public DatabaseTableFilter getEmptyTableFilter() {
        return null;
    }

    @Override
    public DatabaseTableFilter getNewFilter(String column, DatabaseFilterType type, String value) {
        return null;
    }

    @Override
    public DatabaseTableFilterGroup getNewFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator) {
        return null;
    }


    @Override
    public void updateRecord (DatabaseTableRecord record) throws CantUpdateRecordException
    {

        try
        {
            List<DatabaseRecord> records =  record.getValues();

            //ContentValues recordUpdateList = new ContentValues();
            Map<String, Object> recordUpdateList = new HashMap<String, Object>();

            /**
             * I update only the fields marked as modified
             *
             */

            for (int i = 0; i < records.size(); ++i) {

                if(records.get(i).getChange())

                    recordUpdateList.put(records.get(i).getName(), records.get(i).getValue());
            }


            this.database.update(tableName, recordUpdateList, makeFilter().replace("WHERE",""), null);


        }catch (Exception exception)
        {
            throw new CantUpdateRecordException();
        }
    }


    @Override
    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecordException {

        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        try{
            StringBuffer strRecords = new StringBuffer ("");
            StringBuffer strValues  = new StringBuffer ("");

            List<DatabaseRecord> records =  record.getValues();

            Map<String, Object> initialValues = new HashMap<String, Object>();
            //ContentValues initialValues = new ContentValues();

            for (int i = 0; i < records.size(); ++i) {
                initialValues.put(records.get(i).getName(),records.get(i).getValue());

                if(strRecords.length() > 0 )
                    strRecords.append (",");
                strRecords.append(records.get(i).getName ());

                if(strValues.length() > 0 )
                    strValues.append(",");

                strValues.append ("'" + records.get(i).getValue() + "'");
            }

            // this.database.insert(tableName, null, initialValues);

            this.database.execSQL("INSERT INTO " + tableName + "(" + strRecords + ")" + " VALUES (" + strValues + ")");
        }
        catch (Exception exception) {
            throw new CantInsertRecordException();
        }


    }


    @Override
    public void loadToMemory() throws CantLoadTableToMemoryException {


        this.tableRecord  = new DesktopDatabaseRecord();

        List<DatabaseRecord>  recordValues = new ArrayList<DatabaseRecord>();
        this.records = new ArrayList<DatabaseTableRecord>() ;


        String topSentence = "";
        try {


            if(!this.top.isEmpty())
                topSentence = " LIMIT " + this.top ;

            ResultSet rs = this.database.rawQuery("SELECT  * FROM " + tableName + makeFilter() + makeOrder() + topSentence , null);

            List<String> columns = getColumns();

            if (rs.next()) {
                do {
                    /**
                     * Get columns name to read values of files
                     *
                     */

                    for (int i = 0; i < columns.size(); ++i) {
                        DatabaseRecord recordValue = new DesktopRecord();
                        recordValue.setName(columns.get(i).toString());
                        //recordValue.setValue(c.getString(c.getColumnIndex(columns.get(i).toString())));
                        recordValue.setValue(rs.getString(i));
                        recordValue.setChange(false);
                        recordValues.add(recordValue);
                    }

                } while (rs.next());
            }


        } catch (Exception e) {
            throw new CantLoadTableToMemoryException();
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

        ResultSet cursor = this.database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ this.tableName+"'", null);
        try{
            ResultSetMetaData rsmd = cursor.getMetaData();
            if(cursor!=null) {
                if(rsmd.getColumnCount()>0) {
                    cursor.close();
                    return true;
                }
                cursor.close();
            }
        }catch(Exception e){
            e.printStackTrace();
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

        DatabaseTableFilter filter = new DesktopDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value);
        filter.setType(type);

        this.tableFilter.add(filter);
    }

    @Override
    public void setFermatEnumFilter(String columnName, FermatEnum value, DatabaseFilterType type) {

    }

    @Override
    public void setFilterGroup(DatabaseTableFilterGroup filterGroup) {

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

        DatabaseTableFilter filter = new DesktopDatabaseTableFilter();

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

        DataBaseTableOrder order = new DesktopDatabaseTableOrder();

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

    @Override
    public void setFilterOffSet(String offset) {
        this.offset = offset;
    }

    @Override
    public void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias) {

        if (this.tableSelectOperator == null)
            this.tableSelectOperator = new ArrayList<>();

        DatabaseSelectOperator selectOperator = new DesktopDatabaseSelectOperator();

        selectOperator.setColumn(columnName);
        selectOperator.setType(operator);
        selectOperator.setAliasColumn(alias);


        this.tableSelectOperator.add(selectOperator);

    }

    @Override
    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {

    }

    @Override
    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {
        return null;
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

        DatabaseTableFilterGroup filterGroup = new DesktopDatabaseTableFilterGroup(filters,subGroups,operator);

       /* filterGroup.setFilters(filters);
        filterGroup.setSubGroups(subGroups);
        filterGroup.setOperator(operator);*/

        this.tableFilterGroup = filterGroup;
    }

    /**
     * DatabaseTable interface public void.
     */

    @Override
    public String makeFilter(){

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
                    case GREATER_THAN:
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

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<DatabaseSelectOperator> getTableSelectOperator() {
        return tableSelectOperator;
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
            case GREATER_THAN:
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
