package   com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.Bank MoneyWalletDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 13/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BankMoneyWalletDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public BankMoneyWalletDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Bank Money table.
             */
            table = databaseFactory.newTableFactory(ownerId, BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTIONS_TABLE_NAME);

            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_AMOUNT_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_OPERATION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_DOCUMENT_REFERENCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 300, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(BankMoneyWalletDatabaseConstants.BANK_MONEY_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }           /**
             * Create Bank Money Total Balances table.
             */
            table = databaseFactory.newTableFactory(ownerId, BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_TABLE_NAME);

            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_BANK_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BANK_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(BankMoneyWalletDatabaseConstants.BANK_MONEY_ACCOUNTS_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(BankMoneyWalletDatabaseConstants.BANK_MONEY_TOTAL_BALANCES_FIRST_KEY_COLUMN);

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