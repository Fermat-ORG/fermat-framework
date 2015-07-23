/*
 * @#TemplateNetworkServicePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.template.TemplateManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateEstablishedRequestedNetworkServiceConnectionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantInitializeNetworkTemplateDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by loui on 12/02/15.
 * Update by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */
public class TemplateNetworkServicePluginRoot implements TemplateManager, Service, NetworkService, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, Plugin {

   public static Logger LOG = Logger.getGlobal();

    /**
     * DealsWithCommunicationLayerManager Interface member variables.
     */
    private CommunicationLayerManager communicationLayerManager;

    /**
     * Represent the status of the network service
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
     * Represent the publicKey of the network service
     */
    private String publicKey;

    /**
     * Hold the listeners references
     */
    private List<EventListener> listenersAdded;

    /**
     * Holds the templateNetworkServiceManagersCache
     */
    private Map<UUID, TemplateNetworkServiceManager> templateNetworkServiceManagersCache;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the eccKeyPair
     */
    private ECCKeyPair eccKeyPair;

    /**
     * Constructor
     */
    public TemplateNetworkServicePluginRoot() {
        super();
        this.listenersAdded = new ArrayList<>();
        this.templateNetworkServiceManagersCache = new HashMap<>();
    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener(TemplateNetworkServiceManager templateNetworkServiceManager){

        /*
         * Listen and handle incoming network service connection request event
         */
        EventListener eventListener = eventManager.getNewListener(EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST);
        eventListener.setEventHandler(new TemplateIncomingNetworkServiceConnectionRequestHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        /*
         * Listen and handle established network service connection event
         */
        eventListener = eventManager.getNewListener(EventType.ESTABLISHED_NETWORK_SERVICE_CONNECTION);
        eventListener.setEventHandler(new TemplateEstablishedRequestedNetworkServiceConnectionHandler(this));
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkTemplateDataBaseException
     */
    private void initializeDb() throws CantInitializeNetworkTemplateDataBaseException {
        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, TemplateNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkTemplateDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            TemplateNetworkServiceDatabaseFactory templateNetworkServiceDatabaseFactory = new TemplateNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = templateNetworkServiceDatabaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkTemplateDataBaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        LOG.info("TemplateNetworkServicePluginRoot - starting  ");

        /*
         * If all resources are inject
         */
        if (communicationLayerManager != null && pluginDatabaseSystem != null && errorManager != null && eventManager  != null) {

            try {

                /*
                 * Create a new key pair for this execution
                 */
                eccKeyPair = new ECCKeyPair();

                /*
                 * Initialize the data base
                 */
                initializeDb();

                /*
                 * Register this network service whit the communicationLayerManager
                 */
                communicationLayerManager.registerNetworkService(NetworkServices.TEMPLATE, eccKeyPair.getPublicKey());


                /*
                 * Its all ok, set the new status
                */
                this.serviceStatus = ServiceStatus.STARTED;


            } catch (CommunicationException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not register whit the communicationLayerManager. Error reason: "+e.getMessage()));
                throw new CantStartPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE);

            } catch (CantInitializeNetworkTemplateDataBaseException exception) {

                StringBuffer contextBuffer = new StringBuffer();
                contextBuffer.append("Plugin ID: " + pluginId);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("Database Name: " + TemplateNetworkServiceDatabaseConstants.DATA_BASE_NAME);

                String context = contextBuffer.toString();
                String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
                CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
                throw pluginStartException;
            }

        } else {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("communicationLayerManager: " + communicationLayerManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;


        }

    }

    /**
     * (non-Javadoc)
     * @see Service#pause()
     */
    @Override
    public void pause() {

        /*
         * pause all the managers
         */
        for (UUID key : templateNetworkServiceManagersCache.keySet()){
            templateNetworkServiceManagersCache.get(key).pause();
        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;

    }

    /**
     * (non-Javadoc)
     * @see Service#resume()
     */
    @Override
    public void resume() {

        /*
         * resume all the managers
         */
        for (UUID key : templateNetworkServiceManagersCache.keySet()){
            templateNetworkServiceManagersCache.get(key).resume();
        }

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     * @see Service#stop()
     */
    @Override
    public void stop() {

        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the managers
         */
        for (UUID key : templateNetworkServiceManagersCache.keySet()){
            templateNetworkServiceManagersCache.get(key).closeAllConnection();
        }

        //Clear all references
        templateNetworkServiceManagersCache.clear();

         /*
         * Unregister whit the communicationLayerManager
         */
        try {

            communicationLayerManager.unregisterNetworkService(NetworkServices.INTRA_USER);

        } catch (CommunicationException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);

            String context = contextBuffer.toString();
            String possibleCause = "Communication Layer Manager error";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE,UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

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
     * @return TemplateNetworkServiceManager the intra user network service manager
     */
    public TemplateNetworkServiceManager intraUserNetworkServiceManagerFactory(UUID pluginClientId){

        /*
         * Create a new instance
         */
        TemplateNetworkServiceManager manager = new TemplateNetworkServiceManager(eccKeyPair, communicationLayerManager, dataBase, errorManager, eventManager);

        /*
         * Initialize the manager to listener the events
         */
        initializeListener(manager);

        /**
         * Cache the instance
         */
        templateNetworkServiceManagersCache.put(pluginClientId, manager);

        /*
         * return the instance
         */
        return manager;

    }

    /**
     * Return a previously created instances of the intra user network service manager
     *
     * @param pluginClientId
     * @return TemplateNetworkServiceManager
     */
    public TemplateNetworkServiceManager getIntraUserNetworkServiceManager(UUID pluginClientId){

        return  templateNetworkServiceManagersCache.get(pluginClientId);
    }


}
