package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantCreateCustomerBrokerBankMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantDeleteCustomerBrokerBankMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.exceptions.CantupdateCustomerBrokerBankMoneySaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_sale.interfaces.CustomerBrokerBankMoneySale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankMoneySaleContractDaoException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_sale.developer.bitdubai.version_1.structure.CustomerBrokerBankMoneySaleContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 29/9/15.
 */

public class CustomerBrokerBankMoneySaleContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomerBrokerBankMoneySaleContractDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomerBrokerBankMoneySaleContractDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerBankMoneySaleContractDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomerBrokerBankMoneySaleContractDaoException(CantInitializeCustomerBrokerBankMoneySaleContractDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public CustomerBrokerBankMoneySale createCustomerBrokerBankMoneySale(
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
    ) throws CantCreateCustomerBrokerBankMoneySaleException {

        try {
            DatabaseTable BankMoneySaleContractTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = BankMoneySaleContractTable.getEmptyRecord();

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

            BankMoneySaleContractTable.insertRecord(recordToInsert);

            return null;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerBankMoneySaleException("An exception happened",e,"","");
        }

    }

    public DatabaseTableRecord getCustomerBrokerBankMoneySaleContractTable(){
        DatabaseTable BankMoneySaleContractTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
        return BankMoneySaleContractTable.getEmptyRecord();
    }

    public void updateCustomerBrokerBankMoneySale(
            UUID contractId,
            DatabaseTableRecord recordToUpdate
    ) throws CantupdateCustomerBrokerBankMoneySaleException {

        try {
            DatabaseTable BankMoneySaleContractTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
            BankMoneySaleContractTable.setUUIDFilter(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);

            BankMoneySaleContractTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantupdateCustomerBrokerBankMoneySaleException("An exception happened",e,"","");
        }

    }

    public void deleteCustomerBrokerBankMoneySale(
            UUID contractId
    ) throws CantDeleteCustomerBrokerBankMoneySaleException {

        try {
            DatabaseTable BankMoneySaleContractTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = BankMoneySaleContractTable.getEmptyRecord();

            recordToDelete.setUUIDValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);

            BankMoneySaleContractTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerBrokerBankMoneySaleException("An exception happened",e,"","");
        }

    }

    public List<CustomerBrokerBankMoneySale> getAllCustomerBrokerBankMoneySaleFromCurrentDeviceUser() throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        List<CustomerBrokerBankMoneySale> bankMoneySaleContracts = new ArrayList<>();

        for (DatabaseTableRecord record : records) {
            bankMoneySaleContracts.add(constructCustomerBrokerBankMoneySaleContractFromRecord(record));
        }

        return bankMoneySaleContracts;
    }

    public CustomerBrokerBankMoneySale getCustomerBrokerBankMoneySaleForContractId(UUID ContractId) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable identityTable = this.database.getTable(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_TABLE_NAME);
        identityTable.setUUIDFilter(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
        identityTable.loadToMemory();

        List<DatabaseTableRecord> records = identityTable.getRecords();
        identityTable.clearAllFilters();

        CustomerBrokerBankMoneySale bankMoneySaleContract = null;

        for (DatabaseTableRecord record : records) {
            bankMoneySaleContract = constructCustomerBrokerBankMoneySaleContractFromRecord(record);
        }

        return bankMoneySaleContract;
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

        databaseTableRecord.setUUIDValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
        databaseTableRecord.setFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
        databaseTableRecord.setStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
        databaseTableRecord.setLongValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
        databaseTableRecord.setLongValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

    }

    private CustomerBrokerBankMoneySaleContract constructCustomerBrokerBankMoneySaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                contractId                              = record.getUUIDValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CONTRACT_ID_COLUMN_NAME);
        String              customerPublicKey                       = record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String              brokerPublicKey                         = record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
        CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
        float               referencePrice                          = record.getFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_REFERENCE_PRICE_COLUMN_NAME);
        ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_REFERENCE_CURRENCY_COLUMN_NAME));
        float               paymentAmount                           = record.getFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_AMOUNT_COLUMN_NAME);
        float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
        long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
        ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerBankMoneySaleContractDatabaseConstants.CONTRACT_CUSTOMER_BROKER_BANK_MONEY_SALE_STATUS_COLUMN_NAME));


        return new CustomerBrokerBankMoneySaleContract(
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