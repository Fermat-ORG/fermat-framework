package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantGetTransactionsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletWallet {

    /*
    * Get wallet public key
   */
    public String getWalletPublicKey();

    public BitcoinWalletBalance getAvailableBalance();

    public BitcoinWalletBalance getBookBalance();

    public List<BitcoinWalletTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException;

    public void setDescription(UUID transactionID, String memo) throws CantStoreMemoException, CantFindTransactionException;
}
