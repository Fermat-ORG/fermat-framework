package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ComponentDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ConnectionDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.ServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemDataDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.database.SystemMonitorNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.developerUtils.SystemMonitorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInitializeSystemMonitorNetworkServiceDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures.ComponentProfileInfo;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures.EventHandlerRouter;

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
 * <p/>
 * Created by Matias Furszyfer on 17/02/15.
 */
public class SubAppFermatMonitorNetworkServicePluginRoot extends AbstractNetworkService implements DatabaseManagerForDevelopers {

    /**
     * Dealing with the repository database
     */
    private ServiceDAO subAppFermatMonitorServiceDAO;
    private ConnectionDAO subAppFermatMonitorConnectionDAO;
    private SystemDataDAO subAppFermatMonitorSystemDataDAO;
    private ComponentDAO subappFermatMonitorComponentDAO;

    private Database dataBaseCommunication;

    private ComponentDAO componentDAO;

    private SystemMonitorNetworkServiceDeveloperDatabaseFactory systemMonitorNetworkServiceDeveloperDatabaseFactory;

    private SystemMonitorNetworkServiceDatabaseConstants systemMonitorNetworkServiceDatabaseConstants;

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public SubAppFermatMonitorNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_FERMAT_MONITOR,
                NetworkServiceType.FERMAT_MONITOR
        );
    }


    @Override
    protected void onNetworkServiceStart() throws CantStartPluginException {

        try {
            /**
             * Initialize Database
             */
            initializeDb();
            //Initialize Developer Database
            systemMonitorNetworkServiceDeveloperDatabaseFactory = new SystemMonitorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            systemMonitorNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            subAppFermatMonitorServiceDAO = new ServiceDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            subAppFermatMonitorConnectionDAO = new ConnectionDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            subAppFermatMonitorSystemDataDAO = new SystemDataDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            subappFermatMonitorComponentDAO = new ComponentDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            //clean old data
            try {
                subappFermatMonitorComponentDAO.deleteComponentEvent();
            } catch (CantDeleteRecordDataBaseException e) {
                e.printStackTrace();
            }


        /*
         * 4 Listen and handle Complete Request List Component Registered Notification Event
         */
            FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
            fermatEventListener.setEventHandler(new EventHandlerRouter(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        /*
         * 5 Listen and handle Complete Component Registration Notification Event
         */
            fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new EventHandlerRouter(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);


        /*
         * 6 Listen and handle Complete Request list
         */
            fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
            fermatEventListener.setEventHandler(new EventHandlerRouter(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        /*
         * 7 Listen and handle Complete Update Actor Profile Notification Event
         */
            fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
            fermatEventListener.setEventHandler(new EventHandlerRouter(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        /*
         * 8 Listen and handle failure component connection
         */
            fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
            fermatEventListener.setEventHandler(new EventHandlerRouter(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);


        } catch (CantCreateDatabaseException e) {
            e.printStackTrace();
        } catch (CantInitializeSystemMonitorNetworkServiceDataBaseException e) {
            e.printStackTrace();
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


    /**
     * This method initialize the database
     *
     * @throws CantInitializeSystemMonitorNetworkServiceDataBaseException
     */
    private void initializeDb() throws CantInitializeSystemMonitorNetworkServiceDataBaseException, CantCreateDatabaseException {

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
            throw new CantInitializeSystemMonitorNetworkServiceDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

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

    public void saveComponentRegistration(CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent) {
        try {

            NetworkServiceType networkServiceType = completeComponentRegistrationNotificationEvent.getNetworkServiceTypeApplicant();
            PlatformComponentProfile platformComponentProfile = completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered();

            ComponentProfileInfo componentProfileInfo = new ComponentProfileInfo(
                    platformComponentProfile.getIdentityPublicKey(),
                    platformComponentProfile.getAlias(),
                    platformComponentProfile.getNetworkServiceType().getCode());

            try {

                subappFermatMonitorComponentDAO.create(componentProfileInfo);
            } catch (DatabaseTransactionFailedException e) {
                e.printStackTrace();
            }

        } catch (CantInsertRecordDataBaseException e) {
            e.printStackTrace();
        }
    }

    public void updateActor(FermatEvent fermatEvent) {

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return systemMonitorNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new SystemMonitorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return systemMonitorNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }
}
