package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPNetworkService;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.DAPMessageDAO;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.IncomingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16. - Jose Briceño josebricenor@gmail.com (22/02/16)
 */
public class AssetTransmissionNetworkServicePluginRootv2 extends AbstractNetworkServiceBase implements DAPNetworkService {

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
    private DAPMessageDAO dapMessageDAO;
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
                EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ASSET_TRANSMISSION,
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
    protected void onStart() throws CantStartPluginException {
        createDatabase();

        try {
            super.start();
        } catch (CantStartPluginException e) {
            throw new CantStartPluginException(FermatException.wrapException(e));
        }
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
     * @param dapMessage the message to be sent, this message has to contain both the actor
     *                   that sent the message and the actor that will receive the message.
     * @throws CantSendMessageException
     */
    @Override
    public void sendMessage(DAPMessage dapMessage) throws CantSendMessageException {

        try {
            dapMessageDAO.create(dapMessage, MessageStatus.PENDING_TO_SEND);
        } catch (CantInsertRecordDataBaseException e) {
            throw new CantSendMessageException();
        }
    }

    /**
     * This method retrieves the list of new incoming and unread DAP Messages for a specific type.
     *
     * @param type The {@link DAPMessageType} of message to search for.
     * @return {@link List} instance filled with all the {@link DAPMessage} that were found.
     * @throws CantGetDAPMessagesException If there was an error while querying for the list.
     */
    @Override
    public List<DAPMessage> getUnreadDAPMessagesByType(DAPMessageType type) throws CantGetDAPMessagesException {

        try {
            return dapMessageDAO.findUnreadByType(type.getCode());
        } catch (CantReadRecordDataBaseException e) {
            throw new CantGetDAPMessagesException();
        }
    }

    @Override
    public List<DAPMessage> getUnreadDAPMessageBySubject(DAPMessageSubject subject) throws CantGetDAPMessagesException {

        try {
            return dapMessageDAO.findUnreadBySubject(subject.getCode());
        } catch (CantReadRecordDataBaseException e) {
            throw new CantGetDAPMessagesException();
        }
    }

    @Override
    public void confirmReception(DAPMessage message) throws CantUpdateMessageStatusException {

        try {
            dapMessageDAO.confirmDAPMessageReception(message);
        } catch (CantUpdateRecordDataBaseException e) {
            throw new CantUpdateMessageStatusException();
        }

    }

    //PRIVATE METHODS

    private void createDatabase() {
        CommunicationNetworkServiceDatabaseFactory databaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem,pluginId);

        try {
            dataBase = databaseFactory.createDatabase();
        } catch (CantCreateDatabaseException e) {
            e.printStackTrace();
        }
    }

    //GETTER AND SETTERS

    //INNER CLASSES
}
