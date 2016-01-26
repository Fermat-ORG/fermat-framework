package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatMetadata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Gabriel Araujo on 06/01/16.
 */
public class ChatMetadataTransactionRecord implements ChatMetadata{

    private UUID transactionId;

    private String transactionHash;

    private UUID chatId;

    private UUID objectId;

    private PlatformComponentType localActorType;

    private String localActorPublicKey;

    private PlatformComponentType remoteActorType;

    private String remoteActorPublicKey;

    private String chatName;

    private ChatMessageStatus chatMessageStatus;

    private MessageStatus messageStatus;

    private Timestamp date;

    private UUID messageId;

    private String message;

    private DistributionStatus distributionStatus;

    /**
     * Represent the value of processed
     */
    private String processed;

    /**
     * Represent the value of PROCESSED
     */
    public final static String PROCESSED = "Y";

    /**
     * Represent the value of NO_PROCESSED
     */
    public final static String NO_PROCESSED = "N";

    /**
     * Get the Processed
     * @return String
     */
    public String getProcessed() {
        return processed;
    }

    /**
     * Set the Processed
     * @param processed
     */
    public void setProcessed(String processed) {
        this.processed = processed;
    }
    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    @Override
    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    @Override
    public String toString() {
        return "ChatMetadataTransactionRecord{" +
                "chatId=" + chatId +
                ", objectId=" + objectId +
                ", localActorType='" + localActorType + '\'' +
                ", localActorPublicKey='" + localActorPublicKey + '\'' +
                ", remoteActorType='" + remoteActorType + '\'' +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", chatName='" + chatName + '\'' +
                ", chatMessageStatus=" + chatMessageStatus +
                ", date=" + date +
                ", messageId=" + messageId +
                ", message='" + message + '\'' +
                '}';
    }



    @Override
    public PlatformComponentType getLocalActorType() {
        return localActorType;
    }

    public void setLocalActorType(PlatformComponentType localActorType) {
        this.localActorType = localActorType;
    }

    @Override
    public String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public PlatformComponentType getRemoteActorType() {
        return remoteActorType;
    }

    public void setRemoteActorType(PlatformComponentType remoteActorType) {
        this.remoteActorType = remoteActorType;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return remoteActorPublicKey;
    }

    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    @Override
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public ChatMessageStatus getChatMessageStatus() {
        return chatMessageStatus;
    }

    public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
        this.chatMessageStatus = chatMessageStatus;
    }

    @Override
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    @Override
    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
