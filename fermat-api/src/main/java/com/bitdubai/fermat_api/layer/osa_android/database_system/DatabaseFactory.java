package com.bitdubai.fermat_api.layer.osa_android.database_system;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.osa_android.database_system</code> is a interface
 *     that define the methods to  provides the caller with a Table Structure object.
 *
 *
 *
 *  @author  Luis
 *  @version 1.0.0
 *  @since   23/03/15.
 * */
public interface DatabaseFactory {

    public void createTable(UUID ownerId, DatabaseTableFactory tableFactory) throws InvalidOwnerIdException, CantCreateTableException;

    public DatabaseTableFactory newTableFactory(UUID ownerId, String tableName) throws InvalidOwnerIdException;

    public void createTable(DatabaseTableFactory tableFactory) throws CantCreateTableException;

    public DatabaseTableFactory newTableFactory(String tableName);

    public void createDatabase(String databaseName) throws CantCreateDatabaseException;

    
    

}
