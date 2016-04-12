package org.fermat.fermat_dap_plugin.layer.identity.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantGetAssetIssuerIdentityPrivateKeyException extends FermatException {


    public CantGetAssetIssuerIdentityPrivateKeyException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}
