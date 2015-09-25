package com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 14/08/15.
 */
public class CantLoadLoginsFileException extends FermatException {

    public static final String DEFAULT_MESSAGE = "ERROR READ REQUESTED LOGINS XML: ";

    public CantLoadLoginsFileException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
