package com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system.AndroidPlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.Os;
import com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;
import com.bitdubai.android.layer._2_os.android.developer.bitdubai.version_1.file_system.AndroidPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.location_system.LocationSystem;


/**
 * Created by ciencias on 20.01.15.
 */
public class AndroidOsAddonRoot implements Os {

    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;
    Context mContext;
    LocationSystem pluginLocationSystem;

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
        this.platformFileSystem.setContext(context);
    }

    @Override
    public PluginFileSystem getPlugInFileSystem() {
        return this.pluginFileSystem;
   }
    @Override
    public LocationSystem getLocationSystem(){
        return this.pluginLocationSystem;
    }

    public AndroidOsAddonRoot() {
        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem(mContext);
        this.pluginFileSystem = new AndroidPluginFileSystem();
        this.platformFileSystem = new AndroidPlatformFileSystem();
    }

}
