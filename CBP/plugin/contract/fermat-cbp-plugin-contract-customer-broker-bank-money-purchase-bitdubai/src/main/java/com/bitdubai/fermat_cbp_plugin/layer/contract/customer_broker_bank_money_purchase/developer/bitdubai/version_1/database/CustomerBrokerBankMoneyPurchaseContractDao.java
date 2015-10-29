package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantCreateCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantDeleteCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions.CantupdateCustomerBrokerBankMoneyPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.interfaces.CustomerBrokerBankMoneyPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_purchase.developer.bitdubai.version_1.structure.CustomerBrokerBankMoneyPurchaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerBankMoneyPurchaseContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerBankMoneyPurchaseContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException(CantInitializeCustomerBrokerBankMoneyPurchaseContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerBankMoneyPurchase createCustomerBrokerBankMoneyPurchase(
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
    ) throws CantCreateCustomerBrokerBankMoneyPurchaseException {

        try {
            DatabaseTable BankMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = BankMoneyPurchaseContractTable.getEmptyRecord();

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

            BankMoneyPurchaseContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerBankMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerBankMoneyPurchaseContractTable(){
        DatabaseTable BankMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
        return BankMoneyPurchaseContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerBankMoneyPurchase(
            UUID contractId,
            ContractStatus status
    ) throws CantupdateCustomerBrokerBankMoneyPurchaseException {

        try {
            DatabaseTable BankMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
            BankMoneyPurchaseContractTable.setUUIDFilter(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            DatabaseTableRecord recordToUpdate = BankMoneyPurchaseContractTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_STATUS_COLUMN_NAME, status.getCode());

            BankMoneyPurchaseContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerBankMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerBankMoneyPurchase(
            UUID contractId
    ) throws CantDeleteCustomerBrokerBankMoneyPurchaseException {

        try {
            DatabaseTable BankMoneyPurchaseContractTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = BankMoneyPurchaseContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);

            BankMoneyPurchaseContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerBankMoneyPurchaseException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerBankMoneyPurchase> getAllCustomerBrokerBankMoneyPurchaseFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerBankMoneyPurchase> bankMoneyPurchaseContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            bankMoneyPurchaseContracts.add(constructCustomerBrokerBankMoneyPurchaseContractFromRecord(record));
        }

        return bankMoneyPurchaseContracts;
    }

    public CustomerBrokerBankMoneyPurchase getCustomerBrokerBankMoneyPurchaseForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerBankMoneyPurchase bankMoneyPurchaseContract = null;

        for (DatabaseTableRecord record : records) {
            bankMoneyPurchaseContract = constructCustomerBrokerBankMoneyPurchaseContractFromRecord(record);
        }

        return bankMoneyPurchaseContract;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerBankMoneyPurchaseContract constructCustomerBrokerBankMoneyPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerBankMoneyPurchaseContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_PURCHASE_STATUS_COLUMN_NAME));


        return new CustomerBrokerBankMoneyPurchaseContract(
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