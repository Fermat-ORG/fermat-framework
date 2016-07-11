package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database;

/**
 * The Class <code>UnHoldCryptoMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Franklin Marcano on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UnHoldCryptoMoneyTransactionDatabaseConstants {

    /**
     * Hold database table definition.
     */
    public static final String UNHOLD_DATABASE_NAME  = "unhold_DB";
    public static final  String UNHOLD_TABLE_NAME = "unhold";

    public static final String UNHOLD_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    public static final String UNHOLD_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    public static final String UNHOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    public static final String UNHOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    public static final String UNHOLD_AMOUNT_COLUMN_NAME = "amount";
    public static final String UNHOLD_CURRENCY_COLUMN_NAME = "currency";
    public static final String UNHOLD_MEMO_COLUMN_NAME = "memo";
    public static final String UNHOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME = "timestamp_acknowledge";
    public static final String UNHOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME = "timestamp_confirm_reject";
    public static final String UNHOLD_STATUS_COLUMN_NAME = "status";
    public static final String UNHOLD_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME = "block_chain_network_type";
    public static final String UNHOLD_FIRST_KEY_COLUMN = "transaction_id";
    public static final String UNHOLD_FEE_COLUMN_NAME = "fee";
    public static final String UNHOLD_FEE_ORIGIN_COLUMN_NAME = "fee_origin";

}