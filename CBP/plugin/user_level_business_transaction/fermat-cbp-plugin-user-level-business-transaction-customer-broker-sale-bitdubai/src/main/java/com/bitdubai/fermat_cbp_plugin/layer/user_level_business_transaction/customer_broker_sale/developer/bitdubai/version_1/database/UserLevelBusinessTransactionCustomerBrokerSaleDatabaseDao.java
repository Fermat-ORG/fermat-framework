package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.common.enums.TransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_purchase.interfaces.CustomerBrokerPurchaseEventRecord;
import com.bitdubai.fermat_cbp_api.layer.user_level_business_transaction.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions.MissingCustomerBrokerSaleDataException;
import com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.utils.CustomerBrokerSaleImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 15/12/15.
 */
public class UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao {
    Database database;
    UUID pluginId;
    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     */
    public UserLevelBusinessTransactionCustomerBrokerSaleDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    private DatabaseTable getDatabaseTable(String tableName) {

        return database.getTable(tableName);
    }

    private Database openDatabase() throws CantOpenDatabaseException, CantCreateDatabaseException {
        try {
            database = pluginDatabaseSystem.openDatabase(this.pluginId, UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_DATABASE_NAME);

        } catch (DatabaseNotFoundException e) {
            UserLevelBusinessTransactionCustomerBrokerSaleDatabaseFactory userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory = new UserLevelBusinessTransactionCustomerBrokerSaleDatabaseFactory(this.pluginDatabaseSystem);
            database = userLevelBusinessTransactionCustomerBrokerPurchaseDatabaseFactory.createDatabase(this.pluginId, UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_DATABASE_NAME);
        }
        return database;
    }

    private boolean isNewRecord(DatabaseTable table, DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
        table.loadToMemory();
        return table.getRecords().isEmpty();
    }

    public void saveCustomerBrokerSaleEventRecord(CustomerBrokerPurchaseEventRecord customerBrokerPurchaseEventRecord) throws DatabaseOperationException, MissingCustomerBrokerSaleDataException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord customerBrokerPurchaseRecord = getCustomerBrokerPurchaseRecord(customerBrokerPurchaseEventRecord);

            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(customerBrokerPurchaseEventRecord.getEventId().toString());
            filter.setColumn(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, customerBrokerPurchaseRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, customerBrokerPurchaseRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        } catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Customer Broker Purchase Event Record in the database.", null);
        }
    }

    public List<CustomerBrokerSale> getCustomerBrokerSales(DatabaseTableFilter filter) throws DatabaseOperationException, InvalidParameterException
    {
        Database database = null;
        List<CustomerBrokerSale> customerBrokerSales = new ArrayList<>();
        try{
            database = openDatabase();

            for (DatabaseTableRecord record : getCustomerBrokerSaleRecordData(filter))
            {
                final CustomerBrokerSale customerBrokerSale = getCustomerBrokerSale(record);
                customerBrokerSales.add(customerBrokerSale);
            }
        }
        catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "error trying to get customers Broker Purchase from the database with filter: " + filter.toString(), null);
        }

        return customerBrokerSales;
    }


    public void saveCustomerBrokerSaleTransactionData(CustomerBrokerSale customerBrokerSale) throws DatabaseOperationException, MissingCustomerBrokerSaleDataException {
        try
        {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();

            DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TABLE_NAME);
            DatabaseTableRecord customerBrokerPurchaseRecord = getCustomerBrokerSaleRecord(customerBrokerSale);
            DatabaseTableFilter filter = table.getEmptyTableFilter();
            filter.setType(DatabaseFilterType.EQUAL);
            filter.setValue(customerBrokerSale.getTransactionId().toString());
            filter.setColumn(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CONTRACT_TRANSACTION_ID_COLUMN_NAME);

            if (isNewRecord(table, filter))
                transaction.addRecordToInsert(table, customerBrokerPurchaseRecord);
            else {
                table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
                transaction.addRecordToUpdate(table, customerBrokerPurchaseRecord);
            }

            //I execute the transaction and persist the database side of the asset.
            database.executeTransaction(transaction);
            database.closeDatabase();

        }catch (Exception e) {
            if (database != null)
                database.closeDatabase();
            throw new DatabaseOperationException(DatabaseOperationException.DEFAULT_MESSAGE, e, "Error trying to save the Customer Broker Purchase Transaction in the database.", null);
        }
    }

    private List<DatabaseTableRecord> getCustomerBrokerSaleRecordData(DatabaseTableFilter filter) throws CantLoadTableToMemoryException {
        DatabaseTable table = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TABLE_NAME);

        if (filter != null)
            table.addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());

        table.loadToMemory();

        return table.getRecords();
    }

    private CustomerBrokerSale getCustomerBrokerSale(final DatabaseTableRecord record) throws CantLoadTableToMemoryException, DatabaseOperationException, InvalidParameterException
    {
        return new CustomerBrokerSaleImpl(
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_CONTRACT_TRANSACTION_ID_COLUMN_NAME),
                record.getLongValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_TIMESTAMP_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_PURCHASE_STATUS_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_CONTRACT_STATUS_COLUMN_NAME),
                TransactionStatus.getByCode(record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME)),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_CURRENCY_TYPE_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_TRANSACTION_TYPE_COLUMN_NAME),
                record.getStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.
                        CUSTOMER_BROKER_SALE_MEMO_COLUMN_NAME));
    }

    private DatabaseTableRecord getCustomerBrokerSaleRecord(CustomerBrokerSale customerBrokerSale
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_ID_COLUMN_NAME, customerBrokerSale.getTransactionId());
        record.setLongValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TIMESTAMP_COLUMN_NAME, customerBrokerSale.getTimestamp());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CONTRACT_TRANSACTION_ID_COLUMN_NAME, customerBrokerSale.getTransactionId());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_PURCHASE_STATUS_COLUMN_NAME, customerBrokerSale.getPurchaseStatus());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CONTRACT_STATUS_COLUMN_NAME, customerBrokerSale.getContractStatus());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_STATUS_COLUMN_NAME, customerBrokerSale.getTransactionStatus().getCode());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_CURRENCY_TYPE_COLUMN_NAME, customerBrokerSale.getCurrencyType());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_TRANSACTION_TYPE_COLUMN_NAME, customerBrokerSale.getTransactionType());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_MEMO_COLUMN_NAME, customerBrokerSale.getMemo());


        return record;
    }

    private DatabaseTableRecord getCustomerBrokerPurchaseRecord(CustomerBrokerPurchaseEventRecord customerBrokerPurchaseEventRecord
    ) throws DatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_ID_COLUMN_NAME, customerBrokerPurchaseEventRecord.getEventId());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_EVENT_COLUMN_NAME, customerBrokerPurchaseEventRecord.getEvent());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_SOURCE_COLUMN_NAME, customerBrokerPurchaseEventRecord.getSource());
        record.setStringValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_STATUS_COLUMN_NAME, customerBrokerPurchaseEventRecord.getStatus());
        record.setLongValue(UserLevelBusinessTransactionCustomerBrokerSaleConstants.CUSTOMER_BROKER_SALE_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, customerBrokerPurchaseEventRecord.getTimestamp());

        return record;
    }
}
