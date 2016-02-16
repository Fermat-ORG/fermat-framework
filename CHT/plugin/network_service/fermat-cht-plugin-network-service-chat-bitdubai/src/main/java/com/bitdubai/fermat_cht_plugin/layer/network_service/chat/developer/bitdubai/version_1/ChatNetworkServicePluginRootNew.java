package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.IncomingNotificationDAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.OutgoingNotificationDAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.communications.CommunicationChatNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.EncodeMsjContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantSendMessageException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mati on 2016.02.05..
 */
public class ChatNetworkServicePluginRootNew extends AbstractNetworkServiceBase implements NetworkServiceChatManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {


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
     * Represent the intraActorDataBase
     */
    private Database dataBaseCommunication;

    //private Database intraActorDataBase;

    /**
     * DAO
     */
    private IncomingNotificationDAO incomingNotificationsDao;
    private OutgoingNotificationDAO outgoingNotificationDao;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private ChatNetworkServiceDeveloperDatabaseFactory chatNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_CHAT;


    /**
     * cacha identities to register
     */
   // private List<PlatformComponentProfile> actorsToRegisterCache;

    private long reprocessTimer =  300000; //five minutes

    private Timer timer = new Timer();

    /**
     * Executor
     */
    ExecutorService executorService;

    /**
     * Constructor with parameters
     *
     */
    public ChatNetworkServicePluginRootNew() {
        super(new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_CHAT,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CHAT,
                "Chat Network Service",
                null);
        //this.actorsToRegisterCache = new ArrayList<>();

    }

    public IncomingNotificationDAO getIncomingNotificationsDao() {
        return incomingNotificationsDao;
    }

    public OutgoingNotificationDAO getOutgoingNotificationDao() {
        return outgoingNotificationDao;
    }

    @Override
    protected void onStart() {

        try {
        /*
         * Initialize the data base
         */
            initializeDb();
        /*
         * Initialize cache data base
         */
           // initializeCacheDb();

        /*
         * Initialize Developer Database Factory
         */
            chatNetworkServiceDeveloperDatabaseFactory = new ChatNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            chatNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            //DAO
            incomingNotificationsDao = new IncomingNotificationDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            outgoingNotificationDao = new OutgoingNotificationDAO(dataBaseCommunication, this.pluginFileSystem, this.pluginId);

            executorService = Executors.newFixedThreadPool(3);

            // change message state to process again first time
            reprocessMessages();

            //declare a schedule to process waiting request message

            this.startTimer();


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void stop() {
        super.stop();
        executorService.shutdownNow();
    }

    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {
        try {
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + newFermatMessageReceive.toJson()
                    + "\n-------------------------------------------------");

            JsonObject messageData = EncodeMsjContent.decodeMsjContent(newFermatMessageReceive);

            //NotificationDescriptor intraUserNotificationDescriptor = NotificationDescriptor.getByCode(jsonObject.get(JsonObjectConstants.MESSAGE_TYPE).getAsString());
            Gson gson = new Gson();
            ChatMessageTransactionType chatMessageTransactionType = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);
            ChatMetadataRecord chatMetadataRecord;
            switch (chatMessageTransactionType) {
                case CHAT_METADATA_TRASMIT:
                    String chatMetadataXml = messageData.get(ChatTransmissionJsonAttNames.CHAT_METADATA).getAsString();
                    /*
                     * Convert the xml to object
                     */
                    chatMetadataRecord = (ChatMetadataRecord) XMLParser.parseXML(chatMetadataXml, new ChatMetadataRecord());

                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:" + chatMetadataRecord.getLocalActorPublicKey()
                            + "\n-------------------------------------------------");

                    chatMetadataRecord.changeState(ChatProtocolState.PROCESSING_RECEIVE);

                    System.out.println("----------------------------\n" +
                            "CREANDO REGISTRO EN EL INCOMING NOTIFICATION DAO:"
                            + "\n-------------------------------------------------");

                    chatMetadataRecord.setFlagReadead(false);
                    getIncomingNotificationsDao().createNotification(chatMetadataRecord);

                    //NOTIFICATION LAUNCH
                    launchIncomingChatNotification(chatMetadataRecord.getChatId());
                    broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, "CONNECTION_REQUEST|" + chatMetadataRecord.getLocalActorPublicKey());

                    respondReceiveAndDoneCommunication(chatMetadataRecord);
                    getCommunicationNetworkServiceConnectionManager().closeConnection(chatMetadataRecord.getLocalActorPublicKey());
                    break;
                case TRANSACTION_STATUS_UPDATE:
                    DistributionStatus distributionStatus = (messageData.has(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS).getAsString(), DistributionStatus.class) : null;
                    MessageStatus messageStatus = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_STATUS).getAsString(), MessageStatus.class) : null;
                    UUID chatID = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.ID_CHAT).getAsString(), UUID.class);
                    UUID messageID = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_ID).getAsString(), UUID.class);

                    /*
                     * Get the ChatMetadataRecord
                     */

                    String transactionHash = CryptoHasher.performSha256(chatID.toString() + messageID.toString());
                    chatMetadataRecord = getOutgoingNotificationDao().findByTransactionHash(transactionHash);

                    if (chatMetadataRecord != null) {

                        if (distributionStatus != null)
                            chatMetadataRecord.setDistributionStatus(distributionStatus);
                        if (messageStatus != null)
                            chatMetadataRecord.setMessageStatus(messageStatus);
                        chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
                        chatMetadataRecord.changeState(ChatProtocolState.DONE);
                        getOutgoingNotificationDao().update(chatMetadataRecord);

                        //create incoming notification
                        chatMetadataRecord.setFlagReadead(false);
                        getOutgoingNotificationDao().update(chatMetadataRecord);
                        System.out.println("----------------------------\n" +
                                "MENSAJE ACCEPTED LLEGÓ BIEN: CASE ACCEPTED" + chatMetadataRecord.getLocalActorPublicKey()
                                + "\n-------------------------------------------------");
                        //NOTIFICATION LAUNCH
                        launcheIncomingChatStatusNotification(chatID);
                        respondReceiveAndDoneCommunication(chatMetadataRecord);
                        getCommunicationNetworkServiceConnectionManager().closeConnection(chatMetadataRecord.getLocalActorPublicKey());
                    }

                    break;
                default:

                    break;

            }

        } catch (CantUpdateRecordDataBaseException e1) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
        } catch (CantCreateNotificationException e1) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
        } catch (CantInsertRecordDataBaseException e1) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
        } catch (CantReadRecordDataBaseException e1) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
        }
        try {
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.exceptions.CantUpdateRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

        @Override
    public void onSentMessage(FermatMessage messageSent) {
        try {
            JsonObject messageData = EncodeMsjContent.decodeMsjContent(messageSent);

            String chatMetadataXml = messageData.get(ChatTransmissionJsonAttNames.CHAT_METADATA).getAsString();
                    /*
                     * Convert the xml to object
                     */
            ChatMetadataRecord chatMetadataRecord = (ChatMetadataRecord) XMLParser.parseXML(chatMetadataXml, new ChatMetadataRecord());
            if (chatMetadataRecord.getChatProtocolState()==ChatProtocolState.DONE) {
                // close connection, sender is the destination
                System.out.println("ENTRANDO EN EL METODO PARA CERRAR LA CONEXION DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
                System.out.println("ENTRO AL METODO PARA CERRAR LA CONEXION");
                //getCommunicationNetworkServiceConnectionManager().closeConnection(chatMetadataRecord.getRemoteActorPublicKey());
                //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
            }
            launchOutgoingChatNotification(chatMetadataRecord.getChatId());
            //done message type receive
//            if(actorNetworkServiceRecord.getNotificationDescriptor() == NotificationDescriptor.RECEIVED) {
//                actorNetworkServiceRecord.setActorProtocolState(ActorProtocolState.DONE);
//                outgoingNotificationDao.update(actorNetworkServiceRecord);
//                //actorNetworkServiceRecordedAgent.getPoolConnectionsWaitingForResponse().remove(actorNetworkServiceRecord.getActorDestinationPublicKey());
//            }

            System.out.println("SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("EXCEPCION DENTRO DEL PROCCESS EVENT");
            e.printStackTrace();

        }
    }

    @Override
    protected void onNetworkServiceRegistered() {

    }

    @Override
    protected void onClientConnectionClose() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onClientSuccessfulReconnect() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onClientConnectionLoose() {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {
        //I check my time trying to send the message
        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {
        // This network service don t need to do anything in this method
    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile platformComponentProfile) {
        // This network service don t need to do anything in this method
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                .constructPlatformComponentProfileFactory(identityPublicKeyDestination,
                        getNetworkServiceProfile().getAlias(),
                        getNetworkServiceProfile().getName(),
                        NetworkServiceType.CHAT,
                        PlatformComponentType.NETWORK_SERVICE,
                        "");
    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return getWsCommunicationsCloudClientManager().getCommunicationsCloudClientConnection()
                                                                       .constructPlatformComponentProfileFactory(identityPublicKeySender,
                                                                               getNetworkServiceProfile().getAlias(),
                                                                               getNetworkServiceProfile().getName(),
                                                                               NetworkServiceType.CHAT,
                                                                               PlatformComponentType.NETWORK_SERVICE,
                                                                               "");
    }

    @Override
    protected void reprocessMessages() {
        try {
           getOutgoingNotificationDao().changeStatusNotSentMessage();

        }
        catch(CantUpdateRecordDataBaseException e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {
        try {
            getOutgoingNotificationDao().changeStatusNotSentMessage(identityPublicKey);

        }
        catch(CantUpdateRecordDataBaseException  e)
        {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("INTRA USER NS EXCEPCION REPROCESANDO MESSAGEs");
            e.printStackTrace();
        }
    }

    @Override
    protected CommunicationsClientConnection getCommunicationsClientConnection() {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
    }

    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {
        return wsCommunicationsCloudClientManager;
    }

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return pluginDatabaseSystem;
    }

    @Override
    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    @Override
    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    @Override
    public LogManager getLogManager() {
        return logManager;
    }

    private void launchIncomingChatNotification(UUID chatID){
        IncomingChat event = (IncomingChat) getEventManager().getNewEvent(com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType.INCOMING_CHAT);
        event.setChatId(chatID);
        event.setSource(ChatNetworkServicePluginRootNew.EVENT_SOURCE);
        getEventManager().raiseEvent(event);
    }
    private void launchOutgoingChatNotification(UUID chatID){
        OutgoingChat event = (OutgoingChat) getEventManager().getNewEvent(com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType.OUTGOING_CHAT);
        event.setChatId(chatID);
        event.setSource(ChatNetworkServicePluginRootNew.EVENT_SOURCE);
        getEventManager().raiseEvent(event);
    }
    private void launcheIncomingChatStatusNotification(UUID chatID){
        IncomingNewChatStatusUpdate event = (IncomingNewChatStatusUpdate) getEventManager().getNewEvent(com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType.INCOMING_CHAT_MESSAGE_NOTIFICATION);
        event.setChatId(chatID);
        event.setSource(ChatNetworkServicePluginRootNew.EVENT_SOURCE);
        getEventManager().raiseEvent(event);
    }
    private ChatMetadataRecord changeActor(ChatMetadataRecord chatMetadataRecord) {

        chatMetadataRecord.setRemoteActorPublicKey(chatMetadataRecord.getLocalActorPublicKey());
        chatMetadataRecord.setLocalActorPublicKey(chatMetadataRecord.getRemoteActorPublicKey());
        return chatMetadataRecord;
    }

    // respond receive and done notification
    private void respondReceiveAndDoneCommunication(ChatMetadataRecord chatMetadataRecord) throws CantUpdateRecordDataBaseException {


        chatMetadataRecord = changeActor(chatMetadataRecord);
        try {
            UUID newNotificationID = UUID.randomUUID();
            long currentTime = System.currentTimeMillis();
            chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERED);
            String transactionHash = CryptoHasher.performSha256(chatMetadataRecord.getChatId().toString() + chatMetadataRecord.getMessageId().toString());
            chatMetadataRecord.setTransactionHash(transactionHash);
            chatMetadataRecord.setSentCount(1);
            chatMetadataRecord.setSentDate(new Timestamp(currentTime));
            chatMetadataRecord.setFlagReadead(false);
            chatMetadataRecord.setResponseToNotificationId(chatMetadataRecord.getTransactionId());
            chatMetadataRecord.setTransactionId(newNotificationID);
            getOutgoingNotificationDao().createNotification(chatMetadataRecord);
        } catch (CantUpdateRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateRecordDataBaseException(e.getMessage());
        } catch (CantCreateNotificationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateRecordDataBaseException(e.getMessage());
        } catch (CantInsertRecordDataBaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdateRecordDataBaseException(e.getMessage());
        }

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeChatNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeChatNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            ChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    private void checkFailedDeliveryTime(String destinationPublicKey)
    {
        try{

            List<ChatMetadataRecord> chatMetadataRecords = getOutgoingNotificationDao().getNotificationByDestinationPublicKey(destinationPublicKey);

            //if I try to send more than 5 times I put it on hold
            for (ChatMetadataRecord record : chatMetadataRecords) {

                if(!record.getChatProtocolState().getCode().equals(ChatProtocolState.WAITING_RESPONSE.getCode()))
                {
                    if(record.getSentCount() > 10 )
                    {
                        //  if(record.getSentCount() > 20)
                        //  {
                        //reprocess at two hours
                        //  reprocessTimer =  2 * 3600 * 1000;
                        // }

                        record.changeState(ChatProtocolState.WAITING_RESPONSE);
                        record.setSentCount(1);
                        //update state and process again later

                        getOutgoingNotificationDao().update(record);
                    }
                    else
                    {
                        record.setSentCount(record.getSentCount() + 1);
                        getOutgoingNotificationDao().update(record);
                    }
                }
                else
                {
                    //I verify the number of days I'm around trying to send if it exceeds three days I delete record

                    long sentDate = record.getSentDate().getTime();
                    long currentTime = System.currentTimeMillis();
                    long dif = currentTime - sentDate;

                    double dias = Math.floor(dif / (1000 * 60 * 60 * 24));

                    if((int) dias > 3)
                    {
                        //notify the user does not exist to intra user actor plugin
                        record.setDistributionStatus(DistributionStatus.CANNOT_SEND);
                        getIncomingNotificationsDao().createNotification(record);

                        getOutgoingNotificationDao().delete(record.getTransactionId());
                    }

                }

            }


        }
        catch(Exception e)
        {
            System.out.println("INTRA USER NS EXCEPCION VERIFICANDO WAIT MESSAGE");
            e.printStackTrace();
        }

    }

   /*
     * IntraUserManager Interface method implementation
     */

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {
        try {
            ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
            getCommunicationNetworkServiceConnectionManager().getIncomingMessageDao().update(fermatMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessMessages();
            }
        },0, reprocessTimer);
    }


    //DatabaseManagerForDevelopers Implementation
    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return chatNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return chatNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     *
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return chatNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }



    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }

    @Override
    public String getNetWorkServicePublicKey() { return getNetworkServiceProfile().getIdentityPublicKey();
    }

    @Override
    public void sendChatMessageNewStatusNotification(final String localActorPubKey, PlatformComponentType senderType, final String remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus newDistributionStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {
        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (senderType == null) {
                throw new IllegalArgumentException("Argument senderType can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            if (receiverType == null) {
                throw new IllegalArgumentException("Argument receiverType can not be null");
            }
            if (newDistributionStatus == null) {
                throw new IllegalArgumentException("Argument newDistributionStatus can not be null");
            }
            if (chatId == null) {
                throw new IllegalArgumentException("Argument chatId can not be null");
            }


            /*
             * ask for a previous connection
             */
            //CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

            /*
             * Construct the message content in json format
             */
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(chatId, messageID, newDistributionStatus, senderType, receiverType);
            String transactionHash = CryptoHasher.performSha256(chatId.toString() + messageID.toString());
            ChatMetadataRecord chatMetadataRecord = getOutgoingNotificationDao().findByTransactionHash(transactionHash);
            chatMetadataRecord.setDistributionStatus(newDistributionStatus);
            getOutgoingNotificationDao().createNotification(chatMetadataRecord);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(localActorPubKey),
                                getProfileDestinationToRequestConnection(remoteActorPubKey),
                                msjContent);
                    } catch (CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });

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

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public List<String> getRegisteredPubliKey() throws CantRequestListException {
        return null;
    }

    @Override
    public void sendChatMetadata(final String localActorPubKey, final String remoteActorPubKey, final ChatMetadata chatMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException {
        ChatMetadataRecord chatMetadataRecord = new ChatMetadataRecord();

        try {

            if (chatMetadata == null) {
                throw new IllegalArgumentException("Argument chatMetadata can not be null");
            }
            if (localActorPubKey == null || localActorPubKey.length() == 0 || localActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0 || remoteActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            System.out.println("ChatPLuginRoot - Starting method sendChatMetadata");


            //CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

            //  System.out.println("ChatNetworkServicePluginRoot - communicationNetworkServiceLocal: " + communicationNetworkServiceLocal);

            /*
             * Construct the message content in json format
             */
            final String msjContent = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(chatMetadata, chatMetadata.getLocalActorType(), chatMetadata.getRemoteActorType());
            // System.out.println("ChatNetworkServicePluginRoot - Message encoded:\n" + msjContent);

            long currentTime = System.currentTimeMillis();
            ChatProtocolState protocolState = ChatProtocolState.PROCESSING_SEND;
            String msgHash = CryptoHasher.performSha256(chatMetadata.getChatId().toString() + chatMetadata.getMessageId().toString());
            chatMetadataRecord.setTransactionId(UUID.randomUUID());
            chatMetadataRecord.setTransactionHash(msgHash);
            chatMetadataRecord.setChatId(chatMetadata.getChatId());
            chatMetadataRecord.setObjectId(chatMetadata.getObjectId());
            chatMetadataRecord.setLocalActorType(chatMetadata.getLocalActorType());
            chatMetadataRecord.setLocalActorPublicKey(chatMetadata.getLocalActorPublicKey());
            chatMetadataRecord.setRemoteActorType(chatMetadata.getRemoteActorType());
            chatMetadataRecord.setRemoteActorPublicKey(chatMetadata.getRemoteActorPublicKey());
            chatMetadataRecord.setChatName(chatMetadata.getChatName());
            chatMetadataRecord.setChatMessageStatus(chatMetadata.getChatMessageStatus());
            chatMetadataRecord.setMessageStatus(chatMetadata.getMessageStatus());
            chatMetadataRecord.setDate(chatMetadata.getDate());
            chatMetadataRecord.setMessageId(chatMetadata.getMessageId());
            chatMetadataRecord.setMessage(chatMetadata.getMessage());
            chatMetadataRecord.setDistributionStatus(DistributionStatus.SENT);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
            chatMetadataRecord.setSentDate(new Timestamp(currentTime));
            chatMetadataRecord.changeState(protocolState);
            chatMetadataRecord.setResponseToNotificationId(chatMetadataRecord.getTransactionId());
            if(!chatMetadataRecord.isFilled()){
                throw new CantSendChatMessageMetadataException("Some value of ChatMetadata Is passed NULL");
            }


            // System.out.println("ChatPLuginRoot - Chat transaction: " + chatMetadataRecord);

            /*
             * Save into data base
             */
            getOutgoingNotificationDao().createNotification(chatMetadataRecord);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(localActorPubKey),
                                getProfileDestinationToRequestConnection(remoteActorPubKey),
                                msjContent);
                    } catch (CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });
            //System.out.println("ChatNetworkServicePluginRoot - Message sent.");
        }catch(CantSendChatMessageMetadataException e){
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
            String possibleCause = "Missing Fields.";


            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }catch (Exception e) {

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

            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }
    private void reportUnexpectedError(final Exception e) {
        errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

    @Override
    public void sendMessageStatusUpdate(final String localActorPubKey, PlatformComponentType senderType, final String remoteActorPubKey, PlatformComponentType receiverType, MessageStatus messageStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {
        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (senderType == null) {
                throw new IllegalArgumentException("Argument senderType can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            if (receiverType == null) {
                throw new IllegalArgumentException("Argument receiverType can not be null");
            }
            if (messageStatus == null) {
                throw new IllegalArgumentException("Argument messageStatus can not be null");
            }
            if (chatId == null) {
                throw new IllegalArgumentException("Argument chatId can not be null");
            }


            /*
             * ask for a previous connection
             */
            //CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(remoteActorPubKey);

            /*
             * Construct the message content in json format
             */
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(chatId, messageID, messageStatus, senderType, receiverType);
            String transactionHash = CryptoHasher.performSha256(chatId.toString() + messageID.toString());
            ChatMetadataRecord chatMetadataRecord = getOutgoingNotificationDao().findByTransactionHash(transactionHash);
            chatMetadataRecord.setMessageStatus(messageStatus);
            getOutgoingNotificationDao().createNotification(chatMetadataRecord);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendNewMessage(
                                getProfileSenderToRequestConnection(localActorPubKey),
                                getProfileDestinationToRequestConnection(remoteActorPubKey),
                                msjContent);
                    } catch (CantSendMessageException e) {
                        reportUnexpectedError(e);
                    }
                }
            });

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

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.CHAT_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {

            ChatMetadataRecord chatMetadataRecord = getIncomingNotificationsDao().getNotificationById(transactionID);
            chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERED);
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);
            chatMetadataRecord.setMessageStatus(MessageStatus.DELIVERED);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.PROCESSED);
            chatMetadataRecord.setFlagReadead(true);
            getIncomingNotificationsDao().update(chatMetadataRecord);

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
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "Database error");
        }
    }

    @Override
    public List<Transaction<ChatMetadata>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<ChatMetadata>> pendingTransactions = new ArrayList<>();
        try {
            List<ChatMetadataRecord> pendingChatMetadataTransactions = getIncomingNotificationsDao().findAll(CommunicationChatNetworkServiceDatabaseConstants.INCOMING_NOTIFICATION_CHAT_PROCCES_STATUS_COLUMN_NAME, ChatMetadataRecord.NO_PROCESSED);
            if (!pendingChatMetadataTransactions.isEmpty()) {
                for (ChatMetadataRecord chatMetadataRecord : pendingChatMetadataTransactions) {
                    Transaction<ChatMetadata> transaction = new Transaction<>(chatMetadataRecord.getTransactionId(),
                            (ChatMetadata) chatMetadataRecord,
                            Action.APPLY,
                            chatMetadataRecord.getSentDate().getTime());
                    pendingTransactions.add(transaction);

                }

            }

        } catch (CantReadRecordDataBaseException e) {
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
            throw new CantDeliverPendingTransactionsException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "No pending Transaction");
        }
        return pendingTransactions;
    }
}
