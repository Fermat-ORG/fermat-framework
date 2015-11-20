package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantAddNewClausesException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetNextClauseTypeException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantUpdateClausesException;
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
public class CustomerBrokerPurchaseNegotiationDao {

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

        public CustomerBrokerPurchaseNegotiation createCustomerBrokerPurchaseNegotiation(
                String publicKeyCustomer,
                String publicKeyBroker,
                Collection<Clause> clauses
        ) throws CantCreateCustomerBrokerPurchaseNegotiationException {

            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToInsert   = PurchaseNegotiationTable.getEmptyRecord();

                UUID negotiationId = UUID.randomUUID();
                long startDateTime = System.currentTimeMillis();

                loadRecordAsNew(
                        recordToInsert,
                        negotiationId,
                        publicKeyCustomer,
                        publicKeyBroker,
                        startDateTime
                );

                PurchaseNegotiationTable.insertRecord(recordToInsert);

                return newCustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDateTime, NegotiationStatus.WAITING_FOR_BROKER, clauses);

            } catch (CantInsertRecordException e) {
                throw new CantCreateCustomerBrokerPurchaseNegotiationException(CantCreateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }

        }

        public void cancelNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CANCELLED.getCode());

                PurchaseNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void closeNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate   = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.CLOSED.getCode());

                PurchaseNegotiationTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public CustomerBrokerPurchaseNegotiation sendToBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.SENT_TO_BROKER.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);

                sendToBrokerUpdateStatusClause(negotiation);

                return getNegotiationsById(negotiation.getNegotiationId());
            } catch (CantLoadTableToMemoryException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void sendToBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseClauseTable.getEmptyRecord();

                PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.AGREED.getCode(), DatabaseFilterType.EQUAL);
                PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.DRAFT.getCode(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_BROKER.getCode());
                PurchaseClauseTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public CustomerBrokerPurchaseNegotiation waitForBroker(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();

                PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
                PurchaseNegotiationTable.updateRecord(recordToUpdate);

                waitForBrokerUpdateStatusClause(negotiation);

                return getNegotiationsById(negotiation.getNegotiationId());

            } catch (CantLoadTableToMemoryException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (InvalidParameterException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            } catch (CantGetListClauseException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public void waitForBrokerUpdateStatusClause(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
            try {
                DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                DatabaseTableRecord recordToUpdate = PurchaseClauseTable.getEmptyRecord();

                PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
                PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.AGREED.getCode(), DatabaseFilterType.EQUAL);
                PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.DRAFT.getCode(), DatabaseFilterType.EQUAL);

                recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.SENT_TO_BROKER.getCode());
                PurchaseClauseTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecordException e) {
                throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
            }
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations() throws CantLoadTableToMemoryException, InvalidParameterException, CantGetListClauseException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<CustomerBrokerPurchaseNegotiation>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiations(NegotiationStatus status) throws CantLoadTableToMemoryException, InvalidParameterException, CantGetListClauseException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, status.getCode(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<CustomerBrokerPurchaseNegotiation>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByCustomer(ActorIdentity customer) throws CantLoadTableToMemoryException, InvalidParameterException, CantGetListClauseException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, customer.getPublicKey(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<CustomerBrokerPurchaseNegotiation>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public Collection<CustomerBrokerPurchaseNegotiation> getNegotiationsByBroker(ActorIdentity broker) throws CantLoadTableToMemoryException, InvalidParameterException, CantGetListClauseException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, broker.getPublicKey(), DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            Collection<CustomerBrokerPurchaseNegotiation> resultados = new ArrayList<CustomerBrokerPurchaseNegotiation>();

            for (DatabaseTableRecord record : records) {
                resultados.add(constructCustomerBrokerPurchaseFromRecord(record));
            }

            return resultados;
        }

        public CustomerBrokerPurchaseNegotiation getNegotiationsById(UUID negotiationId) throws CantLoadTableToMemoryException, InvalidParameterException, CantGetListClauseException {
            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);

            PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);

            PurchaseNegotiationTable.loadToMemory();

            List<DatabaseTableRecord> records = PurchaseNegotiationTable.getRecords();
            PurchaseNegotiationTable.clearAllFilters();

            return constructCustomerBrokerPurchaseFromRecord(records.get(0));
        }

        /*
            Clause methods
         */

            public Collection<Clause> getClauses(UUID negotiationId) throws CantGetListClauseException{

                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);

                    PurchaseClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                    PurchaseClauseTable.clearAllFilters();

                    Collection<Clause> resultados = new ArrayList<Clause>();

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

            public Clause getClausesByClauseId(UUID clauseId) throws CantGetListClauseException{

                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clauseId, DatabaseFilterType.EQUAL);

                    PurchaseClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                    PurchaseClauseTable.clearAllFilters();

                    for (DatabaseTableRecord record : records) {
                        return constructCustomerBrokerPurchaseClauseFromRecord(record);
                    }

                    return null;
                } catch (CantLoadTableToMemoryException e) {
                    throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
                } catch (InvalidParameterException e) {
                    throw new CantGetListClauseException(CantGetListClauseException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public Clause addNewClause(UUID negotiationId, Clause clause) throws CantAddNewClausesException {
                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    DatabaseTableRecord recordToInsert = PurchaseClauseTable.getEmptyRecord();
                    loadRecordAsNewClause(
                            recordToInsert,
                            negotiationId,
                            clause.getType(),
                            clause.getValue(),
                            clause.getProposedBy()
                    );
                    PurchaseClauseTable.insertRecord(recordToInsert);
                    return constructCustomerBrokerPurchaseClauseFromRecord(recordToInsert);
                } catch (CantInsertRecordException e) {
                    throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
                } catch (InvalidParameterException e) {
                    throw new CantAddNewClausesException(CantAddNewClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public Clause modifyClauseStatus(UUID negotiationId, Clause clause, ClauseStatus status) throws CantUpdateClausesException {
                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    DatabaseTableRecord recordToUpdate   = PurchaseClauseTable.getEmptyRecord();

                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clause.getClauseId(), DatabaseFilterType.EQUAL);

                    recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, status.getCode());

                    PurchaseClauseTable.updateRecord(recordToUpdate);

                    return null;
                } catch (CantUpdateRecordException e) {
                    throw new CantUpdateClausesException(CantUpdateClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public void rejectClauseByType(UUID negotiationId, ClauseType type) throws CantUpdateClausesException {
                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    DatabaseTableRecord recordToUpdate   = PurchaseClauseTable.getEmptyRecord();

                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                    PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME, type.getCode(), DatabaseFilterType.EQUAL);

                    recordToUpdate.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.REJECTED.getCode());

                    PurchaseClauseTable.updateRecord(recordToUpdate);
                } catch (CantUpdateRecordException e) {
                    throw new CantUpdateClausesException(CantUpdateClausesException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public ClauseType getNextClauseType(UUID negotiationId) throws CantGetNextClauseTypeException {

                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);

                    PurchaseClauseTable.setFilterOrder(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                    PurchaseClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                    PurchaseClauseTable.clearAllFilters();

                    return ClauseType.getByCode(records.get(0).getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME));

                } catch (CantLoadTableToMemoryException e) {
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE, e, "", "");
                } catch (InvalidParameterException e) {
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE, e, "", "");
                }
            }

            public String getPaymentMethod(UUID negotiationId) throws CantGetNextClauseTypeException {

                try {
                    DatabaseTable PurchaseClauseTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TABLE_NAME);
                    PurchaseClauseTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
                    PurchaseClauseTable.setStringFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME, ClauseType.CUSTOMER_PAYMENT_METHOD.getCode(), DatabaseFilterType.EQUAL);

                    PurchaseClauseTable.setFilterOrder(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME, DatabaseFilterOrder.DESCENDING);

                    PurchaseClauseTable.loadToMemory();
                    List<DatabaseTableRecord> records = PurchaseClauseTable.getRecords();
                    PurchaseClauseTable.clearAllFilters();

                    return records.get(0).getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME);

                } catch (CantLoadTableToMemoryException e) {
                    throw new CantGetNextClauseTypeException(CantGetNextClauseTypeException.DEFAULT_MESSAGE, e, "", "");
                }
            }

    /*
        Private methods
     */

        /*
            Negotiations
         */

            private void loadRecordAsNew(
                    DatabaseTableRecord databaseTableRecord,
                    UUID   negotiationId,
                    String publicKeyCustomer,
                    String publicKeyBroker,
                    long startDataTime
            ) {
                databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME, publicKeyCustomer);
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, publicKeyBroker);
                databaseTableRecord.setLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME, startDataTime);
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
            }

            private void loadRecordAsUpdate(
                    DatabaseTableRecord databaseTableRecord
            ) {
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
            }

            private CustomerBrokerPurchaseNegotiation newCustomerBrokerPurchaseNegotiation(
                    UUID   negotiationId,
                    String publicKeyCustomer,
                    String publicKeyBroker,
                    long startDateTime,
                    NegotiationStatus statusNegotiation,
                    Collection<Clause> clauses
            ){
                return new CustomerBrokerPurchaseNegotiationInformation(negotiationId, publicKeyCustomer, publicKeyBroker, startDateTime, statusNegotiation, clauses);
            }

            private CustomerBrokerPurchaseNegotiation constructCustomerBrokerPurchaseFromRecord(DatabaseTableRecord record) throws InvalidParameterException, CantGetListClauseException {

                UUID    negotiationId     = record.getUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME);
                String  publicKeyCustomer = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
                String  publicKeyBroker   = record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
                long    startDataTime     = record.getLongValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);
                NegotiationStatus  statusNegotiation = NegotiationStatus.getByCode(record.getStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME));

                return newCustomerBrokerPurchaseNegotiation(negotiationId, publicKeyCustomer, publicKeyBroker, startDataTime, statusNegotiation, getClauses(negotiationId));
            }

        /*
            Clauses
         */

            private void loadRecordAsNewClause(
                    DatabaseTableRecord databaseTableRecord,
                    UUID   negotiationId,
                    ClauseType type,
                    String value,
                    String proposedBy
            ) {

                UUID clauseId = UUID.randomUUID();

                databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME, clauseId);
                databaseTableRecord.setUUIDValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME, negotiationId);
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME, type.getCode());
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME, value);
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME, ClauseStatus.DRAFT.getCode());
                databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME, proposedBy);
                databaseTableRecord.setIntegerValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME, 0);

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




    public CustomerBrokerPurchaseNegotiation updateCustomerBrokerPurchaseNegotiation(CustomerBrokerPurchaseNegotiation negotiation) throws CantUpdateCustomerBrokerPurchaseNegotiationException {
        try {

            DatabaseTable PurchaseNegotiationTable = this.database.getTable(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME);
            PurchaseNegotiationTable.setUUIDFilter(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId(), DatabaseFilterType.EQUAL);
            DatabaseTableRecord recordToUpdate = PurchaseNegotiationTable.getEmptyRecord();
            loadRecordAsUpdate(recordToUpdate);
            PurchaseNegotiationTable.updateRecord(recordToUpdate);

            for(Clause _clause : negotiation.getClauses()) {
                updateCustomerBrokerPurchaseNegotiationClause(_clause);
            }

            return getNegotiationsById(negotiation.getNegotiationId());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        } catch (InvalidParameterException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantGetListClauseException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateCustomerBrokerPurchaseNegotiationException(CantUpdateCustomerBrokerPurchaseNegotiationException.DEFAULT_MESSAGE, e, "", "");
        }
    }


    public Clause updateCustomerBrokerPurchaseNegotiationClause(Clause clause) throws CantUpdateCustomerBrokerPurchaseNegotiationException {


        return null;

    }


    private void loadRecordAsUpdateClause(
            DatabaseTableRecord databaseTableRecord
    ) {
        databaseTableRecord.setStringValue(CustomerBrokerPurchaseNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME, NegotiationStatus.WAITING_FOR_BROKER.getCode());
    }

}
