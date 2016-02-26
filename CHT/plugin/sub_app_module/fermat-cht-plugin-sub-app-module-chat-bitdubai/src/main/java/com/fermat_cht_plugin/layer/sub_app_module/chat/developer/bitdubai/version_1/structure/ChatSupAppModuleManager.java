package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 */
public class ChatSupAppModuleManager implements ChatManager {

    private final MiddlewareChatManager middlewareChatManager;

    public ChatSupAppModuleManager(MiddlewareChatManager middlewareChatManager)
    {
        this.middlewareChatManager = middlewareChatManager;
    }

    @Override
    public List<Chat> getChats() throws CantGetChatException {
        return middlewareChatManager.getChats();
    }

    @Override
    public Chat getChatByChatId(UUID chatId) throws CantGetChatException {
        return middlewareChatManager.getChatByChatId(chatId);
    }

    @Override
    public Chat newEmptyInstanceChat() throws CantNewEmptyChatException {
        return middlewareChatManager.newEmptyInstanceChat();
    }

    @Override
    public void saveChat(Chat chat) throws CantSaveChatException {
        middlewareChatManager.saveChat(chat);
    }

    @Override
    public void deleteChat(Chat chat) throws CantDeleteChatException {
        middlewareChatManager.deleteChat(chat);
    }

    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        return middlewareChatManager.getMessages();
    }

    @Override
    public List<Message> getMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessageByChatId(chatId);
    }

    @Override
    public Message getMessageByMessageId(UUID messageId) throws CantGetMessageException {
        return middlewareChatManager.getMessageByMessageId(messageId);
    }

    @Override
    public Message newEmptyInstanceMessage() throws CantNewEmptyMessageException {
        return middlewareChatManager.newEmptyInstanceMessage();
    }

    @Override
    public void saveMessage(Message message) throws CantSaveMessageException {
        middlewareChatManager.saveMessage(message);
    }

    @Override
    public void deleteMessage(Message message) throws  CantDeleteMessageException {
        middlewareChatManager.deleteMessage(message);
    }

    @Override
    public List<Contact> getContacts() throws CantGetContactException {
        return middlewareChatManager.getContacts();
    }

    @Override
    public Contact getContactByContactId(UUID contactId) throws CantGetContactException {
        return middlewareChatManager.getContactByContactId(contactId);
    }

    @Override
    public Contact newEmptyInstanceContact() throws CantNewEmptyContactException {
        return newEmptyInstanceContact();
    }

    @Override
    public void saveContact(Contact contact) throws CantSaveContactException {
        middlewareChatManager.saveContact(contact);
    }

    @Override
    public void deleteContact(Contact contact) throws CantDeleteContactException {
        middlewareChatManager.deleteContact(contact);
    }

    @Override
    public List<Contact> discoverActorsRegistered() throws CantGetContactException {
        return middlewareChatManager.discoverActorsRegistered();
    }

    /**
     * This method returns the Network Service public key
     * @return
     * @throws CantGetNetworkServicePublicKeyException
     */
    @Override
    public String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException {
        return middlewareChatManager.getNetworkServicePublicKey();
    }

    /**
     * This method return a HashMap with the possible self identities.
     * The HashMap contains a Key-value like PlatformComponentType-ActorPublicKey.
     * If there no identities created in any platform, this hashMaps contains the public chat Network
     * Service.
     * @return
     */
    @Override
    public HashMap<PlatformComponentType, String> getSelfIdentities() {
        return middlewareChatManager.getSelfIdentities();
    }
}
