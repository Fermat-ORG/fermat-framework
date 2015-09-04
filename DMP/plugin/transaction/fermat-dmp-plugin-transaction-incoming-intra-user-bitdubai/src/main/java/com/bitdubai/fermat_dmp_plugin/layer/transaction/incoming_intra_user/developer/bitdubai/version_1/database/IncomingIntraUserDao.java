package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantReadEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.09.03..
 */
public class IncomingIntraUserDao {

    private PluginDatabaseSystem pluginDatabaseSystem;
    private Database             database;

    public IncomingIntraUserDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws CantInitializeIncomingIntraUserCryptoRegistryException {

        if (pluginDatabaseSystem == null)
            throw new CantInitializeIncomingIntraUserCryptoRegistryException(CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, null, "Plugin Database System: null", "You have to set the PluginDatabaseSystem before initializing");

        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_DATABASE);
        } catch (DatabaseNotFoundException e) {
            IncomingIntraUserTransactionDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                this.database = databaseFactory.createDatabase(pluginId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_DATABASE);
            } catch (CantCreateDatabaseException exception) {
                throw new CantInitializeIncomingIntraUserCryptoRegistryException(CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why the Database couldn't be created");
            }
        } catch (CantOpenDatabaseException exception) {
            throw new CantInitializeIncomingIntraUserCryptoRegistryException(CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why we couldn't open the database");
        } catch (Exception exception) {
            throw new CantInitializeIncomingIntraUserCryptoRegistryException(CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "Check the cause ");
        }
    }

    public void saveNewEvent(EventType eventType, EventSource eventSource) throws CantInsertRecordException {
        String eventTypeAsString        = eventType.getCode();
        String eventSourceAsString      = eventSource.getCode();
        DatabaseTable eventsTable       = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME);
        DatabaseTableRecord eventRecord = eventsTable.getEmptyRecord();

        UUID eventRecordID = UUID.randomUUID();
        long unixTime = System.currentTimeMillis();
        eventRecord.setUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME, eventRecordID);
        eventRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_EVENT_COLUMN_NAME, eventTypeAsString);
        eventRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSourceAsString);
        eventRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME, "PENDING");
        eventRecord.setLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, unixTime);
        eventsTable.insertRecord(eventRecord);
    }

    public EventWrapper getNextPendingEvent(EventSource eventSource) throws CantLoadTableToMemoryException {
        DatabaseTable eventsTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME);
        eventsTable.setStringFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME, "PENDING", DatabaseFilterType.EQUAL);
        eventsTable.setStringFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME, eventSource.getCode(), DatabaseFilterType.EQUAL);

        eventsTable.loadToMemory();
        eventsTable.clearAllFilters();

        List<DatabaseTableRecord> events = eventsTable.getRecords();

        if (events == null || events.isEmpty()) {
            return null;
        }

        DatabaseTableRecord event = events.get(0);
        return new EventWrapper(
                event.getUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME),
                event.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_EVENT_COLUMN_NAME),
                event.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_SOURCE_COLUMN_NAME),
                event.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME),
                event.getLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME)
        );
    }

    public void disableEvent(UUID eventId) throws CantLoadTableToMemoryException, IncomingIntraUserCantSaveEventException, CantUpdateRecordException {
        DatabaseTable eventsTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME);
        eventsTable.setUUIDFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_ID_COLUMN_NAME, eventId, DatabaseFilterType.EQUAL);

        eventsTable.loadToMemory();
        List<DatabaseTableRecord> records = eventsTable.getRecords();
        if (records == null || records.isEmpty()) {
            throw new IncomingIntraUserCantSaveEventException(IncomingIntraUserCantSaveEventException.DEFAULT_MESSAGE, null, "Event ID: " + eventId, "I couldn't find the event with the given Id");
        } else if (records.size() > 1) {
            throw new IncomingIntraUserCantSaveEventException(IncomingIntraUserCantSaveEventException.DEFAULT_MESSAGE, null, "Event ID: " + eventId, "More than one Event with the given Id");
        }
        DatabaseTableRecord eventRecord = records.get(0);
        eventRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_STATUS_COLUMN_NAME, "DISABLED");
        eventsTable.updateRecord(eventRecord);
    }





    public boolean isANewTransaction(Transaction<CryptoTransaction> transaction) throws CantLoadTableToMemoryException {
        DatabaseTable registryTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        registryTable.setUUIDFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME, transaction.getTransactionID(), DatabaseFilterType.EQUAL);
        registryTable.loadToMemory();
        registryTable.clearAllFilters();
        return registryTable.getRecords().isEmpty();
    }

    public void saveTransactionAsAcknowledgedToBeNotified(Transaction<CryptoTransaction> transaction) throws CantInsertRecordException {
        saveTransactionInState(transaction, TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED);
    }

    public void updateTransactionToResponsibleToBeApplied(Transaction<CryptoTransaction> transaction) throws CantUpdateRecordException, CantLoadTableToMemoryException, IncomingIntraUserExpectedTransactionNotFoundException {
        updateTransactionToState(transaction, TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
    }





    private void saveTransactionInState(Transaction<CryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantInsertRecordException {
        DatabaseTable registryTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        DatabaseTableRecord transactionRecord = registryTable.getEmptyRecord();
        fillRegistryTableRecordFromCryptoTransaction(transactionRecord, transaction, transactionStatus, protocolStatus);
        registryTable.insertRecord(transactionRecord);
    }

    private void updateTransactionToState(Transaction<CryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantLoadTableToMemoryException, CantUpdateRecordException, IncomingIntraUserExpectedTransactionNotFoundException {
        DatabaseTable registryTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);

        DatabaseTableRecord recordToUpdate = getTransactionRecordFromId(transaction.getTransactionID());
        recordToUpdate.setStringValue(
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME,
                transactionStatus.getCode()
        );

        recordToUpdate.setStringValue(
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME,
                protocolStatus.getCode()
        );
        registryTable.updateRecord(recordToUpdate);
    }

    private DatabaseTableRecord getTransactionRecordFromId(UUID id) throws CantLoadTableToMemoryException, IncomingIntraUserExpectedTransactionNotFoundException {
        DatabaseTable registryTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        registryTable.setUUIDFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);

        registryTable.loadToMemory();
        registryTable.clearAllFilters();

        List<DatabaseTableRecord> records = registryTable.getRecords();

        if (records.size() > 1)
            throw new IncomingIntraUserExpectedTransactionNotFoundException("More than one transaction found with the same public key",null,"","");
        if (records.size() == 0)
            throw new IncomingIntraUserExpectedTransactionNotFoundException("Transaction not found",null,"","");

        return records.get(0);
    }


    private void fillRegistryTableRecordFromCryptoTransaction(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus) {

        databaseTableRecord.setUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME, transaction.getTransactionID());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_HASH_COLUMN_NAME, transaction.getInformation().getTransactionHash());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_FROM_COLUMN_NAME, transaction.getInformation().getAddressFrom().getAddress());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_TO_COLUMN_NAME, transaction.getInformation().getAddressTo().getAddress());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME, String.valueOf(transaction.getInformation().getCryptoCurrency().getCode()));
        databaseTableRecord.setLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_AMOUNT_COLUMN_NAME, transaction.getInformation().getCryptoAmount());

        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME, transaction.getAction().getCode());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_STATUS_COLUMN_NAME, transaction.getInformation().getCryptoStatus().getCode());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME, transactionStatus.getCode());
        databaseTableRecord.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME, protocolStatus.getCode());

        databaseTableRecord.setLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, transaction.getTimestamp());
    }


    public List<Transaction<CryptoTransaction>> getAllTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws InvalidParameterException, CantLoadTableToMemoryException {

        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();
        List<DatabaseTableRecord> records = getAllRecordsInState(transactionStatus, protocolStatus);

        for (DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }

    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantLoadTableToMemoryException {
        DatabaseTable registryTable = this.database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);

        registryTable.setStringFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME,
                transactionStatus.getCode(),
                DatabaseFilterType.EQUAL
        );

        registryTable.setStringFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME,
                protocolStatus.getCode(),
                DatabaseFilterType.EQUAL
        );
        registryTable.loadToMemory();
        registryTable.clearAllFilters();
        return registryTable.getRecords();
    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException {
        CryptoAddress cryptoAddressFrom = new CryptoAddress();
        cryptoAddressFrom.setAddress(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_FROM_COLUMN_NAME));
        cryptoAddressFrom.setCryptoCurrency(CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME)));
        CryptoAddress cryptoAddressTo = new CryptoAddress();
        cryptoAddressTo.setAddress(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ADDRESS_TO_COLUMN_NAME));
        cryptoAddressTo.setCryptoCurrency(CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME)));

        CryptoStatus cryptoStatus = CryptoStatus.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_STATUS_COLUMN_NAME));

        CryptoTransaction cryptoTransaction = new CryptoTransaction(
                databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_HASH_COLUMN_NAME),
                cryptoAddressFrom,
                cryptoAddressTo,
                CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_CURRENCY_COLUMN_NAME)),
                databaseTableRecord.getLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_CRYPTO_AMOUNT_COLUMN_NAME),
                cryptoStatus
        );
        Action action = Action.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME));

        return new Transaction<>(
            databaseTableRecord.getUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME),
            cryptoTransaction,
            action,
            databaseTableRecord.getLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TIMESTAMP_COLUMN_NAME)
        );
    }

}

