package com.bitdubai.fermat_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 12/03/16.
 */
public class RecordsNotFoundException extends FermatException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "Couldn't find any record in database to update or delete.";

    //CONSTRUCTORS

    public RecordsNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RecordsNotFoundException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public RecordsNotFoundException(String message, Exception cause) {
        super(message, cause, "", "");
    }

    public RecordsNotFoundException(String message) {
        super(message, null, "", "");
    }

    public RecordsNotFoundException() {
        super(DEFAULT_MESSAGE, null, "", null);
    }

    public RecordsNotFoundException(Exception exception) {
        super(DEFAULT_MESSAGE, exception, "", "");
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
