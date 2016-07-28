package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_offline_merchandise.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE BROKER SUBMIT OFFLINE MERCHANDISE BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBrokerSubmitOfflineMerchandiseBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

