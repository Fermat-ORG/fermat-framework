package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantGetListClauseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LIST CUSTOMER BROKER PURCHASE CLAUSES EXCEPTION";

    public CantGetListClauseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetListClauseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetListClauseException(final String message) {
        this(message, null);
    }

    public CantGetListClauseException() {
        this(DEFAULT_MESSAGE);
    }
}
