package com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class CantDenyConnectionRequestException extends FermatException {
    private static final String DEFAULT_MESSAGE = "CAN'T DENY CONNECTION REQUEST EXCEPTION";

    public CantDenyConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDenyConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
