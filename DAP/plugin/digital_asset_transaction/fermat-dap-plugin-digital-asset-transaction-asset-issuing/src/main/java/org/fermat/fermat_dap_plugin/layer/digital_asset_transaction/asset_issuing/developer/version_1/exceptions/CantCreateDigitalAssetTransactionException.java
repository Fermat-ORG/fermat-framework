package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/09/15.
 */
public class CantCreateDigitalAssetTransactionException extends DAPException {

    public static final String DEFAULT_MESSAGE = "There was an error creating a Digital Asset.";

    public CantCreateDigitalAssetTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCreateDigitalAssetTransactionException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
