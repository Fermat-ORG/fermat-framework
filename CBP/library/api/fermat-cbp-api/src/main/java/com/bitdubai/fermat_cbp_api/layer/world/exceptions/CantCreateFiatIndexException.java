package com.bitdubai.fermat_cbp_api.layer.world.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 11/21/2015.
 */
public class CantCreateFiatIndexException extends FermatException{

    public static final String DEFAULT_MESSAGE = "Failed to create a Fiat Index.";

    public CantCreateFiatIndexException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}