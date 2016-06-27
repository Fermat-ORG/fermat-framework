package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.database;

/**
 * Created by rodrigo on 6/22/16.
 */
public class FermatCryptoNetworkDatabaseConstants {


    /**
     * Incoming_Transactions database table definition.
     */
    static final String TRANSACTIONS_TABLE_NAME = "transactions";

    static final String TRANSACTIONS_TRX_ID_COLUMN_NAME = "trx_id";
    static final String TRANSACTIONS_HASH_COLUMN_NAME = "hash";
    static final String TRANSACTIONS_BLOCK_HASH_COLUMN_NAME = "block_hash";
    static final String TRANSACTIONS_BLOCKCHAIN_NETWORK_TYPE = "network_type";
    static final String TRANSACTIONS_CRYPTO_STATUS_COLUMN_NAME = "crypto_status";
    static final String TRANSACTIONS_BLOCK_DEPTH_COLUMN_NAME = "block_depth";
    static final String TRANSACTIONS_ADDRESS_TO_COLUMN_NAME = "address_to";
    static final String TRANSACTIONS_ADDRESS_FROM_COLUMN_NAME = "address_from";
    static final String TRANSACTIONS_BTC_AMOUNT_COLUMN_NAME = "btc_amount";
    static final String TRANSACTIONS_FEE_AMOUNT_COLUMN_NAME = "fee";
    static final String TRANSACTIONS_CRYPTO_AMOUNT_COLUMN_NAME = "crypto_amount";
    static final String TRANSACTIONS_OP_RETURN_COLUMN_NAME = "op_return";
    static final String TRANSACTIONS_PROTOCOL_STATUS_COLUMN_NAME = "protocol_status";
    static final String TRANSACTIONS_LAST_UPDATE_COLUMN_NAME = "last_update";
    static final String TRANSACTIONS_TYPE_COLUMN_NAME = "type";

    static final String TRANSACTIONS_FIRST_KEY_COLUMN = "trx_id";

    /**
     * CryptoVaults_Stats database table definition.
     */
    static final String CRYPTOVAULTS_STATS_TABLE_NAME = "cryptovaults_stats";

    static final String CRYPTOVAULTS_STATS_CRYPTO_VAULT_COLUMN_NAME = "crypto_vault";
    static final String CRYPTOVAULTS_STATS_LAST_CONNECTION_REQUEST_COLUMN_NAME = "last_connection_request";
    static final String CRYPTOVAULTS_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME = "monitored_publickeys";

    static final String CRYPTOVAULTS_STATS_FIRST_KEY_COLUMN = "crypto_vault";

    /**
     * CryptoVaults_DetailedStats database table definition.
     */
    static final String CRYPTOVAULTS_DETAILED_STATS_TABLE_NAME = "cryptovaults_detailed_stats";

    static final String CRYPTOVAULTS_DETAILED_STATS_CRYPTO_VAULT_COLUMN_NAME = "crypto_vault";
    static final String CRYPTOVAULTS_DETAILED_STATS_NETWORK_COLUMN_NAME = "network";
    static final String CRYPTOVAULTS_DETAILED_STATS_ORDER_COLUMN_NAME = "key_order";
    static final String CRYPTOVAULTS_DETAILED_STATS_MONITORED_PUBLICKEYS_COLUMN_NAME = "monitored_publickeys";
    static final String CRYPTOVAULTS_DETAILED_STATS_MONITORED_ADDRESSES_COLUMN_NAME = "monitored_addresses";

    static final String CRYPTOVAULTS_DETAILED_STATS_FIRST_KEY_COLUMN = "crypto_vault";

    /**
     * EventAgent_Stats database table definition.
     */
    static final String EVENTAGENT_STATS_TABLE_NAME = "eventagent_stats";

    static final String EVENTAGENT_STATS_EXECUTION_NUMBER_COLUMN_NAME = "execution_number";
    static final String EVENTAGENT_STATS_LAST_EXECUTION_DATE_COLUMN_NAME = "last_execution_date";
    static final String EVENTAGENT_STATS_PENDING_INCOMING_TRANSACTIONS_COLUMN_NAME = "pending_incoming_transactions";
    static final String EVENTAGENT_STATS_PENDING_OUTGOING_TRANSACTIONS_COLUMN_NAME = "pending_outgoing_transactions";

    static final String EVENTAGENT_STATS_FIRST_KEY_COLUMN = "execution_number";

    /**
     * Broadcast database table definition.
     */
    static final String BROADCAST_TABLE_NAME = "broadcast";

    static final String BROADCAST_EXECUTION_NUMBER_COLUMN_NAME = "execution_number";
    static final String BROADCAST_NETWORK = "network";
    static final String BROADCAST_TRANSACTION_ID = "tx_id";
    static final String BROADCAST_TX_HASH = "hash";
    static final String BROADCAST_PEER_COUNT = "peer_count";
    static final String BROADCAST_PEER_BROADCAST_IP = "peer_ip";
    static final String BROADCAST_RETRIES_COUNT = "retries";
    static final String BROADCAST_STATUS = "status";
    static final String BROADCAST_EXCEPTION = "exception";
    static final String BROADCAST_LAST_EXECUTION_DATE_COLUMN_NAME = "last_execution_date";

    static final String BRTOADCAST_FIRST_KEY_COLUMN = "hash";

    /**
     * ActiveNetworks database table definition.
     */
    static final String ACTIVENETWORKS_TABLE_NAME = "activeNetworkTypes";

    static final String ACTIVENETWORKS_NETWORKTYPE= "networkType";
    static final String ACTIVENETWORKS_KEYS = "keys";
    static final String ACTIVENETWORKS_LAST_UPDATE = "last_update";

    static final String ACTIVENETWORKS_FIRST_KEY_COLUMN = "networkType";
}
