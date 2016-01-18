package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by root on 05/01/16.
 */
public interface ChatMetadata {

    UUID getIdChat();

    UUID getIdObject();

    String getLocalActorType();

    String getLocalActorPubKey();

    String getRemoteActorType();

    String getRemoteActorPubKey();

    String getChatName();

    ChatMessageStatus getChatMessageStatus();

    Timestamp getDate();

    UUID getIdMessage();

    String getMessage();
    DistributionStatus getDistributionStatus();

}
