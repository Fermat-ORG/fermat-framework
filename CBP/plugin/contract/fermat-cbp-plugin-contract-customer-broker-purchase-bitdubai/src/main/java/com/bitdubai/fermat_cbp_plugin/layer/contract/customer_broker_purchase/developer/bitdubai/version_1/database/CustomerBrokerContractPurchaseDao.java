package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.ContractClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerContractPurchaseInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */

public class CustomerBrokerContractPurchaseDao {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerContractPurchaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseContractDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerPurchaseContractDatabaseFactory CustomerBrokerPurchaseContractDatabaseFactory = new CustomerBrokerPurchaseContractDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = CustomerBrokerPurchaseContractDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerPurchaseContractDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public CustomerBrokerContractPurchase createCustomerBrokerPurchaseContract(CustomerBrokerContractPurchase contract) throws CantCreateCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                DatabaseTableRecord recordToInsert = PurchaseTable.getEmptyRecord();;
                loadRecordAsNew(
                        recordToInsert,
                        contract.getContractId(),
                        contract.getNegotiatiotId(),
                        contract.getPublicKeyCustomer(),
                        contract.getPublicKeyBroker(),
                        contract.getDateTime(),
                        contract.getStatus()
                );
                PurchaseTable.insertRecord(recordToInsert);
                createCustomerBrokerPurchaseContractClauses(contract.getContractId(), contract.getContractClause());
                return constructCustomerBrokerPurchaseContractFromRecord(recordToInsert);
            } catch (InvalidParameterException e) {
                throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            } catch (CantGetListCustomerBrokerContractPurchaseException e) {
                throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            }
        }

        public void updateStatusCustomerBrokerPurchaseContract(
                String contractID,
                ContractStatus status
        ) throws CantupdateCustomerBrokerContractPurchaseException {

            try {
                DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                PurchaseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordToUpdate = PurchaseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
                PurchaseTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            }
        }

        public List<CustomerBrokerContractPurchase> getAllCustomerBrokerPurchaseContractFromCurrentDeviceUser() throws CantGetListCustomerBrokerContractPurchaseException {
            DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
            try {
                ContractPurchaseTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
            List<DatabaseTableRecord> records = ContractPurchaseTable.getRecords();
            ContractPurchaseTable.clearAllFilters();
            List<CustomerBrokerContractPurchase> Purchases = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                try {
                    Purchases.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
                }
            }
            return Purchases;
        }

        public CustomerBrokerContractPurchase getCustomerBrokerPurchaseContractForcontractID(String contractID) throws CantGetListCustomerBrokerContractPurchaseException {
            DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
            ContractPurchaseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            try {
                ContractPurchaseTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
            List<DatabaseTableRecord> records = ContractPurchaseTable.getRecords();
            ContractPurchaseTable.clearAllFilters();
            CustomerBrokerContractPurchase Purchase = null;
            for (DatabaseTableRecord record : records) {
                try {
                    Purchase = constructCustomerBrokerPurchaseContractFromRecord(record);
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
                }
            }
            return Purchase;
        }

    /*
    *   Methods Clauses
    * */

        public void createCustomerBrokerPurchaseContractClauses(String contractID, Collection<ContractClause> clauses) throws CantCreateCustomerBrokerContractPurchaseException{
            for(ContractClause clause : clauses){
                DatabaseTable ContractClausePurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractClausePurchaseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordToInsert = ContractClausePurchaseTable.getEmptyRecord();;
                loadRecordAsNewClause(
                        recordToInsert,
                        clause.getClauseId(),
                        contractID,
                        clause.getType(),
                        clause.getExecutionOrder(),
                        clause.getStatus()
                );
                try {
                    ContractClausePurchaseTable.insertRecord(recordToInsert);
                } catch (CantInsertRecordException e) {
                    throw new CantCreateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
                }
            }
        }

        public void updateStatusCustomerBrokerPurchaseContractClauseStatus(String contractID, ContractClause clause) throws CantupdateCustomerBrokerContractPurchaseException{
            DatabaseTable ContractPurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);

            ContractPurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            ContractPurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME, clause.getType().getCode(), DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordToUpdate = ContractPurchaseClauseTable.getEmptyRecord();

            recordToUpdate.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME, clause.getStatus().getCode());

            try {
                ContractPurchaseClauseTable.insertRecord(recordToUpdate);
            } catch (CantInsertRecordException e) {
                throw new CantupdateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            }
        }

        public List<ContractClause> getAllCustomerBrokerPurchaseContractClauses(String contractID) throws CantGetListCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable ContractPurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractPurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                ContractPurchaseClauseTable.loadToMemory();
                List<DatabaseTableRecord> records = ContractPurchaseClauseTable.getRecords();
                ContractPurchaseClauseTable.clearAllFilters();
                List<ContractClause> Purchases = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    try {
                        Purchases.add(constructCustomerBrokerPurchaseContractClauseFromRecord(record));
                    } catch (InvalidParameterException e) {
                        throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
                    }
                }
                return Purchases;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
        Methods Private
     */

        private void loadRecordAsNew(
            DatabaseTableRecord databaseTableRecord,
            String contractID,
            String negotiationID,
            String publicKeyCustomer,
            String publicKeyBroker,
            long DateTime,
            ContractStatus status
        ) {
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATA_TIME_COLUMN_NAME, DateTime);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
        }

        private CustomerBrokerContractPurchase constructCustomerBrokerPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListCustomerBrokerContractPurchaseException {
            String contractID = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME);
            String negotiationID = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
            String customerPublicKey = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String brokerPublicKey = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            long DateTime = record.getLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATA_TIME_COLUMN_NAME);
            ContractStatus status = ContractStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME));
            return new CustomerBrokerContractPurchaseInformation(
                    contractID,
                    negotiationID,
                    customerPublicKey,
                    brokerPublicKey,
                    DateTime,
                    status,
                    getAllCustomerBrokerPurchaseContractClauses(contractID)
            );
        }

    /*
    *   Methods Clauses
    * */

        private void loadRecordAsNewClause(
                DatabaseTableRecord databaseTableRecord,
                UUID clauseID,
                String contractID,
                ContractClauseType type,
                Integer executionOrder,
                ContractClauseStatus status
        ) {
            databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME, clauseID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CONTRACT_ID_COLUMN_NAME, contractID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME, type.getCode());
            databaseTableRecord.setIntegerValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME, executionOrder);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME, status.getCode());
        }

        private ContractClause constructCustomerBrokerPurchaseContractClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
            UUID                    clauseID        = record.getUUIDValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME);
            ContractClauseType      type            = ContractClauseType.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME));
            Integer                 executionOrder  = record.getIntegerValue(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME);
            ContractClauseStatus    status          = ContractClauseStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            return new ContractClauseInformation(
                    clauseID,
                    type,
                    executionOrder,
                    status
            );
        }
}
