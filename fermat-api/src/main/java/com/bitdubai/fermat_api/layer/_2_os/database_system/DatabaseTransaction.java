package com.bitdubai.fermat_api.layer._2_os.database_system;

/**
 * Created by ciencias on 3/24/15.
 */
public interface DatabaseTransaction {

    public void addRecordToUpdate(DatabaseTable fromTable, DatabaseTableRecord fromRecord);

    public void addRecordToInsert(DatabaseTable transfersTable, DatabaseTableRecord transferRecord);
}
