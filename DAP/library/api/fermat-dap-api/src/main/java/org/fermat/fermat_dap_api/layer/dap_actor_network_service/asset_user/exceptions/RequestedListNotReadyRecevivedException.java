package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

/**
 * Created by root on 06/10/15.
 */
public class RequestedListNotReadyRecevivedException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T RETURN THE REQUESTED LIST ACTOR ASSET USER REGISTERED, IS NO RECEIVE YET";


    public RequestedListNotReadyRecevivedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public RequestedListNotReadyRecevivedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public RequestedListNotReadyRecevivedException(final String message) {
        this(message, null);
    }

    public RequestedListNotReadyRecevivedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public RequestedListNotReadyRecevivedException() {
        this(DEFAULT_MESSAGE);
    }


}
