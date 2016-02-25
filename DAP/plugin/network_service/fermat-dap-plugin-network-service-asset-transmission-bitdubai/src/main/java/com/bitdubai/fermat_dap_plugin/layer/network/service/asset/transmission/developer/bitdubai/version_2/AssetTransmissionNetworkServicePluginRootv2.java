package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.util.ActorUtils;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.DAPMessageDAO;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.IncomingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.event_handlers.NewReceiveMessagesNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.event_handlers.VPNConnectionCloseNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantInitializeDAPMessageNetworkServiceDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_2.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageReceivedNotificationEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16. - Jose Briceño josebricenor@gmail.com (22/02/16)
 */
public class AssetTransmissionNetworkServicePluginRootv2 extends AbstractNetworkServiceBase implements AssetTransmissionNetworkServiceManager, DatabaseManagerForDevelopers {

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
    private boolean register;

    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION;

    /*
    * Network Manager
    * */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;
    //private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;
    /**
     * cacha identities to register
     */
    private List<PlatformComponentProfile> actorsToRegisterCache;
    //CONSTRUCTORS

    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(getNetworkServiceProfile(), getIdentity(), wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(), errorManager, eventManager, outgoingMessageDao, incomingMessageDao);
    }

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
        System.out.println("AssetTransmissionNetworkService - Starting");

        try {

            /*
             * Initialize Developer Database Factory
             */
            initializeListener();
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dataBase = communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();
            dapMessageDAO = new DAPMessageDAO(dataBase);
            incomingMessageDao = new IncomingMessageDao(dataBase, dapMessageDAO);
            outgoingMessageDao = new OutgoingMessageDao(dataBase, dapMessageDAO);

            //Initialize the manager
            initializeCommunicationNetworkServiceConnectionManager();

            /*
            * Initialize the agent and start
            */
            //communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager);
            //communicationRegistrationProcessNetworkServiceAgent.start();
            //TODO Verificar necesidad de CommunicationRegistrationProcessNetworkServiceAgent.
            /*
             * Set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;
            this.register=Boolean.TRUE;

        } catch (CantInitializeDAPMessageNetworkServiceDatabaseException exception) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Asset User Actor Network Service Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
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

        this.register = Boolean.TRUE;
    }

    /**
     * This method is automatically called when the client connection is loose
     */
    @Override
    protected void onClientConnectionLoose() {

        this.register = Boolean.FALSE;
        if (communicationNetworkServiceConnectionManager == null) {
            this.initializeCommunicationNetworkServiceConnectionManager();
        } else {
            communicationNetworkServiceConnectionManager.restart();
        }
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
            DAPActor actorSender = dapMessage.getActorSender();
            DAPActor actorReceiver = dapMessage.getActorReceiver();
            PlatformComponentType senderType = ActorUtils.getPlatformComponentType(actorSender);
            PlatformComponentType receiverType = ActorUtils.getPlatformComponentType(actorReceiver);

            System.out.println("AssetTransmissionNetworkServicePluginRoot - Actor Sender: PK : " + actorSender.getActorPublicKey() + " - Type: " + senderType.getCode());
            System.out.println("AssetTransmissionNetworkServicePluginRoot - Actor Receiver: PK : " + actorReceiver.getActorPublicKey() + " - Type: " + receiverType.getCode());
            /*
             * If not null
             */
            System.out.println("AssetTransmissionNetworkServicePluginRoot - Sending message.....");

            FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(actorSender.getActorPublicKey(),  //Sender NetworkService
                    actorReceiver.getActorPublicKey(),   //Receiver
                    dapMessage.toXML(),                //Message Content
                    FermatMessageContentType.TEXT);//Type

            /*
             * Configure the correct status
             */
            ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

            /*
             * Save to the data base table
             */
            dapMessageDAO.create(dapMessage, MessageStatus.PENDING_TO_SEND);
            outgoingMessageDao.create(fermatMessage, dapMessage.getIdMessage().toString());
            PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorSender.getActorPublicKey(), NetworkServiceType.UNDEFINED, senderType);
            PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorReceiver.getActorPublicKey(), NetworkServiceType.UNDEFINED, receiverType);
            communicationNetworkServiceConnectionManager.connectTo(sender, getNetworkServiceProfile(), receiver);
            System.out.println("AssetTransmissionNetworkServicePluginRoot - Message sent.");
        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
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

            CantSendMessageException cantSendMessage = new CantSendMessageException(CantSendDigitalAssetMetadataException.DEFAULT_MESSAGE, FermatException.wrapException(e), context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_TRANSMISSION_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantSendMessage);

            throw cantSendMessage;
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

    private void initializeListener() {

        /*
         * Listen and handle new message receive notification
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
        //TODO Cambio de nombre

        //fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this)); //Error por nombre de la clase -v2
        eventManager.addListener(fermatEventListener);

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    //GETTER AND SETTERS

    //INNER CLASSES
}
