package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ConnectionDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemDataDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.developerUtils.SystemMonitorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInitializeSystemMonitorNetworkServiceDataBaseException;

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
public class SubAppFermatMonitorNetworkServicePluginRoot extends AbstractNetworkServiceBase {

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
    private ServiceDAO subAppFermatMonitorServiceDAO;
    private ConnectionDAO subAppFermatMonitorConnectionDAO;
    private SystemDataDAO subAppFermatMonitorSystemDataDAO;

    private Database dataBaseCommunication;

    private SystemMonitorNetworkServiceDeveloperDatabaseFactory systemMonitorNetworkServiceDeveloperDatabaseFactory;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public SubAppFermatMonitorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_FERMAT_MONITOR,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.FERMAT_MONITOR,
                "Fermat Monitor Network Services",
                null);
    }


    @Override
    protected void onStart() throws CantStartPluginException {


        try {
            /**
             * Initialize Database
             */
            initializeDb();
            //Initialize Developer Database
            systemMonitorNetworkServiceDeveloperDatabaseFactory = new SystemMonitorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem,pluginId);
            systemMonitorNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            subAppFermatMonitorServiceDAO = new ServiceDAO((Database) pluginDatabaseSystem);
            subAppFermatMonitorServiceDAO.initializeDatabase(pluginId,SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            subAppFermatMonitorConnectionDAO = new ConnectionDAO((Database) pluginDatabaseSystem);
            subAppFermatMonitorConnectionDAO.initializeDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

            subAppFermatMonitorSystemDataDAO = new SystemDataDAO((Database) pluginDatabaseSystem);
            subAppFermatMonitorSystemDataDAO.initializeDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);


        } catch (CantInitializeTemplateNetworkServiceDatabaseException e) {
            e.printStackTrace();
        } catch (CantCreateDatabaseException e) {
            e.printStackTrace();
        } catch (CantInitializeSystemMonitorNetworkServiceDataBaseException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNetworkServiceRegistered() {

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



    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException, CantCreateDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.PIP_FERMAT_MONITOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            SystemMonitorNetworkServiceDatabaseFactory communicationSystemMonitorNetworkServiceDatabaseFactory = new SystemMonitorNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            /*
             * We create the new database
             */
            this.dataBaseCommunication = communicationSystemMonitorNetworkServiceDatabaseFactory.createDatabase(pluginId, SystemMonitorNetworkServiceDatabaseConstants.DATABASE_NAME);

        }

    }


}