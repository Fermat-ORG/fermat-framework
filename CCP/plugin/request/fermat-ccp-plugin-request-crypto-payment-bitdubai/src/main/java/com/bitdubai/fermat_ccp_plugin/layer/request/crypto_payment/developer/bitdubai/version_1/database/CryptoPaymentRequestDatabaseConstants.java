package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.database.CryptoPaymentRequestDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoPaymentRequestDatabaseConstants {

    /**
     * Crypto Address Request database table definition.
     */
    public static final String CRYPTO_PAYMENT_REQUEST_TABLE_NAME                      = "crypto_payment_request";

    public static final String CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME          = "request_id"            ;
    public static final String CRYPTO_PAYMENT_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME   = "wallet_public_key"     ;
    public static final String CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME = "identity_public_key"   ;
    public static final String CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME       = "identity_type"         ;
    public static final String CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME    = "actor_public_key"      ;
    public static final String CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME          = "actor_type"            ;
    public static final String CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME         = "description"           ;
    public static final String CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME      = "crypto_address"        ;
    public static final String CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME     = "crypto_currency"       ;
    public static final String CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME              = "amount"                ;
    public static final String CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME    = "start_time_stamp"      ;
    public static final String CRYPTO_PAYMENT_REQUEST_END_TIME_STAMP_COLUMN_NAME      = "end_time_stamp"        ;
    public static final String CRYPTO_PAYMENT_REQUEST_TYPE_COLUMN_NAME                = "type"                  ;
    public static final String CRYPTO_PAYMENT_REQUEST_STATE_COLUMN_NAME               = "state"                 ;
    public static final String CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME        = "network_type"          ;
    public static final String CRYPTO_PAYMENT_REQUEST_WALLET_REFERENCE_TYPE_COLUMN_NAME = "reference_wallet";
    public static final String CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_TYPE_COLUMN_NAME = "cryptoCurrencyType";

    public static final String CRYPTO_PAYMENT_REQUEST_FIRST_KEY_COLUMN                = "request_id"            ;

}