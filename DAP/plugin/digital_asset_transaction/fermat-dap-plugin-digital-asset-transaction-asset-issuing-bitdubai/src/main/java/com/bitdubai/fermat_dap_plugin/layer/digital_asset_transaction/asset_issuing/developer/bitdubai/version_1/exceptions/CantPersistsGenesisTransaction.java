package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/09/15.
 */
public class CantPersistsGenesisTransaction extends DAPException{
    public static final String DEFAULT_MESSAGE = "There was an error persisting a Digital Asset genesis address in Asset Issuing database.";

    public CantPersistsGenesisTransaction(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
