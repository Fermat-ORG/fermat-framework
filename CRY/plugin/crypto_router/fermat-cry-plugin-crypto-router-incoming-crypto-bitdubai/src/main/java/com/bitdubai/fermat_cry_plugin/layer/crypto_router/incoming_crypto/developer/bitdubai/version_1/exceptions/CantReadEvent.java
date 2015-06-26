package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by arturo on 05/05/15.
 */
public class CantReadEvent extends FermatException {

    public CantReadEvent(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
