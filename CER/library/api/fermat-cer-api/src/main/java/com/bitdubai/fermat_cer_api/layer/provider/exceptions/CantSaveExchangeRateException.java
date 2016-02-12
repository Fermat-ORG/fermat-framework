package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CantSaveExchangeRateException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cannot save Exchange Rate into Database";

    public CantSaveExchangeRateException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveExchangeRateException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSaveExchangeRateException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantSaveExchangeRateException(final String message) {
        this(message, null, null, null);
    }

    public CantSaveExchangeRateException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSaveExchangeRateException() {
        this(DEFAULT_MESSAGE);
    }

}
