package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOwnIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendNotificationNewIncomingMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cht_api.all_definition.util.ObjectChecker;
import com.bitdubai.fermat_cht_api.layer.middleware.event.IncomingChatMessageNotificationEvent;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.NetworkServiceChatManager;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.ChatMiddlewarePluginRoot;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.database.ChatMiddlewareDatabaseDao;
import com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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
     * Represents the contact factory
     */
    private ChatMiddlewareContactFactory chatMiddlewareContactFactory;

    /**
     * Represents the NetworkServiceChatManager
     */
    NetworkServiceChatManager networkServiceChatManager;

    /**
     * Represents the ErrorManager
     */
    ErrorManager errorManager;

    public ChatMiddlewareManager(
            ChatMiddlewareDatabaseDao chatMiddlewareDatabaseDao,
            ChatMiddlewareContactFactory chatMiddlewareContactFactory,
            ChatMiddlewarePluginRoot chatMiddlewarePluginRoot,
            NetworkServiceChatManager networkServiceChatManager,
            ErrorManager errorManager
    ) {
        this.chatMiddlewareDatabaseDao = chatMiddlewareDatabaseDao;
        this.chatMiddlewareContactFactory = chatMiddlewareContactFactory;
        this.chatMiddlewarePluginRoot = chatMiddlewarePluginRoot;
        this.networkServiceChatManager = networkServiceChatManager;
        this.errorManager = errorManager;
    }

    /**
     * This method returns a full list of chat recorded in database.
     * @return
     * @throws CantGetChatException
     */
    @Override
    public List<Chat> getChats() throws CantGetChatException {
        try{
            return this.chatMiddlewareDatabaseDao.getChatList();
        } catch (DatabaseOperationException exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception);
            throw new CantGetChatException(
                    exception,
                    "Getting the chat list from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
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
     * @param chatId
     * @return
     * @throws CantGetChatException
     */
    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        try{
            ObjectChecker.checkArgument(chatId, "The chat Id argument is null");
            return this.chatMiddlewareDatabaseDao.getChatByChatId(chatId);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "The chat Id probably is null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetChatException(
                    e,
                    "Getting a chat by UUID",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetChatException(
                    FermatException.wrapException(exception),
                    "Getting a chat by UUID",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a new empty instance chat.
     * @return
     * @throws CantNewEmptyChatException
     */
    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        try{
            return this.chatMiddlewareDatabaseDao.newEmptyInstanceChat();
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantNewEmptyChatException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance chat",
                    "Unexpected exception");
        }

    }

    /**
     * This method saves a chat, properly formed, in database.
     * @param chat
     * @throws CantSaveChatException
     */
    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        try{
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.saveChat(chat);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveChatException(
                    e,
                    "Saving a chat in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveChatException(
                    FermatException.wrapException(exception),
                    "Saving a chat in database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a chat from database.
     * @param chat
     * @throws CantDeleteChatException
     */
    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        try{
            ObjectChecker.checkArgument(chat, "The chat argument is null");
            this.chatMiddlewareDatabaseDao.deleteChat(chat);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "The chat probably is null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteChatException(
                    e,
                    "Deleting a chat from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteChatException(
                    FermatException.wrapException(exception),
                    "Deleting a chat from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a full message list.
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        try {
            return this.chatMiddlewareDatabaseDao.getMessages();
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting the full messages list",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting the full messages list",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns a message by chat Id.
     * @param chatId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public List<Message> getMessageByChatId(UUID chatId) throws CantGetMessageException {
        try{
            ObjectChecker.checkArgument(chatId, "The chat id argument is null");
            return this.chatMiddlewareDatabaseDao.getMessageByChatId(chatId);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method return a message by message id.
     * @param messageId
     * @return
     * @throws CantGetMessageException
     */
    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        try{
            ObjectChecker.checkArgument(messageId, "The message Id is null");
            return this.chatMiddlewareDatabaseDao.getMessageByMessageId(messageId);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "The chat id is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetMessageException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method return a new empty instance message.
     * @return
     * @throws CantNewEmptyMessageException
     */
    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        try{
            return this.chatMiddlewareDatabaseDao.newEmptyInstanceMessage();
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantNewEmptyMessageException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance message",
                    "Unexpected exception");
        }
    }

    /**
     * This method saves a message in database.
     * @param message
     * @throws CantSaveMessageException
     */
    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        try{
            ObjectChecker.checkArgument(message, "The message argument is null");
            this.chatMiddlewareDatabaseDao.saveMessage(message);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveMessageException(
                    e,
                    "Saving a message in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveMessageException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method deletes a message from database.
     * @param message
     * @throws CantDeleteMessageException
     */
    @Override
    public void deleteMessage(Message message) throws CantDeleteMessageException {
        try{
            ObjectChecker.checkArgument(message, "The message argument is null");
            this.chatMiddlewareDatabaseDao.deleteMessage(message);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteMessageException(
                    e,
                    "Deleting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteMessageException(
                    FermatException.wrapException(exception),
                    "Deleting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public List<Contact> getContacts() throws CantGetContactException {
        DatabaseTableFilter filter = null;
        try {
            return this.chatMiddlewareDatabaseDao.getContacts(filter);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContactException(
                    e,
                    "Getting the full contact list",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetContactException(
                    FermatException.wrapException(exception),
                    "Getting the full contact list",
                    "Unexpected exception");
        }
    }

    @Override
    public Contact getContactByContactId(UUID contactId) throws CantGetContactException {
        try{
            ObjectChecker.checkArgument(contactId, "The contact id argument is null");
            return this.chatMiddlewareDatabaseDao.getContactByContactId(contactId);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContactException(
                    e,
                    "Getting a message from database",
                    "The contact id is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetContactException(
                    e,
                    "Getting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetContactException(
                    FermatException.wrapException(exception),
                    "Getting a message from database",
                    "Unexpected exception");
        }
    }

    @Override
    public Contact newEmptyInstanceContact() throws CantNewEmptyContactException {
        try{
            return this.chatMiddlewareDatabaseDao.newEmptyInstanceContact();
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantNewEmptyContactException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance contact",
                    "Unexpected exception");
        }
    }

    @Override
    public void saveContact(Contact contact) throws CantSaveContactException {
        try{
            ObjectChecker.checkArgument(contact, "The contact argument is null");
            this.chatMiddlewareDatabaseDao.saveContact(contact);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveContactException(
                    e,
                    "Saving a contact in database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantSaveContactException(
                    e,
                    "Saving a contact in database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSaveContactException(
                    FermatException.wrapException(exception),
                    "Getting a new empty instance contact",
                    "Unexpected exception");
        }
    }

    @Override
    public void deleteContact(Contact contact) throws CantDeleteContactException {
        try{
            ObjectChecker.checkArgument(contact, "The contact argument is null");
            this.chatMiddlewareDatabaseDao.deleteContact(contact);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteContactException(
                    e,
                    "Deleting a message from database",
                    "The message is probably null");
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantDeleteContactException(
                    e,
                    "Deleting a message from database",
                    "An unexpected error happened in a database operation");
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantDeleteContactException(
                    FermatException.wrapException(exception),
                    "Deleting a message from database",
                    "Unexpected exception");
        }
    }

    /**
     * This method returns the active registered actors to contact list.
     * @return
     * @throws CantGetContactException
     */
    @Override
    public List<Contact> discoverActorsRegistered() throws CantGetContactException {
        try{
            return this.chatMiddlewareContactFactory.discoverDeviceActors();
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetContactException(
                    FermatException.wrapException(exception),
                    "Getting the actors registered",
                    "Unexpected exception");
        }

    }

    /**
     * This method will notify PIP to launch a new notification to user
     * @param publicKey
     * @param tittle
     * @param body
     * @throws CantSendNotificationNewIncomingMessageException
     */
    @Override
    public void notificationNewIncomingMessage(
            String publicKey,
            String tittle,
            String body) throws CantSendNotificationNewIncomingMessageException {
        try{
            //TODO MATIAS PLEASE CHECK THIS
            IncomingChatMessageNotificationEvent event =
                    (IncomingChatMessageNotificationEvent) this.chatMiddlewarePluginRoot.
                            getEventManager().
                            getNewEvent(
                                    com.bitdubai.fermat_cht_api.all_definition.events.enums.
                                            EventType.INCOMING_CHAT_MESSAGE_NOTIFICATION);
            event.setLocalPublicKey(publicKey);
            event.setAlertTitle(getSourceString(ChatMiddlewarePluginRoot.EVENT_SOURCE));
            event.setTextTitle(tittle);
            event.setTextBody(body);
            //event.setNotificationType(NotificationType.INCOMING_CHAT_MESSAGE.getCode());
            event.setSource(ChatMiddlewarePluginRoot.EVENT_SOURCE);
            this.chatMiddlewarePluginRoot.getEventManager().raiseEvent(event);
            System.out.println("MiddleWareChatPluginRoot - IncomingChatMessageNotificationEvent fired!: "+event.toString());
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantSendNotificationNewIncomingMessageException(
                    FermatException.wrapException(exception),
                    "Notification new incoming message",
                    "Unexpected exception");
        }

    }

    /**
     * This method returns the Network Service public key
     * @return
     * @throws CantGetNetworkServicePublicKeyException
     */
    @Override
    public String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException {
        try{
            if(this.networkServiceChatManager==null){
                throw new CantGetNetworkServicePublicKeyException("The Network Service is not starting");
            }
            String networkServicePublicKey=this.networkServiceChatManager.getNetWorkServicePublicKey();
            if(networkServicePublicKey==null){
                throw new CantGetNetworkServicePublicKeyException("The Network Service public key is null");
            }
            return networkServicePublicKey;
        } catch (Exception exception){
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetNetworkServicePublicKeyException(
                    FermatException.wrapException(exception),
                    "Getting Network Service public key.",
                    "Unexpected exception");
        }

    }

    /**
     * This method return a HashMap with the possible self identities.
     * The HashMap contains a Key-value like PlatformComponentType-ActorPublicKey.
     * If there no identities created in any platform, this hashMaps contains the public chat Network
     * Service.
     * @return
     */
    @Override
    public HashMap<PlatformComponentType, String> getSelfIdentities()
            throws CantGetOwnIdentitiesException {
        try {
            HashMap<PlatformComponentType, String> selfIdentitiesMap=
                    this.chatMiddlewareContactFactory.getSelfIdentities();
            selfIdentitiesMap=checkSelfIdentitiesMap(selfIdentitiesMap);
            return selfIdentitiesMap;
        } catch (CantGetNetworkServicePublicKeyException exception) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.CHAT_MIDDLEWARE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    FermatException.wrapException(exception));
            throw new CantGetOwnIdentitiesException(
                    FermatException.wrapException(exception),
                    "Checking own identities",
                    "Unexpected Exception getting network service public key");
        }
    }

    @Override
    public void saveChatUserIdentity(ChatUserIdentity chatUserIdentity) throws CantSaveChatUserIdentityException {

    }

    @Override
    public void deleteChatUserIdentity(ChatUserIdentity chatUserIdentity) throws CantDeleteChatUserIdentityException {

    }

    @Override
    public List<ChatUserIdentity> getChatUserIdentities() throws CantGetChatUserIdentityException {
        return null;
    }

    @Override
    public ChatUserIdentity getChatUserIdentity(String publicKey) throws CantGetChatUserIdentityException {
        return null;
    }

    @Override
    public void saveContactConnection(ContactConnection contactConnection) throws CantSaveContactConnectionException {

    }

    @Override
    public void deleteContactConnection(ContactConnection chatUserIdentity) throws CantDeleteContactConnectionException {

    }

    @Override
    public List<ContactConnection> getContactConnections() throws CantGetContactConnectionException {
        return null;
    }

    @Override
    public ContactConnection getContactConnection(UUID contactId) throws CantGetContactConnectionException {
        return null;
    }


    private HashMap<PlatformComponentType, String> checkSelfIdentitiesMap(
            HashMap<PlatformComponentType, String> selfIdentitiesMap) throws
            CantGetNetworkServicePublicKeyException {
        if(selfIdentitiesMap.isEmpty()) {
            String chatNetworkServicePublicKey = getNetworkServicePublicKey();
            selfIdentitiesMap.put(
                    PlatformComponentType.NETWORK_SERVICE,
                    chatNetworkServicePublicKey);
            return selfIdentitiesMap;
        }
        return selfIdentitiesMap;
    }

    private String getSourceString(EventSource eventSource){
        switch (eventSource){
            case MIDDLEWARE_CHAT_MANAGER:
                return INCOMING_CHAT_MESSAGE_NOTIFICATION;
            default:
                return "Method: getSourceString - Invalid value in Source String";

        }
    }

}
