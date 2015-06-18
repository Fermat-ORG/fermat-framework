package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CabtStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWallet {

    /*
    * Get wallet Id
   */
    public UUID getWalletId();

    /*
     * Get the balance of the wallet, the result represents the
     * amount of satoshis the user has.
    */
    public long getBalance() throws CantCalculateBalanceException;

    public void debit(BitcoinTransaction cryptoTransaction) throws CantRegisterDebitDebitException;

    public void credit(BitcoinTransaction cryptoTransaction) throws CantRegisterCreditException;

    public List<BitcoinTransaction> getTransactions(int offset, int amount) throws CantGetTransactionsException;

    public void setDescription(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException;
}
