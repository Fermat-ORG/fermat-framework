package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
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
    Date getDate();
    void setDate(Date date);
    Date getLastMessageDate();
    void setLastMessageDate(Date lastMessageDate);
}
