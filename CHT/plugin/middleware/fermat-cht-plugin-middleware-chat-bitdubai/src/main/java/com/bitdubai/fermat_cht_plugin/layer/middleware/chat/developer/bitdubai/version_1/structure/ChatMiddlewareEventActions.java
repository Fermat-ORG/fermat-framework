package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Owner;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionSearch;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListChatException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingTransactionException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Timestamp;
import java.util.UUID;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 29/08/2016.
 *
 * @author lnacosta
 */
public class ChatMiddlewareEventActions {

    private final Broadcaster                broadcaster                   ;
    private final ChatMiddlewareDatabaseDao  chatMiddlewareDatabaseDao     ;
    private final ChatActorConnectionManager chatActorConnectionManager    ;
    private final NetworkServiceChatManager  networkServiceChatManager     ;

    public ChatMiddlewareEventActions(final ChatMiddlewareDatabaseDao  chatMiddlewareDatabaseDao     ,
                                      final Broadcaster                broadcaster                   ,
                                      final ChatActorConnectionManager chatActorConnectionManager    ,
                                      final NetworkServiceChatManager  networkServiceChatManager     ) {

        this.chatMiddlewareDatabaseDao      = chatMiddlewareDatabaseDao     ;
        this.broadcaster                    = broadcaster                   ;
        this.chatActorConnectionManager     = chatActorConnectionManager    ;
        this.networkServiceChatManager      = networkServiceChatManager     ;
    }

    /**
     * This method checks the incoming chat event and acts according to this.
     *
     * @throws CantGetPendingTransactionException
     */
    public void incomingMessageEventHandler(MessageMetadata messageMetadata) throws CantGetPendingTransactionException, CantListChatException, UnexpectedResultReturnedFromDatabaseException {

        try {

            System.out.println("12345 CHECKING INCOMING CHAT");

            ChatLinkedActorIdentity identity = new ChatLinkedActorIdentity(messageMetadata.getRemoteActorPublicKey(), Actors.CHAT);
            ConnectionState connectionState = chatActorConnectionManager.getSearch(identity).getConnectionState(messageMetadata.getLocalActorPublicKey());

            System.out.println("12345 CHECKING CONTACT EXIST: connection state = " + connectionState);

            if(connectionState.equals(ConnectionState.CONNECTED)) {

                saveMessage(messageMetadata);

                FermatBundle fermatBundle = new FermatBundle();
                fermatBundle.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle.put(APP_NOTIFICATION_PAINTER_FROM, new Owner(SubAppsPublicKeys.CHT_OPEN_CHAT.getCode()));
                fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle.put(NOTIFICATION_ID, ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE_NOTIFICATION);
                fermatBundle.put(APP_ACTIVITY_TO_OPEN_CODE, Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
                fermatBundle.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE);
                broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, fermatBundle);

                FermatBundle fermatBundle2 = new FermatBundle();
                fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

                FermatBundle fermatBundle3 = new FermatBundle();
                fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);

            }else
                System.out.println("12345 CONTACT IS NOT CONNECTED");
        } catch (CantGetActorConnectionException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking if the actors are connected.",
                    "Unexpected error in database operation"
            );
        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Unexpected error in database operation"
            );
        } catch (CantSaveMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot save message from database"
            );
        } catch (CantGetMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot get the message from database"
            );

        } catch (CantGetChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot get chat"
            );
        } catch (CantSaveChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming chat pending transactions",
                    "Cannot save chat"
            );
        } catch (SendStatusUpdateMessageNotificationException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method saves the new chat in database
     *
     * @param messageMetadata
     * @throws DatabaseOperationException
     */
    private void saveMessage(MessageMetadata messageMetadata) throws
            DatabaseOperationException,
            CantGetChatException,
            CantSaveChatException,
            CantSaveMessageException,
            CantGetMessageException,
            SendStatusUpdateMessageNotificationException {

        Chat chat = chatMiddlewareDatabaseDao.getChatByRemotePublicKey(messageMetadata.getLocalActorPublicKey());

        if (chat == null) {
            chat = new ChatImpl();
            chat.setChatId(UUID.randomUUID());
            chat.setObjectId(UUID.randomUUID());
            chat.setLocalActorPublicKey(messageMetadata.getRemoteActorPublicKey());
            chat.setRemoteActorPublicKey(messageMetadata.getLocalActorPublicKey());
            Long dv = System.currentTimeMillis();
            chat.setDate(new Timestamp(dv));
            chat.setTypeChat(TypeChat.INDIVIDUAL);
        }
        chat.setLastMessageDate(new Timestamp(System.currentTimeMillis()));//updating date of last message arrived in chat

        chat.setStatus(ChatStatus.VISSIBLE);

        chatMiddlewareDatabaseDao.saveChat(chat);

        Message messageRecorded = getMessageFromChatMetadata(messageMetadata, chat);

        if (messageRecorded == null) return;

        messageRecorded.setChatId(chat.getChatId());
        messageRecorded.setStatus(MessageStatus.RECEIVE);

        chatMiddlewareDatabaseDao.saveMessage(messageRecorded);

        sendDeliveredMessageNotification(messageRecorded, chat);
    }

    public void sendDeliveredMessageNotification(Message message, Chat chat) throws SendStatusUpdateMessageNotificationException {

        try {

            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();
            networkServiceChatManager.sendMessageStatusUpdate(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    MessageStatus.DELIVERED,
                    message.getMessageId()
            );
        } catch (Exception e) {
            throw new SendStatusUpdateMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    private Message getMessageFromChatMetadata(final MessageMetadata messageMetadata, Chat chatFromDatabase) throws CantGetMessageException {

        if (messageMetadata == null)
            throw new CantGetMessageException("The chat metadata from network service is null");

        try {

            ChatLinkedActorIdentity chatLinkedActorIdentity = new ChatLinkedActorIdentity(chatFromDatabase.getLocalActorPublicKey(), Actors.CHAT);

            final ChatActorConnectionSearch search = chatActorConnectionManager.getSearch(chatLinkedActorIdentity);
            UUID connectionId = search.getConnectionId(chatFromDatabase.getRemoteActorPublicKey());

            if (connectionId == null)
                return null;

            return new MessageImpl(
                    chatFromDatabase.getChatId(),
                    messageMetadata,
                    MessageStatus.CREATED,
                    TypeMessage.INCOMMING,
                    connectionId
            );

        } catch (CantGetActorConnectionException e) {
            throw new CantGetMessageException(e,
                    "Getting message from ChatMetadata",
                    "Cannot get the ActorConnection");
        }
    }

    /**
     * This method checks the incoming status event and acts according to this.
     *
     * @throws CantGetPendingTransactionException
     */
    public void incomingMessageStatusUpdateEventHandler(MessageMetadata messageMetadata) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {

        try {

            System.out.println("12345 CHECKING INCOMING STATUS INSIDE IF MESSAGE == " + messageMetadata.getMessage() + " MESSAGE STATUS == " + messageMetadata.getMessageStatus());

            updateMessageStatus(messageMetadata);

            if (messageMetadata.getMessageStatus() != MessageStatus.READ) {
                FermatBundle fermatBundle3 = new FermatBundle();
                fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
                fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
                fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
                broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);
            }

            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Unexpected error in database operation"
            );
        } catch (CantGetMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot get the message from database"
            );
        } catch (CantSaveMessageException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        }
    }

    /**
     * This method updates a message record in database.
     *
     * @param messageMetadata,
     * @throws DatabaseOperationException
     * @throws CantSaveMessageException
     * @throws CantGetMessageException
     */
    private void updateMessageStatus(MessageMetadata messageMetadata) throws
            DatabaseOperationException,
            CantSaveMessageException,
            CantGetMessageException {

        System.out.println("12345 UPDATING MESSAGE STATUS");
        UUID messageId = messageMetadata.getMessageId();
        MessageStatus messageStatus = chatMiddlewareDatabaseDao.getMessageStatus(messageId);
        if (messageStatus == null) {

            System.out.println("************* MESSAGE DOES NOT EXIST");
            return;
        }
        if (messageStatus.equals(MessageStatus.READ))
            return;

        chatMiddlewareDatabaseDao.updateMessageStatus(messageId, messageMetadata.getMessageStatus());
        System.out.println("12345 MESSAGE STATUS UPDATED");
    }

    public void incomingWritingStatusEventHandler(String senderPk) throws
            CantGetPendingTransactionException,
            UnexpectedResultReturnedFromDatabaseException {
        try {

            UUID chatId = chatMiddlewareDatabaseDao.getChatIdByRemotePublicKey(senderPk);
            if (chatId != null) {
                System.out.println("12345 Saving is writing chat -> you must to inform to android view " + chatId);
            }

            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

            FermatBundle fermatBundle3 = new FermatBundle();
            fermatBundle3.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle3.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle3.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle3);

        } catch (DatabaseOperationException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        } catch (CantGetChatException e) {
            throw new CantGetPendingTransactionException(
                    e,
                    "Checking the incoming status pending transactions",
                    "Cannot update message from database"
            );
        }
    }

}

