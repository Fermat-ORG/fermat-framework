package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;

/**
 * The Class <code>IncomingIntraUserTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IncomingIntraUserTransactionDatabaseConstants {

    /**
     * Incoming Intra User database name.
     */
    static final String INCOMING_INTRA_USER_DATABASE = "incoming_intra_user_database";

    /**
     * Incoming Intra User Registry database table definition.
     */
    static final String INCOMING_INTRA_USER_REGISTRY_TABLE_NAME = "incoming_intra_user_registry";

    static final String INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME                 = "id";
    static final String INCOMING_INTRA_USER_REGISTRY_TRANSACTION_HASH_COLUMN_NAME   = "transaction_hash";
    static final String INCOMING_INTRA_USER_REGISTRY_ADDRESS_TO_COLUMN_NAME         = "address_to";
    static final String INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME    = "crypto_currency";
    static final String INCOMING_INTRA_USER_REGISTRY_CRYPTO_AMOUNT_COLUMN_NAME      = "crypto_amount";
    static final String INCOMING_INTRA_USER_REGISTRY_ADDRESS_FROM_COLUMN_NAME       = "address_from";
    static final String INCOMING_INTRA_USER_REGISTRY_CRYPTO_STATUS_COLUMN_NAME      = "crypto_status";
    static final String INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME             = "action";
    static final String INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME    = "protocol_status";
    static final String INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME = "transaction_status";
    static final String INCOMING_INTRA_USER_REGISTRY_TIMESTAMP_COLUMN_NAME          = "timestamp";
    static final String INCOMING_INTRA_USER_REGISTRY_NETWORK_TYPE                   = "BlockchainNetworkType";



    static final String INCOMING_INTRA_USER_REGISTRY_FIRST_KEY_COLUMN = "id";

    /**
     * Incoming Intra User Events Recorded database table definition.
     */
    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME = "incoming_intra_user_events_recorded";

    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME        = "id";
    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_EVENT_COLUMN_NAME     = "event";
    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME    = "source";
    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME    = "status";
    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String INCOMING_INTRA_USER_EVENTS_RECORDED_FIRST_KEY_COLUMN = "id";

    /**
     * Incoming Intra User Crypto Metadata database table definition.
     */
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME = "incoming_intra_user_crypto_metadata";

    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME                                 = "id";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_REQUEST_FLAG_COLUMN_NAME               = "payment_request_flag";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_REQUEST_ID_COLUMN_NAME                         = "request_id";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME                  = "sender_public_key";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME             = "destination_public_key";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME = "associated_crypto_transaction_hash";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME                = "payment_description";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_ACTION_COLUMN_NAME                             = "action";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME                    = "protocol_status";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME                 = "transaction_status";
    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_TIMESTAMP_COLUMN_NAME                          = "timestamp";

    static final String INCOMING_INTRA_USER_CRYPTO_METADATA_FIRST_KEY_COLUMN = "associated_crypto_transaction_hash";
}

