package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantGenerateTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.UnexpectedTransactionException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.*;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util.TransactionWrapper;

/**
 * Created by jorgegonzalez on 2015.07.08..
 *
 */
public class BitcoinBasicWalletTransactionExecutor implements TransactionExecutor {

    private BitcoinWalletWallet bitcoinWallet;
    private CryptoAddressBookManager cryptoAddressBookManager;

    public BitcoinBasicWalletTransactionExecutor(final BitcoinWalletWallet bitcoinWallet, final CryptoAddressBookManager cryptoAddressBookManager){
        this.bitcoinWallet = bitcoinWallet;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    @Override
    public void executeTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException, CantRegisterDebitException, UnexpectedTransactionException {
        try {
            switch (transaction.getInformation().getCryptoStatus()) {
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
                case IRREVERSIBLE:
                    // define what to do here.
                    break;
                default:
                    throw new UnexpectedTransactionException("El crypto status no es esperado", null, "El cryptoStatus es: " + transaction.getInformation().getCryptoStatus().getCode(), "");
            }
        } catch (CantRegisterCreditException | CantRegisterDebitException | UnexpectedTransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedTransactionException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }
    }

    private void processOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.CREDIT);
            bitcoinWallet.getBalance(BalanceType.BOOK).credit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processOnBlockChainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterCreditException{
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.CREDIT);
            bitcoinWallet.getBalance(BalanceType.AVAILABLE).credit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterCreditException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processReversedOnCryptoNetworkTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.DEBIT);
            bitcoinWallet.getBalance(BalanceType.BOOK).debit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }

    private void processReversedOnBlockchainTransaction(Transaction<CryptoTransaction> transaction) throws CantRegisterDebitException {
        try {
            BitcoinWalletTransactionRecord record = generateBitcoinTransaction(transaction, TransactionType.DEBIT);
            bitcoinWallet.getBalance(BalanceType.AVAILABLE).debit(record);
        } catch (CantGenerateTransactionException e) {
            throw new CantRegisterDebitException("I couldn't generate the transaction",e,"","");
        }
    }


    private BitcoinWalletTransactionRecord generateBitcoinTransaction(final Transaction<CryptoTransaction> transaction, final TransactionType transactionType) throws CantGenerateTransactionException {

        try {
            CryptoTransaction cryptoTransaction = transaction.getInformation();

            CryptoAddressBookRecord cryptoAddressBookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(cryptoTransaction.getAddressTo());


            long timestamp = transaction.getTimestamp();
            TransactionWrapper bitcoinWalletTransactionRecord = new TransactionWrapper();

            bitcoinWalletTransactionRecord.setIdTransaction(transaction.getTransactionID());
            bitcoinWalletTransactionRecord.setTransactionHash(cryptoTransaction.getTransactionHash());
            bitcoinWalletTransactionRecord.setAddressFrom(cryptoTransaction.getAddressFrom());
            bitcoinWalletTransactionRecord.setAddressTo(cryptoTransaction.getAddressTo());
            bitcoinWalletTransactionRecord.setAmount(cryptoTransaction.getCryptoAmount());
            bitcoinWalletTransactionRecord.setTimestamp(timestamp);
            bitcoinWalletTransactionRecord.setMemo("No information");

            bitcoinWalletTransactionRecord.setActorFromPublicKey(cryptoAddressBookRecord.getDeliveredByActorPublicKey());
            bitcoinWalletTransactionRecord.setActorFromType(cryptoAddressBookRecord.getDeliveredByActorType());
            bitcoinWalletTransactionRecord.setActorToPublicKey(cryptoAddressBookRecord.getDeliveredToActorPublicKey());
            bitcoinWalletTransactionRecord.setActorToType(cryptoAddressBookRecord.getDeliveredToActorType());

            return bitcoinWalletTransactionRecord;

        } catch (CantGetCryptoAddressBookRecordException e) {
            throw new CantGenerateTransactionException("I couldn't get crypto address book record",e,"","");
        } catch (CryptoAddressBookRecordNotFoundException e) {
            throw new CantGenerateTransactionException("I couldn't find the crypto address book record",e,"","");
        }
    }
}
