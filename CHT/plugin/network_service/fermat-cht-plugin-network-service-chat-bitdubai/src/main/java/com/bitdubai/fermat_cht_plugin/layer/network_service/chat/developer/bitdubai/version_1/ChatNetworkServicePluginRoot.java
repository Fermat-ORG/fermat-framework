package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantCreateNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.NotificationNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatProtocolState;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewWritingStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatMetadataRecordDAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatMetadataRecord;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.EncodeMsjContent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Gabriel Araujo 15/02/16.
 */
@PluginInfo(createdBy = "Gabirel Araujo", maintainerMail = "franklinmarcano1970@gmail.com",platform = Platforms.CHAT_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CHAT_NETWORK_SERVICE)
public class ChatNetworkServicePluginRoot extends AbstractNetworkService implements NetworkServiceChatManager,
        DatabaseManagerForDevelopers {

    /**
     * Represent the intraActorDataBase
     */
    private Database dataBaseCommunication;

    private ChatMetadataRecordDAO chatMetadataRecordDAO;
    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private ChatNetworkServiceDeveloperDatabaseFactory chatNetworkServiceDeveloperDatabaseFactory;

    ExecutorService executorService;


    Timer timer = new Timer();

    private long reprocessTimer = 300000; //five minutes

    /**
     * Executor
     */
    //ExecutorService executorService;

    /**
     * Constructor with parameters
     *
     */
    public ChatNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                EventSource.NETWORK_SERVICE_CHAT,
                NetworkServiceType.CHAT
        );

    }

    public ChatMetadataRecordDAO getChatMetadataRecordDAO() {
        return chatMetadataRecordDAO;
    }

    @Override
    protected void onNetworkServiceStart() {

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
            chatNetworkServiceDeveloperDatabaseFactory = new ChatNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId,getErrorManager());
            chatNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            chatMetadataRecordDAO = new ChatMetadataRecordDAO(dataBaseCommunication);

            //executorService = Executors.newFixedThreadPool(2);
            executorService = Executors.newFixedThreadPool(3);

            //declare a schedule to process waiting request message
            this.startTimer();

        }catch (Exception e){
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


    }
//    public PlatformComponentProfile constructBasicPlatformComponentProfile(String identityPublicKey,
//                                                                           PlatformComponentType platformComponentType) {
//
//
//        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection()
//                .constructBasicPlatformComponentProfileFactory(
//                        identityPublicKey,
//                        NetworkServiceType.UNDEFINED,
//                        platformComponentType
//
//                );
//    }

    @Override
    public void stop() {
        executorService.shutdownNow();
        super.stop();
    }

    @Override
    public void onNewMessageReceived(NetworkServiceMessage newFermatMessageReceive) {
        try {
            System.out.println("----------------------------\n" +
                    "CONVIERTIENDO MENSAJE ENTRANTE A GSON:" + newFermatMessageReceive.toJson()
                    + "\n-------------------------------------------------");

            JsonObject messageData = EncodeMsjContent.decodeMsjContent(newFermatMessageReceive);
            Gson gson = new Gson();
            ChatMessageTransactionType chatMessageTransactionType = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);
            System.out.println("chatMessageTransactionType = " + chatMessageTransactionType);
            ChatMetadataRecord chatMetadataRecord;
            switch (chatMessageTransactionType) {
                case CHAT_METADATA_TRASMIT:
                    String chatMetadataJson = messageData.get(ChatTransmissionJsonAttNames.CHAT_METADATA).getAsString();
                    System.out.println("chatMetadataJson = " + chatMetadataJson);
                    /*
                     * Convert the xml to object
                     */

                    chatMetadataRecord = ChatMetadataRecord.fromJson(chatMetadataJson);
//                    messageData = EncodeMsjContent.decodeMsjContent(chatMetadataXml);
//                    chatMetadataRecord = new ChatMetadataRecord(messageData);
                    System.out.println("----------------------------\n" +
                            "MENSAJE LLEGO EXITOSAMENTE:" + chatMetadataRecord.getLocalActorPublicKey()
                            + "\n-------------------------------------------------");

                    String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));

                    chatMetadataRecord.changeState(ChatProtocolState.PROCESSING_RECEIVE);
                    chatMetadataRecord.setTransactionId(getChatMetadataRecordDAO().getNewUUID(UUID.randomUUID().toString()));
                    chatMetadataRecord.setResponseToNotification(messageData.get(ChatTransmissionJsonAttNames.RESPONSE_TO).getAsString());
                    chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);
                    chatMetadataRecord.setMessageStatus(MessageStatus.CREATED);
                    chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERING);
                    chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
                    chatMetadataRecord.setSentDate(timeStamp);
                    chatMetadataRecord.setFlagReadead(false);
                    System.out.println("----------------------------\n" +
                            "CREANDO REGISTRO EN EL INCOMING NOTIFICATION DAO:"
                            +"\n "+chatMetadataRecord.getMessage()
                            + "\n-------------------------------------------------");

                    chatMetadataRecord.setFlagReadead(false);
                    getChatMetadataRecordDAO().createNotification(chatMetadataRecord);

                    launchIncomingChatNotification(chatMetadataRecord);

                    break;
                case TRANSACTION_STATUS_UPDATE:
                    DistributionStatus distributionStatus = (messageData.has(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.DISTRIBUTION_STATUS).getAsString(), DistributionStatus.class) : null;
                    MessageStatus messageStatus = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_STATUS).getAsString(), MessageStatus.class) : null;
                    ChatProtocolState chatProtocolState = (messageData.has(ChatTransmissionJsonAttNames.PROTOCOL_STATE)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.PROTOCOL_STATE).getAsString(), ChatProtocolState.class) : null;
                    UUID responseTo = (messageData.has(ChatTransmissionJsonAttNames.RESPONSE_TO)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.RESPONSE_TO).getAsString(), UUID.class) : null;

                    /*
                     * Get the ChatMetadataRecord
                     */

                    if(responseTo != null) {
                        chatMetadataRecord = getChatMetadataRecordDAO().getNotificationByResponseTo(responseTo);

                        if (chatMetadataRecord != null) {
                            System.out.println("12345 UPDATE RECIBIDO MENSAJE == "+chatMetadataRecord.getMessage() + " MESSAGE STATUS == "+messageStatus);
                            chatMetadataRecord.setChatId(chatMetadataRecord.getChatId());

                            if (chatProtocolState == ChatProtocolState.DONE) {
                                System.out.println("----------------------------\n" +
                                        "MENSAJE ACCEPTED LLEGÓ BIEN: CASE DONE" + chatMetadataRecord.getLocalActorPublicKey()
                                        + "\n-------------------------------------------------");
                                chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERED);
                                chatMetadataRecord.changeState(chatProtocolState);
                                getChatMetadataRecordDAO().update(chatMetadataRecord);
                            } else {
                                if (distributionStatus != null)
                                    chatMetadataRecord.setDistributionStatus(distributionStatus);
                                if (messageStatus != null) {
                                    chatMetadataRecord.setMessageStatus(messageStatus);
                                }
                                chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
                                chatMetadataRecord.changeState(ChatProtocolState.DONE);

                                //create incoming notification
                                chatMetadataRecord.setFlagReadead(false);
                                getChatMetadataRecordDAO().update(chatMetadataRecord);
                                //NOTIFICATION LAUNCH
                                if (messageStatus == null) {
                                    break;
                                }
                                System.out.println("----------------------------\n" +
                                        "MENSAJE ACCEPTED LLEGÓ BIEN: CASE OTHER" + chatMetadataRecord.getLocalActorPublicKey()
                                        + "\n-------------------------------------------------");
                                launchIncomingChatStatusNotification(chatMetadataRecord);

                            }
                        }
                    }
                    break;

                case TRANSACTION_WRITING_STATUS:
                    chatMetadataRecord=null;
                    UUID responsTo = (messageData.has(ChatTransmissionJsonAttNames.RESPONSE_TO)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.RESPONSE_TO).getAsString(), UUID.class) : null;
                    if(responsTo!=null)
                    chatMetadataRecord = getChatMetadataRecordDAO().getNotificationByResponseTo(responsTo);
                    if(chatMetadataRecord!=null)
                    launchIncomingWritingStatusNotification(chatMetadataRecord.getChatId());
                default:
                    break;

            }

        } catch (CantUpdateRecordDataBaseException |CantCreateNotificationException |CantInsertRecordDataBaseException | CantReadRecordDataBaseException | NotificationNotFoundException | CantGetNotificationException e) {
            e.printStackTrace();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            e.printStackTrace();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }
        try {
            getNetworkServiceConnectionManager().getIncomingMessagesDao().markAsRead(newFermatMessageReceive);
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException | RecordNotFoundException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void onSentMessage(NetworkServiceMessage messageSent) {

        try {
            JsonObject messageData = EncodeMsjContent.decodeMsjContent(messageSent);
            Gson gson = new Gson();
            UUID chatId = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.ID_CHAT), UUID.class);
            ChatMessageTransactionType chatMessageTransactionType = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);
            if (chatMessageTransactionType==ChatMessageTransactionType.CHAT_METADATA_TRASMIT) {
                launchOutgoingChatNotification(chatId);
                System.out.println("ChatNetworkServicePluginRoot - SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("ChatNetworkServicePluginRoot - EXCEPCION DENTRO DEL PROCCESS EVENT");
            reportUnexpectedError(e);
        }
    }

    private void launchIncomingChatNotification(ChatMetadata chatMetadata){
        IncomingChat event = (IncomingChat) eventManager.getNewEvent(EventType.INCOMING_CHAT);
        event.setChatMetadata(chatMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }
    private void launchOutgoingChatNotification(UUID chatID){
        OutgoingChat event = (OutgoingChat) eventManager.getNewEvent(EventType.OUTGOING_CHAT);
        event.setChatId(chatID);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }
    private void launchIncomingChatStatusNotification(ChatMetadata chatMetadata){
        IncomingNewChatStatusUpdate event = (IncomingNewChatStatusUpdate) eventManager.getNewEvent(EventType.INCOMING_STATUS);
        event.setChatMetadata(chatMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchIncomingWritingStatusNotification(UUID chatId){
        IncomingNewWritingStatusUpdate event = (IncomingNewWritingStatusUpdate) eventManager.getNewEvent(EventType.INCOMING_WRITING_STATUS);
        event.setChatId(chatId);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
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
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            ChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem,getErrorManager());

            try {

                /*
                 * We create the new database
                 */
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }
    
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
    public String getNetWorkServicePublicKey() {
        return getIdentity().getPublicKey();
    }

    @Override
    public void sendChatMessageNewStatusNotification(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, DistributionStatus newDistributionStatus, String transactionID) throws CantSendChatMessageNewStatusNotificationException {
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
            if (transactionID == null) {
                throw new IllegalArgumentException("Argument transactionID can not be null");
            }


            ChatMetadataRecord chatMetadataRecord = getChatMetadataRecordDAO().getNotificationById(UUID.fromString(transactionID));
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(
                    chatMetadataRecord.getResponseToNotification(),
                    chatMetadataRecord.getTransactionId().toString(),
                    newDistributionStatus, senderType,
                    receiverType
            );
            while(!Objects.equals(chatMetadataRecord.getProcessed(), ChatMetadataRecord.PROCESSED)){
                chatMetadataRecord = getChatMetadataRecordDAO().getNotificationById(chatMetadataRecord.getTransactionId());
                chatMetadataRecord.setDistributionStatus(newDistributionStatus);
                chatMetadataRecord.setRemoteActorPublicKey(remoteActorPubKey);
                chatMetadataRecord.setRemoteActorType(receiverType);
                chatMetadataRecord.setLocalActorPublicKey(localActorPubKey);
                chatMetadataRecord.setLocalActorType(senderType);
                chatMetadataRecord.changeState(ChatProtocolState.DONE);
                chatMetadataRecord.setResponseToNotification(getChatMetadataRecordDAO().getNotificationResponseToByID(UUID.fromString(transactionID)));
                chatMetadataRecord.setMsgXML(msjContent);
                if(Objects.equals(chatMetadataRecord.getProcessed(), ChatMetadataRecord.PROCESSED)){
                    chatMetadataRecord.setProcessed(ChatMetadataRecord.PROCESSED);
                    final ChatMetadataRecord chatMetadataTosend = chatMetadataRecord;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                sendMessage(
                                        msjContent,
                                        localActorPubKey,
                                        getActorByPlatformComponentType(senderType),
                                        remoteActorPubKey,
                                        getActorByPlatformComponentType(receiverType)
                                );

                                getChatMetadataRecordDAO().update(chatMetadataTosend);
                            } catch (CantUpdateRecordDataBaseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }


        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    private Actors getActorByPlatformComponentType(PlatformComponentType platformComponentType) {

        switch (platformComponentType) {
            case ACTOR_CHAT:
                return Actors.CHAT;
            default:
                return Actors.CHAT;
        }
    }

    private void sendMessage(final String jsonMessage      ,
                             final String identityPublicKey,
                             final Actors identityType     ,
                             final String actorPublicKey   ,
                             final Actors actorType        ) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    ActorProfile sender = new ActorProfile();
                    sender.setActorType(identityType.getCode());
                    sender.setIdentityPublicKey(identityPublicKey);

                    ActorProfile receiver = new ActorProfile();
                    receiver.setActorType(actorType.getCode());
                    receiver.setIdentityPublicKey(actorPublicKey);

                    sendNewMessage(
                            sender,
                            receiver,
                            jsonMessage
                    );
                } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException e) {
                    reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        });
    }

    @Override
    public void sendChatMessageNewStatusNotification(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, DistributionStatus newDistributionStatus, MessageStatus messageStatus, UUID chatId, UUID messageId) throws CantSendChatMessageNewStatusNotificationException {
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


            ChatMetadataRecord chatMetadataRecord = getChatMetadataRecordDAO().getNotificationByChatAndMessageId(chatId, messageId);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(
                    chatMetadataRecord.getResponseToNotification(),
                    messageStatus,
                    senderType,
                    receiverType,
                    chatId
            );

                chatMetadataRecord = getChatMetadataRecordDAO().getNotificationById(chatMetadataRecord.getTransactionId());
                chatMetadataRecord.setDistributionStatus(newDistributionStatus);
                chatMetadataRecord.setRemoteActorPublicKey(remoteActorPubKey);
                chatMetadataRecord.setRemoteActorType(receiverType);
                chatMetadataRecord.setLocalActorPublicKey(localActorPubKey);
                chatMetadataRecord.setLocalActorType(senderType);
                chatMetadataRecord.setMsgXML(msjContent);
//                if(!Objects.equals(chatMetadataRecord.getMessageStatus(), messageStatus)){
            if(!chatMetadataRecord.getMessageStatus().getCode().equals(messageStatus.getCode())){
                    chatMetadataRecord.setMessageStatus(messageStatus);
                    final ChatMetadataRecord chatMetadataToSend = chatMetadataRecord;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        sendMessage(
                                msjContent,
                                localActorPubKey,
                                getActorByPlatformComponentType(senderType),
                                remoteActorPubKey,
                                getActorByPlatformComponentType(receiverType)
                        );
                        getChatMetadataRecordDAO().update(chatMetadataToSend);
                    } catch (CantUpdateRecordDataBaseException e) {
                        e.printStackTrace();
                            }
                        }
                    });
                }




        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void sendWritingStatus(final String localActorPubKey, final PlatformComponentType senderType, final String remoteActorPubKey, final PlatformComponentType receiverType, final UUID chatId) throws CantSendChatMessageNewStatusNotificationException {
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

            ChatMetadataRecord chatMetadataRecord = getChatMetadataRecordDAO().getNotificationByChatAndMessageId(chatId, null);
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionWritingNotification(
                    chatMetadataRecord.getResponseToNotification(),
                    senderType,
                    receiverType,
                    chatId
            );

            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    sendMessage(
                            msjContent,
                            localActorPubKey,
                            getActorByPlatformComponentType(senderType),
                            remoteActorPubKey,
                            getActorByPlatformComponentType(receiverType)
                    );
                }
            });

        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

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
            System.out.println("ChatNetworkServicePluginRoot - Starting method sendChatMetadata");

            String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));

            ChatProtocolState protocolState = ChatProtocolState.PROCESSING_SEND;
            chatMetadataRecord.setTransactionId(getChatMetadataRecordDAO().getNewUUID(UUID.randomUUID().toString()));
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
            chatMetadataRecord.setResponseToNotification(chatMetadataRecord.getTransactionId().toString());
            chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
            chatMetadataRecord.setSentDate(timeStamp);
            chatMetadataRecord.changeState(protocolState);
            chatMetadataRecord.setTypeChat(chatMetadata.getTypeChat());
            chatMetadataRecord.setGroupMembers(chatMetadata.getGroupMembers());
            final String EncodedMsg = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(chatMetadataRecord, chatMetadata.getLocalActorType(), chatMetadata.getRemoteActorType());


            chatMetadataRecord.setMsgXML(EncodedMsg);
            if(!chatMetadataRecord.isFilled(true)){
                throw new CantSendChatMessageMetadataException("Some value of ChatMetadata Is passed NULL");
            }

            // System.out.println("ChatPLuginRoot - Chat transaction: " + chatMetadataRecord);

            /*
             * Save into data base
             */
            final String sender = chatMetadataRecord.getLocalActorPublicKey();
            final PlatformComponentType senderType = chatMetadataRecord.getLocalActorType();
            final String remote = chatMetadataRecord.getRemoteActorPublicKey();
            final PlatformComponentType remoteType = chatMetadataRecord.getRemoteActorType();
            getChatMetadataRecordDAO().createNotification(chatMetadataRecord);
            System.out.println("*** 12345 case 6:send msg in NS layer" + new Timestamp(System.currentTimeMillis()));
            if(chatMetadata.getTypeChat().equals(TypeChat.INDIVIDUAL)) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {

                            sendMessage(
                                    EncodedMsg,
                                    localActorPubKey,
                                    getActorByPlatformComponentType(senderType),
                                    remoteActorPubKey,
                                    getActorByPlatformComponentType(remoteType)
                            );
                    }
                });
            }else if(chatMetadata.getTypeChat().equals(TypeChat.GROUP)){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {

                        for(GroupMember groupMember : chatMetadata.getGroupMembers()) {

                            sendMessage(
                                    EncodedMsg,
                                    localActorPubKey,
                                    getActorByPlatformComponentType(senderType),
                                    groupMember.getActorPublicKey(),
                                    getActorByPlatformComponentType(remoteType)
                            );
                        }
                    }
                });
            }

        }catch(CantSendChatMessageMetadataException e){
            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Missing Fields.";


            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    private void reportUnexpectedError(final Exception e) {
        reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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


        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {

            ChatMetadataRecord chatMetadataRecord = getChatMetadataRecordDAO().getNotificationById(transactionID);
            chatMetadataRecord.changeState(ChatProtocolState.DONE);
            chatMetadataRecord.setDistributionStatus(DistributionStatus.DELIVERED);
            chatMetadataRecord.setChatMessageStatus(ChatMessageStatus.CREATED_CHAT);
            chatMetadataRecord.setMessageStatus(MessageStatus.DELIVERED);
            chatMetadataRecord.setProcessed(ChatMetadataRecord.PROCESSED);
            chatMetadataRecord.setFlagReadead(true);
            getChatMetadataRecordDAO().update(chatMetadataRecord);

        } catch (Exception e) {
            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);
            CantConfirmTransactionException cantConfirmTransactionException = new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "Database error");
            reportUnexpectedError(cantConfirmTransactionException);
            throw cantConfirmTransactionException;
        }
    }

    @Override
    public List<Transaction<ChatMetadata>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        List<Transaction<ChatMetadata>> pendingTransactions = new ArrayList<>();
        try {
            List<ChatMetadataRecord> pendingChatMetadataTransactions = getChatMetadataRecordDAO().findAll(ChatNetworkServiceDataBaseConstants.CHAT_METADATA_TRANSACTION_RECORD_PROCCES_STATUS_COLUMN_NAME, ChatMetadataRecord.NO_PROCESSED);
            if (!pendingChatMetadataTransactions.isEmpty()) {
                for (ChatMetadataRecord chatMetadataRecord : pendingChatMetadataTransactions) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    Date parsedDate = null;
                    try {
                        parsedDate = dateFormat.parse(chatMetadataRecord.getSentDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    Transaction<ChatMetadata> transaction = new Transaction<>(chatMetadataRecord.getTransactionId(),
                            (ChatMetadata) chatMetadataRecord,
                            Action.APPLY,
                            timestamp.getTime());
                    pendingTransactions.add(transaction);

                }

            }

        } catch (CantReadRecordDataBaseException e) {
            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);
            CantDeliverPendingTransactionsException cantDeliverPendingTransactionsException = new CantDeliverPendingTransactionsException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, e, contextBuffer.toString(), "No pending Transaction");
            reportUnexpectedError(cantDeliverPendingTransactionsException);
            throw cantDeliverPendingTransactionsException;
        }
        return pendingTransactions;
    }

    protected void reprocessPendingMessage() {
        try{
            if(chatMetadataRecordDAO != null) {
                List<ChatMetadataRecord> chatMetadataRecordList =
                        chatMetadataRecordDAO.findAllToSend(
                                ChatProtocolState.PROCESSING_SEND,
                                DistributionStatus.SENT);

                String remotePublicKey;
                System.out.println("CHAT NS - I found " + chatMetadataRecordList.size() + " pending for sending");

                for (ChatMetadataRecord chatMetadataRecord : chatMetadataRecordList) {
                    remotePublicKey = chatMetadataRecord.getRemoteActorPublicKey();
                    System.out.println("CHAT NS - Trying to re-send to " + remotePublicKey);

                    String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis()));

                    ChatProtocolState protocolState = ChatProtocolState.PROCESSING_SEND;
                    chatMetadataRecord.setTransactionId(chatMetadataRecord.getTransactionId());
                    chatMetadataRecord.setChatId(chatMetadataRecord.getChatId());
                    chatMetadataRecord.setObjectId(chatMetadataRecord.getObjectId());
                    chatMetadataRecord.setLocalActorType(chatMetadataRecord.getLocalActorType());
                    chatMetadataRecord.setLocalActorPublicKey(chatMetadataRecord.getLocalActorPublicKey());
                    chatMetadataRecord.setRemoteActorType(chatMetadataRecord.getRemoteActorType());
                    chatMetadataRecord.setRemoteActorPublicKey(chatMetadataRecord.getRemoteActorPublicKey());
                    chatMetadataRecord.setChatName(chatMetadataRecord.getChatName());
                    chatMetadataRecord.setChatMessageStatus(chatMetadataRecord.getChatMessageStatus());
                    chatMetadataRecord.setMessageStatus(chatMetadataRecord.getMessageStatus());
                    chatMetadataRecord.setDate(chatMetadataRecord.getDate());
                    chatMetadataRecord.setMessageId(chatMetadataRecord.getMessageId());
                    chatMetadataRecord.setMessage(chatMetadataRecord.getMessage());
                    chatMetadataRecord.setDistributionStatus(DistributionStatus.SENT);
                    chatMetadataRecord.setResponseToNotification(chatMetadataRecord.getTransactionId().toString());
                    chatMetadataRecord.setProcessed(ChatMetadataRecord.NO_PROCESSED);
                    chatMetadataRecord.setSentDate(timeStamp);
                    chatMetadataRecord.changeState(protocolState);
                    chatMetadataRecord.setTypeChat(chatMetadataRecord.getTypeChat());
                    chatMetadataRecord.setGroupMembers(chatMetadataRecord.getGroupMembers());
                    final String EncodedMsg = EncodeMsjContent.encodeMSjContentChatMetadataTransmit(
                            chatMetadataRecord,
                            chatMetadataRecord.getLocalActorType(),
                            chatMetadataRecord.getRemoteActorType());


                    chatMetadataRecord.setMsgXML(EncodedMsg);

                    sendMessage(
                            EncodedMsg,
                            chatMetadataRecord.getLocalActorPublicKey(),
                            Actors.CHAT,
                            chatMetadataRecord.getRemoteActorPublicKey(),
                            Actors.CHAT);
                /*sendChatMetadata(
                        chatMetadataRecord.getLocalActorPublicKey(),
                        remotePublicKey,
                        chatMetadataRecord);*/
                }
            }
        } catch (Exception e) {
            System.out.println("CHAT NS - EXCEPTION PROCESSING MESSAGES NOT SENT");
            e.printStackTrace();
        }
    }

    @Override
    protected void onNetworkServiceRegistered() {

        reprocessPendingMessage();

    }

    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // change message state to process retry later
                reprocessPendingMessage();
            }
        }, 0, reprocessTimer);
    }

}
