package com.bitdubai.fermat_cer_api.layer.search.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/26/2015.
 */
public class CantGetProviderException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cannot get Exchange Rate Povider";

    public CantGetProviderException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetProviderException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetProviderException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantGetProviderException(final String message) {
        this(message, null, null, null);
    }

    public CantGetProviderException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetProviderException() {
        this(DEFAULT_MESSAGE);
    }

}
