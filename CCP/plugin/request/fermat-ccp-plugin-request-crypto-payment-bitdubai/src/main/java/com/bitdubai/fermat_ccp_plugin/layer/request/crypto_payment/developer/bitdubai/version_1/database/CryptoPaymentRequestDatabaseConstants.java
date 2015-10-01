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
    static final String CRYPTO_ADDRESS_REQUEST_TABLE_NAME                       = "crypto_address_request";

    static final String CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME           = "request_id"            ;
    static final String CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME    = "wallet_public_key"     ;
    static final String CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME  = "identity_public_key"   ;
    static final String CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME     = "actor_public_key"      ;
    static final String CRYPTO_ADDRESS_REQUEST_DESCRIPTION_COLUMN_NAME          = "description"           ;
    static final String CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME       = "crypto_address"        ;
    static final String CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME      = "crypto_currency"       ;
    static final String CRYPTO_ADDRESS_REQUEST_AMOUNT_COLUMN_NAME               = "amount"                ;
    static final String CRYPTO_ADDRESS_REQUEST_START_TIME_STAMP_COLUMN_NAME     = "start_time_stamp"      ;
    static final String CRYPTO_ADDRESS_REQUEST_END_TIME_STAMP_COLUMN_NAME       = "end_time_stamp"        ;
    static final String CRYPTO_ADDRESS_REQUEST_TYPE_COLUMN_NAME                 = "type"                  ;
    static final String CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME                = "state"                 ;

    static final String CRYPTO_ADDRESS_REQUEST_FIRST_KEY_COLUMN                 = "request_id"            ;

}