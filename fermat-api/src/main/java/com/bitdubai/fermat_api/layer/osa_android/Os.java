package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    public PluginFileSystem getPlugInFileSystem();

    public PluginDatabaseSystem getPluginDatabaseSystem();

    public PlatformDatabaseSystem getPlatfotmDatabaseSystem();

    public PlatformFileSystem getPlatformFileSystem();

    public LocationSystem getLocationSystem();
            
    void setContext (Object context);

}
