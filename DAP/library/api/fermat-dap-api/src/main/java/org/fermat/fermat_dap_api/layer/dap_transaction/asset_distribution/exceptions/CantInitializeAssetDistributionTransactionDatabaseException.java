package org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/09/15.
 */
public class CantInitializeAssetDistributionTransactionDatabaseException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE ASSET DISTRIBUTION DIGITAL ASSET TRANSACTION DATABASE EXCEPTION";

    public CantInitializeAssetDistributionTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetDistributionTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetDistributionTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetDistributionTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
