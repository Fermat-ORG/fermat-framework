package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/7/2015.
 */
public class CantGetProviderInfoException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cant fetch the provider's info from the database";

    public CantGetProviderInfoException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetProviderInfoException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetProviderInfoException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetProviderInfoException(final String message) {
        this(message, null, null, null);
    }

    public CantGetProviderInfoException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetProviderInfoException() {
        this(DEFAULT_MESSAGE);
    }

}
