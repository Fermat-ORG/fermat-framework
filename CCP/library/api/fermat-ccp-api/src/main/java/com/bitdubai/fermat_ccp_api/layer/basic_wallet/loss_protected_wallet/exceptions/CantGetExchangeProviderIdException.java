package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 31/03/16.
 */
public class CantGetExchangeProviderIdException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET EXCHANGE PROVIDER ID";

    public CantGetExchangeProviderIdException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetExchangeProviderIdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetExchangeProviderIdException(final String message) {
        this(message, null);
    }

    public CantGetExchangeProviderIdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetExchangeProviderIdException() {
        this(DEFAULT_MESSAGE);
    }
}