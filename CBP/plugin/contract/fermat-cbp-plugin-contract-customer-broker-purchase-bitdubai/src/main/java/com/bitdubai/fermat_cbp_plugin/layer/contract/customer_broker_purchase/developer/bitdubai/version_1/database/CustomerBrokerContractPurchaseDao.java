package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantGetListCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantupdateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.ListsForStatusPurchase;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.ContractClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerContractPurchaseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure.ListsForStatusPurchaseInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import static java.util.Collections.reverseOrder;

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
                DatabaseTableRecord recordToInsert = PurchaseTable.getEmptyRecord();

                loadRecordAsNew(
                        recordToInsert,
                        contract.getContractId(),
                        contract.getNegotiatiotId(),
                        contract.getPublicKeyCustomer(),
                        contract.getPublicKeyBroker(),
                        contract.getDateTime(),
                        contract.getStatus(),
                        contract.getNearExpirationDatetime()
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

        public void updateStatusCustomerBrokerPurchaseContract(String contractID, ContractStatus status) throws CantupdateCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable PurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                PurchaseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordToUpdate = PurchaseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, status.getCode());
                PurchaseTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractPurchaseException("An exception happened", e, "", "");
            }
        }

        public void updateNegotiationNearExpirationDatetime(String contractId, Boolean status) throws CantupdateCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                PurchaseNegotiationClauseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordsToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
                Integer NearExpirationDatetime = 0;
                if(status){
                    NearExpirationDatetime = 1;
                }
                recordsToUpdate.setIntegerValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
                PurchaseNegotiationClauseTable.updateRecord(recordsToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractPurchaseException(CantupdateCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerContractPurchase> getAllCustomerBrokerContractPurchase() throws CantGetListCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                ContractPurchaseTable.addFilterOrder(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
                ContractPurchaseTable.loadToMemory();
                Collection<DatabaseTableRecord> records = ContractPurchaseTable.getRecords();
                ContractPurchaseTable.clearAllFilters();
                Collection<CustomerBrokerContractPurchase> Purchases = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                   Purchases.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                }
                return Purchases;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public CustomerBrokerContractPurchase getCustomerBrokerPurchaseContractForcontractID(String contractID) throws CantGetListCustomerBrokerContractPurchaseException {
            DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
            ContractPurchaseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
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

        public Collection<CustomerBrokerContractPurchase> getCustomerBrokerContractPurchaseForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractPurchaseException {
            DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
            ContractPurchaseTable.addFilterOrder(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            ContractPurchaseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            try {
                ContractPurchaseTable.loadToMemory();
                List<DatabaseTableRecord> records = ContractPurchaseTable.getRecords();
                ContractPurchaseTable.clearAllFilters();
                Collection<CustomerBrokerContractPurchase> Purchase = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    Purchase.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                }
                return Purchase;
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public ListsForStatusPurchase getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable ContractPurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME);
                ListsForStatusPurchase Purchases = new ListsForStatusPurchaseInformation();

                /*
                    History
                 */

                String Query = "SELECT * FROM " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME +
                        " WHERE " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.COMPLETED.getCode() +
                        "' OR " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.CANCELLED.getCode() +
                        "' ORDER BY " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME +
                        "' DESC";

                Collection<DatabaseTableRecord> res_1 = ContractPurchaseTable.customQuery(Query, true);

                Collection<CustomerBrokerContractPurchase> historyContracts = new ArrayList<>();
                for (DatabaseTableRecord record : res_1) {
                    historyContracts.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                }

                Purchases.setHistoryContracts(historyContracts);

                /*
                    Waiting for Broker
                 */

                Query = "SELECT * FROM " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME +
                        " WHERE " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.PAYMENT_SUBMIT.getCode() +
                        "' OR " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.PENDING_MERCHANDISE.getCode() +
                        "' ORDER BY " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME +
                        "' DESC";

                Collection<DatabaseTableRecord> res_2 = ContractPurchaseTable.customQuery(Query, true);

                Collection<CustomerBrokerContractPurchase> waitingForBroker = new ArrayList<>();
                for (DatabaseTableRecord record : res_2) {
                    waitingForBroker.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                }

                Purchases.setContractsWaitingForBroker(waitingForBroker);

                /*
                    Waiting for Broker
                 */

                Query = "SELECT * FROM " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_TABLE_NAME +
                        " WHERE " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.MERCHANDISE_SUBMIT.getCode() +
                        "' OR " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME +
                        " = '" +
                        ContractStatus.PENDING_PAYMENT.getCode() +
                        "' ORDER BY " +
                        CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME +
                        "' DESC";

                Collection<DatabaseTableRecord> res_3 = ContractPurchaseTable.customQuery(Query, true);

                Collection<CustomerBrokerContractPurchase> waitingForCustomer = new ArrayList<>();
                for (DatabaseTableRecord record : res_3) {
                    waitingForCustomer.add(constructCustomerBrokerPurchaseContractFromRecord(record));
                }

                Purchases.setContractsWaitingForCustomer(waitingForCustomer);

                return Purchases;

            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractPurchaseException(CantGetListCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
    *   Methods Clauses
    * */

        public void createCustomerBrokerPurchaseContractClauses(String contractID, Collection<ContractClause> clauses) throws CantCreateCustomerBrokerContractPurchaseException{
            for(ContractClause clause : clauses){
                DatabaseTable ContractClausePurchaseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractClausePurchaseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
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

        public List<ContractClause> getAllCustomerBrokerPurchaseContractClauses(String contractID) throws CantGetListCustomerBrokerContractPurchaseException {
            try {
                DatabaseTable ContractPurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractPurchaseClauseTable.addStringFilter(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
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
            ContractStatus status,
            Boolean nearExpirationDatetime
        ) {
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME, contractID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME, negotiationID);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME, DateTime);
            databaseTableRecord.setStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME, status.getCode());

            Integer _nearExpirationDatetime = 0;
            if(nearExpirationDatetime){
                _nearExpirationDatetime = 1;
            }

            databaseTableRecord.setIntegerValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, _nearExpirationDatetime);
        }

        private CustomerBrokerContractPurchase constructCustomerBrokerPurchaseContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListCustomerBrokerContractPurchaseException {
            String contractID = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CONTRACT_ID_COLUMN_NAME);
            String negotiationID = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEGOTIATION_ID_COLUMN_NAME);
            String customerPublicKey = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String brokerPublicKey = record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            long DateTime = record.getLongValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_DATE_TIME_COLUMN_NAME);
            ContractStatus status = ContractStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_STATUS_COLUMN_NAME));
            Integer nearExpirationDatetime = record.getIntegerValue(CustomerBrokerPurchaseContractDatabaseConstants.CONTRACTS_PURCHASE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);

            Boolean _NearExpirationDatetime = true;
            if(nearExpirationDatetime == 0){
                _NearExpirationDatetime = false;
            }

            return new CustomerBrokerContractPurchaseInformation(
                    contractID,
                    negotiationID,
                    customerPublicKey,
                    brokerPublicKey,
                    DateTime,
                    status,
                    getAllCustomerBrokerPurchaseContractClauses(contractID),
                    _NearExpirationDatetime
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
