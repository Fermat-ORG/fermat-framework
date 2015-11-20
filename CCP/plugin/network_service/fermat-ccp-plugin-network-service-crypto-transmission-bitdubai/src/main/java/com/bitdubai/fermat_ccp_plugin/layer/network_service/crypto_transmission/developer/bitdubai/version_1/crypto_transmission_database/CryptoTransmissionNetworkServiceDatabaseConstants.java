package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.cryptotransmission.developer.bitdubai.version_1.database.CryptoTransmissionNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 05/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoTransmissionNetworkServiceDatabaseConstants {

    public static final String DATABASE_NAME = "crypto_transmission_database";

    /**
     * CRYPTO TRANSMISSION METADATA database table definition.
     */
    public static final String CRYPTO_TRANSMISSION_METADATA_TABLE_NAME = "crypto_transmission_metadata";

    public static final String CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME = "transmission_id";
    public static final String CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME = "request_id";
    public static final String CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME = "crypto_currency";
    public static final String CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    public static final String CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLICK_KEY_COLUMN_NAME = "sender_publick_key";
    public static final String CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME = "destination_public_key";
    public static final String CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME = "associated_crypto_transaction_hash";
    public static final String CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME = "payment_description";
    public static final String CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME = "status";
    public static final String CRYPTO_TRANSMISSION_METADATA_TYPE_COLUMN_NAME = "type";
    public static final String CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME = "pending_flag";
    public static final String CRYPTO_TRANSMISSION_METADATA_TIMESTAMP_COLUMN_NAME = "timestamp";

   public static final String CRYPTO_TRANSMISSION_METADATA_FIRST_KEY_COLUMN = "transmission_id";

    /**
     * COMPONENT VERSIONS DETAILS database table definition.
     */
    public static final String COMPONENT_VERSIONS_DETAILS_TABLE_NAME = "component_versions_details";

    public static final String COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME = "id";
    public static final String COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME = "ipk";
    public static final String COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME = "last_connection";

   public static final String COMPONENT_VERSIONS_DETAILS_FIRST_KEY_COLUMN = "id";


}