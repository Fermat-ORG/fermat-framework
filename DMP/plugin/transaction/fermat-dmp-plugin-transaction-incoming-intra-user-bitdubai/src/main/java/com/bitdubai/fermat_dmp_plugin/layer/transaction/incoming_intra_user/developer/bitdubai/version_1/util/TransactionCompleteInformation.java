package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGenerateTransactionException;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.util.TransactionCompleteInformation</code>
 * gathers the information from the crypto transaction and its metadata.
 */
public class TransactionCompleteInformation {

    private Transaction<CryptoTransaction>       cryptoTransactionContainer;
    private Transaction<FermatCryptoTransaction> transactionMetadata;

    public TransactionCompleteInformation(Transaction<CryptoTransaction> cryptoTransaction, Transaction<FermatCryptoTransaction> transactionMetadata){
        this.cryptoTransactionContainer   = cryptoTransaction;
        this.transactionMetadata = transactionMetadata;
    }

    public Transaction<CryptoTransaction> getCryptoTransaction() {
        return this.cryptoTransactionContainer;
    }

    public Transaction<FermatCryptoTransaction> getTransactionMetadata() {
        return this.transactionMetadata;
    }

    public BitcoinWalletTransactionRecord generateBitcoinTransaction(final CryptoAddressBookManager cryptoAddressBookManager) throws IncomingIntraUserCantGenerateTransactionException {

        try {
            CryptoTransaction cryptoTransaction             = this.cryptoTransactionContainer.getInformation();
            CryptoAddressBookRecord cryptoAddressBookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(cryptoTransaction.getAddressTo());

            long timestamp                                                     = this.cryptoTransactionContainer.getTimestamp();
            String memo                                                        = this.transactionMetadata.getInformation().getPaymentDescription();
            IncomingIntraUserTransactionWrapper bitcoinWalletTransactionRecord = new IncomingIntraUserTransactionWrapper();

            bitcoinWalletTransactionRecord.setIdTransaction(this.cryptoTransactionContainer.getTransactionID());
            bitcoinWalletTransactionRecord.setTransactionHash(cryptoTransaction.getTransactionHash());
            bitcoinWalletTransactionRecord.setAddressFrom(cryptoTransaction.getAddressFrom());
            bitcoinWalletTransactionRecord.setAddressTo(cryptoTransaction.getAddressTo());
            bitcoinWalletTransactionRecord.setAmount(cryptoTransaction.getCryptoAmount());
            bitcoinWalletTransactionRecord.setTimestamp(timestamp);
            bitcoinWalletTransactionRecord.setMemo(memo);

            bitcoinWalletTransactionRecord.setActorFromPublicKey(cryptoAddressBookRecord.getDeliveredByActorPublicKey());
            bitcoinWalletTransactionRecord.setActorFromType(cryptoAddressBookRecord.getDeliveredByActorType());
            bitcoinWalletTransactionRecord.setActorToPublicKey(cryptoAddressBookRecord.getDeliveredToActorPublicKey());
            bitcoinWalletTransactionRecord.setActorToType(cryptoAddressBookRecord.getDeliveredToActorType());

            return bitcoinWalletTransactionRecord;

        } catch (CantGetCryptoAddressBookRecordException e) {
            throw new IncomingIntraUserCantGenerateTransactionException("I couldn't get crypto address book record",e,"","");
        } catch (CryptoAddressBookRecordNotFoundException e) {
            throw new IncomingIntraUserCantGenerateTransactionException("I couldn't find the crypto address book record",e,"","");
        }
    }
}
