package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.*;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

/**
 * Created by jorgegonzalez on 2015.07.08..
 */
public class BitcoinBasicWalletTransactionExecutor implements TransactionExecutor {

    private BitcoinWalletWallet bitcoinWallet;

    public BitcoinBasicWalletTransactionExecutor(final BitcoinWalletWallet bitcoinWallet){
        this.bitcoinWallet = bitcoinWallet;
    }

    @Override
    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws  CantRegisterCreditException, CantRegisterDebitDebitException, UnexpectedTransactionException {
        switch (transaction.getInformation().getCryptoStatus()){
            case ON_CRYPTO_NETWORK:
                processOnCryptoNetworkTransaction(transaction);
                break;
            case ON_BLOCKCHAIN:
                processOnBlockChainTransaction(transaction);
                break;
            //todo ezequiel, cambie de Reversed a REVERSED_ON_CRYPTO_NETWORK
            case REVERSED_ON_CRYPTO_NETWORK:
                processReversedOnCryptoNetworkTransaction(transaction);
                break;
            case REVERSED_ON_BLOCKCHAIN:
                processReversedOnBlockchainTransaction(transaction);
                break;
            default:
                throw new UnexpectedTransactionException("El crypto status no es esperado",null,"El cryptoStatus es: "+ transaction.getInformation().getCryptoStatus().getCode(),"");
        }
    }

    private void processOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException{
        BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction.getInformation(), TransactionType.CREDIT);
        record.setIdTransaction(transaction.getTransactionID());
        bitcoinWallet.getBookBalance().credit(record);
    }

    private void processOnBlockChainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException{
        BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction.getInformation(), TransactionType.CREDIT);
        record.setIdTransaction(transaction.getTransactionID());
        bitcoinWallet.getAvailableBalance().credit(record);
    }

    private void processReversedOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitDebitException{
        BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction.getInformation(), TransactionType.CREDIT);
        record.setIdTransaction(transaction.getTransactionID());
        bitcoinWallet.getBookBalance().debit(record);
    }

    private void processReversedOnBlockchainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitDebitException{
        BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction.getInformation(), TransactionType.CREDIT);
        record.setIdTransaction(transaction.getTransactionID());
        bitcoinWallet.getAvailableBalance().debit(record);
    }


    private BitcoinWalletTransactionRecord generateBitcoinTransaction(final CryptoTransaction cryptoTransaction, final TransactionType transactionType){
        long timestamp = System.currentTimeMillis() / 1000L;
        BitcoinWalletTransactionRecord bitcoinWalletTransactionRecord = new TransactionWrapper();
        bitcoinWalletTransactionRecord.setTramsactionHash(cryptoTransaction.getTransactionHash());
        bitcoinWalletTransactionRecord.setAddressFrom(cryptoTransaction.getAddressFrom());
        bitcoinWalletTransactionRecord.setAddressTo(cryptoTransaction.getAddressTo());
        bitcoinWalletTransactionRecord.setAmount(cryptoTransaction.getCryptoAmount());
        bitcoinWalletTransactionRecord.setType(transactionType);
        bitcoinWalletTransactionRecord.setTimestamp(timestamp);
        bitcoinWalletTransactionRecord.setMemo("No information");

        return bitcoinWalletTransactionRecord;
    }

}
