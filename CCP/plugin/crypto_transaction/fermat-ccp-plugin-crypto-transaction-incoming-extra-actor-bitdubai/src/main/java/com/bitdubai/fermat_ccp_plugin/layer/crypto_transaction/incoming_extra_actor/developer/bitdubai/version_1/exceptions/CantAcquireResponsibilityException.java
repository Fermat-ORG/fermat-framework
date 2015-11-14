package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;

/**
 * Created by jorgegonzalez on 2015.07.07..
 */
public class CantAcquireResponsibilityException extends DatabaseSystemException {

    public static final String DEFAULT_MESSAGE = "CAN'T ACQUIRE RESPONSABILITY";

    public CantAcquireResponsibilityException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAcquireResponsibilityException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAcquireResponsibilityException(final String message) {
        this(message, null);
    }

    public CantAcquireResponsibilityException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantAcquireResponsibilityException() {
        this(DEFAULT_MESSAGE);
    }
}
