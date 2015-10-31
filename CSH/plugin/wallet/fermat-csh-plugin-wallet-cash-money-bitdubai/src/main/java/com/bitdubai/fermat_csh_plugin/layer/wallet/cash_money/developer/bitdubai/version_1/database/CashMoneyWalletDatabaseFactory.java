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
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
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
    protected  Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
             * Create Cash Money table.
             */
            table = databaseFactory.newTableFactory(ownerId, CashMoneyWalletDatabaseConstants.CASH_MONEY_TABLE_NAME);

            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_WALLET_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_PUBLIC_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_AMOUNT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_CASH_REFERENCE_COLUMN_NAME, DatabaseDataType.STRING, 300, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 300, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CashMoneyWalletDatabaseConstants.CASH_MONEY_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Cash Money Total Balances table.
             */
            table = databaseFactory.newTableFactory(ownerId, CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_TABLE_NAME);

            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_WALLET_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_PUBLIC_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_CASH_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(CashMoneyWalletDatabaseConstants.CASH_MONEY_TOTAL_BALANCES_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
            table = databaseFactory.newTableFactory(ownerId,CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TABLE_NAME );

            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_TRANSACTION_ID_COLUMN_NAME,DatabaseDataType.STRING,100,Boolean.TRUE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_FROM,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_PUBLIC_KEY_ACTOR_TO,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_STATUS,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_BALANCE_TYPE,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TRANSACTION_TYPE,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_AMAUNT,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_CURRENCY_TYPE,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_CASH_REFERENCE,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_TIME_STAMP,DatabaseDataType.STRING,100,Boolean.FALSE);
            table.addColumn(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD_MEMO,DatabaseDataType.STRING,100,Boolean.FALSE);

            table.addIndex(CashMoneyWalletDatabaseConstants.CASH_MONEY_BALANCE_RECORD__FIRST_KEY_COLUMN);
            try {
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException e) {
                e.printStackTrace();
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