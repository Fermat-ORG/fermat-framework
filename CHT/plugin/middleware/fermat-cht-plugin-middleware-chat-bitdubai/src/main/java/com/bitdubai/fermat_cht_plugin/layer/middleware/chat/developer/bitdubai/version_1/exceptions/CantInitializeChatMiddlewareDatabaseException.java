package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cht_plugin.layer.middleware.chat.version_1.exceptions.CantInitializeChatMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Miguel Payarez - (miguel_payarez@hotmail.com) on 05/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeChatMiddlewareDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CHAT MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeChatMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeChatMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeChatMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeChatMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}