package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;

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
        bankMoneyWalletDao = new BankMoneyWalletDao(this.database);
    }

    @Override
    public double getBalance() throws CantCalculateBalanceException {
        switch (balanceType){
            case AVAILABLE: return bankMoneyWalletDao.getAvailableBalance();
            case BOOK: return bankMoneyWalletDao.getBookBalance();
            default: return bankMoneyWalletDao.getAvailableBalance();
        }
    }

    @Override
    public void debit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterDebitException {
        try {
            bankMoneyWalletDao.addDebit(bankMoneyTransactionRecord, balanceType);
        }catch (CantAddDebitException e){
            throw new CantRegisterDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"couldn't debit from account",null);
        }
    }

    @Override
    public void credit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterCreditException {
        try {
            bankMoneyWalletDao.addCredit(bankMoneyTransactionRecord, balanceType);
        }catch (CantAddCreditException e){
            throw new CantRegisterCreditException(CantAddDebitException.DEFAULT_MESSAGE,e,"couldn't Credit from account",null);
        }
    }


}
