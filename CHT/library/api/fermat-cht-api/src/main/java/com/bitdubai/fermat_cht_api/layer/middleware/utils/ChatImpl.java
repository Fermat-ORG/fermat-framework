package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeChat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 * pdated by Manuel Perez on 08/02/2016
 */
public class ChatImpl implements Chat {

    private UUID chatId;
    private String localActorPublicKey;
    private String remoteActorPublicKey;
    private String chatName;
    private ChatStatus status;
    private Timestamp date;
    private Timestamp lastMessageDate;
    private TypeChat typeChat;
    private boolean scheduledDelivery;

    /**
     * Constructor without arguments
     */
    public ChatImpl() {
    }

    @Override
    public UUID getChatId() {
        return chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public String getLocalActorPublicKey() {
        return localActorPublicKey;
    }

    @Override
    public void setLocalActorPublicKey(String localActorPublicKey) {
        this.localActorPublicKey = localActorPublicKey;
    }

    @Override
    public String getRemoteActorPublicKey() {
        return this.remoteActorPublicKey;
    }

    @Override
    public void setRemoteActorPublicKey(String remoteActorPublicKey) {
        this.remoteActorPublicKey = remoteActorPublicKey;
    }

    @Override
    public String getChatName() {
        return this.chatName;
    }

    @Override
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public ChatStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(ChatStatus status) {
        this.status = status;
    }

    @Override
    public Timestamp getDate() {
        return this.date;
    }

    @Override
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public Timestamp getLastMessageDate() {
        return this.lastMessageDate;
    }

    @Override
    public void setLastMessageDate(Timestamp lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    @Override
    public TypeChat getTypeChat() {
        return typeChat;
    }

    @Override
    public void setTypeChat(TypeChat typeChat) {
        this.typeChat = typeChat;
    }

    @Override
    public boolean getScheduledDelivery() {
        return scheduledDelivery;
    }

    @Override
    public void setScheduledDelivery(boolean scheduledDelivery) {

        this.scheduledDelivery = scheduledDelivery;
    }

    @Override
    public String toString() {
        return "ChatImpl{" +
                "chatId=" + chatId +
                ", localActorPublicKey='" + localActorPublicKey + '\'' +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", chatName='" + chatName + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", lastMessageDate=" + lastMessageDate +
                ", typeChat=" + typeChat +
                ", scheduledDelivery=" + scheduledDelivery +
                '}';
    }
}
