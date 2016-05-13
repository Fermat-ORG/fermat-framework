package com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
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
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.ExpectedTransactionNotFoundException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistAndCryptoStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.*;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantGetRecordException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantReadEvent;
import com.bitdubai.fermat_bch_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantSaveEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */

/**
 * Esta clase maneja una tabla en su base de datos con la siguiente estructura:
 * <p/>
 * Tabla: IncomingCryptoRegistry
 * <p/>
 * Campos:
 * <p/>
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
 * <p/>
 * La clase basicamente maneja consultas a su tabla IncomingCryptoRegistry.
 * <p/>
 * <p/>
 * Tabla: EventsRecorded
 * <p/>
 * Campos;
 * <p/>
 * Id
 * Event  (enum EventType.name())
 * Source (enum EventSource.name())
 * Status
 * Timestamp
 * <p/>
 * Nota: Definir los status de cada tabla en la capa numero 1 - Definiciones
 * <p/>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class IncomingCryptoRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem, TransactionProtocolManager<CryptoTransaction> {

    int flag = 0;

    public void proofTransaction() {

        if (this.flag == 0)
            return;

        this.flag++;

        List<Transaction<CryptoTransaction>> transactionList = new ArrayList<>();
        CryptoTransaction c = new CryptoTransaction("random",
                BlockchainNetworkType.getDefaultBlockchainNetworkType(),
                new CryptoAddress("addFrom", CryptoCurrency.BITCOIN),
                new CryptoAddress("addTo", CryptoCurrency.BITCOIN),
                CryptoCurrency.BITCOIN, 1, CryptoStatus.IRREVERSIBLE
        );
        /*
        Campos del BitcoinWalletTransactionRecord
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

            // If the database does not exist it means that this is the first time we run this plugin. So we just create it
            IncomingCryptoDataBaseFactory databaseFactory = new IncomingCryptoDataBaseFactory();
            databaseFactory.setPluginDatabaseSystem(this.pluginDatabaseSystem);

            try {
                this.database = databaseFactory.createDatabase(pluginId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantInitializeCryptoRegistryException("Failed attempt to create IncomingCrypto database", cantCreateDatabaseException, "Database Name: " + IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE, "");
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeCryptoRegistryException("Failed attempt to open IncomingCrypto database", cantOpenDatabaseException, "Database Name: " + IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE, "");
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
            // The monitor Agent will manage this exception
            throw new CantSaveEvent("Database Transaction Failed", databaseTransactionFailedException, "", "");
        }
    }

    EventWrapper getNextPendingEvent() throws CantReadEvent {

        DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
        eventsTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING", DatabaseFilterType.EQUAL);
        try {
            eventsTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantReadEvent("Failed Attempt to read event", cantLoadTableToMemory, "", "");
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
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    void disableEvent(UUID eventId) throws CantReadEvent, CantSaveEvent {
        try {
            DatabaseTransaction dbTrx = this.database.newTransaction();
            DatabaseTable eventsTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName, eventId, DatabaseFilterType.EQUAL);
            try {
                eventsTable.loadToMemory();
            } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new CantReadEvent("Failed attempt to read event", cantLoadTableToMemory, "", "");
            }
            List<DatabaseTableRecord> records = eventsTable.getRecords();
            if (records == null || records.isEmpty()) {
                throw new CantReadEvent("I could not find the event with Id", null, String.format("Id: %s", eventId), "");
            } else if (records.size() > 1) {
                //
                throw new CantSaveEvent("More than one event with the same Id", null, String.format("Id: %s", eventId), "");
            }

            DatabaseTableRecord eventRecord = records.get(0);
            eventRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "DISABLED");
            dbTrx.addRecordToUpdate(eventsTable, eventRecord);
            this.database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, databaseTransactionFailedException);
            throw new CantSaveEvent("Database Transaction failed", databaseTransactionFailedException, "", "");
        }
    }


    // Used by the Monitor Agent
    // Las coloca en (A,TBN)
    public void acknowledgeTransactions(List<Transaction<CryptoTransaction>> transactionList) { // throws CantAcknowledgeTransactionException
        try {
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            //DatabaseTransaction databaseTransaction = this.database.newTransaction();
            for (Transaction<CryptoTransaction> transaction : transactionList) {
                // We first check if we have this transaction registered as (A,TBN). This would not be
                // a mistake. It just mean that the system shut down before we could confirm reception to
                // the sender and the sender sent it again.
                registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

                registryTable.loadToMemory();

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
                    }
                }

                // if it is not empty we ignore the transaction because we already have it.

                registryTable.clearAllFilters();
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    // Retorna las que están en (A,TBN)
    public List<Transaction<CryptoTransaction>> getAcknowledgedTransactions() throws InvalidParameterException {//throws CantListTransactionsException
        List<Transaction<CryptoTransaction>> tbaList = new ArrayList<>();
        try {
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    TransactionStatus.ACKNOWLEDGED.getCode(),
                    DatabaseFilterType.EQUAL
            );
            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.TO_BE_NOTIFIED.getCode(),
                    DatabaseFilterType.EQUAL
            );


            registryTable.loadToMemory();


            List<DatabaseTableRecord> records = registryTable.getRecords();

            for (DatabaseTableRecord r : records)
                tbaList.add(getTransactionFromRecord(r));

            registryTable.clearAllFilters();

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        return tbaList;
    }

    // Pasa una a (R,NAR)
    public void acquireResponsibility(Transaction<CryptoTransaction> transaction) { // throws CantAcquireResponsibility
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        try {
            // We look for the record to update
            registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

            registryTable.loadToMemory();

            List<DatabaseTableRecord> records = registryTable.getRecords();
            if (records.size() != 1) {
                String message = "Unexpected number of transactions found";
                String context = "The number of transactions found was: " + records.size() + " and we expected 1";
                FermatException e = new ExpectedTransactionNotFoundException(message, null, context, "");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                //TODO: MANAGE EXCEPTION
            } else {
                DatabaseTableRecord recordToUpdate = records.get(0);
                recordToUpdate.setStringValue(
                        IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                        TransactionStatus.RESPONSIBLE.getCode());

                recordToUpdate.setStringValue(
                        IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                        ProtocolStatus.NO_ACTION_REQUIRED.getCode());

                registryTable.updateRecord(recordToUpdate);

            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (CantUpdateRecordException cantUpdateRecord) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }


    // Used by Relay Agent
    // Retorna las (R,NAR)
    public List<Transaction<CryptoTransaction>> getResponsibleNARTransactions() throws InvalidParameterException { //throws CantAccessTransactionsException

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        List<Transaction<CryptoTransaction>> narList = new ArrayList<>();
        try {
            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    TransactionStatus.RESPONSIBLE.getCode(),
                    DatabaseFilterType.EQUAL
            );
//            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
//                    ProtocolStatus.NO_ACTION_REQUIRED.getCode(),
//                    DatabaseFilterType.EQUAL
//            );

            registryTable.loadToMemory();

            List<DatabaseTableRecord> records = registryTable.getRecords();


            for (DatabaseTableRecord r : records) {
                narList.add(getTransactionFromRecord(r));
            }

            registryTable.clearAllFilters();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        return narList;
    }

    // Pasa la transacción a TBN y le agrega el Specialist.
    public void setToNotify(UUID id, Specialist specialist) {
        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        try {
            // We look for the record to update
            registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, id, DatabaseFilterType.EQUAL);


            registryTable.loadToMemory();


            List<DatabaseTableRecord> records = registryTable.getRecords();

            if (records.size() != 1) {
                String message = "Unexpected number of transactions found";
                String context = "The number of transactions found was: " + records.size() + " and we expected 1";
                FermatException e = new ExpectedTransactionNotFoundException(message, null, context, "");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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


                registryTable.updateRecord(recordToUpdate);
            }
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (CantUpdateRecordException cantUpdateRecord) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    // La lista de (R,TBN) o (R,SN)
    private List<Transaction<CryptoTransaction>> getResponsibleTransactionsPendingAction() throws InvalidParameterException {//throws CantAccessTransactionsException;
        List<Transaction<CryptoTransaction>> tbnList = getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_NOTIFIED);
        List<Transaction<CryptoTransaction>> snList = getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.SENDING_NOTIFIED);
        List<Transaction<CryptoTransaction>> concatenatedList = new ArrayList<>();
        concatenatedList.addAll(tbnList);
        concatenatedList.addAll(snList);
        return concatenatedList;
    }

    // Da los Specialist de las que están en TBN y SN
    public Set<SpecialistAndCryptoStatus> getSpecialists() throws InvalidParameterException {//throws CantReadSpecialistsException

        try {
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            DatabaseTableRecord r;

            List<Transaction<CryptoTransaction>> transactionList = getResponsibleTransactionsPendingAction();

            Set<SpecialistAndCryptoStatus> specialistAndCryptoStatuses = new HashSet<>();


            for (Transaction<CryptoTransaction> transaction : transactionList) {

                if (transaction.getInformation().getCryptoStatus() == CryptoStatus.IRREVERSIBLE) {
                    confirmReception(transaction.getTransactionID()); //I'll ignore this status.
                    continue;
                }

                // We look for the record to update
                registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);


                registryTable.loadToMemory();


                List<DatabaseTableRecord> records = registryTable.getRecords();
                if (records.size() != 1) {
                    String message = "Unexpected number of transactions found";
                    String context = "The number of transactions found was: " + records.size() + " and we expected 1";
                    FermatException e = new ExpectedTransactionNotFoundException(message, null, context, "");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    //TODO: MANAGE EXCEPTION
                } else {
                    r = records.get(0);
                    Specialist s = Specialist.getByCode(r.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName));
                    CryptoStatus c = CryptoStatus.getByCode(r.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName));
                    specialistAndCryptoStatuses.add(new SpecialistAndCryptoStatus(s, c));
                }

                registryTable.clearAllFilters();
            }
            return specialistAndCryptoStatuses;
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    // Pasa las que son TBN a SN
    public void setToSendingNotified() { // throws CantSaveTransactionsException
        try {
            // First we get the TO_BE_NOTIFIED transactions
            List<DatabaseTableRecord> tbnList = getAllRecordsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_NOTIFIED);

            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);

            for (DatabaseTableRecord recordToUpdate : tbnList) {
                recordToUpdate.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                        ProtocolStatus.SENDING_NOTIFIED.getCode());

                registryTable.updateRecord(recordToUpdate);

            }

        } catch (CantUpdateRecordException cantUpdateRecord) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
            // TODO: MANAGE EXCEPTION.
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
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
        registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transactionId, DatabaseFilterType.EQUAL);
        DatabaseTableRecord recordToUpdate;

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();
        if (records.size() != 1) {
            String message = "Unexpected number of transactions found";
            String context = "The number of transactions found was: " + records.size() + " and we expected 1";
            FermatException e = new ExpectedTransactionNotFoundException(message, null, context, "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
            } catch (CantUpdateRecordException cantUpdateRecord) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantUpdateRecord);
                throw new CantConfirmTransactionException(null, cantUpdateRecord, null, null);
                // TODO: MANAGE EXCEPTION.
            } catch (Exception exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            }

        }

        registryTable.clearAllFilters();
        System.out.println("TTF - INCOMING CRYPTO: RECEPTION CONFIRMED BY SPECIALIST");
    }

    /*
     *  El método getPendingTransactions retorna la lista de todas las transacciones con ProtocolStatus SENDING_NOTIFIED que tienen como Specialist al pasado como argumento. Notar que no camba el estado de ninguna transacción.
     */
    public List<Transaction<CryptoTransaction>> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {

        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();
        try {
            DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName, ProtocolStatus.SENDING_NOTIFIED.getCode(), DatabaseFilterType.EQUAL);
            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, TransactionStatus.RESPONSIBLE.getCode(), DatabaseFilterType.EQUAL);

            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName, specialist.getCode(), DatabaseFilterType.EQUAL);


            registryTable.loadToMemory();


            List<DatabaseTableRecord> records = registryTable.getRecords();

            for (DatabaseTableRecord r : records)
                returnList.add(getTransactionFromRecord(r));


            System.out.println("TTF - INCOMING CRYPTO PENDING TRANSACTIONS METHOD CALLED");


        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            throw new CantDeliverPendingTransactionsException("I could not load records from memory", cantLoadTableToMemory, "", "");
        } catch (InvalidParameterException e) {
            throw new CantDeliverPendingTransactionsException("Invalid Parameter detected", e, "", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
        return returnList;
    }


    /************************************************************
     * 1Private methods, from more basic to more complex    *
     ************************************************************/


    private DatabaseTableRecord getRecordByPrimaryKey(UUID transactionId) throws CantGetRecordException {

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        registryTable.addUUIDFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transactionId, DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantGetRecordException("O can't load record", cantLoadTableToMemory, "", "");
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();
        if (records.size() != 1) {
            throw new CantGetRecordException("Inconsistent number of records with given ID", null, "Number of records" + records.size(), "");
        } else
            return records.get(0);
    }

    private void fillRegistryTableRecord(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus,
                                         Specialist specialist) {

        databaseTableRecord.setUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_TRANSACTION_COLUMN.columnName, XMLParser.parseObject(transaction.getInformation()));
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName, transaction.getInformation().getCryptoStatus().getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName, transaction.getAction().getCode());
        // We set the specialist as a parameter
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_SPECIALIST_COLUMN.columnName, specialist.getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, transactionStatus.getCode());
        databaseTableRecord.setStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName, protocolStatus.getCode());

        databaseTableRecord.setLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName, transaction.getTimestamp());

    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException {
        CryptoTransaction cryptoTransaction = (CryptoTransaction) XMLParser.parseXML(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_CRYPTO_TRANSACTION_COLUMN.columnName), new CryptoTransaction());
        Action action = Action.getByCode(databaseTableRecord.getStringValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ACTION_COLUMN.columnName));
        return new Transaction<>(
                databaseTableRecord.getUUIDValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_ID_COLUMN.columnName),
                cryptoTransaction,
                action,
                databaseTableRecord.getLongValue(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName)
        );
    }


    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {

        DatabaseTable registryTable = this.database.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME);
        try {

            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    transactionStatus.getCode(),
                    DatabaseFilterType.EQUAL
            );

            registryTable.addStringFilter(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    protocolStatus.getCode(),
                    DatabaseFilterType.EQUAL
            );


            registryTable.loadToMemory();


            registryTable.clearAllFilters();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }

        return registryTable.getRecords();
    }

    private List<Transaction<CryptoTransaction>> getAllTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws InvalidParameterException {

        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();

        List<DatabaseTableRecord> records = getAllRecordsInState(transactionStatus, protocolStatus);

        for (DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }
}
