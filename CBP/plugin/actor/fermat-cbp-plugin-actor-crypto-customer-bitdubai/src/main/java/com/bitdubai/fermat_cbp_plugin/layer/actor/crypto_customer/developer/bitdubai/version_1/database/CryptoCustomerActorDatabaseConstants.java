package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 16/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoCustomerActorDatabaseConstants {

    /**
     * Crypto Customer Actor database table definition.
     */
    static final String CRYPTO_CUSTOMER_ACTOR_TABLE_NAME = "crypto_customer_actor";

    static final String CRYPTO_CUSTOMER_ACTOR_ACTOR_ID_COLUMN_NAME = "actor_id";
    static final String CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_ACTOR_COLUMN_NAME = "public_key_actor";
    static final String CRYPTO_CUSTOMER_ACTOR_PUBLIC_KEY_IDENTITY_COLUMN_NAME = "public_key_identity";
    static final String CRYPTO_CUSTOMER_ACTOR_DESCRIPTION_ACTOR_COLUMN_NAME = "description_actor";

    static final String CRYPTO_CUSTOMER_ACTOR_FIRST_KEY_COLUMN = "actor_id";

    /**
     * Crypto Customer Identity Wallet Relationship database table definition.
     */
    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TABLE_NAME = "crypto_customer_identity_wallet_relationship";

    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME = "relationship_id";
    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_IDENTITY_COLUMN_NAME = "public_key_identity";
    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_PUBLIC_KEY_WALLET_COLUMN_NAME = "public_key_wallet";
    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String CRYPTO_CUSTOMER_IDENTITY_WALLET_RELATIONSHIP_FIRST_KEY_COLUMN = "relationship_id";

    /**
     * Crypto Customer Operation database table definition.
     */
    static final String CRYPTO_CUSTOMER_OPERATION_TABLE_NAME = "crypto_customer_operation";

    static final String CRYPTO_CUSTOMER_OPERATION_OPERATION_ID_COLUMN_NAME = "operation_id";
    static final String CRYPTO_CUSTOMER_OPERATION_OPERATION_ID_DOC_COLUMN_NAME = "operation_id_doc";
    static final String CRYPTO_CUSTOMER_OPERATION_OPERATION_TYPE_COLUMN_NAME = "operation_type";
    static final String CRYPTO_CUSTOMER_OPERATION_OPERATION_ACCION_COLUMN_NAME = "operation_accion";
    static final String CRYPTO_CUSTOMER_OPERATION_PUBLIC_KEY_BROKER_COLUMN_NAME = "public_key_broker";
    static final String CRYPTO_CUSTOMER_OPERATION_TIMESTAMP_COLUMN_NAME = "timestamp";

    static final String CRYPTO_CUSTOMER_OPERATION_FIRST_KEY_COLUMN = "operation_id";

}