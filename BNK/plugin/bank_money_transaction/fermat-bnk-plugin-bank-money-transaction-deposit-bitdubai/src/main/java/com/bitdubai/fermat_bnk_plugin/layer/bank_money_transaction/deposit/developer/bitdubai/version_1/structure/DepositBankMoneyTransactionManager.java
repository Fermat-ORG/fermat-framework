package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositBankMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.Date;
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }



    @Override
    public BankTransaction makeDeposit(BankTransactionParameters bankTransactionParameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionDao.registerDepositTransaction(bankTransactionParameters);
        try{
            bankMoneyWallet.getAvailableBalance().credit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.CREDIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
            bankMoneyWallet.getBookBalance().credit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.CREDIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
        }catch (CantRegisterCreditException e){
            throw new CantMakeDepositTransactionException(CantRegisterCreditException.DEFAULT_MESSAGE,e,null,null);
        }
        //TODO: Colocar BigDecimal el valor Float bankTransactionParameters.getAmount().floatValue()
        return new BankTransactionImpl(bankTransactionParameters.getTransactionId(),bankTransactionParameters.getPublicKeyPlugin(),bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getAmount().floatValue(),bankTransactionParameters.getAccount(),bankTransactionParameters.getCurrency(),bankTransactionParameters.getMemo(), BankOperationType.DEPOSIT, TransactionType.CREDIT,new Date().getTime(), BankTransactionStatus.CONFIRMED);

    }


    public void setBankMoneyWallet(BankMoneyWallet bankMoneyWallet) {
        this.bankMoneyWallet = bankMoneyWallet;
    }
}
