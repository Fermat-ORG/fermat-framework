package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */

/**
 * This class define methods to manage a DatabaseTable object
 * Set filters and orders, and load records to memory.
 * <p>
 * *
 */

public class AndroidDatabaseTable implements DatabaseTable {

    /**
     * DatabaseTable Member Variables.
     */
    String tableName;
    AndroidDatabase database;

    private List<DatabaseTableFilter> tableFilter;
    private List<DatabaseTableRecord> records;
    private List<DataBaseTableOrder> tableOrder;
    private String top = "";
    private String offset = "";
    private DatabaseTableFilterGroup tableFilterGroup;

    private List<DatabaseSelectOperator> tableSelectOperator;

    // Public constructor declarations.

    /**
     * <p>DatabaseTable implementation constructor
     *
     * @param database  name database to use
     * @param tableName name table to use
     */

    public AndroidDatabaseTable(AndroidDatabase database, String tableName) {
        this.tableName = tableName;
        this.database = database;
    }

    /**
     * DatabaseTable interface implementation.
     */

    /**
     * <p>This method return a new empty instance of DatabaseTableColumn object
     *
     * @return DatabaseTableColumn object
     */
    @Override
    public DatabaseTableColumn newColumn() {
        return new AndroidDatabaseTableColumn();
    }

    /**
     * <p>This method return a list of table columns names
     *
     * @return List<String> of columns names
     */
    private List<String> getColumns(SQLiteDatabase database) {
        List<String> columns = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM " + tableName, null);
        String[] columnNames = c.getColumnNames();
        c.close();

        columns.addAll(Arrays.asList(columnNames));

        return columns;
    }

    /**
     * <p>This method return a list of Database Table Record objects
     *
     * @return List<DatabaseTableRecord> List of DatabaseTableRecord objects
     */
    @Override
    public List<DatabaseTableRecord> getRecords() {
        return this.records;
    }

    /**
     * <p>This method return a new empty instance of DatabaseTableRecord object
     *
     * @return DatabaseTableRecord object
     */
    @Override
    public DatabaseTableRecord getEmptyRecord() {
        return new AndroidDatabaseRecord();

    }


    @Override
    public DatabaseTableFilter getEmptyTableFilter() {
        return new AndroidDatabaseTableFilter();

    }

    @Override
    public DatabaseTableFilter getNewFilter(String column, DatabaseFilterType type, String value) {
        return new AndroidDatabaseTableFilter(column, type, value);
    }

    @Override
    public DatabaseTableFilterGroup getNewFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator) {
        return new AndroidDatabaseTableFilterGroup(tableFilters, filterGroups, filterOperator);
    }

    /**
     * <p>This method clean Filter object
     */
    @Override
    public void clearAllFilters() {
        this.tableFilter = null;
        this.tableFilterGroup = null;
    }

    /**
     * <p>This method return a list of DatabaseTableFilter objects
     *
     * @return List<DatabaseTableFilter> object
     */
    @Override
    public List<DatabaseTableFilter> getFilters() {
        return this.tableFilter;
    }

    /**
     * <p>This method return a DatabaseTableFilterGroup objects
     *
     * @return DatabaseTableFilterGroup object
     */
    @Override
    public DatabaseTableFilterGroup getFilterGroup() {
        return this.tableFilterGroup;
    }

    /**
     * <p>This method update a table record in the database
     *
     * @param record DatabaseTableRecord object to update
     * @throws CantUpdateRecordException
     */
    @Override
    public void updateRecord(DatabaseTableRecord record) throws CantUpdateRecordException {
        SQLiteDatabase database = null;
        try {
            List<DatabaseRecord> records = record.getValues();
            StringBuilder strRecords = new StringBuilder();

            for (int i = 0; i < records.size(); ++i) {

                if (records.get(i).getChange()) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");

                    strRecords.append(records.get(i).getName())
                            .append(" = '")
                            .append(records.get(i).getValue())
                            .append("'");
                }
            }

            database = this.database.getWritableDatabase();
            database.execSQL("UPDATE " + tableName + " SET " + strRecords + " " + makeFilter());

        } catch (Exception exception) {
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if (database != null)
                database.close();
        }
    }

    /**
     * <p>This method inserts a new record in the database
     *
     * @param record DatabaseTableRecord to insert
     * @throws CantInsertRecordException
     */
    @Override
    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecordException {
        /**
         * First I get the table records with values.
         * and construct de ContentValues array for SqlLite
         */
        SQLiteDatabase database = null;
        try {
            StringBuilder strRecords = new StringBuilder("");
            StringBuilder strValues = new StringBuilder("");

            List<DatabaseRecord> records = record.getValues();


            for (int i = 0; i < records.size(); ++i) {
                if (strRecords.length() > 0)
                    strRecords.append(",");
                strRecords.append(records.get(i).getName());

                if (strValues.length() > 0)
                    strValues.append(",");

                strValues.append("'")
                        .append(records.get(i).getValue())
                        .append("'");
            }
            database = this.database.getWritableDatabase();
            database.execSQL("INSERT INTO " + tableName + "(" + strRecords + ")" + " VALUES (" + strValues + ")");
        } catch (Exception exception) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if (database != null) database.close();
        }
    }

    /**
     * <p>This method load all table records in a List of DatabaseTableRecord object
     * <p>Then use the method getRecords() to to retrieve.
     *
     * @throws CantLoadTableToMemoryException
     */
    @Override
    public void loadToMemory() throws CantLoadTableToMemoryException {

        this.records = new ArrayList<>();

        String topSentence = "";
        String offsetSentence = "";
        if (!this.top.isEmpty())
            topSentence = " LIMIT " + this.top;

        if (!this.offset.isEmpty())
            offsetSentence = " OFFSET " + this.offset;

        Cursor cursor = null;

        /**
         * Get columns name to read values of files
         *
         */
        SQLiteDatabase database = null;
        try {
            database = this.database.getReadableDatabase();
            List<String> columns = getColumns(database);
            cursor = database.rawQuery("SELECT  * FROM " + tableName + makeFilter() + makeOrder() + topSentence + offsetSentence, null);
            while (cursor.moveToNext()) {
                DatabaseTableRecord tableRecord = new AndroidDatabaseRecord();
                List<DatabaseRecord> recordValues = new ArrayList<>();

                for (String column : columns) {
                    DatabaseRecord recordValue = new AndroidRecord();
                    recordValue.setName(column);
                    recordValue.setValue(cursor.getString(cursor.getColumnIndex(column)));
                    recordValue.setChange(false);
                    recordValue.setUseValueofVariable(false);
                    recordValues.add(recordValue);
                }

                tableRecord.setValues(recordValues);
                this.records.add(tableRecord);
            }
            cursor.close();
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "Check the cause for this error");
        } finally {
            if (database != null)
                database.close();
        }
    }

    @Override
    public List<DatabaseTableRecord> customQuery(final String query, final boolean customResult) throws CantLoadTableToMemoryException {
        Cursor cursor = null;
        SQLiteDatabase database = null;
        List<String> columns = null;

        List<DatabaseTableRecord> databaseTableRecords = new ArrayList<>();
        try {
            database = this.database.getReadableDatabase();

            if (!customResult)
                columns = getColumns(database);

            cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()) {
                DatabaseTableRecord tableRecord = new AndroidDatabaseRecord();
                List<DatabaseRecord> recordValues = new ArrayList<>();

                if (customResult) {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        DatabaseRecord recordValue = new AndroidRecord();
                        recordValue.setName("Column" + i);
                        recordValue.setValue(cursor.getString(i));
                        recordValues.add(recordValue);
                    }
                } else {
                    for (final String column : columns) {
                        DatabaseRecord recordValue = new AndroidRecord();
                        recordValue.setName(column);
                        recordValue.setValue(cursor.getString(cursor.getColumnIndex(column)));
                        recordValue.setChange(false);
                        recordValue.setUseValueofVariable(false);
                        recordValues.add(recordValue);
                    }
                }

                tableRecord.setValues(recordValues);
                databaseTableRecords.add(tableRecord);
            }
            cursor.close();

        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
            throw new CantLoadTableToMemoryException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "Check the cause for this error");
        } finally {
            if (database != null)
                database.close();
        }
        return databaseTableRecords;
    }

    /**
     * <p>Check if the set will table in tableName variable exists
     *
     * @return boolean
     */
    @Override
    public boolean isTableExists() {
        SQLiteDatabase database = null;
        try {
            database = this.database.getReadableDatabase();
            Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + this.tableName + "'", null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.close();
                    return true;
                }
                cursor.close();
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (database != null) database.close();
        }
        return false;
    }

    /**
     * <p>Sets the filter on a string field
     *
     * @param columName column name to filter
     * @param value     value to filter
     * @param type      DatabaseFilterType object
     */
    @Override
    public void setStringFilter(String columName, String value, DatabaseFilterType type) {

        if (this.tableFilter == null)
            this.tableFilter = new ArrayList<>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value);
        filter.setType(type);

        this.tableFilter.add(filter);
    }

    /**
     * <p>Sets the filter on a UUID field
     *
     * @param columName column name to filter
     * @param value     value to filter
     * @param type      DatabaseFilterType object
     */
    @Override
    public void setUUIDFilter(String columName, UUID value, DatabaseFilterType type) {

        if (this.tableFilter == null)
            this.tableFilter = new ArrayList<>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter();

        filter.setColumn(columName);
        filter.setValue(value.toString());
        filter.setType(type);

        this.tableFilter.add(filter);
    }

    /**
     * <p>Sets the order in which filtering field shown in ascendent or descending
     *
     * @param columnName Name of the column to sort
     * @param direction  DatabaseFilterOrder object
     */
    @Override
    public void setFilterOrder(String columnName, DatabaseFilterOrder direction) {

        if (this.tableOrder == null)
            this.tableOrder = new ArrayList<>();

        DataBaseTableOrder order = new AndroidDatabaseTableOrder();

        order.setColumName(columnName);
        order.setDirection(direction);


        this.tableOrder.add(order);
    }

    /**
     * <p>Sets the operator to apply on select statement
     *
     * @param columnName Name of the column to apply operator
     * @param operator   DataBaseSelectOperatorType type
     */
    @Override
    public void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias) {

        if (this.tableSelectOperator == null)
            this.tableSelectOperator = new ArrayList<>();

        DatabaseSelectOperator selectOperator = new AndroidDatabaseSelectOperator();

        selectOperator.setColumn(columnName);
        selectOperator.setType(operator);
        selectOperator.setAliasColumn(alias);


        this.tableSelectOperator.add(selectOperator);
    }

    /**
     * <p>Sets the filter and subgroup to filter for queries with grouped where
     *
     * @param filterGroup DatabaseTableFilterGroup object
     */
    @Override
    public void setFilterGroup(DatabaseTableFilterGroup filterGroup) {
        this.tableFilterGroup = filterGroup;
    }

    @Override
    public void setFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator) {
        this.tableFilterGroup = new AndroidDatabaseTableFilterGroup(tableFilters, filterGroups, filterOperator);
    }

    /**
     * DatabaseTable interface private void.
     */
    public String makeFilter() {

        // I check the definition for the filter object, filter type, filter columns names
        // and build the WHERE statement
        String filter = "";
        StringBuilder strFilter = new StringBuilder();

        if (this.tableFilter != null) {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter.append(tableFilter.get(i).getColumn());

                switch (tableFilter.get(i).getType()) {
                    case EQUAL:
                        strFilter.append(" ='")
                                .append(tableFilter.get(i).getValue())
                                .append("'");
                        break;
                    case GRATER_THAN:
                        strFilter.append(" >'")
                                .append(tableFilter.get(i).getValue())
                                .append("'");;
                        break;
                    case LESS_THAN:
                        strFilter.append(" < ")
                                .append(tableFilter.get(i).getValue());
                        break;
                    case LIKE:
                        strFilter.append(" Like '%")
                                .append(tableFilter.get(i).getValue())
                                .append("%'");
                        break;
                    default:
                        strFilter.append(" ");
                        break;
                }

                if (i < tableFilter.size() - 1)
                    strFilter.append(" AND ");
            }


            filter = strFilter.toString();
            if (strFilter.length() > 0) filter = " WHERE " + filter;

            return filter;
        } else {
            //if set group filter
            if (this.tableFilterGroup != null) {
                return makeGroupFilters(this.tableFilterGroup);
            } else {
                return filter;
            }
        }
    }


    public String makeGroupFilters(DatabaseTableFilterGroup databaseTableFilterGroup) {

        StringBuilder strFilter = new StringBuilder();
        String filter;

        if (databaseTableFilterGroup != null && (databaseTableFilterGroup.getFilters().size() > 0 || databaseTableFilterGroup.getSubGroups().size() > 0)) {
            strFilter.append("(");
            strFilter.append(makeInternalConditionGroup(databaseTableFilterGroup.getFilters(), databaseTableFilterGroup.getOperator()));

            int ix = 0;

            if (databaseTableFilterGroup.getSubGroups() != null){

                for (DatabaseTableFilterGroup subGroup : databaseTableFilterGroup.getSubGroups()) {
                    if (subGroup.getFilters().size() > 0 || ix > 0) {
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

            }

            strFilter.append(")");
        }

        filter = strFilter.toString();
        if (strFilter.length() > 0) filter = " WHERE " + filter;

        return filter;
    }

    @Override
    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {
        SQLiteDatabase database = null;
        try {
            List<DatabaseRecord> records = record.getValues();

            String queryWhereClause = "";

            if (!records.isEmpty()) {
                for (int i = 0; i < records.size(); ++i) {

                    if (queryWhereClause.length() > 0) {
                        queryWhereClause += " and ";
                        queryWhereClause += records.get(i).getName();
                    } else
                        queryWhereClause += records.get(i).getName();
                    queryWhereClause += "=";
                    queryWhereClause += "'" + records.get(i).getValue() + "'";
                }
            } else {
                queryWhereClause = null;
            }

            database = this.database.getWritableDatabase();
            if (queryWhereClause != null) {
                database.execSQL("DELETE FROM " + tableName + " WHERE " + queryWhereClause);
            } else {
                database.execSQL("DELETE FROM " + tableName);
            }

        } catch (Exception exception) {
            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if(database != null)
                database.close();
        }
    }

    //testear haber si funciona así de abstracto o hay que hacerlo más especifico
    @Override
    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {
        SQLiteDatabase database = null;
        try {
            database = this.database.getReadableDatabase();
            Cursor c = database.rawQuery(" SELECT * from " + tableName + " WHERE pk=" + pk, null);

            List<String> columns = getColumns(database);
            DatabaseTableRecord tableRecord1 = new AndroidDatabaseRecord();
            if (c.moveToFirst()) {
                /**
                 -                * Get columns name to read values of files
                 *
                 */

                List<DatabaseRecord> recordValues = new ArrayList<>();

                for (int i = 0; i < columns.size(); ++i) {
                    DatabaseRecord recordValue = new AndroidRecord();
                    recordValue.setName(columns.get(i));
                    recordValue.setValue(c.getString(c.getColumnIndex(columns.get(i))));
                    recordValue.setChange(false);
                    recordValues.add(recordValue);
                }
                tableRecord1.setValues(recordValues);

                if (c.moveToNext()) {
                    //si pasa esto es porque hay algo mal
                    throw new Exception(); //TODO:Se deberia lanzar una FermatException
                }

            } else {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            }
            c.close();
            return tableRecord1;
        } catch (Exception e) {
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        } finally {
            if (database != null)
                database.close();
        }
    }

    /**
     * <p>Sets the number of records to be selected in query
     *
     * @param top number of records to select (in string)
     */
    @Override
    public void setFilterTop(String top) {
        this.top = top;
    }

    /**
     * <p>Sets the records page
     *
     * @param offset filter offset
     */
    public void setFilterOffSet(String offset) {
        this.offset = offset;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public List<DatabaseSelectOperator> getTableSelectOperator() {
        return tableSelectOperator;
    }

    @Override
    public String toString() {
        return tableName;
    }


    private String makeOrder() {

        // I check the definition for the oder object, order direction, order columns names
        // and build the ORDER BY statement
        String order;
        StringBuilder strOrder = new StringBuilder();

        if (this.tableOrder != null) {
            for (int i = 0; i < tableOrder.size(); ++i) {

                switch (tableOrder.get(i).getDirection()) {
                    case DESCENDING:
                        strOrder.append(tableOrder.get(i).getColumName())
                                .append(" DESC ");
                        break;
                    case ASCENDING:
                        strOrder.append(tableOrder.get(i).getColumName());
                        break;
                    default:
                        strOrder.append(" ");
                        break;

                }
                if (i < tableOrder.size() - 1)
                    strOrder.append(" , ");
            }
        }

        order = strOrder.toString();
        if (strOrder.length() > 0) order = " ORDER BY " + order;

        return order;
    }


    private String makeInternalCondition(DatabaseTableFilter filter) {

        StringBuilder strFilter = new StringBuilder();

        strFilter.append(filter.getColumn());

        switch (filter.getType()) {
            case EQUAL:
                strFilter.append(" ='")
                        .append(filter.getValue())
                        .append("'");
                break;
            case GRATER_THAN:
                strFilter.append(" > ")
                        .append(filter.getValue());
                break;
            case LESS_THAN:
                strFilter.append(" < ")
                        .append(filter.getValue());
                break;
            case LIKE:
                strFilter.append(" Like '%")
                        .append(filter.getValue())
                        .append("%'");
                break;
            default:
                strFilter.append(" ");
        }
        return strFilter.toString();
    }

    private String makeInternalConditionGroup(List<DatabaseTableFilter> filters, DatabaseFilterOperator operator) {

        StringBuilder strFilter = new StringBuilder();

        for (DatabaseTableFilter filter : filters) {
            switch (operator) {
                case AND:
                    if (strFilter.length() > 0)
                        strFilter.append(" AND ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                case OR:
                    if (strFilter.length() > 0)
                        strFilter.append(" OR ");

                    strFilter.append(makeInternalCondition(filter));
                    break;
                default:
                    strFilter.append(" ");
            }

        }
        return strFilter.toString();
    }

}

