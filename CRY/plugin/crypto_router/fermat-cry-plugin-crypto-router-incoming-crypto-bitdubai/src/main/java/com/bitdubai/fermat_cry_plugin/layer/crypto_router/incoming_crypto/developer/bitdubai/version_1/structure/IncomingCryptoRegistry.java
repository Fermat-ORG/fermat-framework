package com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
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
import java.util.EnumSet;
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

public class IncomingCryptoRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, TransactionProtocolManager<CryptoTransaction> {

    static int flag = 0;

    public void proofTransaction() {

        if(this.flag == 0)
            return;

        this.flag++;

        List<Transaction<CryptoTransaction>> transactionList = new ArrayList<>();
        CryptoTransaction c = new CryptoTransaction("random",
                new CryptoAddress("addFrom", CryptoCurrency.BITCOIN),
                new CryptoAddress("addTo", CryptoCurrency.BITCOIN),
                CryptoCurrency.BITCOIN, 1, CryptoStatus.CONFIRMED
        );
        /*
        Campos del BitcoinTransaction
        String transactionHash,
        CryptoAddress addressFrom,
        CryptoAddress addressTo,
        CryptoCurrency cryptoCurrency,
        long cryptoAmount,
        CryptoStatus cryptoStatus
         */
        Transaction<CryptoTransaction> t = new Transaction<>(UUID.randomUUID(), c, Action.APPLY, System.currentTimeMillis() / 1000L);
        transactionList.add(t);
        acknowledgeTransactions(transactionList);
        acquireResponsibility(t);
        System.out.println("TTF INCOMING CRYPTO: TRANSACTION INITIALIZED BY REGISTRY");
    }


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

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * IncomingCryptoRegistry member methods.
     */
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
                throw new CantInitializeCryptoRegistryException("Failed attempt to create IncomingCrypto database",cantCreateDatabaseException,"Database Name: "+IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE,"");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCryptoRegistryException("Failed attempt to open IncomingCrypto database",cantOpenDatabaseException,"Database Name: "+IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE,"");
        }
    }


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
            long unixTime = System.currentTimeMillis();
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

            DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING", DatabaseFilterType.EQUAL);
            try {
                eventsTable.loadToMemory();
            } catch (CantLoadTableToMemory cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantReadEvent("Failed Attempt to read event",cantLoadTableToMemory,"","");
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
                throw new CantReadEvent("Failed attempt to read event",cantLoadTableToMemory,"","");
            }
            List<DatabaseTableRecord> records = eventsTable.getRecords();
            if (records == null || records.isEmpty()) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantReadEvent("", null,"",String.format("I could not find the event with Id: %s", eventId));
            } else if (records.size() > 1) {
                Exception e = new Exception(String.format("More than one event with Id: %s", eventId))
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
                fillRegistryTableRecord(transactionRecord, transaction, TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED, Specialist.UNKNOWN_SPECIALIST);
                try {
                    registryTable.insertRecord(transactionRecord);
                } catch (CantInsertRecordException cantInsertRecord) {
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
                                      TransactionStatus.ACKNOWLEDGED.getCode(),
                                      DatabaseFilterType.EQUAL
                                     );
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName ,
                ProtocolStatus.TO_BE_NOTIFIED.getCode(),
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
                    TransactionStatus.RESPONSIBLE.getCode()
                                         );

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.NO_ACTION_REQUIRED.getCode()
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
                TransactionStatus.RESPONSIBLE.getCode(),
                DatabaseFilterType.EQUAL
        );
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                ProtocolStatus.NO_ACTION_REQUIRED.getCode(),
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


        for (DatabaseTableRecord r : records){
            narList.add(getTransactionFromRecord(r));
        }

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
        if(records == null)
            return;


        if (records.size() != 1) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new ExpectedTransactionNotFoundException());
            //TODO: MANAGE EXCEPTION
        } else {
            DatabaseTableRecord recordToUpdate = records.get(0);

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.TO_BE_NOTIFIED.getCode()
            );

            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,
                    specialist.getCode()
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
    public EnumSet<Specialist> getSpecialists() throws InvalidParameterException {//throws CantReadSpecialistsException

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        DatabaseTableRecord r;

        List<Transaction<CryptoTransaction>> transactionList = getResponsibleTransactionsPendingAction();

        EnumSet<Specialist> specialistEnumSet = EnumSet.noneOf(Specialist.class);


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
                specialistEnumSet.add(Specialist.getByCode(r.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName)));
            }

            registryTable.clearAllFilters();
        }

        return specialistEnumSet;
    }

    // Pasa las que son TBN a SN
    void setToSendingNotified() { // throws CantSaveTransactionsException

        // First we get the TO_BE_NOTIFIED transactions
        List<DatabaseTableRecord> tbnList = getAllRecordsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_NOTIFIED);

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        for(DatabaseTableRecord recordToUpdate: tbnList) {
            recordToUpdate.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                                          ProtocolStatus.SENDING_NOTIFIED.getCode());
            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                // TODO: MANAGE EXCEPTION.
            }
        }
    }

    /*
     * TransactionProtocolManager interface implementation
     */

    /*
     * El método confirmedReception marca la transacción marcada como argumento como (DELIVERED,RECEPTION_NOTIFIED).
     */
    public void confirmReception(UUID transactionId) throws CantConfirmTransactionException {

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
                    TransactionStatus.DELIVERED.getCode()
            );
            recordToUpdate.setStringValue(
                    IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.RECEPTION_NOTIFIED.getCode()
            );

            try {
                registryTable.updateRecord(recordToUpdate);
            } catch (CantUpdateRecord cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                throw new CantConfirmTransactionException();
                // TODO: MANAGE EXCEPTION.
            }

        }

        registryTable.clearAllFilters();
        System.out.println("TTF - INCOMING CRYPTO: RECEPTION CONFIRMED BY SPECIALIST");
    }

    /*
     *  El método getPendingTransactions retorna la lista de todas las transacciones con ProtocolStatus SENDING_NOTIFIED que tienen como Specialist al pasado como argumento. Notar que no camba el estado de ninguna transacción.
     */
    public List<Transaction<CryptoTransaction>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,ProtocolStatus.SENDING_NOTIFIED.getCode(),DatabaseFilterType.EQUAL);
        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, TransactionStatus.RESPONSIBLE.getCode(), DatabaseFilterType.EQUAL);

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,specialist.getCode(),DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantDeliverPendingTransactionsException();//TODO: MANAGE EXCEPTION
        }


        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();

        List<DatabaseTableRecord> records = registryTable.getRecords();

        for(DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        System.out.println("TTF - INCOMING CRYPTO PENDING TRANSACTIONS METHOD CALLED");
        return returnList;
    }




    private void fillRegistryTableRecord(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus,
                                         Specialist specialist){

        databaseTableRecord.setUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName, transaction.getInformation().getTransactionHash());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName, transaction.getInformation().getAddressFrom().getAddress());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName, transaction.getInformation().getAddressTo().getAddress());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName, String.valueOf(transaction.getInformation().getCryptoCurrency()));
        databaseTableRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName,transaction.getInformation().getCryptoAmount());

        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName,transaction.getAction().getCode());
        // We set the specialist as a parameter
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName,specialist.getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName, transaction.getInformation().getCryptoStatus().getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, transactionStatus.getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName, protocolStatus.getCode());

        databaseTableRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName, transaction.getTimestamp());

    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord){
        CryptoAddress cryptoAddressFrom = new CryptoAddress();
        cryptoAddressFrom.setAddress(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName));
        cryptoAddressFrom.setCryptoCurrency(CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)));
        CryptoAddress cryptoAddressTo = new CryptoAddress();
        cryptoAddressTo.setAddress(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName));
        cryptoAddressTo.setCryptoCurrency(CryptoCurrency.BITCOIN);

        CryptoStatus cryptoStatus = null;
        try {
            cryptoStatus = CryptoStatus.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName));
        } catch (InvalidParameterException e) {
            // TODO: Manage exception
            e.printStackTrace();
        }

        CryptoTransaction cryptoTransaction = new CryptoTransaction(
                  databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName),
                  cryptoAddressFrom,
                  cryptoAddressTo,
                  CryptoCurrency.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)),
                  databaseTableRecord.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName),
                  cryptoStatus
                                                                     );
        Action action = null;
        try {
            action = Action.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName));
        } catch (InvalidParameterException e) {
            // Manage Exceotion
            e.printStackTrace();
        }

        return new Transaction<>(
                  databaseTableRecord.getUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName),
                  cryptoTransaction,
                  action,
                  databaseTableRecord.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName)
                                  );
    }


    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName ,
                transactionStatus.getCode(),
                DatabaseFilterType.EQUAL
        );

        registryTable.setStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName ,
                protocolStatus.getCode(),
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
