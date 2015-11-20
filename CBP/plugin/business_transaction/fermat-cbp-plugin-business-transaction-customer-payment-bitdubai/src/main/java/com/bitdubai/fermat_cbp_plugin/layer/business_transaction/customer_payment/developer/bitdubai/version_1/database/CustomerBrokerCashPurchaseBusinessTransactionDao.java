package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_cash_purchase.interfaces.CustomerBrokerCashPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.structure.CustomerBrokerCashPurchaseBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions.CantInsertRecordCustomerBrokerCashPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions.CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_cash_purchase.developer.bitdubai.version_1.exceptions.CustomerBrokerCashPurchaseBusinessTransactionInconsistentTableStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CustomerBrokerCashPurchaseBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CustomerBrokerCashPurchaseBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCashPurchaseBusinessTransactionDatabaseFactory databaseFactory = new CustomerBrokerCashPurchaseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerCashPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCustomerBrokerCashPurchase(
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CashCurrencyType cashCurrencyType
    ) throws CantInsertRecordCustomerBrokerCashPurchaseBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, contractId, publicKeyBroker, publicKeyCustomer, paymentTransactionId, paymentCurrency, merchandiseCurrency, merchandiseAmount, executionTransactionId, cashCurrencyType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCustomerBrokerCashPurchaseBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCustomerBrokerCashPurchaseBusinessTransactionException(CantInsertRecordCustomerBrokerCashPurchaseBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCustomerBrokerCashPurchase(CustomerBrokerCashPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCustomerBrokerCashPurchaseBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CustomerBrokerCashPurchase> getAllCustomerBrokerCashPurchaseListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CustomerBrokerCashPurchase> CustomerBrokerCashPurchase = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CustomerBrokerCashPurchase.add(constructCustomerBrokerCashPurchaseFromRecord(record));
        }
        return CustomerBrokerCashPurchase;
    }


    private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            BusinessTransactionStatus transactionStatus,
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            CashCurrencyType cashCurrencyType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, paymentTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_CASH_CURRENCY_TYPE_COLUMN_NAME, cashCurrencyType.getCode());
    }

    private void setToState(CustomerBrokerCashPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CustomerBrokerCashPurchaseBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CustomerBrokerCashPurchaseBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CustomerBrokerCashPurchaseBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CustomerBrokerCashPurchase constructCustomerBrokerCashPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_TRANSACTION_ID_COLUMN_NAME);
        UUID                        contractId              = record.getUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                      CustomerPublicKey       = record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        UUID                        paymentTransactionId    = record.getUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        CurrencyType                paymentCurrency         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        CashCurrencyType            cashCurrencyType        = CashCurrencyType.getByCode(record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_CASH_CURRENCY_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CustomerBrokerCashPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CASH_PURCHASE_STATUS_COLUMN_NAME));

        return new CustomerBrokerCashPurchaseBusinessTransactionImpl(
                transactionId,
                contractId,
                brokerPublicKey,
                CustomerPublicKey,
                paymentTransactionId,
                paymentCurrency,
                merchandiseCurrency,
                merchandiseAmount,
                executionTransactionId,
                cashCurrencyType,
                status
        );
    }
}
