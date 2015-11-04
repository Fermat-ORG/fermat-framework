package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

/**
 * Created by toshiba on 20/05/2015.
 */
public class AndroidOsDataBaseSystem implements DataBaseSystemOs {
    /**
     * DataBaseSystemOs interface member variables.
     */

    private final PlatformDatabaseSystem platformDatabaseSystem;
    private final PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     */

    public AndroidOsDataBaseSystem(String path) {
        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem(path);
        this.platformDatabaseSystem = new AndroidPlatformDatabaseSystem(path);
    }


    /**
     * DatBaseSystemOs Interface implementation.
     */

    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }


    public PlatformDatabaseSystem getPlatformDatabaseSystem() {
        return this.platformDatabaseSystem;
    }


}
