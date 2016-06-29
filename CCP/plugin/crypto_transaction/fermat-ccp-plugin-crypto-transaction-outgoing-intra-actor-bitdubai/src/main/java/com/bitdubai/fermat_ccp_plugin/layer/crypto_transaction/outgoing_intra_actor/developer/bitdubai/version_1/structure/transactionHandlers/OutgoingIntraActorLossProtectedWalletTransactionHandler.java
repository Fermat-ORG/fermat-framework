package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.structure.transactionHandlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.OutgoingIntraActorTransactionSentEvent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.database.OutgoingIntraActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantCancelTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorCantHandleTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorInconsistentTableStateException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions.OutgoingIntraActorUnexpectedCryptoStatusException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.interfaces.OutgoingIntraActorTransactionHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.version_1.util.OutgoingIntraActorTransactionWrapper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

/**
 * Created by natalia on 22/03/16.
 */
public class OutgoingIntraActorLossProtectedWalletTransactionHandler implements OutgoingIntraActorTransactionHandler {

    private EventManager eventManager;
    private BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager;
    private OutgoingIntraActorDao dao;
    private BitcoinLossProtectedWallet lossProtectedWalletWallet;

    public OutgoingIntraActorLossProtectedWalletTransactionHandler(EventManager eventManager,
                                                                   BitcoinLossProtectedWalletManager bitcoinLossProtectedWalletManager,
                                                             OutgoingIntraActorDao outgoingIntraActorDao) {
        this.eventManager         = eventManager;
        this.bitcoinLossProtectedWalletManager = bitcoinLossProtectedWalletManager;
        this.dao                  = outgoingIntraActorDao;
    }

    //TODO tengo que enviarle los datos del exchange rate al que hago esta transaccion
    @Override
    public void handleTransaction(OutgoingIntraActorTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraActorCantHandleTransactionException {
        try {
            CryptoStatus oldStatus = transaction.getCryptoStatus();
            this.lossProtectedWalletWallet = this.bitcoinLossProtectedWalletManager.loadWallet(transaction.getWalletPublicKey());

            if (oldStatus.equals(CryptoStatus.PENDING_SUBMIT))
                handleOldCryptoStatusIsPendingSubmit(transaction, newCryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_CRYPTO_NETWORK))
                handleOldCryptoStatusIsOnCryptoNetwork(transaction, newCryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_BLOCKCHAIN))
                handleOldCryptoStatusIsOnBlockchain(transaction, newCryptoStatus);

        } catch (FermatException e) {
            throw new OutgoingIntraActorCantHandleTransactionException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new OutgoingIntraActorCantHandleTransactionException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }
    }

    private void handleOldCryptoStatusIsPendingSubmit(OutgoingIntraActorTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraActorInconsistentTableStateException, CantLoadTableToMemoryException, CantUpdateRecordException, CantRegisterCreditException, OutgoingIntraActorCantCancelTransactionException, CantRegisterDebitException, OutgoingIntraActorUnexpectedCryptoStatusException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                return;
            case ON_CRYPTO_NETWORK:
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_CRYPTO_NETWORK);
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                transaction.setMemo("Transaction sent to " + transaction.getAddressTo().getAddress() + " reversed");
                lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case ON_BLOCKCHAIN:
                lossProtectedWalletWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                return;
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // dado que el último estado en PENDING_SUBMIT por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar. Tal vez si se cae la app entre la aplicación del book balance pero antes de setear el crypto estado en el bloque anterior de este switch
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                lossProtectedWalletWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }



    private void handleOldCryptoStatusIsOnCryptoNetwork(OutgoingIntraActorTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraActorUnexpectedCryptoStatusException, CantRegisterCreditException, OutgoingIntraActorCantCancelTransactionException, CantRegisterDebitException, OutgoingIntraActorInconsistentTableStateException, CantLoadTableToMemoryException, CantUpdateRecordException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case ON_CRYPTO_NETWORK:
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case ON_BLOCKCHAIN:
                lossProtectedWalletWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                return;
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar. Idem switch anterior
                transaction.setMemo("Transaction sent to " + transaction.getAddressTo().getAddress() + " reversed");
                lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                lossProtectedWalletWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }



    private void handleOldCryptoStatusIsOnBlockchain(OutgoingIntraActorTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraActorUnexpectedCryptoStatusException, CantRegisterCreditException, OutgoingIntraActorCantCancelTransactionException, CantRegisterDebitException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case REVERSED_ON_CRYPTO_NETWORK:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case ON_BLOCKCHAIN:
                return;
            case REVERSED_ON_BLOCKCHAIN:
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                lossProtectedWalletWallet.getBalance(BalanceType.BOOK).credit(transaction);
                lossProtectedWalletWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraActorUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }

    private void raiseNotificationEvent(OutgoingIntraActorTransactionWrapper transaction) {
        FermatEvent eventToLaunch = this.eventManager.getNewEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.OUTGOING_INTRA_ACTOR_TRANSACTION_SENT);
        ((OutgoingIntraActorTransactionSentEvent) eventToLaunch).setTransactionHash(transaction.getTransactionHash());
        this.eventManager.raiseEvent(eventToLaunch);
    }
}
