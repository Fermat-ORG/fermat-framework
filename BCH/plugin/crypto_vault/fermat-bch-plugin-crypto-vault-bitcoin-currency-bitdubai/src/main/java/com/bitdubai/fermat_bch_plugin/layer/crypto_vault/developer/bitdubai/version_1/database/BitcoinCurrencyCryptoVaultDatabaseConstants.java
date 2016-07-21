package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.CryptoVault.BitcoinCurrency.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinCurrencyCryptoVaultDatabaseConstants {

    /**
     * key_accounts database table definition.
     */
    static final String KEY_ACCOUNTS_TABLE_NAME = "key_accounts";

    static final String KEY_ACCOUNTS_ID_COLUMN_NAME = "id";
    static final String KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME = "description";
    static final String KEY_ACCOUNTS_TYPE_COLUMN_NAME = "type";

    static final String KEY_ACCOUNTS_FIRST_KEY_COLUMN = "id";

    /**
     * key_Maintenance database table definition.
     */
    static final String KEY_MAINTENANCE_TABLE_NAME = "key_maintenance";

    static final String KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME = "account_id";
    static final String KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME = "generated_keys";
    static final String KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME = "used_keys";

    static final String KEY_MAINTENANCE_FIRST_KEY_COLUMN = "account_id";

    /**
     * key_Maintenance_detail database table definition.
     */
    static final String KEY_MAINTENANCE_DETAIL_TABLE_NAME = "key_maintenance_detail";

    static final String KEY_MAINTENANCE_DETAIL_ACCOUNT_ID_COLUMN_NAME = "account_id";
    static final String KEY_MAINTENANCE_DETAIL_KEY_DEPTH_COLUMN_NAME = "depth";
    static final String KEY_MAINTENANCE_DETAIL_PUBLIC_KEY_COLUMN_NAME = "public_key";
    static final String KEY_MAINTENANCE_DETAIL_ADDRESS_COLUMN_NAME = "address";
    static final String KEY_MAINTENANCE_DETAIL_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME = "network_type";

    static final String KEY_MAINTENANCE_DETAIL_FIRST_KEY_COLUMN = "account_id";



    /**
     * key_Maintenance_Monitor database table definition.
     */
    static final String KEY_MAINTENANCE_MONITOR_TABLE_NAME = "key_maintenance_monitor";

    static final String KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME = "execution_number";
    static final String KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME = "account_id";
    static final String KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME = "execution_date";
    static final String KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME = "generated_keys";
    static final String KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME = "used_keys";
    static final String KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME = "threshold";

    static final String KEY_MAINTENANCE_MONITOR_FIRST_KEY_COLUMN = "execution_number";

    /**
     * active_Networks database table definition.
     */
    static final String ACTIVE_NETWORKS_TABLE_NAME = "active_networks";

    static final String ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME = "networktype";
    static final String ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME = "activation_date";

    static final String ACTIVE_NETWORKS_FIRST_KEY_COLUMN = "networktype";

    /**
     * Imported_Seeds database table definition.
     */
    static final String IMPORTED_SEED_TABLE_NAME = "imported_seed";

    static final String IMPORTED_SEED_DATE_COLUMN_NAME = "seed_date";
    static final String IMPORTED_SEED_WALLET_ADDRESS_COLUMN_NAME = "wallet_address";
    static final String IMPORTED_SEED_NETWORK_TYPE_COLUMN_NAME = "network_type";
    static final String IMPORTED_SEED_BALANCE_COLUMN_NAME = "balance";
    static final String IMPORTED_SEED_STATUS_COLUMN_NAME = "status";

    static final String IMPORTED_SEED_FIRST_KEY_COLUMN = "seed_date";

}