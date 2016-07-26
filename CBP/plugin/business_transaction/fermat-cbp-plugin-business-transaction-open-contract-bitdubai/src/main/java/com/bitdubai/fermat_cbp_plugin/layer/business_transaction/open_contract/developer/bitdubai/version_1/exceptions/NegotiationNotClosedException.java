package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class NegotiationNotClosedException extends CBPException {

    private static final String DEFAULT_MESSAGE = "The Negotiation is not closed";

    public NegotiationNotClosedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public NegotiationNotClosedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public NegotiationNotClosedException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
