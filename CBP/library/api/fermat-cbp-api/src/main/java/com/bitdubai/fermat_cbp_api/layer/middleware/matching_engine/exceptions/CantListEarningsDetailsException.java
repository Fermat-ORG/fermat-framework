package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsDetailsException</code>
 * is thrown when there is an error trying to list earnings details.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/02/2016.
 */
public class CantListEarningsDetailsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST EARNINGS DETAILS EXCEPTION";

    public CantListEarningsDetailsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListEarningsDetailsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
