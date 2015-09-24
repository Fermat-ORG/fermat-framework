package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure.transactionHandlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantCancelTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserCantHandleTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserInconsistentTableStateException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.OutgoingIntraUserUnexpectedCryptoStatusException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.interfaces.OutgoingIntraUserTransactionHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionWrapper;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.OutgoingIntraActorTransactionSentEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraUserBitcoinWalletTransactionHandler implements OutgoingIntraUserTransactionHandler {

    private EventManager         eventManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingIntraUserDao dao;
    private BitcoinWalletWallet  bitcoinWallet;

    public OutgoingIntraUserBitcoinWalletTransactionHandler(EventManager         eventManager,
                                                            BitcoinWalletManager bitcoinWalletManager,
                                                            OutgoingIntraUserDao outgoingIntraUserDao) {
        this.eventManager         = eventManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.dao                  = outgoingIntraUserDao;
    }
    
    @Override
    public void handleTransaction(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraUserCantHandleTransactionException {
        try {
            CryptoStatus oldStatus = transaction.getCryptoStatus();
            this.bitcoinWallet = this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());

            if (oldStatus.equals(CryptoStatus.PENDING_SUBMIT))
                handleOldCryptoStatusIsPendingSubmit(transaction, newCryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_CRYPTO_NETWORK))
                handleOldCryptoStatusIsOnCryptoNetwork(transaction, newCryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_BLOCKCHAIN))
                handleOldCryptoStatusIsOnBlockchain(transaction, newCryptoStatus);

        } catch (FermatException e) {
            throw new OutgoingIntraUserCantHandleTransactionException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new OutgoingIntraUserCantHandleTransactionException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }
    }
    
    private void handleOldCryptoStatusIsPendingSubmit(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraUserInconsistentTableStateException, CantLoadTableToMemoryException, CantUpdateRecordException, CantRegisterCreditException, OutgoingIntraUserCantCancelTransactionException, CantRegisterDebitException, OutgoingIntraUserUnexpectedCryptoStatusException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                return;
            case ON_CRYPTO_NETWORK:
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_CRYPTO_NETWORK);
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                transaction.setMemo("Transaction sent to " + transaction.getAddressTo().getAddress() + " reversed");
                bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case ON_BLOCKCHAIN:
                bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                return;
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // dado que el último estado en PENDING_SUBMIT por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar. Tal vez si se cae la app entre la aplicación del book balance pero antes de setear el crypto estado en el bloque anterior de este switch
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }

    
    
    private void handleOldCryptoStatusIsOnCryptoNetwork(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraUserUnexpectedCryptoStatusException, CantRegisterCreditException, OutgoingIntraUserCantCancelTransactionException, CantRegisterDebitException, OutgoingIntraUserInconsistentTableStateException, CantLoadTableToMemoryException, CantUpdateRecordException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case ON_CRYPTO_NETWORK:
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case ON_BLOCKCHAIN:
                bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                return;
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar. Idem switch anterior
                transaction.setMemo("Transaction sent to " + transaction.getAddressTo().getAddress() + " reversed");
                bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }
    
    
    
    private void handleOldCryptoStatusIsOnBlockchain(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus newCryptoStatus) throws OutgoingIntraUserUnexpectedCryptoStatusException, CantRegisterCreditException, OutgoingIntraUserCantCancelTransactionException, CantRegisterDebitException {
        switch (newCryptoStatus) {
            case PENDING_SUBMIT:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case REVERSED_ON_CRYPTO_NETWORK:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
            case ON_BLOCKCHAIN:
                return;
            case REVERSED_ON_BLOCKCHAIN:
                transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                bitcoinWallet.getBalance(BalanceType.BOOK).credit(transaction);
                bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                dao.cancelTransaction(transaction);
                return;
            case IRREVERSIBLE:
                dao.setToPIW(transaction);
                raiseNotificationEvent(transaction);
                return;
            default:
                throw new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + transaction.getCryptoStatus().getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + newCryptoStatus.getCode(),"");
        }
    }

    private void raiseNotificationEvent(OutgoingIntraUserTransactionWrapper transaction) {
        FermatEvent eventToLaunch = this.eventManager.getNewEvent(EventType.OUTGOING_INTRA_ACTOR_TRANSACTION_SENT);
        ((OutgoingIntraActorTransactionSentEvent) eventToLaunch).setTransactionHash(transaction.getTransactionHash());
        this.eventManager.raiseEvent(eventToLaunch);
    }
}
