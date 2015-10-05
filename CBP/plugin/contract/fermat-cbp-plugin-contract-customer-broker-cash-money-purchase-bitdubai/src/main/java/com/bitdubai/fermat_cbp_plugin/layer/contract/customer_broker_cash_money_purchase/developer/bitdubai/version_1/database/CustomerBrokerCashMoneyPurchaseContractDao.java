package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantCreateCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantDeleteCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.exceptions.CantupdateCustomerBrokerCashMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_cash_money_purchase.interfaces.CustomerBrokerCashMoneyPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCashMoneyPurchaseContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_purchase.developer.bitdubai.version_1.structure.CustomerBrokerCashMoneyPurchaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerCashMoneyPurchaseContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerCashMoneyPurchaseContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerCashMoneyPurchaseContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerCashMoneyPurchaseContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerCashMoneyPurchaseContractDaoException(CantInitializeCustomerBrokerCashMoneyPurchaseContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerCashMoneyPurchase createCustomerBrokerCashMoneyPurchase(
            String publicKeyCustomer,
            String publicKeyBroker,
            float merchandiseAmount,
            CurrencyType merchandiseCurrency,
            float referencePrice,
            ReferenceCurrency referenceCurrency,
            float paymentAmount,
            CurrencyType paymentCurrency,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate
    ) throws CantCreateCustomerBrokerCashMoneyPurchaseException {

        try {
            DatabaseTable CashMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = CashMoneyPurchaseContractTable.getEmptyRecord();

            UUID contractID = UUID.randomUUID();

            loadRecordAsNew(
                    recordToInsert,
                    contractID,
                    publicKeyCustomer,
                    publicKeyBroker,
                    merchandiseAmount,
                    merchandiseCurrency,
                    referencePrice,
                    referenceCurrency,
                    paymentAmount,
                    paymentCurrency,
                    paymentExpirationDate,
                    merchandiseDeliveryExpirationDate
            );

            CashMoneyPurchaseContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerCashMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerCashMoneyPurchaseContractTable(){
        DatabaseTable CashMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
        return CashMoneyPurchaseContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerCashMoneyPurchase(
            UUID contractId,
            String status
    ) throws CantupdateCustomerBrokerCashMoneyPurchaseException {

        try {
            DatabaseTable CashMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
            CashMoneyPurchaseContractTable.setUUIDFilter(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate = CashMoneyPurchaseContractTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_STATUS_COLUMN_NAME, status);

            CashMoneyPurchaseContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerCashMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerCashMoneyPurchase(
            UUID contractId
    ) throws CantDeleteCustomerBrokerCashMoneyPurchaseException {

        try {
            DatabaseTable CashMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = CashMoneyPurchaseContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);

            CashMoneyPurchaseContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerCashMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerCashMoneyPurchase> getAllCustomerBrokerCashMoneyPurchaseFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerCashMoneyPurchase> CashMoneyPurchaseContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            CashMoneyPurchaseContracts.add(constructCustomerBrokerCashMoneyPurchaseContractFromRecord(record));
        }

        return CashMoneyPurchaseContracts;
    }

    public CustomerBrokerCashMoneyPurchase getCustomerBrokerCashMoneyPurchaseForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerCashMoneyPurchase CashMoneyPurchaseContract = null;

        for (DatabaseTableRecord record : records) {
            CashMoneyPurchaseContract = constructCustomerBrokerCashMoneyPurchaseContractFromRecord(record);
        }

        return CashMoneyPurchaseContract;
    }

    /*
        Methods Private
     */

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord,
                                 UUID contractId,
                                 String publicKeyCustomer,
                                 String publicKeyBroker,
                                 float merchandiseAmount,
                                 CurrencyType merchandiseCurrency,
                                 float referencePrice,
                                 ReferenceCurrency referenceCurrency,
                                 float paymentAmount,
                                 CurrencyType paymentCurrency,
                                 long paymentExpirationDate,
                                 long merchandiseDeliveryExpirationDate) {

        databaseTableRecord.setUUIDValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerCashMoneyPurchaseContract constructCustomerBrokerCashMoneyPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerCashMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_CASH_MONEY_PURCHASE_STATUS_COLUMN_NAME));


        return new CustomerBrokerCashMoneyPurchaseContract(
                contractId,
                customerPublicKey,
                brokerPublicKey,
                paymentCurrency,
                merchandiseCurrency,
                referencePrice,
                referenceCurrency,
                paymentAmount,
                merchandiseAmount,
                paymentExpirationDate,
                merchandiseDeliveryExpirationDate,
                status
        );
    }

}