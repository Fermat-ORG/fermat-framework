package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 15/09/15.
 */
public class MissingAssetDataException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to insert data in table. The referenced Asset Facory public key does not exists.";

    public MissingAssetDataException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
