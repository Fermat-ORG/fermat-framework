package   com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.database.Customer Broker Bank Money PurchaseContractDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerBankMoneyPurchaseContractDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CustomerBrokerBankMoneyPurchaseContractDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Contract Customer Broker Bank Money Purchase table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);

            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 20, Boolean.FALSE);

            table.addIndex(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_FIRST_KEY_COLUMN);

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