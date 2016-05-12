package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.loss_protected_wallet.developer.bitdubai.version_1.structure;

/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinLossProtectedWalletDatabaseConstants {

    /**
     * LOSS_PROTECTED Wallet database table definition.
     */
    public static final String LOSS_PROTECTED_WALLET_TABLE_NAME = "LossProtectedWallet";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ID_COLUMN_NAME = "Id";
    // This second Id is used to verify that that the same transaction is not applied twice.
    // We can't use the transaction hash because some credit/debit operations do not involve a hash
    public static final String LOSS_PROTECTED_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME = "VerificationId";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME = "addressFrom";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME = "addressTo";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME= "actorFrom";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_COLUMN_NAME= "actorTo";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME= "actorFromType";
    public static final String LOSS_PROTECTED_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME= "actorToType";
    public static final String LOSS_PROTECTED_WALLET_TABLE_AMOUNT_COLUMN_NAME = "amount";
    public static final String LOSS_PROTECTED_WALLET_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String LOSS_PROTECTED_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME = "balanceType";
    public static final String LOSS_PROTECTED_WALLET_TABLE_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String LOSS_PROTECTED_WALLET_TABLE_MEMO_COLUMN_NAME = "memo";
    public static final String LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";
    public static final String LOSS_PROTECTED_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME = "runningBookBalance";
    public static final String LOSS_PROTECTED_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "runningAvailableBalance";
    public static final String LOSS_PROTECTED_WALLET_TABLE_RUNNING_NETWORK_TYPE ="runningNetworkType";
    public static final String LOSS_PROTECTED_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME ="transactionstateenv";
    public static final String LOSS_PROTECTED_WALLET_TABLE_EXCHANGE_RATE_COLUMN_NAME ="dollarexchangerate";


    // tabla movimientos- balance y book balance, id

    public static final String LOSS_PROTECTED_WALLET_BALANCE_TABLE_NAME = "LossProtectedWalletTotalBalances";
    public static final String LOSS_PROTECTED_WALLET_BALANCE_TABLE_ID_COLUMN_NAME = "Id";
    public static final String LOSS_PROTECTED_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME = "availableBalance";
    public static final String LOSS_PROTECTED_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME = "bookBalance";
    public static final String LOSS_PROTECTED_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE ="runningNetworkType";

    // table BTC Spending

    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_NAME = "LossProtectedWalletSpent";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_ID_COLUMN_NAME = "Id";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TRANSACTION_ID_COLUMN_NAME = "transactionid";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_BTC_SPENT_COLUMN_NAME = "btcspent";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_EXCHANGE_RATE_COLUMN_NAME = "dollarexchangerate";
    public static final String LOSS_PROTECTED_WALLET_SPENT_TABLE_RUNNING_NETWORK_TYPE = "runningNetworkType";


}
