package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system;

import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer._2_os.database_system.DatabaseTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 26/03/2015.
 */
public class AndroidDatabaseTransaction implements DatabaseTransaction {

    /**
     * AndroidDatabaseTransaction Interface member variables.
     */

    private List<DatabaseTable> updateTables;
    private List<DatabaseTableRecord>  updateRecords;

    private List<DatabaseTable> insertTables;
    private List<DatabaseTableRecord>  insertRecords;

    /**
     * DatabaseTransaction interface implementation.
     */

    @Override
    public void addRecordToUpdate(DatabaseTable table, DatabaseTableRecord record){
        if(updateTables == null)
            updateTables = new ArrayList<DatabaseTable>();

        if(updateRecords == null)
            updateRecords = new ArrayList<DatabaseTableRecord>();

        updateTables.add(table);
        updateRecords.add(record);
    }

    @Override
    public void addRecordToInsert(DatabaseTable table, DatabaseTableRecord record){

        if(insertTables == null)
            insertTables = new ArrayList<DatabaseTable>();

        if(insertRecords == null)
            insertRecords = new ArrayList<DatabaseTableRecord>();

        insertTables.add(table);
        insertRecords.add(record);
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToUpdate(){
       return updateRecords;
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToInsert(){

        return insertRecords;
    }

    @Override
    public List<DatabaseTable> getTablesToUpdate(){
        return updateTables;
    }

    @Override
    public List<DatabaseTable> getTablesToInsert(){

        return insertTables;
    }

}
