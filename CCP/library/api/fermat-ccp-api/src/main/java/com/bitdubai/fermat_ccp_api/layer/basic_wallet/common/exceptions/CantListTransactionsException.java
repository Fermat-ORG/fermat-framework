package com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 2015.06.17..
 */
public class CantListTransactionsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST TRANSACTIONS";

    public CantListTransactionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListTransactionsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListTransactionsException(final String message) {
        this(message, null);
    }

    public CantListTransactionsException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantListTransactionsException() {
        this(DEFAULT_MESSAGE);
    }
}