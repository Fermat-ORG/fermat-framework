package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.GroupMember;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/01/16.
 */
public class ChatMetadataRecord implements ChatMetadata {

    UUID chatId;
    UUID objectId;
    PlatformComponentType localActorType;
    String localActorPublicKey;
    PlatformComponentType remoteActorType;
    String remoteActorPublicKey;
    String chatName;
    ChatMessageStatus chatMessageStatus;
    MessageStatus messageStatus;
    String date;
    UUID messageId;
    String message;
    DistributionStatus distributionStatus;
    TypeChat typeChat;
    List<GroupMember> groupMembers;

    public ChatMetadataRecord(
            UUID chatId,
            UUID objectId,
            PlatformComponentType localActorType,
            String localActorPublicKey,
            PlatformComponentType remoteActorType,
            String remoteActorPublicKey,
            String chatName,
            ChatMessageStatus chatMessageStatus,
            MessageStatus messageStatus,
            String date,
            UUID messageId,
            String message,
            DistributionStatus distributionStatus,
            TypeChat typeChat,
            List<GroupMember> groupMembers) {
        this.chatId = chatId;
        this.objectId = objectId;
        this.localActorType = localActorType;
        this.localActorPublicKey = localActorPublicKey;
        this.remoteActorType = remoteActorType;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.chatName = chatName;
        this.chatMessageStatus = chatMessageStatus;
        this.messageStatus = messageStatus;
        this.date = date;
        this.messageId = messageId;
        this.message = message;
        this.distributionStatus = distributionStatus;
        this.typeChat = typeChat;
        this.groupMembers = groupMembers;
    }


    @Override
    public UUID getObjectId() {
        return this.objectId;
    }

    @Override
    public PlatformComponentType getLocalActorType() {
        return this.localActorType;
    }

    @Override
    public String getLocalActorPublicKey() {
        return this.localActorPublicKey;
    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return this.remoteActorType;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return this.remoteActorPublicKey;
    }

    @Override
    public String getChatName() {
        return this.chatName;
    }

    @Override
    public ChatMessageStatus getChatMessageStatus() {
        return this.chatMessageStatus;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return this.messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public String getDate() {
        return this.date;
    }

    @Override
    public UUID getMessageId() {
        return this.messageId;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public DistributionStatus getDistributionStatus() {
        return this.distributionStatus;
    }

    @Override
    public TypeChat getTypeChat() {
        return typeChat;
    }

    @Override
    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ChatMetadataRecord fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ChatMetadataRecord.class);
    }

    @Override
    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public void setLocalActorType(PlatformComponentType localActorType) {
        this.localActorType = localActorType;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    public void setRemoteActorType(PlatformComponentType remoteActorType) {
        this.remoteActorType = remoteActorType;
    }

    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    /**
     * This method returns a XML String with all the objects set in this record
     *
     * @return
     */
    public String toString() {
        return XMLParser.parseObject(this);
    }
}
