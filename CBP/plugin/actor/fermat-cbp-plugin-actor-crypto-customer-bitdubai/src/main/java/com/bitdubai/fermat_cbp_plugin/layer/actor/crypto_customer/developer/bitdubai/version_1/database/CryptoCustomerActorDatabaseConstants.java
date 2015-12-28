package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 21/11/15.
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
    static final String CRYPTO_CUSTOMER_ACTOR_NAME_ACTOR_COLUMN_NAME = "name_actor";
    static final String CRYPTO_CUSTOMER_ACTOR_CONNECTION_STATE_COLUMN_NAME = "connection_state";
    static final String CRYPTO_CUSTOMER_ACTOR_TIMESTAMP_COLUMN_NAME = "timestamp";

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

}