package com.bitdubai.wallet_platform_api.layer._3_os.Database_System;

/**
 * Created by ciencias on 20.01.15.
 */

import com.bitdubai.wallet_platform_api.layer._3_os.Database_System.Database;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.CantOpenDatabaseException;

import java.util.UUID;

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface PluginDatabaseSystem {

    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException;
 
    public Database createDatabase (UUID ownerId, String databaseName);

    void setContext (Object context);

}
