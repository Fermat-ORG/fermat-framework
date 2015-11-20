package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/28/15.
 */
public class CantGetItemInformationException extends FermatException {
    public final static String DEFAULT_MESSAGE = "Error trying to get Item information.";
    public CantGetItemInformationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
