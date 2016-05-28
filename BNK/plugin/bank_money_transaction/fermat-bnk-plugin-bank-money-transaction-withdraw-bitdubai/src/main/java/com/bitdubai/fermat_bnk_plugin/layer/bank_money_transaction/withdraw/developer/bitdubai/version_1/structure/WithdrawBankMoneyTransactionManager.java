package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces.WithdrawManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.WithdrawBankMoneyTransactionPluginRoot;
import com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.withdraw.developer.bitdubai.version_1.database.WithdrawBankMoneyTransactionDao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


/**
 * Created by memo on 19/11/15.
 */
public class WithdrawBankMoneyTransactionManager implements WithdrawManager, Serializable {


    private WithdrawBankMoneyTransactionPluginRoot pluginRoot;
    private WithdrawBankMoneyTransactionDao withdrawBankMoneyTransactionDao;
    private BankMoneyWalletManager bankMoneyWalletManager;

    public WithdrawBankMoneyTransactionManager(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, WithdrawBankMoneyTransactionPluginRoot pluginRoot, BankMoneyWalletManager bankMoneyWalletManager) throws CantStartPluginException {
        this.pluginRoot = pluginRoot;
        withdrawBankMoneyTransactionDao = new WithdrawBankMoneyTransactionDao(pluginId, pluginDatabaseSystem, pluginRoot);
        this.bankMoneyWalletManager = bankMoneyWalletManager;

        try {
            withdrawBankMoneyTransactionDao.initialize();
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_DEPOSIT_MONEY_TRANSACTION);
        }

    }


    @Override
    public BankTransaction makeWithdraw(BankTransactionParameters bankTransactionParameters) throws CantMakeWithdrawTransactionException {
        withdrawBankMoneyTransactionDao.registerWithdrawTransaction(bankTransactionParameters);
        try {
            BigDecimal availableBalance = bankMoneyWalletManager.getAvailableBalance().getBalance(bankTransactionParameters.getAccount());
            BigDecimal bookBalance = bankMoneyWalletManager.getBookBalance().getBalance(bankTransactionParameters.getAccount());

            if (bankTransactionParameters.getAmount().compareTo(availableBalance) <= 0 && bankTransactionParameters.getAmount().compareTo(bookBalance) <= 0) {
                bankMoneyWalletManager.getAvailableBalance().debit(new BankMoneyTransactionRecordImpl(pluginRoot, UUID.randomUUID(), BalanceType.AVAILABLE.getCode(), TransactionType.DEBIT.getCode(), bankTransactionParameters.getAmount(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), new BigDecimal(0), new BigDecimal(0), (new Date().getTime()), bankTransactionParameters.getMemo(), null));
                bankMoneyWalletManager.getBookBalance().debit(new BankMoneyTransactionRecordImpl(pluginRoot, UUID.randomUUID(), BalanceType.BOOK.getCode(), TransactionType.DEBIT.getCode(), bankTransactionParameters.getAmount(), bankTransactionParameters.getCurrency().getCode(), BankOperationType.WITHDRAW.getCode(), "test_reference", null, bankTransactionParameters.getAccount(), BankAccountType.SAVINGS.getCode(), new BigDecimal(0), new BigDecimal(0), (new Date().getTime()), bankTransactionParameters.getMemo(), null));
            } else {
                throw new CantMakeWithdrawTransactionException(CantMakeWithdrawTransactionException.DEFAULT_MESSAGE + " no posee suficiente fondos para realizar esta transaccion", null, "no posee suficiente fondos para realizar esta transaccion", null);
            }
        } catch (CantRegisterDebitException | CantCalculateBalanceException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantMakeWithdrawTransactionException(CantRegisterDebitException.DEFAULT_MESSAGE, e, null, null);
        }
        return new BankTransactionImpl(bankTransactionParameters.getTransactionId(), bankTransactionParameters.getPublicKeyPlugin(), bankTransactionParameters.getPublicKeyWallet(),
                bankTransactionParameters.getAmount(), bankTransactionParameters.getAccount(), bankTransactionParameters.getCurrency(), bankTransactionParameters.getMemo(), BankOperationType.WITHDRAW, TransactionType.DEBIT, new Date().getTime(), BankTransactionStatus.CONFIRMED);
    }
}
