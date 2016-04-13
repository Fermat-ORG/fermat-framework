package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class CantGetDAPMessagesException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "CAN'T GET MESSAGE LIST";

    //CONSTRUCTORS

    public CantGetDAPMessagesException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetDAPMessagesException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantGetDAPMessagesException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetDAPMessagesException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantGetDAPMessagesException(Exception exception) {
        super(exception);
    }

    public CantGetDAPMessagesException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
