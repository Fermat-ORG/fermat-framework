package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.deposit.developer.bitdubai.version_1.database.DepositBankMoneyTransactionDao;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by memo on 19/11/15.
 */
public class DepositBankMoneyTransactionManager implements DepositManager, Serializable {

    private ErrorManager errorManager;
    private DepositBankMoneyTransactionDao depositBankMoneyTransactionDao;
    private BankMoneyWalletManager bankMoneyWalletManager;

    public DepositBankMoneyTransactionManager(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, ErrorManager errorManager, BankMoneyWalletManager bankMoneyWalletManager) throws CantStartPluginException {
        this.errorManager=errorManager;
        this.bankMoneyWalletManager = bankMoneyWalletManager;

        depositBankMoneyTransactionDao = new DepositBankMoneyTransactionDao(pluginId,pluginDatabaseSystem,errorManager);

        try {
            depositBankMoneyTransactionDao.initialize();
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);

            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }



    @Override
    public BankTransaction makeDeposit(BankTransactionParameters bankTransactionParameters) throws CantMakeDepositTransactionException {
        depositBankMoneyTransactionDao.registerDepositTransaction(bankTransactionParameters);
        try{
            bankMoneyWalletManager.getAvailableBalance().credit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.CREDIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
            bankMoneyWalletManager.getBookBalance().credit(new BankMoneyTransactionRecordImpl(errorManager,UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.CREDIT.getCode(), bankTransactionParameters.getAmount().floatValue(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.DEPOSIT.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), 0, 0, (new Date().getTime()), bankTransactionParameters.getMemo(), null));
        }catch (CantRegisterCreditException e){
            throw new CantMakeDepositTransactionException(CantRegisterCreditException.DEFAULT_MESSAGE,e,null,null);
        }
        //TODO: Colocar BigDecimal el valor Float bankTransactionParameters.getAmount().floatValue()
        return new BankTransactionImpl(bankTransactionParameters.getTransactionId(),bankTransactionParameters.getPublicKeyPlugin(),bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getAmount().floatValue(),bankTransactionParameters.getAccount(),bankTransactionParameters.getCurrency(),bankTransactionParameters.getMemo(), BankOperationType.DEPOSIT, TransactionType.CREDIT,new Date().getTime(), BankTransactionStatus.CONFIRMED);

    }
}
