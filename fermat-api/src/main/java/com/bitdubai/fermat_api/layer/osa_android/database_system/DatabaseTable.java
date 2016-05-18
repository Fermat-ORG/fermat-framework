package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

import java.util.List;
import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>DatabaseTable</code> is a interface
 *     that define the methods to manage a DatabaseTable object. Set filters and orders, and load records to memory.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since  01/01/15.
 * */
public interface DatabaseTable {

    void loadToMemory() throws CantLoadTableToMemoryException;

    long getCount() throws CantLoadTableToMemoryException;

    List<DatabaseTableRecord> getRecords();

    void insertRecord (DatabaseTableRecord record) throws CantInsertRecordException;

    void updateRecord (DatabaseTableRecord record) throws CantUpdateRecordException;

    void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException;

    DatabaseTableRecord getEmptyRecord();

    boolean isTableExists();

    List<DatabaseTableRecord> customQuery(String query, boolean customResult) throws CantLoadTableToMemoryException;

    DatabaseTableFilter getEmptyTableFilter();

    DatabaseTableFilter getNewFilter(String column, DatabaseFilterType type, String value);

    DatabaseTableFilterGroup getNewFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator);

    void setFilterTop(String top);

    void setFilterOffSet(String offset);

    void addStringFilter(String columnName, String value, DatabaseFilterType type);

    void addFermatEnumFilter(String columnName, FermatEnum value, DatabaseFilterType type);

    void addFilterOrder(String columnName, DatabaseFilterOrder direction);

    void addUUIDFilter(String columnName, UUID value, DatabaseFilterType type);

    void addAggregateFunction(String columnName, DataBaseAggregateFunctionType operator, String alias);

    void setFilterGroup(DatabaseTableFilterGroup filterGroup);

    void setFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator);

    void clearAllFilters();

    @Deprecated // try to not use this when you're updating records. android database needs filters to update records.
    DatabaseTableRecord getRecordFromPk(String pk) throws Exception;

    // todo try to substract this method from here, they don't belong
    String makeFilter();
    String getTableName();
    List<DatabaseAggregateFunction> getTableAggregateFunction();

}
