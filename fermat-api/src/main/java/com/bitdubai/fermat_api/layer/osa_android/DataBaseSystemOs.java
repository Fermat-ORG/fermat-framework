package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;


/**
 * Created by Natalia on 20/05/2015.
 */
public interface DataBaseSystemOs {

    PluginDatabaseSystem getPluginDatabaseSystem();

    PlatformDatabaseSystem getPlatformDatabaseSystem();

}
