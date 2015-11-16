package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealWithDatabaseManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetLogToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.LogTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure.DeveloperModuleDatabaseTool;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.version_1.structure.DeveloperModuleLogTool;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperSubAppModulePluginRoot extends AbstractPlugin implements DealWithDatabaseManagers, DealsWithLogManagers, ToolManager {

    // TODO PLEASE MAKE USE OF THE ERROR MANAGER.

    public DeveloperSubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    //Esto seria lo nuevo
    private Map<Plugins,Plugin> databaseManagersOnPlugins;
    private Map<Plugins,Plugin> logManagersOnPlugins;
    private Map<Addons,Addon> databaseManagersOnAddons;
    private Map<Addons,Addon> logManagersOnAddons;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setDatabaseManagers(Map<Plugins, Plugin> databaseManagersOnPlugins, Map<Addons, Addon> databaseManagersOnAddons) {
        this.databaseManagersOnPlugins = databaseManagersOnPlugins;
        this.databaseManagersOnAddons = databaseManagersOnAddons;
    }

    @Override
    public void setLogManagers(Map<Plugins, Plugin> logManagersOnPlugins, Map<Addons, Addon> logManagersOnAddons) {
        this.logManagersOnPlugins = logManagersOnPlugins;
        this.logManagersOnAddons = logManagersOnAddons;
    }

    @Override
    public DatabaseTool getDatabaseTool() throws CantGetDataBaseToolException {
        try {
            return new DeveloperModuleDatabaseTool(this.databaseManagersOnPlugins,this.databaseManagersOnAddons);
        } catch (Exception e) {
            throw new CantGetDataBaseToolException(CantGetDataBaseToolException.DEFAULT_MESSAGE ,e, " Error get DeveloperActorDatabaseTool object","");
        }
    }

    @Override
    public LogTool getLogTool() throws CantGetLogToolException {
        try {
            return new DeveloperModuleLogTool(logManagersOnPlugins,logManagersOnAddons);
        } catch(Exception e) {
            throw new CantGetLogToolException(CantGetLogToolException.DEFAULT_MESSAGE ,e, " Error get DeveloperActorLogTool object","");

        }
    }
}
