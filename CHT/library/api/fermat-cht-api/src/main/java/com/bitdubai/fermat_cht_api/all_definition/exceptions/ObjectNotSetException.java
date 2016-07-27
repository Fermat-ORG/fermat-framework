package com.bitdubai.fermat_cht_api.all_definition.exceptions;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public class ObjectNotSetException extends CHTException {

    static final String DEFAULT_MESSAGE = "The required object is not set.";

    public ObjectNotSetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ObjectNotSetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
