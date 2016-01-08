package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 */
public interface Chat {
    UUID getChatId();
    void setChatId(UUID chatId);
    UUID getObjectId();
    void setObjectId(UUID objectId);
    String getLocalActorType();
    void setLocalActorType(String localActorType);
    String getLocalActorPublicKey();
    void setLocalActorPublicKey(String localActorPublicKey);
    String getRemoteActorType();
    void setRemoteActorType(String remoteActorType);
    String getRemoteActorPublicKey();
    void setRemoteActorPublicKey(String remoteActorPublicKey);
    String getChatName();
    void setChatName(String chatName);
    ChatStatus getStatus();
    void setStatus(ChatStatus status);
    Date getDate();
    void setDate(Date date);
    Date getLastMessageDate();
    void setLastMessageDate(Date lastMessageDate);
}
