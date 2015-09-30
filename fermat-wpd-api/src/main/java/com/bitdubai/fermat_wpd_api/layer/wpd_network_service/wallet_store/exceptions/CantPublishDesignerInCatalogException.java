package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/27/15.
 */
public class CantPublishDesignerInCatalogException extends FermatException {
    public static final String DEFAULT_MESSAGE = "error trying to publish Designer into Wallet Store catalog.";
    public CantPublishDesignerInCatalogException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
