package com.bitdubai.fermat_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 6/22/16.
 */
public class UnexpectedResultReturnedFromDatabaseException extends FermatException {

    private static final String DEFAULT_MESSAGE = "A not expected result was returned by the executing query.";
    private static final String DEFAULT_REASON = "Database inconsistency.";

    public UnexpectedResultReturnedFromDatabaseException() {
        super(DEFAULT_MESSAGE, null, null, DEFAULT_REASON);
    }

    public UnexpectedResultReturnedFromDatabaseException(String context) {
        super(DEFAULT_MESSAGE, null, context, DEFAULT_REASON);
    }

    public UnexpectedResultReturnedFromDatabaseException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, DEFAULT_REASON);
    }

    public UnexpectedResultReturnedFromDatabaseException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public UnexpectedResultReturnedFromDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
