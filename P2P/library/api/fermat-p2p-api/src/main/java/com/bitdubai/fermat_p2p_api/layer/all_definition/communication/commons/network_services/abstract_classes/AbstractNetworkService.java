package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientConnection;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.agents.NetworkServiceRegistrationProcessAgent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientConnectionClosedEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientConnectionLostEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientNetworkServiceRegisteredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.event_handlers.NetworkClientRegisteredEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeIdentityException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeNetworkServiceProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.data_base.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
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

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    protected ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    protected EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.DEVICE_LOCATION)
    protected LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.NETWORK_CLIENT)
    protected NetworkClientManager networkClientManager;

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
     * Represents the registered
     */
    private boolean registered;

    /**
     * Hold the listeners references
     */
    protected List<FermatEventListener> listenersAdded;

    /**
     * AGENTS DEFINITION ----->
     */
    /**
     * Represent the networkServiceRegistrationProcessAgent
     */
    private NetworkServiceRegistrationProcessAgent networkServiceRegistrationProcessAgent;

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
             * Initialize listeners
             */
            initializeNetworkServiceListeners();

            /*
             * Initialize the agents and start
             */
            this.networkServiceRegistrationProcessAgent = new NetworkServiceRegistrationProcessAgent(this);
            this.networkServiceRegistrationProcessAgent.start();

            onNetworkServiceStart();

        } catch (Exception exception) {

            System.out.println(exception.toString());

            String context = "Plugin ID: " + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR
                    + "Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME
                    + "NS Name: " + "COMPLETE"; // TODO COMPLETE WITH NETWORK SERVICE NAME

            String possibleCause = "The Template triggered an unexpected problem that wasn't able to solve by itself - ";
            possibleCause += exception.getMessage();
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
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
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                throw new CantInitializeIdentityException(exception, "", "Unhandled Exception");
            }


        } catch (CantCreateFileException cantCreateFileException) {

            /*
             * The file cannot be load. We can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantCreateFileException);
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
            errorManager.reportUnexpectedPluginException(
                    this.getPluginVersionReference(),
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

    public final void handleNetworkServiceRegisteredEvent() {

        System.out.println("********** THIS NETWORK SERVICE HAS BEEN REGISTERED: " + this.getProfile());
        this.registered = Boolean.TRUE;
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

                /*
                if (communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.stop();
                }
                */

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

/*
                if (communicationNetworkServiceConnectionManager != null) {
                    communicationNetworkServiceConnectionManager.closeAllConnection();
                    communicationNetworkServiceConnectionManager.stop();
                }
*/
                this.registered = Boolean.FALSE;
/*
                communicationSupervisorPendingMessagesAgent.removeAllConnectionWaitingForResponse();

                */
                onNetworkClientConnectionClosed();

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is automatically called when the client connection was closed
     */
    protected void onNetworkClientConnectionClosed() {

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

    public final NetworkServiceProfile getProfile() {

        return profile;
    }

    public final NetworkClientConnection getConnection() {

        return networkClientManager.getConnection();
    }
}