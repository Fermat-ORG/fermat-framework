package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPluginDatabaseSystem;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.io.Serializable;


/**
 * Created by Natalia on 18/05/2015.
 */

/**
 * This addon handles a layer of database representation.
 * Encapsulates all the necessary functions to manage the database , its tables and records.
 * For interfaces PluginDatabase the modules need to authenticate with their plugin ids
 * * * *
 */

public class DataBaseSystemAndroidAddonRoot implements Addon,DataBaseSystemOs,Service,Serializable {

    /**
     * DataBaseSystemAndroidAddonRoot member variables.
     */

    PlatformDatabaseSystem platformDatabaseSystem;
    PluginDatabaseSystem pluginDatabaseSystem;
    Context context;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * Constructor
     */
    public DataBaseSystemAndroidAddonRoot() {

        this.pluginDatabaseSystem = new AndroidPluginDatabaseSystem("");
        this.platformDatabaseSystem = new AndroidPlatformDatabaseSystem("");
    }


    /**
     * DatBaseSystemOs Interface implementation.
     */
    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }

    @Override
    public PlatformDatabaseSystem getPlatformDatabaseSystem(){
        return this.platformDatabaseSystem;
    }

    public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	/**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }



    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

}
