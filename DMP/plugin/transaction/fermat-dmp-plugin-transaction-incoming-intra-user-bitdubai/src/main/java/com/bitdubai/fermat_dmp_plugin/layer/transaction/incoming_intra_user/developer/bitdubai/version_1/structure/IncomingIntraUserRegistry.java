package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.FermatCryptoTransaction;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database.IncomingIntraUserTransactionDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAccessTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcknowledgeTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantReadEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingIntraUserRegistry {

    public void saveNewEvent(EventType eventType, EventSource eventSource) throws IncomingIntraUserCantSaveEventException {

    }


    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;

    public IncomingIntraUserRegistry(final ErrorManager errorManager, final PluginDatabaseSystem pluginDatabaseSystem){
        this.errorManager         = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * IncomingExtraUserRegistry member variables.
     */
    private Database database;


    /**
     * IncomingExtraUserRegistry member methods.
     */
    public void initialize(UUID pluginId) throws CantInitializeIncomingIntraUserCryptoRegistryException {

    }

    // Used by the Monitor Agent
    // Las coloca en (A,TBN)
    public void acknowledgeTransactions(List<Transaction<CryptoTransaction>> transactionList) throws IncomingIntraUserCantAcknowledgeTransactionException { // throws CantAcknowledgeTransactionException

    }

    public void acknowledgeFermatCryptoTransactions(List<Transaction<FermatCryptoTransaction>> transactionList) throws IncomingIntraUserCantAcknowledgeTransactionException { // throws CantAcknowledgeTransactionException
    }

    protected EventWrapper getNextCryptoPendingEvent() throws IncomingIntraUserCantReadEventException {
        return null;
    }

    protected EventWrapper getNextMetadataPendingEvent() throws IncomingIntraUserCantReadEventException {
        return null;
    }

    protected void disableEvent(UUID eventId) throws IncomingIntraUserCantReadEventException, IncomingIntraUserCantSaveEventException {

    }

    // Retorna las que están en (A,TBN)
    protected List<Transaction<CryptoTransaction>> getAcknowledgedTransactions() throws InvalidParameterException {//throws CantGetTransactionsException
        return null;
    }

    protected List<Transaction<FermatCryptoTransaction>> getAcknowledgedFermatCryptoTransactions() throws InvalidParameterException {
        return null;
    }

    // Pasa una a (R,TBA)
    protected void acquireResponsibility(Transaction<CryptoTransaction> transaction) throws IncomingIntraUserCantAcquireResponsibilityException { //

    }

    protected void acquireFermatCryptoTransactionResponsibility(Transaction<FermatCryptoTransaction> transaction) throws IncomingIntraUserCantAcquireResponsibilityException { //
    }

    // Used by Relay Agent
    // Retorna las (R,TBA)
    protected List<Transaction<CryptoTransaction>> getResponsibleTBATransactions() throws InvalidParameterException {
        return getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
    }

    // Pasa la transacción a APPLIED.
    protected void setToApplied(UUID id) throws IncomingIntraUserCantAccessTransactionsException {

    }

    private void fillRegistryTableRecord(DatabaseTableRecord databaseTableRecord,
                                         Transaction<CryptoTransaction> transaction,
                                         TransactionStatus transactionStatus,
                                         ProtocolStatus protocolStatus) {

    }

    private Transaction<CryptoTransaction> getTransactionFromRecord(DatabaseTableRecord databaseTableRecord) throws InvalidParameterException {
        return null;
    }

    private List<DatabaseTableRecord> getAllRecordsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) {
        return null;
    }

    private List<Transaction<CryptoTransaction>> getAllTransactionsInState(TransactionStatus transactionStatus, ProtocolStatus protocolStatus) throws InvalidParameterException {
        return null;
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
