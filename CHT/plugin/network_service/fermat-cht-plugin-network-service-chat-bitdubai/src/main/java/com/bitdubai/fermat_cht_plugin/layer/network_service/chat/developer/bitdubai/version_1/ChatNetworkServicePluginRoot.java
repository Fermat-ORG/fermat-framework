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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewChatStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingNewWritingStatusUpdate;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingMessage;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.OutgoingChat;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
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
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MessageMetadataRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.ActorProfile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 15/02/16.
 */
@PluginInfo(createdBy = "Gabirel Araujo", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CHAT_NETWORK_SERVICE)
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

    /**
     * Executor
     */
    //ExecutorService executorService;

    /**
     * Constructor with parameters
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
            chatNetworkServiceDeveloperDatabaseFactory = new ChatNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId, getErrorManager());
            chatNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            chatMetadataRecordDAO = new ChatMetadataRecordDAO(dataBaseCommunication);

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }


    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void onNewMessageReceived(NetworkServiceMessage newFermatMessageReceive) {
        try {
            System.out.println("----------------------------\n" + "CONVIERTIENDO MENSAJE ENTRANTE A GSON: " + newFermatMessageReceive.toJson() + "\n-------------------------------------------------");

            JsonObject messageData = EncodeMsjContent.decodeMsjContent(newFermatMessageReceive);
            Gson gson = new Gson();
            ChatMessageTransactionType chatMessageTransactionType = gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MSJ_CONTENT_TYPE), ChatMessageTransactionType.class);
            System.out.println("chatMessageTransactionType = " + chatMessageTransactionType);
            MessageMetadataRecord messageMetadataRecord;
            switch (chatMessageTransactionType) {
                case MESSAGE_METADATA_TRANSMIT:
                    String messageMetadataJson = messageData.get(ChatTransmissionJsonAttNames.MESSAGE_METADATA).getAsString();
                    messageMetadataRecord = MessageMetadataRecord.fromJson(messageMetadataJson);
                    messageMetadataRecord.setTransactionId(UUID.randomUUID());
                    messageMetadataRecord.setMessageStatus(MessageStatus.CREATED);
                    messageMetadataRecord.setDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis())));
                    getChatMetadataRecordDAO().createNotification(messageMetadataRecord);

                    launchIncomingMessageNotification(messageMetadataRecord);
                    break;
                case TRANSACTION_STATUS_UPDATE:
                    MessageStatus messageStatus = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_STATUS).getAsString(), MessageStatus.class) : null;
                    UUID messageId = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_ID)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_ID).getAsString(), UUID.class) : null;

                    if (messageId != null) {
                        messageMetadataRecord = getChatMetadataRecordDAO().getMessageNotificationByMessageId(messageId);
                            if(messageMetadataRecord != null) {
                                    if (messageStatus != null) {
                                        messageMetadataRecord.setMessageStatus(messageStatus);
                                        getChatMetadataRecordDAO().update(messageMetadataRecord);
                                    }else break;

                                    System.out.println("----------------------------\n" + "MENSAJE ACCEPTED LLEGÓ BIEN: CASE OTHER" + messageMetadataRecord.getLocalActorPublicKey() + "\n-------------------------------------------------");
                                    launchIncomingChatStatusNotification(messageMetadataRecord);
                                }
                    }
                    break;
                case TRANSACTION_WRITING_STATUS:
                    String remotePk = (messageData.has(ChatTransmissionJsonAttNames.SENDER_PUBLIC_KEY)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.SENDER_PUBLIC_KEY).getAsString(), String.class) : null;
                    if (remotePk != null && remotePk.length()>0)
                        launchIncomingWritingStatusNotification(remotePk);
                default:
                    break;

            }

        } catch (CantUpdateRecordDataBaseException | CantCreateNotificationException | CantInsertRecordDataBaseException | CantReadRecordDataBaseException | NotificationNotFoundException | CantGetNotificationException e) {
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
            if (chatMessageTransactionType == ChatMessageTransactionType.CHAT_METADATA_TRASMIT) {
                launchOutgoingChatNotification(chatId);
                System.out.println("ChatNetworkServicePluginRoot - SALIENDO DEL HANDLE NEW SENT MESSAGE NOTIFICATION");
            }

        } catch (Exception e) {
            //quiere decir que no estoy reciviendo metadata si no una respuesta
            System.out.println("ChatNetworkServicePluginRoot - EXCEPCION DENTRO DEL PROCCESS EVENT");
            reportUnexpectedError(e);
        }
    }

    private void launchIncomingMessageNotification(MessageMetadata messageMetadata) {
        IncomingMessage event = (IncomingMessage) eventManager.getNewEvent(EventType.INCOMING_MESSAGE);
        event.setMessageMetadata(messageMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchIncomingChatNotification(ChatMetadata chatMetadata) {
        IncomingChat event = (IncomingChat) eventManager.getNewEvent(EventType.INCOMING_CHAT);
        event.setChatMetadata(chatMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchOutgoingChatNotification(UUID chatID) {
        OutgoingChat event = (OutgoingChat) eventManager.getNewEvent(EventType.OUTGOING_CHAT);
        event.setChatId(chatID);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchIncomingChatStatusNotification(MessageMetadata messageMetadata) {
        IncomingNewChatStatusUpdate event = (IncomingNewChatStatusUpdate) eventManager.getNewEvent(EventType.INCOMING_STATUS);
        event.setMessageMetadata(messageMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchIncomingWritingStatusNotification(String remotePk) {
        IncomingNewWritingStatusUpdate event = (IncomingNewWritingStatusUpdate) eventManager.getNewEvent(EventType.INCOMING_WRITING_STATUS);
        event.setSenderPk(remotePk);
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
            ChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem, getErrorManager());

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

    private Actors getActorByPlatformComponentType(PlatformComponentType platformComponentType) {

        switch (platformComponentType) {
            case ACTOR_CHAT:
                return Actors.CHAT;
            default:
                return Actors.CHAT;
        }
    }

    private void sendMessage(final String jsonMessage,
                             final String identityPublicKey,
                             final Actors identityType,
                             final String actorPublicKey,
                             final Actors actorType) {

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

            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionWritingNotification(
                    localActorPubKey
            );

            sendMessage(
                    msjContent,
                    localActorPubKey,
                    getActorByPlatformComponentType(senderType),
                    remoteActorPubKey,
                    getActorByPlatformComponentType(receiverType)
            );

        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: ").append(pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: ").append(pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: ").append(errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: ").append(eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void sendMessageMetadata(String localActorPubKey,String remoteActorPubKey, MessageMetadata messageMetadata) throws CantSendChatMessageMetadataException, IllegalArgumentException {

        MessageMetadataRecord messageMetadataRecord = new MessageMetadataRecord();
        try {

            if (messageMetadata == null) {
                throw new IllegalArgumentException("Argument chatMetadata can not be null");
            }
            if (localActorPubKey == null || localActorPubKey.length() == 0 || localActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }
            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0 || remoteActorPubKey.equals("null")) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }
            messageMetadataRecord.setTransactionId(UUID.randomUUID());
            messageMetadataRecord.setLocalActorPublicKey(localActorPubKey);
            messageMetadataRecord.setLocalActorType(messageMetadata.getLocalActorType());
            messageMetadataRecord.setRemoteActorPublicKey(remoteActorPubKey);
            messageMetadataRecord.setRemoteActorType(messageMetadata.getRemoteActorType());
            messageMetadataRecord.setDate(messageMetadata.getDate());
            messageMetadataRecord.setMessageStatus(messageMetadata.getMessageStatus());
            messageMetadataRecord.setMessage(messageMetadata.getMessage());
            messageMetadataRecord.setMessageId(messageMetadata.getMessageId());

            final String EncodedMsg = EncodeMsjContent.encodeMSjContentMessageMetadataTransmit(
                    messageMetadataRecord);
            getChatMetadataRecordDAO().createNotification(messageMetadataRecord);
            //Only to individual chat type
            System.out.println("##### IMPRIMO ESTO PORQUE QUIERO VER QUE ESTOY ENVIANDO");
            System.out.println("EncodedMsg = " + EncodedMsg);
            sendMessage(
                    EncodedMsg,
                    messageMetadataRecord.getLocalActorPublicKey(),
                    Actors.CHAT,
                    messageMetadataRecord.getRemoteActorPublicKey(),
                    Actors.CHAT);


        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: ").append(pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: ").append(pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: ").append(errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: ").append(eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void sendMessageStatusUpdate(String localActorPubKey, PlatformComponentType senderType, String remoteActorPubKey, PlatformComponentType receiverType, DistributionStatus distributionStatus, MessageStatus messageStatus, UUID chatId, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {
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

            MessageMetadataRecord messageMetadataRecord = getChatMetadataRecordDAO().getMessageNotificationByMessageId(messageID);
            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(
                    messageMetadataRecord.getMessageId().toString(),
                    messageStatus,
                    chatId
            );

            messageMetadataRecord.setRemoteActorPublicKey(remoteActorPubKey);
            messageMetadataRecord.setRemoteActorType(receiverType);
            messageMetadataRecord.setLocalActorPublicKey(localActorPubKey);
            messageMetadataRecord.setLocalActorType(senderType);
            if (!messageMetadataRecord.getMessageStatus().getCode().equals(messageStatus.getCode())) {
                messageMetadataRecord.setMessageStatus(messageStatus);
                sendMessage(
                        msjContent,
                        localActorPubKey,
                        getActorByPlatformComponentType(senderType),
                        remoteActorPubKey,
                        getActorByPlatformComponentType(receiverType)
                );
                getChatMetadataRecordDAO().update(messageMetadataRecord);
            }
        } catch (Exception e) {

            StringBuilder contextBuffer = new StringBuilder();
            contextBuffer.append("Plugin ID: ").append(pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: ").append(pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: ").append(errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: ").append(eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantSendChatMessageNewStatusNotificationException pluginStartException = new CantSendChatMessageNewStatusNotificationException(CantSendChatMessageNewStatusNotificationException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    private void reportUnexpectedError(final Exception e) {
        reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }


}
