package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantAddNewAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantEditAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.WalletBankMoneyPluginRoot;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantGetAccountsException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyWalletDatabaseException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletManagerImpl implements BankMoneyWalletManager, Serializable {
    private WalletBankMoneyPluginRoot pluginRoot;
    BankMoneyWalletDao bankMoneyWalletDao;

    public BankMoneyWalletManagerImpl(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem, WalletBankMoneyPluginRoot pluginRoot, String publicKey) throws CantStartPluginException {
        this.pluginRoot = pluginRoot;
        this.bankMoneyWalletDao = new BankMoneyWalletDao(pluginId, pluginDatabaseSystem, pluginRoot, publicKey);

        try {
            this.bankMoneyWalletDao.initialize();
        } catch (CantInitializeBankMoneyWalletDatabaseException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION);
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public BankMoneyWalletBalance getBookBalance() {
        return new BankMoneyWalletBalanceImpl(bankMoneyWalletDao, BalanceType.BOOK);
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance() {
        return new BankMoneyWalletBalanceImpl(bankMoneyWalletDao, BalanceType.AVAILABLE);
    }

    @Override
    public List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset, String account) throws CantGetBankMoneyWalletTransactionsException {
        try {
            return bankMoneyWalletDao.getTransactions(type, account);
        } catch (CantGetTransactionsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetBankMoneyWalletTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public BigDecimal getHeldFunds(String account) throws CantGetHeldFundsException {
        return bankMoneyWalletDao.getHeldFunds(account);
    }

    @Override
    public List<BankAccountNumber> getAccounts() {
        try {
            return bankMoneyWalletDao.getAccounts();
        } catch (CantGetAccountsException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return null;
    }

    @Override
    public void hold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterHoldException {
        try {
            getAvailableBalance().debit(bankMoneyTransactionRecord);
        } catch (FermatException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public void unhold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterUnholdException {
        try {
            getAvailableBalance().credit(bankMoneyTransactionRecord);
        } catch (FermatException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
    }

    @Override
    public void addNewAccount(BankAccountNumber bankAccountNumber) throws CantAddNewAccountException {
        System.out.println("registrando bankAccountNumber = " + bankAccountNumber.getAccount());
        try {
            bankMoneyWalletDao.addNewAccount(bankAccountNumber);
        } catch (CantInsertRecordException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantAddNewAccountException(CantAddNewAccountException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void editAccount(String originalAccountNumber, String newAlias, String newAccountNumber, String newImageId) throws CantEditAccountException {
        try {
            bankMoneyWalletDao.editAccount(originalAccountNumber, newAlias, newAccountNumber, newImageId);
        } catch (CantUpdateRecordException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantEditAccountException(CantEditAccountException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    @Override
    public void createBankName(String bankName) {
        try {
            bankMoneyWalletDao.createBankName(bankName);
        } catch (FermatException e) {
            pluginRoot.reportError(null, e);
        }
    }

    @Override
    public String getBankName() {
        try {
            return bankMoneyWalletDao.getBankName();
        } catch (FermatException e) {
            pluginRoot.reportError(null, e);
        }
        return null;
    }
}
