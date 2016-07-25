package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class ActorTypeNotSupportedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR TYPE NOT SUPPORTED EXCEPTION";

    public ActorTypeNotSupportedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorTypeNotSupportedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
