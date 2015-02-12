package com.bitdubai.wallet_platform_api.layer._3_os;

import com.bitdubai.wallet_platform_api.layer._3_os.Database_System.PluginDatabaseSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PlatformFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    PluginFileSystem getPlugInFileSystem();
    
    PluginDatabaseSystem getPluginDatabaseSystem();

    PlatformFileSystem getPlatformFileSystem();
            
    void setContext (Object context);

}
