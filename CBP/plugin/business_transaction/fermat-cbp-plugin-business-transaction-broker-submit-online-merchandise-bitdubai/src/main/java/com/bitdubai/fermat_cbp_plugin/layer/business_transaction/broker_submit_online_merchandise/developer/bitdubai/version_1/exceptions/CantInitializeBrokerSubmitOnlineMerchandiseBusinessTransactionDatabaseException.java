package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CUSTOMER ONLINE PAYMENT BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBrokerSubmitOnlineMerchandiseBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

