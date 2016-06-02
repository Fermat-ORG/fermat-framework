package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantInitializeAssetRedeemPointIdentityDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE INTRA USER IDENTITY DATABASE EXCEPTION";

    public CantInitializeAssetRedeemPointIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetRedeemPointIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetRedeemPointIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetRedeemPointIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
