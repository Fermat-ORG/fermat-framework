package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    PluginFileSystem getFileSystem();
    PluginDatabaseSystem getPluginsDatabaseSystem();
    
    
    
    void setContext (Object context);

}
