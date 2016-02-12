package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications.IncomingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public class AssetTransmissionNetworkServicePluginRootv2 extends AbstractNetworkServiceBase
        implements AssetTransmissionNetworkServiceManager {

    //VARIABLE DECLARATION

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;


    /**
     * Represent the dataBase
     */
    private Database dataBaseCommunication;

    private Database dataBase;

    /**
     * DAO
     */
    private IncomingMessageDao incomingMessageDao;
    private OutgoingMessageDao outgoingMessageDao;
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;
    //CONSTRUCTORS

    /**
     * Constructor with parameters
     */
    public AssetTransmissionNetworkServicePluginRootv2() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_INTRA_ACTOR,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.INTRA_USER,
                "Asset Transmission Network Service",
                null);
        this.actorsToRegisterCache = new ArrayList<>();
    }

    //PUBLIC METHODS

    /**
     * This method is called when the network service method
     * AbstractPlugin#start() is called
     */
    @Override
    protected void onStart() {

    }

    /**
     * This method is automatically called when the network service receive
     * a new message
     *
     * @param newFermatMessageReceive
     */
    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {

    }

    /**
     * This method is automatically called when the network service receive
     * a new message was sent
     *
     * @param messageSent
     */
    @Override
    public void onSentMessage(FermatMessage messageSent) {

    }

    /**
     * This method is automatically called when the network service is registered
     */
    @Override
    protected void onNetworkServiceRegistered() {

    }

    /**
     * This method is automatically called when the client connection is close
     */
    @Override
    protected void onClientConnectionClose() {

    }

    /**
     * This method is automatically called when the client connection is Successful reconnect
     */
    @Override
    protected void onClientSuccessfulReconnect() {

    }

    /**
     * This method is automatically called when the client connection is loose
     */
    @Override
    protected void onClientConnectionLoose() {

    }

    /**
     * This method is automatically called when a component connection request is fail
     *
     * @param remoteParticipant
     */
    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile
                                                               remoteParticipant) {

    }

    /**
     * This method is automatically called when the list of platform Component Profile Registered
     * is received for the network service
     *
     * @param remotePlatformComponentProfileRegisteredList
     */
    @Override
    protected void onReceivePlatformComponentProfileRegisteredList
    (CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    /**
     * This method is automatically called when the request to update a actor profile is complete
     *
     * @param platformComponentProfileUpdate
     */
    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile
                                                        platformComponentProfileUpdate) {

    }

    /**
     * This method is automatically called when the request of component registrations is fail
     *
     * @param platformComponentProfile
     */
    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile
                                                          platformComponentProfile) {

    }

    /**
     * This method sends the digital asset metadata to a remote device.
     *
     * @param dapMessage {@link DAPMessage <AssetMetadataContentMessage>} instance, which should contain all the information to send
     *                   this metadata to the destination device.
     * @throws CantSendDigitalAssetMetadataException
     */
    @Override
    public void sendDigitalAssetMetadata(DAPMessage dapMessage) throws CantSendDigitalAssetMetadataException {

    }

    /**
     * Method that send the Transaction New Status Notification
     *
     * @param actorSenderPublicKey   {@link String} that represents the public key from the actor that sends the message.
     * @param senderType             {@link PlatformComponentType} that represents the type of actor that sends the message
     * @param actorReceiverPublicKey {@link String} that represents the public key from the actor that receives the message.
     * @param receiverType           {@link PlatformComponentType} that represents the type of actor that receives the message
     * @param genesisTransaction     {@link String} the genesisTransaction related with the status notification
     * @param newDistributionStatus  {@link DistributionStatus} with the new status for the transaction.
     * @throws CantSendTransactionNewStatusNotificationException in case something goes wrong while trying to send the message.
     */
    @Override
    public void sendTransactionNewStatusNotification(String actorSenderPublicKey, PlatformComponentType senderType, String actorReceiverPublicKey, PlatformComponentType receiverType, String genesisTransaction, DistributionStatus newDistributionStatus) throws CantSendTransactionNewStatusNotificationException {

    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {

    }

    @Override
    public List<Transaction<DigitalAssetMetadataTransaction>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return null;
    }


    /**
     * This method is automatically called  when the client connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     */
    @Override
    protected void reprocessMessages() {

    }

    /**
     * This method is automatically called  when the vpn connection is close
     * for the network service when need to update message state,
     * is his define protocol are no complete
     * and change incomplete message process to his original state
     * for reprocessing again
     *
     * @param identityPublicKey
     */
    @Override
    protected void reprocessMessages(String identityPublicKey) {

    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    /**
     * Get the CommunicationsClientConnection instance
     *
     * @return CommunicationsClientConnection
     */
    @Override
    protected CommunicationsClientConnection getCommunicationsClientConnection() {
        return null;
    }

    /**
     * Get the ErrorManager instance
     *
     * @return ErrorManager
     */
    @Override
    public ErrorManager getErrorManager() {
        return null;
    }

    /**
     * Get the EventManager instance
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager() {
        return null;
    }

    /**
     * Get the WsCommunicationsCloudClientManager instance
     *
     * @return WsCommunicationsCloudClientManager
     */
    @Override
    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return null;
    }

    /**
     * Get the PluginDatabaseSystem instance
     *
     * @return PluginDatabaseSystem
     */
    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return null;
    }

    /**
     * Get the PluginFileSystem instance
     *
     * @return PluginFileSystem
     */
    @Override
    public PluginFileSystem getPluginFileSystem() {
        return null;
    }

    /**
     * Get the Broadcaster instance
     *
     * @return Broadcaster
     */
    @Override
    public Broadcaster getBroadcaster() {
        return null;
    }

    /**
     * Get the LogManager instance
     *
     * @return LogManager
     */
    @Override
    public LogManager getLogManager() {
        return null;
    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return null;
    }

    /**
     * This method is automatically called when the CommunicationSupervisorPendingMessagesAgent is trying to request
     * a new connection for a message pending to send. This method need construct the profile specific to
     * the network service work.
     * <p/>
     * Example: Is the network service work with actor this profile has to mach with the actor.
     * <p/>
     * <code>
     *
     * @param identityPublicKeyDestination
     * @return PlatformComponentProfile
     * @overray public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
     * <p/>
     * return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
     * .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
     * actor.getAlias(),
     * actor.getName(),
     * NetworkServiceType.UNDEFINED,
     * PlatformComponentType.ACTOR_INTRA_USER,
     * "");
     * <p/>
     * }
     * <p/>
     * </code>
     */
    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return null;
    }

    //INNER CLASSES
}
