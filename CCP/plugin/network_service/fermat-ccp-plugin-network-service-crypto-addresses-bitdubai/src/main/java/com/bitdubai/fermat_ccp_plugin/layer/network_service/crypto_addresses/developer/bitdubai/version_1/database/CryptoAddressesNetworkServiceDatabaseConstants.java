package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.CryptoAddressesNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceDatabaseConstants {

    /**
     * Crypto Address Request database table definition.
     */
    static final String CRYPTO_ADDRESS_REQUEST_TABLE_NAME = "crypto_address_request";

    static final String CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME = "request_id";
    static final String CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    static final String CRYPTO_ADDRESS_REQUEST_ACTOR_TYPE_COLUMN_NAME = "actor_type";
    static final String CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_REQUESTING_COLUMN_NAME = "actor_public_key_requesting";
    static final String CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_TO_SEND_COLUMN_NAME = "crypto_address_to_send";
    static final String CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_ACCEPTING_COLUMN_NAME = "actor_public_key_accepting";
    static final String CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_TO_RECEIVE_COLUMN_NAME = "crypto_address_to_receive";
    static final String CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME = "state";
    static final String CRYPTO_ADDRESS_REQUEST_RESULT_COLUMN_NAME = "result";

    static final String CRYPTO_ADDRESS_REQUEST_FIRST_KEY_COLUMN = "request_id";

}