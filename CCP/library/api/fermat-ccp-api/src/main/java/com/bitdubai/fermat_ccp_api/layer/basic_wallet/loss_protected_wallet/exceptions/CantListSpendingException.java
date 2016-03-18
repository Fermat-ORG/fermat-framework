package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 14/03/16.
 */
public class CantListSpendingException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST SPENDING BTC";

    public CantListSpendingException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListSpendingException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListSpendingException(final String message) {
        this(message, null);
    }

    public CantListSpendingException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantListSpendingException() {
        this(DEFAULT_MESSAGE);
    }
}