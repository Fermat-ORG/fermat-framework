package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;

/**
 * <p>The abstract class <code>Database</code> is a interface
 * that define the methods to execute transactions an query on database.
 * Return too an Database Table object.
 *
 * @author Luis
 * @version 1.0.0
 * @since 22/01/15.
 */

public interface Database {

    void executeQuery(String query) throws CantExecuteQueryException;

    DatabaseTable getTable(String tableName);

    DatabaseTransaction newTransaction();

    @Deprecated
        // execute directly from the transaction object. @see DatabaseTransaction.execute() method.
    void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException;

    DatabaseFactory getDatabaseFactory();

    void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException;

    @Deprecated
        // is not used anymore
    void closeDatabase();
}
