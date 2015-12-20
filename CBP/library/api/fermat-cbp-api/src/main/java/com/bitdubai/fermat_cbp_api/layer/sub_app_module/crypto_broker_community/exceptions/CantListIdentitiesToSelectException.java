package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantListIdentitiesToSelectException</code>
 * is thrown when there is an error trying to list identities to select.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CantListIdentitiesToSelectException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST IDENTITIES TO SELECT EXCEPTION";

    public CantListIdentitiesToSelectException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListIdentitiesToSelectException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
