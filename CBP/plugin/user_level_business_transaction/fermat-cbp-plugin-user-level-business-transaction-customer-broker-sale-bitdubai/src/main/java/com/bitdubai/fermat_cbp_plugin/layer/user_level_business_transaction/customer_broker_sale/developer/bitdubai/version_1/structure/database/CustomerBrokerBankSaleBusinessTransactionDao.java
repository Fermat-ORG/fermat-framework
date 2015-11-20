package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_bank_sale.interfaces.CustomerBrokerBankSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.structure.CustomerBrokerBankSaleBusinessTransactionImpl;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions.CantInsertRecordCustomerBrokerBankSaleBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions.CantUpdateStatusCustomerBrokerBankSaleBusinessTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions.CustomerBrokerBankSaleBusinessTransactionInconsistentTableStateException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 27.09.15.
 */
public class CustomerBrokerBankSaleBusinessTransactionDao {
    private Database database;

    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public CustomerBrokerBankSaleBusinessTransactionDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerBankSaleBusinessTransactionDatabaseFactory databaseFactory = new CustomerBrokerBankSaleBusinessTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*CREATE NEW TRANSACTION*/
    public void createNewCustomerBrokerBankSale(
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
    ) throws CantInsertRecordCustomerBrokerBankSaleBusinessTransactionException {
        try {
            DatabaseTable transactionTable = this.database.getTable(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = transactionTable.getEmptyRecord();
            BusinessTransactionStatus transactionStatus = BusinessTransactionStatus.PENDING_PAYMENT;
            loadRecordAsNew(recordToInsert, transactionStatus, contractId, publicKeyBroker, publicKeyCustomer, paymentTransactionId, paymentCurrency, merchandiseCurrency, merchandiseAmount, executionTransactionId, bankCurrencyType, bankOperationType);
            transactionTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantInsertRecordCustomerBrokerBankSaleBusinessTransactionException("An exception happened", e, "", "");
        } catch (Exception exception) {
            throw new CantInsertRecordCustomerBrokerBankSaleBusinessTransactionException(CantInsertRecordCustomerBrokerBankSaleBusinessTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    /*UPDATE STATUS TRANSACTION*/
    public void updateTransactionStatusCustomerBrokerBankSale(CustomerBrokerBankSaleBusinessTransactionImpl businessTransaction, BusinessTransactionStatus transactionStatus) throws CantUpdateStatusCustomerBrokerBankSaleBusinessTransactionException {
        try {
            setToState(businessTransaction, transactionStatus);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantUpdateStatusCustomerBrokerBankSaleBusinessTransactionException("An exception happened",exception,"","");
        } catch (Exception exception) {
            throw new CantUpdateStatusCustomerBrokerBankSaleBusinessTransactionException("An unexpected exception happened", FermatException.wrapException(exception), null, null);
        }
    }

    /*GENERATE LIST TRANSACTION*/
    public List<CustomerBrokerBankSale> getAllCustomerBrokerBankSaleListFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TABLE_NAME);
        identityTable.loadToMemory();
        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();
        List<CustomerBrokerBankSale> CustomerBrokerBankSale = new ArrayList<>();
        for (DatabaseTableRecord record : records) {
            CustomerBrokerBankSale.add(constructCustomerBrokerBankSaleFromRecord(record));
        }
        return CustomerBrokerBankSale;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TRANSACTION_ID_COLUMN_NAME, transactionId);
        databaseTableRecord.setUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME, paymentTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME, executionTransactionId);
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_CURRENCY_TYPE_COLUMN_NAME, bankCurrencyType.getCode());
        databaseTableRecord.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_OPERATION_TYPE_COLUMN_NAME, bankOperationType.getCode());
    }

    private void setToState(CustomerBrokerBankSaleBusinessTransactionImpl businessTransaction, BusinessTransactionStatus status) throws CantUpdateRecordException, CustomerBrokerBankSaleBusinessTransactionInconsistentTableStateException, CantLoadTableToMemoryException {
        DatabaseTable       transactionTable = this.database.getTable(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TABLE_NAME);
        DatabaseTableRecord recordToUpdate   = getByPrimaryKey(businessTransaction.getTransactionId());
        recordToUpdate.setStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_STATUS_COLUMN_NAME, status.getCode());
        transactionTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getByPrimaryKey(UUID id) throws CantLoadTableToMemoryException, CustomerBrokerBankSaleBusinessTransactionInconsistentTableStateException {
        DatabaseTable transactionTable = this.database.getTable(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TABLE_NAME);
        List<DatabaseTableRecord> records;

        transactionTable.setStringFilter(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TRANSACTION_ID_COLUMN_NAME, id.toString(), DatabaseFilterType.EQUAL);
        transactionTable.loadToMemory();
        records = transactionTable.getRecords();

        if (records.size() != 1)
            throw new CustomerBrokerBankSaleBusinessTransactionInconsistentTableStateException("The number of records with a primary key is different thatn one ", null, "The id is: " + id.toString(), "");

        return records.get(0);
    }

    private CustomerBrokerBankSale constructCustomerBrokerBankSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                        transactionId           = record.getUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_TRANSACTION_ID_COLUMN_NAME);
        UUID                        contractId              = record.getUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_CONTRACT_ID_COLUMN_NAME);
        String                      brokerPublicKey         = record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String                      CustomerPublicKey       = record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        UUID                        paymentTransactionId    = record.getUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        CurrencyType                paymentCurrency         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType                merchandiseCurrency     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float                       merchandiseAmount       = record.getFloatValue(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME));
        UUID                        executionTransactionId  = record.getUUIDValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        BankCurrencyType            bankCurrencyType        = BankCurrencyType.getByCode(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_CURRENCY_TYPE_COLUMN_NAME));
        BankOperationType           bankOperationType       = BankOperationType.getByCode(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_BANK_OPERATION_TYPE_COLUMN_NAME));
        BusinessTransactionStatus   status                  = BusinessTransactionStatus.getByCode(record.getStringValue(CustomerBrokerBankSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_BANK_SALE_STATUS_COLUMN_NAME));

        return new CustomerBrokerBankSaleBusinessTransactionImpl(
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
