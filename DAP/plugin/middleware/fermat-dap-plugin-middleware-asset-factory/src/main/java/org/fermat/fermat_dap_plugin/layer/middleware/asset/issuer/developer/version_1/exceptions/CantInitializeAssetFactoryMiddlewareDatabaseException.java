package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 15/09/15.
 */
public class CantInitializeAssetFactoryMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE ASSET FACTORY MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeAssetFactoryMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetFactoryMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetFactoryMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetFactoryMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
