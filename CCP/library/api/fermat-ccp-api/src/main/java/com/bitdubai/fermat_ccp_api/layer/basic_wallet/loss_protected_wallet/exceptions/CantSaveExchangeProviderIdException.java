package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 31/03/16.
 */
public class CantSaveExchangeProviderIdException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T SET EXCHANGE PROVIDER ID";

    public CantSaveExchangeProviderIdException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSaveExchangeProviderIdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSaveExchangeProviderIdException(final String message) {
        this(message, null);
    }

    public CantSaveExchangeProviderIdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSaveExchangeProviderIdException() {
        this(DEFAULT_MESSAGE);
    }
}