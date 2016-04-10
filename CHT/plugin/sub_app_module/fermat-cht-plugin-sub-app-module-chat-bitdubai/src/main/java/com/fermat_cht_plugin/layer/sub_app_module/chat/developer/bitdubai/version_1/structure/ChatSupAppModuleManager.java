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
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
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
    private final ChatIdentityManager chatIdentityManager;

    public ChatSupAppModuleManager(MiddlewareChatManager middlewareChatManager, ChatIdentityManager chatIdentityManager)
    {
        this.middlewareChatManager = middlewareChatManager;
        this.chatIdentityManager   = chatIdentityManager;
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


    /**
     * This method returns the Network Service public key
     * @return
     * @throws CantGetNetworkServicePublicKeyException
     */
    @Override
    public String getNetworkServicePublicKey() throws CantGetNetworkServicePublicKeyException {
        return middlewareChatManager.getNetworkServicePublicKey();
    }


    @Override
    public boolean isIdentityDevice() throws  CantListChatIdentityException {
        if(chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser().isEmpty())
            return false;
        else return true;
    }

    @Override
    public List<ChatIdentity> getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        return chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser();
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
    public void saveGroupMember(GroupMember groupMember) throws CantSaveGroupMemberException {
        middlewareChatManager.saveGroupMember(groupMember);
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) throws CantDeleteGroupMemberException {
        middlewareChatManager.deleteGroupMember(groupMember);
    }


    @Override
    public List<GroupMember> getGroupMembersByGroupId(UUID groupId) throws CantListGroupMemberException {
        return getGroupMembersByGroupId(groupId);
    }

    @Override
    public void clearChatMessageByChatId(UUID chatId) throws CantDeleteMessageException, CantGetMessageException {
        List<Message> messages = middlewareChatManager.getMessagesByChatId(chatId);

        for (Message message : messages)
        {
            middlewareChatManager.deleteMessage(message);
        }
    }
}
