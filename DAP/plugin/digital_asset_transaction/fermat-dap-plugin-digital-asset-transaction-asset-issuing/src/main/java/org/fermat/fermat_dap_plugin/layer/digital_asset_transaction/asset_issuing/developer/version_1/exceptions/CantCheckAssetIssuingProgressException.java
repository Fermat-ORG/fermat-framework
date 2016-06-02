package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/09/15.
 */
public class CantCheckAssetIssuingProgressException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error checking the Assets Issuing progress.";

    public CantCheckAssetIssuingProgressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCheckAssetIssuingProgressException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
