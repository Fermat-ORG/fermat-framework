package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.utils.TransactionTypeAndCryptoStatus</code>
 * provides the transaction and crypto status to notify associated to a group of transactions
 */
public class TransactionTypeAndCryptoStatus {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 2437;
    private static final int HASH_PRIME_NUMBER_ADD = 757;

    private final CryptoTransactionType transactionType;
    private final CryptoStatus          cryptoStatus   ;

    public TransactionTypeAndCryptoStatus(final CryptoTransactionType transactionType,
                                          final CryptoStatus          cryptoStatus   ) {

        this.transactionType = transactionType;
        this.cryptoStatus    = cryptoStatus   ;
    }

    public CryptoTransactionType getTransactionType() {
        return transactionType;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }

    @Override
    public boolean equals(Object other){
        return other != null &&
                other instanceof TransactionTypeAndCryptoStatus &&
                 ((TransactionTypeAndCryptoStatus) other).getCryptoStatus().equals(this.cryptoStatus) &&
                  ((TransactionTypeAndCryptoStatus) other).getTransactionType().equals(this.transactionType);
    }

    @Override
    public int hashCode() {
        int finalHash = 0;
        finalHash += this.cryptoStatus.hashCode();
        finalHash += this.transactionType.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + finalHash;
    }

}
