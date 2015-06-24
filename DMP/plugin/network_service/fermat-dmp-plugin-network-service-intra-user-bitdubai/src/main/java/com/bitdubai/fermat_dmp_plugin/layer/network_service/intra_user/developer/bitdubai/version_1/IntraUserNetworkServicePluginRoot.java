package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.IntraUserIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.NetworkServiceNotRegisteredException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.NetworkServiceNotSupportedException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by loui on 12/02/15.
 * Update by Roberto Requena - (rrequena) on 20/05/15.
 *
 * @version 1.0
 */
public class IntraUserNetworkServicePluginRoot  implements IntraUserManager, Service, NetworkService, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, Plugin {

    /**
     * DealsWithCommunicationLayerManager Interface member variables.
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * Hold the listeners references
     */
    private List<EventListener> listenersAdded;

    /**
     * Holds the intraUserNetworkServiceManagersCache
     */
    private Map<UUID, IntraUserNetworkServiceManager>  intraUserNetworkServiceManagersCache;

    /**
     * Constructor
     */
    public IntraUserNetworkServicePluginRoot() {
        super();
        this.listenersAdded                       = new ArrayList<EventListener>();
        this.intraUserNetworkServiceManagersCache = new HashMap<UUID, IntraUserNetworkServiceManager>();
    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(IntraUserNetworkServiceManager intraUserNetworkServiceManager){

        /*
         * Listen and handle incoming network service connection request in event
         */
        EventListener eventListener = eventManager.getNewListener(EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST);
        eventListener.setEventHandler(new IntraUserIncomingNetworkServiceConnectionRequestHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        /*
         * If all resources are inject
         */
        if (communicationLayerManager != null &&
                pluginDatabaseSystem  != null &&
                    errorManager      != null &&
                        eventManager  != null) {

            /*
             * Register this network service whit the communicationLayerManager
             */
            try {
                communicationLayerManager.registerNetworkService(NetworkServices.INTRA_USER);
            } catch (CommunicationException e) {

            }

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;


        } else {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("No all required resource are injected"));
            throw new CantStartPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE);
        }



    }


    @Override
    public void pause() {

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the manages
         */
        for (UUID key : intraUserNetworkServiceManagersCache.keySet()){
            intraUserNetworkServiceManagersCache.get(key).closeAllConnection();
        }

        //Clear all references
        intraUserNetworkServiceManagersCache.clear();

         /*
         * Unregister whit the communicationLayerManager
         */
        try {
            communicationLayerManager.unregisterNetworkService(NetworkServices.INTRA_USER);
        } catch (CommunicationException e) {

        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


    /**
     * (non-Javadoc)
     * @see DealsWithCommunicationLayerManager#setCommunicationLayerManager(CommunicationLayerManager)  No Compila (Luis)
     */
    @Override
    public void setCommunicationLayerManager(CommunicationLayerManager communicationLayerManager) {
        this.communicationLayerManager = communicationLayerManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithEvents#setEventManager(EventManager) )
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /* (non-Javadoc)
    * @see DealsWithPluginDatabaseSystem#setPluginDatabaseSystem()
    */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * (non-Javadoc)
     * @see NetworkService#getId()
     */
    @Override
    public UUID getId() {
        return this.pluginId;
    }

    /**
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * This is a factory method for create new instances of the intra user network service manager
     *
     * @param pluginClientId the plugin client id
     * @return IntraUserNetworkServiceManager the intra user network service manager
     */
    public IntraUserNetworkServiceManager intraUserNetworkServiceManagerFactory(UUID pluginClientId){

        /*
         * Create a new instance
         */
        IntraUserNetworkServiceManager manager = new IntraUserNetworkServiceManager(this, communicationLayerManager, pluginDatabaseSystem, errorManager);

        /*
         * Initialize the manager to listener the events
         */
        initializeListener(manager);

        /**
         * Cache the instance
         */
        intraUserNetworkServiceManagersCache.put(pluginClientId, manager);

        /*
         * return the instance
         */
        return manager;

    }

    /**
     * Return a previously created instances of the intra user network service manager
     *
     * @param pluginClientId
     * @return IntraUserNetworkServiceManager
     */
    public IntraUserNetworkServiceManager getIntraUserNetworkServiceManager(UUID pluginClientId){

        return  intraUserNetworkServiceManagersCache.get(pluginClientId);
    }


}
