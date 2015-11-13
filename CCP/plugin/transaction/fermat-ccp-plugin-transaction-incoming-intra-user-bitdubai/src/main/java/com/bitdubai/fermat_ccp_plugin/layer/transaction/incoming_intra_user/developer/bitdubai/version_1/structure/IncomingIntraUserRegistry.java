package com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.database.IncomingIntraUserDao;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGetTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAccessTransactionsException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcknowledgeTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantReadEventException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.EventWrapper;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;

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
     * IncomingIntraUserRegistry member variables.
     */
    private IncomingIntraUserDao incomingIntraUserDao;


    /**
     * IncomingIntraUserRegistry member methods.
     */
    public void initialize(UUID pluginId) throws com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIncomingIntraUserCryptoRegistryException {
        this.incomingIntraUserDao = new IncomingIntraUserDao(this.pluginDatabaseSystem);
        this.incomingIntraUserDao.initialize(pluginId);
    }

    /*
     * Eevnts table management
     */
    public void saveNewEvent(FermatEnum eventType, EventSource eventSource) throws IncomingIntraUserCantSaveEventException {
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

    public void disableEvent(UUID eventId) throws com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantDisableEventException {
        try {
            this.incomingIntraUserDao.disableEvent(eventId);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantDisableEventException("A database exception occurred",e,"","");
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantDisableEventException("An unexpected exception occurred",FermatException.wrapException(e),"","");
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
    public void acquireResponsibility(Transaction<CryptoTransaction> transaction) throws com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException {
        try {
            this.incomingIntraUserDao.updateTransactionToResponsibleToBeApplied(transaction);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    /********************************************
     *      Methods used by the Relay Agent     *
     ********************************************/

    // Retorna las (R,TBA)
    public List<TransactionCompleteInformation> getResponsibleTBATransactions() throws IncomingIntraUserCantGetTransactionsException {
        try {
            return this.incomingIntraUserDao.getAllTransactionsToBeApplied();
        } catch (InvalidParameterException | CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantGetTransactionsException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantGetTransactionsException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }
    }

    // Pasa la transacción a APPLIED.
    public void setToApplied(UUID id) throws IncomingIntraUserCantAccessTransactionsException {
        try {
            this.incomingIntraUserDao.updateTransactionToApplied(id);
        } catch (CantLoadTableToMemoryException | com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException | CantUpdateRecordException e) {
            throw new IncomingIntraUserCantAccessTransactionsException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantAccessTransactionsException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    /*
     * Metadata Table Management
     */

    /*******************************************************
     *      Methods used by the Metadata Monitor Agent     *
     *******************************************************/

    public void acknowledgeFermatCryptoTransactions(List<Transaction<FermatCryptoTransaction>> transactionList) throws IncomingIntraUserCantAcknowledgeTransactionException {
        try {
            for (Transaction<FermatCryptoTransaction> transaction : transactionList)
                if (this.incomingIntraUserDao.isANewFermatTransaction(transaction))
                    this.incomingIntraUserDao.saveFermatTransactionAsAcknowledgedToBeNotified(transaction);
        } catch (CantInsertRecordException | CantLoadTableToMemoryException e) {
            throw new IncomingIntraUserCantAcknowledgeTransactionException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantAcknowledgeTransactionException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    public List<Transaction<FermatCryptoTransaction>> getAcknowledgedFermatCryptoTransactions() throws IncomingIntraUserCantGetTransactionsException {
        try {
            return this.incomingIntraUserDao.getAllFermatCryptoTransactionsInState(TransactionStatus.ACKNOWLEDGED, ProtocolStatus.TO_BE_NOTIFIED);
        } catch (CantLoadTableToMemoryException | InvalidParameterException e) {
            throw new IncomingIntraUserCantGetTransactionsException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantGetTransactionsException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }

    public void acquireFermatCryptoTransactionResponsibility(Transaction<FermatCryptoTransaction> transaction) throws com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException { //
        try {
            this.incomingIntraUserDao.updateFermatCryptoTransactionToResponsibleToBeApplied(transaction);
        } catch (CantUpdateRecordException | CantLoadTableToMemoryException | com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserExpectedTransactionNotFoundException e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException("Database errors ocurred",e,"","");
        } catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantAcquireResponsibilityException("An unexpected exception ocurred",FermatException.wrapException(e),"","");
        }
    }
}
