package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.ChatMessageStatus;
import com.bitdubai.fermat_cht_api.layer.network_service.chat.enums.DistributionStatus;
import java.sql.Timestamp;

/**
 * Created by root on 05/01/16.
 */
public interface ChatMetada {

    Integer getIdChat();

    Integer getIdObjecto();

    String getLocalActorType();

    String getLocalActorPubKey();

    String getRemoteActorType();

    String getRemoteActorPubKey();

    String getChatName();

    ChatMessageStatus getChatMessageStatus();

    Timestamp getDate();

    Integer getIdMessage();

    String getMessage();
    DistributionStatus getDistributionStatus();

}
