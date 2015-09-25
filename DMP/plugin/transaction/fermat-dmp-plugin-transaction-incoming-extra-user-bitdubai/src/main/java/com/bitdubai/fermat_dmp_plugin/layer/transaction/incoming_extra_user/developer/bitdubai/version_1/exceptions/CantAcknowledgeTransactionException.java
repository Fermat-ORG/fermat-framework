package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
public class CantAcknowledgeTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T ACKNOWLEDGE THE TRANSACTION";

    public CantAcknowledgeTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAcknowledgeTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAcknowledgeTransactionException(final String message) {
        this(message, null);
    }

    public CantAcknowledgeTransactionException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantAcknowledgeTransactionException() {
        this(DEFAULT_MESSAGE);
    }
}
