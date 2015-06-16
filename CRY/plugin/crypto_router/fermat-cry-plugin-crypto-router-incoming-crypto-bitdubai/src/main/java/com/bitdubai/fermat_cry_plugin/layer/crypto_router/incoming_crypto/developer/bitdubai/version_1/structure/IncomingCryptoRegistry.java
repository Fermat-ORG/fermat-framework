package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.*;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantReadEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.ExpectedTransactionNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */

/**
 * Esta clase maneja una tabla en su base de datos con la siguiente estructura:
 *
 * Tabla: IncomingCryptoRegistry
 *
 * Campos:
 *
 * Id: Transaction ID
 * AddressTo
 * CryptoCurrency
 * CryptoAmount
 * AdressFrom
 * ReceptorType (call method getName() to ReceptorType enum member)
 * CryptoStatus (call method getName() to CryptoStatus enum member) (Identified, Received, Confirmed, Reversed)
 * NotificationStatus (call method getName() to NotificationStatus enum member) (NO_ACTION_REQUIRED, ETC)
 * TransactionStatus (call method getName() to TransactionStatus enum member) (PENDING_TRANSFER, IN_PROGRESS, PENDING_NOTIFICATION, NOTIFICATED, FINALIZED)
 * Timestamp
 *
 * La clase basicamente maneja consultas a su tabla IncomingCryptoRegistry.
 *
 *
 * Tabla: EventsRecorded
 *
 * Campos;
 *
 * Id
 * Event  (enum EventType.name())
 * Source (enum EventSource.name())
 * Status
 * Timestamp
 *
 * Nota: Definir los status de cada tabla en la capa numero 1 - Definiciones
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class IncomingCryptoRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * IncomingCryptoRegistry member variables.
     */
    private Database database;

    public void initialize(UUID pluginId) throws CantInitializeCryptoRegistryException {
        try {
            this.database = this.pluginDatabaseSystem.openDatabase(pluginId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE);
        } catch (DatabaseNotFoundException e) {
            IncomingCryptoDataBaseFactory databaseFactory = new IncomingCryptoDataBaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeCryptoRegistryException();
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCryptoRegistryException();
        }
    }


    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * IncomingCryptoRegistry member methods.
     */
    static class EventWrapper {
        final UUID eventId;
        final String eventType;
        final String eventSource;
        final String eventStatus;
        final long eventTimeStamp;

        public EventWrapper(UUID eventId, String eventType, String eventSource, String eventStatus, long eventTimeStamp) {
            this.eventId = eventId;
            this.eventType = eventType;
            this.eventSource = eventSource;
            this.eventStatus = eventStatus;
            this.eventTimeStamp = eventTimeStamp;
        }
    }

    void saveNewEvent(String eventType, String eventSource) throws CantSaveEvent {
        try {
            DatabaseTransaction dbTrx = this.database.newTransaction();
            DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = eventsTable.getEmptyRecord();

            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis() / 1000L;
            eventRecord.setUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName, eventRecordID);
            eventRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName, eventType);
            eventRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName, eventSource);
            eventRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING");
            eventRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName, unixTime);
            dbTrx.addRecordToInsert(eventsTable, eventRecord);
            this.database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            throw new CantSaveEvent();
        }
    }

    EventWrapper getNextPendingEvent() throws CantReadEvent {
        try {
            DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING", DatabaseFilterType.EQUAL);
            try {
                eventsTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantReadEvent();
            }

            List<DatabaseTableRecord> events = eventsTable.getRecords();
            if (events != null && !events.isEmpty()) {
                DatabaseTableRecord event = events.get(0);
                return new EventWrapper(
                        event.getUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName),
                        event.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName),
                        event.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName),
                        event.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName),
                        event.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName)
                );
            }
            return null;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantReadEvent();
        }
    }

    void disableEvent(UUID eventId) throws CantReadEvent, CantSaveEvent {
        try {
            DatabaseTransaction dbTrx = this.database.newTransaction();
            DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName, eventId, DatabaseFilterType.EQUAL);
            try {
                eventsTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantReadEvent();
            }
            List<DatabaseTableRecord> records = eventsTable.getRecords();
            if (records == null || records.isEmpty()) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception(String.format("I could not find the event with Id: %s", eventId)));
                throw new CantReadEvent();
            } else if (records.size() > 1) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception(String.format("More than one event with Id: %s", eventId)));
                throw new CantSaveEvent();
            }

            DatabaseTableRecord eventRecord = records.get(0);
            eventRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "DISABLED");
            dbTrx.addRecordToUpdate(eventsTable, eventRecord);
            this.database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            throw new CantSaveEvent();
        }
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * TransactionManager interface implementation.
     *
    //@Override
    public UUID getNextPendingTransactionByDestination(UUID destinationId) throws CantSearchForTransactionsException {
        try {
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName, destinationId, DatabaseFilterType.EQUAL);
            registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, "NOTIFICATED", DatabaseFilterType.EQUAL);
            try {
                registryTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantSearchForTransactionsException();
            }

            for (DatabaseTableRecord record : registryTable.getRecords()) {
                UUID trxID = record.getUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName);
                if (trxID != null) return trxID;
            }
            return null;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantSearchForTransactionsException();
        }
    }

    public boolean releaseTransaction(UUID trxID) throws CantReleaseTransactionException {
        try {
            DatabaseTransaction dbTrx = this.database.newTransaction();
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, trxID, DatabaseFilterType.EQUAL);
            try {
                registryTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantReleaseTransactionException();
            }
            List<DatabaseTableRecord> records = registryTable.getRecords();
            if (records == null || records.isEmpty()) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception(String.format("I could not find the transaction with trxId: %s", trxID)));
                throw new CantReleaseTransactionException();
            } else if (records.size() > 1) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception(String.format("More than one transaction with trxId: %s", trxID)));
                throw new CantReleaseTransactionException();
            }

            DatabaseTableRecord trxRecord = records.get(0);
            trxRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, "FINALIZED");
            dbTrx.addRecordToUpdate(registryTable, trxRecord);
            this.database.executeTransaction(dbTrx);
            return true;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantReleaseTransactionException();
        }
    }
*/

    // Used by the Monitor Agent

    // Las coloca en (A,TBN)
    public void acknowledgeTransactions(List<Transaction<CryptoTransaction>> transactionList) { // throws CantAcknowledgeTransactionException
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        //DatabaseTransaction databaseTransaction = this.database.newTransaction();
        for(Transaction<CryptoTransaction> transaction : transactionList) {
            // We first check if we have this transaction registered as (A,TBN). This would not be
            // a mistake. It just mean that the system shut down before we could confirm reception to
            // the sender and the sender sent it again.
            registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

            try {
                registryTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                //TODO: MANAGE EXCEPTION
            }

            List<DatabaseTableRecord> records = registryTable.getRecords();
            if (records.isEmpty()) {
                // if it is empty this is a new transaction
                // If it is a new transaction we save it as usual
                DatabaseTableRecord transactionRecord = registryTable.getEmptyRecord();
                fillRegistryTableRecord(transactionRecord, transaction, TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED, Specialist.UNKNOWN);
                try {
                    registryTable.insertRecord(transactionRecord);
                } catch (CantInsertRecord cantInsertRecord) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantInsertRecord);
                    // TODO: MANAGE EXCEPTION.
                }
            }

            // if it is not empty we ignore the transaction because we already have it.

            registryTable.clearAllFilters();
        }
    }

    // Retorna las que están en (A,TBN)
    List<Transaction<CryptoTransaction>> getAcknowledgedTransactions() {//throws CantGetTransactionsException

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName ,
                                      TransactionStatus.ACKNOWLEDGED.name(),
                                      DatabaseFilterType.EQUAL
                                     );
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName ,
                ProtocolStatus.TO_BE_NOTIFIED.name(),
                DatabaseFilterType.EQUAL
        );

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<Transaction<CryptoTransaction>> tbaList = new ArrayList<>();
        List<DatabaseTableRecord> records = registryTable.getRecords();

        for(DatabaseTableRecord r : records)
            tbaList.add(getTransactionFromRecord(r));

        registryTable.clearAllFilters();

        return tbaList;
    }

    // Pasa una a (R,NAR)
    void acquireResponsibility(Transaction<CryptoTransaction> transaction) { // throws CantAcquireResponsibility
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        // We look for the record to update
        registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();
        if (records.size() != 1) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new ExpectedTransactionNotFoundException());
            //TODO: MANAGE EXCEPTION
        } else {
            DatabaseTableRecord recordToUpdate = records.get(0);
            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    TransactionStatus.RESPONSIBLE.name()
                                         );

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.NO_ACTION_REQUIRED.name()
                                         );
            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                //TODO: MANAGE EXCEPTION
            }
        }

    }


    // Used by Relay Agent
    // Retorna las (R,NAR)
    List<Transaction<CryptoTransaction>> getResponsibleNARTransactions() { //throws CantAccessTransactionsException

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName ,
                TransactionStatus.RESPONSIBLE.name(),
                DatabaseFilterType.EQUAL
        );
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName ,
                ProtocolStatus.NO_ACTION_REQUIRED.name(),
                DatabaseFilterType.EQUAL
        );

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<Transaction<CryptoTransaction>> narList = new ArrayList<>();
        List<DatabaseTableRecord> records = registryTable.getRecords();

        for(DatabaseTableRecord r : records)
            narList.add(getTransactionFromRecord(r));

        registryTable.clearAllFilters();

        return narList;
    }

    // Pasa la transacción a TBN y le agrega el Specialist.
    void setToNotify(UUID id, Specialist specialist) {
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        // We look for the record to update
        registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, id, DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();
        if (records.size() != 1) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new ExpectedTransactionNotFoundException());
            //TODO: MANAGE EXCEPTION
        } else {
            DatabaseTableRecord recordToUpdate = records.get(0);

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.TO_BE_NOTIFIED.name()
            );

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,
                    specialist.name()
            );

            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                //TODO: MANAGE EXCEPTION
            }
        }
    }

    // La lista de (R,TBN) o (R,SN)
    private List<Transaction<CryptoTransaction>> getResponsibleTransactionsPendingAction() {//throws CantAccessTransactionsException;
        List<Transaction<CryptoTransaction>> tbnList = getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_NOTIFIED);
        List<Transaction<CryptoTransaction>> snList = getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.SENDING_NOTIFIED);
        List<Transaction<CryptoTransaction>> concatenatedList = new ArrayList<>();
        concatenatedList.addAll(tbnList);
        concatenatedList.addAll(snList);
        return concatenatedList;
    }

    // Da los Specialist de las que están en TBN y SN
    public List<String> getSpecialists() {//throws CantReadSpecialistsException

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        DatabaseTableRecord r;

        List<Transaction<CryptoTransaction>> transactionList = getResponsibleTransactionsPendingAction();

        List<String> specialistList = new ArrayList<>();


        for(Transaction<CryptoTransaction> transaction : transactionList) {


            // We look for the record to update
            registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

            try {
                registryTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                //TODO: MANAGE EXCEPTION
            }

            List<DatabaseTableRecord> records = registryTable.getRecords();
            if (records.size() != 1) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new ExpectedTransactionNotFoundException());
                //TODO: MANAGE EXCEPTION
            } else {
                r = records.get(0);
                specialistList.add(r.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName));
            }

            registryTable.clearAllFilters();
        }

        return specialistList;
    }

    // Pasa las que son TBN a SN
    void setToSendingNotified() { // throws CantSaveTransactionsException

        // First we get the TO_BE_NOTIFIED transactions
        List<DatabaseTableRecord> tbnList = getAllRecordsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_NOTIFIED);

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        for(DatabaseTableRecord recordToUpdate: tbnList) {
            recordToUpdate.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                                          ProtocolStatus.SENDING_NOTIFIED.name());
            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                // TODO: MANAGE EXCEPTION.
            }
        }
    }

    /*
     * TransactionSender interface implementation
     */

    /*
     * El método confirmedReception marca la transacción marcada como argumento como (DELIVERED,RECEPTION_NOTIFIED).
     */
    public void confirmReception(UUID transactionId){

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        registryTable.setUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transactionId, DatabaseFilterType.EQUAL);
        DatabaseTableRecord recordToUpdate;

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();
        if (records.size() != 1) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new ExpectedTransactionNotFoundException());
            //TODO: MANAGE EXCEPTION
        } else {
            recordToUpdate = records.get(0);
            // We will update it independently if it was or no in the (D,RN) state
            // If it was updated it means the system shut down before the receptor could register
            // that he had notified the reception.
            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    TransactionStatus.DELIVERED.name()
            );
            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.RECEPTION_NOTIFIED.name()
            );

            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                // TODO: MANAGE EXCEPTION.
            }

        }
        registryTable.clearAllFilters();

    }

    /*
     *  El método getPendingTransactions retorna la lista de todas las transacciones con ProtocolStatus SENDING_NOTIFIED que tienen como Specialist al pasado como argumento. Notar que no camba el estado de ninguna transacción.
     */
    public List<Transaction<CryptoTransaction>> getPendingTransactions(Specialist specialist){

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,ProtocolStatus.SENDING_NOTIFIED.name(),DatabaseFilterType.EQUAL);
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,specialist.name(),DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }


        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();

        List<DatabaseTableRecord> records = registryTable.getRecords();

        for(DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }




    private void fillRegistryTableRecord(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus,
                                         Specialist specialist){

        databaseTableRecord.setUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName, transaction.getInformation().getTransactionHash());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName, transaction.getInformation().getAddressFrom());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName, transaction.getInformation().getAddressTo());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName,transaction.getInformation().getCryptoCurrency().getCode());
        databaseTableRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName,transaction.getInformation().getCryptoAmount());

        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName,transaction.getAction());
        // We set the specialist as a parameter
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,specialist.name());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName, transaction.getInformation().getCryptoStatus());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, transactionStatus.name());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName, protocolStatus.name());

        databaseTableRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName, transaction.getTimestamp());

    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord){

          CryptoTransaction cryptoTransaction = new CryptoTransaction(
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName),
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName),
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName),
                  CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)),
                  databaseTableRecord.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName),
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName)
                                                                     );

          return new Transaction<>(
                  databaseTableRecord.getUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName),
                  cryptoTransaction,
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName),
                  databaseTableRecord.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName)
                                  );
    }


    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName ,
                transactionStatus.name(),
                DatabaseFilterType.EQUAL
        );

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName ,
                protocolStatus.name(),
                DatabaseFilterType.EQUAL
        );

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        registryTable.clearAllFilters();

        return registryTable.getRecords();
    }

    private List<Transaction<CryptoTransaction>> getAllTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {

        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();

        List<DatabaseTableRecord> records = getAllRecordsInState(transactionStatus,protocolStatus);

        for(DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }
}
