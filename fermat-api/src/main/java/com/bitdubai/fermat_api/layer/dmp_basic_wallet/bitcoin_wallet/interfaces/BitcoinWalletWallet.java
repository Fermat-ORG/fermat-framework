package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantListTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantFindTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet</code>
 * haves all consumable methods from a bitcoin wallet.
 *
 * Created by eze on 2015.06.17..
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 * @version 1.0
 */
public interface BitcoinWalletWallet {

    /**
     * Throw the method <code>getAvailableBalance</code> you can get an instance of BitcoinWalletBalance that allows you
     * to do all possible actions over the available balance.
     *
     * @return an instance of BitcoinWalletBalance.
     */
    BitcoinWalletBalance getAvailableBalance();

    /**
     * Throw the method <code>getBookBalance</code> you can get an instance of BitcoinWalletBalance that allows you
     * to do all possible actions over the book balance.
     *
     * @return an instance of BitcoinWalletBalance.
     */
    BitcoinWalletBalance getBookBalance();

    /**
     * Throw the method <code>getTransactions</code> you can get all the transactions made with the loaded wallet.
     *
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of bitcoin wallet transactions.
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<BitcoinWalletTransaction> getTransactions(int max,
                                                   int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>listTransactionsByActor</code> you can get all the transactions made with the
     * loaded wallet related with an specific actor (both credits or debits).
     *
     * @param actorPublicKey public key of the actor from which we need the transaction
     * @param max quantity of instance you want to return
     * @param offset the point of start in the list you're trying to bring.
     * @return a list of bitcoin wallet transactions.
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<BitcoinWalletTransaction> listTransactionsByActor(String actorPublicKey,
                                                           int max,
                                                           int offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>setTransactionDescription</code> you can add or change a description for an existent transaction.
     *
     * @param transactionID to identify the transaction.
     * @param description string describing the transaction
     * @throws CantStoreMemoException if something goes wrong.
     * @throws CantFindTransactionException if we cant find the transaction.
     */
    void setTransactionDescription(UUID   transactionID,
                                   String description) throws CantStoreMemoException, CantFindTransactionException;
}
