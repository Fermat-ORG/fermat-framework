package com.bitdubai.fermat_api.layer._3_os;

import com.bitdubai.fermat_api.layer._3_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._3_os.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer._3_os.location_system.LocationSystem;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    public PluginFileSystem getPlugInFileSystem();

    public PluginDatabaseSystem getPluginDatabaseSystem();

    public PlatformFileSystem getPlatformFileSystem();

    public LocationSystem getLocationSystem();
            
    void setContext (Object context);

}
