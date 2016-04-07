package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetBuyerDatabaseConstants {
    public static final String ASSET_BUYER_DATABASE = "assetBuyerDatabase";
    /**
     * Asset seller database table definition.
     */
    public static final String ASSET_BUYER_TABLE_NAME = "asset_buyer";

    public static final String ASSET_BUYER_ENTRY_ID_COLUMN_NAME = "entry_id";
    public static final String ASSET_BUYER_NETWORK_TYPE_COLUMN_NAME = "networkType";
    public static final String ASSET_BUYER_SELLER_PUBLICKEY_COLUMN_NAME = "sellerPublicKey";
    public static final String ASSET_BUYER_SELL_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_BUYER_SELLER_CRYPTO_ADDRESS_COLUMN_NAME = "sellerCryptoAddress";
    public static final String ASSET_BUYER_BUYER_CRYPTO_ADDRESS_COLUMN_NAME = "buyerCryptoAddress";
    public static final String ASSET_BUYER_CRYPTO_CURRENCY_COLUMN_NAME = "currency";
    public static final String ASSET_BUYER_NEGOTIATION_REFERENCE_COLUMN_NAME = "negotiation";
    public static final String ASSET_BUYER_BUYER_TRANSACTION_COLUMN_NAME = "unsignedTransaction"; //This will be encode as string using Apache Commons base 64 encoding.
    public static final String ASSET_BUYER_BUYER_VALUE_COLUMN_NAME = "buyerValue";
    public static final String ASSET_BUYER_SELLER_TRANSACTION_COLUMN_NAME = "signedTransaction"; //This will be encode as string using Apache Commons base 64 encoding.
    public static final String ASSET_BUYER_SELLER_VALUE_COLUMN_NAME = "sellerValue";
    public static final String ASSET_BUYER_SELLER_ENTRY_ID_COLUMN_NAME = "seller_entry_id";
    public static final String ASSET_BUYER_OUTGOING_ID_COLUMN_NAME = "outgoingTxId"; //This is the OutgoingTransactionManager UUID that we'll use to reference this tx.
    public static final String ASSET_BUYER_TX_HASH_COLUMN_NAME = "transactionHash"; //This transaction is the already full signed transaction.
    public static final String ASSET_BUYER_TIMESTAMP_COLUMN_NAME = "timeStamp";

    public static final String ASSET_BUYER_FIRST_KEY_COLUMN = ASSET_BUYER_ENTRY_ID_COLUMN_NAME;

    public static final String ASSET_BUYER_NEGOTIATION_TABLE_NAME = "negotiation";

    public static final String ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME = "entry_id";
    public static final String ASSET_BUYER_NEGOTIATION_OBJECT_XML_COLUMN_NAME = "object";
    public static final String ASSET_BUYER_NEGOTIATION_SELLER_PUBLICKEY_COLUMN_NAME = "sellerPublicKey";
    public static final String ASSET_BUYER_NEGOTIATION_ASSET_PUBLICKEY_COLUMN_NAME = "assetPublicKey";
    public static final String ASSET_BUYER_NEGOTIATION_BTC_WALLET_PK_COLUMN_NAME = "btcWalletPublicKey";
    public static final String ASSET_BUYER_NEGOTIATION_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_BUYER_NEGOTIATION_NETWORK_TYPE_COLUMN_NAME = "networkType";
    public static final String ASSET_BUYER_NEGOTIATION_FOR_PROCESS_COLUMN_NAME = "for_process";
    public static final String ASSET_BUYER_NEGOTIATION_TIMESTAMP_COLUMN_NAME = "timeStamp";

    public static final String ASSET_BUYER_NEGOTIATION_FIRST_KEY_COLUMN = ASSET_BUYER_NEGOTIATION_ID_COLUMN_NAME;

    /**
     * Events recorded database table definition.
     */
    public static final String ASSET_BUYER_EVENTS_RECORDED_TABLE_NAME = "buyer_events_recorded";

    public static final String ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME = "event_id";
    public static final String ASSET_BUYER_EVENTS_RECORDED_EVENT_COLUMN_NAME = "event";
    public static final String ASSET_BUYER_EVENTS_RECORDED_SOURCE_COLUMN_NAME = "source";
    public static final String ASSET_BUYER_EVENTS_RECORDED_STATUS_COLUMN_NAME = "status";
    public static final String ASSET_BUYER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME = "timestamp";

    public static final String ASSET_BUYER_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN = ASSET_BUYER_EVENTS_RECORDED_ID_COLUMN_NAME;
}
