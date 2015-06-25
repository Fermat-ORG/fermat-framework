package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class NetworkServiceNotSupportedException extends CommunicationException {

    
    public static final String DEFAULT_MESSAGE = "NETWORK SERVICE IS NOT REGISTERED";

    public NetworkServiceNotSupportedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public NetworkServiceNotSupportedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public NetworkServiceNotSupportedException(final String message) {
        this(message, null);
    }

    public NetworkServiceNotSupportedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public NetworkServiceNotSupportedException() {
        this(DEFAULT_MESSAGE);
    }

}
