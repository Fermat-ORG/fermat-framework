package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure.transactionHandlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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

/**
 * Created by eze on 2015.09.21..
 */
public class OutgoingIntraUserBitcoinWalletTransactionHandler implements OutgoingIntraUserTransactionHandler {

    private ErrorManager         errorManager;
    private BitcoinWalletManager bitcoinWalletManager;
    private OutgoingIntraUserDao dao;
    private BitcoinWalletWallet  bitcoinWallet;

    public OutgoingIntraUserBitcoinWalletTransactionHandler(ErrorManager         errorManager,
                                                            BitcoinWalletManager bitcoinWalletManager,
                                                            OutgoingIntraUserDao outgoingIntraUserDao) {
        this.errorManager         = errorManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.dao                  = outgoingIntraUserDao;
    }
    
    @Override
    public void handleTransaction(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus cryptoStatus) throws OutgoingIntraUserCantHandleTransactionException {
        try {
            CryptoStatus oldStatus = transaction.getCryptoStatus();
            this.bitcoinWallet = this.bitcoinWalletManager.loadWallet(transaction.getWalletPublicKey());

            if (oldStatus.equals(CryptoStatus.PENDING_SUBMIT))
                handleOldCryptoStatusIsPendingSubmit(transaction, cryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_CRYPTO_NETWORK))
                handleOldCryptoStatusIsOnCryptoNetwork(transaction, cryptoStatus);

            if (oldStatus.equals(CryptoStatus.ON_BLOCKCHAIN))
                handleOldCryptoStatusIsOnBlockchain(transaction, cryptoStatus);

        } catch (CantLoadWalletException e) {
            throw new OutgoingIntraUserCantHandleTransactionException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new OutgoingIntraUserCantHandleTransactionException("An unexpected exception happened",FermatException.wrapException(e),"","");
        }
    }
    
    private void handleOldCryptoStatusIsPendingSubmit(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus cryptoStatus) {
        switch (cryptoStatus) {
            case PENDING_SUBMIT:
                return;
            case ON_CRYPTO_NETWORK:
                try {
                    dao.setToCryptoStatus(transaction, CryptoStatus.ON_CRYPTO_NETWORK);
                    return;
                } catch (CantUpdateRecordException | CantLoadTableToMemoryException | OutgoingIntraUserInconsistentTableStateException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case REVERSED_ON_CRYPTO_NETWORK:
                try {
                    transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                    bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                    dao.cancelTransaction(transaction);
                    return;
                } catch (OutgoingIntraUserCantCancelTransactionException | CantRegisterCreditException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case ON_BLOCKCHAIN:
                try {
                    bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                    dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                    return;
                } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar
                try {
                    transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                    bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                    dao.cancelTransaction(transaction);
                    return;
                } catch (OutgoingIntraUserInconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case IRREVERSIBLE:
                try {
                    dao.setToPIW(transaction);
                    return;
                } catch (CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            default:
                FermatException e = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                return;
        }
    }

    
    
    private void handleOldCryptoStatusIsOnCryptoNetwork(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus cryptoStatus) {
        switch (cryptoStatus) {
            case PENDING_SUBMIT:
                FermatException e = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                return;
            case ON_CRYPTO_NETWORK:
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                try {
                    transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                    bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                    dao.cancelTransaction(transaction);
                    return;
                } catch (OutgoingIntraUserInconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case ON_BLOCKCHAIN:
                try {
                    bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                    dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                    return;
                } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case REVERSED_ON_BLOCKCHAIN:
                // En este punto en teoría no se aplicó el credit en el book balance
                // por lo que no lo revertimos
                // TODO: Analizar si eso puede fallar
                try {
                    transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                    bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                    dao.cancelTransaction(transaction);
                    return;
                } catch (OutgoingIntraUserInconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case IRREVERSIBLE:
                try {
                    dao.setToPIW(transaction);
                    return;
                } catch (CantUpdateRecordException | OutgoingIntraUserInconsistentTableStateException | CantLoadTableToMemoryException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            default:
                FermatException e2 = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e2);
                return;
        }
    }
    
    
    
    private void handleOldCryptoStatusIsOnBlockchain(OutgoingIntraUserTransactionWrapper transaction, CryptoStatus cryptoStatus) {
        switch (cryptoStatus) {
            case PENDING_SUBMIT:
                FermatException e3 = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e3);                case ON_CRYPTO_NETWORK:
                return;
            case REVERSED_ON_CRYPTO_NETWORK:
                FermatException e4 = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e4);
                return;
            case ON_BLOCKCHAIN:
                return;
            case REVERSED_ON_BLOCKCHAIN:
                try {
                    transaction.setMemo("Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed");
                    bitcoinWallet.getBalance(BalanceType.BOOK).credit(transaction);
                    bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                    dao.cancelTransaction(transaction);
                    return;
                } catch (OutgoingIntraUserInconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            case IRREVERSIBLE:
                try {
                    dao.setToPIW(transaction);
                    return;
                } catch (CantUpdateRecordException | OutgoingIntraUserInconsistentTableStateException | CantLoadTableToMemoryException e1) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
                    return;
                } catch (Exception exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, FermatException.wrapException(exception));
                    return;
                }
            default:
                FermatException e2 = new OutgoingIntraUserUnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e2);
                return;
        }
    }
    
}
