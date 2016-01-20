package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by frank on 11/18/15.
 */
public class CantReadDigitalAssetFileException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error reading a Digital Asset file.";

    public CantReadDigitalAssetFileException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
