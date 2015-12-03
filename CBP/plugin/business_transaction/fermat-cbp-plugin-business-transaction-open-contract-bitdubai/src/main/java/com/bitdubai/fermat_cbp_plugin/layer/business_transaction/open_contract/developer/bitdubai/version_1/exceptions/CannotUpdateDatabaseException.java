package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.open_contract.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 30/11/15.
 */
public class CannotUpdateDatabaseException extends CBPException {

    private static final String DEFAULT_MESSAGE = "Cannot update the database";

    public CannotUpdateDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CannotUpdateDatabaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CannotUpdateDatabaseException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
