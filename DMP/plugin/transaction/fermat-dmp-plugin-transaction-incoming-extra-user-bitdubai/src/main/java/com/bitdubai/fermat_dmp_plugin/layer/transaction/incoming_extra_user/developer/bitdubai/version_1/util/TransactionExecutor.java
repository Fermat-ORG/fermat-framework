package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletCryptoAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;

import java.util.UUID;

/**
 * Created by eze on 2015.06.22..
 */
public class TransactionExecutor implements DealsWithBitcoinWallet, DealsWithWalletAddressBook {

    /*
     * DealsWithBitcoinWallet Interface member variables
    */
    private BitcoinWalletManager bitcoinWalletManager;


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
    * DealsWithWalletAddressBook method implementation
    */
    @Override
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
    }



    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws CantGetWalletAddressBookRegistryException, CantGetWalletCryptoAddressBookException, CantLoadWalletException, CantRegisterCreditException {


        UUID temporalId = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

        BitcoinWallet bitcoinWallet2 = bitcoinWalletManager.loadWallet(temporalId);
        bitcoinWallet2.credit(generateBitcoinTransaction(transaction.getInformation()));
        if (true)
          return;

        UUID walletID;
        PlatformWalletType platformWalletType;


        WalletAddressBookRegistry walletAddressBookRegistry = this.walletAddressBookManager.getWalletAddressBookRegistry();

        WalletAddressBookRecord walletAddressBookRecord = walletAddressBookRegistry.getWalletCryptoAddressBookByCryptoAddress(transaction.getInformation().getAddressTo());

        walletID = walletAddressBookRecord.getWalletId();
        platformWalletType = walletAddressBookRecord.getWalletType();

        switch (platformWalletType) {
            case BASIC_WALLET_BITCOIN_WALLET:
                BitcoinWallet bitcoinWallet = bitcoinWalletManager.loadWallet(walletID);
                bitcoinWallet.credit(generateBitcoinTransaction(transaction.getInformation()));
                return;
            default:
                break;
        }
    }


    private BitcoinTransaction generateBitcoinTransaction(CryptoTransaction cryptoTransaction){
        BitcoinTransaction bitcoinTransaction = new TransactionWrapper();

        long timestamp = System.currentTimeMillis() / 1000L;

        bitcoinTransaction.setAddressFrom(cryptoTransaction.getAddressFrom());
        bitcoinTransaction.setAddressTo(cryptoTransaction.getAddressTo());
        bitcoinTransaction.setAmount(cryptoTransaction.getCryptoAmount());
        bitcoinTransaction.setType(TransactionType.CREDIT);
        bitcoinTransaction.setState(TransactionState.RECEIVED);
        bitcoinTransaction.setTimestamp(timestamp);
        bitcoinTransaction.setMemo("No information");

        return bitcoinTransaction;
    }
}
