package com.bitdubai.wallet_platform_api.layer._3_os;

import javax.swing.table.TableColumn;
import java.util.List;

/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTable {
    
    public List<TableColumn> getColumns();
    
    public List<DatabaseTableRecord> getRecords();

    public DatabaseTableRecord getEmptyRecord();
    
    public void addFilter (DatabaseTableFilter filter);
    
    public void clearAllFilters();
    
    public List<DatabaseTableFilter> getFilters();
    
    public void updateRecord (DatabaseTableRecord record);

    public void insertRecord (DatabaseTableRecord record);

}
