package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.crypto_wallet.developer.bitdubai.version_1.structure;

/**
 * Created by eze on 2015.06.23..
 */
public class CryptoWalletDatabaseConstants {

    /**
     * CRYPTO Wallet database table definition.
     */
    public static final String CRYPTO_WALLET_TABLE_NAME = "CryptoWalletWallet";
    public static final String CRYPTO_WALLET_TABLE_ID_COLUMN_NAME = "Id";
    // This second Id is used to verify that that the same transaction is not applied twice.
    // We can't use the transaction hash because some credit/debit operations do not involve a hash
    public static final String CRYPTO_WALLET_TABLE_VERIFICATION_ID_COLUMN_NAME = "VerificationId";
    public static final String CRYPTO_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME = "addressFrom";
    public static final String CRYPTO_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME = "addressTo";
    public static final String CRYPTO_WALLET_TABLE_ACTOR_FROM_COLUMN_NAME= "actorFrom";
    public static final String CRYPTO_WALLET_TABLE_ACTOR_TO_COLUMN_NAME= "actorTo";
    public static final String CRYPTO_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME= "actorFromType";
    public static final String CRYPTO_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME= "actorToType";
    public static final String CRYPTO_WALLET_TABLE_AMOUNT_COLUMN_NAME = "amount";
    public static final String CRYPTO_WALLET_TABLE_TYPE_COLUMN_NAME = "type";
    public static final String CRYPTO_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME = "balanceType";
    public static final String CRYPTO_WALLET_TABLE_TIME_STAMP_COLUMN_NAME = "timestamp";
    public static final String CRYPTO_WALLET_TABLE_MEMO_COLUMN_NAME = "memo";
    public static final String CRYPTO_WALLET_TABLE_TRANSACTION_HASH_COLUMN_NAME = "transactionHash";
    public static final String CRYPTO_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME = "runningBookBalance";
    public static final String CRYPTO_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME = "runningAvailableBalance";
    public static final String CRYPTO_WALLET_TABLE_RUNNING_NETWORK_TYPE ="runningNetworkType";
    public static final String CRYPTO_WALLET_TABLE_TRANSACTION_STATE_COLUMN_NAME ="transactionstateenv";
    public static final String CRYPTO_WALLET_TABLE_TRANSACTION_FEE_COLUMN_NAME ="transactionFee";
    public static final String CRYPTO_WALLET_TABLE_TRANSACTION_FEE_ORIGIN_COLUMN_NAME ="transactionFeeOrigin";
    public static final String CRYPTO_WALLET_TABLE_TRANSACTION_TOTAL_COLUMN_NAME ="transactionTotal";
    public static final String CRYPTO_WALLET_TABLE_CRYPTO_CURRENCY_COLUMN_NAME ="cryptoCurrencyType";



    // tabla nueva movimientos- balance y book balance, id

    public static final String CRYPTO_WALLET_BALANCE_TABLE_NAME = "CRYPTOWalletWalletTotalBalances";
    public static final String CRYPTO_WALLET_BALANCE_TABLE_ID_COLUMN_NAME = "Id";
    public static final String CRYPTO_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME = "availableBalance";
    public static final String CRYPTO_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME = "bookBalance";
    public static final String CRYPTO_WALLET_BALANCE_TABLE_RUNNING_NETWORK_TYPE ="runningNetworkType";

}
