package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.*;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDatabaseConstants;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * Created by memo on 23/11/15.
 */
public class BankMoneyWalletImpl implements BankMoneyWallet {
    private ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;

    BankMoneyWalletDao bankMoneyWalletDao;

    public BankMoneyWalletImpl(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem,ErrorManager errorManager) throws CantStartPluginException  {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.bankMoneyWalletDao = new BankMoneyWalletDao(this.pluginId,this.pluginDatabaseSystem,this.errorManager);
        try {
            this.bankMoneyWalletDao.initialize();
        } catch (CantInitializeBankMoneyWalletDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_BNK_HOLD_MONEY_TRANSACTION);
        } catch (Exception e) {
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
    public List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset,String account) throws CantGetBankMoneyWalletTransactionsException {
        try {
            return bankMoneyWalletDao.getTransactions(type, account);
        }catch (CantGetTransactionsException e){
            throw new CantGetBankMoneyWalletTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE,e,null,null);
        }
    }

    @Override
    public double getHeldFunds(String account) throws CantGetHeldFundsException {
        return bankMoneyWalletDao.getHeldFunds(account);
    }

    @Override
    public List<BankAccountNumber> getAccounts(UUID walletPublicKey) {
        try {
            return bankMoneyWalletDao.getAccounts(walletPublicKey.toString());
        }catch(CantGetAccountsException e){

        }
        return null;
    }

    @Override
    public void hold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterHoldException {
        try {
            bankMoneyWalletDao.makeHold(bankMoneyTransactionRecord, BalanceType.AVAILABLE);
        }catch (CantMakeHoldException e){

        }
    }

    @Override
    public void unhold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterUnholdException {
        try {
            bankMoneyWalletDao.makeUnhold(bankMoneyTransactionRecord, BalanceType.AVAILABLE);
        }catch (CantMakeUnholdException e){

        }
    }

    @Override
    public void addNewAccount(BankAccountNumber bankAccountNumber, UUID walletPublicKey) throws CantAddNewAccountException {
        try {
            bankMoneyWalletDao.addNewAccount(bankAccountNumber,walletPublicKey);
        }catch (CantInsertRecordException e){
            throw new CantAddNewAccountException(CantInsertRecordException.DEFAULT_MESSAGE,e,null,null);
        }
    }
}
