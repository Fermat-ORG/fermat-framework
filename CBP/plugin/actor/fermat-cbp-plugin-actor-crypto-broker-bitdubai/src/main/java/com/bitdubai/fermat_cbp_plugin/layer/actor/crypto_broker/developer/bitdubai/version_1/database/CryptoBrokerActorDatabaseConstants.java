package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 16/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerActorDatabaseConstants {

    /**
     * Crypto Broker Actor Relationship database table definition.
     */
    static final String CRYPTO_BROKER_ACTOR_RELATIONSHIP_TABLE_NAME = "crypto_broker_actor_relationship";

    static final String CRYPTO_BROKER_ACTOR_RELATIONSHIP_RELATIONSHIP_ID_COLUMN_NAME = "relationship_id";
    static final String CRYPTO_BROKER_ACTOR_RELATIONSHIP_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String CRYPTO_BROKER_ACTOR_RELATIONSHIP_WALLET_COLUMN_NAME = "wallet";

    static final String CRYPTO_BROKER_ACTOR_RELATIONSHIP_FIRST_KEY_COLUMN = "relationship_id";

    /**
     * Actor Extra Data database table definition.
     */
    static final String ACTOR_EXTRA_DATA_TABLE_NAME = "actor_extra_data";

    static final String ACTOR_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String ACTOR_EXTRA_DATA_ALIAS_COLUMN_NAME = "alias";

    static final String ACTOR_EXTRA_DATA_FIRST_KEY_COLUMN = "broker_public_key";

    /**
     * Quote Extra Data database table definition.
     */
    static final String QUOTE_EXTRA_DATA_TABLE_NAME = "quote_extra_data";

    static final String QUOTE_EXTRA_DATA_QUOTE_ID_COLUMN_NAME = "quote_id";
    static final String QUOTE_EXTRA_DATA_BROKER_PUBLIC_KEY_COLUMN_NAME = "broker_public_key";
    static final String QUOTE_EXTRA_DATA_MERCHANDISE_COLUMN_NAME = "merchandise";
    static final String QUOTE_EXTRA_DATA_PAYMENT_CURRENCY_COLUMN_NAME = "payment_currency";
    static final String QUOTE_EXTRA_DATA_PRICE_COLUMN_NAME = "price";

    static final String QUOTE_EXTRA_DATA_FIRST_KEY_COLUMN = "quote_id";

}