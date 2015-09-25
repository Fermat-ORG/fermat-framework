package com.bitdubai.fermat_api.layer.ccp_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/21/15.
 */
public class CantGetSkinException extends FermatException {
    public CantGetSkinException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
