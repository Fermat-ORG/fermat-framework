package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;


/**
 * Created by jorgegonzalez on 2015.06.25..
 */
public class NIOSocketException extends CloudCommunicationException {

    public static final String DEFAULT_MESSAGE = "THERE WAS A PROBLEM INITIALIZING THE NIO SOCKET CHANNEL";

    public NIOSocketException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public NIOSocketException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public NIOSocketException(final String message) {
        this(message, null);
    }

    public NIOSocketException() {
        this(DEFAULT_MESSAGE);
    }

    public NIOSocketException(Exception exception){
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }
}
