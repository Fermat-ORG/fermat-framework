package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * Created by ciencias on 3/23/15.
 */
public interface DatabaseFactory {

    public void createTable(UUID ownerId, DatabaseTableFactory tableFactory) throws InvalidOwnerId, CantCreateTableException;

    public DatabaseTableFactory newTableFactory(UUID ownerId, String tableName) throws InvalidOwnerId;

    public void createTable(DatabaseTableFactory tableFactory) throws InvalidOwnerId, CantCreateTableException;

    public DatabaseTableFactory newTableFactory(String tableName) throws InvalidOwnerId;

    public void createDatabase(String databaseName) throws CantCreateDatabaseException;


}
