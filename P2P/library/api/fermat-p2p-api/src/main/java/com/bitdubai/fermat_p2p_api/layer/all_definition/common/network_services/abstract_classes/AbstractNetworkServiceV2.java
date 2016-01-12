package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionLooseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mati on 2016.01.12..
 */
public abstract class AbstractNetworkServiceV2 extends AbstractPlugin implements Service,NetworkService{


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Network Service details.
     */
    private final PlatformComponentType platformComponentType   ;
    private final NetworkServiceType networkServiceType      ;
    private final String                   name                    ;
    private final String                   alias                   ;
    private final String                   extraData               ;
    private final EventSource eventSource             ;
    private PlatformComponentProfile platformComponentProfile;
    protected ECCKeyPair identity                ;
    protected AtomicBoolean register                ;

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private CopyOnWriteArrayList<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;

    /**
     * Hold the listeners references
     */
    private List<FermatEventListener> listenersAdded = new ArrayList<>();


    public AbstractNetworkServiceV2(final PluginVersionReference pluginVersionReference,
                                  final PlatformComponentType  platformComponentType ,
                                  final NetworkServiceType     networkServiceType    ,
                                  final String                 name                  ,
                                  final String                 alias                 ,
                                  final String                 extraData             ,
                                  final EventSource            eventSource           ) {

        super(pluginVersionReference);

        this.platformComponentType = platformComponentType;
        this.networkServiceType    = networkServiceType   ;
        this.name                  = name                 ;
        this.alias                 = alias                ;
        this.extraData             = extraData            ;
        this.eventSource           = eventSource          ;
    }

    public final boolean isRegister() {
        return register.get();
    }

    public void setRegister(AtomicBoolean register) {
        this.register = register;
    }



    public final String getName() {
        return name;
    }

    public final String getAlias() {
        return alias;
    }

    public final String getExtraData() {
        return extraData;
    }

    public final PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    public final NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public final EventSource getEventSource() {
        return eventSource;
    }

    public final PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    //TODO: VER EL TEMA DE LA DATABASE

    public void start() throws CantStartPluginException{
        try {
          /*
         * Create a new key pair for this execution
         */
            identity = new ECCKeyPair();
            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            initializeListener();
            onStart();
            serviceStatus = ServiceStatus.STARTED;


        } catch (CantInitializeTemplateNetworkServiceDatabaseException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pause(){
        onPause();
        serviceStatus = ServiceStatus.PAUSED;
    }

    public void resume(){
        onResume();
        serviceStatus = ServiceStatus.STARTED;
    }

    public void stop(){
        onStop();
        serviceStatus = ServiceStatus.STOPPED;
    }

    public ServiceStatus getStatus(){
        return serviceStatus;
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfilePluginRoot for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        //todo: esto va para lo ultimo porque hay que cambiar el extends al V2
       // this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(this,platformComponentProfile, identity, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), dataBaseCommunication, errorManager, eventManager);
    }


    private static final String PLUGIN_IDS_DIRECTORY_NAME = "security";
    private static final String PLUGIN_IDS_FILE_NAME      = "networkServiceKeyPairsFile";
    private static final String PAIR_SEPARATOR            = ";"         ;

    protected final void loadKeyPair(final PluginFileSystem pluginFileSystem) throws CantLoadKeyPairException {

        try {
            final PluginTextFile identityFile = pluginFileSystem.getTextFile(pluginId, PLUGIN_IDS_DIRECTORY_NAME, buildNetworkServiceKeyPairFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            final String identityFileContent = identityFile.getContent();

            final String[] identityKeyPairSplit = identityFileContent.split(PAIR_SEPARATOR);

            if (identityKeyPairSplit.length == 2)
                this.identity = new ECCKeyPair(identityKeyPairSplit[0], identityKeyPairSplit[1]);
            else
                throw new CantLoadKeyPairException("identityKeyPairSplit: " + identityFileContent, "ErrorTrying to load the key pair, the string is not valid.");

        } catch (CantCreateFileException e) {

            throw new CantLoadKeyPairException(e, "", "Cant create ns identity file exception.");
        } catch( FileNotFoundException e) {

            try {

                ECCKeyPair identity = new ECCKeyPair();

                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, PLUGIN_IDS_DIRECTORY_NAME, buildNetworkServiceKeyPairFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                String fileContent = identity.getPrivateKey() + PAIR_SEPARATOR + identity.getPublicKey();

                pluginTextFile.setContent(fileContent);
                pluginTextFile.persistToMedia();

                this.identity = identity;

            } catch (CantCreateFileException | CantPersistFileException z) {

                throw new CantLoadKeyPairException(z, "", "Cant create, persist or who knows what....");
            }
        }
    }

    private String buildNetworkServiceKeyPairFileName() {
        return PLUGIN_IDS_FILE_NAME + "_" + pluginId;
    }


   // public abstract void initializeCommunicationNetworkServiceConnectionManager();
    public abstract String getIdentityPublicKey();

    /**
     * falta hacer la explicacion de cada uno
     */
    abstract void onStart();
    abstract void onStop();
    abstract void onResume();
    abstract void onPause();


    abstract void initializeAgent();
    abstract Database initializeDatabase();

    /**
     * Handlers
     */

    abstract void onHandleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfile);
    abstract void onHandleFailureNetworkServiceRegistrationNotificationEvent(PlatformComponentProfile profileToRegister);
    abstract void onHandleFailureSubComponentRegistrationNotificationEvent(PlatformComponentProfile profileToRegister);
    abstract void onHandleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile);
    abstract void onHandleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList);
    abstract void onHandleVpnConnectionCloseNotificationEvent();
    abstract void onHandleVpnConnectionLooseNotificationEvent();
    abstract void onHandleVpnReconnectSuccesfullNotificationEvet();
    abstract void onHandleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent);
    abstract void onHandleCompleteUpdateActorNotificationEvent(PlatformComponentProfile actor);
    abstract void onHandleNewMessages(final FermatMessage message);
    abstract void onHandleNewSentMessageNotificationEvent(final FermatMessage message);


    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){

        onHandleCompleteComponentRegistrationNotificationEvent(platformComponentProfileRegistered);
        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == networkServiceType &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){

            /*
             * Mark as register
             */
            this.register = new AtomicBoolean(true);

            initializeAgent();
        }

    }


    /**
     * Handles the events handleNewMessages
     * @param message
     */
    public void handleNewMessages(final FermatMessage message){
        onHandleNewMessages(message);
    }

    /**
     * Handles the events handleNewSentMessageNotificationEvent
     * @param data
     */

    public void handleNewSentMessageNotificationEvent(FermatMessage data){
        onHandleNewSentMessageNotificationEvent(data);
    }


    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile profileToRegister) {
    System.out.println("----------------------------------\n" +
            "NS FAILED REGISTRARION WITH " + profileToRegister.getAlias() + "\n" +
            "--------------------------------------------------------");
    if(networkServiceApplicant.getNetworkServiceType()== profileToRegister.getNetworkServiceType()){
        onHandleFailureNetworkServiceRegistrationNotificationEvent(profileToRegister);
    }else{
        onHandleFailureSubComponentRegistrationNotificationEvent(profileToRegister);
    }

//        cryptoAddressesExecutorAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());
//
//        //I check my time trying to send the message
//        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());

    }

    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile){
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    /**
     * (non-Javadoc)
     *
     * @see NetworkService#
     */
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteRequestListComponentRegisteredNotificationEvent");

        /*
         * save into the cache
         */

        remoteNetworkServicesRegisteredList.addAllAbsent(platformComponentProfileRegisteredList);


        System.out.println("--------------------------------------\n" +
                "REGISTRO DE USUARIOS INTRA USER CONECTADOS");
        for (PlatformComponentProfile platformComponentProfile : platformComponentProfileRegisteredList) {
            System.out.println(platformComponentProfile.getAlias() + "\n");
        }
        System.out.println("--------------------------------------\n" +
                "FIN DE REGISTRO DE USUARIOS INTRA USER CONECTADOS");


        //save register actors in the cache
        //TODO: Ver si me conviene guardar los actores en una base de datos nueva de cache


        /* -----------------------------------------------------------------------
         * This is for test and example of how to use
         */
        if (getRemoteNetworkServicesRegisteredList() != null && !getRemoteNetworkServicesRegisteredList().isEmpty()) {


        }

    }


    /**
     * Handles the events VPNConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    public void handleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof VPNConnectionCloseNotificationEvent){

            VPNConnectionCloseNotificationEvent vpnConnectionCloseNotificationEvent = (VPNConnectionCloseNotificationEvent) fermatEvent;

            if(vpnConnectionCloseNotificationEvent.getNetworkServiceApplicant() == getNetworkServiceType()){

                if(communicationNetworkServiceConnectionManager != null)
                    communicationNetworkServiceConnectionManager.closeConnection(vpnConnectionCloseNotificationEvent.getRemoteParticipant().getIdentityPublicKey());

            }

        }

    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {

        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){

            onHandleClientConnectionCloseNotificationEvent(fermatEvent);
            System.out.println("----------------------------\n" +
                    "CHANGING OUTGOING NOTIFICATIONS RECORDS " +
                    "THAT HAVE THE PROTOCOL STATE SET TO SENT" +
                    "TO PROCESSING SEND IN ORDER TO ENSURE PROPER RECEPTION :"
                    + "\n-------------------------------------------------");

            this.register = new AtomicBoolean(false);
            if(communicationNetworkServiceConnectionManager != null)
                communicationNetworkServiceConnectionManager.closeAllConnection();
        }

    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {

        if(communicationNetworkServiceConnectionManager != null) {
            communicationNetworkServiceConnectionManager.stop();
            this.register = new AtomicBoolean(false);
        }

    }

    /*
     * Handles the events ClientSuccessfullReconnectNotificationEvent
     */
    public void handleClientSuccessfullReconnectNotificationEvent(FermatEvent fermatEvent) {

        System.out.println("SuccessfullReconnectNotificationEvent");
        if(communicationNetworkServiceConnectionManager != null) {
            communicationNetworkServiceConnectionManager.restart();
            this.register = new AtomicBoolean(true);
        }

    }

    public void handleCompleteUpdateActorNotificationEvent(PlatformComponentProfile platformComponentProfile){
        /*
         * Recieve Notification that Actor was update sucessfully
         */

    }



    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {

         /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteComponentRegistrationNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteComponentRegistrationNotificationEvent event) {
                handleCompleteComponentRegistrationNotificationEvent(event.getPlatformComponentProfileRegistered());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteRequestListComponentRegisteredNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {
                handleCompleteRequestListComponentRegisteredNotificationEvent(event.getRegisteredComponentList());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteComponentConnectionRequestNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteComponentConnectionRequestNotificationEvent event) {
                handleCompleteComponentConnectionRequestNotificationEvent(event.getApplicantComponent(), event.getRemoteComponent());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         *  failure connection
         */

        fermatEventListener = eventManager.getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<FailureComponentConnectionRequestNotificationEvent>(this) {
            @Override
            public void processEvent(FailureComponentConnectionRequestNotificationEvent event) {
                handleFailureComponentRegistrationNotificationEvent(event.getNetworkServiceApplicant(), event.getRemoteParticipant());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

                /*
         * Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<VPNConnectionCloseNotificationEvent>(this) {
            @Override
            public void processEvent(VPNConnectionCloseNotificationEvent event) {
                handleVpnConnectionCloseNotificationEvent(event);
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Close Notification Event
         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
//        fermatEventListener.setEventHandler(new ClientConnectionClose(this) {
//            @Override
//            public void processEvent(ClientConnectionCloseNotificationEvent event) {
//                handleClientConnectionCloseNotificationEvent(event);
//            }
//        });
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Loose Notification Event
         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
//        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEvent(this) {
//            @Override
//            public void processEvent(ClientConnectionLooseNotificationEvent event) {
//                handleClientConnectionCloseNotificationEvent(event);
//            }
//        });
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);


        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
//        fermatEventListener.setEventHandler(new ClientSuccessfullReconnectNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);



         /*
         * Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteUpdateActorNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteUpdateActorNotificationEvent event) {
                handleCompleteUpdateActorNotificationEvent(event.getPlatformComponentProfileUpdate());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */


        /**
         *Listen and handle the received messages
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageReceivedNotificationEvent>(this) {
            @Override
            public void processEvent(NewNetworkServiceMessageReceivedNotificationEvent event) {
                handleNewMessages((FermatMessage) event.getData());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         * Listen and handle the sent messages
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageSentNotificationEvent>(this) {
            @Override
            public void processEvent(NewNetworkServiceMessageSentNotificationEvent event) {
                handleNewSentMessageNotificationEvent((FermatMessage) event.getData());
            }
        });
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }



        /**
         * (non-javadoc)
         *
         * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType,  String,String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
         */
        public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias,String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
            return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias,  identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
        }

        /**
         * (non-javadoc)
         *
         * @see NetworkService#getRemoteNetworkServicesRegisteredList()
         */
        public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
            return remoteNetworkServicesRegisteredList;
        }

        /**
         * (non-javadoc)
         *
         * @see NetworkService#requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters)
         */
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

        System.out.println(" TemplateNetworkServiceRoot - requestRemoteNetworkServicesRegisteredList");

         /*
         * Request the list of component registers
         */
        try {

            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfile, discoveryQueryParameters);

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }



}
