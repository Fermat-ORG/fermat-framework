package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 16/11/15.
 */
public class CantInitializeChatIdentityDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CHAT IDENTITY  DATABASE EXCEPTION";

    public CantInitializeChatIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeChatIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeChatIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeChatIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
