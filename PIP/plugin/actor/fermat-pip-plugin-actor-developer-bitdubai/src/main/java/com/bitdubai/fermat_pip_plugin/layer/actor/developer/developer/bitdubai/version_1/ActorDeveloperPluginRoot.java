package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetDataBaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure.DeveloperActorDatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure.DeveloperActorLogTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */

/**
 * The User Manager knows the users managed by the current device.
 *
 * It is responsible for login in users to the current device.
 */

public class ActorDeveloperPluginRoot implements DealWithDatabaseManagers, DealsWithLogManagers, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, ToolManager, Service, Plugin {


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();


    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
    * DeviceUser Interface member variables
    */
    private UUID pluginId;

    //Esto seria lo nuevo
    private Map<Plugins,Plugin> databaseManagersOnPlugins;
    private Map<Plugins,Plugin> logManagersOnPlugins;
    private Map<Addons,Addon> databaseManagersOnAddons;
    private Map<Addons,Addon> logManagersOnAddons;

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {        this.serviceStatus = ServiceStatus.STARTED;
        if(errorManager == null)
            throw new IllegalArgumentException();
        this.errorManager = errorManager;
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
        return this.serviceStatus;
    }


    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem=pluginFileSystem;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public DatabaseTool getDatabaseTool() throws CantGetDataBaseTool {
        try
        {
            return new DeveloperActorDatabaseTool(this.databaseManagersOnPlugins,this.databaseManagersOnAddons);
        }
        catch (Exception e)
        {
            throw new CantGetDataBaseTool(CantGetDataBaseTool.DEFAULT_MESSAGE ,e, " Error get DeveloperActorDatabaseTool object","");
        }

    }

    @Override
    public LogTool getLogTool() throws CantGetLogTool {
        try
        {
            return new DeveloperActorLogTool(logManagersOnPlugins,logManagersOnAddons);
        }
        catch(Exception e)
        {
            throw new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE ,e, " Error get DeveloperActorLogTool object","");

        }

    }


    @Override
    public void setDatabaseManagers(Map<Plugins,Plugin> databaseManagersOnPlugins,Map<Addons,Addon> databaseManagersOnAddons) {
        this.databaseManagersOnPlugins = databaseManagersOnPlugins;
        this.databaseManagersOnAddons = databaseManagersOnAddons;
    }

    @Override
    public void setLogManagers(Map<Plugins,Plugin> logManagersOnPlugins,Map<Addons,Addon> logManagersOnAddons) {
        this.logManagersOnPlugins = logManagersOnPlugins;
        this.logManagersOnAddons = logManagersOnAddons;
    }


}
