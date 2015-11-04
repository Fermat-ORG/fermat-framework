package com.bitdubai.fermat_p2p_api.layer.p2p_communication.wifi.exceptions;


import com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationException;


public class WifiCommunicationException extends CommunicationException{


    private static final long serialVersionUID = 3147635757820692887L;

    public static final String DEFAULT_MESSAGE = "THE WIFI COMMUNICATION CHANNEL HAS THROWN AN EXCEPTION";

    public WifiCommunicationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }



}
