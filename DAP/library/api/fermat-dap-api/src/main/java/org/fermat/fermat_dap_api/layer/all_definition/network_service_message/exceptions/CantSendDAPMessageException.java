package org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions;

/**
 * Created by franklin on 15/10/15.
 */
public class CantSendDAPMessageException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE";


    public CantSendDAPMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendDAPMessageException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendDAPMessageException(final String message) {
        this(message, null);
    }

    public CantSendDAPMessageException(final String message, final Exception cause, final String context) {
        this(message, cause, context, null);
    }

    public CantSendDAPMessageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendDAPMessageException() {
        this(DEFAULT_MESSAGE);
    }
}
