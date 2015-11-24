package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantGetTransactionsException;

import java.util.List;

/**
 * Created by francisco on 04/11/15.
 */
public class ImplementBankMoney implements BankMoneyWallet, BankMoneyWalletBalance {

    PluginDatabaseSystem databaseSystem;
    BankMoneyWalletDao bankMoneyWalletDao= new BankMoneyWalletDao(databaseSystem);



    @Override
    public BankMoneyWalletBalance getBookBalance() {
        try {
        return (BankMoneyWalletBalance) bankMoneyWalletDao.getBalanceType(BalanceType.BOOK);
        }catch (CantCalculateBalanceException E){

        }
        return null;
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance()  {
        try {
            return (BankMoneyWalletBalance) bankMoneyWalletDao.getBalanceType(BalanceType.AVAILABLE);
        }catch (CantCalculateBalanceException E){

        }
        return  null;

    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(TransactionType transactionType, int max, int offset) throws CantGetBankMoneyWalletTransactionsException {
        try {
            return bankMoneyWalletDao.getTransactions(transactionType,max,offset);
        } catch (CantGetTransactionsException e) {
          throw new CantGetBankMoneyWalletTransactionsException(
                  CantGetBankMoneyWalletTransactionsException.DEFAULT_MESSAGE,
                  e,
                  "Cant Transaction BankMoneyWallet Exception",
                  "Cant Get Transactions Exception"
          );
        }
    }


    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return bankMoneyWalletDao.getAmaunt();
    }

    @Override
    public void debit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterDebitException {
        try {
            bankMoneyWalletDao.addDebit(bankMoneyTransactionRecord,bankMoneyTransactionRecord.getBalanceType());
        } catch (CantAddDebitException e) {
           throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,
                   e,
                   "Cant Register Debit Exception",
                   "Cant Add Debit Exception");
        }
    }

    @Override
    public void credit(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterCreditException {
        try {
            bankMoneyWalletDao.addCredit(bankMoneyTransactionRecord,bankMoneyTransactionRecord.getBalanceType());
        } catch (CantAddCreditException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE,
                    e,
                    "Cant Register Credit Exception",
                    "Cant Add Credit Exception");
        }
    }

    @Override
    public double getHeldFunds() throws CantGetHeldFundsException {
        return 0;
    }

    @Override
    public void hold() throws CantRegisterHoldException {

    }

    @Override
    public void unhold() throws CantRegisterUnholdException {

    }
}
