package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionExecutorFactory;


/**
 * Created by eze on 2015.06.22..
 */
public class IncomingExtraUserTransactionHandler implements DealsWithBitcoinWallet, DealsWithActorAddressBook, DealsWithWalletAddressBook {

    /*
     * DealsWithBitcoinWallet Interface member variables
    */
    private BitcoinWalletManager bitcoinWalletManager;

    /*
     * DealsWithActorAddressBook Interface member variables
     */
    private ActorAddressBookManager actorAddressBookManager;
    /*
    * DealsWithWalletAddressBook member variables
    */
    private WalletAddressBookManager walletAddressBookManager;

    /*
     * DealsWithBitcoinWallet Interface method implementation
    */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /*
     * DealsWithActorAddressBook Interface method implementation
     */
    @Override
    public void setActorAddressBookManager(ActorAddressBookManager actorAddressBookManager) {
        this.actorAddressBookManager = actorAddressBookManager;
    }

    /*
    * DealsWithWalletAddressBook method implementation
    */
    @Override
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
    }


    public void handleTransaction(Transaction<CryptoTransaction> transaction) throws CantGetWalletAddressBookRegistryException, CantGetWalletAddressBookException, CantLoadWalletException, CantRegisterCreditException, CantRegisterDebitException, UnexpectedTransactionException {
        try {
            WalletAddressBookRegistry walletAddressBookRegistry = this.walletAddressBookManager.getWalletAddressBookRegistry();
            try {
                WalletAddressBookRecord walletAddressBookRecord = walletAddressBookRegistry.getWalletCryptoAddressBookByCryptoAddress(transaction.getInformation().getAddressTo());
                ReferenceWallet referenceWallet = walletAddressBookRecord.getWalletType();
                String walletPublicKey = walletAddressBookRecord.getWalletPublicKey();

                TransactionExecutorFactory executorFactory = new TransactionExecutorFactory(bitcoinWalletManager, actorAddressBookManager);
                TransactionExecutor executor = executorFactory.newTransactionExecutor(referenceWallet, walletPublicKey);

                executor.executeTransaction(transaction);
            } catch (WalletAddressBookNotFoundException exception) {
                //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
                throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
            }
        } catch (CantGetWalletAddressBookRegistryException | CantGetWalletAddressBookException | CantLoadWalletException | CantRegisterCreditException | CantRegisterDebitException | UnexpectedTransactionException e){
            throw e;
        } catch (Exception exception) {
            //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
        }

    }


}
