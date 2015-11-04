package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * The Class <code>package com.bitdubai.fermat_dap_api.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuingtransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeAssetIssuingTransactionDatabaseException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE ASSET ISSUING TRANSACTION DATABASE EXCEPTION";

    public CantInitializeAssetIssuingTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetIssuingTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetIssuingTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetIssuingTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

