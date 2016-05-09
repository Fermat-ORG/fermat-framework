package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantListSpendingException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantRevertLossProtectedTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>BitcoinWalletWallet</code>
 * haves all consumable methods from a bitcoin wallet.
 *
 * Created by eze on 2015.06.17..
 * Modified by  Natalia Cortez 03/14/2016
 * @version 1.0
 */
public interface BitcoinLossProtectedWallet {

    /**
     * Throw the method <code>getBalance</code> you can get an instance of BitcoinWalletBalance that allows you
     * to do all possible actions over the balance of the requested type.
     *
     * @param balanceType type of balance that you need.
     *
     * @return an instance of BitcoinWalletBalance.
     */
    BitcoinLossProtectedWalletBalance getBalance(BalanceType balanceType);



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
    List<BitcoinLossProtectedWalletTransaction> listTransactions(BalanceType balanceType, TransactionType transactionType,
                                                    int max,
                                                    int offset) throws CantListTransactionsException;

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
    List<BitcoinLossProtectedWalletTransaction> listTransactionsByActor(String actorPublicKey,
                                                           BalanceType balanceType,
                                                           int max,
                                                           int offset) throws CantListTransactionsException;


    /**
     * * Throw the method <code>listTransactionsByActorAndType</code> you can get all the transactions made with the
     * loaded wallet related with an specific actor credits or debits.
     *
     * @param actorPublicKey
     * @param balanceType
     * @param transactionType
     * @param max
     * @param offset
     * @return
     * @throws CantListTransactionsException
     */
    List<BitcoinLossProtectedWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
                                                                  final BalanceType balanceType,
                                                                  final TransactionType transactionType,
                                                                  final int max,
                                                                  final int offset) throws CantListTransactionsException;
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
    List<BitcoinLossProtectedWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                              TransactionType transactionType,
                                                                              int max,
                                                                              int offset,
                                                                             BlockchainNetworkType blockchainNetworkType) throws CantListTransactionsException;

    /**
     * Throw the method <code>setTransactionDescription</code> you can add or change a description for an existent transaction.
     *
     * @param transactionID to identify the transaction.
     * @param description   string describing the transaction
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException if something goes wrong.
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException if we cant find the transaction.
     */
    void setTransactionDescription(UUID transactionID,
                                   String description) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException, com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;

    /**
     * Throw the method <code>getActorTransactionSummary</code> you can get the summary of transaction of an specific user and balance type.
     *
     * @param actorPublicKey public key of the actor from which we need the transaction
     * @param balanceType    type of balance that you're trying to get
     *
     * @return an instance of BitcoinWalletTransactionSummary
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException if something goes wrong.
     */
    BitcoinLossProtectedWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                               BalanceType balanceType) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;


    /**
     * Throw the method <code>deleteTransaction</code> you can delete an existent transaction.
     *
     * @param transactionID to identify the transaction.
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException if something goes wrong.
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException if we cant find the transaction.
     */
    void deleteTransaction(UUID transactionID) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;

    /**
     * Throw the method <code>getTransactionById</code> return transaction information.
     * @param transactionID
     * @return
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException
     */
    BitcoinLossProtectedWalletTransaction getTransactionById(UUID transactionID) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;


    /**
     *  Throw the method <code>listTransactionsSpending</code> return spending btc information for transaction id.
     * @param transactionId
     * @return
     * @throws CantListSpendingException
     */
     List<BitcoinLossProtectedWalletSpend> listTransactionsSpending(UUID transactionId) throws CantListSpendingException;
    /**
     *  Throw the method <code>listAllSpending()</code> return all spending btc information.
     * @return
     * @throws CantListSpendingException
     */
    List<BitcoinLossProtectedWalletSpend> listAllWalletSpending(BlockchainNetworkType blockchainNetworkType) throws CantListSpendingException;



    /**
         *
         * @param transactionRecord
         * @param credit
         * @throws CantRevertLossProtectedTransactionException
         */
    void revertTransaction(BitcoinLossProtectedWalletTransactionRecord transactionRecord, boolean credit) throws CantRevertLossProtectedTransactionException;
}
