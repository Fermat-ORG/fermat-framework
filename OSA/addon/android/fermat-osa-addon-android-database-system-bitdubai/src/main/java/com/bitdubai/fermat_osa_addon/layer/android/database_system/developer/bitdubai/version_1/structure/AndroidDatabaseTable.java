package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseTableOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseAggregateFunction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantTruncateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

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
    private List<AndroidDatabaseTableNearbyLocationOrder> tableNearbyLocationOrders;
    private String top = "";
    private String offset = "";
    private DatabaseTableFilterGroup tableFilterGroup;

    private List<DatabaseAggregateFunction> tableAggregateFunction;

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
     * <p>This method return a list of table columns names
     *
     * @return List<String> of columns names
     */
    private List<String> getColumns(SQLiteDatabase database) {
        List<String> columns = new ArrayList<>();
        Cursor c = database.rawQuery(new StringBuilder().append("SELECT * FROM ").append(tableName).toString(), null);
        String[] columnNames = c.getColumnNames();
        c.close();

        columns.addAll(Arrays.asList(columnNames));

        return columns;
    }

    @Override
    public long getCount() throws CantLoadTableToMemoryException {
        return this.records.size();
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
        return
                new AndroidDatabaseTableFilter();

    }

    @Override
    public DatabaseTableFilter getNewFilter(final String             column,
                                            final DatabaseFilterType type  ,
                                            final String             value ) {

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
            StringBuilder query = new StringBuilder("");
            StringBuilder strRecords = new StringBuilder();

            for (int i = 0; i < records.size(); ++i) {

                if (records.get(i).isChange()) {

                    if (strRecords.length() > 0)
                        strRecords.append(",");

                    strRecords.append(records.get(i).getName())
                            .append(" = '")
                            .append(records.get(i).getValue())
                            .append("'");
                }
            }

            query.append("UPDATE ")
                    .append(tableName)
                    .append(" SET ")
                    .append(strRecords)
                    .append(makeFilter());

            database = this.database.getWritableDatabase();
            database.execSQL(query.toString());

        } catch (Exception exception) {
            throw new CantUpdateRecordException(CantUpdateRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if (database != null)
                database.close();
        }
    }

    @Override
    public void deleteAllRecord() throws CantDeleteRecordException {

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
            database.execSQL(new StringBuilder().append("INSERT INTO ").append(tableName).append("(").append(strRecords).append(")").append(" VALUES (").append(strValues).append(")").toString());
        } catch (Exception exception) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if (database != null) database.close();
        }
    }


    @Override
    public void truncate() throws CantTruncateTableException {

        try (SQLiteDatabase database = this.database.getWritableDatabase()) {

            database.execSQL(new StringBuilder().append("DELETE FROM ").append(tableName).toString());

        } catch (Exception exception) {

            throw new CantTruncateTableException(
                    exception,
                    null,
                    "Check the cause for this error"
            );
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
        StringBuilder queryString = new StringBuilder("");
        String topSentence = "";
        String offsetSentence = "";
        if (!this.top.isEmpty())
            topSentence = new StringBuilder().append(" LIMIT ").append(this.top).toString();

        if (!this.offset.isEmpty())
            offsetSentence = new StringBuilder().append(" OFFSET ").append(this.offset).toString();

        Cursor cursor = null;

        /**
         * Get columns name to read values of files
         *
         */
        SQLiteDatabase database = null;
        try {
            database = this.database.getReadableDatabase();
            List<String> columns = getColumns(database);
            queryString.append("SELECT *");
            queryString.append(makeOutputColumns());
            queryString.append(" FROM ");
            queryString.append(tableName);
            queryString.append(makeFilter());
            queryString.append(makeOrder());
            queryString.append(topSentence);
            queryString.append(offsetSentence);

            cursor = database.rawQuery(queryString.toString(), null);
            while (cursor.moveToNext()) {
                AndroidDatabaseRecord tableRecord = new AndroidDatabaseRecord();

                for (String column : columns) {
                    DatabaseRecord recordValue = new AndroidRecord(
                            column,
                            cursor.getString(cursor.getColumnIndex(column)),
                            false
                    );
                    tableRecord.addValue(recordValue);
                }

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

            if (cursor != null)
                cursor.close();
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
                AndroidDatabaseRecord tableRecord = new AndroidDatabaseRecord();

                if (customResult) {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        DatabaseRecord recordValue = new AndroidRecord(
                                new StringBuilder().append("Column").append(i).toString(),
                                cursor.getString(i),
                                false
                        );
                        tableRecord.addValue(recordValue);
                    }
                } else {
                    for (final String column : columns) {
                        DatabaseRecord recordValue = new AndroidRecord(
                                column,
                                cursor.getString(cursor.getColumnIndex(column)),
                                false
                        );
                        tableRecord.addValue(recordValue);
                    }
                }

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
            if (cursor != null)
                cursor.close();
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
        Cursor cursor = null;
        try {
            database = this.database.getReadableDatabase();
            cursor = database.rawQuery(new StringBuilder().append("select DISTINCT tbl_name from sqlite_master where tbl_name = '").append(this.tableName).append("'").toString(), null);
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
            if (cursor != null)
                cursor.close();
        }
        return false;
    }

    /**
     * <p>Sets the filter on a string field
     *
     * @param columnName column name to filter
     * @param value     value to filter
     * @param type      DatabaseFilterType object
     */
    @Override
    public void addStringFilter(String columnName, String value, DatabaseFilterType type) {

        if (this.tableFilter == null)
            this.tableFilter = new ArrayList<>();

        DatabaseTableFilter filter = new AndroidDatabaseTableFilter(
                columnName,
                type      ,
                value
        );

        this.tableFilter.add(filter);
    }

    @Override
    public void addFermatEnumFilter(final String             columnName,
                                    final FermatEnum         value     ,
                                    final DatabaseFilterType type      ) {

        if (this.tableFilter == null)
            this.tableFilter = new ArrayList<>();

        this.tableFilter.add(
                new AndroidDatabaseTableFilter(
                        columnName,
                        type,
                        value.getCode()
                )
        );
    }

    /**
     * <p>Sets the filter on a UUID field
     *
     * @param columName column name to filter
     * @param value     value to filter
     * @param type      DatabaseFilterType object
     */
    @Override
    public void addUUIDFilter(String columName, UUID value, DatabaseFilterType type) {

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
    public void addFilterOrder(final String              columnName,
                               final DatabaseFilterOrder direction ) {

        if (this.tableOrder == null)
            this.tableOrder = new ArrayList<>();

        DataBaseTableOrder order = new AndroidDatabaseTableOrder(columnName, direction);

        this.tableOrder.add(order);
    }

    // TODO implement in android version
    @Override
    public void addNearbyLocationOrder(final String              latitudeField ,
                                       final String              longitudeField,
                                       final Location            point         ,
                                       final DatabaseFilterOrder direction     ,
                                       final String              distanceField ) {

        if (tableNearbyLocationOrders == null)
            tableNearbyLocationOrders = new ArrayList<>();

        tableNearbyLocationOrders.add(
                new AndroidDatabaseTableNearbyLocationOrder(
                        latitudeField ,
                        longitudeField,
                        point         ,
                        direction     ,
                        distanceField
                )
        );
    }

    /**
     * <p>Sets the operator to apply on select statement
     *
     * @param columnName Name of the column to apply operator
     * @param operator   DataBaseAggregateFunctionType type
     */
    @Override
    public void addAggregateFunction(String columnName, DataBaseAggregateFunctionType operator, String alias) {

        if (this.tableAggregateFunction == null)
            this.tableAggregateFunction = new ArrayList<>();

        DatabaseAggregateFunction AggregateFunction = new AndroidDatabaseAggregateFunction(
                columnName,
                operator,
                alias
        );

        this.tableAggregateFunction.add(AggregateFunction);
    }

    /**
     * <p>Sets the filter and subgroup to filter for queries with grouped where
     *
     * @param filterGroup DatabaseTableFilterGroup object
     */
    @Override
    public void setFilterGroup(final DatabaseTableFilterGroup filterGroup) {
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

                strFilter.append(makeInternalCondition(tableFilter.get(i)));

                if (i < tableFilter.size() - 1)
                    strFilter.append(" AND ");
            }


            filter = strFilter.toString();
            if (strFilter.length() > 0) filter = new StringBuilder().append(" WHERE ").append(filter).toString();

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
        if (strFilter.length() > 0) filter = new StringBuilder().append(" WHERE ").append(filter).toString();

        return filter;
    }

    public String makeOutputColumns() {

        if (tableAggregateFunction != null) {

            String filter = ", ";
            for (DatabaseAggregateFunction AggregateFunction : tableAggregateFunction) {
                filter += new StringBuilder().append(AggregateFunction.toSQLQuery()).append(", ").toString();
            }

            return filter.substring(0, filter.length() - 2);
        }

        else
            return "";
    }

    @Override
    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {
        SQLiteDatabase database = null;
        try {
            List<DatabaseRecord> records = record.getValues();

            StringBuilder queryWhereClause = new StringBuilder();

            if (!records.isEmpty()) {
                for (int i = 0; i < records.size(); ++i) {

                    if (queryWhereClause.length() > 0) {
                        queryWhereClause.append(" and ");
                        queryWhereClause.append(records.get(i).getName());
                    } else
                        queryWhereClause.append(records.get(i).getName());
                    queryWhereClause.append("=");
                    queryWhereClause.append("'").append(records.get(i).getValue()).append("'");
                }
            } else {
                queryWhereClause = null;
            }

            database = this.database.getWritableDatabase();
            if (queryWhereClause != null) {
                database.execSQL(new StringBuilder().append("DELETE FROM ").append(tableName).append(" WHERE ").append(queryWhereClause.toString()).toString());
            } else {
                database.execSQL(new StringBuilder().append("DELETE FROM ").append(tableName).toString());
            }

        } catch (Exception exception) {
            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if(database != null)
                database.close();
        }
    }

    //testear a ver si funciona así de abstracto o hay que hacerlo más especifico
    @Override
    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {
        SQLiteDatabase database = null;
        Cursor c = null;
        try {
            database = this.database.getReadableDatabase();
            c = database.rawQuery(new StringBuilder().append(" SELECT * from ").append(tableName).append(" WHERE pk=").append(pk).toString(), null);

            List<String> columns = getColumns(database);
            AndroidDatabaseRecord tableRecord1 = new AndroidDatabaseRecord();
            if (c.moveToFirst()) {

                for (int i = 0; i < columns.size(); ++i) {
                    DatabaseRecord recordValue = new AndroidRecord(
                            columns.get(i),
                            c.getString(c.getColumnIndex(columns.get(i))),
                            false
                    );
                    tableRecord1.addValue(recordValue);
                }

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
            c.close();
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        } finally {
            if (database != null)
                database.close();
            if (c != null)
                c.close();
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
    public List<DatabaseAggregateFunction> getTableAggregateFunction() {
        return tableAggregateFunction;
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
                        strOrder.append(tableOrder.get(i).getColumnName())
                                .append(" DESC ");
                        break;
                    case ASCENDING:
                        strOrder.append(tableOrder.get(i).getColumnName());
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
        if (strOrder.length() > 0) order = new StringBuilder().append(" ORDER BY ").append(order).toString();

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
            case NOT_EQUALS:
                strFilter.append(" <> '")
                        .append(filter.getValue())
                        .append("'");
                break;
            case GREATER_OR_EQUAL_THAN:
                strFilter.append(" >= ")
                        .append(filter.getValue());
                break;
            case GREATER_THAN:
                strFilter.append(" > ")
                        .append(filter.getValue());
                break;
            case LESS_OR_EQUAL_THAN:
                strFilter.append(" <= ")
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
            case STARTS_WITH:
                strFilter.append(" Like '")
                        .append(filter.getValue())
                        .append("%'");
                break;
            case ENDS_WITH:
                strFilter.append(" Like '%")
                        .append(filter.getValue())
                        .append("'");
                break;
            default:
                throw new RuntimeException("Database Filter Type not implemented yet. "+filter.getType());
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