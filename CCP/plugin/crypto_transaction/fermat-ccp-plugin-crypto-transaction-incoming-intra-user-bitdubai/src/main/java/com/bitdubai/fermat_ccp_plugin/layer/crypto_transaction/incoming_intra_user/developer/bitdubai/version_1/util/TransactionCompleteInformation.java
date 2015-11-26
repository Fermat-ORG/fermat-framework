package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.FermatCryptoTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransactionRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantGetCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CryptoAddressBookRecordNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;

/**
 * The class <code>TransactionCompleteInformation</code>
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

    public BitcoinWalletTransactionRecord generateBitcoinTransaction(final CryptoAddressBookManager cryptoAddressBookManager) throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGenerateTransactionException {

        try {
            CryptoTransaction       cryptoTransaction       = this.cryptoTransactionContainer.getInformation();
            CryptoAddressBookRecord cryptoAddressBookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(cryptoTransaction.getAddressTo());

            long  timestamp = this.cryptoTransactionContainer.getTimestamp()                          ;
            String memo     = this.transactionMetadata       .getInformation().getPaymentDescription();

            return new IncomingIntraUserTransactionWrapper(
                    cryptoTransactionContainer.getTransactionID()            ,
                    null,
                    cryptoAddressBookRecord   .getDeliveredByActorPublicKey(),
                    cryptoAddressBookRecord   .getDeliveredToActorPublicKey(),
                    cryptoAddressBookRecord   .getDeliveredByActorType()     ,
                    cryptoAddressBookRecord   .getDeliveredToActorType()     ,
                    cryptoTransaction         .getTransactionHash()          ,
                    cryptoTransaction         .getAddressFrom()              ,
                    cryptoTransaction         .getAddressTo()                ,
                    cryptoTransaction         .getCryptoAmount()             ,
                    timestamp                                                ,
                    memo
            );

        } catch (CantGetCryptoAddressBookRecordException e) {

            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGenerateTransactionException("I couldn't get crypto address book record",e,"","");
        } catch (CryptoAddressBookRecordNotFoundException e) {

            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantGenerateTransactionException("I couldn't find the crypto address book record",e,"","");
        }
    }
}
