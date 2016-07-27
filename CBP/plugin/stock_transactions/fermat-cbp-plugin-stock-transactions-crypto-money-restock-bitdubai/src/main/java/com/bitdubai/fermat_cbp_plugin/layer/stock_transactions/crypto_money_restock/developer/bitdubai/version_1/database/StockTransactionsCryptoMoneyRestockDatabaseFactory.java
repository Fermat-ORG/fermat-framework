package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * The Class <code>BusinessTransactionBankMoneyDestockDatabaseFactory</code>
 * contains the logic for handling database the plugin
 * Created by franklin on 16/11/15.
 */
public class StockTransactionsCryptoMoneyRestockDatabaseFactory {
    private final PluginDatabaseSystem pluginDatabaseSystem;

    public StockTransactionsCryptoMoneyRestockDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

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
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "Business Transaction Bank Money Restock", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }


        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Bank Money
             */
            table = databaseFactory.newTableFactory(ownerId, StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TABLE_NAME);

            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.TRUE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CRY_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_BANK_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_CONCEPT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_PRICE_REFERENCE_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_ORIGIN_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_BLOCK_CHAIN_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 255, Boolean.FALSE);
            table.addColumn(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FEE_ORIGIN_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);


            table.addIndex(StockTransactionsCrpytoMoneyRestockDatabaseConstants.CRYPTO_MONEY_RESTOCK_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Business Transaction Bank Money Restock", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "Business Transaction Bank Money Restock", "There is a problem with the ownerId of the database.");
        }
        return database;
    }
}
