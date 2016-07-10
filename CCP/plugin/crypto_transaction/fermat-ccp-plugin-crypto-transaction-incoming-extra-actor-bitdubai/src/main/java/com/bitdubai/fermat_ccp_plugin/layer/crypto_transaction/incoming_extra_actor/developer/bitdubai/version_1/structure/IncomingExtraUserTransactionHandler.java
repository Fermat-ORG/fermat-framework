package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
/**
 * Created by eze on 2015.06.22.
 *
 */
public class IncomingExtraUserTransactionHandler {

    /*
     * DealsWithBitcoinWallet Interface member variables
    */
    private CryptoWalletManager cryptoWalletManager;

    /*
    * DealsWithCryptoAddressBook member variables
    */
    private CryptoAddressBookManager cryptoAddressBookManager;

    /**
     * DealsWithEventManager
     */
    private EventManager eventManager;

    private Broadcaster broadcaster;

    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;

    public IncomingExtraUserTransactionHandler(CryptoWalletManager cryptoWalletManager, CryptoAddressBookManager cryptoAddressBookManager, EventManager eventManager,Broadcaster broadcaster,BitcoinLossProtectedWalletManager lossProtectedWalletManager) {
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.eventManager = eventManager;
        this.broadcaster = broadcaster;
        this.lossProtectedWalletManager = lossProtectedWalletManager;
    }

    public void handleTransaction(Transaction<CryptoTransaction> transaction) throws CantGetCryptoAddressBookRecordException, CantLoadWalletsException, CantRegisterCreditException, CantRegisterDebitException, com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException {
        try {
            try {
                CryptoAddressBookRecord cryptoAddressBookRecord = this.cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(transaction.getInformation().getAddressTo());
                ReferenceWallet         referenceWallet         = cryptoAddressBookRecord.getWalletType();
                String                  walletPublicKey         = cryptoAddressBookRecord.getWalletPublicKey();

                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util.TransactionExecutorFactory executorFactory = new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util.TransactionExecutorFactory(this.cryptoWalletManager, this.cryptoAddressBookManager,lossProtectedWalletManager);
                com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.TransactionExecutor executor        = executorFactory.newTransactionExecutor(referenceWallet, walletPublicKey);

                executor.executeTransaction(transaction);

                launchIncomingMoneyNotificationEvent(cryptoAddressBookRecord,transaction);

            } catch (CryptoAddressBookRecordNotFoundException exception) {
                //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
                throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
            }
        } catch (CantGetCryptoAddressBookRecordException | CantLoadWalletsException | CantRegisterCreditException | CantRegisterDebitException | com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException e){
            throw e;
        } catch (Exception exception) {
            //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
            throw new CantGetCryptoAddressBookRecordException(CantGetCryptoAddressBookRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
        }
    }

    private void launchIncomingMoneyNotificationEvent(CryptoAddressBookRecord cryptoAddressBookRecord,Transaction<CryptoTransaction> transaction) {

        FermatBundle fermatBundle = new FermatBundle();

        fermatBundle.put("NotificationType","TRANSACTIONARRIVE");
        fermatBundle.put("InvolvedActor","");
        fermatBundle.put("Amount",transaction.getInformation().getCryptoAmount());

        broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE,cryptoAddressBookRecord.getWalletPublicKey(),  "TRANSACTIONARRIVE_" + transaction.getTransactionID().toString());

    }
}
