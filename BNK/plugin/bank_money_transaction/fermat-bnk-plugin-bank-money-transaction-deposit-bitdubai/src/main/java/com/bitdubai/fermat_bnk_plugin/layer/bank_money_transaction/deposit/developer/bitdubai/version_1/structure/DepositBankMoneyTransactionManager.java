package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionManager implements DepositManager {


    private UUID pluginId;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private ErrorManager errorManager;
    private DepositBankMoneyTransactionDao depositBankMoneyTransactionDao;
    private BankMoneyWallet bankMoneyWallet;
    private BankMoneyTransactionRecordImpl bankMoneyTransactionRecord;

    public DepositBankMoneyTransactionManager(UUID pluginId,PluginDatabaseSystem pluginDatabaseSystem,ErrorManager errorManager) throws CantStartPluginException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager=errorManager;
        depositBankMoneyTransactionDao = new DepositBankMoneyTransactionDao(pluginId,pluginDatabaseSystem,errorManager);
        try {
            depositBankMoneyTransactionDao.initialize();
        }catch (CantInitializeDepositBankMoneyTransactionDatabaseException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }catch (Exception e){
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }



    @Override
    public BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionDao.registerDepositTransaction(parameters);
        try{
            bankMoneyWallet.getAvailableBalance().credit(bankMoneyTransactionRecord);
            bankMoneyWallet.getBookBalance().credit(bankMoneyTransactionRecord);
        }catch (CantRegisterCreditException e){
            throw new CantMakeDepositTransactionException(CantRegisterCreditException.DEFAULT_MESSAGE,e,null,null);
        }

        return null;
    }


    public void setBankMoneyWallet(BankMoneyWallet bankMoneyWallet) {
        this.bankMoneyWallet = bankMoneyWallet;
    }
}
