package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;

/**
 *
 *  <p>The abstract class <code>Database</code> is a interface
 *     that define the methods to execute transactions an query on database.
 *     Return too an Database Table object.
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   22/01/15.
 * */

 public interface Database {

    public void executeQuery();

    public DatabaseTable getTable(String tableName);

    public DatabaseTransaction newTransaction();

    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException;

    public DatabaseFactory getDatabaseFactory();

    public void openDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException;

    public void closeDatabase();
}
