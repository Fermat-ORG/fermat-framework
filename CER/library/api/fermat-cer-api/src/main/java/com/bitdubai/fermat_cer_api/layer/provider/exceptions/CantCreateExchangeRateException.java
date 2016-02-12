package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CantCreateExchangeRateException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cannot create Exchange Rate";

    public CantCreateExchangeRateException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateExchangeRateException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateExchangeRateException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantCreateExchangeRateException(final String message) {
        this(message, null, null, null);
    }

    public CantCreateExchangeRateException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateExchangeRateException() {
        this(DEFAULT_MESSAGE);
    }

}
