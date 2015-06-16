package com.bitdubai.fermat_api.layer.osa_android.database_system;

import java.util.List;


/**
 *
 *  <p>The abstract class <code>DatabaseTransaction</code> is a interface
 *     that define the methods to manage insert and update transactions on the database.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   24/03/15.
 * */

 public interface DatabaseTransaction {

    public void addRecordToUpdate(DatabaseTable fromTable, DatabaseTableRecord fromRecord);

    public void addRecordToInsert(DatabaseTable transfersTable, DatabaseTableRecord transferRecord);

    public List<DatabaseTableRecord> getRecordsToUpdate();

    public List<DatabaseTableRecord> getRecordsToInsert();

    public List<DatabaseTable> getTablesToUpdate();

    public List<DatabaseTable> getTablesToInsert();
}
