package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.ActorTransactionHistory;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleActorTransactionHistory</code>
 * implements the interface ActorTransactionHistory and all its methods.
 *
 * Created by mati on 2015.09.17..
 * @version 1.0
 */
public class CryptoWalletWalletModuleActorTransactionHistory implements ActorTransactionHistory {

    private long totalAmount;

    private int transactionsQuantity;

    private TransactionType transactionType;

    public CryptoWalletWalletModuleActorTransactionHistory(long totalAmount,
                                                           int transactionsQuantity,
                                                           TransactionType transactionType) {
        this.totalAmount = totalAmount;
        this.transactionsQuantity = transactionsQuantity;
        this.transactionType = transactionType;
    }

    @Override
    public int getTransactionsQuantity() {
        return transactionsQuantity;
    }

    @Override
    public long getTotalAmount() {
        return totalAmount;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }
}
