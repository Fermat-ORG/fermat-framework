package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;

// import com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.enums.AccountStatus;

/**
 * Created by ciencias on 3/24/15.
 */
class BasicWalletDatabaseConstants {
     /**
     * Value Chunks database table definition.
     */
    static final String VALUE_CHUNKS_TABLE_NAME = "ValueChunks";
    static final String VALUE_CHUNKS_TABLE_ID_COLUMN_NAME = "Id";
    static final String VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME = "Status";
    static final String VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME = "IdParent";
    static final String VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";
    static final String VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME = "IdCredit";
    static final String VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME = "IdDebit";

    /**
     * Debits database table definition.
     */
    static final String DEBITS_TABLE_NAME = "Debits";
    static final String DEBITS_TABLE_ID_COLUMN_NAME = "Id";
    static final String DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String DEBITS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";

    /**
     * Credits database table definition.
     */
    static final String CREDITS_TABLE_NAME = "Credits";
    static final String CREDITS_TABLE_ID_COLUMN_NAME = "Id";
    static final String CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME = "FiatAmount";
    static final String CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME = "CryptoAmount";
    static final String CREDITS_TABLE_TIME_STAMP_COLUMN_NAME = "TimeStamp";
    
}
