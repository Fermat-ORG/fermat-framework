package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CabtStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWallet implements BitcoinWallet {

    long balance =123;

    @Override
    public UUID getWalletId() {
        return UUID.fromString("25428311-deb3-4064-93b2-69093e859871");
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return balance;
    }

    @Override
    public void debit(BitcoinTransaction cryptoTransaction) throws CantRegisterDebitDebitException {
        this.balance -= cryptoTransaction.getAmount();

    }

    @Override
    public void credit(BitcoinTransaction cryptoTransaction) throws CantRegisterCreditException {
        this.balance += cryptoTransaction.getAmount();
    }

    @Override
    public List<BitcoinTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException {
        return new ArrayList<>();
    }

    @Override
    public void setDescription(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException {

    }
}
