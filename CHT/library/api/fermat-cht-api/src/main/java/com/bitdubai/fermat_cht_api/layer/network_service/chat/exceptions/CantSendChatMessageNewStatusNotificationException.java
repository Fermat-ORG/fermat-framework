package com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by root on 06/10/15.
 */
public class CantSendChatMessageNewStatusNotificationException extends CHTException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND CHAT MESSAGE NEW STATUS NOTIFICATION";

    public CantSendChatMessageNewStatusNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendChatMessageNewStatusNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendChatMessageNewStatusNotificationException(final String message) {
        this(message, null);
    }

    public CantSendChatMessageNewStatusNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendChatMessageNewStatusNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
