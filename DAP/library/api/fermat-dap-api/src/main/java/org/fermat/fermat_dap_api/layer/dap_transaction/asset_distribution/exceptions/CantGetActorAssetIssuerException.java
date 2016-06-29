package org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/10/15.
 */
public class CantGetActorAssetIssuerException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error getting Actor Asset Issuer.";

    public CantGetActorAssetIssuerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
