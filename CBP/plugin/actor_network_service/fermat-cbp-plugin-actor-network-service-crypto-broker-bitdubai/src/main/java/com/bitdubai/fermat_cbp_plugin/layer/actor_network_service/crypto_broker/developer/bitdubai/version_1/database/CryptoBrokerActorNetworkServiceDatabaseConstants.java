package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorNetworkServiceDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/15.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CryptoBrokerActorNetworkServiceDatabaseConstants {

    /**
     * Crypto Broker Actor Network Service database global definition.
     */
    public static final String CRYPTO_BROKER_ACTOR_NETWORK_SERVICE_DATABASE_NAME = "crypto_broker_actor_network_service";

    /**
     * Connection News database table definition.
     */
    public static final String CONNECTION_NEWS_TABLE_NAME = "connection_news";

    public static final String CONNECTION_NEWS_REQUEST_ID_COLUMN_NAME = "request_id";
    public static final String CONNECTION_NEWS_SENDER_PUBLIC_KEY_COLUMN_NAME = "sender_public_key";
    public static final String CONNECTION_NEWS_SENDER_ACTOR_TYPE_COLUMN_NAME = "sender_actor_type";
    public static final String CONNECTION_NEWS_SENDER_ALIAS_COLUMN_NAME = "sender_alias";
    public static final String CONNECTION_NEWS_DESTINATION_PUBLIC_KEY_COLUMN_NAME = "destination_public_key";
    public static final String CONNECTION_NEWS_REQUEST_TYPE_COLUMN_NAME = "request_type";
    public static final String CONNECTION_NEWS_REQUEST_STATE_COLUMN_NAME = "request_state";
    public static final String CONNECTION_NEWS_REQUEST_ACTION_COLUMN_NAME = "request_action";
    public static final String CONNECTION_NEWS_SENT_TIME_COLUMN_NAME = "sent_time";

    public static final String CONNECTION_NEWS_FIRST_KEY_COLUMN = "request_id";

    /**
     * Quotes Request database table definition.
     */
    public static final String QUOTES_REQUEST_TABLE_NAME = "quotes_request";

    public static final String QUOTES_REQUEST_REQUEST_ID_COLUMN_NAME = "request_id";
    public static final String QUOTES_REQUEST_REQUESTER_PUBLIC_KEY_COLUMN_NAME = "requester_public_key";
    public static final String QUOTES_REQUEST_REQUESTER_ACTOR_TYPE_COLUMN_NAME = "requester_actor_type";
    public static final String QUOTES_REQUEST_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME = "crypto_broker_public_key";
    public static final String QUOTES_REQUEST_UPDATE_TIME_COLUMN_NAME = "update_time";
    public static final String QUOTES_REQUEST_TYPE_COLUMN_NAME = "type";
    public static final String QUOTES_REQUEST_STATE_COLUMN_NAME = "state";

    public static final String QUOTES_REQUEST_FIRST_KEY_COLUMN = "request_id";

    /**
     * Quotes database table definition.
     */
    public static final String QUOTES_TABLE_NAME = "quotes";

    public static final String QUOTES_REQUEST_ID_COLUMN_NAME = "request_id"; // RELATION WITH QUOTES REQUEST TABLE.
    public static final String QUOTES_MERCHANDISE_COLUMN_NAME = "merchandise";
    public static final String QUOTES_MERCHANDISE_TYPE_COLUMN_NAME = "merchandise_type";
    public static final String QUOTES_PAYMENT_CURRENCY_COLUMN_NAME = "payment_currency";
    public static final String QUOTES_PAYMENT_CURRENCY_TYPE_COLUMN_NAME = "payment_currency_type";
    public static final String QUOTES_PRICE_COLUMN_NAME = "price";
    public static final String QUOTES_SUPPORTED_PLATFORMS_COLUMN_NAME = "supported_platforms";

    public static final String QUOTES_FIRST_KEY_COLUMN = "request_id";

}