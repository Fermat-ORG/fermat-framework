package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception {@link CantHandleExtraDataRequestEventException} is thrown when we're trying to handle the request of extra data such as the quotes.
 * <p/>
 * Created by Nelson Ramirez - (nelsonalfo@gmail.com) on 17/04/2016.
 */
public class CantHandleExtraDataRequestEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT HANDLE THE EXTRA DATA REQUEST EVENT";

    public CantHandleExtraDataRequestEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleExtraDataRequestEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleExtraDataRequestEventException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
