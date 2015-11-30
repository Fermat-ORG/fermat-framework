package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database.WithdrawBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by memo on 19/11/15.
 */
public class WithdrawBankMoneyTransactionManager implements WithdrawManager {


    private UUID pluginId;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private ErrorManager errorManager;
    private WithdrawBankMoneyTransactionDao withdrawBankMoneyTransactionDao;
    private BankMoneyWallet bankMoneyWallet;
    private BankMoneyTransactionRecordImpl bankMoneyTransactionRecord;

    public WithdrawBankMoneyTransactionManager(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager) throws CantStartPluginException {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager=errorManager;
        withdrawBankMoneyTransactionDao = new WithdrawBankMoneyTransactionDao(pluginId,pluginDatabaseSystem,errorManager);
        try {
            withdrawBankMoneyTransactionDao.initialize();
        }catch (CantInitializeWithdrawBankMoneyTransactionDatabaseException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }catch (Exception e){
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }




    @Override
    public BankTransaction makeWithdraw(BankTransactionParameters parameters) throws CantMakeWithdrawTransactionException {
        withdrawBankMoneyTransactionDao.registerWithdrawTransaction(parameters);
        try{
            if(parameters.getAmount()<bankMoneyWallet.getAvailableBalance().getBalance(parameters.getAccount())&& parameters.getAmount()<bankMoneyWallet.getBookBalance().getBalance(parameters.getAccount())){
                bankMoneyWallet.getAvailableBalance().debit(bankMoneyTransactionRecord);
                bankMoneyWallet.getBookBalance().debit(bankMoneyTransactionRecord);
            }
            else{
                throw new CantMakeWithdrawTransactionException(CantMakeWithdrawTransactionException.DEFAULT_MESSAGE + " no posee suficiente fondos para realizar esta transaccion",null,"no posee suficiente fondos para realizar esta transaccion",null);
            }
        }catch (CantRegisterDebitException |CantCalculateBalanceException e){
            throw new CantMakeWithdrawTransactionException(CantRegisterDebitException.DEFAULT_MESSAGE,e,null,null);
        }
        //TODO: retornar objeto de bank transaction
        return null;
    }
    public void setBankMoneyWallet(BankMoneyWallet bankMoneyWallet) {
        this.bankMoneyWallet = bankMoneyWallet;
    }
}
