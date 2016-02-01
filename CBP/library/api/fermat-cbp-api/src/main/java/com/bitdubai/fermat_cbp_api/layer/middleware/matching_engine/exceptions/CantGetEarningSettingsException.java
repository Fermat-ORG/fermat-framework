package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantGetEarningSettingsException</code>
 * is thrown when there is an error trying to get the earnings settings for a specific wallet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/02/2016.
 */
public class CantGetEarningSettingsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET EARNINGS SETTINGS EXCEPTION";

    public CantGetEarningSettingsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetEarningSettingsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
