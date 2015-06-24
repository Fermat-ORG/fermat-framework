package com.bitdubai.fermat_api.layer;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class PIPException extends FermatException {

    private static final String DEFAULT_MESSAGE = "THE PIP LAYER HAS TRIGGERED AN EXCEPTION: ";

    public PIPException(final String message, final Exception cause, final String context, final String possibleReason){
        super(DEFAULT_MESSAGE + message, cause, context, possibleReason);
    }

    public PIPException(final String message, final Exception cause){
        this(DEFAULT_MESSAGE + message, cause, "", "");
    }

    public PIPException(final String message){
        this(message, null);
    }

    public PIPException(){
        this("");
    }
}
