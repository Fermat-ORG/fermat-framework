package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantGetNotificationException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageTransactionType;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.ChangedMessageStatusUpdateEvent;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingMessageEvent;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.events.IncomingWritingStatusUpdateEvent;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageNewStatusNotificationException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatMetadataRecordDAO;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDataBaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.database.ChatNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatNetworkServiceDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.ChatTransmissionJsonAttNames;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.EncodeMsjContent;
import com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure.MessageMetadataRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.entities.NetworkServiceMessage;
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
public class ChatNetworkServicePluginRoot extends AbstractNetworkService implements NetworkServiceChatManager, DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

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

            chatNetworkServiceDeveloperDatabaseFactory = new ChatNetworkServiceDeveloperDatabaseFactory(dataBaseCommunication);

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
                    messageMetadataRecord.setPackageId(UUID.randomUUID());
                    messageMetadataRecord.setMessageStatus(MessageStatus.CREATED);
                    messageMetadataRecord.setDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis())));
                    messageMetadataRecord.setChatMessageTransactionType(chatMessageTransactionType);

                    launchIncomingMessageNotification(messageMetadataRecord);
                    break;
                case TRANSACTION_STATUS_UPDATE:
                    MessageStatus messageStatus = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_STATUS)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_STATUS).getAsString(), MessageStatus.class) : null;
                    UUID messageId = (messageData.has(ChatTransmissionJsonAttNames.MESSAGE_ID)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.MESSAGE_ID).getAsString(), UUID.class) : null;

                    if (messageId != null && messageStatus != null) {
                        messageMetadataRecord = new MessageMetadataRecord();
                        messageMetadataRecord.setMessageId(messageId);
                        messageMetadataRecord.setMessageStatus(messageStatus);

                        System.out.println("----------------------------\n" + "MENSAJE ACCEPTED LLEGÓ BIEN: CASE OTHER" + messageMetadataRecord.getLocalActorPublicKey() + "\n-------------------------------------------------");
                        launchChangedMessageStatusNotification(messageMetadataRecord);
                    }
                    break;
                case TRANSACTION_WRITING_STATUS:
                    String remotePk = (messageData.has(ChatTransmissionJsonAttNames.SENDER_PUBLIC_KEY)) ? gson.fromJson(messageData.get(ChatTransmissionJsonAttNames.SENDER_PUBLIC_KEY).getAsString(), String.class) : null;
                    if (remotePk != null && remotePk.length()>0)
                        launchIncomingWritingStatusNotification(remotePk);
                default:
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);

        }
    }

    @Override
    public synchronized void onMessageFail(UUID packageId) {

        try {

            UUID messageId = getChatMetadataRecordDAO().getMessageIdByPackageId(packageId);

            if (messageId != null) {

                MessageMetadataRecord messageMetadata = new MessageMetadataRecord();
                messageMetadata.setMessageId(messageId);
                messageMetadata.setMessageStatus(MessageStatus.CANNOT_SEND);

                getChatMetadataRecordDAO().deleteMessageByPackageId(packageId);
                launchChangedMessageStatusNotification(messageMetadata);
            } else
                System.out.println("PACKAGE WITH ID:"+packageId.toString()+" was not found");

        } catch (CantGetNotificationException e) {
            e.printStackTrace();
            CantGetNotificationException pluginStartException = new CantGetNotificationException(CantGetNotificationException.DEFAULT_MESSAGE, e, "", "CAN NOT GET NOTIFICATION");

            reportUnexpectedError(pluginStartException);
        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
            CantReadRecordDataBaseException pluginStartException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");

            reportUnexpectedError(pluginStartException);
        } catch (Exception e){
            e.printStackTrace();
            CantReadRecordDataBaseException pluginStartException = new CantReadRecordDataBaseException("Something bad happened", e, "", "");

            reportUnexpectedError(pluginStartException);
        }

    }

    @Override
    public void onSentMessage(UUID packageId) {

        System.out.println("message sent+ :" + packageId);

        try {

            UUID messageId = getChatMetadataRecordDAO().getMessageIdByPackageId(packageId);

            if (messageId != null) {

                MessageMetadataRecord messageMetadata = new MessageMetadataRecord();
                messageMetadata.setMessageId(messageId);
                messageMetadata.setMessageStatus(MessageStatus.SENT);

                getChatMetadataRecordDAO().deleteMessageByPackageId(packageId);
                launchChangedMessageStatusNotification(messageMetadata);
            } else
                System.out.println("PACKAGE WITH ID:"+packageId.toString()+" was not found");

        } catch (CantGetNotificationException e) {
            e.printStackTrace();
            CantGetNotificationException pluginStartException = new CantGetNotificationException(CantGetNotificationException.DEFAULT_MESSAGE, e, "", "CAN NOT GET NOTIFICATION");

            reportUnexpectedError(pluginStartException);
        } catch (CantReadRecordDataBaseException e) {
            e.printStackTrace();
            CantReadRecordDataBaseException pluginStartException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "");

            reportUnexpectedError(pluginStartException);
        } catch (Exception e){
            e.printStackTrace();
            CantReadRecordDataBaseException pluginStartException = new CantReadRecordDataBaseException("Something bad happened", e, "", "");

            reportUnexpectedError(pluginStartException);
        }
    }

    private void launchIncomingMessageNotification(MessageMetadata messageMetadata) {
        IncomingMessageEvent event = (IncomingMessageEvent) eventManager.getNewEvent(EventType.INCOMING_MESSAGE);
        event.setMessageMetadata(messageMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchChangedMessageStatusNotification(MessageMetadata messageMetadata) {
        ChangedMessageStatusUpdateEvent event = (ChangedMessageStatusUpdateEvent) eventManager.getNewEvent(EventType.CHANGED_MESSAGE_STATUS);
        event.setMessageMetadata(messageMetadata);
        event.setSource(eventSource);
        eventManager.raiseEvent(event);
    }

    private void launchIncomingWritingStatusNotification(String remotePk) {
        IncomingWritingStatusUpdateEvent event = (IncomingWritingStatusUpdateEvent) eventManager.getNewEvent(EventType.INCOMING_WRITING_STATUS);
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

            this.dataBaseCommunication = this.pluginDatabaseSystem.openDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeChatNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            ChatNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new ChatNetworkServiceDatabaseFactory(pluginDatabaseSystem, getErrorManager());

            try {
                this.dataBaseCommunication = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, ChatNetworkServiceDataBaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {
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

    private UUID sendMessage(final String jsonMessage,
                             final String identityPublicKey,
                             final String actorPublicKey,
                             final boolean monitoring) {

        try {
            ActorProfile sender = new ActorProfile();
            sender.setActorType(Actors.CHAT.getCode());
            sender.setIdentityPublicKey(identityPublicKey);

            ActorProfile receiver = new ActorProfile();
            receiver.setActorType(Actors.CHAT.getCode());
            receiver.setIdentityPublicKey(actorPublicKey);

            return sendNewMessage(
                    sender,
                    receiver,
                    jsonMessage,
                    monitoring
            );
        } catch (com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return null;
    }

    @Override
    public void sendWritingStatus(final String localActorPubKey, final String remoteActorPubKey, final UUID chatId) throws CantSendChatMessageNewStatusNotificationException {
        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }

            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }

            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionWritingNotification(
                    localActorPubKey
            );

            sendMessage(
                    msjContent,
                    localActorPubKey,
                    remoteActorPubKey,
                    false
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
            messageMetadataRecord.setLocalActorPublicKey(localActorPubKey);
            messageMetadataRecord.setRemoteActorPublicKey(remoteActorPubKey);
            messageMetadataRecord.setDate(messageMetadata.getDate());
            messageMetadataRecord.setMessageStatus(messageMetadata.getMessageStatus());
            messageMetadataRecord.setMessage(messageMetadata.getMessage());
            messageMetadataRecord.setMessageId(messageMetadata.getMessageId());

            final String EncodedMsg = EncodeMsjContent.encodeMSjContentMessageMetadataTransmit(messageMetadataRecord);

            //Only to individual chat type
            System.out.println("##### IMPRIMO ESTO PORQUE QUIERO VER QUE ESTOY ENVIANDO");
            System.out.println("EncodedMsg = " + EncodedMsg);
            UUID transactionId = sendMessage(
                    EncodedMsg,
                    messageMetadataRecord.getLocalActorPublicKey(),
                    messageMetadataRecord.getRemoteActorPublicKey(),
                    true
            );
            messageMetadataRecord.setPackageId(transactionId);
            messageMetadataRecord.setChatMessageTransactionType(ChatMessageTransactionType.MESSAGE_METADATA_TRANSMIT);

            if (transactionId != null)
                getChatMetadataRecordDAO().createNotification(messageMetadataRecord);
            else {

                MessageMetadataRecord messageMetadataToRaise = new MessageMetadataRecord();
                messageMetadataToRaise.setMessageId(messageMetadata.getMessageId());
                messageMetadataToRaise.setMessageStatus(MessageStatus.CANNOT_SEND);
                launchChangedMessageStatusNotification(messageMetadataToRaise);
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

            CantSendChatMessageMetadataException pluginStartException = new CantSendChatMessageMetadataException(CantSendChatMessageMetadataException.DEFAULT_MESSAGE, e, context, possibleCause);

            reportUnexpectedError(pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void sendMessageStatusUpdate(String localActorPubKey, String remoteActorPubKey, MessageStatus messageStatus, UUID messageID) throws CantSendChatMessageNewStatusNotificationException {

        try {

            if (localActorPubKey == null || localActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument localActorPubKey can not be null");
            }

            if (remoteActorPubKey == null || remoteActorPubKey.length() == 0) {
                throw new IllegalArgumentException("Argument remoteActorPubKey can not be null");
            }

            if (messageStatus == null) {
                throw new IllegalArgumentException("Argument messageStatus can not be null");
            }

            if (messageID == null) {
                throw new IllegalArgumentException("Argument messageID can not be null");
            }

            final String msjContent = EncodeMsjContent.encodeMSjContentTransactionNewStatusNotification(
                    messageID.toString(),
                    messageStatus
            );

            sendMessage(
                    msjContent,
                    localActorPubKey,
                    remoteActorPubKey,
                    false
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

    private void reportUnexpectedError(final Exception e) {
        reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
    }

}
