package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

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
