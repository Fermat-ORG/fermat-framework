package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationClauseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantGetListPurchaseNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseClause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 19/10/15.
 */

public class CustomerBrokerPurchaseNegotiationDao implements NegotiationClauseManager {

    private Database database;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;

    /*
        Builders
     */

        public CustomerBrokerPurchaseNegotiationDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId = pluginId;
        }

    /*
        Public methods
     */

        public void initializeDatabase() throws CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException {
            try {
                database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            } catch (CantOpenDatabaseException cantOpenDatabaseException) {
                throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantOpenDatabaseException.getMessage());
            } catch (DatabaseNotFoundException e) {
                CustomerBrokerPurchaseNegotiationDatabaseFactory customerBrokerPurchaseNegotiationDatabaseFactory = new CustomerBrokerPurchaseNegotiationDatabaseFactory(pluginDatabaseSystem);
                try {
                    database = customerBrokerPurchaseNegotiationDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    throw new CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
                }
            }
        }

    /*
        Public methods
     */

        public void createCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantCreateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = PurchaseNegotiationTable.getEmptyRecord();

                recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, negotiation.getCustomerPublicKey());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getBrokerPublicKey());
                recordToInsert.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME, negotiation.getStartDate());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, negotiation.getStatus().getCode());

                PurchaseNegotiationTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerPurchaseNegotiationException(CantCreateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                PurchaseNegotiationClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                DatabaseTableRecord recordsToDelete = PurchaseNegotiationClauseTable.getEmptyRecord();
                recordsToDelete.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
                PurchaseNegotiationClauseTable.deleteRecord(recordsToDelete);

                for(Clause _clause : negotiation.getClauses()) {
                    addNewClause(negotiation.getNegotiationId(), _clause);
                }

            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantAddNewClausesException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantDeleteRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public boolean closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);
                return true;
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.SENT_TO_BROKER.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);
                sendToBrokerUpdateStatusClause(negotiation);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void sendToBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_BROKER.getCode());
                for(Clause _clause : negotiation.getClauses()) {
                    PurchaseNegotiationClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                    PurchaseNegotiationClauseTable.updateRecord(recordToUpdate);
                }
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);
                waitForBrokerUpdateStatusClause(negotiation);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void waitForBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationClauseTable.getEmptyRecord();
                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.WAITING_FOR_BROKER.getCode());
                for(Clause _clause : negotiation.getClauses()) {
                    PurchaseNegotiationClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, _clause.getClauseId(), DatabaseFilterType.EQUAL);
                    PurchaseNegotiationClauseTable.updateRecord(recordToUpdate);
                }
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantGetListPurchaseNegotiationsException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            try {
                PurchaseNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
                PurchaseNegotiationTable.clearAllFilters();
                Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByContractId(UUID negotiationId) throws CantGetListPurchaseNegotiationsException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                PurchaseNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
                PurchaseNegotiationTable.clearAllFilters();
                Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
                }
                return resultados;
            } catch (InvalidParameterException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations(NegotiationStatus status) throws CantGetListPurchaseNegotiationsException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);
                PurchaseNegotiationTable.loadToMemory();
                List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
                PurchaseNegotiationTable.clearAllFilters();
                Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
                }
                return resultados;
            } catch (InvalidParameterException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListPurchaseNegotiationsException(CantGetListPurchaseNegotiationsException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
    *   Methods of Clauses
    * */

        @Override
        public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                PurchaseClauseTable.loadToMemory();
                List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                PurchaseClauseTable.clearAllFilters();
                Collection<Clause> resultados = new ArrayList<>();
                for (DatabaseTableRecord record : records) {
                    resultados.add(constructCustomerBrokerPurchaseClauseFromRecord(record));
                }
                return resultados;
            } catch (CantLoadTableToMemoryException e) {
                throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        @Override
        public void addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                DatabaseTableRecord recordToInsert = PurchaseClauseTable.getEmptyRecord();
                recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clause.getClauseId());
                recordToInsert.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME, clause.getType().getCode());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME, clause.getValue());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, clause.getStatus().getCode());
                recordToInsert.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME, clause.getProposedBy());
                recordToInsert.setIntegerValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME, (int) clause.getIndexOrder());
                PurchaseClauseTable.insertRecord(recordToInsert);
            } catch (CantInsertRecordException e) {
                throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
            }
        }

    /*
    *   Private Methods
    * */

        private CustomerBrokerPurchaseNegotiation newCustomerBrokerPurchaseNegotiation(
                UUID   negotiationId,
                String publicKeyCustomer,
                String publicKeyBroker,
                Long startDateTime,
                Long negotiationExpirationDate,
                NegotiationStatus statusNegotiation,
                Collection<Clause> clauses
        ){
            return new CustomerBrokerPurchaseNegotiationInformation(
                    negotiationId,
                    publicKeyCustomer,
                    publicKeyBroker,
                    startDateTime,
                    negotiationExpirationDate,
                    statusNegotiation,
                    clauses
            );
        }

        private CustomerBrokerPurchaseNegotiation constructCustomerBrokerPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {
            UUID    negotiationId     = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME);
            String  publicKeyCustomer = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
            String  publicKeyBroker   = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
            Long    startDataTime     = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);


            Long    negotiationExpirationDate = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);


            NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));

            return newCustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, negotiationExpirationDate, statusNegotiation, getClauses(negotiationId));
        }

        private CustomerBrokerPurchaseClause newCustomerBrokerPurchaseClause(
                UUID            clauseId,
                ClauseType      type,
                String          value,
                ClauseStatus    status,
                String          proposedBy,
                short           indexOrder
        ){
            return new CustomerBrokerPurchaseClause(clauseId, type, value, status, proposedBy, indexOrder);
        }

        private CustomerBrokerPurchaseClause constructCustomerBrokerPurchaseClauseFromRecord(DatabaseTableRecord record) throws InvalidParameterException{
            UUID            clauseId            = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME);
            ClauseType      type                = ClauseType.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME));
            String          value               = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME);
            ClauseStatus    status              = ClauseStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME));
            String          proposedBy          = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME);
            int             indexOrder          = record.getIntegerValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME);
            return newCustomerBrokerPurchaseClause(clauseId, type, value, status, proposedBy, (short) indexOrder);
        }
}
