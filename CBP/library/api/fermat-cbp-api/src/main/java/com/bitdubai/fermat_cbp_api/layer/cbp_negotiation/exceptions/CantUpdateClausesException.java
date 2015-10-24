package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantUpdateClausesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE PURCHASE CLAUSES EXCEPTION";

    public CantUpdateClausesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateClausesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateClausesException(final String message) {
        this(message, null);
    }

    public CantUpdateClausesException() {
        this(DEFAULT_MESSAGE);
    }
}