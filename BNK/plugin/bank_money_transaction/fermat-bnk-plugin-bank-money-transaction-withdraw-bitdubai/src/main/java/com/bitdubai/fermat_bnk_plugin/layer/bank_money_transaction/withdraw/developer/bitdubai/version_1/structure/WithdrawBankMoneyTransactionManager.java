package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database.WithdrawBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }




    @Override
    public BankTransaction makeWithdraw(BankTransactionParameters bankTransactionParameters) throws CantMakeWithdrawTransactionException {
        withdrawBankMoneyTransactionDao.registerWithdrawTransaction(bankTransactionParameters);
        try{
            //TODO: Revisar Guillermo BigDecimal
            if(bankTransactionParameters.getAmount().floatValue()<bankMoneyWallet.getAvailableBalance().getBalance(bankTransactionParameters.getAccount())&& bankTransactionParameters.getAmount().floatValue()<bankMoneyWallet.getBookBalance().getBalance(bankTransactionParameters.getAccount())){
                bankMoneyWallet.getAvailableBalance().debit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.DEBIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
                bankMoneyWallet.getBookBalance().debit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.DEBIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
            }
            else{
                throw new CantMakeWithdrawTransactionException(CantMakeWithdrawTransactionException.DEFAULT_MESSAGE + " no posee suficiente fondos para realizar esta transaccion",null,"no posee suficiente fondos para realizar esta transaccion",null);
            }
        }catch (CantRegisterDebitException |CantCalculateBalanceException e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_WITHDRAW_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMakeWithdrawTransactionException(CantRegisterDebitException.DEFAULT_MESSAGE,e,null,null);
        }
        //TODO: Revisar Guillermo BigDecimal
        return new BankTransactionImpl(bankTransactionParameters.getTransactionId(),bankTransactionParameters.getPublicKeyPlugin(),bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getAmount().floatValue(),bankTransactionParameters.getAccount(),bankTransactionParameters.getCurrency(),bankTransactionParameters.getMemo(), BankOperationType.WITHDRAW, TransactionType.DEBIT,new Date().getTime(), BankTransactionStatus.CONFIRMED);
    }
    public void setBankMoneyWallet(BankMoneyWallet bankMoneyWallet) {
        this.bankMoneyWallet = bankMoneyWallet;
    }
}
