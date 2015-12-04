package com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CyclicalRelationshipFoundException</code>
 * is thrown when a cyclical relationship is found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class CyclicalRelationshipFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CYCLICAL RELATIONSHIP FOUND EXCEPTION";

    public CyclicalRelationshipFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CyclicalRelationshipFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CyclicalRelationshipFoundException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
