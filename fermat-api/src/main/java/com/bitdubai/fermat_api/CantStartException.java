package com.bitdubai.fermat_api;

/**
 * The exception <code>com.bitdubai.fermat_api.CantStartException</code>
 * is thrown when there is an error trying to Start something.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/05/2016.
 */
public class CantStartException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START EXCEPTION";

    public CantStartException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
