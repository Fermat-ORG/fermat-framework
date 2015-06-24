package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class NetworkServiceNotSupportedException extends CommunicationException {


    private static final String DEFAULT_MESSAGE = "NETWORK SERVICE IS NOT REGISTERED: ";

    public NetworkServiceNotSupportedException(final String message, final Exception cause){
        super(message, cause);
    }

    public NetworkServiceNotSupportedException(final String message){
        this(message, null);
    }

    public NetworkServiceNotSupportedException(){
        this("");
    }

}
