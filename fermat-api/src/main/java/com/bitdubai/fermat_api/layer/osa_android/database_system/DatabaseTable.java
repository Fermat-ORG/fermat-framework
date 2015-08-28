package com.bitdubai.fermat_api.layer.osa_android.database_system;

import java.util.List;
import java.util.UUID;


import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantSelectRecordException;
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
    

    public DatabaseTableColumn newColumn();

    public List<DatabaseTableRecord> getRecords();

    public DatabaseTableRecord getEmptyRecord();

    public void clearAllFilters();
    
    public List<DatabaseTableFilter> getFilters();

    public DatabaseTableFilterGroup getFilterGroup();

    public DatabaseTableFilter getEmptyTableFilter();

    public DatabaseTableFilterGroup getEmptyTableFilterGroup();

    public void updateRecord (DatabaseTableRecord record) throws CantUpdateRecordException;

    public void insertRecord (DatabaseTableRecord record) throws CantInsertRecordException;

    public void loadToMemory() throws CantLoadTableToMemoryException;

    public boolean isTableExists();

    public void setStringFilter(String columnName, String value,DatabaseFilterType type);

    public void setFilterGroup(List<DatabaseTableFilter> filters, List<DatabaseTableFilterGroup> subGroups, DatabaseFilterOperator type);

    public void setUUIDFilter(String columnName, UUID value,DatabaseFilterType type);

    public void setStateFilter(String columName, WalletFactoryProjectState walletFactoryProjectState,DatabaseFilterType type);

    public void setFilterOrder(String columnName, DatabaseFilterOrder direction);

    public void setFilterTop(String top);

    public void setFilterOffSet(String offset);

    public void setSelectOperator(String columnName, DataBaseSelectOperatorType operator, String alias);

    public void deleteRecord(DatabaseTableRecord record) throws CantDeleteRecordException;

    public DatabaseTableRecord getRecordFromPk(String pk) throws Exception;

    // modif leon
    String makeFilter();
    String getTableName();
    List<DatabaseSelectOperator> getTableSelectOperator();

}
