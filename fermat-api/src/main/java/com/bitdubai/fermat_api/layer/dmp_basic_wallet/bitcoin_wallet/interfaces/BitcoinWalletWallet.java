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
public interface BitcoinWalletWallet {

    /*
    * Get wallet Id
   */
    public UUID getWalletId();

    /*
     * Get the balance of the wallet, the result represents the
     * amount of satoshis the user has.
    */

    /* TODO: NATALIA los siguientes 3 métodos pasan a la interfaz Wallet Balance
     * getBalance se convierte en dos metodos, uno llamado getAvailableBalance
     * que retorna una interfaz WalletBalance que representa al available balance y
     * otro método getBookBalance que retorna una interfaz WalletBalance pero esta
     * represental al book balance.
     */
    public long getBalance() throws CantCalculateBalanceException;

    public void debit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitDebitException;

    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException;

    // Estos dos quedan aquí
    public List<BitcoinWalletTransactionRecord> getTransactions(int max, int offset) throws CantGetTransactionsException;

    public void setDescription(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException;
}
