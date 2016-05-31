package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetsOverBitcoinCryptoVaultDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public AssetsOverBitcoinCryptoVaultDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create key_accounts table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);

            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.TRUE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);

            table.addIndex(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create key_Maintenance table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME);

            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.TRUE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);

            table.addIndex(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create key_Maintenance_detail table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_TABLE_NAME);

            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ACCOUNT_ID_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.TRUE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_KEY_DEPTH_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.TRUE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);

            table.addIndex(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }


            /**
             * Create key_Maintenance_Monitor table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME);

            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME, DatabaseDataType.INTEGER, 10, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME, DatabaseDataType.INTEGER, 100, Boolean.FALSE);

            table.addIndex(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create active_Networks table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_TABLE_NAME);

            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.TRUE);
            table.addColumn(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}