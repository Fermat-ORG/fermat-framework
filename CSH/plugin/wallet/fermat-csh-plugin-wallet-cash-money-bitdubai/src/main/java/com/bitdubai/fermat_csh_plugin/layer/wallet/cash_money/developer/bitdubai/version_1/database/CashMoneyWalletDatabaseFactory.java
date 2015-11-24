package   com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.Cash MoneyWalletDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CashMoneyWalletDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CashMoneyWalletDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Wallets table.
             */
            table = databaseFactory.newTableFactory(ownerId, CashMoneyWalletDatabaseConstants.WALLETS_TABLE_NAME);

            table.addColumn(CashMoneyWalletDatabaseConstants.WALLETS_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.WALLETS_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.WALLETS_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.WALLETS_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.WALLETS_TIMESTAMP_WALLET_CREATION_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(CashMoneyWalletDatabaseConstants.WALLETS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Transactions table.
             */
            table = databaseFactory.newTableFactory(ownerId, CashMoneyWalletDatabaseConstants.TRANSACTIONS_TABLE_NAME);

            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_ACTOR_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.TRANSACTIONS_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            table.addIndex(CashMoneyWalletDatabaseConstants.TRANSACTIONS_FIRST_KEY_COLUMN);

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
