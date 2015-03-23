package com.bitdubai.fermat_api.layer._2_os.database_system;

/**
 * Created by ciencias on 20.01.15.
 */

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer._2_os.file_system.exceptions.CantOpenDatabaseException;

import java.util.UUID;

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface PluginDatabaseSystem {

    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException;
 
    public Database createDatabase (UUID ownerId, String databaseName);

    void setContext (Object context);

}
