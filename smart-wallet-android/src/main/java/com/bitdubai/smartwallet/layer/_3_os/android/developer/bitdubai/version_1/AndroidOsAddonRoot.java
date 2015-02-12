package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidPlatformFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PlatformFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.Database_System.PluginDatabaseSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.Os;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidPluginFileSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOsAddonRoot implements Os {

    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;
    Context mContext;

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }

    @Override
    public PlatformFileSystem getPlatformFileSystem() {
        return this.platformFileSystem;
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
        this.pluginFileSystem.setContext(context);
        this.pluginDatabaseSystem.setContext(context);
    }

    @Override
    public PluginFileSystem getPlugInFileSystem() {
        return this.pluginFileSystem;
    }


    public AndroidOsAddonRoot() {
        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem(mContext);
        this.pluginFileSystem = new AndroidPluginFileSystem();
        this.platformFileSystem = new AndroidPlatformFileSystem();
    }

}
