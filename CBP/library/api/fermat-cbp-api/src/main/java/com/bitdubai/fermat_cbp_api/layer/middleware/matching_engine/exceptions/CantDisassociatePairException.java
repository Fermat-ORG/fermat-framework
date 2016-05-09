package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException</code>
 * is thrown when there is an error trying to disassociate an earnings pair.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public class CantDisassociatePairException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T DISASSOCIATE PAIR EXCEPTION";

    public CantDisassociatePairException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDisassociatePairException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
