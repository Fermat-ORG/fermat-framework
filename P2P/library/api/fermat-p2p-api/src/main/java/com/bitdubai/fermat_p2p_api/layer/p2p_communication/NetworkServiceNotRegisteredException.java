package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class NetworkServiceNotRegisteredException extends CommunicationException {

    public static final String DEFAULT_MESSAGE = "NETWORK SERVICE IS NOT REGISTERED";

    public NetworkServiceNotRegisteredException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}
