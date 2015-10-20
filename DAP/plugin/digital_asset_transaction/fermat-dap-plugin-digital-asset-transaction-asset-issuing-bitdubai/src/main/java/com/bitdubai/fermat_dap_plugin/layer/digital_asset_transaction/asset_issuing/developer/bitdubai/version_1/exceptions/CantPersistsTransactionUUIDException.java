package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/10/15.
 */
public class CantPersistsTransactionUUIDException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error persisting a Digital Asset genesis transaction in Asset Issuing database.";

    public CantPersistsTransactionUUIDException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
