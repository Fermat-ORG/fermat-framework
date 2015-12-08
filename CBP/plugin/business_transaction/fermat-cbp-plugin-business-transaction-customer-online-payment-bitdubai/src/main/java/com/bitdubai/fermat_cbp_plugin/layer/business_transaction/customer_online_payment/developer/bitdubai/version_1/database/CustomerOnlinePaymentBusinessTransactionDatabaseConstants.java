package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Manuel Perez - (darkpriestrelative@gmail.com) on 08/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerOnlinePaymentBusinessTransactionDatabaseConstants {
    /**
     * Online Payment database table definition.
     */
    public static final String ONLINE_PAYMENT_TABLE_NAME = "online_payment";

    public static final String ONLINE_PAYMENT_CONTRACT_HASH_COLUMN_NAME = "contract_hash";
    public static final String ONLINE_PAYMENT_CUSTOMER_PUBLIC_KEY_COLUMN_NAME = "customer_public_key";
    public static final String ONLINE_PAYMENT_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    public static final String ONLINE_PAYMENT_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String ONLINE_PAYMENT_TRANSACTION_HASH_COLUMN_NAME = "transaction_hash";
    public static final String ONLINE_PAYMENT_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    public static final String ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME = "contract_transaction_status";

    public static final String ONLINE_PAYMENT_FIRST_KEY_COLUMN = "contract_hash";

}
