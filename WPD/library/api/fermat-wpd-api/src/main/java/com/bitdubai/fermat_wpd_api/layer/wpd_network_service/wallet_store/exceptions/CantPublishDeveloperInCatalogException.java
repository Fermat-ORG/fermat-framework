package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 17/08/15.
 */
public class CantPublishDeveloperInCatalogException  extends FermatException {
    public static final String DEFAULT_MESSAGE = "error trying to publish Developer into Wallet Store catalog.";
    public CantPublishDeveloperInCatalogException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
