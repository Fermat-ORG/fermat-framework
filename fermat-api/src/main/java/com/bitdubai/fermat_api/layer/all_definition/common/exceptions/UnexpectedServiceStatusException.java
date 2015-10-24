package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.UnexpectedServiceStatusException</code>
 * is thrown when the status of a specific service is not the expected.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class UnexpectedServiceStatusException extends FermatException {

    private static final String DEFAULT_MESSAGE = "SERVICE STATUS NOT EXPECTED EXCEPTION";

    public UnexpectedServiceStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnexpectedServiceStatusException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public UnexpectedServiceStatusException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
