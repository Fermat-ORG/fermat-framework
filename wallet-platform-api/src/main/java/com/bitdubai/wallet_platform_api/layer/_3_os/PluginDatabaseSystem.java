package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 20.01.15.
 */

import java.util.UUID;

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface PluginDatabaseSystem {

    public PluginDatabase openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException;
 
    public PluginDatabase createDatabase (UUID ownerId, String databaseName);

    void setContext (Object context);

}
