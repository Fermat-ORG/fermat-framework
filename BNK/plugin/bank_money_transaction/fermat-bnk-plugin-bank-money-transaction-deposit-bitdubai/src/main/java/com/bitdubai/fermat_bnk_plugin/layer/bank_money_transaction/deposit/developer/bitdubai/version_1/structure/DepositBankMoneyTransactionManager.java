package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;

import java.util.UUID;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionManager implements DepositManager {


    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;

    DepositBankMoneyTransactionDao depositBankMoneyTransactionDao;

    BankMoneyWallet bankMoneyWallet;
    BankMoneyTransactionRecord bankMoneyTransactionRecord;

    public DepositBankMoneyTransactionManager(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }



    @Override
    public BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionDao.registerDepositTransaction(parameters);
        try{
            bankMoneyWallet.getAvailableBalance().credit(bankMoneyTransactionRecord);
        }catch (CantRegisterCreditException e){
            throw new CantMakeDepositTransactionException(CantRegisterCreditException.DEFAULT_MESSAGE,e,null,null);
        }

        return null;
    }

    public void setDepositBankMoneyTransactionDao(DepositBankMoneyTransactionDao dao){
        depositBankMoneyTransactionDao = dao;
    }

    public DepositBankMoneyTransactionDao getDepositBankMoneyTransactionDao() {
        return depositBankMoneyTransactionDao;
    }

    public BankMoneyWallet getBankMoneyWallet() {
        return bankMoneyWallet;
    }

    public void setBankMoneyWallet(BankMoneyWallet bankMoneyWallet) {
        this.bankMoneyWallet = bankMoneyWallet;
    }
}
