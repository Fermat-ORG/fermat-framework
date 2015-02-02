package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    PluginFileSystem getPlugInFileSystem();
    
    PluginDatabaseSystem getPluginDatabaseSystem();

    PlatformFileSystem getPlatformFileSystem();
            
    void setContext (Object context);

}
