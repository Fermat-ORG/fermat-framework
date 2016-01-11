package com.bitdubai.fermat_cht_api.layer.middleware.utils;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public class ChatImpl implements Chat {
    //TODO: Documentar
    private UUID       chatId;
    private UUID       objectId;
    private String     localActorType;
    private String     localActorPublicKey;
    private String     remoteActorType;
    private String     remoteActorPublicKey;
    private String     chatName;
    private ChatStatus status;
    private Date       date;
    private Date       lastMessageDate;

    public ChatImpl(){};

    public ChatImpl(UUID chatId,
                    UUID objectId,
                    String localActorType,
                    String localActorPublicKey,
                    String remoteActorType,
                    String remoteActorPublicKey,
                    String chatName,
                    ChatStatus status,
                    Date date,
                    Date lastMessageDate
    )
    {
        this.chatId               = chatId;
        this.objectId             = objectId;
        this.localActorType       = localActorType;
        this.localActorPublicKey  = localActorPublicKey;
        this.remoteActorType      = remoteActorType;
        this.remoteActorPublicKey = remoteActorPublicKey;
        this.chatName             = chatName;
        this.status               = status;
        this.date                 = date;
        this.lastMessageDate      = lastMessageDate;
    };

    @Override
    public UUID getChatId() {
        return chatId;
    }

    @Override
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    @Override
    public UUID getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getLocalActorType() {
        return localActorType;
    }

    @Override
    public void setLocalActorType(String localActorType) {
        this.localActorType = localActorType;
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
    public String getRemoteActorType() {
        return this.remoteActorType;
    }

    @Override
    public void setRemoteActorType(String remoteActorType) {
        this.remoteActorType = remoteActorType;
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
    public Date getDate() {
        return this.date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Date getLastMessageDate() {
        return this.lastMessageDate;
    }

    @Override
    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
