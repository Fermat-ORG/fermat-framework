package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface DatabaseSystem {
    public Database getDatabase (String databaseName);

    public Database createDatabase (String databaseName, String databaseSchema);
    public Database createDatabase (String databaseName);

    void setContext (Object context);

}
