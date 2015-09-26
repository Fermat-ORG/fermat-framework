package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

/**
 * Created by Natalia on 30/03/2015.
 */
public class BlockchainInfoDatabaseConstants {
    /**
     * Incoming Crypto database table definition.
     */
    static final String INCOMING_CRYPTO_TABLE_NAME = "incoming_crypto";
    static final String INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME = "trx_hash";
    static final String INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME = "amount";
    static final String INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME = "crypto_address_to";
    static final String INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_FROM_COLUMN_NAME = "crypto_address_from";
    static final String INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME = "status";
    static final String INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME = "current_confirmations";
    static final String INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME = "previous_confirmations";

    /**
     * Crypto Account database table definition.
     */
    static final String OUTGOING_CRYPTO_TABLE_NAME = "crypto_accounts";
    static final String OUTGOING_CRYPTO_TABLE_ID_COLUMN_NAME = "id";
    static final String OUTGOING_CRYPTO_TABLE_TRX_COLUMN_NAME = "trx_hash";
    static final String OUTGOING_CRYPTO_TABLE_WALLET_COLUMN_NAME = "wallet_id";

    /**
     * Crypto Address database table definition.
     */
    static final String CRYPTO_ADDRESSES_TABLE_NAME = "crypto_address";
    static final String CRYPTO_ADDRESSES_TABLE_ID_COLUMN_NAME = "id";
    static final String CRYPTO_ADDRESSES_TABLE_WALLET_COLUMN_NAME = "wallet_id";
    static final String CRYPTO_ADDRESSES_TABLE_ADDRESS_COLUMN_NAME = "address";
    static final String CRYPTO_ADDRESSES_TABLE_LABEL_COLUMN_NAME = "label";

}
