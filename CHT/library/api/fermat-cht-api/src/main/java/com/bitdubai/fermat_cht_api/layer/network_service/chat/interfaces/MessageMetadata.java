package com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces;

import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;

import java.util.UUID;

/**
 * Created by Gabriel Araujo on 16/08/16.
 */
public interface MessageMetadata extends MetadataToSend {

    UUID getMessageId();

    MessageStatus getMessageStatus();

    String getMessage();

}
