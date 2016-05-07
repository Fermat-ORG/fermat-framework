package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.*;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantAccessTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantAcknowledgeTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantAcquireResponsibilityException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantSaveEventException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.ExpectedTransactionNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/30/15.
 * Modified by Arturo Vallone 25/04/2015
 */

/**
 * Esta clase maneja una tabla en su base de datos con la siguiente estructura:
 * <p/>
 * Tabla: IncomingExtraUserRegistry
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
 * La clase basicamente maneja consultas a su tabla IncomingExtraUserRegistry.
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

public class IncomingExtraUserRegistry implements DealsWithErrors, DealsWithPluginDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * IncomingExtraUserRegistry member variables.
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
     * IncomingExtraUserRegistry member methods.
     */
    public void initialize(UUID pluginId) throws CantInitializeCryptoRegistryException {
        if (pluginDatabaseSystem == null)
            throw new CantInitializeCryptoRegistryException(CantInitializeCryptoRegistryException.DEFAULT_MESSAGE, null, "Plugin Database System: null", "You have to set the PluginDatabaseSystem before initializing");

        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE);
        } catch (DatabaseNotFoundException e) {
            IncomingExtraUserDataBaseFactory databaseFactory = new IncomingExtraUserDataBaseFactory(pluginDatabaseSystem);

            try {
                database = databaseFactory.createDatabase(pluginId, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE);
            } catch (CantCreateDatabaseException exception) {
                throw new CantInitializeCryptoRegistryException(CantInitializeCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why the Database couldn't be created");
            }
        } catch (CantOpenDatabaseException exception) {
            throw new CantInitializeCryptoRegistryException(CantInitializeCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause to see why we couldn't open the database");
        } catch (Exception exception) {
            throw new CantInitializeCryptoRegistryException(CantInitializeCryptoRegistryException.DEFAULT_MESSAGE, exception, "", "Check the cause ");
        }
    }

    // Used by the Monitor Agent
    // Las coloca en (A,TBN)
    public void acknowledgeTransactions(List<Transaction<CryptoTransaction>> transactionList) throws CantAcknowledgeTransactionException { // throws CantAcknowledgeTransactionException
        DatabaseTable registryTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME);
        for (Transaction<CryptoTransaction> transaction : transactionList) {
            // We first check if we have this transaction registered as (A,TBN). This would not be
            // a mistake. It just mean that the system shut down before we could confirm reception to
            // the sender and the sender sent it again.
            registryTable.addUUIDFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

            try {
                registryTable.loadToMemory();
            } catch (CantLoadTableToMemoryException exception) {
                throw new CantAcknowledgeTransactionException(CantDeliverPendingTransactionsException.DEFAULT_MESSAGE, exception, registryTable.toString(), "We cant load the table to memory, there is no way we can circunvent this problem");
            }
            List<DatabaseTableRecord> records = registryTable.getRecords();
            if (records.isEmpty()) {
                // if it is empty this is a new transaction
                // If it is a new transaction we save it as usual
                DatabaseTableRecord transactionRecord = registryTable.getEmptyRecord();
                fillRegistryTableRecord(transactionRecord, transaction, TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED);
                try {
                    registryTable.insertRecord(transactionRecord);
                } catch (CantInsertRecordException cantInsertRecord) {
                    throw new CantAcknowledgeTransactionException(CantAcknowledgeTransactionException.DEFAULT_MESSAGE, cantInsertRecord, "Table : " + registryTable.toString(), "This is a database level issue, check the cause to see the reason");
                } catch (Exception e) {
                    throw new CantAcknowledgeTransactionException(CantAcknowledgeTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, "check the cause to see the reason");
                }
            }

            // if it is not empty we ignore the transaction because we already have it.
            registryTable.clearAllFilters();
        }
    }

    protected void saveNewEvent(String eventType, String eventSource) throws CantSaveEventException {
        try {
            
            DatabaseTransaction dbTrx = database.newTransaction();
            DatabaseTable eventsTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableRecord eventRecord = eventsTable.getEmptyRecord();

            UUID eventRecordID = UUID.randomUUID();
            long unixTime = System.currentTimeMillis();
            eventRecord.setUUIDValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName, eventRecordID);
            eventRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName, eventType);
            eventRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName, eventSource);
            eventRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING");
            eventRecord.setLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName, unixTime);
            dbTrx.addRecordToInsert(eventsTable, eventRecord);
            database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException exception) {
            
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, null, "The Transaction Failed. Check the Cause");
        } catch (Exception exception) {
            
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, null, " Check the Cause");
        }
    }

    protected EventWrapper getNextPendingEvent() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException {
        try {
            
            DatabaseTable eventsTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.addStringFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "PENDING", DatabaseFilterType.EQUAL);

            eventsTable.loadToMemory();

            List<DatabaseTableRecord> events = eventsTable.getRecords();

            if (events == null || events.isEmpty()) {
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            }

            DatabaseTableRecord event = events.get(0);
            return new EventWrapper(
                    event.getUUIDValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName),
                    event.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_EVENT_COLUMN.columnName),
                    event.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_SOURCE_COLUMN.columnName),
                    event.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName),
                    event.getLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_TIMESTAMP_COLUMN.columnName)
            );

        } catch (CantLoadTableToMemoryException exception) {
            
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException.DEFAULT_MESSAGE, exception, null, "There is no way to gracefully handle this, check the cause");
        } catch (Exception exception) {
            
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException.DEFAULT_MESSAGE, exception, null, " Check the Cause");
        }
    }

    protected void disableEvent(UUID eventId) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException, CantSaveEventException {
        try {
            
            DatabaseTransaction dbTrx = database.newTransaction();
            DatabaseTable eventsTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_NAME);
            eventsTable.addUUIDFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_ID_COLUMN.columnName, eventId, DatabaseFilterType.EQUAL);
            try {
                eventsTable.loadToMemory();
            } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
                throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantReadEventException();
            }
            List<DatabaseTableRecord> records = eventsTable.getRecords();
            if (records == null || records.isEmpty()) {
                throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, null, "Event ID: " + eventId, "I couldn't find the event with the given Id");
            } else if (records.size() > 1) {
                throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, null, "Event ID: " + eventId, "More than one Event with the given Id");
            }
            DatabaseTableRecord eventRecord = records.get(0);
            eventRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_EVENTS_RECORDED_TABLE_STATUS_COLUMN.columnName, "DISABLED");
            dbTrx.addRecordToUpdate(eventsTable, eventRecord);
            database.executeTransaction(dbTrx);
        } catch (DatabaseTransactionFailedException exception) {
            
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, null, "The Transaction Failed. Check the Cause");
        } catch (Exception exception) {
            
            throw new CantSaveEventException(CantSaveEventException.DEFAULT_MESSAGE, exception, null, " Check the Cause");
        }
    }

    // Retorna las que están en (A,TBN)
    protected List<Transaction<CryptoTransaction>> getAcknowledgedTransactions() throws InvalidParameterException {//throws CantListTransactionsException
        List<Transaction<CryptoTransaction>> tbaList = new ArrayList<>();

        DatabaseTable registryTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME);
        registryTable.addStringFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                TransactionStatus.ACKNOWLEDGED.getCode(),
                DatabaseFilterType.EQUAL);
        registryTable.addStringFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                ProtocolStatus.TO_BE_NOTIFIED.getCode(),
                DatabaseFilterType.EQUAL);

        try {
            registryTable.loadToMemory();
        } catch (CantLoadTableToMemoryException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            return tbaList;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            return tbaList;
        }

        List<DatabaseTableRecord> records = registryTable.getRecords();

        for (DatabaseTableRecord r : records)
            tbaList.add(getTransactionFromRecord(r));

        registryTable.clearAllFilters();

        return tbaList;
    }

    // Pasa una a (R,TBA)
    protected void acquireResponsibility(Transaction<CryptoTransaction> transaction) throws CantAcquireResponsibilityException { //
        try {
            
            DatabaseTable registryTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME);

            // We look for the record to update
            registryTable.addUUIDFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID(), DatabaseFilterType.EQUAL);

            registryTable.loadToMemory();

            List<DatabaseTableRecord> records = registryTable.getRecords();

            if (records.size() != 1) {
                throw new ExpectedTransactionNotFoundException();
            }

            DatabaseTableRecord recordToUpdate = records.get(0);
            recordToUpdate.setStringValue(
                    com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    TransactionStatus.RESPONSIBLE.getCode()
            );

            recordToUpdate.setStringValue(
                    com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.TO_BE_APPLIED.getCode()
            );
            registryTable.updateRecord(recordToUpdate);

        } catch (FermatException exception) {
            throw new CantAcquireResponsibilityException(CantAcquireResponsibilityException.DEFAULT_MESSAGE, exception, null, "Check the Cause");
        } catch (Exception exception) {
            
            throw new CantAcquireResponsibilityException(CantAcquireResponsibilityException.DEFAULT_MESSAGE, exception, null, "Check the Cause");
        }

    }


    // Used by Relay Agent
    // Retorna las (R,TBA)
    protected List<Transaction<CryptoTransaction>> getResponsibleTBATransactions() throws InvalidParameterException {
        return getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
    }

    // Pasa la transacción a APPLIED.
    protected void setToApplied(UUID id) throws CantAccessTransactionsException {
        try {

            
            DatabaseTable registryTable = database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME);

            // We look for the record to update
            registryTable.addUUIDFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName, id, DatabaseFilterType.EQUAL);
            registryTable.loadToMemory();

            List<DatabaseTableRecord> records = registryTable.getRecords();

            if (records.size() != 1) {
                throw new ExpectedTransactionNotFoundException();
            }

            DatabaseTableRecord recordToUpdate = records.get(0);

            recordToUpdate.setStringValue(
                    com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    ProtocolStatus.APPLIED.getCode()
            );

            registryTable.updateRecord(recordToUpdate);


        } catch (FermatException exception) {
            throw new CantAccessTransactionsException(CantAccessTransactionsException.DEFAULT_MESSAGE, exception, null, "Check the Cause");
        } catch (Exception exception) {
            
            throw new CantAccessTransactionsException(CantAccessTransactionsException.DEFAULT_MESSAGE, exception, null, "Check the Cause");
        }
    }

    private void fillRegistryTableRecord(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus) {

        databaseTableRecord.setUUIDValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName, transaction.getTransactionID());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName, transaction.getInformation().getTransactionHash());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName, transaction.getInformation().getAddressFrom().getAddress());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName, transaction.getInformation().getAddressTo().getAddress());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName, String.valueOf(transaction.getInformation().getCryptoCurrency().getCode()));
        databaseTableRecord.setLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName, transaction.getInformation().getCryptoAmount());

        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ACTION_COLUMN.columnName, transaction.getAction().getCode());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName, transaction.getInformation().getCryptoStatus().getCode());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName, transactionStatus.getCode());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName, protocolStatus.getCode());

        databaseTableRecord.setLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName, transaction.getTimestamp());
        databaseTableRecord.setStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NETWORK_TYPE.columnName, transaction.getInformation().getBlockchainNetworkType().getCode());

    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException {
        CryptoAddress cryptoAddressFrom = new CryptoAddress();
        cryptoAddressFrom.setAddress(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_FROM_COLUMN.columnName));
        cryptoAddressFrom.setCryptoCurrency(CryptoCurrency.getByCode(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)));
        CryptoAddress cryptoAddressTo = new CryptoAddress();
        cryptoAddressTo.setAddress(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ADDRESS_TO_COLUMN.columnName));
        cryptoAddressTo.setCryptoCurrency(CryptoCurrency.getByCode(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)));

        CryptoStatus cryptoStatus = null;
        try {
            cryptoStatus = CryptoStatus.getByCode(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_STATUS_COLUMN.columnName));
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, e, null, "Check the Cause");
        }

        CryptoTransaction cryptoTransaction = new CryptoTransaction(
                databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_HASH_COLUMN.columnName),
                BlockchainNetworkType.getByCode(databaseTableRecord.getStringValue(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NETWORK_TYPE.columnName)),
                cryptoAddressFrom,
                cryptoAddressTo,
                CryptoCurrency.getByCode(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_CURRENCY_COLUMN.columnName)),
                databaseTableRecord.getLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_CRYPTO_AMOUNT_COLUMN.columnName),
                cryptoStatus
        );
        Action action = null;
        try {
            action = Action.getByCode(databaseTableRecord.getStringValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ACTION_COLUMN.columnName));
        } catch (InvalidParameterException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, e, null, "Check the Cause");
        }

        return new Transaction<>(
                databaseTableRecord.getUUIDValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_ID_COLUMN.columnName),
                cryptoTransaction,
                action,
                databaseTableRecord.getLongValue(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TIMESTAMP_COLUMN.columnName)
        );
    }

    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {
        try {
            
            DatabaseTable registryTable = this.database.getTable(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME);

            registryTable.addStringFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_TRANSACTION_STATUS_COLUMN.columnName,
                    transactionStatus.getCode(),
                    DatabaseFilterType.EQUAL
            );

            registryTable.addStringFilter(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_PROTOCOL_STATUS_COLUMN.columnName,
                    protocolStatus.getCode(),
                    DatabaseFilterType.EQUAL
            );
            registryTable.loadToMemory();
            registryTable.clearAllFilters();

            return registryTable.getRecords();
        } catch (FermatException cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
            return new ArrayList<>(0);
        } catch (Exception cantLoadTableToMemory) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadTableToMemory);
            //TODO: MANAGE EXCEPTION
            return new ArrayList<>(0);
        }
    }

    private List<Transaction<CryptoTransaction>> getAllTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws InvalidParameterException {

        List<Transaction<CryptoTransaction>> returnList = new ArrayList<>();

        List<DatabaseTableRecord> records = getAllRecordsInState(transactionStatus, protocolStatus);

        for (DatabaseTableRecord r : records)
            returnList.add(getTransactionFromRecord(r));

        return returnList;
    }

    protected static class EventWrapper {
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
}
