package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system.AndroidPlatformDatabaseSystem;
// NO COMPILA (LUI) import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_location.AndroidLocationManager;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.file_system.AndroidPlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.device_location.LocationManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.Os;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.database_system.AndroidPluginDatabaseSystem;
import com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.file_system.AndroidPluginFileSystem;
import com.bitdubai.fermat_api.layer._2_os.location_system.LocationSystem;


/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This Addon provides access to Android dependent functionality, like the File System and Database System.
 */

public class AndroidOsAddonRoot implements Os {

    /**
     * Os interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;
    PluginDatabaseSystem pluginDatabaseSystem;
    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;
    LocationSystem pluginLocationSystem;
    LocationManager pluginLocationManager;


    Context context;

    public AndroidOsAddonRoot() {

        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem();
        this.pluginFileSystem = new AndroidPluginFileSystem();
        this.platformFileSystem = new AndroidPlatformFileSystem();
        this.platformDatabaseSystem = new AndroidPlatformDatabaseSystem();
// NO COMPILA (LUI)         this.pluginLocationManager = new AndroidLocationManager();

    }

    /**
     * Os interface implementation.
     */

    @Override
    public PluginFileSystem getPlugInFileSystem() {
        return this.pluginFileSystem;
    }

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }


    @Override
    public PlatformDatabaseSystem getPlatfotmDatabaseSystem(){
        return this.platformDatabaseSystem;
    }

    @Override
    public PlatformFileSystem getPlatformFileSystem() {
        return this.platformFileSystem;
    }

    @Override
    public LocationSystem getLocationSystem(){
        return this.pluginLocationSystem;
    }

    @Override
    public LocationManager getLocationManager(){
        return this.pluginLocationManager;
    }

    @Override
    public void setContext(Object context) {

        this.context = (Context) context;

        this.pluginFileSystem.setContext(context);
        this.pluginDatabaseSystem.setContext(context);
        this.platformFileSystem.setContext(context);
        this.platformDatabaseSystem.setContext(context);
// NO COMPILA (LUIS)        this.pluginLocationManager.setContext(context);
    }



}
