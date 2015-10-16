package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_purchase.interfaces.CustomerBrokerBankPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.structure.CustomerBrokerBankPurchaseBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions.CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions.CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions.CustomerBrokerBankPurchaseBusinessTransactionInconsistentTableStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CustomerBrokerBankPurchaseBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CustomerBrokerBankPurchaseBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerBankPurchaseBusinessTransactionDatabaseFactory databaseFactory = new CustomerBrokerBankPurchaseBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerBankPurchaseBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCustomerBrokerBankPurchase(
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            String executionTransactionId,
            BankCurrencyType bankCurrencyType,
            BankOperationType bankOperationType
    ) throws CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, contractId, publicKeyBroker, publicKeyCustomer, paymentTransactionId, paymentCurrency, merchandiseCurrency, merchandiseAmount, executionTransactionId, bankCurrencyType, bankOperationType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException(CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCustomerBrokerBankPurchase(CustomerBrokerBankPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CustomerBrokerBankPurchase> getAllCustomerBrokerBankPurchaseListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CustomerBrokerBankPurchase> CustomerBrokerBankPurchase = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CustomerBrokerBankPurchase.add(constructCustomerBrokerBankPurchaseFromRecord(record));
        }
        return CustomerBrokerBankPurchase;
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
            BankCurrencyType bankCurrencyType,
            BankOperationType bankOperationType
    ) {
        UUID transactionId = UUID.randomUUID();

        databaseTableRecord.setUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, paymentTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_BANK_CURRENCY_TYPE_COLUMN_NAME, bankCurrencyType.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_BANK_OPERATION_TYPE_COLUMN_NAME, bankOperationType.getCode());
    }

    private void setToState(CustomerBrokerBankPurchaseBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CustomerBrokerBankPurchaseBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CustomerBrokerBankPurchaseBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CustomerBrokerBankPurchaseBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CustomerBrokerBankPurchase constructCustomerBrokerBankPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_TRANSACTION_ID_COLUMN_NAME);
        UUID                        contractId              = record.getUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                      CustomerPublicKey       = record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        UUID                        paymentTransactionId    = record.getUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        CurrencyType                paymentCurrency         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        BankCurrencyType            bankCurrencyType        = BankCurrencyType.getByCode(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_BANK_CURRENCY_TYPE_COLUMN_NAME));
        BankOperationType           bankOperationType       = BankOperationType.getByCode(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_BANK_OPERATION_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CustomerBrokerBankPurchaseBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_PURCHASE_STATUS_COLUMN_NAME));

        return new CustomerBrokerBankPurchaseBusinessTransactionImpl(
                transactionId,
                contractId,
                brokerPublicKey,
                CustomerPublicKey,
                paymentTransactionId,
                paymentCurrency,
                merchandiseCurrency,
                merchandiseAmount,
                executionTransactionId,
                bankCurrencyType,
                bankOperationType,
                status
        );
    }
}
