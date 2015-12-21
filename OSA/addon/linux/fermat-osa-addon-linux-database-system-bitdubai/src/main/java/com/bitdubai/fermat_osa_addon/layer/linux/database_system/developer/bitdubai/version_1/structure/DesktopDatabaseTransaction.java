/*
* @#DesktopDatabaseTransaction.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.structure.DesktopDatabaseTransaction</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DesktopDatabaseTransaction implements DatabaseTransaction {

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

    /**
     *<p>This method add a table and a record to the update transaction
     *
     * @param table DatabaseTable object to modified
     * @param record DatabaseTableRecord object fields and values to modified
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

    /**
     * <p>This method add a table and a record to the update transaction
     *
     * @param table DatabaseTable object to inserted
     * @param record DatabaseTableRecord object fields and values to inserted
     */
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
    public void addRecordToSelect(DatabaseTable selectTable, DatabaseTableRecord selectRecord) {

    }

    /**
     * <p>This method gets list of DatabaseTableRecord object that will be modified
     *
     * @return List of DatabaseTableRecord object to update
     */
    @Override
    public List<DatabaseTableRecord> getRecordsToUpdate(){
        return updateRecords;
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToSelect() {
        return null;
    }

    /**
     *<p>This method gets list of DatabaseTableRecord object that will be inserted
     *
     * @return List of DatabaseTableRecord object to insert
     */
    @Override
    public List<DatabaseTableRecord> getRecordsToInsert(){

        return insertRecords;
    }

    /**
     *<p>This method gets list of DatabaseTable object that will be updated
     *
     * @return List of DatabaseTable to update
     */
    @Override
    public List<DatabaseTable> getTablesToUpdate(){
        return updateTables;
    }

    /**
     *<p>This method gets list of DatabaseTable object that will be inserted
     *
     * @return List of DatabaseTable to insert
     */
    @Override
    public List<DatabaseTable> getTablesToInsert(){

        return insertTables;
    }

    @Override
    public List<DatabaseTable> getTablesToSelect() {
        return null;
    }

}
