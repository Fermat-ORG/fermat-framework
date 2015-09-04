package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database.IncomingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAccessTransactionsException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcknowledgeTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantDisableEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantReadEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingIntraUserRegistry {

    private PluginDatabaseSystem pluginDatabaseSystem;

    public IncomingIntraUserRegistry(final PluginDatabaseSystem pluginDatabaseSystem){
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * IncomingExtraUserRegistry member variables.
     */
    private IncomingIntraUserDao incomingIntraUserDao;


    /**
     * IncomingExtraUserRegistry member methods.
     */
    public void initialize(UUID pluginId) throws CantInitializeIncomingIntraUserCryptoRegistryException {
        this.incomingIntraUserDao = new IncomingIntraUserDao(this.pluginDatabaseSystem);
        this.incomingIntraUserDao.initialize(pluginId);
    }

    /*
     * Eevnts table management
     */
    public void saveNewEvent(EventType eventType, EventSource eventSource) throws IncomingIntraUserCantSaveEventException {
        try {
            this.incomingIntraUserDao.saveNewEvent (eventType,eventSource);
        } catch (CantInsertRecordException e) {
            throw new IncomingIntraUserCantSaveEventException("A database exception occurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantSaveEventException("An unexpected exception occurred",FermatException.wrapException(e),"","");
        }
    }

    public EventWrapper getNextCryptoPendingEvent() throws IncomingIntraUserCantReadEventException {
        try {
            return this.incomingIntraUserDao.getNextPendingEvent(EventSource.CRYPTO_ROUTER);
        } catch (CantLoadTableToMemoryException e) {
            throw new  IncomingIntraUserCantReadEventException("A database exception occurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantReadEventException("An unexpected exception occurred",FermatException.wrapException(e),"","");
        }
    }

    public EventWrapper getNextMetadataPendingEvent() throws IncomingIntraUserCantReadEventException {
        try {
            return this.incomingIntraUserDao.getNextPendingEvent(EventSource.NETWORK_SERVICE_CRYPTO_TRANSMISSION);
        } catch (CantLoadTableToMemoryException e) {
            throw new  IncomingIntraUserCantReadEventException("A database exception occurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantReadEventException("An unexpected exception occurred",FermatException.wrapException(e),"","");
        }
    }

    public void disableEvent(UUID eventId) throws IncomingIntraUserCantDisableEventException {
        try {
            this.incomingIntraUserDao.disableEvent(eventId);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantDisableEventException("A database exception occurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantDisableEventException("An unexpected exception occurred",FermatException.wrapException(e),"","");
        }
    }

    /*
     * Crypto Registry Table management
     */

    /*****************************************************
     *      Methods used by the Crypto Monitor Agent     *
     *****************************************************/

    // Las coloca en (A,TBN)
    public void acknowledgeTransactions(List<Transaction<CryptoTransaction>> transactionList) throws IncomingIntraUserCantAcknowledgeTransactionException { // throws CantAcknowledgeTransactionException
        try {
            for (Transaction<CryptoTransaction> transaction : transactionList)
                if (this.incomingIntraUserDao.isANewTransaction(transaction))
                    this.incomingIntraUserDao.saveTransactionAsAcknowledgedToBeNotified(transaction);
        } catch (CantInsertRecordException | CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantAcknowledgeTransactionException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantAcknowledgeTransactionException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    // Retorna las que están en (A,TBN)
    public List<Transaction<CryptoTransaction>> getAcknowledgedTransactions() throws InvalidParameterException, IncomingIntraUserCantGetTransactionsException {
        try {
            return this.incomingIntraUserDao.getAllTransactionsInState(TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED);
        } catch (CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantGetTransactionsException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantGetTransactionsException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    // Pasa una a (R,TBA)
    public void acquireResponsibility(Transaction<CryptoTransaction> transaction) throws IncomingIntraUserCantAcquireResponsibilityException {
        try {
            this.incomingIntraUserDao.updateTransactionToResponsibleToBeApplied(transaction);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | IncomingIntraUserExpectedTransactionNotFoundException  e) {
            throw new IncomingIntraUserCantAcquireResponsibilityException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantAcquireResponsibilityException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    /********************************************
     *      Methods used by the Relay Agent     *
     ********************************************/

    // Retorna las (R,TBA)
    public List<Transaction<CryptoTransaction>> getResponsibleTBATransactions() throws InvalidParameterException, IncomingIntraUserCantGetTransactionsException {
        try {
            return this.incomingIntraUserDao.getAllTransactionsInState(TransactionStatus.RESPONSIBLE, ProtocolStatus.TO_BE_APPLIED);
        } catch (CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantGetTransactionsException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantGetTransactionsException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    // Pasa la transacción a APPLIED.
    public void setToApplied(UUID id) throws IncomingIntraUserCantAccessTransactionsException {

    }

    /*
     * Metadata Table Management
     */

    /*******************************************************
     *      Methods used by the Metadata Monitor Agent     *
     *******************************************************/

    public void acknowledgeFermatCryptoTransactions(List<Transaction<FermatCryptoTransaction>> transactionList) throws IncomingIntraUserCantAcknowledgeTransactionException {
    }

    public List<Transaction<FermatCryptoTransaction>> getAcknowledgedFermatCryptoTransactions() throws InvalidParameterException {
        return null;
    }

    public void acquireFermatCryptoTransactionResponsibility(Transaction<FermatCryptoTransaction> transaction) throws IncomingIntraUserCantAcquireResponsibilityException { //
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
}
