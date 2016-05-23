package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletBalanceImpl implements BankMoneyWalletBalance{

    BankMoneyWalletDao bankMoneyWalletDao;

    BalanceType balanceType;

    public BankMoneyWalletBalanceImpl(BankMoneyWalletDao bankMoneyWalletDao,BalanceType balanceType) {
        this.balanceType = balanceType;
        this.bankMoneyWalletDao = bankMoneyWalletDao;
    }

    @Override
    public double getBalance(String accountNumber) throws CantCalculateBalanceException {
        switch (balanceType){
            case AVAILABLE: return bankMoneyWalletDao.getAvailableBalance(accountNumber);
            case BOOK: return bankMoneyWalletDao.getBookBalance(accountNumber);
            default: return bankMoneyWalletDao.getAvailableBalance(accountNumber);
        }
    }

    @Override
    public void debit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterDebitException {
        try {
            System.out.println("BNK debit ="+ bankMoneyTransactionRecord.getBankAccountNumber());
            bankMoneyWalletDao.addDebit(bankMoneyTransactionRecord, balanceType);
        }catch (CantAddDebitException e){
            throw new CantRegisterDebitException(CantAddDebitException.DEFAULT_MESSAGE,e,"couldn't debit from account",null);
        }
    }

    @Override
    public void credit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterCreditException {
        try {
            System.out.println("BNK credit ="+ bankMoneyTransactionRecord.getBankAccountNumber() + " balanceType = "+balanceType.getCode());
            bankMoneyWalletDao.addCredit(bankMoneyTransactionRecord, balanceType);
        }catch (CantAddCreditException e){
            throw new CantRegisterCreditException(CantAddCreditException.DEFAULT_MESSAGE,e,"couldn't Credit from account",null);
        }
    }


}
