package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database;

/**
 * The Class <code>HoldCryptoMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Franklin Marcano on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class HoldCryptoMoneyTransactionDatabaseConstants {

    /**
     * Hold database table definition.
     */
    public static final String HOLD_DATABASE_NAME  = "hold_DB";
    public static final  String HOLD_TABLE_NAME = "hold";

    public static final String HOLD_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    public static final String HOLD_AMOUNT_COLUMN_NAME = "amount";
    public static final String HOLD_CURRENCY_COLUMN_NAME = "currency";
    public static final String HOLD_MEMO_COLUMN_NAME = "memo";
    public static final String HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME = "timestamp_acknowledge";
    public static final String HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME = "timestamp_confirm_reject";
    public static final String HOLD_STATUS_COLUMN_NAME = "status";
    public static final String HOLD_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME = "block_chain_network_type";
    public static final String HOLD_FEE_COLUMN_NAME = "fee";
    public static final String HOLD_FEE_ORIGIN_TYPE_COLUMN_NAME = "fee_origin";

    public static final String HOLD_FIRST_KEY_COLUMN = "transaction_id";

}