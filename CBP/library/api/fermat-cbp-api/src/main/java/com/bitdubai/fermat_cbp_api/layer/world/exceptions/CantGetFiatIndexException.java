package com.bitdubai.fermat_cbp_api.layer.world.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public class CantGetFiatIndexException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed to get the Fiat Index.";

    public CantGetFiatIndexException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}