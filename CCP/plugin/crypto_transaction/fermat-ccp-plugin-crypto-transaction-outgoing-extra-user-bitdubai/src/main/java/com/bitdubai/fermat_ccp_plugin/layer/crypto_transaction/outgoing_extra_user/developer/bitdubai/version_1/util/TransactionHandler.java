package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.InconsistentTableStateException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedCryptoStatusException;

/**
 * Created by eze on 2015.07.08..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class TransactionHandler {

    public static void handleTransaction(TransactionWrapper   transaction       ,
                                         BitcoinNetworkManager bitcoinNetworkManager,
                                         CryptoStatus         cryptoStatus      ,
                                         BitcoinWalletWallet  bitcoinWallet     ,
                                         com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDao dao               ,
                                         ErrorManager         errorManager      ){

        CryptoStatus oldStatus = transaction.getCryptoStatus();

        if(oldStatus.equals(CryptoStatus.PENDING_SUBMIT)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    return;
                case ON_CRYPTO_NETWORK:
                    try {
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_CRYPTO_NETWORK);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case REVERSED_ON_CRYPTO_NETWORK:
                    try {
                        String memo = "Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed";
                        bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                        dao.cancelTransaction(transaction, memo);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case ON_BLOCKCHAIN:
                    try {
                        bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case REVERSED_ON_BLOCKCHAIN:
                    // En este punto en teoría no se aplicó el credit en el book balance
                    // por lo que no lo revertimos
                    // TODO: Analizar si eso puede fallar
                    try {
                        String memo = "Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed";
                        bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                        dao.cancelTransaction(transaction, memo);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                default:
                    FermatException e = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    return;
            }
        }

        if(oldStatus.equals(CryptoStatus.ON_CRYPTO_NETWORK)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    FermatException e = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
                    return;
                case ON_CRYPTO_NETWORK:
                    return;
                case REVERSED_ON_CRYPTO_NETWORK:
                    try {
                        String memo = "Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed";
                        bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                        dao.cancelTransaction(transaction, memo);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case ON_BLOCKCHAIN:
                    try {
                        bitcoinWallet.getBalance(BalanceType.BOOK).debit(transaction);
                        dao.setToCryptoStatus(transaction, CryptoStatus.ON_BLOCKCHAIN);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantRegisterDebitException | CantUpdateRecordException | CantLoadTableToMemoryException | InconsistentTableStateException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case REVERSED_ON_BLOCKCHAIN:
                    // En este punto en teoría no se aplicó el credit en el book balance
                    // por lo que no lo revertimos
                    // TODO: Analizar si eso puede fallar
                    try {
                        String memo = "Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed";
                        bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                        dao.cancelTransaction(transaction, memo);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                default:
                    FermatException e2 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e2);
                    return;
            }
        }

        if(oldStatus.equals(CryptoStatus.ON_BLOCKCHAIN)) {
            switch (cryptoStatus) {
                case PENDING_SUBMIT:
                    FermatException e3 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e3);                case ON_CRYPTO_NETWORK:
                    return;
                case REVERSED_ON_CRYPTO_NETWORK:
                    FermatException e4 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e4);
                    return;
                case ON_BLOCKCHAIN:
                    return;
                case REVERSED_ON_BLOCKCHAIN:
                    try {
                        String memo = "Transaction sended to " + transaction.getAddressTo().getAddress() + " reversed";
                        bitcoinWallet.getBalance(BalanceType.BOOK).credit(transaction);
                        bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(transaction);
                        dao.cancelTransaction(transaction, memo);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (InconsistentTableStateException | CantUpdateRecordException | CantLoadTableToMemoryException | CantRegisterCreditException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                case IRREVERSIBLE:
                    try {
                        dao.setToPIW(transaction);
                        bitcoinNetworkManager.getTransactionManager().confirmReception(transaction.getTransactionId());

                        return;
                    } catch (CantUpdateRecordException | InconsistentTableStateException | CantLoadTableToMemoryException e1) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e1);
                        return;
                    } catch (Exception exception) {
                        errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, exception);
                        return;
                    }
                default:
                    FermatException e2 = new UnexpectedCryptoStatusException("Unexpected crypto status", null, "Old crypto status: " + oldStatus.getCode() + FermatException.CONTEXT_CONTENT_SEPARATOR + "CryptoStatus found: " + cryptoStatus.getCode(),"");
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e2);
                    return;
            }
        }


    }
}
