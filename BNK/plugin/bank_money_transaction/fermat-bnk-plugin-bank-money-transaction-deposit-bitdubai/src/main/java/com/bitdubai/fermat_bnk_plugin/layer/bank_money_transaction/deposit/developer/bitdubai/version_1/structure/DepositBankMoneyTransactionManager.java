package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;

import java.util.UUID;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionManager implements DepositManager {


    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;

    DepositBankMoneyTransactionDao depositBankMoneyTransactionDao;



    public DepositBankMoneyTransactionManager(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }



    @Override
    public BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionDao.registerDepositTransaction(parameters);
        //con la wallet hacer el credito.
        return null;
    }

    public void setDepositBankMoneyTransactionDao(DepositBankMoneyTransactionDao dao){
        depositBankMoneyTransactionDao = dao;
    }

}
