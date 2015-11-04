package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.22..
 * Modified by lnacosta (laion.cj91@gmail.com) on 16/10/2015.
 */
public class CantExecuteQueryException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T EXECUTE QUERY EXCEPTION.";

    public CantExecuteQueryException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecuteQueryException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteQueryException(Exception cause) {
        super(DEFAULT_MESSAGE, cause, null, null);
    }
}
