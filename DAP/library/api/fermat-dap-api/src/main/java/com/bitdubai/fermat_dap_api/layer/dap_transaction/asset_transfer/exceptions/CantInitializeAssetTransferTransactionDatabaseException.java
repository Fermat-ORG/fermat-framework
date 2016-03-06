package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/09/15.
 */
public class CantInitializeAssetTransferTransactionDatabaseException extends DAPException{
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE ASSET DISTRIBUTION DIGITAL ASSET TRANSACTION DATABASE EXCEPTION";

    public CantInitializeAssetTransferTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetTransferTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetTransferTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetTransferTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
