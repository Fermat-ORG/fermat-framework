package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateEventException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerUpdateNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 16.12.15.
 */
public class CustomerBrokerUpdateNegotiationTransactionDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private Database database;

    public CustomerBrokerUpdateNegotiationTransactionDatabaseDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final Database database) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerUpdateNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //CREATE NEW NEGOTIATION TRANSACTION
    public void createCustomerBrokerUpdateNegotiationTransaction(UUID transactionId, Negotiation negotiation, NegotiationType negotiationType, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {


        Date time = new Date();
        long timestamp = time.getTime();
        String negotiationXML = XMLParser.parseObject(negotiation);

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME, negotiation.getBrokerPublicKey());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, negotiation.getCustomerPublicKey());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME, negotiationType.getCode());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME, negotiationXML);
            record.setLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME, timestamp);
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_SEND_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString());
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString());


            table.insertRecord(record);

            if (statusTransaction.getCode() == NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode()) {
                if (negotiation.getStatus().getCode().equals(NegotiationStatus.CANCELLED.getCode())) {
                    System.out.print(new StringBuilder().append("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - DAO. CONFIRM negotiationType: ").append(negotiationType.getCode()).append(" transactionId: ").append(transactionId).append(" ****\n").toString());
                } else {
                    System.out.print(new StringBuilder().append("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO. CONFIRM negotiationType: ").append(negotiationType.getCode()).append(" transactionId: ").append(transactionId).append(" ****\n").toString());
                }
            } else {
                if (negotiation.getStatus().getCode().equals(NegotiationStatus.CANCELLED.getCode())) {
                    System.out.print(new StringBuilder().append("\n\n**** 4) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CANCEL - DAO. NEGOTIATION negotiationType: ").append(negotiationType.getCode()).append(" transactionId: ").append(transactionId).append(" ****\n").toString());
                } else {
                    System.out.print(new StringBuilder().append("\n\n**** 4) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO. NEGOTIATION negotiationType: ").append(negotiationType.getCode()).append(" transactionId: ").append(transactionId).append(" ****\n").toString());
                }
            }

//            CustomerBrokerUpdate negotiationTransactionTest = getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(negotiation.getNegotiationId());
//            System.out.print("\n**** 4.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO. NEGOTIATION DATE OF DATEBASE****\n" +
//                    "\n - Customer: " + negotiationTransactionTest.getPublicKeyCustomer() +
//                    "\n - Broker: " + negotiationTransactionTest.getPublicKeyBroker() +
//                    "\n - negotiationId: " + negotiationTransactionTest.getNegotiationId() +
//                    "\n - transactionId: " + negotiationTransactionTest.getTransactionId() +
//                    "\n - Status: " + negotiationTransactionTest.getStatusTransaction());

        } catch (CantInsertRecordException e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, "Customer Broker Update Negotiation Transaction", "Cant create new Customer Broker Update Negotiation Transaction, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction", "Cant create new Customer Broker Update Negotiation Transaction, unknown failure.");
        }

    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION
    public void updateStatusRegisterCustomerBrokerUpdateNegotiationTranasction(UUID transactionId, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {
        try {
            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("Cant Update Status Customer Broker Update Negotiation Transaction, not exists.", "Customer Broker Update Negotiation Transaction, Update State", "Cant Update State Customer Broker Update Negotiation Transaction, not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, statusTransaction.getCode());
            table.updateRecord(record);

//            System.out.print("\n\n**** 8) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER UPDATE - DAO - UPDATE STATUS SALE NEGOTIATION STATUS : " + NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode() + " ****\n");
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, "Customer Broker Update Negotiation Transaction, Update State", "Cant Update State Customer Broker Update Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction, Update State", "Cant Update State Customer Broker Update Negotiation Transaction, unknown failure.");
        }
    }

    //GET NEW NEGOTIATION TRANSACTION FROM TRANSACTION ID
    public CustomerBrokerUpdate getRegisterCustomerBrokerUpdateNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {

        try {

            CustomerBrokerUpdate getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerUpdateFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET NEW NEGOTIATION TRANSACTION FROM NEGOTIATION ID
    public CustomerBrokerUpdate getRegisterCustomerBrokerUpdateNegotiationTranasctionFromNegotiationId(UUID negotiationId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {

        try {
            CustomerBrokerUpdate getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerUpdateFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEW NEGOTIATION TRANSACTION
    public List<CustomerBrokerUpdate> getAllRegisterCustomerBrokerUpdateNegotiationTranasction() throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {

        try {
            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Update Negotiation Transaction", "");

            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO SUBMIT
    public List<CustomerBrokerUpdate> getPendingToSubmitNegotiation() throws CantGetNegotiationTransactionListException {

        try {

            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    /*
    public List<String> getPendingToSubmitNegotiation() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        return getStringList(
                NegotiationTransactionStatus.PENDING_SUBMIT.getCode(),
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);

    }*/

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO CONFIRM
    public List<CustomerBrokerUpdate> getPendingToConfirmtNegotiation() throws CantGetNegotiationTransactionListException {
        try {

            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }
    }
    /*public List<String> getPendingToConfirmtNegotiation() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        return getStringList(
                NegotiationTransactionStatus.PENDING_CONFIRMATION.getCode(),
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_NEGOTIATION_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);

    }*/

    //GET TRANSACTION ID OF NEGOTIATION TRANSACTION
    public UUID getTransactionId(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        String transactionId = getValue(
                negotiationId,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME);

        return UUID.fromString(transactionId);

    }

    /**
     * Get List of all negotiation transaction with CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME in FALSE
     *
     * @return List of All CustomerBrokerNew register with CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME in FALSE
     * @throws CantGetNegotiationTransactionListException
     */
    public List<CustomerBrokerUpdate> getPendingToConfirmTransactionNegotiation() throws CantGetNegotiationTransactionListException {

        try {
            List<CustomerBrokerUpdate> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = getDatabaseTransactionTable();
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerUpdateFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }
    }

    /**
     * Change the CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME to TRUE, indicated confirmation that the negotiation transmision is done
     *
     * @param transactionId
     * @throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException
     */
    public void confirmTransaction(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {
        try {

            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("Cant Update Status Customer Broker Update Negotiation Transaction, not exists.", "Customer Broker Update Negotiation Transaction, Update State", "Cant Update State Customer Broker Update Negotiation Transaction, not exists");

            DatabaseTable table = getDatabaseTransactionTable();
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.TRUE.toString());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, "Customer Broker Update Negotiation Transaction, Update Confirm Transaction", "Cant Update Confirm Transaction Customer Broker Update Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Negotiation Transaction, Customer Broker New", "Cant confirm Transaction, unknown failure.");
        }
    }

    /**
     * Change the CUSTOMER_BROKER_UPDATE_CONFIRM_TRANSACTION_COLUMN_NAME to TRUE, indicated confirmation that the negotiation transmision is done
     *
     * @param transactionId
     * @throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException
     */
    public void sendTransaction(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {
        try {

            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException("Cant Update Status Customer Broker Update Negotiation Transaction, not exists.", "Customer Broker Update Negotiation Transaction, Update State", "Cant Update State Customer Broker Update Negotiation Transaction, not exists");

            DatabaseTable table = getDatabaseTransactionTable();
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_SEND_TRANSACTION_COLUMN_NAME, Boolean.TRUE.toString());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), e, "Customer Broker Update Negotiation Transaction, Update Confirm Transaction", "Cant Update Confirm Transaction Customer Broker Update Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Negotiation Transaction, Customer Broker New", "Cant confirm Transaction, unknown failure.");
        }
    }
    
    /*END TRANSACTION*/

    /*EVENT*/
    //GET LIST PENDING EVENT
    public List<UUID> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            List<UUID> eventTypeList = new ArrayList<>();
            UUID eventId;
            table.addStringFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (records.isEmpty())
                return eventTypeList;
            for (DatabaseTableRecord databaseTableRecord : records) {
                eventId = databaseTableRecord.getUUIDValue(
                        CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE, e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");
        }

    }

    //GET LIST PENDING EVENT
    public List<String> getAllEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            List<String> eventTypeList = new ArrayList<>();
            String eventString;
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (records.isEmpty())
                return eventTypeList;

            for (DatabaseTableRecord databaseTableRecord : records) {
                eventString = new StringBuilder()
                        .append("\n - ID = ").append(databaseTableRecord.getUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME))
                        .append("\n - STATUS = ").append(databaseTableRecord.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME))
                        .append(", ")
                        .append("\n - TYPE = ").append(databaseTableRecord.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME)).toString()
                ;

                eventTypeList.add(eventString);
            }

            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE, e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");
        }

    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventType(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value = records.get(0).getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        }

    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventStatus(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value = records.get(0).getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        }

    }

    //SAVE NEW EVENT
    public void saveNewEventTansaction(String eventType, String eventSource) throws CantSaveEventException {

        try {

            UUID eventId = UUID.randomUUID();
            Date time = new Date();
            long timestamp = time.getTime();

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            DatabaseTableRecord eventRecord = table.getEmptyRecord();

            eventRecord.setUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TYPE_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TIMESTAMP_COLUMN_NAME, timestamp);

            table.insertRecord(eventRecord);

            System.out.print("\n**** 17) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT ****\n");

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Distribution database");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }

    }

    //UPDATE STATUS THE EVENT
    public void updateEventTansactionStatus(UUID eventId, EventStatus eventStatus) throws CantRegisterCustomerBrokerUpdateEventException, CantUpdateRecordException {

        try {

            if (!eventExists(eventId))
                throw new CantRegisterCustomerBrokerUpdateEventException("Cant Update Status Customer Broker Update Negotiation Transaction, Event Id not exists.", "Customer Broker Update Negotiation Transaction, Update Event State", "Cant Update Event State Customer Broker Update Negotiation Transaction, Id not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_STATUS_COLUMN_NAME, eventStatus.getCode());

            table.updateRecord(record);

            //            System.out.print("\n\n**** 19.1) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT ****\n");

        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterCustomerBrokerUpdateEventException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "Customer Broker Update Negotiation Transaction Update Event Status Not Found", "unknown failure");
        }

    }
    /*END EVENT*/

    /*PRIVATE METHOD*/
    private Database getDataBase() {
        return database;
    }

    private DatabaseTable getDatabaseTransactionTable() {
        return getDataBase().getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
    }

    private DatabaseTable getDatabaseEventTable() {
        return getDataBase().getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
    }

    private boolean eventExists(UUID eventId) throws CantRegisterCustomerBrokerUpdateEventException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if event tablet exists", "Customer Broker Update Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateEventException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction Event Id Not Exists", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateEventException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction Event Id Not Exists", "unknown failure.");
        }

    }

    private boolean transactionExists(UUID transactionId) throws CantRegisterCustomerBrokerUpdateNegotiationTransactionException {
        try {
            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update tablet exists", "Customer Broker Update Negotiation Transaction", "");
            }
            table.addUUIDFilter(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction Id Not Exists", new StringBuilder().append("Cant load ").append(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerUpdateNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction Id Not Exists", "unknown failure.");
        }
    }

    private CustomerBrokerUpdate getCustomerBrokerUpdateFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID transactionId = record.getUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TRANSACTION_ID_COLUMN_NAME);
        UUID negotiationId = record.getUUIDValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_ID_COLUMN_NAME);
        String publicKeyBroker = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String publicKeyCustomer = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        NegotiationTransactionStatus status = NegotiationTransactionStatus.getByCode(record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_STATUS_TRANSACTION_COLUMN_NAME));
        NegotiationType negotiationType = NegotiationType.getByCode(record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_TYPE_COLUMN_NAME));
        String negotiationXML = record.getStringValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_NEGOTIATION_XML_COLUMN_NAME);
        long timestamp = record.getLongValue(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TIMESTAMP_COLUMN_NAME);

        return new CustomerBrokerUpdateImpl(transactionId, negotiationId, publicKeyBroker, publicKeyCustomer, status, negotiationType, negotiationXML, timestamp);
    }

    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetNegotiationTransactionListException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET NEGOTIATION TRANSACTION LISt. TABLE NO FOUNT.", "NEGOTIATION TRANSACTION CUSTOMER BROKER NEW", "CANT GET NEGOTIATION TRANSACTION LIST, TABLE NO FOUNT.");
            }
            List<String> negotiationList = new ArrayList<>();
            String negotiation;
            table.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (records.isEmpty()) {
                //There is no records in database, I'll return an empty list.
                return negotiationList;
            }

            for (DatabaseTableRecord databaseTableRecord : records) {
                negotiation = databaseTableRecord.getStringValue(valueColumn);
                negotiationList.add(negotiation);
            }

            return negotiationList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE, e, new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), new StringBuilder().append("Getting ").append(valueColumn).append(" based on ").append(key).toString(), "Cannot load the table into memory");
        }
    }

    private String getValue(String key, String keyColumn, String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_UPDATE_TABLE_NAME);
            table.addStringFilter(keyColumn, key, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value = records.get(0).getStringValue(valueColumn);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,
                    "Getting value from database",
                    "Cannot load the database table");
        }

    }

    private void checkDatabaseRecords(List<DatabaseTableRecord> records) throws UnexpectedResultReturnedFromDatabaseException {
        //Represents the maximum number of records in records
        int validResultsNumber = 1;
        int recordsSize;
        if (records.isEmpty()) {
            return;
        }
        recordsSize = records.size();
        if (recordsSize > validResultsNumber) {
            throw new UnexpectedResultReturnedFromDatabaseException(new StringBuilder().append("I excepted ").append(validResultsNumber).append(", but I got ").append(recordsSize).toString());
        }
    }
}
