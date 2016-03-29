package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ConnectionDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemDataDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * This plugin is designed to look up for the resources needed by a newly installed wallet. We are talking about the
 * navigation structure, plus the images needed by the wallet to be able to run.
 * <p/>
 * It will try to gather those resources from other peers or a centralized location provided by the wallet developer
 * if it is not possible.
 * <p/>
 * It will also serve other peers with these resources when needed.
 * <p/>
 *
 * Created by Matias Furszyfer on 17/02/15.
 */
public class SubAppFermatMonitorNetworkServicePluginRoot extends AbstractPlugin{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM      , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API   , layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    /**
     * Dealing with the repository database
     */
    ServiceDAO subAppFermatMonitorServiceDAO;
    ConnectionDAO subAppFermatMonitorConnectionDAO;
    SystemDataDAO subAppFermatMonitorSystemDataDAO;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public SubAppFermatMonitorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            /**
             *  Initialize database clases
             */

//            subAppFermatMonitorServiceDAO = new ServiceDAO((Database) pluginDatabaseSystem);
//            subAppFermatMonitorServiceDAO.initializeDatabase(pluginId,SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);
//
//
//            subAppFermatMonitorConnectionDAO = new ConnectionDAO((Database) pluginDatabaseSystem);
//            subAppFermatMonitorConnectionDAO.initializeDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);
//
//            subAppFermatMonitorSystemDataDAO = new SystemDataDAO((Database) pluginDatabaseSystem);
//            subAppFermatMonitorSystemDataDAO.initializeDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);


            /**
             *  Connect with main repository
             */


            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception e) {

            throw new CantStartPluginException(e, null, "Unhandled Exception.");

        }

    }

    @Override
    public void stop() {


        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }


}
