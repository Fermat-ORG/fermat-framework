package com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 07/04/16.
 */
public class CantChangeProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE PROTOCOL STATE EXCEPTION";

    public CantChangeProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
