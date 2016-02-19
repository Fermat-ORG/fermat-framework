package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.exceptions.RelationshipNotFoundException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/02/2016.
 */
public class RelationshipNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "RELATIONSHIP NOT FOUND EXCEPTION";

    public RelationshipNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RelationshipNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public RelationshipNotFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
