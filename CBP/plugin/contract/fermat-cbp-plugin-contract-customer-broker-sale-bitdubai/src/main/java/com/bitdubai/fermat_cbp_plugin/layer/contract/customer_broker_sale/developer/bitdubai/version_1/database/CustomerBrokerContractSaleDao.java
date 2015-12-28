package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantupdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.ContractSaleClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerContractSaleInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.ListsForStatusSaleInformation;

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

        public void initializeDatabase() throws CantInitializeCustomerBrokerSaleContractDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerSaleContractDatabaseFactory CustomerBrokerSaleContractDatabaseFactory = new CustomerBrokerSaleContractDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = CustomerBrokerSaleContractDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

        public CustomerBrokerContractSale createCustomerBrokerSaleContract(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException {
            try {
                DatabaseTable SaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
                DatabaseTableRecord recordToInsert = SaleTable.getEmptyRecord();;
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
                SaleTable.insertRecord(recordToInsert);
                createCustomerBrokerSaleContractClauses(contract.getContractId(), contract.getContractClause());
                return constructCustomerBrokerSaleContractFromRecord(recordToInsert);
            } catch (InvalidParameterException e) {
                throw new CantCreateCustomerBrokerContractSaleException("An exception happened", e, "", "");
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerContractSaleException("An exception happened", e, "", "");
            } catch (CantGetListCustomerBrokerContractSaleException e) {
                throw new CantCreateCustomerBrokerContractSaleException("An exception happened", e, "", "");
            }
        }

        public void updateStatusCustomerBrokerSaleContract(
                String contractID,
                ContractStatus status
        ) throws CantupdateCustomerBrokerContractSaleException {

            try {
                DatabaseTable SaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
                SaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordToUpdate = SaleTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, status.getCode());
                SaleTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractSaleException("An exception happened", e, "", "");
            }
        }

        public void updateNegotiationNearExpirationDatetime(String contractId, Boolean status) throws CantupdateCustomerBrokerContractSaleException {
            try {
                DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
                SaleNegotiationClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordsToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
                Integer NearExpirationDatetime = 0;
                if(status){
                    NearExpirationDatetime = 1;
                }
                recordsToUpdate.setIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
                SaleNegotiationClauseTable.updateRecord(recordsToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantupdateCustomerBrokerContractSaleException(CantupdateCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public List<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException {
            DatabaseTable ContractSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            try {
                ContractSaleTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            }
            List<DatabaseTableRecord> records = ContractSaleTable.getRecords();
            ContractSaleTable.clearAllFilters();
            List<CustomerBrokerContractSale> Sales = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                try {
                    Sales.add(constructCustomerBrokerSaleContractFromRecord(record));
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
                }
            }
            return Sales;
        }

        public CustomerBrokerContractSale getCustomerBrokerSaleContractForcontractID(String contractID) throws CantGetListCustomerBrokerContractSaleException {
            DatabaseTable ContractSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            try {
                ContractSaleTable.loadToMemory();
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            }
            List<DatabaseTableRecord> records = ContractSaleTable.getRecords();
            ContractSaleTable.clearAllFilters();
            CustomerBrokerContractSale Sale = null;
            for (DatabaseTableRecord record : records) {
                try {
                    Sale = constructCustomerBrokerSaleContractFromRecord(record);
                } catch (InvalidParameterException e) {
                    throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
                }
            }
            return Sale;
        }

        public Collection<CustomerBrokerContractSale> getCustomerBrokerContractSaleForStatus(ContractStatus status) throws CantGetListCustomerBrokerContractSaleException {
            DatabaseTable ContractSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            ContractSaleTable.addFilterOrder(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
            try {
                ContractSaleTable.loadToMemory();
                List<DatabaseTableRecord> records = ContractSaleTable.getRecords();
                ContractSaleTable.clearAllFilters();
                Collection<CustomerBrokerContractSale> Sale = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    Sale.add(constructCustomerBrokerSaleContractFromRecord(record));
                }
                return Sale;
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public ListsForStatusSale getCustomerBrokerContractHistory() throws CantGetListCustomerBrokerContractSaleException {
            try {
                DatabaseTable ContractSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
                ListsForStatusSale Sales = new ListsForStatusSaleInformation();

                /*
                    History
                 */

                    SortedMap listHistory  = new TreeMap(reverseOrder());

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.COMPLETED.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r1 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r1) {
                        listHistory.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.CANCELLED.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r2 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r2) {
                        listHistory.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    Collection<CustomerBrokerContractSale> historyContracts = new ArrayList<>();

                    Iterator iterator = listHistory.keySet().iterator();
                    while (iterator.hasNext()) {
                        Object key = iterator.next();
                        historyContracts.add((CustomerBrokerContractSale) listHistory.get(key));
                    }

                    Sales.setHistoryContracts(historyContracts);

                /*
                    Waiting for Broker
                 */
                    SortedMap listWaitingForBroker  = new TreeMap(reverseOrder());

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.PAYMENT_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r3 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r3) {
                        listWaitingForBroker.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.PENDING_MERCHANDISE.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r4 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r4) {
                        listWaitingForBroker.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    Collection<CustomerBrokerContractSale> waitingForBroker = new ArrayList<>();

                    iterator = listWaitingForBroker.keySet().iterator();
                    while (iterator.hasNext()) {
                        Object key = iterator.next();
                        waitingForBroker.add((CustomerBrokerContractSale) listWaitingForBroker.get(key));
                    }

                    Sales.setContractsWaitingForBroker(waitingForBroker);

                /*
                    Waiting for Broker
                 */
                    SortedMap listWaitingForCustomer  = new TreeMap(reverseOrder());

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.MERCHANDISE_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r5 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r5) {
                        listWaitingForCustomer.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    ContractSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.PENDING_PAYMENT.getCode(), DatabaseFilterType.EQUAL);
                    ContractSaleTable.loadToMemory();
                    Collection<DatabaseTableRecord> r6 = ContractSaleTable.getRecords();
                    ContractSaleTable.clearAllFilters();

                    for (DatabaseTableRecord record : r6) {
                        listWaitingForCustomer.put(
                                record.getFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME),
                                constructCustomerBrokerSaleContractFromRecord(record)
                        );
                    }

                    Collection<CustomerBrokerContractSale> waitingForCustomer = new ArrayList<>();

                    iterator = listWaitingForCustomer.keySet().iterator();
                    while (iterator.hasNext()) {
                        Object key = iterator.next();
                        waitingForCustomer.add((CustomerBrokerContractSale) listWaitingForCustomer.get(key));
                    }

                    Sales.setContractsWaitingForCustomer(waitingForCustomer);

                return Sales;

            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
            }
        }


    /*
    *   Methods Clauses
    * */

        public void createCustomerBrokerSaleContractClauses(String contractID, Collection<ContractClause> clauses) throws CantCreateCustomerBrokerContractSaleException{
            for(ContractClause clause : clauses){
                DatabaseTable ContractClauseSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractClauseSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordToInsert = ContractClauseSaleTable.getEmptyRecord();;
                loadRecordAsNewClause(
                        recordToInsert,
                        clause.getClauseId(),
                        contractID,
                        clause.getType(),
                        clause.getExecutionOrder(),
                        clause.getStatus()
                );
                try {
                    ContractClauseSaleTable.insertRecord(recordToInsert);
                } catch (CantInsertRecordException e) {
                    throw new CantCreateCustomerBrokerContractSaleException("An exception happened", e, "", "");
                }
            }
        }

        public void updateStatusCustomerBrokerSaleContractClauseStatus(String contractID, ContractClause clause) throws CantupdateCustomerBrokerContractSaleException{
            DatabaseTable ContractSaleClauseTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);

            ContractSaleClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            ContractSaleClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME, clause.getType().getCode(), DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordToUpdate = ContractSaleClauseTable.getEmptyRecord();

            recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME, clause.getStatus().getCode());

            try {
                ContractSaleClauseTable.insertRecord(recordToUpdate);
            } catch (CantInsertRecordException e) {
                throw new CantupdateCustomerBrokerContractSaleException("An exception happened", e, "", "");
            }
        }

        public List<ContractClause> getAllCustomerBrokerSaleContractClauses(String contractID) throws CantGetListCustomerBrokerContractSaleException {
            try {
                DatabaseTable ContractSaleClauseTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
                ContractSaleClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
                ContractSaleClauseTable.loadToMemory();
                List<DatabaseTableRecord> records = ContractSaleClauseTable.getRecords();
                ContractSaleClauseTable.clearAllFilters();
                List<ContractClause> Sales = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    try {
                        Sales.add(constructCustomerBrokerSaleContractClauseFromRecord(record));
                    } catch (InvalidParameterException e) {
                        throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
                    }
                }
                return Sales;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
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
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationID);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
            databaseTableRecord.setFloatValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME, DateTime);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, status.getCode());

            Integer _nearExpirationDatetime = 0;
            if(nearExpirationDatetime){
                _nearExpirationDatetime = 1;
            }

            databaseTableRecord.setIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, _nearExpirationDatetime);
        }

        private CustomerBrokerContractSale constructCustomerBrokerSaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListCustomerBrokerContractSaleException {
            String contractID = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME);
            String negotiationID = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEGOTIATION_ID_COLUMN_NAME);
            String customerPublicKey = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String brokerPublicKey = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
            long DateTime = record.getLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME);
            ContractStatus status = ContractStatus.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME));
            Integer nearExpirationDatetime = record.getIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);

            Boolean _NearExpirationDatetime = true;
            if(nearExpirationDatetime == 0){
                _NearExpirationDatetime = false;
            }

            return new CustomerBrokerContractSaleInformation(
                    contractID,
                    negotiationID,
                    customerPublicKey,
                    brokerPublicKey,
                    DateTime,
                    status,
                    getAllCustomerBrokerSaleContractClauses(contractID),
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
            databaseTableRecord.setUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME, clauseID);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CONTRACT_ID_COLUMN_NAME, contractID);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME, type.getCode());
            databaseTableRecord.setIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME, executionOrder);
            databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME, status.getCode());
        }

        private ContractClause constructCustomerBrokerSaleContractClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {
            UUID                    clauseID        = record.getUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME);
            ContractClauseType      type            = ContractClauseType.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME));
            Integer                 executionOrder  = record.getIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME);
            ContractClauseStatus    status          = ContractClauseStatus.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME));
            return new ContractSaleClauseInformation(
                    clauseID,
                    type,
                    executionOrder,
                    status
            );
        }
}
