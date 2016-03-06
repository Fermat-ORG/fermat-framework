package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException</code>
 * is thrown when we're trying to associate a pair that is already associated.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public class PairAlreadyAssociatedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "PAIR ALREADY ASSOCIATED EXCEPTION";

    public PairAlreadyAssociatedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public PairAlreadyAssociatedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public PairAlreadyAssociatedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
