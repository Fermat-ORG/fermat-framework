package com.bitdubai.fermat_cht_api.layer.middleware.mocks;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactListException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * This class is only for testing
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class ChatMock implements Chat {

    private String localActorPubKey;
    private String remoteActorPubKey;
    @Override
    public UUID getChatId() {
        return UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f");
    }

    @Override
    public void setChatId(UUID chatId) {

    }

    @Override
    public UUID getObjectId() {
        return UUID.fromString("f85ac4bf-4cb7-4c07-b923-2edd3efef02c");
    }

    @Override
    public void setObjectId(UUID objectId) {

    }

    @Override
    public PlatformComponentType getLocalActorType() {
        return PlatformComponentType.NETWORK_SERVICE;
    }

    @Override
    public void setLocalActorType(PlatformComponentType localActorType) {

    }

    @Override
    public String getLocalActorPublicKey() {
        return this.localActorPubKey;
    }

    @Override
    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPubKey = localActorPublicKey;
    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return PlatformComponentType.NETWORK_SERVICE;
    }

    @Override
    public void setRemoteActorType(PlatformComponentType remoteActorType) {

    }

    @Override
    public String getRemoteActorPublicKey() {
        return this.remoteActorPubKey;
    }

    @Override
    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPubKey = remoteActorPublicKey;
    }

    @Override
    public String getChatName() {
        return "Evil Chat";
    }

    @Override
    public void setChatName(String chatName) {

    }

    @Override
    public ChatStatus getStatus() {
        return ChatStatus.VISSIBLE;
    }

    @Override
    public void setStatus(ChatStatus status) {

    }

    @Override
    public Timestamp getDate() {
        return new Timestamp(2001l);
    }

    @Override
    public void setDate(Timestamp date) {

    }

    @Override
    public Timestamp getLastMessageDate() {
        return new Timestamp(2001l);
    }

    @Override
    public void setLastMessageDate(Timestamp lastMessageDate) {

    }

    @Override
    public List<Contact> getContactAssociated() {
        return null;
    }

    @Override
    public void setContactAssociated(List<Contact> chatContacts) {

    }

    @Override
    public void setContactAssociated(Contact contact) {

    }

    @Override
    public boolean isWriting() {
        return false;
    }

    @Override
    public void setIsWriting(boolean isWriting) {

    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public void setIsOnline(boolean isWriting) {

    }

    @Override
    public String getContactListString() {
        return null;
    }

    @Override
    public void setContactAssociated(String chatContacts) throws CantGetContactListException {

    }

    @Override
    public List<Message> getMessagesAsociated() {
        return null;
    }

    @Override
    public void setMessagesAsociated(List<Message> messages) {

    }

    @Override
    public TypeChat getTypeChat() {
        return null;
    }

    @Override
    public void setTypeChat(TypeChat typeChat) {

    }

    @Override
    public List<GroupMember> getGroupMembersAssociated() {
        return null;
    }

    @Override
    public void setGroupMembersAssociated(List<GroupMember> groupMembers) {

    }

    @Override
    public boolean getScheduledDelivery() {
        return false;
    }

    @Override
    public void setScheduledDelivery(boolean scheduledDelivery) {

    }
}
