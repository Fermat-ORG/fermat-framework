package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOnlineStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetWritingStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveActionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendWritingStatusMessageNotificationException;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.all_definition.util.ObjectChecker;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.enums.ActionState;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ActionOnline;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions.CantSendChatMessageMetadataException;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.MessageMetadata;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.CantGetPendingActionListException;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


/**
 * This class is the implementation of MiddlewareChatManager.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/01/16.
 */
public class ChatMiddlewareManager implements MiddlewareChatManager {

    public static String INCOMING_CHAT_MESSAGE_NOTIFICATION = "New Message";

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
    public List<Chat> getChats() throws CantGetChatException {
        try {
            return this.chatMiddlewareDatabaseDao.getChatList();
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
     * This method returns a new empty instance chat.
     *
     * @return
     * @throws CantNewEmptyChatException
     */
    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        try {
            return this.chatMiddlewareDatabaseDao.newEmptyInstanceChat();
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantNewEmptyChatException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance chat",
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

    /**
     * This method deletes a chat from database.
     *
     * @param chat
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        try {
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.deleteChat(chat);
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
     * This method deletes all chats from database.
     *
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteChats() throws CantDeleteChatException {
        try {
            this.chatMiddlewareDatabaseDao.deleteChats();
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting all chats from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteChatException(
                    FermatException.wrapException(exception),
                    "Deleting all chats from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes all messages of a chat from database.
     *
     * @param chatId
     * @throws CantDeleteMessageException
     */
    @Override
    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.deleteMessagesByChatId(chatId);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting messages from database",
                    "The chat id probably is null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting messages from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteMessageException(
                    FermatException.wrapException(exception),
                    "Deleting messages from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a full message list.
     *
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        try {
            return this.chatMiddlewareDatabaseDao.getMessages();
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting the full messages list",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting the full messages list",
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
    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The chat id argument is null");
            return this.chatMiddlewareDatabaseDao.getMessageByChatId(chatId);
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
    public int getCountMessageByChatId(UUID chatId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(chatId, "The message Id is null");
            return this.chatMiddlewareDatabaseDao.getCountMessageByChatId(chatId);
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
     * This method return a message by message id.
     *
     * @param messageId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        try {
            ObjectChecker.checkArgument(messageId, "The message Id is null");
            return this.chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
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

    /**
     * This method return a new empty instance message.
     *
     * @return
     * @throws CantNewEmptyMessageException
     */
    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        try {
            return this.chatMiddlewareDatabaseDao.newEmptyInstanceMessage();
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantNewEmptyMessageException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance message",
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

    /**
     * This method deletes a message from database.
     *
     * @param message
     * @throws CantDeleteMessageException
     */
    @Override
    public void deleteMessage(Message message) throws CantDeleteMessageException {
        try {
            ObjectChecker.checkArgument(message, "The message argument is null");
            this.chatMiddlewareDatabaseDao.deleteMessage(message);
        } catch (ObjectNotSetException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteMessageException(
                    FermatException.wrapException(exception),
                    "Deleting a message from database",
                    "Unexpected exception");
        }
    }

    public void sendReadMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException {
        try {
            UUID chatId = message.getChatId();
            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat == null) {
                throw new SendStatusUpdateMessageNotificationException("Chat not found");
            }

            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();
            networkServiceChatManager.sendMessageStatusUpdate(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    DistributionStatus.DELIVERED,
                    MessageStatus.READ,
                    chat.getChatId(),
                    message.getMessageId());
        } catch (Exception e) {
            throw new SendStatusUpdateMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public void sendDeliveredMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException {
        try {
            UUID chatId = message.getChatId();
            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat == null) {
                throw new SendStatusUpdateMessageNotificationException("Chat not found");
            }

            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();
            networkServiceChatManager.sendMessageStatusUpdate(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    DistributionStatus.DELIVERED,
                    MessageStatus.DELIVERED,
                    chat.getChatId(),
                    message.getMessageId());
        } catch (Exception e) {
            throw new SendStatusUpdateMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public void sendWritingStatus(UUID chatId) throws SendWritingStatusMessageNotificationException {
        try {
            ActionState writingState = chatMiddlewareDatabaseDao.getWritingActionById(chatId);
            System.out.println("12345 writingState " + writingState);
            if (writingState != null && writingState == ActionState.ACTIVE) return;

            chatMiddlewareDatabaseDao.saveWritingAction(chatId, ActionState.ACTIVE);

            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat == null) {
                throw new SendWritingStatusMessageNotificationException("Chat not found");
            }
            String localActorPublicKey = chat.getLocalActorPublicKey();
            String remoteActorPublicKey = chat.getRemoteActorPublicKey();
            networkServiceChatManager.sendWritingStatus(
                    localActorPublicKey,
                    remoteActorPublicKey,
                    chat.getChatId()
            );
//            chatMiddlewareDatabaseDao.saveWritingAction(chatId, ActionState.PENDING);

        } catch (Exception e) {
            throw new SendWritingStatusMessageNotificationException(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public boolean checkWritingStatus(UUID chatId) throws CantGetWritingStatus {
        try {
            Chat chat = chatMiddlewareDatabaseDao.getChatByChatId(chatId);
            if (chat != null)
                return chat.isWriting();
            else return false;
        } catch (CantGetChatException e) {
            throw new CantGetWritingStatus(
                    e,
                    "Something went wrong",
                    "");
        } catch (DatabaseOperationException e) {
            throw new CantGetWritingStatus(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public void activeOnlineStatus(String remotePublicKey) throws CantGetOnlineStatus {
        try {
            if (remotePublicKey == null) {
                List<ActionOnline> actionOnlines = chatMiddlewareDatabaseDao.getOnlineActionsByActiveState();
                if (actionOnlines == null || actionOnlines.isEmpty()) return;
                for (ActionOnline actionOnline : actionOnlines) {
                    chatMiddlewareDatabaseDao.saveOnlineActionState(actionOnline.getPublicKey(), ActionState.NONE);
                }
                return;
            }

            ActionState onlineState = chatMiddlewareDatabaseDao.getOnlineActionStateByPk(remotePublicKey);
            System.out.println("12345 onlineState " + onlineState);
            if (onlineState != null && onlineState == ActionState.ACTIVE) return;

            chatMiddlewareDatabaseDao.saveOnlineActionState(remotePublicKey, ActionState.ACTIVE);

        } catch (CantSaveActionException e) {
            throw new CantGetOnlineStatus(
                    e,
                    "Something went wrong",
                    "");
        } catch (CantGetPendingActionListException e) {
            e.printStackTrace();
        }
    }

    public boolean checkOnlineStatus(String remotePublicKey) throws CantGetOnlineStatus {
        try {
            ActionOnline onlineAction = chatMiddlewareDatabaseDao.getOnlineActionByPk(remotePublicKey);
            if (onlineAction != null)
                return onlineAction.getValue();
            else return false;
        } catch (CantSaveActionException e) {
            throw new CantGetOnlineStatus(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    public String checkLastConnection(String remotePublicKey) throws CantGetOnlineStatus {
        try {
            ActionOnline onlineAction = chatMiddlewareDatabaseDao.getOnlineActionByPk(remotePublicKey);
            if (onlineAction != null && onlineAction.getLastConnection() != null)
                return onlineAction.getLastConnection();
            else return "no record";
        } catch (CantSaveActionException e) {
            throw new CantGetOnlineStatus(
                    e,
                    "Something went wrong",
                    "");
        }
    }

    /**
     * This method returns the Network Service public key
     *
     * @return
     * @throws CantGetNetworkServicePublicKeyException
     */
    @Override
    public String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException {
        try {
            if (this.networkServiceChatManager == null) {
                throw new CantGetNetworkServicePublicKeyException("The Network Service is not starting");
            }
            String networkServicePublicKey = this.networkServiceChatManager.getNetWorkServicePublicKey();
            if (networkServicePublicKey == null) {
                throw new CantGetNetworkServicePublicKeyException("The Network Service public key is null");
            }
            return networkServicePublicKey;
        } catch (Exception exception) {
            chatMiddlewarePluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetNetworkServicePublicKeyException(
                    FermatException.wrapException(exception),
                    "Getting Network Service public key.",
                    "Unexpected exception");
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
                createdMessage.setStatus(MessageStatus.SEND);
            } catch (IllegalArgumentException e) {
                /**
                 * In this case, any argument in chat or message was null or not properly set.
                 * I'm gonna change the status to CANNOT_SEND to avoid send this message.
                 */
                createdMessage.setStatus(MessageStatus.CANNOT_SEND);
            }
            chatMiddlewareDatabaseDao.saveMessage(createdMessage);

            FermatBundle fermatBundle2 = new FermatBundle();
            fermatBundle2.put(Broadcaster.PUBLISH_ID, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
            fermatBundle2.put(Broadcaster.NOTIFICATION_TYPE, ChatBroadcasterConstants.CHAT_UPDATE_VIEW);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode(), fermatBundle2);

//            broadcaster.publish(BroadcasterType.UPDATE_VIEW, BROADCAST_CODE);
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

    @Override
    public void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException {
        try {
            chatMiddlewareDatabaseDao.saveGroupMember(groupMember);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException {
        try {
            chatMiddlewareDatabaseDao.deleteGroupMember(groupMember);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateActorConnection(ChatActorConnection chatActorConnection) {
        chatActorConnectionManager.updateActorConnection(chatActorConnection);
    }

    private HashMap<PlatformComponentType, Object> checkSelfIdentitiesMap(
            HashMap<PlatformComponentType, Object> selfIdentitiesMap) throws
            CantGetNetworkServicePublicKeyException {
        if (selfIdentitiesMap.isEmpty()) {
            String chatNetworkServicePublicKey = getNetworkServicePublicKey();
            selfIdentitiesMap.put(
                    PlatformComponentType.NETWORK_SERVICE,
                    chatNetworkServicePublicKey);
            return selfIdentitiesMap;
        }
        return selfIdentitiesMap;
    }

}
