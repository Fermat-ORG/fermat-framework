package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletWallet {

    /*
    * Get wallet Id
   */
    public UUID getWalletId();

    public BitcoinWalletBalance getAvailableBalance();

    public BitcoinWalletBalance getBookBalance();

    public List<BitcoinWalletTransactionRecord> getTransactions(int max, int offset) throws CantGetTransactionsException;

    public void setDescription(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException;
}
