package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CantInitializeProviderInfoException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant save the provider's info to the database";

    public CantInitializeProviderInfoException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeProviderInfoException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeProviderInfoException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantInitializeProviderInfoException(final String message) {
        this(message, null, null, null);
    }

    public CantInitializeProviderInfoException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeProviderInfoException() {
        this(DEFAULT_MESSAGE);
    }

}
