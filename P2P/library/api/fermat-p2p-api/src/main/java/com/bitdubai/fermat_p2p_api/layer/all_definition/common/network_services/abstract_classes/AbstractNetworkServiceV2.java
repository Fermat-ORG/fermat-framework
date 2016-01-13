package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
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
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteUpdateActorNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.FailureComponentConnectionRequestNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.VPNConnectionCloseNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Matias Furszyfer on 2016.01.12..
 */
public abstract class AbstractNetworkServiceV2 extends AbstractPlugin implements Service,NetworkService{


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
    protected AtomicBoolean register                = new AtomicBoolean(false);

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;


    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

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

    public ServiceStatus getStatus(){
        return serviceStatus;
    }

    public Database getDataBaseCommunication() {
        return dataBaseCommunication;
    }

    protected abstract PluginFileSystem getPluginFileSystem();

    protected abstract ErrorManager getErrorManager();

    protected abstract WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager();

    public CommunicationNetworkServiceConnectionManager getCommunicationNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    public PlatformComponentProfile getPlatformComponentProfile() {
        return platformComponentProfile;
    }

    protected abstract EventManager getEventManager();

    public CommunicationNetworkServiceDeveloperDatabaseFactory getCommunicationNetworkServiceDeveloperDatabaseFactory() {
        return communicationNetworkServiceDeveloperDatabaseFactory;
    }

    protected abstract PluginDatabaseSystem getPluginDatabaseSystem();

    //TODO: VER EL TEMA DE LA DATABASE
    protected abstract void validateInjectedResources() throws CantStartPluginException;

    public void start() throws CantStartPluginException{
        validateInjectedResources();
        try {
          /*
         * Create a new key pair for this execution
         */
            identity = new ECCKeyPair();
            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(getPluginDatabaseSystem(), pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            initializeListener();
            initializeDb();


            /*
             * Verify if the communication cloud client is active
             */
            if (!getWsCommunicationsCloudClientManager().isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            remoteNetworkServicesRegisteredList = new CopyOnWriteArrayList<PlatformComponentProfile>();
            onStart();
            serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeTemplateNetworkServiceDatabaseException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pause(){
          /*
         * Pause
         */
        communicationNetworkServiceConnectionManager.pause();
        onPause();
        serviceStatus = ServiceStatus.PAUSED;
    }

    public void resume(){
         /*
         * resume the managers
         */
        communicationNetworkServiceConnectionManager.resume();
        onResume();
        serviceStatus = ServiceStatus.STARTED;
    }

    public void stop(){
        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();
        /*
         * Stop all connection on the managers
         */
        communicationNetworkServiceConnectionManager.closeAllConnection();
        //set to not register
        register = new AtomicBoolean(false);
        onStop();
        serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfilePluginRoot for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(this,platformComponentProfile, identity, getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection(), dataBaseCommunication, getErrorManager(), getEventManager());
    };

    //TODO: vaya a sabaer porqué me pusieron esto
    public abstract String getIdentityPublicKey();

    /**
     * falta hacer la explicacion de cada uno
     */
    protected abstract void onStart();
    protected abstract void onStop();
    protected abstract void onResume();
    protected abstract void onPause();

    /**
     * Initializer
     */
    protected abstract void initializeAgent();

    /**
     * Handlers
     */

    /*
     * Receive the event CompleteComponentRegistrationNotificationEvent
     * when a PlatformComponentProfile is registered successfully in the CloudServer
     */
    protected abstract void onHandleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfile);
    //TODO: ver si este va
    /*
     * Receive the event FailureNetworkServiceRegistrationNotificationEvent
     * when a PlatformComponentProfile was sent incorrectly to be registered in the CloudServer
     */
    protected abstract void onHandleFailureNetworkServiceConnectionNotificationEvent(PlatformComponentProfile profileToRegister);
    /*
     * Receive the event FailureNetworkServiceRegistrationNotificationEvent
     * when a PlatformComponentProfile was sent incorrectly to be registered in the CloudServer
     */
    protected abstract void onHandleFailureComponentConnectionNotificationEvent(PlatformComponentProfile profileToRegister);
    /*
     * Receive the event CompleteComponentConnectionRequestNotificationEvent
     * when is stablished successfully a Connection request, receiving two parameters:
     * the applicantComponentProfile and the remoteComponentProfile
     */
    protected abstract void onHandleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile);
    /*
     * Receive the event CompleteRequestListComponentRegisteredNotificationEvent
     * when receive the list of Components Registered in the CloudServer
     */
    protected abstract void onHandleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList);
    /*
     * Receive the event VpnConnectionCloseNotificationEvent
     * when a VPN connection is closed receiving as parameter the PlatformComponentProfile of the RemoteParticipant
     */
    protected abstract void onHandleVpnConnectionCloseNotificationEvent(FermatEvent fermatEvent);
    /*
     * Receive the event VpnConnectionLooseNotificationEvent
     * when a VPN connection is closed caused by the code 1006
     * receiving as parameter the PlatformComponentProfile of the RemoteParticipant
     */
    protected abstract void onHandleVpnConnectionLooseNotificationEvent();
    /*
     * Receive the event VpnReconnectSuccesfullNotificationEvet
     * when a VPN connection is Reconnect Succesfully
     * receiving as parameter the PlatformComponentProfile of the RemoteParticipant
     */
    protected abstract void onHandleVpnReconnectSuccesfullNotificationEvet();
    /*
     * Receive the event ClientConnectionCloseNotificationEvent
     * when the Connection to the CloudServer is lose abnormally
     */
    protected abstract void onHandleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent);
    /*
     * Receive the event ClientConnectionLooseNotificationEvent
     *  when the Connection to the CloudServer is lose
     */
    protected abstract void onHandleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent);
    /*
     * Receive the event CompleteUpdateActorNotificationEvent
     * when a PlatformComponentProfile is update successfully in the CloudServer
     */
    protected abstract void onHandleCompleteUpdateActorNotificationEvent(PlatformComponentProfile actor);
    /*
     * Receive the event NewMessages
     */
    protected abstract void onHandleNewMessages(final FermatMessage message);
    /*
     * Receive the event NewSentMessageNotificationEvent from the template
     */
    protected abstract void onHandleNewSentMessageNotificationEvent(final FermatMessage message);
    /*
     * Receive the event ClientSuccessfulReconnectionNotificationEvent
     * when the Client is Successfully Reconnected to the CloudServer
     */
    protected abstract void onHandleClientSuccessfulReconnectionNotificationEvent(final FermatEvent fermatEvent);


    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered){
        System.out.println("REGISTRANDO NS");
        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType()  == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType()  == networkServiceType &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())){
            System.out.println("REGISTRANDO NS"+networkServiceType);

            /*
             * Mark as register
             */
            this.register = new AtomicBoolean(true);
            initializeAgent();
            onHandleCompleteComponentRegistrationNotificationEvent(platformComponentProfileRegistered);
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


    public void handleFailureComponentConnectionNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile profileToRegister) {
        System.out.println("----------------------------------\n" +
                "NS FAILED Connection WITH " + profileToRegister.getAlias() + "\n" +
                "--------------------------------------------------------");
        if(networkServiceApplicant.getNetworkServiceType()== profileToRegister.getNetworkServiceType()){
            onHandleFailureNetworkServiceConnectionNotificationEvent(profileToRegister);
        }else{
            onHandleFailureComponentConnectionNotificationEvent(profileToRegister);
        }
    }

    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile){
                System.out.println("CONEXION ESTABLECIDA NS");
          /*
         * Tell the manager to handler the new connection stablished
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
        onHandleCompleteComponentConnectionRequestNotificationEvent(applicantComponentProfile, remoteComponentProfile);
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

        onHandleCompleteRequestListComponentRegisteredNotificationEvent(platformComponentProfileRegisteredList);


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
            onHandleVpnConnectionCloseNotificationEvent(fermatEvent);
        }
    }

    /**
     * Handles the events ClientConnectionCloseNotificationEvent
     * @param fermatEvent
     */
    public void handleClientConnectionCloseNotificationEvent(FermatEvent fermatEvent) {
        if(fermatEvent instanceof ClientConnectionCloseNotificationEvent){
            System.out.println("----------------------------\n" +
                    "CHANGING OUTGOING NOTIFICATIONS RECORDS " +
                    "THAT HAVE THE PROTOCOL STATE SET TO SENT" +
                    "TO PROCESSING SEND IN ORDER TO ENSURE PROPER RECEPTION :"
                    + "\n-------------------------------------------------");
            this.register = new AtomicBoolean(false);
            if(communicationNetworkServiceConnectionManager != null)
                communicationNetworkServiceConnectionManager.closeAllConnection();
        }
        onHandleClientConnectionCloseNotificationEvent(fermatEvent);
    }

    /*
     * Handles the events ClientConnectionLooseNotificationEvent
     */
    public void handleClientConnectionLooseNotificationEvent(FermatEvent fermatEvent) {
        if(communicationNetworkServiceConnectionManager != null) {
            communicationNetworkServiceConnectionManager.stop();
            this.register = new AtomicBoolean(false);
        }
        onHandleClientConnectionLooseNotificationEvent(fermatEvent);
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
        onHandleClientSuccessfulReconnectionNotificationEvent(fermatEvent);
    }

    public void handleCompleteUpdateActorNotificationEvent(PlatformComponentProfile platformComponentProfile){
        /*
         * Recieve Notification that Actor was update sucessfully
         */
        onHandleCompleteUpdateActorNotificationEvent(platformComponentProfile);
    }



    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {
         /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteComponentRegistrationNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteComponentRegistrationNotificationEvent event) {
                handleCompleteComponentRegistrationNotificationEvent(event.getPlatformComponentProfileRegistered());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteRequestListComponentRegisteredNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteRequestListComponentRegisteredNotificationEvent event) {
                handleCompleteRequestListComponentRegisteredNotificationEvent(event.getRegisteredComponentList());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteComponentConnectionRequestNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteComponentConnectionRequestNotificationEvent event) {
                handleCompleteComponentConnectionRequestNotificationEvent(event.getApplicantComponent(), event.getRemoteComponent());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         *  failure connection
         */

        fermatEventListener = getEventManager().getNewListener(P2pEventType.FAILURE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<FailureComponentConnectionRequestNotificationEvent>(this) {
            @Override
            public void processEvent(FailureComponentConnectionRequestNotificationEvent event) {
                handleFailureComponentConnectionNotificationEvent(event.getNetworkServiceApplicant(), event.getRemoteParticipant());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

                /*
         * Listen and handle VPN Connection Close Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<VPNConnectionCloseNotificationEvent>(this) {
            @Override
            public void processEvent(VPNConnectionCloseNotificationEvent event) {
                handleVpnConnectionCloseNotificationEvent(event);
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Close Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        fermatEventListener.setEventHandler(new FermatEventHandler() {
            @Override
            public void handleEvent(FermatEvent fermatEvent) throws FermatException {
                handleClientConnectionCloseNotificationEvent(fermatEvent);
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Client Connection Loose Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        fermatEventListener.setEventHandler(new FermatEventHandler() {

            @Override
            public void handleEvent(FermatEvent fermatEvent) throws FermatException {
                handleClientConnectionLooseNotificationEvent(fermatEvent);
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
        fermatEventListener.setEventHandler(new FermatEventHandler() {
            @Override
            public void handleEvent(FermatEvent fermatEvent) throws FermatException {
                handleClientSuccessfullReconnectNotificationEvent(fermatEvent);
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);



         /*
         * Listen and handle Complete Update Actor Profile Notification Event
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.COMPLETE_UPDATE_ACTOR_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<CompleteUpdateActorNotificationEvent>(this) {
            @Override
            public void processEvent(CompleteUpdateActorNotificationEvent event) {
                handleCompleteUpdateActorNotificationEvent(event.getPlatformComponentProfileUpdate());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */


        /**
         *Listen and handle the received messages
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageReceivedNotificationEvent>(this) {
            @Override
            public void processEvent(NewNetworkServiceMessageReceivedNotificationEvent event) {
                handleNewMessages((FermatMessage) event.getData());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /**
         * Listen and handle the sent messages
         */
        fermatEventListener = getEventManager().getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_SENT_NOTIFICATION);
        fermatEventListener.setEventHandler(new AbstractCommunicationBaseEventHandler<NewNetworkServiceMessageSentNotificationEvent>(this) {
            @Override
            public void processEvent(NewNetworkServiceMessageSentNotificationEvent event) {
                handleNewSentMessageNotificationEvent((FermatMessage) event.getData());
            }
        });
        getEventManager().addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);
    }

    /**
     * (non-javadoc)
     *
     * @see NetworkService#constructDiscoveryQueryParamsFactory(PlatformComponentType, NetworkServiceType,  String,String, Location, Double, String, String, Integer, Integer, PlatformComponentType, NetworkServiceType)
     */
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias,String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType, networkServiceType, alias,  identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
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
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
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

            getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection().requestListComponentRegistered(platformComponentProfile, discoveryQueryParameters);

        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getWsCommunicationsCloudClientManager(): " + getWsCommunicationsCloudClientManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + getPluginDatabaseSystem());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + getErrorManager());
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("getEventManager(): " + getEventManager());
            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

        }

    }


    private static final String PLUGIN_IDS_DIRECTORY_NAME = "security";
    private static final String PLUGIN_IDS_FILE_NAME      = "networkServiceKeyPairsFile";
    private static final String PAIR_SEPARATOR            = ";";

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

    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBaseCommunication = this.getPluginDatabaseSystem().openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(getPluginDatabaseSystem());

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_INTRAUSER_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }
}
