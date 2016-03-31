package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/28/16.
 */
public class InconsistentResultObtainedInDatabaseQueryException extends FermatException {

    public static final String DEFAULT_MESSAGE = "We have found an unexpected result which may result in a database inconsistent data problem.";

    public InconsistentResultObtainedInDatabaseQueryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InconsistentResultObtainedInDatabaseQueryException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
