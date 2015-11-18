package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
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
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The class <code>IncomingIntraUserDao</code>
 * hide all the details of the access to the plugin's databases.
 */
public class IncomingIntraUserDao {

    private PluginDatabaseSystem pluginDatabaseSystem;
    private Database             database;

    public IncomingIntraUserDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public void initialize(UUID pluginId) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException {

        if (pluginDatabaseSystem == null)
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, null, "Plugin Database System: null", "You have to set the PluginDatabaseSystem before initializing");

        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_DATABASE);
        } catch (DatabaseNotFoundException e) {
            IncomingIntraUserTransactionDatabaseFactory databaseFactory = new IncomingIntraUserTransactionDatabaseFactory(pluginDatabaseSystem);
            try {
                this.database = databaseFactory.createDatabase(pluginId, IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_DATABASE);
            } catch (CantCreateDatabaseException exception) {
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why the Database couldn't be created");
            }
        } catch (CantOpenDatabaseException exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why we couldn't open the database");
        } catch (Exception exception) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "Check the cause ");
        }
    }

    public void saveNewEvent(FermatEnum eventType, EventSource eventSource) throws CantInsertRecordException {
        String              eventTypeAsString   = eventType.getCode();
        String              eventSourceAsString = eventSource.getCode();
        DatabaseTable       eventsTable         = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_EVENTS_RECORDED_TABLE_NAME);
        DatabaseTableRecord eventRecord         = eventsTable.getEmptyRecord();

        UUID eventRecordID = UUID.randomUUID();
        long unixTime      = System.currentTimeMillis();
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
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
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

    public void updateTransactionToResponsibleToBeApplied(Transaction<CryptoTransaction> transaction) throws CantUpdateRecordException, CantLoadTableToMemoryException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException {
        updateTransactionToState(transaction, TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
    }

    public void updateTransactionToApplied(UUID id) throws CantLoadTableToMemoryException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException, CantUpdateRecordException {
        DatabaseTable       registryTable  = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        DatabaseTableRecord recordToUpdate = getTransactionRecordFromId(id,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME);

        recordToUpdate.setStringValue(
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME,
                ProtocolStatus.APPLIED.getCode()
        );
        registryTable.updateRecord(recordToUpdate);
    }

    public List<TransactionCompleteInformation> getAllTransactionsToBeApplied() throws InvalidParameterException, CantLoadTableToMemoryException {
        List<TransactionCompleteInformation> transactionsToBeApplied      = new ArrayList<>();
        List<Transaction<CryptoTransaction>> cryptoTransactionsToEvaluate = getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
        Transaction<FermatCryptoTransaction> metadata;

        for(Transaction<CryptoTransaction> cryptoTransaction : cryptoTransactionsToEvaluate){
            metadata = getAssociatedMetadata(cryptoTransaction.getInformation().getTransactionHash());
            if(metadata != null)
                transactionsToBeApplied.add(new TransactionCompleteInformation(cryptoTransaction,metadata));
        }
        return transactionsToBeApplied;
    }

    private Transaction<FermatCryptoTransaction> getAssociatedMetadata(String associatedCryptoTransactionHash) throws CantLoadTableToMemoryException, InvalidParameterException {
        DatabaseTable       cryptoMetadataTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME);
        cryptoMetadataTable.setStringFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME,
                associatedCryptoTransactionHash,
                DatabaseFilterType.EQUAL);
        cryptoMetadataTable.loadToMemory();
        cryptoMetadataTable.clearAllFilters();
        List<DatabaseTableRecord> records = cryptoMetadataTable.getRecords();
        if(thereIsAnInconsistentNunberOfRecords(records))
            return null;
        return getFermatCryptoTransactionFromRecord(records.get(0));
    }

    private boolean thereIsAnInconsistentNunberOfRecords(List<DatabaseTableRecord> records) {
        return records.size() != 1;
    }


    private void saveTransactionInState(Transaction<CryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantInsertRecordException {
        DatabaseTable       registryTable     = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        DatabaseTableRecord transactionRecord = registryTable.getEmptyRecord();
        fillRegistryTableRecordFromCryptoTransaction(transactionRecord, transaction, transactionStatus, protocolStatus);
        registryTable.insertRecord(transactionRecord);
    }

    private void updateTransactionToState(Transaction<CryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantLoadTableToMemoryException, CantUpdateRecordException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException {
        DatabaseTable       registryTable  = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME);
        DatabaseTableRecord recordToUpdate = getTransactionRecordFromId(transaction.getTransactionID(),
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME);

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

    private DatabaseTableRecord getTransactionRecordFromId(UUID id,String tableName, String columnName) throws CantLoadTableToMemoryException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException {
        DatabaseTable registryTable = database.getTable(tableName);
        registryTable.setUUIDFilter(columnName, id, DatabaseFilterType.EQUAL);

        registryTable.loadToMemory();
        registryTable.clearAllFilters();

        List<DatabaseTableRecord> records = registryTable.getRecords();

        if (records.size() > 1)
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException("More than one transaction found with the same public key",null,"","");
        if (records.size() == 0)
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException("Transaction not found",null,"","");

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
        List<DatabaseTableRecord> records               = getAllRecordsInState(transactionStatus, protocolStatus,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TABLE_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_TRANSACTION_STATUS_COLUMN_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_PROTOCOL_STATUS_COLUMN_NAME);

        for (DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }

    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus,
                                                           String tableName,
                                                           String transactionStatusColumnName,
                                                           String protocolStatusColumnName) throws CantLoadTableToMemoryException {
        DatabaseTable registryTable = this.database.getTable(tableName);

        registryTable.setStringFilter(transactionStatusColumnName,
                transactionStatus.getCode(),
                DatabaseFilterType.EQUAL
        );

        registryTable.setStringFilter(protocolStatusColumnName,
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
        CryptoAddress cryptoAddressTo   = new CryptoAddress();
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


    /*
     * Crypto Metadata methods
     */
    public void saveFermatTransactionAsAcknowledgedToBeNotified(Transaction<FermatCryptoTransaction> transaction) throws CantInsertRecordException {
        saveFermatTransactionInState(transaction, TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED);
    }

    public boolean isANewFermatTransaction(Transaction<FermatCryptoTransaction> transaction) throws CantLoadTableToMemoryException {
        DatabaseTable cryptoMetadataTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME);
        cryptoMetadataTable.setUUIDFilter(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME, transaction.getTransactionID(), DatabaseFilterType.EQUAL);
        cryptoMetadataTable.loadToMemory();
        cryptoMetadataTable.clearAllFilters();
        return cryptoMetadataTable.getRecords().isEmpty();
    }

    public void saveFermatTransactionInState(Transaction<FermatCryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantInsertRecordException {
        DatabaseTable       cryptoMetadataTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME);
        DatabaseTableRecord transactionRecord   = cryptoMetadataTable.getEmptyRecord();
        fillCryptoMetadataTableRecordFromFermantCryptoTransaction(transactionRecord, transaction, transactionStatus, protocolStatus);
        cryptoMetadataTable.insertRecord(transactionRecord);
    }

    public static final String TRUE  = "T";
    public static final String FALSE = "F";

    private void fillCryptoMetadataTableRecordFromFermantCryptoTransaction(DatabaseTableRecord recordToFill, Transaction<FermatCryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus){
        String flag = transaction.getInformation().isAnswerToAPaymentRequest() ? TRUE : FALSE;

        recordToFill.setUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME, transaction.getTransactionID());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_REQUEST_FLAG_COLUMN_NAME, flag);
        if(transaction.getInformation().getRequestId()!=null) recordToFill.setUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_REQUEST_ID_COLUMN_NAME, transaction.getInformation().getRequestId());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME, transaction.getInformation().getSenderPublicKey());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME, transaction.getInformation().getDestinationPublicKey());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME,transaction.getInformation().getAssociatedCryptoTransactionHash());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME,transaction.getInformation().getPaymentDescription());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ACTION_COLUMN_NAME,transaction.getAction().getCode());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME,protocolStatus.getCode());
        recordToFill.setStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME,transactionStatus.getCode());
        recordToFill.setLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TIMESTAMP_COLUMN_NAME, transaction.getTimestamp());
    }

    public void updateFermatCryptoTransactionToResponsibleToBeApplied(Transaction<FermatCryptoTransaction> fermatCryptoTransactionTransaction) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
        updateFermatCryptoTransactionToState(fermatCryptoTransactionTransaction,TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
    }

    private void updateFermatCryptoTransactionToState(Transaction<FermatCryptoTransaction> transaction, TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws CantLoadTableToMemoryException, CantUpdateRecordException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException {
        DatabaseTable       cryptoMetadataTable = database.getTable(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME);
        DatabaseTableRecord recordToUpdate      = getTransactionRecordFromId(transaction.getTransactionID(),
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ID_COLUMN_NAME);

        recordToUpdate.setStringValue(
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME,
                transactionStatus.getCode()
        );

        recordToUpdate.setStringValue(
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME,
                protocolStatus.getCode()
        );
        cryptoMetadataTable.updateRecord(recordToUpdate);
    }


    public List<Transaction<FermatCryptoTransaction>> getAllFermatCryptoTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws InvalidParameterException, CantLoadTableToMemoryException {

        List<Transaction<FermatCryptoTransaction>> returnList = new ArrayList<>();
        List<DatabaseTableRecord>                  records    = getAllRecordsInState(transactionStatus, protocolStatus,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TABLE_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TRANSACTION_STATUS_COLUMN_NAME,
                IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PROTOCOL_STATUS_COLUMN_NAME);

        for (DatabaseTableRecord r : records)
            returnList.add(getFermatCryptoTransactionFromRecord(r));

        return returnList;
    }

    private Transaction<FermatCryptoTransaction> getFermatCryptoTransactionFromRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException {

        boolean flag                            = databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_REQUEST_FLAG_COLUMN_NAME).equals(TRUE);
        UUID    requestId                       = flag ? databaseTableRecord.getUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_REQUEST_ID_COLUMN_NAME) : null;
        String  senderPublicKey                 = databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME);;
        String  destinationPublicKey            = databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        String  associatedCryptoTransactionHash = databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME);;
        String  paymentDescription              = databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME);

        FermatCryptoTransaction fermatCryptoTransaction = new FermatCryptoTransaction(flag,requestId,senderPublicKey,destinationPublicKey,associatedCryptoTransactionHash,paymentDescription);
        Action action = Action.getByCode(databaseTableRecord.getStringValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ACTION_COLUMN_NAME));

        return new Transaction<>(
                databaseTableRecord.getUUIDValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_REGISTRY_ID_COLUMN_NAME),
                fermatCryptoTransaction,
                action,
                databaseTableRecord.getLongValue(IncomingIntraUserTransactionDatabaseConstants.INCOMING_INTRA_USER_CRYPTO_METADATA_TIMESTAMP_COLUMN_NAME)
        );
    }
}