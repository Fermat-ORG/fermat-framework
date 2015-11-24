package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletBalanceImpl implements BankMoneyWalletBalance{

    BankMoneyWalletDao bankMoneyWalletDao;

    Database database;

    BalanceType balanceType;

    public BankMoneyWalletBalanceImpl(Database database,BalanceType balanceType) {
        this.database = database;
        this.balanceType = balanceType;
    }

    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return 0;
    }

    @Override
    public void debit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterDebitException {

    }

    @Override
    public void credit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterCreditException {

    }

    @Override
    public void hold() throws CantRegisterHoldException {

    }

    @Override
    public void unhold() throws CantRegisterUnholdException {

    }
}
