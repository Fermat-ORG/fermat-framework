package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/23/15.
 */
public class CantPublishSkinInCatalogException extends FermatException {
    public CantPublishSkinInCatalogException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
