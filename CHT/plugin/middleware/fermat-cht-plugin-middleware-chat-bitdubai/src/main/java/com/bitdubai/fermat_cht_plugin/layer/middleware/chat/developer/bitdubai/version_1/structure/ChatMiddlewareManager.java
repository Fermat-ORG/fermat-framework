package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.util.ObjectChecker;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


/**
 * This class is the implementation of MiddlewareChatManager.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
public class ChatMiddlewareManager implements MiddlewareChatManager {

    private ChatMiddlewarePluginRoot chatMiddlewarePluginRoot;
    /**
     * Represents the plugin Database Dao.
     */
    private ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao;

    /**
     * Represents the NetworkServiceChatManager
     */
    NetworkServiceChatManager networkServiceChatManager;

    NetworkServiceChatManager chatNetworkServiceManager;

    ChatActorConnectionManager chatActorConnectionManager;

    private final Broadcaster broadcaster;

    public ChatMiddlewareManager(
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao,
            ChatMiddlewarePluginRoot chatMiddlewarePluginRoot,
            NetworkServiceChatManager networkServiceChatManager,
            NetworkServiceChatManager chatNetworkServiceManager,
            Broadcaster broadcaster,
            ChatActorConnectionManager chatActorConnectionManager
    ) {
        this.chatMiddlewareDatabaseDao = chatMiddlewareDatabaseDao;
        this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
        this.networkServiceChatManager = networkServiceChatManager;
        this.chatNetworkServiceManager = chatNetworkServiceManager;
        this.broadcaster = broadcaster;
        this.chatActorConnectionManager = chatActorConnectionManager;
    }

    /**
     * This method returns a full list of chat recorded in database.
     *
     * @return
     * @throws CantGetChatException
     */
    @Override
    public List<Chat> listVisibleChats() throws CantGetChatException {
        try {
            return this.chatMiddlewareDatabaseDao.listVisibleChats();
        } catch (DatabaseOperationException exception) {
            chatMiddlewarePluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetChatException(
                    exception,
                    "Getting the chat list from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetChatException(
                    FermatException.wrapException(exception),
                    "Getting the chat list from database",
                    "Unexpected exception");
        }
    }

    @Override
    public Boolean existAnyVisibleChat() throws CantGetChatException {

        try {
            return this.chatMiddlewareDatabaseDao.existAnyVisibleChat();
        } catch (DatabaseOperationException exception) {
            chatMiddlewarePluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetChatException(
                    exception,
                    "Getting the chat list from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetChatException(
                    FermatException.wrapException(exception),
                    "Getting the chat list from database",
                    "Unexpected exception");
        }
    }


    /**
     * This method returns a chat by UUID
     *
     * @param chatId
     * @return
     * @throws CantGetChatException
     */
    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        try {
            ObjectChecker.checkArgument(chatId, "The chat Id argument is null");
            return this.chatMiddlewareDatabaseDao.getChatByChatId(chatId);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "The chat Id probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetChatException(
                    FermatException.wrapException(exception),
                    "Getting a chat by UUID",
                    "Unexpected exception");
        }
    }

    /**
     * This method saves a chat, properly formed, in database.
     *
     * @param chat
     * @throws CantSaveChatException
     */
    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        try {
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.saveChat(chat);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveChatException(
                    FermatException.wrapException(exception),
                    "Saving a chat in database",
                    "Unexpected exception");
        }
    }

    @Override
    public void markChatAs(UUID chatId, ChatStatus chatStatus) throws CantSaveChatException {

        try {

            ObjectChecker.checkArgument(chatId, "The chatId argument is null");
            ObjectChecker.checkArgument(chatStatus, "The chatStatus argument is null");
            this.chatMiddlewareDatabaseDao.updateChatStatus(chatId, chatStatus);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveChatException(
                    FermatException.wrapException(exception),
                    "Saving a chat in database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a chat from database.
     *
     * @param chatId
     * @param isDeleteChat
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteChat(UUID chatId, boolean isDeleteChat) throws CantDeleteChatException {
        try {
            ObjectChecker.checkArgument(chatId, "The chatId argument is null");
            this.chatMiddlewareDatabaseDao.deleteChat(chatId, isDeleteChat);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteChatException(
                    FermatException.wrapException(exception),
                    "Deleting a chat from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a chat from database.
     *
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteAllChats() throws CantDeleteChatException {
        try {
            this.chatMiddlewareDatabaseDao.deleteAllChats();
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteChatException(
                    FermatException.wrapException(exception),
                    "Deleting a chat from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a message by chat Id.
     *
     * @param chatId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The chat id argument is null");
            return this.chatMiddlewareDatabaseDao.getMessagesByChatId(chatId);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public Message getLastMessageByChatId(UUID chatId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The chat id argument is null");
            return this.chatMiddlewareDatabaseDao.getLastMessageByChatId(chatId);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public long getUnreadCountMessageByChatId(UUID chatId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The message Id is null");
            return this.chatMiddlewareDatabaseDao.getUnreadCountMessageByChatId(chatId);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException {
        try {
            ObjectChecker.checkArgument(publicKey, "The publicKey argument is null");
            return this.chatMiddlewareDatabaseDao.getChatByRemotePublicKey(publicKey);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "The chat Id probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetChatException(
                    FermatException.wrapException(exception),
                    "Getting a chat by UUID",
                    "Unexpected exception");
        }
    }

    /**
     * This method saves a message in database.
     *
     * @param message
     * @throws CantSaveMessageException
     */
    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        try {
            ObjectChecker.checkArgument(message, "The message argument is null");
            System.out.println("*** 12345 case 3:send msg in Manager layer" + new Timestamp(System.currentTimeMillis()));
            this.chatMiddlewareDatabaseDao.saveMessage(message);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public void markAsRead(UUID messageId) throws CantSaveMessageException {
        try {

            System.out.println("*** 12345 case 3:send msg in Manager layer" + new Timestamp(System.currentTimeMillis()));
            this.chatMiddlewareDatabaseDao.updateMessageStatus(messageId, MessageStatus.READ);
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    public void sendReadMessageNotification(UUID messageId, UUID chatId) throws SendStatusUpdateMessageNotificationException {
        try {
            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat == null) {
                throw new SendStatusUpdateMessageNotificationException("Chat not found");
            }

            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();
            networkServiceChatManager.sendMessageStatusUpdate(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    MessageStatus.READ,
                    messageId
            );
        } catch (Exception e) {
            throw new SendStatusUpdateMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException {

        try {

            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);

            if (chat == null)
                throw new SendWritingStatusMessageNotificationException("Chat not found");

            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();

            networkServiceChatManager.sendWritingStatus(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    chat.getChatId()
            );

        } catch (Exception e) {
            throw new SendWritingStatusMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    @Override
    public Timestamp getLastMessageReceivedDate(UUID chatId) throws CantGetChatException {

        try {
            if(chatId != null)
                return chatMiddlewareDatabaseDao.getLastMessageReceivedDateByChatId(chatId);
            else
                return null;

        } catch (Exception e) {
            throw new CantGetChatException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    @Override
    public void sendMessage(Message createdMessage) throws CantSendChatMessageException {
        try {
            System.out.println("*** 12345 case 5:send msg in Agent layer" + new Timestamp(System.currentTimeMillis()));
            UUID chatId = createdMessage.getChatId();
            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat == null) {
                return;
            }
            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();

            try {

                MessageMetadata messageMetadata = new MessageMetadataRecord(
                        chat.getLocalActorPublicKey(),
                        chat.getRemoteActorPublicKey(),
                        createdMessage.getMessageId(),
                        createdMessage.getMessage(),
                        createdMessage.getStatus(),
                        new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Timestamp(System.currentTimeMillis())));
                chatNetworkServiceManager.sendMessageMetadata(localActorPublicKey,
                        remoteActorPublicKey,
                        messageMetadata);
                createdMessage.setStatus(MessageStatus.SENT);
            } catch (IllegalArgumentException e) {
                /**
                 * In this case, any argument in chat or message was null or not properly set.
                 * I'm gonna change the status to CANNOT_SEND to avoid send this message.
                 */
                createdMessage.setStatus(MessageStatus.CANNOT_SEND);
            }
            chatMiddlewareDatabaseDao.saveMessage(createdMessage);
/*
            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);

            fermatBundle2.put(ChatBroadcasterConstants.CHAT_BROADCASTER_TYPE, ChatBroadcasterConstants.NEW_MESSAGE_TYPE);
            fermatBundle2.put(ChatBroadcasterConstants.CHAT_MESSAGE, createdMessage);

            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);*/

        } catch (DatabaseOperationException e) {
            throw new CantSendChatMessageException(
                    e,
                    "Sending a message",
                    "Unexpected error in database"
            );
        } catch (CantGetChatException e) {
            throw new CantSendChatMessageException(
                    e,
                    "Sending a message",
                    "Cannot get the chat"
            );
        } catch (CantSendChatMessageMetadataException e) {
            throw new CantSendChatMessageException(
                    e,
                    "Sending a message",
                    "Cannot send the ChatMetadata"
            );
        } catch (CantSaveMessageException e) {
            throw new CantSendChatMessageException(
                    e,
                    "Sending a message",
                    "Cannot save the message"
            );
        }
    }

}
