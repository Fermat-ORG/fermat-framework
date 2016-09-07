package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
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
    private ChatStatus status;
    private Timestamp date;
    private Timestamp lastMessageDate;

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
    public ChatStatus getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(ChatStatus status) {
        this.status = status;
    }

    @Override
    public Timestamp getCreationDate() {
        return this.date;
    }

    @Override
    public void setCreationDate(Timestamp date) {
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
    public String toString() {
        return "ChatImpl{" +
                "chatId=" + chatId +
                ", localActorPublicKey='" + localActorPublicKey + '\'' +
                ", remoteActorPublicKey='" + remoteActorPublicKey + '\'' +
                ", status=" + status +
                ", date=" + date +
                ", lastMessageDate=" + lastMessageDate +
                '}';
    }
}
