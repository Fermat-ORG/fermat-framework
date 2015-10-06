package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/30/15.
 */
public class CantGetDesignerException extends FermatException {
    public final static String DEFAULT_MESSAGE = "There was an error trying to get the Designer information";

    public CantGetDesignerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
