package com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by Gabriel Araujo on 06/10/15.
 */
public class CantSendChatMessageMetadataException extends CHTException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND CHAT MESSAGE METADATA";

    public CantSendChatMessageMetadataException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendChatMessageMetadataException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendChatMessageMetadataException(final String message) {
        this(message, null);
    }

    public CantSendChatMessageMetadataException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendChatMessageMetadataException() {
        this(DEFAULT_MESSAGE);
    }
}
