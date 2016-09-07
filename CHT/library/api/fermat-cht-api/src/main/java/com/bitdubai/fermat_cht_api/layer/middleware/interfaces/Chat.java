package com.bitdubai.fermat_cht_api.layer.middleware.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by franklin on 08/01/16.
 * Updated by Manuel Perez on 08/02/2016
 */
public interface Chat extends Serializable {

    UUID getChatId();

    void setChatId(UUID chatId);

    String getLocalActorPublicKey();

    void setLocalActorPublicKey(String localActorPublicKey);

    String getRemoteActorPublicKey();

    void setRemoteActorPublicKey(String remoteActorPublicKey);

    ChatStatus getStatus();

    void setStatus(ChatStatus status);

    Timestamp getCreationDate();

    void setCreationDate(Timestamp date);

    Timestamp getLastMessageDate();

    void setLastMessageDate(Timestamp lastMessageDate);

}
