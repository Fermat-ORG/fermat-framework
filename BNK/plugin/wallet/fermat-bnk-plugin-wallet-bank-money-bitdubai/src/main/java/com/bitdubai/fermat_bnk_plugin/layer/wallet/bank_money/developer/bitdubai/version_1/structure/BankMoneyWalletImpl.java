package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;

import java.util.List;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletImpl implements BankMoneyWallet {

    @Override
    public BankMoneyWalletBalance getBookBalance() {
        return null;
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance() {
        return null;
    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset) throws CantGetBankMoneyWalletTransactionsException {
        return null;
    }

    @Override
    public double getHeldFunds() throws CantGetHeldFundsException, CantGetHeldFundsException {
        return 0;
    }
}
