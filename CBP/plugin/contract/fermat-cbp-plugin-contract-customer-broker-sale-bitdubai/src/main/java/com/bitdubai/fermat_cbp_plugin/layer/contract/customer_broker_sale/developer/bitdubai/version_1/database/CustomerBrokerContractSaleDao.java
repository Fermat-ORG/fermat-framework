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
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantUpdateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.ListsForStatusSale;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleContractDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.ContractSaleClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.CustomerBrokerContractSaleInformation;
import com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_sale.developer.bitdubai.version_1.structure.ListsForStatusSaleInformation;

import java.util.ArrayList;
import java.util.Collection;
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

    public void initializeDatabase() throws CantInitializeCustomerBrokerSaleContractDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CustomerBrokerSaleContractDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantOpenDatabaseException.getMessage());
        } catch (DatabaseNotFoundException e) {
            CustomerBrokerSaleContractDatabaseFactory CustomerBrokerSaleContractDatabaseFactory = new CustomerBrokerSaleContractDatabaseFactory(pluginDatabaseSystem);
            try {
                database = CustomerBrokerSaleContractDatabaseFactory.createDatabase(pluginId, CustomerBrokerSaleContractDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeCustomerBrokerSaleContractDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public CustomerBrokerContractSale createCustomerBrokerSaleContract(CustomerBrokerContractSale contract) throws CantCreateCustomerBrokerContractSaleException {
        try {
            DatabaseTable SaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            DatabaseTableRecord recordToInsert = SaleTable.getEmptyRecord();
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
            return contract;
        } catch (CantInsertRecordException e) {
            throw new CantCreateCustomerBrokerContractSaleException("An exception happened", e, "", "");
        }
    }

    public void updateStatusCustomerBrokerSaleContract(String contractID, ContractStatus status) throws CantUpdateCustomerBrokerContractSaleException {
        try {
            DatabaseTable SaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            SaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordToUpdate = SaleTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, status.getCode());
            SaleTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerContractSaleException("An exception happened", e, "", "");
        }
    }

    public void updateContractNearExpirationDatetime(String contractId, Boolean status) throws CantUpdateCustomerBrokerContractSaleException {
        try {
            DatabaseTable SaleNegotiationClauseTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            SaleNegotiationClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordsToUpdate = SaleNegotiationClauseTable.getEmptyRecord();
            String NearExpirationDatetime = "0";
            if (status) {
                NearExpirationDatetime = "1";
            }
            recordsToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, NearExpirationDatetime);
            SaleNegotiationClauseTable.updateRecord(recordsToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerContractSaleException(CantUpdateCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
        }
    }

    //ADD YORDIN ALAYN 07.04.16
    public void cancelContract(String contractID, String reason) throws CantUpdateCustomerBrokerContractSaleException {
        try {
            DatabaseTable SaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            SaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordToUpdate = SaleTable.getEmptyRecord();
            recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CANCEL_REASON_COLUMN_NAME, reason);
            recordToUpdate.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, ContractStatus.CANCELLED.getCode());
            SaleTable.updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerContractSaleException("An exception happened", e, "", "");
        }
    }

    public Collection<CustomerBrokerContractSale> getAllCustomerBrokerContractSale() throws CantGetListCustomerBrokerContractSaleException {
        try {
            DatabaseTable ContractSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME);
            ContractSaleTable.addFilterOrder(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);
            ContractSaleTable.loadToMemory();
            Collection<DatabaseTableRecord> records = ContractSaleTable.getRecords();
            ContractSaleTable.clearAllFilters();
            Collection<CustomerBrokerContractSale> Sales = new ArrayList<>();
            for (DatabaseTableRecord record : records) {
                Sales.add(constructCustomerBrokerSaleContractFromRecord(record));
            }
            return Sales;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantGetListCustomerBrokerContractSaleException(CantGetListCustomerBrokerContractSaleException.DEFAULT_MESSAGE, e, "", "");
        }
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

            String Query = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME)
                    .append(" WHERE ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.COMPLETED.getCode())
                    .append("' OR ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.CANCELLED.getCode())
                    .append("' ORDER BY ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME)
                    .append(" DESC").toString();

            Collection<DatabaseTableRecord> res_1 = ContractSaleTable.customQuery(Query, true);
            Collection<CustomerBrokerContractSale> historyContracts = new ArrayList<>();
            int i = 0;
            for (DatabaseTableRecord record : res_1) {
                historyContracts.add(constructInformationByCustomQuery(record));
            }
            Sales.setHistoryContracts(historyContracts);

            /*
                Waiting for Broker
             */
            Query = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME)
                    .append(" WHERE ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.PAYMENT_SUBMIT.getCode())
                    .append("' OR ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.PENDING_MERCHANDISE.getCode())
                    .append("' ORDER BY ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME)
                    .append(" DESC").toString();

            Collection<DatabaseTableRecord> res_2 = ContractSaleTable.customQuery(Query, true);
            Collection<CustomerBrokerContractSale> waitingForBroker = new ArrayList<>();
            for (DatabaseTableRecord record : res_2) {
                waitingForBroker.add(constructInformationByCustomQuery(record));
            }
            Sales.setContractsWaitingForBroker(waitingForBroker);

            /*
                Waiting for Broker
             */
            Query = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_TABLE_NAME)
                    .append(" WHERE ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.MERCHANDISE_SUBMIT.getCode())
                    .append("' OR ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME)
                    .append(" = '")
                    .append(ContractStatus.PENDING_PAYMENT.getCode())
                    .append("' ORDER BY ")
                    .append(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME)
                    .append(" DESC").toString();

            Collection<DatabaseTableRecord> res_3 = ContractSaleTable.customQuery(Query, true);
            Collection<CustomerBrokerContractSale> waitingForCustomer = new ArrayList<>();
            for (DatabaseTableRecord record : res_3) {
                waitingForCustomer.add(constructInformationByCustomQuery(record));
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

    public void createCustomerBrokerSaleContractClauses(String contractID, Collection<ContractClause> clauses) throws CantCreateCustomerBrokerContractSaleException {
        DatabaseTable ContractClauseSaleTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
        ContractClauseSaleTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
        for (ContractClause clause : clauses) {
            DatabaseTableRecord recordToInsert = ContractClauseSaleTable.getEmptyRecord();
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

    public Collection<ContractClause> getAllCustomerBrokerSaleContractClauses(String contractID) throws CantGetListCustomerBrokerContractSaleException {
        try {
            DatabaseTable ContractSaleClauseTable = this.database.getTable(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TABLE_NAME);
            ContractSaleClauseTable.addStringFilter(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID, DatabaseFilterType.EQUAL);
            ContractSaleClauseTable.loadToMemory();
            List<DatabaseTableRecord> records = ContractSaleClauseTable.getRecords();
            ContractSaleClauseTable.clearAllFilters();
            Collection<ContractClause> Sales = new ArrayList<>();
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
            Long DateTime,
            ContractStatus status,
            Boolean nearExpirationDatetime
    ) {
        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME, contractID);
        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEGOTIATION_ID_COLUMN_NAME, negotiationID);
        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
        databaseTableRecord.setLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME, DateTime);
        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME, status.getCode());

        String _nearExpirationDatetime = "0";
        if (nearExpirationDatetime) {
            _nearExpirationDatetime = "1";
        }

        databaseTableRecord.setStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME, _nearExpirationDatetime);
    }

    private CustomerBrokerContractSale constructCustomerBrokerSaleContractFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListCustomerBrokerContractSaleException {
        String contractID = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CONTRACT_ID_COLUMN_NAME);
        String negotiationID = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEGOTIATION_ID_COLUMN_NAME);
        String customerPublicKey = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        String brokerPublicKey = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_BROKER_PUBLIC_KEY_COLUMN_NAME);
        Long DateTime = record.getLongValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_DATE_TIME_COLUMN_NAME);
        ContractStatus status = ContractStatus.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_STATUS_COLUMN_NAME));
        String nearExpirationDatetime = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_NEAR_EXPIRATION_DATE_TIME_COLUMN_NAME);
        String cancelReason = record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CONTRACTS_SALE_CANCEL_REASON_COLUMN_NAME);

        Boolean _NearExpirationDatetime = true;
        if (nearExpirationDatetime.equals("0")) {
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
                _NearExpirationDatetime,
                cancelReason
        );
    }

    private CustomerBrokerContractSale constructInformationByCustomQuery(DatabaseTableRecord record) throws InvalidParameterException, CantGetListCustomerBrokerContractSaleException {

        String contractID = record.getStringValue("Column0");
        String negotiationID = record.getStringValue("Column1");
        String customerPublicKey = record.getStringValue("Column2");
        String brokerPublicKey = record.getStringValue("Column3");
        Long DateTime = record.getLongValue("Column4");
        ContractStatus status = ContractStatus.getByCode(record.getStringValue("Column5"));
        String nearExpirationDatetime = record.getStringValue("Column6");
        String cancelReason = record.getStringValue("Column7");


        Boolean _NearExpirationDatetime = true;
        if (nearExpirationDatetime.equals("0")) {
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
                _NearExpirationDatetime,
                cancelReason
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
        UUID clauseID = record.getUUIDValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CLAUSE_ID_COLUMN_NAME);
        ContractClauseType type = ContractClauseType.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_TYPE_COLUMN_NAME));
        Integer executionOrder = record.getIntegerValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_EXECUTION_ORDER_COLUMN_NAME);
        ContractClauseStatus status = ContractClauseStatus.getByCode(record.getStringValue(CustomerBrokerSaleContractDatabaseConstants.CLAUSE_CONTRACT_CURRENT_STATUS_COLUMN_NAME));
        return new ContractSaleClauseInformation(
                clauseID,
                type,
                executionOrder,
                status
        );
    }
}
