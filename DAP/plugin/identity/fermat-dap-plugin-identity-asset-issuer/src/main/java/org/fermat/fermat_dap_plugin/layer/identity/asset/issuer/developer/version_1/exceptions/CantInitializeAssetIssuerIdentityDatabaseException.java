package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantInitializeAssetIssuerIdentityDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE INTRA USER IDENTITY DATABASE EXCEPTION";

    public CantInitializeAssetIssuerIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetIssuerIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetIssuerIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetIssuerIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
