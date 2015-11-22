package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionSummaryBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyBalanceRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionSummary;
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
    public BankMoneyWalletBalance getBookBalance() throws CantTransactionBankMoneyException {
        return (BankMoneyWalletBalance) bankMoneyWalletDao.getBalanceType(BalanceType.BOOK);
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance() throws CantTransactionBankMoneyException {
        return (BankMoneyWalletBalance) bankMoneyWalletDao.getBalanceType(BalanceType.AVAILABLE);
    }

    @Override
    public List<BankMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionBankMoneyException {
        try {
            return bankMoneyWalletDao.getTransactions(balanceType,max,offset);
        } catch (CantGetTransactionsException e) {
          throw new CantTransactionBankMoneyException(
                  CantTransactionBankMoneyException.DEFAULT_MESSAGE,
                  e,
                  "Cant Transaction BankMoneyWallet Exception",
                  "Cant Get Transactions Exception"
          );
        }
    }

    @Override
    public BankMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryBankMoneyException {
        return null;
    }

    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return bankMoneyWalletDao.getAmaunt();
    }

    @Override
    public void debit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
        try {
            bankMoneyWalletDao.addDebit(BankMoneyBalanceRecord,balanceType);
        } catch (CantAddDebitException e) {
           throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE,
                   e,
                   "Cant Register Debit Exception",
                   "Cant Add Debit Exception");
        }
    }

    @Override
    public void credit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {
        try {
            bankMoneyWalletDao.addCredit(BankMoneyBalanceRecord, balanceType);
        } catch (CantAddCreditException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE,
                    e,
                    "Cant Register Credit Exception",
                    "Cant Add Credit Exception");
        }
    }
}
