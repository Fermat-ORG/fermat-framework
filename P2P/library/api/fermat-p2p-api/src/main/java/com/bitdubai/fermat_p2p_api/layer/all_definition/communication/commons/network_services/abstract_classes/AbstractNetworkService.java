package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationUtil;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.client.request.ActorListMsgRequest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeIdentityException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantInitializeNetworkServiceProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.factories.NetworkServiceMessageFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NetworkServiceProfile;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels;

import java.util.List;
import java.util.UUID;

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

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.DEVICE_LOCATION)
    protected LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

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
     * Represents the registered
     */
    private boolean registered;

    /**
     * Constructor with parameters
     *
     * @param pluginVersionReference
     * @param eventSource
     * @param networkServiceType
     */
    public AbstractNetworkService(final PluginVersionReference pluginVersionReference,
                                  final EventSource eventSource,
                                  final NetworkServiceType networkServiceType) {

        super(pluginVersionReference);

        this.eventSource           = eventSource;
        this.networkServiceType    = networkServiceType;

        this.registered            = Boolean.FALSE;

    }

    /**
     * (non-javadoc)
     * @see AbstractPlugin#start()
     */
    @Override
    public synchronized final void start() throws CantStartPluginException {

        try {

            /*
             * Initialize the identity
             */
            initializeIdentity();

            /*
             * Initialize the profile
             */
            initializeProfile();

            /**
             * Start elements
             */
            onNetworkServiceStart();

            p2PLayerManager.register(this);

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception exception) {

            String context = "Plugin ID: " + pluginId + CantStartPluginException.CONTEXT_CONTENT_SEPARATOR + "NS Name: " + this.networkServiceType;

            String possibleCause = "The Template triggered an unexpected problem that wasn't able to solve by itself - ";
            possibleCause += exception.getMessage();
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

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
            //ramdom location
            location = LocationUtil.ramdomLocation(); //locationManager.getLastKnownLocation();

        } catch (Exception exception) {

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

    public void handleNetworkClientActorListReceivedEvent(final UUID               queryId      ,
                                                          final List<ActorProfile> actorProfiles) {


        FermatBundle bundle = new FermatBundle();
        bundle.put("actorProfiles", actorProfiles);

        System.out.println("AbstractNs: onNetworkServiceActorListReceived");
//        broadcaster.publish(BroadcasterType.UPDATE_VIEW, bundle, query.getBroadcastCode());
    }



    public void handleProfileRegisteredSuccessfully(ProfileTypes types, UUID packageId, String profilePublicKey) {

        if (types.equals(ProfileTypes.NETWORK_SERVICE))
            onNetworkServiceRegistered();
    }

    /**
     * Notify the client when a incoming message is receive by the incomingTemplateNetworkServiceMessage
     * ant fire a new event
     *
     * @param incomingMessage
     */
    public final void onMessageReceived(NetworkServiceMessage incomingMessage) {

        try {

            //TODO networkServiceMessage.setContent(AsymmetricCryptography.decryptMessagePrivateKey(networkServiceMessage.getContent(), this.identity.getPrivateKey()));

            onNewMessageReceived(incomingMessage);

        } catch (Exception e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    public final void handleNetworkServiceRegisteredEvent() {

        this.registered = Boolean.TRUE;

        try {

            onNetworkServiceRegistered();

        } catch (Exception ex) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
        }


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

        this.registered = Boolean.FALSE;

        try {
            onNetworkClientConnectionLost();
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
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


        this.registered = Boolean.FALSE;

        try {
            onNetworkClientConnectionClosed();
        } catch (Exception exception) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    /**
     * Method that send a new Message
     *
     * @Exceptions MessageTooBigException
     */
    public final UUID sendNewMessage(final ActorProfile sender        ,
                                     final ActorProfile destination   ,
                                     final String       messageContent,
                                     final boolean p2pLayerMonitoring) throws CantSendMessageException {

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

            return p2PLayerManager.sendMessage(
                    networkServiceMessage,
                    getNetworkServiceType(),
                    destination.getHomeNodePublicKey(),
                    p2pLayerMonitoring);

        } catch (Exception e){

            System.out.println("Error sending message: " + e.getMessage());
            throw new CantSendMessageException(e, "destination: "+destination+" - message: "+messageContent, "Unhandled error trying to send a message.");
        }
    }


    /**
     *
     * @param discoveryQueryParameters
     * @param requesterPublicKey
     * @return
     * @throws com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException
     * @Exceptions MessageTooBigException
     */
    public final UUID discoveryActorProfiles(final DiscoveryQueryParameters discoveryQueryParameters, String requesterPublicKey) throws com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException {

         /*
         * Create the query
         */
        ActorListMsgRequest actorListMsgRequest = new ActorListMsgRequest(networkServiceType.getCode(),discoveryQueryParameters, requesterPublicKey);

        return p2PLayerManager.sendDiscoveryMessage(actorListMsgRequest, networkServiceType, null);


    }

    protected final UUID subscribeActorOnline(String remoteActorPk) throws com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException {
        return p2PLayerManager.subscribeActorOnlineEvent(getNetworkServiceType(), remoteActorPk);
    }

    protected final UUID unSubscribeActorOnline(UUID previousSubscribePackageId) throws com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantSendMessageException {
        return p2PLayerManager.unSubscribeActorOnlineEvent(getNetworkServiceType(), previousSubscribePackageId);
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

    public final synchronized void handleOnMessageSent(UUID messageId) {

        System.out.println("Message Delivered id: " + messageId);

        //networkServiceMessage.setContent(AsymmetricCryptography.decryptMessagePrivateKey(networkServiceMessage.getContent(), this.identity.getPrivateKey()));

        try {

            onSentMessage(messageId);
        } catch (Exception e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    public final synchronized void handleOnMessageFail(UUID messageId) {

        System.out.println("Message not delivered " + messageId);

        //networkServiceMessage.setContent(AsymmetricCryptography.decryptMessagePrivateKey(networkServiceMessage.getContent(), this.identity.getPrivateKey()));

        try {
            onMessageFail(messageId);
        } catch (Exception e) {
            this.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

    }

    public final void handleOnNodeEventArrive(UUID packageId) {
        onNodeEventArrive(packageId);
    }

    /**
     * Method to override if you want to subscribe and listen for event from node
     * @param eventPackageId
     */
    public void onNodeEventArrive(UUID eventPackageId){

    }

    public synchronized void onSentMessage(UUID messageId) {

    }

    public synchronized void onMessageFail(UUID messageId) {

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

    public ECCKeyPair getIdentity() {

        return identity;
    }

    public final NetworkServiceProfile getProfile() {

        return profile;
    }

    public final P2PLayerManager getConnection() {

        return p2PLayerManager;
    }

    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

}