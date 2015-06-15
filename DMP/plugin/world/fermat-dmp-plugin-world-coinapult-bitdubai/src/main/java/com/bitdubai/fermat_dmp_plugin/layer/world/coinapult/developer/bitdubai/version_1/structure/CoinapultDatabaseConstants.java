/*
 * @#CoinapultDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.CoinapultDatabaseConstants</code>
 * keeps constants the column names of the database.
 *
 * Created by Roberto Requena - (rrequena) on 30/04/15.
 * @version 1.0
 */
public class CoinapultDatabaseConstants {

    /**
     * Crypto Address database table definition.
     */
    static final String ADDRESSES_TABLE_NAME = "coinapult_addresses";
    static final String ADDRESSES_TABLE_ADDRESS_COLUMN_NAME = "address";
    static final String ADDRESSES_TABLE_TYPE_COLUMN_NAME = "type";
    static final String ADDRESSES_TABLE_WALLET_ID_COLUMN_NAME = "wallet_id";

    /**
     * Balances History database table definition.
     */
    static final String BALANCES_HISTORY_TABLE_NAME = "coinapult_balances_history";
    static final String BALANCES_HISTORY_TABLE_AMOUNT_COLUMN_NAME = "amount";
    static final String BALANCES_HISTORY_TABLE_CURRENCY_COLUMN_NAME = "currency";
    static final String BALANCES_HISTORY_TABLE_TIMESTAMP_COLUMN_NAME = "timestamp";
    static final String BALANCES_HISTORY_TABLE_WALLET_ID_COLUMN_NAME = "wallet_id";

    /**
     * TRANSACTION History database table definition.
     */
    static final String TRANSACTION_HISTORY_TABLE_NAME = "coinapult_transaction_history";
    static final String TRANSACTION_HISTORY_TABLE_ADDRESS_COLUMN_NAME = "address";
    static final String TRANSACTION_HISTORY_TABLE_COMPLETE_TIME_NAME = "complete_time";
    static final String TRANSACTION_HISTORY_TABLE_EXPIRATION_NAME = "expiration";
    static final String TRANSACTION_HISTORY_TABLE_IN_AMOUNT_NAME = "in_amount";
    static final String TRANSACTION_HISTORY_TABLE_IN_CURRENCY_NAME = "in_currency";
    static final String TRANSACTION_HISTORY_TABLE_IN_EXPECTED_NAME = "in_expected";
    static final String TRANSACTION_HISTORY_TABLE_OUT_AMOUNT_NAME = "out_amount";
    static final String TRANSACTION_HISTORY_TABLE_OUT_CURRENCY_NAME = "out_currency";
    static final String TRANSACTION_HISTORY_TABLE_OUT_EXPECTED_NAME = "out_expected";
    static final String TRANSACTION_HISTORY_TABLE_QUOTE_ASK = "quote_ask";
    static final String TRANSACTION_HISTORY_TABLE_QUOTE_BID_NAME = "quote_bid";
    static final String TRANSACTION_HISTORY_TABLE_STATE_NAME = "state";
    static final String TRANSACTION_HISTORY_TABLE_TIMESTAMP_NAME = "timestamp";
    static final String TRANSACTION_HISTORY_TABLE_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String TRANSACTION_HISTORY_TABLE_TYPE_COLUMN_NAME = "type";
    static final String TRANSACTION_HISTORY_TABLE_WALLET_ID_COLUMN_NAME = "wallet_id";

}
