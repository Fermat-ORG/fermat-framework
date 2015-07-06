package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.WalletAddressBookNotFoundException;
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


    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws CantGetWalletAddressBookRegistryException, CantGetWalletAddressBookException, CantLoadWalletException, CantRegisterCreditException {

/*
        UUID temporalId = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

        BitcoinWalletWallet bitcoinWallet2 = bitcoinWalletManager.loadWallet(temporalId);
        bitcoinWallet2.credit(generateBitcoinTransaction(transaction.getInformation()));
        if (true)
          return;
        */
        WalletAddressBookRegistry walletAddressBookRegistry = this.walletAddressBookManager.getWalletAddressBookRegistry();

        try{
            WalletAddressBookRecord walletAddressBookRecord = walletAddressBookRegistry.getWalletCryptoAddressBookByCryptoAddress(transaction.getInformation().getAddressTo());

            UUID walletID = walletAddressBookRecord.getWalletId();
            PlatformWalletType platformWalletType = walletAddressBookRecord.getWalletType();

            switch (platformWalletType) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    BitcoinWalletWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletID);
                    try {
                        //TODO: revisar por el cambio en la interface
                        bitcoinWalletWallet.getAvailableBalance().credit(generateBitcoinTransaction(transaction.getInformation()));
                    } catch (CantCalculateBalanceException e) {
                        e.printStackTrace();
                    }
                    System.out.println("TTF - Transaction applie by transaction executor");
                    return;
                default:
                    break;
            }
        } catch(WalletAddressBookNotFoundException exception){
            //TODO LUIS we should define what is going to happen in this case, in the meantime we throw an exception
            throw new CantGetWalletAddressBookException(CantGetWalletAddressBookException.DEFAULT_MESSAGE, exception, "", "Check the cause to see what happened");
        }

    }


    private BitcoinWalletTransactionRecord generateBitcoinTransaction(CryptoTransaction cryptoTransaction){
        BitcoinWalletTransactionRecord bitcoinWalletTransactionRecord = new TransactionWrapper();

        long timestamp = System.currentTimeMillis() / 1000L;

        bitcoinWalletTransactionRecord.setTramsactionHash(cryptoTransaction.getTransactionHash());
        bitcoinWalletTransactionRecord.setAddressFrom(cryptoTransaction.getAddressFrom());
        bitcoinWalletTransactionRecord.setAddressTo(cryptoTransaction.getAddressTo());
        bitcoinWalletTransactionRecord.setAmount(cryptoTransaction.getCryptoAmount());
        bitcoinWalletTransactionRecord.setType(TransactionType.CREDIT);
      //  bitcoinWalletTransactionRecord.setState(TransactionState.RECEIVED);
        bitcoinWalletTransactionRecord.setTimestamp(timestamp);
        bitcoinWalletTransactionRecord.setMemo("No information");

        return bitcoinWalletTransactionRecord;
    }
}
