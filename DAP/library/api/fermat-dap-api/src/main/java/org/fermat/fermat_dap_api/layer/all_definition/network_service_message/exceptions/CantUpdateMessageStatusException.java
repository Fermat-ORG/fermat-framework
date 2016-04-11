package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/02/16.
 */
public class CantUpdateMessageStatusException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE MESSAGE STATUS";

    //CONSTRUCTORS

    public CantUpdateMessageStatusException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantUpdateMessageStatusException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantUpdateMessageStatusException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantUpdateMessageStatusException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantUpdateMessageStatusException(Exception exception) {
        super(exception);
    }

    public CantUpdateMessageStatusException() {
        this(DEFAULT_MESSAGE);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
