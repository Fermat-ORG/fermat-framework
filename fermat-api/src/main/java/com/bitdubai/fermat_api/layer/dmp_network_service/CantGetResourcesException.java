package com.bitdubai.fermat_api.layer.dmp_network_service;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Natalia on 09/03/2015.
 */
public class CantGetResourcesException extends FermatException {


    private static final long serialVersionUID = 4150767203325009811L;

    private static final String DEFAULT_MESSAGE = "CAN'T GET REQUESTED RESOURCES: ";


    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */


    public CantGetResourcesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetResourcesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetResourcesException(final String message) {
        this(message, null);
    }

    public CantGetResourcesException() {
        this(DEFAULT_MESSAGE);
    }

}
