package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRequestProfileListException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientCall;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServicePendingMessagesSupervisorAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServiceRegistrationProcessAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.QueriesDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceQuery;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.factories.NetworkServiceDatabaseFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorFoundEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorListReceivedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientActorUnreachableEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientCallConnectedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientConnectionClosedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientConnectionLostEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientNetworkServiceRegisteredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientNewMessageTransmitEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientRegisteredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientSentMessageDeliveredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientSentMessageFailedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeIdentityException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeNetworkServiceProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories.NetworkServiceMessageFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.structure.NetworkServiceConnectionManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService</code>
 * implements the basic functionality of a network service component and define its behavior.<p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public abstract class AbstractNetworkService extends AbstractPlugin implements NetworkService {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    protected EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.DEVICE_LOCATION)
    protected LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    protected Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.NETWORK_CLIENT)
    protected NetworkClientManager networkClientManager;

    //todo: esto va por ahora, más adelante se saca si o si
    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.P2P_LAYER)
    private P2PLayerManager p2PLayerManager;

    /**
     * Represents the EVENT_SOURCE
     */
    public EventSource eventSource;

    /**
     * Represents the identity
     */
    private ECCKeyPair identity;

    /**
     * Represents the network Service Type
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represents the network service profile.
     */
    private NetworkServiceProfile profile;

    /**
     * Represents the dataBase
     */
    private Database networkServiceDatabase;

    private QueriesDao queriesDao;

    /**
     * Represents the registered
     */
    private boolean registered;

    /**
     * Holds the listeners references
     */
    protected List<FermatEventListener> listenersAdded;

    /**
     * Represents the networkServiceConnectionManager
     */
    private NetworkServiceConnectionManager networkServiceConnectionManager;

    /**
     * AGENTS DEFINITION ----->
     */
    /**
     * Represents the networkServiceRegistrationProcessAgent
     */
    private NetworkServiceRegistrationProcessAgent networkServiceRegistrationProcessAgent;

    /**
     * Represents the NetworkServicePendingMessagesSupervisorAgent
     */
    private NetworkServicePendingMessagesSupervisorAgent networkServicePendingMessagesSupervisorAgent;

    /**
     * Constructor with parameters
     *
     * @param pluginVersionReference
     * @param eventSource
     * @param networkServiceType
     */
    public AbstractNetworkService(final PluginVersionReference pluginVersionReference,
                                  final EventSource            eventSource           ,
                                  final NetworkServiceType     networkServiceType    ) {

        super(pluginVersionReference);

        this.eventSource           = eventSource;
        this.networkServiceType    = networkServiceType;

        this.registered            = Boolean.FALSE;
        this.listenersAdded        = new CopyOnWriteArrayList<>();
    }

    /**
     * (non-javadoc)
     * @see AbstractPlugin#start()
     */
    @Override
    public final void start() throws CantStartPluginException {

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {

            /*
             * Initialize the identity
             */
            initializeIdentity();

            /*
             * Initialize the profile
             */
            initializeProfile();

            /*
             * Initialize the data base
             */
            initializeDataBase();

            queriesDao = new QueriesDao(getDataBase());

            /*
             * Delete the history of queries of the network service each time we start it.
             */
            deleteQueriesHistory();

            /*
             * Initialize listeners
             */
            initializeNetworkServiceListeners();

            this.networkServiceConnectionManager = new NetworkServiceConnectionManager(this);

            /*
             * Initialize the agents and start
             */
//            this.networkServiceRegistrationProcessAgent = new NetworkServiceRegistrationProcessAgent(this);
//            this.networkServiceRegistrationProcessAgent.start();
            p2PLayerManager.register(this);

            /**
             * Start elements
             */
            onNetworkServiceStart();

            /**
             * Register Elements after Start
             */
            handleNetworkServiceRegisteredEvent();

        } catch (Exception exception) {

            System.out.println(exception.toString());

            String context = "Plugin ID: " + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "Database Name: " + NetworkServiceDatabaseConstants.DATABASE_NAME
                    + "NS Name: " + this.networkServiceType;

            String possibleCause = "The Template triggered an unexpected problem that wasn't able to solve by itself - ";
            possibleCause += exception.getMessage();
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;

        }
    }

    /**
     * This method validates if there are injected all the required resources
     * in the network service root.
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * Ask if the resources are injected.
         */
        if (networkClientManager == null ||
                pluginDatabaseSystem == null ||
                locationManager == null ||
                errorManager == null ||
                eventManager == null) {

            String context =
                    "Plugin ID: " + pluginId
                    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "networkClientManager: " + networkClientManager
                    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "pluginDatabaseSystem: " + pluginDatabaseSystem
                    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "locationManager: " + locationManager
                    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "errorManager: " + errorManager
                    + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "eventManager: " + eventManager;

            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }

    private static final String IDENTITY_FILE_DIRECTORY = "private"   ;
    private static final String IDENTITY_FILE_NAME      = "nsIdentity";

    /**
     * Initializes a key pair identity for this network service
     *
     * @throws CantInitializeIdentityException if something goes wrong.
     */
    private void initializeIdentity() throws CantInitializeIdentityException {

        try {

             /*
              * Load the file with the network service identity
              */
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            String content = pluginTextFile.getContent();

            identity = new ECCKeyPair(content);

        } catch (FileNotFoundException e) {

            /*
             * The file does not exist, maybe it is the first time that the plugin had been run on this device,
             * We need to create the new network service identity
             */
            try {

                /*
                 * Create the new network service identity
                 */
                identity = new ECCKeyPair();

                /*
                 * save into the file
                 */
                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, IDENTITY_FILE_DIRECTORY, IDENTITY_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.setContent(identity.getPrivateKey());
                pluginTextFile.persistToMedia();

            } catch (Exception exception) {
                /*
                 * The file cannot be created. We can not handle this situation.
                 */
                this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantInitializeIdentityException(exception, "", "Unhandled Exception");
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. We can not handle this situation.
             */
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
            throw new CantInitializeIdentityException(cantCreateFileException, "", "Error creating the identity file.");

        }

    }

    /**
     * Initializes the profile of this network service
     *
     * @throws CantInitializeNetworkServiceProfileException if something goes wrong.
     */
    private void initializeProfile() throws CantInitializeNetworkServiceProfileException {

        Location location;

        try {

            location = locationManager.getLastKnownLocation();

        } catch (CantGetDeviceLocationException exception) {

            location = null;
            // TODO MANAGE IN OTHER WAY...
            this.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception
            );
        }
        this.profile = new NetworkServiceProfile();

        this.profile.setIdentityPublicKey(this.identity.getPublicKey());
        this.profile.setNetworkServiceType(this.networkServiceType);
        this.profile.setLocation(location);

    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeNetworkServiceDatabaseException
     */
    private void initializeDataBase() throws CantInitializeNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.networkServiceDatabase = this.pluginDatabaseSystem.openDatabase(pluginId, NetworkServiceDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            NetworkServiceDatabaseFactory networkServiceDatabaseFactory = new NetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.networkServiceDatabase = networkServiceDatabaseFactory.createDatabase(pluginId, NetworkServiceDatabaseConstants.DATABASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeNetworkServiceDatabaseException(cantOpenDatabaseException);

            }
        }

    }

    /**
     * Initializes all event listener and configure
     */
    private void initializeNetworkServiceListeners() {

        /*
         * 1. Listen and handle Network Client Registered Event
         */
        FermatEventListener networkClientRegistered = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_REGISTERED);
        networkClientRegistered.setEventHandler(new NetworkClientRegisteredEventHandler(this));
        eventManager.addListener(networkClientRegistered);
        listenersAdded.add(networkClientRegistered);

        /*
         * 2. Listen and handle Network Client Network Service Registered Event
         */
        FermatEventListener networkServiceProfileRegisteredListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_NETWORK_SERVICE_PROFILE_REGISTERED);
        networkServiceProfileRegisteredListener.setEventHandler(new NetworkClientNetworkServiceRegisteredEventHandler(this));
        eventManager.addListener(networkServiceProfileRegisteredListener);
        listenersAdded.add(networkServiceProfileRegisteredListener);

        /*
         * 3. Listen and handle Network Client Connection Closed Event
         */
        FermatEventListener connectionClosedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNECTION_CLOSED);
        connectionClosedListener.setEventHandler(new NetworkClientConnectionClosedEventHandler(this));
        eventManager.addListener(connectionClosedListener);
        listenersAdded.add(connectionClosedListener);

        /*
         * 4. Listen and handle Network Client Connection Lost Event
         */
        FermatEventListener connectionLostListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CONNECTION_LOST);
        connectionLostListener.setEventHandler(new NetworkClientConnectionLostEventHandler(this));
        eventManager.addListener(connectionLostListener);
        listenersAdded.add(connectionLostListener);

         /*
         * 5. Listen and handle Network Client Sent Message Delivered Event
         */
        FermatEventListener networkClientCallConnectedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_CALL_CONNECTED);
        networkClientCallConnectedListener.setEventHandler(new NetworkClientCallConnectedEventHandler(this));
        eventManager.addListener(networkClientCallConnectedListener);
        listenersAdded.add(networkClientCallConnectedListener);

        /*
         * 6. Listen and handle Actor Found Event
         */
        FermatEventListener actorFoundListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_FOUND);
        actorFoundListener.setEventHandler(new NetworkClientActorFoundEventHandler(this));
        eventManager.addListener(actorFoundListener);
        listenersAdded.add(actorFoundListener);

        /*
         * 7. Listen and handle Network Client New Message Transmit Event
         */
        FermatEventListener newMessageTransmitListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT);
        newMessageTransmitListener.setEventHandler(new NetworkClientNewMessageTransmitEventHandler(this));
        eventManager.addListener(newMessageTransmitListener);
        listenersAdded.add(newMessageTransmitListener);

        /*
         * 8. Listen and handle Network Client Sent Message Delivered Event
         */
        FermatEventListener sentMessageDeliveredListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_DELIVERED);
        sentMessageDeliveredListener.setEventHandler(new NetworkClientSentMessageDeliveredEventHandler(this));
        eventManager.addListener(sentMessageDeliveredListener);
        listenersAdded.add(sentMessageDeliveredListener);

        /*
         * 9. Listen and handle Network Client Actor Unreachable Event
         */
        FermatEventListener actorUnreachableListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_UNREACHABLE);
        actorUnreachableListener.setEventHandler(new NetworkClientActorUnreachableEventHandler(this));
        eventManager.addListener(actorUnreachableListener);
        listenersAdded.add(actorUnreachableListener);

        /*
         * 10. Listen and handle Network Client Actor List Received Event
         */
        FermatEventListener actorListReceivedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_ACTOR_LIST_RECEIVED);
        actorListReceivedListener.setEventHandler(new NetworkClientActorListReceivedEventHandler(this));
        eventManager.addListener(actorListReceivedListener);
        listenersAdded.add(actorListReceivedListener);

        /*
         * 11. Listen and handle Network Client Sent Message Failed Event
         */
        FermatEventListener sentMessageFailedListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_SENT_MESSAGE_FAILED);
        sentMessageFailedListener.setEventHandler(new NetworkClientSentMessageFailedEventHandler(this));
        eventManager.addListener(sentMessageFailedListener);
        listenersAdded.add(sentMessageFailedListener);

    }

    private void deleteQueriesHistory() throws CantDeleteRecordDataBaseException {

        queriesDao.deleteAll();
    }

    public final void handleNetworkClientRegisteredEvent(final CommunicationChannels communicationChannel) throws FermatException {

        if(networkServiceRegistrationProcessAgent != null && networkServiceRegistrationProcessAgent.getActive()) {
            networkServiceRegistrationProcessAgent.stop();
            networkServiceRegistrationProcessAgent = null;
        }

        if (this.getConnection().isConnected() && this.getConnection().isRegistered())
            this.getConnection().registerProfile(this.getProfile());
        else {
            this.networkServiceRegistrationProcessAgent = new NetworkServiceRegistrationProcessAgent(this);
            this.networkServiceRegistrationProcessAgent.start();
        }

    }

    public final void handleNetworkClientCallConnected(NetworkClientCall networkClientCall) {

        Map<String, Object> filters = new HashMap<>();
        filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());
        filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, networkClientCall.getProfile().getIdentityPublicKey());

        try {
            synchronized (this) {
                /*
                 * Read all pending message from database
                 */
                List<NetworkServiceMessage> messages = getNetworkServiceConnectionManager().getOutgoingMessagesDao().findAllPendingToSendByPublicKey(filters);

                /*
                 * For each message
                 */
                for (NetworkServiceMessage message : messages) {
//                    System.out.println("12345P2P Estado de conexión = "+networkClientCall.isConnected());
//                    System.out.println("12345P2P Intentando enviar mensaje= " +message.getContent());

                    if (networkClientCall.isConnected() && (message.getFermatMessagesStatus() == FermatMessagesStatus.PENDING_TO_SEND)) {
//                        System.out.println("12345P2P INSIDE");
//                        System.out.println("12345** --Estado= " +message.getFermatMessagesStatus());

                        networkClientCall.sendPackageMessage(message);

                        /*
                         * Change the message and update in the data base
                         */
                        message.setFermatMessagesStatus(FermatMessagesStatus.SENT);
                        getNetworkServiceConnectionManager().getOutgoingMessagesDao().update(message);

                    } else {
                        System.out.println("networkClientCall - Connection is connected = " + networkClientCall.isConnected());
                    }

                }
                /*
                 * Hang up the call
                 */
                networkClientCall.hangUp();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public final void handleNetworkClientActorListReceivedEvent(final UUID                    queryId      ,
                                                                final List<ActorProfile> actorProfiles) throws CantReadRecordDataBaseException, RecordNotFoundException, CantUpdateRecordDataBaseException {


        NetworkServiceQuery query = queriesDao.findById(queryId.toString());

        queriesDao.markAsDone(query);

        onNetworkServiceActorListReceived(query, actorProfiles);

    }

    /**
     * By default it will broadcast the information in a fermat bundle, but it is overridable.
     *
     * @param query
     * @param actorProfiles
     */
    public void onNetworkServiceActorListReceived(final NetworkServiceQuery      query        ,
                                                  final List<ActorProfile>  actorProfiles) {

        FermatBundle bundle = new FermatBundle();
        bundle.put("actorProfiles", actorProfiles);

        broadcaster.publish(BroadcasterType.UPDATE_VIEW, bundle, query.getBroadcastCode());

    }

    /**
     * Through this method you can handle the actor found event for the actor trace that you could have done.
     *
     * @param actorProfile an instance of the actor profile
     */
    public void handleActorFoundEvent(ActorProfile actorProfile){

    }

    public void handleActorUnreachableEvent(ActorProfile actorProfile) {

        checkFailedSentMessages(actorProfile.getIdentityPublicKey());
        onActorUnreachable(actorProfile);
    }

    /**
     * Through this method you can handle the actor found event for the actor trace that you could have done.
     *
     * @param actorProfile an instance of the actor profile
     */
    public void onActorUnreachable(ActorProfile actorProfile){

    }

    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage received
     */
    public final void onMessageReceived(String incomingMessage) {

        try {

            NetworkServiceMessage networkServiceMessage = NetworkServiceMessage.parseContent(incomingMessage);

            //TODO networkServiceMessage.setContent(AsymmetricCryptography.decryptMessagePrivateKey(networkServiceMessage.getContent(), this.identity.getPrivateKey()));
           /*
            * process the new message receive
            */
            networkServiceMessage.setFermatMessagesStatus(FermatMessagesStatus.NEW_RECEIVED);

            NetworkServiceMessage networkServiceMessageOld;

            try {
                networkServiceMessageOld = networkServiceConnectionManager.getIncomingMessagesDao().findById(networkServiceMessage.getId().toString());
                if(networkServiceMessageOld!=null && networkServiceMessageOld.equals(networkServiceMessage)) {
                    System.out.println("***************** MESSAGE DUPLICATED. IGNORING MESSAGE *****************");
                    return;
                }

                if(networkServiceMessageOld!=null){
                    System.out.println("***************** ID DUPLICATED. GENERATING A NEW ONE *****************");
                    networkServiceMessage.setId(UUID.randomUUID());
                }
            }catch(CantReadRecordDataBaseException | RecordNotFoundException e){
                e.printStackTrace();
            }

            networkServiceConnectionManager.getIncomingMessagesDao().create(networkServiceMessage);
            networkServiceConnectionManager.getNetworkServiceRoot().onNewMessageReceived(networkServiceMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void handleNetworkServiceRegisteredEvent() {

        this.registered = Boolean.TRUE;

        try {
            if (networkServicePendingMessagesSupervisorAgent == null)
                this.networkServicePendingMessagesSupervisorAgent = new NetworkServicePendingMessagesSupervisorAgent(this);

            this.networkServicePendingMessagesSupervisorAgent.start();
            System.out.println("12345CBP handleNetworkServiceRegisteredEvent starteado");
        } catch (Exception ex) {
            System.out.println("Failed to start the messages supervisor agent - > NS: " + this.getProfile().getNetworkServiceType());
        }

        onNetworkServiceRegistered();
    }

    protected void onNetworkServiceRegistered() {

    }

    protected void onNetworkServiceStart() throws CantStartPluginException {

    }

    /**
     * Handle the event NetworkClientConnectionLostEvent
     * @param communicationChannel
     */
    public final void handleNetworkClientConnectionLostEvent(final CommunicationChannels communicationChannel) {

        try {

            if(!networkClientManager.getConnection().isRegistered()) {

                try {
                    if (networkServicePendingMessagesSupervisorAgent != null)
                        this.networkServicePendingMessagesSupervisorAgent.pause();
                } catch (Exception ex) {
                    System.out.println("Failed to pause the messages supervisor agent - > NS: "+this.getProfile().getNetworkServiceType());
                }

                this.registered = Boolean.FALSE;
/*
                reprocessMessages();
*/
                onNetworkClientConnectionLost();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is automatically called when the client connection was lost
     */
    protected void onNetworkClientConnectionLost() {

    }

    /**
     * Handle the event NetworkClientConnectionClosedEvent
     * @param communicationChannel
     */
    public final void handleNetworkClientConnectionClosedEvent(final CommunicationChannels communicationChannel) {

        try {

            if(!networkClientManager.getConnection().isRegistered()) {

                try {
                    if (networkServicePendingMessagesSupervisorAgent != null)
                        this.networkServicePendingMessagesSupervisorAgent.pause();
                } catch (Exception ex) {
                    System.out.println("Failed to pause the messages supervisor agent - > NS: "+this.getProfile().getNetworkServiceType());
                }

                this.registered = Boolean.FALSE;

                onNetworkClientConnectionClosed();

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method tha send a new Message
     */
    public void sendNewMessage(NetworkServiceProfile destination, String messageContent) throws CantSendMessageException {

        try {

            /*
             * Created the message
             */
            NetworkServiceMessage networkServiceMessage = NetworkServiceMessageFactory.buildNetworkServiceMessage(
                    this.getProfile(),
                    destination      ,
                    messageContent,
                    MessageContentType.TEXT
            );

            /*
             * Save to the data base table
             */
            networkServiceConnectionManager.getOutgoingMessagesDao().create(networkServiceMessage);

            /*
             * Ask the client to connect
             */
            networkServiceConnectionManager.connectTo(destination);

        } catch (Exception e){

            System.out.println("Error sending message: " + e.getMessage());
            throw new CantSendMessageException(e, "destination: "+destination+" - message: "+messageContent, "Unhandled error trying to send a message.");
        }
    }

    /**
     * Check fail sent messages.
     * When a call to an actor fails then we update the fail count of the messages sent to it.
     * Then the message will be sent again after a amount of time defined in the message sender supervisor agent.
     * If the message stays more than three days not being sent then we're going to delete it.
     *
     * @param destinationPublicKey of the actor which we're sending the messages.
     */
    private void checkFailedSentMessages(final String destinationPublicKey){

        try {

            /*
             * Read all pending message from database
             */
            Map<String, Object> filters = new HashMap<>();
            filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_RECEIVER_PUBLIC_KEY_COLUMN_NAME, destinationPublicKey                    );
            filters.put(NetworkServiceDatabaseConstants.OUTGOING_MESSAGES_STATUS_COLUMN_NAME, MessagesStatus.PENDING_TO_SEND.getCode());

            List<NetworkServiceMessage> messages = getNetworkServiceConnectionManager().getOutgoingMessagesDao().findAll(filters);

            for (NetworkServiceMessage fermatMessageCommunication: messages) {

                /*
                 * Increment the fail count field
                 */
                fermatMessageCommunication.setFailCount(fermatMessageCommunication.getFailCount() + 1);

                if(fermatMessageCommunication.getFailCount() > 10) {

                    /*
                     * Calculate the date
                     */
                    long sentDate = fermatMessageCommunication.getShippingTimestamp().getTime();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;
                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    /*
                     * if have mora that 3 days
                     */
                    if ((int) dias > 3) {
                        getNetworkServiceConnectionManager().getOutgoingMessagesDao().delete(fermatMessageCommunication.getId().toString());
                    }
                } else {
                    getNetworkServiceConnectionManager().getOutgoingMessagesDao().update(fermatMessageCommunication);
                }

            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Method tha send a new Message
     */
    public UUID onlineActorsDiscoveryQuery(final DiscoveryQueryParameters discoveryQueryParameters,
                                           final String                   broadcastCode           ) throws CantRequestProfileListException {

        try {

            System.out.println("-------------- online actors discovery query requested: " + discoveryQueryParameters + " \n------------- " + new Timestamp(System.currentTimeMillis()));

            UUID queryId = getConnection().onlineActorsDiscoveryQuery(discoveryQueryParameters, getPublicKey());

            /*
             * Create the query
             */
            NetworkServiceQuery networkServiceQuery = new NetworkServiceQuery(
                    queryId,
                    broadcastCode      ,
                    discoveryQueryParameters,
                    System.currentTimeMillis(),
                    QueryTypes.ACTOR_LIST,
                    QueryStatus.REQUESTED
            );

            queriesDao.create(networkServiceQuery);

            return queryId;

        }catch (Exception e){

            System.out.println("Error sending query request: " + e.getMessage());
            throw new CantRequestProfileListException(e, "discoveryQueryParameters: "+discoveryQueryParameters+" - broadcastCode: "+broadcastCode, "Unhandled error trying to send a query request.");
        }
    }

    /**
     * Method tha send a new Message
     */
    public void sendNewMessage(ActorProfile sender, ActorProfile destination, String messageContent) throws CantSendMessageException {

        try {

            /*
             * Created the message
             */
            NetworkServiceMessage networkServiceMessage = NetworkServiceMessageFactory.buildNetworkServiceMessage(
                    sender           ,
                    destination      ,
                    this.getProfile(),
                    messageContent   ,
                    MessageContentType.TEXT
            );

            /*
             * Save to the data base table
             */
            networkServiceConnectionManager.getOutgoingMessagesDao().create(networkServiceMessage);

            /*
             * Ask the client to connect
             */
            networkServiceConnectionManager.connectTo(destination);

        }catch (Exception e){

            System.out.println("Error sending message: " + e.getMessage());
            throw new CantSendMessageException(e, "destination: "+destination+" - message: "+messageContent, "Unhandled error trying to send a message.");
        }
    }

    /**
     * This method is automatically called when the network service receive
     * a new message
     *
     * @param messageReceived
     */
    public synchronized void onNewMessageReceived(NetworkServiceMessage messageReceived) {

        System.out.println("Me llego un nuevo mensaje" + messageReceived);
    }



    public final synchronized void onNetworkServiceSentMessage(NetworkServiceMessage networkServiceMessage) {

        System.out.println("Message Delivered " + networkServiceMessage);

        //networkServiceMessage.setContent(AsymmetricCryptography.decryptMessagePrivateKey(networkServiceMessage.getContent(), this.identity.getPrivateKey()));

        try {
            networkServiceConnectionManager.getOutgoingMessagesDao().markAsDelivered(networkServiceMessage);

            onSentMessage(networkServiceMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public final synchronized void onNetworkServiceFailedMessage(NetworkServiceMessage networkServiceMessage) {

        System.out.println("12345P2P onNetworkServiceFailedMessage Message failed " + networkServiceMessage.toJson());

        try {
            networkServiceConnectionManager.getOutgoingMessagesDao().markAsPendingToSend(networkServiceMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void onSentMessage(NetworkServiceMessage networkServiceMessage) {

    }

    /**
     * Get the database instance
     * @return Database
     */
    public Database getDataBase() {
        return this.networkServiceDatabase;
    }

    /**
     * This method is automatically called when the client connection was closed
     */
    protected void onNetworkClientConnectionClosed() {

    }

    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return networkServiceConnectionManager;
    }

    public LocationManager getLocationManager() {

        return locationManager;
    }

    public EventManager getEventManager() {

        return eventManager;
    }

    /**
     * Get registered value
     *
     * @return boolean
     */
    public final boolean isRegistered() {
        return registered;
    }

    public final String getPublicKey() {

        return this.identity.getPublicKey();
    }

    public ECCKeyPair getIdentity() {

        return identity;
    }


    public final NetworkServiceProfile getProfile() {

        return profile;
    }

    public final NetworkClientConnection getConnection() {

        return networkClientManager.getConnection();
    }

    public final NetworkClientConnection getConnection(String uriToNode) {

        return networkClientManager.getConnection(uriToNode);
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public void startConnection() throws CantRegisterProfileException {
        getConnection().registerProfile(getProfile());
    }


}
