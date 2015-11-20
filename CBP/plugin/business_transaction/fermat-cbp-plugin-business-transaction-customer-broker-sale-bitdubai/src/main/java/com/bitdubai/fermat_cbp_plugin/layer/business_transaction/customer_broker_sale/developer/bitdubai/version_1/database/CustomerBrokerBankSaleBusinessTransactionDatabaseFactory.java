package   com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.database.Customer Broker Bank SaleBusinessTransactionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CustomerBrokerBankSaleBusinessTransactionDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CustomerBrokerBankSaleBusinessTransactionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
             * Create Customer Broker Bank Sale table.
             */
            table = databaseFactory.newTableFactory(ownerId, CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TABLE_NAME);

            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_CONTRACT_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_OPERATION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_FIRST_KEY_COLUMN);

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