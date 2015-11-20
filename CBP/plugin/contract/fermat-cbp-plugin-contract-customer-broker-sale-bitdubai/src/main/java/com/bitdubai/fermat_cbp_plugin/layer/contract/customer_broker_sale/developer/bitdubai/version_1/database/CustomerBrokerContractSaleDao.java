package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerContractSaleDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerContractSaleInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerContractSaleDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerContractSaleDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerContractSaleDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerContractSaleDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerContractSaleDatabaseFactory CustomerBrokerContractSaleDatabaseFactory = new CustomerBrokerContractSaleDatabaseFactory(pluginDatabaseSystem);

                try {
                    database = CustomerBrokerContractSaleDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerContractSaleDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public CustomerBrokerContractSale createCustomerBrokerContractSale(
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
        ) throws CantCreateCustomerBrokerContractSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = PurchaseContractTable.getEmptyRecord();
    
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
    
                PurchaseContractTable.insertRecord(recordToInsert);

                    return constructCustomerBrokerContractSaleFromRecord(recordToInsert);
            } catch (InvalidParameterException e) {
                throw new CantCreateCustomerBrokerContractSaleException("An exception happened",e,"","");
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerContractSaleException("An exception happened",e,"","");
            }
    
        }

        public DatabaseTableRecord getCustomerBrokerContractSaleTable(){
            DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            return PurchaseContractTable.getEmptyRecord();
        }

        public void updateCustomerBrokerContractSale(
                UUID contractId,
                ContractStatus status
        ) throws CantupdateCustomerBrokerContractSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
                PurchaseContractTable.setUUIDFilter(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);
    
                DatabaseTableRecord recordToUpdate = PurchaseContractTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_STATUS_COLUMN_NAME, status.getCode());
    
                PurchaseContractTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractSaleException("An exception happened",e,"","");
            }
    
        }

        public void deleteCustomerBrokerContractSale(
                UUID contractId
        ) throws CantDeleteCustomerBrokerContractSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
                DatabaseTableRecord recordToDelete   = PurchaseContractTable.getEmptyRecord();
    
                recordToDelete.setUUIDValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
    
                PurchaseContractTable.deleteRecord(recordToDelete);
            } catch (CantDeleteRecordException e) {
                throw new CantDeleteCustomerBrokerContractSaleException("An exception happened",e,"","");
            }
    
        }

        public List<CustomerBrokerContractSale> getAllCustomerBrokerContractSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractSaleException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            try {
                identityTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE,e,"","");
            }
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            List<CustomerBrokerContractSale> PurchaseContracts = new ArrayList<>();
    
            for (DatabaseTableRecord record : records) {
                try {
                    PurchaseContracts.add(constructCustomerBrokerContractSaleFromRecord(record));
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE,e,"","");
                }
            }
    
            return PurchaseContracts;
        }

        public CustomerBrokerContractSale getCustomerBrokerContractSaleForContractId(UUID ContractId) throws CantGetListCustomerBrokerContractSaleException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            identityTable.setUUIDFilter(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
            try {
                identityTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE,e,"","");
            }
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            CustomerBrokerContractSale PurchaseContract = null;
    
            for (DatabaseTableRecord record : records) {
                try {
                    PurchaseContract = constructCustomerBrokerContractSaleFromRecord(record);
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE,e,"","");
                }
            }
    
            return PurchaseContract;
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

            databaseTableRecord.setUUIDValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
            databaseTableRecord.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
            databaseTableRecord.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
            databaseTableRecord.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
            databaseTableRecord.setStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
            databaseTableRecord.setLongValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
            databaseTableRecord.setLongValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

        }

        private CustomerBrokerContractSale constructCustomerBrokerContractSaleFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
    
            UUID                contractId                              = record.getUUIDValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME);
            String              customerPublicKey                       = record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String              brokerPublicKey                         = record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
            CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
            float               referencePrice                          = record.getFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_REFERENCE_PRICE_COLUMN_NAME);
            ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_REFERENCE_CURRENCY_COLUMN_NAME));
            float               paymentAmount                           = record.getFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_AMOUNT_COLUMN_NAME);
            float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
            long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
            long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
            ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerContractSaleDatabaseConstants.CONTRACT_SALE_STATUS_COLUMN_NAME));
    
    
            return new CustomerBrokerContractSaleInformation(
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
