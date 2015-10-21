package com.bitdubai.fermat_ccp_api.layer.basic_wallet.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>FiatOverCryptoWalletWallet</code>
 * provides the methods to administrate and consult the balances of a fiat over crypto wallet.
 */
public interface FiatOverCryptoWalletWallet {

    /**
     * The method <code>getWalletPublicKey</code> provides the wallet public key
     *
     * @return the wallet public key
     */
    public String getWalletPublicKey();

    /**
     * The method <code>getAvailableBalance</code> gives us an interface to manipulate the available
     * balance of the wallet
     *
     * @return an interface that let us manipulate de balance
     */
    public FiatOverCryptoWalletBalance getAvailableBalance();

    /**
     * The method <code>getBookBalance</code> gives us an interface to manipulate the book
     * balance of the wallet
     *
     * @return an interface that let us manipulate de balance
     */
    public FiatOverCryptoWalletBalance getBookBalance();

    /**
     * The method <code>getTransactions</code> gives the next max transactions starting from the list
     * of transactions of the wallet starting from the position offset. If there is less than max
     * transactions in the list it returns all the transactions stored in that las portion of the list
     *
     * @param max    the maximum number of transactions to take from the transaction list
     * @param offset the starting position from which we want the next transactions to come.
     * @return the list of transactions
     * @throws CantListTransactionsException
     */
    public List<FiatOverCryptoWalletTransaction> getTransactions(int max, int offset) throws CantListTransactionsException;

    /**
     * The method <code>setDescription</code> lets the client set a description of a selected transaction
     *
     * @param transactionID The identifier of the transaction to edit its description
     * @param memo          The new description of the transaction
     * @throws CantStoreMemoException
     * @throws CantFindTransactionException
     */
    public void setDescription(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException;

}
