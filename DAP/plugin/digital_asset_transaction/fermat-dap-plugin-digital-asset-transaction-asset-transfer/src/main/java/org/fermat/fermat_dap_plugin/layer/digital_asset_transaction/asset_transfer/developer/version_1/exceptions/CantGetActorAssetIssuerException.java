package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor Mars (marsvicam@gmail.com) on 08/01/16.
 */
public class CantGetActorAssetIssuerException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error getting Actor Asset Issuer.";

    public CantGetActorAssetIssuerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
