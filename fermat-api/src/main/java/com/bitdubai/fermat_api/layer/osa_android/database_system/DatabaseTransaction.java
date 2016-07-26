package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;

import java.util.List;


/**
 * <p>The abstract class <code>DatabaseTransaction</code> is a interface
 * that define the methods to manage insert and update transactions on the database.
 *
 * @author Luis
 * @version 1.0.0
 * @since 24/03/15.
 */

public interface DatabaseTransaction {

    void execute() throws DatabaseTransactionFailedException;

    void addRecordToUpdate(DatabaseTable fromTable, DatabaseTableRecord fromRecord);

    void addRecordToInsert(DatabaseTable transfersTable, DatabaseTableRecord transferRecord);

    void addRecordToSelect(DatabaseTable selectTable, DatabaseTableRecord selectRecord);

    void addRecordToDelete(DatabaseTable selectTable, DatabaseTableRecord deleteRecord);

    List<DatabaseTableRecord> getRecordsToUpdate();

    List<DatabaseTableRecord> getRecordsToSelect();

    List<DatabaseTableRecord> getRecordsToInsert();

    List<DatabaseTable> getTablesToUpdate();

    List<DatabaseTable> getTablesToInsert();

    List<DatabaseTable> getTablesToSelect();

}
