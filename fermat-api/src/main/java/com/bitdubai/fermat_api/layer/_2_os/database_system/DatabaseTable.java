package com.bitdubai.fermat_api.layer._2_os.database_system;

import java.util.List;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.*;
/**
 * Created by ciencias on 01.02.15.
 */
public interface DatabaseTable {
    

    public DatabaseTableColumn newColumn(String name);

    public List<String> getColumns();

    public List<DatabaseTableRecord> getRecords();

    public DatabaseTableRecord getEmptyRecord();
    
    public void addFilter (DatabaseTableFilter filter);
    
    public void clearAllFilters();
    
    public List<DatabaseTableFilter> getFilters();
    
    public void updateRecord (DatabaseTableRecord record) throws CantNotUpdateRecord;

    public void insertRecord (DatabaseTableRecord record) throws CantNotInsertRecord;

    public void loadToMemory() throws CantNotLoadTableToMemory;



}
