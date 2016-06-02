package org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/09/15.
 */
public class CantDeliverDigitalAssetToAssetWalletException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Delivering Digital Asset to asset wallet.";

    public CantDeliverDigitalAssetToAssetWalletException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantDeliverDigitalAssetToAssetWalletException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
