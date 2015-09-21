package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantListTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantFindTransactionException;

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
     * Throw the method <code>getBalance</code> you can get an instance of BitcoinWalletBalance that allows you
     * to do all possible actions over the balance of the requested type.
     *
     * @param balanceType type of balance that you need.
     *
     * @return an instance of BitcoinWalletBalance.
     */
    BitcoinWalletBalance getBalance(BalanceType balanceType);

    /**
     * Throw the method <code>listTransactions</code> you can list all the transactions made with the loaded wallet.
     *
     * @param balanceType type of balance that you're trying to get
     * @param max         quantity of instance you want to return
     * @param offset      the point of start in the list you're trying to bring.
     *
     * @return a list of bitcoin wallet transactions.
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<BitcoinWalletTransaction> listTransactions(BalanceType balanceType,
                                                    int         max,
                                                    int         offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>listTransactionsByActor</code> you can get all the transactions made with the
     * loaded wallet related with an specific actor (both credits or debits).
     *
     * @param actorPublicKey public key of the actor from which we need the transaction
     * @param balanceType    type of balance that you're trying to get
     * @param max            quantity of instance you want to return
     * @param offset         the point of start in the list you're trying to bring.
     *
     * @return a list of bitcoin wallet transactions.
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<BitcoinWalletTransaction> listTransactionsByActor(String      actorPublicKey,
                                                           BalanceType balanceType,
                                                           int         max,
                                                           int         offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>listLastActorTransactionsByTransactionType</code> you can get the last transaction for each actor
     * who have made transactions with this wallet.
     *
     * @param balanceType     type of balance that you need
     * @param transactionType type of transaction you want to bring, credits or debits or both.
     * @param max             quantity of instance you want to return
     * @param offset          the point of start in the list you're trying to bring.
     *
     * @return a list of crypto wallet transactions (enriched crypto transactions).
     *
     * @throws CantListTransactionsException if something goes wrong.
     */
    List<BitcoinWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType     balanceType,
                                                                              TransactionType transactionType,
                                                                              int             max,
                                                                              int             offset) throws CantListTransactionsException;

    /**
     * Throw the method <code>setTransactionDescription</code> you can add or change a description for an existent transaction.
     *
     * @param transactionID to identify the transaction.
     * @param description   string describing the transaction
     *
     * @throws CantStoreMemoException if something goes wrong.
     * @throws CantFindTransactionException if we cant find the transaction.
     */
    void setTransactionDescription(UUID   transactionID,
                                   String description) throws CantStoreMemoException, CantFindTransactionException;

    /**
     * Throw the method <code>getActorTransactionSummary</code> you can get the summary of transaction of an specific user and balance type.
     *
     * @param actorPublicKey public key of the actor from which we need the transaction
     * @param balanceType    type of balance that you're trying to get
     *
     * @return an instance of BitcoinWalletTransactionSummary
     *
     * @throws CantGetActorTransactionSummaryException if something goes wrong.
     */
    BitcoinWalletTransactionSummary getActorTransactionSummary(String      actorPublicKey,
                                                               BalanceType balanceType) throws CantGetActorTransactionSummaryException;

}
