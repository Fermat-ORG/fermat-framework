package com.bitdubai.fermat_api.layer._2_os.database_system;

import java.util.List;
import java.util.UUID;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;
/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTable {
    

    public DatabaseTableColumn newColumn(String name);

    public List<String> getColumns();

    public List<DatabaseTableRecord> getRecords();

    public DatabaseTableRecord getEmptyRecord();

    public void clearAllFilters();
    
    public List<DatabaseTableFilter> getFilters();
    
    public void updateRecord (DatabaseTableRecord record) throws CantUpdateRecord;

    public void insertRecord (DatabaseTableRecord record) throws CantInsertRecord;

    public void loadToMemory() throws CantLoadTableToMemory;

    public boolean isTableExists();

    public void setStringFilter(String columnName, String value,DatabaseFilterType type);

    public void setUUIDFilter(String columnName, UUID value,DatabaseFilterType type);

    public void setFilterOrder(String columnName, DatabaseFilterOrder direction);

    public void setFilterTop(String top);



}
