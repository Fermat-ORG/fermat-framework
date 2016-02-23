package com.bitdubai.fermat_cht_plugin.layer.network_service.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 22/02/16.
 */

public class RecordsNotFoundException extends CHTException {

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
        super(message, cause);
    }

    public RecordsNotFoundException(String message) {
        super(message);
    }

    public RecordsNotFoundException() {
    }

    public RecordsNotFoundException(Exception exception) {
        super(exception);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
