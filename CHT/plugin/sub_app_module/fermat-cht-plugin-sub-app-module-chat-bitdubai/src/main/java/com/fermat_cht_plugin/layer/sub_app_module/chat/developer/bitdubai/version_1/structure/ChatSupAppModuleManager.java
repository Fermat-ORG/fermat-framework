package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateSelfIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOwnIdentitiesException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantListGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantNewEmptyMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveGroupMemberException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSendChatMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.SendStatusUpdateMessageNotificationException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Group;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 06/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
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
    public void deleteChats() throws CantDeleteChatException {
        middlewareChatManager.deleteChats();
    }

    @Override
    public void deleteMessagesByChatId(UUID chatId) throws CantDeleteMessageException {
        middlewareChatManager.deleteMessagesByChatId(chatId);
    }

    @Override
    public List<Message> getMessages() throws CantGetMessageException {
        return middlewareChatManager.getMessages();
    }

    @Override
    public List<Message> getMessagesByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessagesByChatId(chatId);
    }

    @Override
    public Message getMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getMessageByChatId(chatId);
    }

    @Override
    public int getCountMessageByChatId(UUID chatId) throws CantGetMessageException {
        return middlewareChatManager.getCountMessageByChatId(chatId);
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
        System.out.println("*** 12345 case 2:send msg in Module layer" + new Timestamp(System.currentTimeMillis()));
        middlewareChatManager.saveMessage(message);
    }

    @Override
    public void deleteMessage(Message message) throws  CantDeleteMessageException {
        middlewareChatManager.deleteMessage(message);
    }

    @Override
    public Chat getChatByRemotePublicKey(String publicKey) throws CantGetChatException {
        return middlewareChatManager.getChatByRemotePublicKey(publicKey);
    }

    @Override
    public void sendReadMessageNotification(Message message) throws SendStatusUpdateMessageNotificationException {
        middlewareChatManager.sendReadMessageNotification(message);
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
    public List<ContactConnection> discoverActorsRegistered() throws CantGetContactConnectionException {
        try
        {
            List<ContactConnection> contactConnections = middlewareChatManager.getContactConnections();

            for (ContactConnection contactConnection : contactConnections)
            {
                middlewareChatManager.deleteContactConnection(contactConnection);
            }

        } catch (CantGetContactConnectionException e) {
            throw new CantGetContactConnectionException(
                    e,
                    "delete contact connections",
                    "Cannot get the contact connection"
            );
        } catch (CantDeleteContactConnectionException e) {
            throw new CantGetContactConnectionException(
                    e,
                    "delete contact connections",
                    "Cannot delete contact connections"
            );
        }

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
    public HashMap<PlatformComponentType, Object> getSelfIdentities()
            throws CantGetOwnIdentitiesException {
        return middlewareChatManager.getSelfIdentities();
    }

    /**
     * This method returns the contact id by local public key.
     *
     * @param localPublicKey
     * @return
     * @throws CantGetContactException
     */
    @Override
    public Contact getContactByLocalPublicKey(String localPublicKey) throws CantGetContactException {
        return middlewareChatManager.getContactByLocalPublicKey(localPublicKey);
    }

    @Override
    public void createSelfIdentities() throws CantCreateSelfIdentityException {
        middlewareChatManager.createSelfIdentities();
    }

    @Override
    public boolean isIdentityDevice() throws CantGetChatUserIdentityException {
        if(middlewareChatManager.getChatUserIdentities().isEmpty())
            return false;
        else return true;
    }

    @Override
    public List<ChatUserIdentity> getChatUserIdentities() throws CantGetChatUserIdentityException {
        return middlewareChatManager.getChatUserIdentities();
    }

    @Override
    public ChatUserIdentity getChatUserIdentity(String publicKey) throws CantGetChatUserIdentityException {
        return middlewareChatManager.getChatUserIdentity(publicKey);
    }

    @Override
    public void saveContactConnection(ContactConnection contactConnection) throws CantSaveContactConnectionException {
        middlewareChatManager.saveContactConnection(contactConnection);
    }

    @Override
    public void deleteContactConnection(ContactConnection chatUserIdentity) throws CantDeleteContactConnectionException {
        middlewareChatManager.deleteContactConnection(chatUserIdentity);
    }

    @Override
    public List<ContactConnection> getContactConnections() throws CantGetContactConnectionException {
        return middlewareChatManager.getContactConnections();
    }

    @Override
    public ContactConnection getContactConnectionByContactId(UUID contactId) throws CantGetContactConnectionException {
        return middlewareChatManager.getContactConnection(contactId);
    }

    /**
     * This method sends the message through the Chat Network Service
     *
     * @param createdMessage
     * @throws CantSendChatMessageException
     */
    @Override
    public void sendMessage(Message createdMessage) throws CantSendChatMessageException {
        middlewareChatManager.sendMessage(createdMessage);
    }

    @Override
    public void saveGroup(Group group) throws CantSaveGroupException {
        middlewareChatManager.saveGroup(group);
    }

    @Override
    public void deleteGroup(Group group) throws CantDeleteGroupException {
        middlewareChatManager.deleteGroup(group);
    }

    @Override
    public List<Group> getGroups() throws CantListGroupException {
        return null;
    }

    @Override
    public Group getGroup(UUID groupId) throws CantGetGroupException {
        return null;
    }


    @Override
    public void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException {
        middlewareChatManager.saveGroupMember(groupMember);
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException {
        middlewareChatManager.deleteGroupMember(groupMember);
    }

    @Override
    public List<GroupMember> getGroupMembersByGroupId(UUID groupId) throws CantListGroupMemberException {
        return null;
    }
}
