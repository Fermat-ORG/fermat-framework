package com.bitdubai.fermat_api.layer.osa_android.database_system;

import java.util.List;
import java.util.UUID;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;

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
    
    List<DatabaseTableRecord> customQuery(String query, boolean customResult) throws CantLoadTableToMemoryException;

    DatabaseTableColumn newColumn();

    List<DatabaseTableRecord> getRecords();

    DatabaseTableRecord getEmptyRecord();

    void clearAllFilters();
    
    List<DatabaseTableFilter> getFilters();

    DatabaseTableFilterGroup getFilterGroup();

    DatabaseTableFilter getEmptyTableFilter();

    DatabaseTableFilter getNewFilter(String column, DatabaseFilterType type, String value);

    DatabaseTableFilterGroup getNewFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator);

    void updateRecord (DatabaseTableRecord record) throws CantUpdateRecordException;

    void insertRecord (DatabaseTableRecord record) throws CantInsertRecordException;

    void loadToMemory() throws CantLoadTableToMemoryException;

    boolean isTableExists();

    void setStringFilter(String columnName, String value,DatabaseFilterType type);

    void setFermatEnumFilter(String columnName, FermatEnum value,DatabaseFilterType type);

    void setFilterGroup(DatabaseTableFilterGroup filterGroup);

    void setFilterGroup(List<DatabaseTableFilter> tableFilters, List<DatabaseTableFilterGroup> filterGroups, DatabaseFilterOperator filterOperator);

    void setUUIDFilter(String columnName, UUID value,DatabaseFilterType type);

    void setFilterOrder(String columnName, DatabaseFilterOrder direction);

    void setFilterTop(String top);

    void setFilterOffSet(String offset);

    void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias);

    void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException;

    DatabaseTableRecord getRecordFromPk(String pk) throws Exception;

    // modif leon
    String makeFilter();
    String getTableName();
    List<DatabaseSelectOperator> getTableSelectOperator();

}
