package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.exceptions.CloudCommunicationException;

/**
 * Created by jorgegonzalez on 2015.06.25..
 */
public class ClientInitializationException extends CloudCommunicationException {

    public static final String DEFAULT_MESSAGE = "THE CLOUD COMMUNICATION CLIENT FAILED TO INITIALIZE";

    public ClientInitializationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
