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
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkService;
import com.bitdubai.fermat_api.layer.dmp_network_service.template.TemplateManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateEstablishedRequestedNetworkServiceConnectionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.event_handlers.TemplateIncomingNetworkServiceConnectionRequestHandler;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.exceptions.CantInitializeNetworkTemplateDataBaseException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.TemplateNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.database.TemplateNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationLayerManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.DealsWithCommunicationLayerManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Roberto Requena - (rrequena) on 21/07/15.
 *
 * @version 1.0
 */
public class TemplateNetworkServicePluginRoot implements TemplateManager, Service, NetworkService, DealsWithCommunicationLayerManager, DealsWithPluginDatabaseSystem, DealsWithEvents, DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Plugin {

    /**
     * Represent the logManager
     */
    private LogManager logManager;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

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
    private List<FermatEventListener> listenersAdded;

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
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (communicationLayerManager == null ||
                pluginDatabaseSystem  == null ||
                    errorManager      == null ||
                        eventManager  == null) {


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
     * Initialize the event listener and configure
     */
    private void initializeListener(TemplateNetworkServiceManager templateNetworkServiceManager){

        /*
         * Listen and handle incoming network service connection request event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(EventType.INCOMING_NETWORK_SERVICE_CONNECTION_REQUEST);
        fermatEventListener.setEventHandler(new TemplateIncomingNetworkServiceConnectionRequestHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle established network service connection event
         */
        fermatEventListener = eventManager.getNewListener(EventType.ESTABLISHED_NETWORK_SERVICE_CONNECTION);
        fermatEventListener.setEventHandler(new TemplateEstablishedRequestedNetworkServiceConnectionHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
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
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        logManager.log(TemplateNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "Starting", "Starting", "Starting");

        /*
         * Validate required resources
         */
        validateInjectedResources();

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
             * TODO: Register this network service whit the communicationLayerManager
             */
            //communicationLayerManager.registerNetworkService(NetworkServices.TEMPLATE, eccKeyPair.getPublicKey());


            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;


       /* } catch (CommunicationException e) {

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception("Can not register whit the communicationLayerManager. Error reason: "+e.getMessageContent()));
            throw new CantStartPluginException(Plugins.BITDUBAI_USER_NETWORK_SERVICE);

      */  } catch (CantInitializeNetworkTemplateDataBaseException exception) {

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
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return TemplateNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    /**
     * (non-Javadoc)
     * @see DealsWithLogger#setLogManager(LogManager)
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.IncomingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.OutgoingTemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceManager");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceMessage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.structure.TemplateNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (TemplateNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                TemplateNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                TemplateNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                TemplateNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

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
    public TemplateNetworkServiceManager templateNetworkServiceManagerFactory(UUID pluginClientId){

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
     * Return a previously created instances of the template network service manager
     *
     * @param pluginClientId
     * @return TemplateNetworkServiceManager
     */
    public TemplateNetworkServiceManager getTemplateNetworkServiceManager(UUID pluginClientId){

        return  templateNetworkServiceManagersCache.get(pluginClientId);
    }


}
