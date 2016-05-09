package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/03/2016.
 */
public class EarningsSettingsNotRegisteredException extends FermatException {

    private static final String DEFAULT_MESSAGE = "EARNINGS SETTINGS NOT REGISTERED EXCEPTION";

    public EarningsSettingsNotRegisteredException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public EarningsSettingsNotRegisteredException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }


    public EarningsSettingsNotRegisteredException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
