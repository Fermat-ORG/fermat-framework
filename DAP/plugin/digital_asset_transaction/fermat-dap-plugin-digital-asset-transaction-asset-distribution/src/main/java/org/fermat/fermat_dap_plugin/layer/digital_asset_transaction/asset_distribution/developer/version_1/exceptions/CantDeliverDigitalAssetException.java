package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/09/15.
 */
public class CantDeliverDigitalAssetException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Delivering Digital Asset to asset wallet.";

    public CantDeliverDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantDeliverDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
