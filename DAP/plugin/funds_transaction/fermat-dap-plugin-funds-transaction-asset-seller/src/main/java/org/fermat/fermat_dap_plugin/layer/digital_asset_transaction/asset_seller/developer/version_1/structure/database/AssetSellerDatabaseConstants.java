package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.database;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellerDatabaseConstants {
    public static final String ASSET_SELLER_DATABASE = "assetSellerDatabase";
    /**
     * Asset seller database table definition.
     */
    public static final String ASSET_SELLER_TABLE_NAME = "asset_seller";

    public static final String ASSET_SELLER_ENTRY_ID_COLUMN_NAME = "entry_id";
    public static final String ASSET_SELLER_GENESIS_TRANSACTION_COLUMN_NAME = "genesisTx";
    public static final String ASSET_SELLER_NETWORK_TYPE_COLUMN_NAME = "network";
    public static final String ASSET_SELLER_BUYER_PUBLICKEY_COLUMN_NAME = "buyerPublicKey";
    public static final String ASSET_SELLER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME = "buyerCryptoAddress";
    public static final String ASSET_SELLER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME = "sellerCryptoAddress";
    public static final String ASSET_SELLER_CRYPTO_CURRENCY_COLUMN_NAME = "cryptoCurrency";
    public static final String ASSET_SELLER_SELL_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_SELLER_NEGOTIATION_REFERENCE_COLUMN_NAME = "negotiation";
    public static final String ASSET_SELLER_SELLER_TRANSACTION_COLUMN_NAME = "unsignedTransaction"; //This will be encode as string using Apache Commons base 64 encoding.
    public static final String ASSET_SELLER_SELLER_VALUE_COLUMN_NAME = "sellerValue";
    public static final String ASSET_SELLER_BUYER_TRANSACTION_COLUMN_NAME = "signedTransaction"; //This will be encode as string using Apache Commons base 64 encoding.
    public static final String ASSET_SELLER_BUYER_VALUE_COLUMN_NAME = "buyerValue";
    public static final String ASSET_SELLER_TX_HASH_COLUMN_NAME = "transactionHash"; //This transaction is the already full signed transaction.
    public static final String ASSET_SELLER_TIMESTAMP_COLUMN_NAME = "timeStamp";

    public static final String ASSET_SELLER_FIRST_KEY_COLUMN = ASSET_SELLER_ENTRY_ID_COLUMN_NAME;

    public static final String ASSET_SELLER_NEGOTIATION_TABLE_NAME = "negotiation";

    public static final String ASSET_SELLER_NEGOTIATION_ID_COLUMN_NAME = "entry_id";
    public static final String ASSET_SELLER_NEGOTIATION_OBJECT_XML_COLUMN_NAME = "object";
    public static final String ASSET_SELLER_NEGOTIATION_ACCEPTED_ASSETS_COLUMN_NAME = "acceptedAssets";
    public static final String ASSET_SELLER_NEGOTIATION_REJECTED_ASSETS_COLUMN_NAME = "rejectedAssets";
    public static final String ASSET_SELLER_NEGOTIATION_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_SELLER_NEGOTIATION_BUYER_PUBLICKEY_COLUMN_NAME = "buyerPk";
    public static final String ASSET_SELLER_NEGOTIATION_TIMESTAMP_COLUMN_NAME = "timeStamp";

    public static final String ASSET_SELLER_NEGOTIATION_FIRST_KEY_COLUMN = ASSET_SELLER_NEGOTIATION_ID_COLUMN_NAME;

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_SELLER_EVENTS_RECORDED_TABLE_NAME = "seller_events_recorded";

    public static final String ASSET_SELLER_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_SELLER_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_SELLER_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_SELLER_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_SELLER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_SELLER_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = ASSET_SELLER_EVENTS_RECORDED_ID_COLUMN_NAME;
}
