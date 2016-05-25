package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactListException;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 * Updated by Manuel Perez on 08/02/2016
 */
public interface Chat extends Serializable {
    UUID getChatId();
    void setChatId(UUID chatId);
    UUID getObjectId();
    void setObjectId(UUID objectId);
    PlatformComponentType getLocalActorType();
    void setLocalActorType(PlatformComponentType localActorType);
    String getLocalActorPublicKey();
    void setLocalActorPublicKey(String localActorPublicKey);
    PlatformComponentType getRemoteActorType();
    void setRemoteActorType(PlatformComponentType remoteActorType);
    String getRemoteActorPublicKey();
    void setRemoteActorPublicKey(String remoteActorPublicKey);
    String getChatName();
    void setChatName(String chatName);
    ChatStatus getStatus();
    void setStatus(ChatStatus status);
    Timestamp getDate();
    void setDate(Timestamp date);
    Timestamp getLastMessageDate();
    void setLastMessageDate(Timestamp lastMessageDate);
    //New objects to make a relation between messages and this interface
    List<Contact> getContactAssociated();
    void setContactAssociated(List<Contact> chatContacts);
    void setContactAssociated(Contact contact);
    boolean isWriting();
    void setIsWriting(boolean isWriting);
    boolean isOnline();
    void setIsOnline(boolean isWriting);
     /**
     * This method will return a String with xml format with the contact list
     * @return
     */
    String getContactListString();

    /**
     * This method requires a valid List<Contact> XML String to set this list to this object
     * @param chatContacts
     * @throws CantGetContactListException
     */
    void setContactAssociated(String chatContacts) throws CantGetContactListException;

    List<Message> getMessagesAsociated();
    void setMessagesAsociated(List<Message> messages);

    TypeChat getTypeChat();
    void setTypeChat(TypeChat typeChat);

    List<GroupMember> getGroupMembersAssociated();
    void setGroupMembersAssociated(List<GroupMember> groupMembers);

    boolean getScheduledDelivery();
    void setScheduledDelivery(boolean scheduledDelivery);
}
