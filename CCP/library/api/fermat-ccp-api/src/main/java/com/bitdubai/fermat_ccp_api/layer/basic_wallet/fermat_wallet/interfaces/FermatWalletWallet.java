package com.bitdubai.fermat_ccp_api.layer.basic_wallet.fermat_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.fermat_wallet.exceptions.CantRevertTransactionException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>CryptoWalletWallet</code>
 * haves all consumable methods from a bitcoin wallet.
 *
 * Created by eze on 2015.06.17..
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 * @version 1.0
 */
public interface FermatWalletWallet {

    /**
     * Throw the method <code>getBalance</code> you can get an instance of CryptoWalletBalance that allows you
     * to do all possible actions over the balance of the requested type.
     *
     * @param balanceType type of balance that you need.
     *
     * @return an instance of CryptoWalletBalance.
     */
    FermatWalletBalance getBalance(BalanceType balanceType);



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
    List<FermatWalletTransaction> listTransactions(BalanceType balanceType, TransactionType transactionType,
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
    List<FermatWalletTransaction> listTransactionsByActor(String actorPublicKey,
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
    List<FermatWalletTransaction> listTransactionsByActorAndType(final String actorPublicKey,
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
    List<FermatWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                             TransactionType transactionType,
                                                                             int max,
                                                                             int offset) throws CantListTransactionsException;

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
     * @return an instance of CryptoWalletTransactionSummary
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException if something goes wrong.
     */
    FermatWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
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
    FermatWalletTransaction getTransactionById(UUID transactionID) throws com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;


    /**
     *
     * @param transactionRecord
     * @param credit
     * @throws CantRevertTransactionException
     */
    void revertTransaction(FermatWalletTransactionRecord transactionRecord, boolean credit) throws CantRevertTransactionException;
}
