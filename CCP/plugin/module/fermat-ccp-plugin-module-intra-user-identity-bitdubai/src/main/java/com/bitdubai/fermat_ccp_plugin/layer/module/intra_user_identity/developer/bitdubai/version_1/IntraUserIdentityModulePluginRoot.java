package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.actor.exception.CantGetLogTool;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This plug-in provides the methods for the Intra Users Identity sub app.
 * To manage Intra User information and intra users connections
 * Created by Natalia Cortez on 05/01/16.
 *
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

@PluginInfo(createdBy = "Natalia Cortez", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.INTRA_IDENTITY_USER)

public class IntraUserIdentityModulePluginRoot extends AbstractModule implements
        LogManagerForDevelopers{


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    public IntraUserIdentityModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("IntraUserIdentityModulePluginRoot");
//        returnedClasses.add("com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.version_1.structure.IntraUserIdentityModule");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (IntraUserIdentityModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraUserIdentityModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraUserIdentityModulePluginRoot.newLoggingLevel, "Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.INTRA_WALLET_USER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

    }


    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;



        } catch (Exception e) {
            throw new CantStartPluginException("Unknown Error", e);
        }

    }

    /**
     * IntraUserIdentityModuleManagerInterface implementation.
     */

    @Override
    public ModuleManager getModuleManager() throws CantGetModuleManagerException {
        return new IntraUserIdentityModuleManager(pluginId,errorManager,pluginFileSystem,intraWalletUserIdentityManager, locationManager);
    }
}
