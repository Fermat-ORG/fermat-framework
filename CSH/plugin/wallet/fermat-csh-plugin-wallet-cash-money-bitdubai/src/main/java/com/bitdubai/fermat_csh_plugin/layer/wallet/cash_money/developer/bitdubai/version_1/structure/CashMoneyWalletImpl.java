package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantChangeCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantRegisterCashMoneyWalletTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CashMoneyWalletImpl implements CashMoneyWallet {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;

    private CashMoneyWalletDao dao;
    private CashMoneyWalletBalanceImpl cashMoneyWalletBalanceImpl;
    private String walletPublicKey;


    public CashMoneyWalletImpl(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) throws CantGetCashMoneyWalletException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

        try {
            this.dao = new CashMoneyWalletDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();

            this.cashMoneyWalletBalanceImpl = new CashMoneyWalletBalanceImpl(pluginDatabaseSystem, pluginId, errorManager);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletException(CantGetCashMoneyWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }


    @Override
    public void changeWalletTo(String walletPublicKey) throws CantChangeCashMoneyWalletException {
        if (dao.walletExists(walletPublicKey))
            this.walletPublicKey = walletPublicKey;
        else {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, new CantChangeCashMoneyWalletException());
            throw new CantChangeCashMoneyWalletException(CantChangeCashMoneyWalletException.DEFAULT_MESSAGE, null, "CashMoneyWalletImpl", "This wallet is not registered. Public key" + walletPublicKey);
        }
    }

    @Override
    public FiatCurrency getCurrency() throws CantGetCashMoneyWalletCurrencyException {
        return dao.getWalletCurrency(walletPublicKey);
    }


    @Override
    public CashMoneyWalletBalance getBookBalance() throws CantGetCashMoneyWalletBalanceException {
        this.cashMoneyWalletBalanceImpl.changeBalanceTo(BalanceType.BOOK);
        return this.cashMoneyWalletBalanceImpl;
    }

    @Override
    public CashMoneyWalletBalance getAvailableBalance() throws CantGetCashMoneyWalletBalanceException {
        this.cashMoneyWalletBalanceImpl.changeBalanceTo(BalanceType.AVAILABLE);
        return this.cashMoneyWalletBalanceImpl;
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(TransactionType transactionType, int max, int offset) throws CantGetCashMoneyWalletTransactionsException {
        return dao.getTransactions(transactionType, max, offset);
    }


    @Override
    public double getHeldFunds(String actorPublicKey) throws CantGetHeldFundsException {
        return dao.getHeldFunds(walletPublicKey, actorPublicKey);
    }


    @Override
    public void hold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterHoldException {
        try {
            dao.debit(walletPublicKey, BalanceType.AVAILABLE, cashMoneyWalletTransaction.getAmount());
            dao.registerTransaction(cashMoneyWalletTransaction);
        }catch (CantRegisterCashMoneyWalletTransactionException | CantRegisterDebitException e) {
            throw new CantRegisterHoldException(CantRegisterHoldException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

    @Override
    public void unhold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterUnholdException {
        try {
            dao.credit(walletPublicKey, BalanceType.AVAILABLE, cashMoneyWalletTransaction.getAmount());
            dao.registerTransaction(cashMoneyWalletTransaction);
        }catch (CantRegisterCashMoneyWalletTransactionException | CantRegisterCreditException e) {
            throw new CantRegisterUnholdException(CantRegisterUnholdException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

}
