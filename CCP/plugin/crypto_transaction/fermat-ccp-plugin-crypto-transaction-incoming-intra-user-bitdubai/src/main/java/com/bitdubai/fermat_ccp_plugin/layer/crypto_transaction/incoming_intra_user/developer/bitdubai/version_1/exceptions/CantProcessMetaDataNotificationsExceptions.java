package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 10/11/15.
 */
public class CantProcessMetaDataNotificationsExceptions extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T PROCESS INCOMING METADATA EXCEPTION";

    public CantProcessMetaDataNotificationsExceptions(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantProcessMetaDataNotificationsExceptions(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantProcessMetaDataNotificationsExceptions(final String message) {
        this(message, null);
    }

    public CantProcessMetaDataNotificationsExceptions() {
        this(DEFAULT_MESSAGE);
    }
}
