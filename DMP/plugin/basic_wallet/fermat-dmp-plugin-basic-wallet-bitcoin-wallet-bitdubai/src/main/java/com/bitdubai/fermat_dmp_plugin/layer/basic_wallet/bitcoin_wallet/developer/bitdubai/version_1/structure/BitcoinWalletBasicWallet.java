package com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CabtStoreMemoException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.06.23..
 */
public class BitcoinWalletBasicWallet implements BitcoinWallet {
    @Override
    public UUID getWalletId() {
        return null;
    }

    @Override
    public long getBalance() throws CantCalculateBalanceException {
        return 0;
    }

    @Override
    public void debit(BitcoinTransaction cryptoTransaction) throws CantRegisterDebitDebitException {

    }

    @Override
    public void credit(BitcoinTransaction cryptoTransaction) throws CantRegisterCreditException {

    }

    @Override
    public List<BitcoinTransaction> getTransactions(int max, int offset) throws CantGetTransactionsException {
        return null;
    }

    @Override
    public void setDescription(UUID transactionID, String memo) throws CabtStoreMemoException, CantFindTransactionException {

    }
}
