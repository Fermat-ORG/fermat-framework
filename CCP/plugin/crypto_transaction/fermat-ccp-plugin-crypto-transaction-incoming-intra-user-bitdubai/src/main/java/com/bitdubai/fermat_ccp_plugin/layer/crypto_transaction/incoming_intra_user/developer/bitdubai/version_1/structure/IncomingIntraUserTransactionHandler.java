package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

/**
 * Created by eze on 2015.09.11..
 */
public class IncomingIntraUserTransactionHandler {
    private EventManager             eventManager;
    private BitcoinWalletManager     bitcoinWalletManager;
    private CryptoAddressBookManager cryptoAddressBookManager;

    public IncomingIntraUserTransactionHandler(EventManager eventManager, BitcoinWalletManager bitcoinWalletManager, CryptoAddressBookManager cryptoAddressBookManager) {
        this.eventManager             = eventManager;
        this.bitcoinWalletManager     = bitcoinWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    public void handleTransaction(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation transaction) throws CantGetCryptoAddressBookRecordException, CantLoadWalletException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException {
        try {
            try {
                CryptoAddressBookRecord cryptoAddressBookRecord = this.cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(transaction.getCryptoTransaction().getInformation().getAddressTo());
                ReferenceWallet         referenceWallet         = cryptoAddressBookRecord.getWalletType();
                String                  walletPublicKey         = cryptoAddressBookRecord.getWalletPublicKey();

                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserTransactionExecutorFactory executorFactory = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util.IncomingIntraUserTransactionExecutorFactory(this.bitcoinWalletManager, this.cryptoAddressBookManager);
                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor executor        = executorFactory.newTransactionExecutor(referenceWallet, walletPublicKey);

                executor.executeTransaction(transaction);

                launchIncomingMoneyNotificationEvent(cryptoAddressBookRecord,transaction.getCryptoTransaction());

            } catch (CryptoAddressBookRecordNotFoundException exception) {
                //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
                throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
            }
        } catch (com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantExecuteTransactionException | CantGetCryptoAddressBookRecordException | CantLoadWalletException e){
            throw e;
        } catch (Exception exception) {
            //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
        }
    }

    private void launchIncomingMoneyNotificationEvent(CryptoAddressBookRecord cryptoAddressBookRecord,Transaction<CryptoTransaction> transaction) {
        FermatEvent                    platformEvent                  = eventManager.getNewEvent(EventType.INCOMING_MONEY_NOTIFICATION);
        IncomingMoneyNotificationEvent incomingMoneyNotificationEvent = (IncomingMoneyNotificationEvent) platformEvent;
        incomingMoneyNotificationEvent.setSource(EventSource.INCOMING_INTRA_USER);
        incomingMoneyNotificationEvent.setActorId(cryptoAddressBookRecord.getDeliveredToActorPublicKey());
        incomingMoneyNotificationEvent.setActorType(cryptoAddressBookRecord.getDeliveredToActorType());
        incomingMoneyNotificationEvent.setAmount(transaction.getInformation().getCryptoAmount());
        incomingMoneyNotificationEvent.setCryptoCurrency(transaction.getInformation().getCryptoCurrency());
        incomingMoneyNotificationEvent.setWalletPublicKey(cryptoAddressBookRecord.getWalletPublicKey());
        incomingMoneyNotificationEvent.setIntraUserIdentityPublicKey(cryptoAddressBookRecord.getDeliveredByActorPublicKey());

        eventManager.raiseEvent(platformEvent);
    }
}
