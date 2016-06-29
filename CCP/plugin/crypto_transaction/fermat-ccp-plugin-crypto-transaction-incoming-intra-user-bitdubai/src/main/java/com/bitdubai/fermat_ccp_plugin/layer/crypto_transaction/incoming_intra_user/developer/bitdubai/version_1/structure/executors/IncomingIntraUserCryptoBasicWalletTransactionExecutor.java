package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure.executors;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantProcessRequestAcceptedException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.exceptions.CantSetToCreditedInWalletException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUSerUnexpectedTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGenerateTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation;
import com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events.IncomingIntraUserTransactionDebitNotificationEvent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.UUID;

/**
 * Created by eze on 2015.09.15..
 */
public class IncomingIntraUserCryptoBasicWalletTransactionExecutor implements TransactionExecutor {

    private CryptoWalletWallet bitcoinWallet;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private EventManager             eventManager;
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    public IncomingIntraUserCryptoBasicWalletTransactionExecutor(final CryptoWalletWallet bitcoinWallet, final CryptoAddressBookManager cryptoAddressBookManager, final EventManager eventManager, final CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager){
        this.bitcoinWallet            = bitcoinWallet;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.eventManager             = eventManager;
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;

    }

    @Override
    public void executeTransaction(TransactionCompleteInformation transactionContainer) throws IncomingIntraUserCantExecuteTransactionException {
        Transaction<CryptoTransaction> transaction = transactionContainer.getCryptoTransaction();
        try {
            switch (transaction.getInformation().getCryptoStatus()) {
                case ON_CRYPTO_NETWORK:
                    processOnCryptoNetworkTransaction(transactionContainer);
                    break;
                case ON_BLOCKCHAIN:
                    processOnBlockChainTransaction(transactionContainer);
                    break;
                //todo ezequiel, cambie de Reversed a REVERSED_ON_CRYPTO_NETWORK
                case REVERSED_ON_CRYPTO_NETWORK:
                    processReversedOnCryptoNetworkTransaction(transactionContainer);
                    break;
                case REVERSED_ON_BLOCKCHAIN:
                    processReversedOnBlockchainTransaction(transactionContainer);
                    break;
                case IRREVERSIBLE:
                    //TODO: ver porqué sigue quedando acá la transaccion
                    break;
                default:
                    throw new IncomingIntraUSerUnexpectedTransactionException("El crypto status no es esperado", null, "El cryptoStatus es: " + transaction.getInformation().getCryptoStatus().getCode(), "");
            }
        } catch (CantRegisterDebitException | CantRegisterCreditException  | IncomingIntraUSerUnexpectedTransactionException e) {
            throw new IncomingIntraUserCantExecuteTransactionException("An exception happened",e,"","");
        } catch (Exception e) {
            throw new IncomingIntraUserCantExecuteTransactionException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }
    }

    private void processOnCryptoNetworkTransaction(TransactionCompleteInformation transaction) throws CantRegisterCreditException {
        try {
            CryptoWalletTransactionRecord record = transaction.generateBitcoinTransaction(cryptoAddressBookManager);
            bitcoinWallet.getBalance(BalanceType.BOOK).credit(record);

            //notified to Transmission NS that transaction Credit in Wallet
            cryptoTransmissionNetworkServiceManager.informTransactionCreditedInWallet(transaction.getTransactionMetadata().getTransactionID());


        } catch (IncomingIntraUserCantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
        } catch (CantSetToCreditedInWalletException e) {
            e.printStackTrace();
        }
    }

    private void processOnBlockChainTransaction(TransactionCompleteInformation transaction) throws CantRegisterCreditException {
        try {
            CryptoWalletTransactionRecord record = transaction.generateBitcoinTransaction(cryptoAddressBookManager);
            bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(record);


          //  broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, cryptoAddressBookRecord.getWalletPublicKey(), "TRANSACTIONARRIVE_" + transaction.getTransactionID().toString());


        } catch (IncomingIntraUserCantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
       }

//        //Esto se hiso aca hasta pensar mejor el proceso para cambiar el estado del request
//        try
//        {
//        if(transaction.getTransactionMetadata().getInformation().getRequestId() != null)
//        {
//            processPaymentRequest(transaction.getTransactionMetadata().getInformation().getRequestId());
//        }
//
//        } catch (CantProcessRequestAcceptedException e) {
//            throw new CantRegisterCreditException("I couldn't update request payment",e,"","");
//        }
    }

    private void processReversedOnCryptoNetworkTransaction(TransactionCompleteInformation transaction) throws CantRegisterDebitException {
        try {
            CryptoWalletTransactionRecord record = transaction.generateBitcoinTransaction(cryptoAddressBookManager);
            bitcoinWallet.getBalance(BalanceType.BOOK).debit(record);
        } catch (IncomingIntraUserCantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processReversedOnBlockchainTransaction(TransactionCompleteInformation transaction) throws CantRegisterDebitException {
        try {
            CryptoWalletTransactionRecord record = transaction.generateBitcoinTransaction(cryptoAddressBookManager);
            bitcoinWallet.getBalance(BalanceType.AVAILABLE).debit(record);


        } catch (IncomingIntraUserCantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processPaymentRequest(UUID requestId) throws CantProcessRequestAcceptedException {
       try
        {
            //Hay que disparar un evento para que escuche el Crypto Payment
            FermatEvent platformEvent  = eventManager.getNewEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.INCOMING_INTRA_USER_DEBIT_TRANSACTION);
            IncomingIntraUserTransactionDebitNotificationEvent incomingDebitNotificationEvent = (IncomingIntraUserTransactionDebitNotificationEvent) platformEvent;
            incomingDebitNotificationEvent.setSource(EventSource.INCOMING_INTRA_USER);
            incomingDebitNotificationEvent.setRequestId(requestId);

            eventManager.raiseEvent(platformEvent);
        }
        catch(Exception e)
        {
            throw new CantProcessRequestAcceptedException("I couldn't update request payment accepted",FermatException.wrapException(e),"","unknown error");
        }
    }
}
