package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Negotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransactionListException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseEventException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseNegotiationTransactionDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    private Database database;

    public CustomerBrokerCloseNegotiationTransactionDatabaseDao(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final Database database) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
    }

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
        } catch (DatabaseNotFoundException e) {
            try {
                CustomerBrokerCloseNegotiationTransactionDatabaseFactory databaseFactory = new CustomerBrokerCloseNegotiationTransactionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, CustomerBrokerCloseNegotiationTransactionDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeCustomerBrokerCloseNegotiationTransactionDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    //CREATE SEND NEW NEGOTIATION TRANSACTION
    public void createCustomerBrokerCloseNegotiationTransaction(UUID transactionId, Negotiation negotiation, NegotiationType negotiationType, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {

        Date time = new Date();
        long timestamp = time.getTime();
        String negotiationXML = XMLParser.parseObject(negotiation);

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId);
            record.setUUIDValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME, negotiation.getNegotiationId());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_PUBLIC_KEY_BROKER_COLUMN_NAME, negotiation.getBrokerPublicKey());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME, negotiation.getCustomerPublicKey());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_STATUS_COLUMN_NAME, statusTransaction.getCode());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_TYPE_COLUMN_NAME, negotiationType.getCode());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_XML_COLUMN_NAME, negotiationXML);
            record.setLongValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TIMESTAMP_COLUMN_NAME, timestamp);
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_SEND_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString());
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString());

            table.insertRecord(record);

            if (statusTransaction.getCode() == NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode()) {
                System.out.print(new StringBuilder().append("\n\n**** 22) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - DAO. CONFIRM negotiationType: ").append(negotiationType.getCode()).append(" transactionId: ").append(transactionId).append(" ****\n").toString());
            } else {
                System.out.print(new StringBuilder().append("\n\n**** 4) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - DAO. NEGOTIATION negotiationType: ").append(negotiationType.getCode()).append("transactionId: ").append(transactionId).append(" ****\n").toString());
            }

        } catch (CantInsertRecordException e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, "customer broker close Negotiation Transaction", "Cant create new customer broker close Negotiation Transaction, insert database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "customer broker close Negotiation Transaction", "Cant create new customer broker close Negotiation Transaction, unknown failure.");
        }

    }

    //UPDATE STATUS NEW NEGOTIATION TRANSACTION
    public void updateStatusRegisterCustomerBrokerCloseNegotiationTranasction(UUID transactionId, NegotiationTransactionStatus statusTransaction) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {
        try {
            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException("Cant Update Status customer broker close Negotiation Transaction, not exists.", "customer broker close Negotiation Transaction, Update State", "Cant Update State customer broker close Negotiation Transaction, not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_STATUS_COLUMN_NAME, statusTransaction.getCode());
            table.updateRecord(record);

            System.out.print(new StringBuilder().append("\n\n**** 8) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - DAO - UPDATE STATUS NEGOTIATION STATUS : ").append(NegotiationTransactionStatus.SENDING_NEGOTIATION.getCode()).append(" ****\n").toString());
        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, "customer broker close Negotiation Transaction, Update State", "Cant Update State customer broker close Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "customer broker close Negotiation Transaction, Update State", "Cant Update State customer broker close Negotiation Transaction, unknown failure.");
        }
    }

    //GET NEGOTIATION TRANSACTION FROM TRANSACTION ID
    public CustomerBrokerClose getRegisterCustomerBrokerCloseNegotiationTranasction(UUID transactionId) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {

        try {

            CustomerBrokerClose getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker close exists", "customer broker close Negotiation Transaction", "");
            }
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerCloseFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(em.getMessage(), em, "customer broker close Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "customer broker close Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET NEGOTIATION TRANSACTIO FROM NEGOTIATION ID
    public CustomerBrokerClose getRegisterCustomerBrokerCloseNegotiationTranasctionFromNegotiationId(UUID negotiationId) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {

        try {

            CustomerBrokerClose getTransaction = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if Customer Broker Update exists", "Customer Broker Update Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME, negotiationId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                return getTransaction;

            for (DatabaseTableRecord records : record) {
                getTransaction = getCustomerBrokerCloseFromRecord(records);
            }

            return getTransaction;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEW NEGOTIATION TRANSACTION
    public List<CustomerBrokerClose> getAllRegisterCustomerBrokerCloseNegotiationTranasction() throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {


        try {

            List<CustomerBrokerClose> getTransactions = null;

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker close exists", "customer broker close Negotiation Transaction", "");
            }
            table.loadToMemory();
            record = table.getRecords();
            if (record.size() == 0)
                throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException("The number of records is 0 ", null, "", "");

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerCloseFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(em.getMessage(), em, "customer broker close Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "customer broker close Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEGOTIATION TRANSACTION PENDING TO SUBMIT
    public List<CustomerBrokerClose> getPendingToSubmitNegotiation() throws CantGetNegotiationTransactionListException {

        try {

            List<CustomerBrokerClose> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Close Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_STATUS_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerCloseFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    //GET LIST NEW NEGOTIATION TRANSACTION PENDING TO CONFIRM
    public List<CustomerBrokerClose> getPendingToConfirmtNegotiation() throws CantGetNegotiationTransactionListException {

        try {

            List<CustomerBrokerClose> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker update exists", "Customer Broker Close Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_STATUS_COLUMN_NAME, NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM.getCode(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerCloseFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Update Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Update Negotiation Transaction not return register", "unknown failure.");
        }

    }

    /**
     * Get List of all negotiation transaction with CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME in FALSE
     *
     * @return List of All CustomerBrokerClose register with CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME in FALSE
     * @throws CantRegisterCustomerBrokerCloseNegotiationTransactionException
     */
    public List<CustomerBrokerClose> getPendingToConfirmTransactionNegotiation() throws CantGetNegotiationTransactionListException {

        try {
            List<CustomerBrokerClose> getTransactions = new ArrayList<>();

            List<DatabaseTableRecord> record;
            DatabaseTable table = getDatabaseTransactionTable();
            if (table == null)
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker new exists", "Customer Broker Close Negotiation Transaction", "");

            table.addStringFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.FALSE.toString(), DatabaseFilterType.EQUAL);
            table.loadToMemory();
            record = table.getRecords();
            if (record.isEmpty())
                return getTransactions;

            for (DatabaseTableRecord records : record) {
                getTransactions.add(getCustomerBrokerCloseFromRecord(records));
            }

            return getTransactions;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantGetNegotiationTransactionListException(em.getMessage(), em, "Customer Broker Close Negotiation Transaction not return register", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantGetNegotiationTransactionListException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Close Negotiation Transaction not return register", "unknown failure.");
        }
    }

    /**
     * Change the CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME to TRUE, indicated confirmation that the negotiation transmision is done
     *
     * @param transactionId
     * @throws CantRegisterCustomerBrokerCloseNegotiationTransactionException
     */
    public void confirmTransaction(UUID transactionId) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {
        try {

            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException("Cant Update Status Customer Broker Close Negotiation Transaction, not exists.", "Customer Broker Close Negotiation Transaction, Update State", "Cant Update State Customer Broker Close Negotiation Transaction, not exists");

            DatabaseTable table = getDatabaseTransactionTable();
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME, Boolean.TRUE.toString());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, "Customer Broker Close Negotiation Transaction, Update Confirm Transaction", "Cant Update Confirm Transaction Customer Broker Close Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Negotiation Transaction, Customer Broker Close", "Cant confirm Transaction, unknown failure.");
        }
    }

    /**
     * Change the CUSTOMER_BROKER_CLOSE_CONFIRM_TRANSACTION_COLUMN_NAME to TRUE, indicated confirmation that the negotiation transmision is done
     *
     * @param transactionId
     * @throws CantRegisterCustomerBrokerCloseNegotiationTransactionException
     */
    public void sendTransaction(UUID transactionId) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {
        try {

            if (!transactionExists(transactionId))
                throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException("Cant Update Status Customer Broker Close Negotiation Transaction, not exists.", "Customer Broker Close Negotiation Transaction, Update State", "Cant Update State Customer Broker Close Negotiation Transaction, not exists");

            DatabaseTable table = getDatabaseTransactionTable();
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_SEND_TRANSACTION_COLUMN_NAME, Boolean.TRUE.toString());
            table.updateRecord(record);

        } catch (CantUpdateRecordException e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, "Customer Broker Close Negotiation Transaction, Update Confirm Transaction", "Cant Update Confirm Transaction Customer Broker Close Negotiation Transaction, update database problems.");
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "Negotiation Transaction, Customer Broker Close", "Cant confirm Transaction, unknown failure.");
        }
    }
/*
    //GET TRANSACTION ID OF NEGOTIATION TRANSACTION
    public UUID getTransactionId(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        String transactionId= getValue(
                negotiationId,
                CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME);

        return UUID.fromString(transactionId);

    }

    //GET NEGOTIATION TYPE OF NEGOTIATION TRANSACTION
    public NegotiationType getNegotiationType(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {

        try {

            String contractTypeCode=getValue(
                    negotiationId,
                    CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME,
                    CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_TYPE_COLUMN_NAME);
            return NegotiationType.getByCode(contractTypeCode);

        } catch (InvalidParameterException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e,"Getting Contract Type from database","The contractType in database is invalid");
        }

    }

    //GET NEGOTIATION XML OF NEGOTIATION TRANSACTION
    public String getNegotiationXML(String negotiationId) throws UnexpectedResultReturnedFromDatabaseException {
        return getValue(
                negotiationId,
                CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME,
                CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_XML_COLUMN_NAME);

    }
    */
    /*END TRANSACTION*/

    /*EVENT*/
    //GET LIST PENDING EVENT
    public List<UUID> getPendingEvents() throws UnexpectedResultReturnedFromDatabaseException, CantGetNegotiationTransactionListException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
            List<UUID> eventTypeList = new ArrayList<>();
            UUID eventId;
            table.addStringFilter(
                    CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_STATUS_COLUMN_NAME,
                    EventStatus.PENDING.getCode(),
                    DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            if (records.isEmpty()) {
                //There is no records in database, I'll return an empty list.
                return eventTypeList;
            }
            for (DatabaseTableRecord databaseTableRecord : records) {
                eventId = databaseTableRecord.getUUIDValue(
                        CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_ID_COLUMN_NAME);
                eventTypeList.add(eventId);
            }
            return eventTypeList;

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetNegotiationTransactionListException(CantGetNegotiationTransactionListException.DEFAULT_MESSAGE, e, "Getting events in EventStatus.PENDING", "Cannot load the table into memory");
        }
    }

    //GET EVENT TYPE OF TRANSACTION
    public String getEventType(UUID eventId) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            List<DatabaseTableRecord> records = table.getRecords();
            checkDatabaseRecords(records);
            String value = records.get(0).getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TYPE_COLUMN_NAME);

            return value;

        } catch (CantLoadTableToMemoryException e) {
            throw new UnexpectedResultReturnedFromDatabaseException(e, "Getting value from database", "Cannot load the database table");
        }

    }

    //SAVE NEW EVENT
    public void saveNewEventTansaction(String eventType, String eventSource) throws CantSaveEventException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
            DatabaseTableRecord eventRecord = table.getEmptyRecord();
            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            //Logger LOG = Logger.getGlobal();
            //LOG.info("Distribution DAO:\nUUID:" + eventRecordID + "\n" + unixTime);
            eventRecord.setUUIDValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_ID_COLUMN_NAME, eventRecordID);
            eventRecord.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TYPE_COLUMN_NAME, eventType);
            eventRecord.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_SOURCE_COLUMN_NAME, eventSource);
            eventRecord.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_STATUS_COLUMN_NAME, EventStatus.PENDING.getCode());
            eventRecord.setLongValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TIMESTAMP_COLUMN_NAME, unixTime);
            table.insertRecord(eventRecord);

        } catch (CantInsertRecordException exception) {
            throw new CantSaveEventException(exception, "Saving new event.", "Cannot insert a record in Asset Distribution database");
        } catch (Exception exception) {
            throw new CantSaveEventException(FermatException.wrapException(exception), "Saving new event.", "Unexpected exception");
        }
    }

    //UPDATE STATUS THE EVENT
    public void updateEventTansactionStatus(UUID eventId, EventStatus eventStatus) throws CantRegisterCustomerBrokerCloseEventException, CantUpdateRecordException {
        try {

            if (!eventExists(eventId))
                throw new CantRegisterCustomerBrokerCloseEventException("Cant Update Status Customer Broker Close Negotiation Transaction, Event Id not exists.", "Customer Broker Close Negotiation Transaction, Update Event State", "Cant Update Event State Customer Broker Close Negotiation Transaction, Id not exists");

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            DatabaseTableRecord record = table.getEmptyRecord();
            record.setStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_STATUS_COLUMN_NAME, eventStatus.getCode());

            table.updateRecord(record);

//            System.out.print("\n\n**** 19.1) MOCK NEGOTIATION TRANSACTION - NEGOTIATION TRANSMISSION - DAO - REGISTER NEW EVENT ****\n");

        } catch (CantLoadTableToMemoryException e) {
            throw new CantRegisterCustomerBrokerCloseEventException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, e, "Customer Broker Close Negotiation Transaction Update Event Status Not Found", "unknown failure");
        }
    }
    
    /*END EVENT*/

    /*PRIVATE METHOD*/

    private Database getDataBase() {
        return database;
    }

    private DatabaseTable getDatabaseTransactionTable() {
        return getDataBase().getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
    }

    private DatabaseTable getDatabaseEventTable() {
        return getDataBase().getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
    }

    private boolean eventExists(UUID eventId) throws CantRegisterCustomerBrokerCloseEventException {

        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_TABLE_NAME);
            if (table == null)
                throw new CantRegisterCustomerBrokerCloseEventException("Cant check if event tablet exists", "Customer Broker Close Negotiation Transaction", "");

            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_EVENT_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerCloseEventException(em.getMessage(), em, "Customer Broker Close Negotiation Transaction Event Id Not Exists", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseEventException(e.getMessage(), FermatException.wrapException(e), "Customer Broker Close Negotiation Transaction Event Id Not Exists", "unknown failure.");
        }

    }

    private boolean transactionExists(UUID transactionId) throws CantRegisterCustomerBrokerCloseNegotiationTransactionException {
        try {
            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if customer broker close tablet exists", "customer broker close Negotiation Transaction", "");
            }
            table.addUUIDFilter(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME, transactionId, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            return table.getRecords().size() > 0;
        } catch (CantLoadTableToMemoryException em) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(em.getMessage(), em, "customer broker close Negotiation Transaction Id Not Exists", new StringBuilder().append("Cant load ").append(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME).append(" table in memory.").toString());
        } catch (Exception e) {
            throw new CantRegisterCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), "customer broker close Negotiation Transaction Id Not Exists", "unknown failure.");
        }
    }

    private CustomerBrokerClose getCustomerBrokerCloseFromRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID transactionId = record.getUUIDValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TRANSACTION_ID_COLUMN_NAME);
        UUID negotiationId = record.getUUIDValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_ID_COLUMN_NAME);
        String publicKeyBroker = record.getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        String publicKeyCustomer = record.getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        NegotiationTransactionStatus status = NegotiationTransactionStatus.getByCode(record.getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_STATUS_COLUMN_NAME));
        NegotiationType negotiationType = NegotiationType.getByCode(record.getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_TYPE_COLUMN_NAME));
        String negotiationXML = record.getStringValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_NEGOTIATION_XML_COLUMN_NAME);
        long timestamp = record.getLongValue(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TIMESTAMP_COLUMN_NAME);

        return new CustomerBrokerCloseImpl(transactionId, negotiationId, publicKeyBroker, publicKeyCustomer, status, negotiationType, negotiationXML, timestamp);
    }

    private String getValue(String key, String keyColumn, String valueColumn) throws UnexpectedResultReturnedFromDatabaseException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
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

    private List<String> getStringList(String key, String keyColumn, String valueColumn) throws CantGetNegotiationTransactionListException {
        try {

            DatabaseTable table = this.database.getTable(CustomerBrokerCloseNegotiationTransactionDatabaseConstants.CUSTOMER_BROKER_CLOSE_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET NEGOTIATION TRANSACTION LISt. TABLE NO FOUNT.", "NEGOTIATION TRANSACTION customer broker close", "CANT GET NEGOTIATION TRANSACTION LIST, TABLE NO FOUNT.");
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
