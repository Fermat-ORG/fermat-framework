package com.bitdubai.fermat_tky_api.layer.song_wallet.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/03/16.
 */
public class CantSynchronizeWithExternalAPIException extends TKYException {

    public static final String DEFAULT_MESSAGE = "CANNOT GET SYNCHRONIZE WITH EXTERNAL TOKENLY API";

    public CantSynchronizeWithExternalAPIException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSynchronizeWithExternalAPIException(
            Exception cause,
            String context,
            String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantSynchronizeWithExternalAPIException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSynchronizeWithExternalAPIException(final String message) {
        this(message, null);
    }

    public CantSynchronizeWithExternalAPIException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSynchronizeWithExternalAPIException() {
        this(DEFAULT_MESSAGE);
    }
}


