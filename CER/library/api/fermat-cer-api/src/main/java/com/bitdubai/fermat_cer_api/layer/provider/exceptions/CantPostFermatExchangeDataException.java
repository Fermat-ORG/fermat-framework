package com.bitdubai.fermat_cer_api.layer.provider.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 12/26/2015.
 */
public class CantPostFermatExchangeDataException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Cannot post Fermat Exchange Data";

    public CantPostFermatExchangeDataException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPostFermatExchangeDataException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantPostFermatExchangeDataException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantPostFermatExchangeDataException(final String message) {
        this(message, null, null, null);
    }

    public CantPostFermatExchangeDataException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantPostFermatExchangeDataException() {
        this(DEFAULT_MESSAGE);
    }

}
