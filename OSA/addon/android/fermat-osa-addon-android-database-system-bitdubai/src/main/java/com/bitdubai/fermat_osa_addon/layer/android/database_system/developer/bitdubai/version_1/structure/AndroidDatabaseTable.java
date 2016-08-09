package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantTruncateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseRecordExistException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * Created by Natalia on 09/02/2015..
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 * Modified by Matias Furszyfer
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
    private String tableName;
    private AndroidDatabase database;

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
        Cursor c = database.rawQuery("SELECT * FROM " + tableName, null);
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
            ContentValues contentValues =  new ContentValues();
            for (int i = 0; i < records.size(); ++i) {
                DatabaseRecord androidRecord = records.get(i);
                if (androidRecord.isChange()) {
                    contentValues.put(androidRecord.getName(),androidRecord.getValue());
                }
            }
            database = this.database.getWritableDatabase();
            String filter = makeFilter2();
            Log.i("AndroidDatabase","Database name:"+tableName+" update quantity: "+database.update(tableName, contentValues,filter , null));
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
        if (record==null) throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, new Exception("Record null"), null, "Check the cause for this error");
        SQLiteDatabase database = null;
        try {
            List<DatabaseRecord> records = record.getValues();
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < records.size(); ++i) {
                DatabaseRecord databaseRecord = records.get(i);
                contentValues.put(databaseRecord.getName(), databaseRecord.getValue());
            }
            database = this.database.getWritableDatabase();
            Log.i("AndroidDatabase", "Database name:" + tableName + " insert id: " + database.insert(tableName, null, contentValues));
//            database.execSQL("INSERT INTO " + tableName + "(" + strRecords + ")" + " VALUES (" + strValues + ")");
        } catch (Exception exception) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if (database != null) database.close();
        }
    }

    @Override
    public void insertRecordIfNotExist(DatabaseTableRecord record,List<DatabaseTableFilter> filters,DatabaseTableFilterGroup databaseTableFilterGroup) throws DatabaseRecordExistException, CantInsertRecordException {
        if (record==null) throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, new Exception("Record null"), null, "Check the cause for this error");
        SQLiteDatabase database = null;
        try {
            database = this.database.getWritableDatabase();
            if (numRecords(database,makeFilter(filters,databaseTableFilterGroup))>0)throw new DatabaseRecordExistException("DatabaseTableRecord: "+record.toString()+" exist.");
            List<DatabaseRecord> records = record.getValues();
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < records.size(); ++i) {
                DatabaseRecord databaseRecord = records.get(i);
                contentValues.put(databaseRecord.getName(), databaseRecord.getValue());
            }
            Log.i("AndroidDatabase", "Database name:" + tableName + " insert id: " + database.insertOrThrow(tableName, null, contentValues));
        } catch (DatabaseNotFoundException e) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "Check the cause for this error");
        } catch (CantOpenDatabaseException e) {
            throw new CantInsertRecordException(CantInsertRecordException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "Check the cause for this error");
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public long numRecords() {
        return numRecords(null,makeFilter2());
    }

    private long numRecords(SQLiteDatabase openDatabase,String filterSelection) {
        boolean databaseOpen = openDatabase!=null;
        long num = -1;
        try {
            if (!databaseOpen)openDatabase = this.database.getReadableDatabase();
            num  = DatabaseUtils.queryNumEntries(openDatabase, tableName, filterSelection);
        } catch (CantOpenDatabaseException e) {
            e.printStackTrace();
        } catch (DatabaseNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!databaseOpen && openDatabase!=null) {
                openDatabase.close();
            }
        }
        return num;
    }

    @Override
    public void truncate() throws CantTruncateTableException {
        SQLiteDatabase database = null;
        try {
            database = this.database.getWritableDatabase();

//            database.execSQL("DELETE FROM " + tableName);
            Log.i("AndroidDatabase", "Truncate table: "+tableName+", records quantity: " + database.delete(tableName, null, null));
        } catch (Exception exception) {

            throw new CantTruncateTableException(
                    exception,
                    null,
                    "Check the cause for this error"
            );
        } finally {
            if (database != null) {
                database.close();
            }
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
            String queryString = "SELECT *" + makeOutputColumns() + " FROM " + tableName + makeFilter() + makeOrder() + topSentence + offsetSentence;
            cursor = database.rawQuery(queryString, null);
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
        }
    }

    @Override
    public List<DatabaseTableRecord> loadRecords(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> databaseTableFilterGroups, String[] columns) throws CantLoadTableToMemoryException{

        List<DatabaseTableRecord> records = new ArrayList<>();
        StringBuilder queryString = new StringBuilder("");
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
            if(columns==null) {
                List<String> columnsNames = getColumns(database);
                columns = columnsNames.toArray(new String[columnsNames.size()]);
            }
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

                records.add(tableRecord);
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
        return records;
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
                                "Column" + i,
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
    public DatabaseTableFilter buildFilter(String columnName, String value, DatabaseFilterType type){
        return  new AndroidDatabaseTableFilter(
                columnName,
                type      ,
                value
        );
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


    public String makeFilter2() {
        return makeFilter(this.tableFilter,this.tableFilterGroup);
    }

    public String makeFilter(List<DatabaseTableFilter> tableFilter,DatabaseTableFilterGroup tableFilterGroup) {
        // I check the definition for the filter object, filter type, filter columns names
        // and build the WHERE statement
        String filter = "";
        StringBuilder strFilter = new StringBuilder();
        if (tableFilter != null) {
            for (int i = 0; i < tableFilter.size(); ++i) {

                strFilter.append(makeInternalCondition(tableFilter.get(i)));

                if (i < tableFilter.size() - 1)
                    strFilter.append(" AND ");
            }
            filter = strFilter.toString();
            return filter;
        } else {
            //if set group filter
            if (tableFilterGroup != null) {
                return makeGroupFilters2(tableFilterGroup);
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

    public String makeGroupFilters2(DatabaseTableFilterGroup databaseTableFilterGroup) {

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

        return strFilter.toString();
    }

    public String makeOutputColumns() {

        if (tableAggregateFunction != null) {

            String filter = ", ";
            for (DatabaseAggregateFunction AggregateFunction : tableAggregateFunction) {
                filter += AggregateFunction.toSQLQuery() + ", ";
            }

            return filter.substring(0, filter.length() - 2);
        }

        else
            return "";
    }

    @Override
    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException {
        if(record==null) throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, new InvalidParameterException("Record null"), null, "Check the cause for this error");;
        SQLiteDatabase database = null;
        try {
             database = this.database.getWritableDatabase();
            String filter = makeFilter2();
            int rowDeleted =  database.delete(tableName, (!filter.isEmpty()) ? filter : null, null);
            Log.i("AndroidDatabase", "Database name:" + tableName + " delete id: " +rowDeleted);

        } catch (Exception exception) {
            throw new CantDeleteRecordException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Check the cause for this error");
        } finally {
            if(database != null)
                database.close();
        }
    }

    public void deleteRecordOld(DatabaseTableRecord record) throws CantDeleteRecordException {
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

    //testear a ver si funciona así de abstracto o hay que hacerlo más especifico
    @Override
    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception {
        SQLiteDatabase database = null;
        try {
            database = this.database.getReadableDatabase();
            Cursor c = database.rawQuery(" SELECT * from " + tableName + " WHERE pk=" + pk, null);

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

    public long recordsSize() throws Exception{
        SQLiteDatabase db = null;
        try{
            db = this.database.getReadableDatabase();
            return DatabaseUtils.queryNumEntries(db, tableName);
        } catch (DatabaseNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (CantOpenDatabaseException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if (db != null) {
                db.close();
            }
        }
    }

}

