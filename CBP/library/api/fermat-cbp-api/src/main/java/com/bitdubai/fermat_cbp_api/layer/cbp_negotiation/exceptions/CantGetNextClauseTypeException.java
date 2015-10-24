package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantGetNextClauseTypeException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET NEXT CLAUSE EXCEPTION";

    public CantGetNextClauseTypeException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetNextClauseTypeException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetNextClauseTypeException(final String message) {
        this(message, null);
    }

    public CantGetNextClauseTypeException() {
        this(DEFAULT_MESSAGE);
    }
}