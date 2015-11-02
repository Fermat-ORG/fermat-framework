package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantDeleteCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_sale.interfaces.CustomerBrokerSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerSaleInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerSaleContractDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerSaleContractDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerSaleContractDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerSaleContractDatabaseFactory customerBrokerSaleContractDatabaseFactory = new CustomerBrokerSaleContractDatabaseFactory(pluginDatabaseSystem);

                try {
                    database = customerBrokerSaleContractDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public CustomerBrokerSale createCustomerBrokerSale(
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
        ) throws CantCreateCustomerBrokerSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
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

                    return constructCustomerBrokerSaleContractFromRecord(recordToInsert);
            } catch (InvalidParameterException e) {
                throw new CantCreateCustomerBrokerSaleException("An exception happened",e,"","");
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerSaleException("An exception happened",e,"","");
            }
    
        }

        public DatabaseTableRecord getCustomerBrokerSaleContractTable(){
            DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            return PurchaseContractTable.getEmptyRecord();
        }

        public void updateCustomerBrokerSale(
                UUID contractId,
                ContractStatus status
        ) throws CantupdateCustomerBrokerSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
                PurchaseContractTable.setUUIDFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);
    
                DatabaseTableRecord recordToUpdate = PurchaseContractTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_STATUS_COLUMN_NAME, status.getCode());
    
                PurchaseContractTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerSaleException("An exception happened",e,"","");
            }
    
        }

        public void deleteCustomerBrokerSale(
                UUID contractId
        ) throws CantDeleteCustomerBrokerSaleException {
    
            try {
                DatabaseTable PurchaseContractTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
                DatabaseTableRecord recordToDelete   = PurchaseContractTable.getEmptyRecord();
    
                recordToDelete.setUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
    
                PurchaseContractTable.deleteRecord(recordToDelete);
            } catch (CantDeleteRecordException e) {
                throw new CantDeleteCustomerBrokerSaleException("An exception happened",e,"","");
            }
    
        }

        public List<CustomerBrokerSale> getAllCustomerBrokerSaleFromCurrentDeviceUser() throws CantGetListCustomerBrokerSaleException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            try {
                identityTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerSaleException(CantGetListCustomerBrokerSaleException.DEFAULT_MESSAGE,e,"","");
            }
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            List<CustomerBrokerSale> PurchaseContracts = new ArrayList<>();
    
            for (DatabaseTableRecord record : records) {
                try {
                    PurchaseContracts.add(constructCustomerBrokerSaleContractFromRecord(record));
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerSaleException(CantGetListCustomerBrokerSaleException.DEFAULT_MESSAGE,e,"","");
                }
            }
    
            return PurchaseContracts;
        }

        public CustomerBrokerSale getCustomerBrokerSaleForContractId(UUID ContractId) throws CantGetListCustomerBrokerSaleException {
            DatabaseTable identityTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_TABLE_NAME);
            identityTable.setUUIDFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, ContractId, DatabaseFilterType.EQUAL);
            try {
                identityTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerSaleException(CantGetListCustomerBrokerSaleException.DEFAULT_MESSAGE,e,"","");
            }
    
            List<DatabaseTableRecord> records = identityTable.getRecords();
            identityTable.clearAllFilters();
    
            CustomerBrokerSale PurchaseContract = null;
    
            for (DatabaseTableRecord record : records) {
                try {
                    PurchaseContract = constructCustomerBrokerSaleContractFromRecord(record);
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerSaleException(CantGetListCustomerBrokerSaleException.DEFAULT_MESSAGE,e,"","");
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

            databaseTableRecord.setUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME, contractId);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME, merchandiseAmount);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME, merchandiseCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_REFERENCE_PRICE_COLUMN_NAME, referencePrice);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_REFERENCE_CURRENCY_COLUMN_NAME, referenceCurrency.getCode());
            databaseTableRecord.setFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_AMOUNT_COLUMN_NAME, paymentAmount);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_CURRENCY_COLUMN_NAME, paymentCurrency.getCode());
            databaseTableRecord.setLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME, paymentExpirationDate);
            databaseTableRecord.setLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME, merchandiseDeliveryExpirationDate);

        }

        private CustomerBrokerSale constructCustomerBrokerSaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
    
            UUID                contractId                              = record.getUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CONTRACT_ID_COLUMN_NAME);
            String              customerPublicKey                       = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String              brokerPublicKey                         = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            CurrencyType        paymentCurrency                         = CurrencyType.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_CURRENCY_COLUMN_NAME));
            CurrencyType        merchandiseCurrency                     = CurrencyType.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME));
            float               referencePrice                          = record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_REFERENCE_PRICE_COLUMN_NAME);
            ReferenceCurrency   referenceCurrency                       = ReferenceCurrency.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_REFERENCE_CURRENCY_COLUMN_NAME));
            float               paymentAmount                           = record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_AMOUNT_COLUMN_NAME);
            float               merchandiseAmount                       = record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
            long                paymentExpirationDate                   = record.getLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_PAYMENT_EXPIRATION_DATE_COLUMN_NAME);
            long                merchandiseDeliveryExpirationDate       = record.getLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_MERCHANDISE_DELIVERY_EXPIRATION_DATE_COLUMN_NAME);
            ContractStatus      status                                  = ContractStatus.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACT_SALE_STATUS_COLUMN_NAME));
    
    
            return new CustomerBrokerSaleInformation(
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
