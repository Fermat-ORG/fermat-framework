package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginDatabaseSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginFileSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.Os;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;
import com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system.AndroidPluginFileSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOs implements Os {

    PluginDatabaseSystem mPluginDatabaseSystem;
    PluginFileSystem mPluginFileSystem;
    Context mContext;

    @Override
    public PluginDatabaseSystem getPluginsDatabaseSystem() {
        return mPluginDatabaseSystem;
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
        mPluginFileSystem.setContext(context);
        mPluginDatabaseSystem.setContext(context);
    }

    @Override
    public PluginFileSystem getFileSystem() {
        return mPluginFileSystem;
    }


    public AndroidOs() {
        mPluginDatabaseSystem = new AndroidPluginDatabaseSystem(mContext);
        mPluginFileSystem = new AndroidPluginFileSystem();
    }

}
