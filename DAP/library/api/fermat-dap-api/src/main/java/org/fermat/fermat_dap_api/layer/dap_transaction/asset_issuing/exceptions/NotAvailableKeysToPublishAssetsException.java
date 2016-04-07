package org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/29/16.
 */
public class NotAvailableKeysToPublishAssetsException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Not enough keys available to generate assets.";

    public NotAvailableKeysToPublishAssetsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
