package com.bitdubai.fermat_api.layer._2_os.database_system;

import java.util.List;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTable {
    
    //public List<TableColumn> getColumns();

    public List<String> getColumns();

    public List<DatabaseTableRecord> getRecords();

    public DatabaseTableRecord getEmptyRecord();
    
    public void addFilter (DatabaseTableFilter filter);
    
    public void clearAllFilters();
    
    public List<DatabaseTableFilter> getFilters();
    
    public void updateRecord (DatabaseTableRecord record);

    public void insertRecord (DatabaseTableRecord record);

}
