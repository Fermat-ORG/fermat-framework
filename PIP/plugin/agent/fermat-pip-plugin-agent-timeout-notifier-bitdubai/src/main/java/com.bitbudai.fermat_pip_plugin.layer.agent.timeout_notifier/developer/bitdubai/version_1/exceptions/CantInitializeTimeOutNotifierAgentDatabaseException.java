package com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.version_1.exceptions.CantInitializeTimeOutNotifierAgentDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Acosta Rodrigo - (acosta_rodrigo@hotmail.com) on 28/03/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeTimeOutNotifierAgentDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE TIMEOUT NOTIFIER AGENT DATABASE EXCEPTION";

    public CantInitializeTimeOutNotifierAgentDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeTimeOutNotifierAgentDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeTimeOutNotifierAgentDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeTimeOutNotifierAgentDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
