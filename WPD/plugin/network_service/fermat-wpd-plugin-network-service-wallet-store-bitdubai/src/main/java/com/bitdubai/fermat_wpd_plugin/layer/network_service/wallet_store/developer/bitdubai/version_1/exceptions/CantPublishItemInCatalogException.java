package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/27/15.
 */
public class CantPublishItemInCatalogException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Error trying to publish item in catalog.";
    public CantPublishItemInCatalogException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
