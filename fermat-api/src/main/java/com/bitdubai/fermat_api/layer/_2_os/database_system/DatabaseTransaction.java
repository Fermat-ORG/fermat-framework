package com.bitdubai.fermat_api.layer._2_os.database_system;

import java.util.List;

/**
 * Created by ciencias on 3/24/15.
 */
public interface DatabaseTransaction {

    public void addRecordToUpdate(DatabaseTable fromTable, DatabaseTableRecord fromRecord);

    public void addRecordToInsert(DatabaseTable transfersTable, DatabaseTableRecord transferRecord);

    public List<DatabaseTableRecord> getRecordsToUpdate();

    public List<DatabaseTableRecord> getRecordsToInsert();

    public List<DatabaseTable> getTablesToUpdate();

    public List<DatabaseTable> getTablesToInsert();
}
