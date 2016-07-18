package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class UnsupportedCurrencyPairException extends FermatException {

    public static final String DEFAULT_MESSAGE = "This CurrencyPair is unsupported by this provider";

    public UnsupportedCurrencyPairException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public UnsupportedCurrencyPairException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public UnsupportedCurrencyPairException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public UnsupportedCurrencyPairException(final String message) {
        this(message, null, null, null);
    }

    public UnsupportedCurrencyPairException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public UnsupportedCurrencyPairException() {
        this(DEFAULT_MESSAGE);
    }

}
