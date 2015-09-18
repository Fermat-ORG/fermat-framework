package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionHistory</code>
 * contains all methods needed to consume the Transaction History of an Actor.
 *
 * Created by mati on 2015.09.17..
 * @version 1.0
 */
public interface ActorTransactionHistory {

    /**
     * Throw the method <code>getTransactionQuantity</code> the quantity of transactions that you've with the actor.
     * @return the number of transactions
     */
    int getTransactionsQuantity();

    /**
     * Throw the method <code>getTotalAmount</code> the quantity of transactions that you've with the actor.
     * @return the sum total of the amount on each transaction.
     */
    long getTotalAmount();

    /**
     * Throw the method <code>getTransactionType</code> the type of transactions handled here, if null, means that is counting all type of transactions.
     * @return an element of TransactionType enum.
     */
    TransactionType getTransactionType();

}
