package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ProtocolState;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerConnectionInformation;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.FailureComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.structure.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.communication.structure.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.ConnectionNewsDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerActorNetworkServiceManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerExecutorAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.database.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoBrokerActorNetworkServicePluginRoot</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 */
public class CryptoBrokerActorNetworkServicePluginRoot extends AbstractNetworkService implements DatabaseManagerForDevelopers {


    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons .ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons .EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION   , plugin = Plugins.WS_CLOUD_CLIENT       )
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * Network Service Implementation Variables.
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;
    private CommunicationNetworkServiceConnectionManager        communicationNetworkServiceConnectionManager;
    private CopyOnWriteArrayList<PlatformComponentProfile>      remoteNetworkServicesRegisteredList;
    private List<FermatEventListener>                           listenersAdded;
    private Database                                            dataBase;

    /**
     * Crypto Broker Actor Network Service member variables.
     */
    private CryptoBrokerExecutorAgent cryptoBrokerExecutorAgent;

    private ConnectionNewsDao connectionNewsDao;

    /**
     * Constructor of the Network Service.
     */
    public CryptoBrokerActorNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_BROKER,
                "Crypto Broker Actor Network Service",
                "CryptoBrokerActorNetworkService",
                null,
                EventSource.ACTOR_NETWORK_SERVICE_CRYPTO_BROKER
        );

        this.listenersAdded = new ArrayList<>();
    }

    /**
     * Service Interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {

            loadKeyPair(pluginFileSystem);


        } catch (CantLoadKeyPairException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem trying to load the key pair of the plugin.");
        }

        // template stuff.
        try {

            /*
             * Initialize the data base
             */
            initializeCommunicationDb();

            /*
             * Initialize listeners
             */
            initializeListener();


            /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()){

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<>();

        } catch (final CantInitializeNetworkServiceDatabaseException e) {

            String context = "Plugin ID: "     + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR+
                             "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME;

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, context, "The Template Database triggered an unexpected problem that wasn't able to solve by itself");
        }

        try {

            connectionNewsDao = new ConnectionNewsDao(pluginDatabaseSystem, pluginId);

            connectionNewsDao.initialize();

            final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

            fermatManager = new CryptoBrokerActorNetworkServiceManager(
                    communicationsClientConnection         ,
                    connectionNewsDao                      ,
                    errorManager                           ,
                    getPluginVersionReference()
            );


        } catch(final CantInitializeDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem initializing crypto broker ans dao.");
        }

        /*
         * Its all ok, set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    private CryptoBrokerActorNetworkServiceManager fermatManager;

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    private void initializeListener(){

         /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         *  failure connection
         */

        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new FailureComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         * new message
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeCommunicationDb() throws CantInitializeNetworkServiceDatabaseException {

        try {

            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            CommunicationNetworkServiceDatabaseFactory communicationLayerNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                this.dataBase = communicationLayerNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantCreateDatabaseException);

            }
        }

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
        if (wsCommunicationsCloudClientManager == null ||
                pluginDatabaseSystem               == null ||
                errorManager                       == null ||
                eventManager                       == null ) {

            String context =
                    "Plugin ID:                          " + pluginId                           + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "pluginDatabaseSystem:               " + pluginDatabaseSystem               + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "errorManager:                       " + errorManager                       + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR +
                            "eventManager:                       " + eventManager;

            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;

        }

    }

    @Override
    public void pause() {

        // pause connections manager.
        communicationNetworkServiceConnectionManager.pause();

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        // resume connections manager.
        communicationNetworkServiceConnectionManager.resume();

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        // remove all listeners from the event manager and from the plugin.
        for (final FermatEventListener listener: listenersAdded)
            eventManager.removeListener(listener);

        listenersAdded.clear();

        // close all connections.
        communicationNetworkServiceConnectionManager.closeAllConnection();

        // set to not registered.
        register = Boolean.FALSE;

        fermatManager.setPlatformComponentProfile(null);

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getRemoteNetworkServicesRegisteredList()
     */
    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    /**
     * (non-javadoc)
     * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
     */
    @Override
    public void requestRemoteNetworkServicesRegisteredList(final DiscoveryQueryParameters discoveryQueryParameters) {

        /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(
                    this.getPlatformComponentProfilePluginRoot(),
                    discoveryQueryParameters
            );

        } catch (final CantRequestListException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    /**
     * This method initialize the CommunicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the RegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    @Override
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
                this.getPlatformComponentProfilePluginRoot(),
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                dataBase,
                errorManager,
                eventManager,
                this.getEventSource(),
                getPluginVersionReference()
        );
    }

    /**
     * (non-javadoc)
     * @see NetworkService#getNetworkServiceConnectionManager()
     */
    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * (non-javadoc)
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType, String, String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(final PlatformComponentType platformComponentType         ,
                                                                         final NetworkServiceType    networkServiceType            ,
                                                                         final String                alias                         ,
                                                                         final String                identityPublicKey             ,
                                                                         final Location              location                      ,
                                                                         final Double                distance                      ,
                                                                         final String                name                          ,
                                                                         final String                extraData                     ,
                                                                         final Integer               firstRecord                   ,
                                                                         final Integer               numRegister                   ,
                                                                         final PlatformComponentType fromOtherPlatformComponentType,
                                                                         final NetworkServiceType    fromOtherNetworkServiceType   ) {

        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(
                platformComponentType         ,
                networkServiceType            ,
                alias                         ,
                identityPublicKey             ,
                location                      ,
                distance                      ,
                name                          ,
                extraData                     ,
                firstRecord                   ,
                numRegister                   ,
                fromOtherPlatformComponentType,
                fromOtherNetworkServiceType
        );
    }

    /**
     * Handles the events CompleteComponentRegistrationNotification
     */
    public void initializeAgent() {

        try {

            cryptoBrokerExecutorAgent = new CryptoBrokerExecutorAgent(
                    this                              ,
                    errorManager                      ,
                    eventManager                      ,
                    connectionNewsDao                 ,
                    wsCommunicationsCloudClientManager
            );

            cryptoBrokerExecutorAgent.start();

        } catch(final CantStartAgentException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){

        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType()  == this.getPlatformComponentType() &&
                platformComponentProfileRegistered.getNetworkServiceType()  == this.getNetworkServiceType() &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(this.getIdentityPublicKey())){

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            initializeAgent();

            fermatManager.setPlatformComponentProfile(this.getPlatformComponentProfilePluginRoot());

        }

    }


    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {
        System.out.println("----------------------------------\n" +
                "FAILED CONNECTION WITH "+remoteParticipant.getAlias()+"\n" +
                "--------------------------------------------------------");
        cryptoBrokerExecutorAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());

    }

    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile){

        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        remoteNetworkServicesRegisteredList.addAllAbsent(platformComponentProfileRegisteredList);
    }

    public final void handleNewMessages(final FermatMessage fermatMessage) {

        try {

            Gson gson = new Gson();

            String jsonMessage = fermatMessage.getContent();

            NetworkServiceMessage networkServiceMessage = gson.fromJson(jsonMessage, NetworkServiceMessage.class);

            switch (networkServiceMessage.getMessageType()) {

                case INFORMATION:
                    InformationMessage informationMessage = gson.fromJson(jsonMessage, InformationMessage.class);
                    receiveInformation(informationMessage);
                    break;

                case REQUEST:
                    // update the request to processing receive state with the given action.
                    RequestMessage requestMessage = gson.fromJson(jsonMessage, RequestMessage.class);
                    receiveRequest(requestMessage);
                    break;

                default:
                    throw new CantHandleNewMessagesException(
                            "message type: " +networkServiceMessage.getMessageType().name(),
                            "Message type not handled."
                    );
            }



        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.    .
     */
    private void receiveInformation(final InformationMessage informationMessage) throws CantHandleNewMessagesException {

        try {

            final ProtocolState state = ProtocolState.PROCESSING_RECEIVE;

            switch (informationMessage.getAction()) {
                case ACCEPT:
                    connectionNewsDao.acceptConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;
                case DENY:
                    connectionNewsDao.denyConnection(
                            informationMessage.getRequestId(),
                            state
                    );
                    break;
                default:
                    throw new CantHandleNewMessagesException(
                            "action not supported: " +informationMessage.getAction(),
                            "action not handled."
                    );
            }

         } catch(CantAcceptConnectionRequestException | CantDenyConnectionRequestException | ConnectionRequestNotFoundException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
        } catch(Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }

    /**
     * I indicate to the Agent the action that it must take:
     * - Protocol State: PROCESSING_RECEIVE.
     * - Type          : RECEIVED.
     */
    private void receiveRequest(final RequestMessage requestMessage) throws CantHandleNewMessagesException {

        try {

            final ProtocolState           state  = ProtocolState          .PROCESSING_RECEIVE;
            final RequestType             type   = RequestType            .RECEIVED          ;

            final CryptoBrokerConnectionInformation connectionInformation = new CryptoBrokerConnectionInformation(
                    requestMessage.getSenderPublicKey(),
                    requestMessage.getSenderActorType(),
                    requestMessage.getSenderAlias(),
                    null, //requestMessage.getSenderImage(),
                    requestMessage.getDestinationPublicKey(),
                    requestMessage.getSentTime()
            );

            connectionNewsDao.createConnectionRequest(
                    requestMessage.getRequestId(),
                    connectionInformation,
                    state,
                    type,
                    requestMessage.getRequestAction()
            );

        } catch(CantRequestConnectionException e) {
            // i inform to error manager the error.
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Error in Crypto Broker ANS Dao.");
        } catch(Exception e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantHandleNewMessagesException(e, "", "Unhandled Exception.");
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new CryptoBrokerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }
}
