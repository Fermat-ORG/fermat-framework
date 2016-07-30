package com.bitdubai.fermat_cht_api.all_definition.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/01/16.
 */
public class UnexpectedResultReturnedFromDatabaseException extends CHTException {

    public static final String DEFAULT_MESSAGE = "The Database returns an unexpected result.";

    public UnexpectedResultReturnedFromDatabaseException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public UnexpectedResultReturnedFromDatabaseException(String message, String possibleReason) {
        super(DEFAULT_MESSAGE, null, message, possibleReason);
    }

    public UnexpectedResultReturnedFromDatabaseException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
