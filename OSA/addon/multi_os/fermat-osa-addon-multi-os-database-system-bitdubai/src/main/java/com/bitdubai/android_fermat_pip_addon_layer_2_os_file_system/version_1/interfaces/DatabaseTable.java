package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantInsertRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantUpdateRecord;

import java.util.List;
import java.util.UUID;

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

    public void updateRecord(DatabaseTableRecord record) throws CantUpdateRecord;

    public void insertRecord(DatabaseTableRecord record) throws CantInsertRecord;

    public void loadToMemory() throws CantLoadTableToMemory;

    public boolean isTableExists();

    public void setStringFilter(String columnName, String value, DatabaseFilterType type);

    public void setUUIDFilter(String columnName, UUID value, DatabaseFilterType type);

    public void setFilterOrder(String columnName, DatabaseFilterOrder direction);

    public void setFilterTop(String top);


}
