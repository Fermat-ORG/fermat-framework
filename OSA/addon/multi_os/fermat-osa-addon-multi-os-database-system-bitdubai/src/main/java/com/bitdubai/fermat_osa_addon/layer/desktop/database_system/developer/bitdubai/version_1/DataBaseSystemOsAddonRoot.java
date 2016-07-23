package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure.DesktopPlatformDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure.DesktopPluginDatabaseSystem;


/**
 * Created by Matias.
 */

/**
 * This addon handles a layer of database representation.
 * Encapsulates all the necessary functions to manage the database , its tables and records.
 * For interfaces PluginDatabase the modules need to authenticate with their plugin ids
 * * * *
 */

public class DataBaseSystemOsAddonRoot implements Addon, DataBaseSystemOs, Service {

    /**
     * DataBaseSystemOsAddonRoot member variables.
     */

    PlatformDatabaseSystem platformDatabaseSystem;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * Constructor
     */
    public DataBaseSystemOsAddonRoot() {

        this.pluginDatabaseSystem = new DesktopPluginDatabaseSystem();
        this.platformDatabaseSystem = new DesktopPlatformDatabaseSystem();
    }


    /**
     * DatBaseSystemOs Interface implementation.
     */
    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }

    @Override
    public PlatformDatabaseSystem getPlatformDatabaseSystem() {
        return this.platformDatabaseSystem;
    }


    /*
    *
    *   Method only available for Android OS
    */
    @Override
    public void setContext(Object context) {
        //this.pluginDatabaseSystem.setContext(context);
        //this.platformDatabaseSystem.setContext(context);
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
