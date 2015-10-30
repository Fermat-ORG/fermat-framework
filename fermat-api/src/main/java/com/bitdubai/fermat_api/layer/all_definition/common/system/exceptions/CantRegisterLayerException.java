package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantRegisterLayerException</code>
 * is thrown when there is an error trying to register a layer.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class CantRegisterLayerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REGISTER LAYER EXCEPTION";

    public CantRegisterLayerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterLayerException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterLayerException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
