package com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantCreateCustomerRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantDeleteCustomerRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantListCustomersException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantModifyCustomerRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.interfaces.BrokerCustomerRelationship;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.exceptions.CantInitializeCustomersMiddlewareDaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 28/9/15.
 */
public class CustomersMiddlewareDao {

    private Database             database;
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
        Builders
     */

    public CustomersMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
        Public methods
     */

    public void initialize(UUID pluginId) throws CantInitializeCustomersMiddlewareDaoException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
        } catch (DatabaseNotFoundException e) {

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomersMiddlewareDaoException("I couldn't open the database", cantOpenDatabaseException, "Database Name: " + CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME, "");
        } catch (Exception exception) {
            throw new CantInitializeCustomersMiddlewareDaoException(CantInitializeCustomersMiddlewareDaoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public void createCustomerRelationship(CryptoBrokerIdentity brokerIdentity, CryptoCustomerIdentity customerIdentity, CustomerType customerType) throws CantCreateCustomerRelationshipException{
        try {
            DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
            DatabaseTableRecord recordToInsert   = customersTable.getEmptyRecord();

            loadRecordAsNew(recordToInsert, brokerIdentity, customerIdentity, customerType);

            customersTable.insertRecord(recordToInsert);
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerRelationshipException("An exception happened",e,"","");
        }
    }

    public void removeCustomerRelationship(UUID RelationshipId) throws CantDeleteCustomerRelationshipException{
        try {
            DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
            DatabaseTableRecord recordToDelete   = customersTable.getEmptyRecord();
            loadRecordToDelete(recordToDelete, RelationshipId);
            customersTable.deleteRecord(recordToDelete);
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteCustomerRelationshipException("An exception happened",e,"","");
        }
    }

    public void  modifyCustomerRelationship(BrokerCustomerRelationship brokerCustomerRelationship) throws CantModifyCustomerRelationshipException{
        try {
            DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
            DatabaseTableRecord recordToUpdate   = customersTable.getEmptyRecord();
            loadRecordToUpdate(recordToUpdate, brokerCustomerRelationship);
            customersTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantModifyCustomerRelationshipException("An exception happened",e,"","");
        }
    }

    public List<BrokerCustomerRelationship> getAllBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListCustomersException, CantLoadTableToMemoryException, InvalidParameterException {

        List<BrokerCustomerRelationship> brokerCustomers = new ArrayList<>();

        DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerIdentity.getPublicKey(), DatabaseFilterType.EQUAL);

        customersTable.loadToMemory();

        List<DatabaseTableRecord> records = customersTable.getRecords();
        customersTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            brokerCustomers.add(createBrokerCustomerRelationshipCustomers(record));
        }
        return brokerCustomers;
    }

    public List<BrokerCustomerRelationship> getSporadicBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListCustomersException, CantLoadTableToMemoryException, InvalidParameterException {

        List<BrokerCustomerRelationship> brokerCustomers = new ArrayList<>();

        DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CUSTOMER_TYPE_COLUMN_NAME, CustomerType.SPORADIC.getCode(), DatabaseFilterType.EQUAL);

        customersTable.loadToMemory();

        List<DatabaseTableRecord> records = customersTable.getRecords();
        customersTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            brokerCustomers.add(createBrokerCustomerRelationshipCustomers(record));
        }
        return brokerCustomers;
    }

    public List<BrokerCustomerRelationship> getFrequentBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListCustomersException, CantLoadTableToMemoryException, InvalidParameterException {

        List<BrokerCustomerRelationship> brokerCustomers = new ArrayList<>();

        DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CUSTOMER_TYPE_COLUMN_NAME, CustomerType.FREQUENT.getCode(), DatabaseFilterType.EQUAL);

        customersTable.loadToMemory();

        List<DatabaseTableRecord> records = customersTable.getRecords();
        customersTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            brokerCustomers.add(createBrokerCustomerRelationshipCustomers(record));
        }
        return brokerCustomers;
    }

    public List<BrokerCustomerRelationship> searchBrokerCustomerByName(CryptoBrokerIdentity brokerIdentity,CryptoCustomerIdentity customerIdentity) throws CantListCustomersException, CantLoadTableToMemoryException, InvalidParameterException {

        List<BrokerCustomerRelationship> brokerCustomers = new ArrayList<>();

        DatabaseTable customersTable = this.database.getTable(CustomersMiddlewareDatabaseConstants.CUSTOMERS_TABLE_NAME);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
        customersTable.setStringFilter(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, customerIdentity.getPublicKey(), DatabaseFilterType.EQUAL);

        customersTable.loadToMemory();

        List<DatabaseTableRecord> records = customersTable.getRecords();
        customersTable.clearAllFilters();

        for (DatabaseTableRecord record : records) {
            brokerCustomers.add(createBrokerCustomerRelationshipCustomers(record));
        }
        return brokerCustomers;
    }


    /*
        Methods Private
     */

    private void loadRecordAsNew(DatabaseTableRecord databaseTableRecord, CryptoBrokerIdentity brokerIdentity, CryptoCustomerIdentity customerIdentity, CustomerType customerType) {
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerIdentity.getPublicKey());
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, customerIdentity.getPublicKey());
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CUSTOMER_TYPE_COLUMN_NAME, customerType.getCode());
    }

    private void loadRecordToDelete(DatabaseTableRecord databaseTableRecord, UUID RelationshipId) {
        databaseTableRecord.setUUIDValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_RELATIONSHIP_ID_COLUMN_NAME, RelationshipId);
    }

    private void loadRecordToUpdate(DatabaseTableRecord databaseTableRecord, BrokerCustomerRelationship brokerCustomerRelationship) {
        databaseTableRecord.setUUIDValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_RELATIONSHIP_ID_COLUMN_NAME, brokerCustomerRelationship.getRelationshipId());
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, brokerCustomerRelationship.getCryptoBroker().getPublicKey());
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, brokerCustomerRelationship.getCryptoCustomer().getPublicKey());
        databaseTableRecord.setStringValue(CustomersMiddlewareDatabaseConstants.CUSTOMERS_CUSTOMER_TYPE_COLUMN_NAME, brokerCustomerRelationship.getCustomerType().getCode());
    }

    private BrokerCustomerRelationship createBrokerCustomerRelationshipCustomers(DatabaseTableRecord record) throws InvalidParameterException {
        return null;
    }
}
