package com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantAddNewClausesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T ADD NEW PURCHASE CLAUSES EXCEPTION";

    public CantAddNewClausesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAddNewClausesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAddNewClausesException(final String message) {
        this(message, null);
    }

    public CantAddNewClausesException() {
        this(DEFAULT_MESSAGE);
    }
}