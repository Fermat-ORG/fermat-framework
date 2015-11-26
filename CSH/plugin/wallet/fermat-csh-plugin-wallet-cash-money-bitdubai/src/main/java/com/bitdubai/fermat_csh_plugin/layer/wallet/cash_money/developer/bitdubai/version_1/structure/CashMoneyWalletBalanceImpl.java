package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.exceptions.CantRegisterCashMoneyWalletTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/23/2015.
 */
public class CashMoneyWalletBalanceImpl implements CashMoneyWalletBalance {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private String walletPublicKey;
    private BalanceType balanceType;

    private CashMoneyWalletDao dao;


    public CashMoneyWalletBalanceImpl(final PluginDatabaseSystem pluginDatabaseSystem, final UUID pluginId, final ErrorManager errorManager) throws CantGetCashMoneyWalletBalanceException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;

        try {
            this.dao = new CashMoneyWalletDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCashMoneyWalletBalanceException(CantGetCashMoneyWalletBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void changeBalanceTo(BalanceType balanceType){
        this.balanceType = balanceType;
    }

    @Override
    public double getBalance() throws CantGetCashMoneyWalletBalanceException {
        return dao.getWalletBalance(walletPublicKey, balanceType);
    }

    @Override
    public void debit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterDebitException {
        try {
            dao.debit(walletPublicKey, balanceType, cashMoneyWalletTransaction.getAmount());
            dao.registerTransaction(cashMoneyWalletTransaction);
        } catch (CantRegisterCashMoneyWalletTransactionException e) {
            throw new CantRegisterDebitException(CantRegisterDebitException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

    @Override
    public void credit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterCreditException {
        try {
            dao.credit(walletPublicKey, balanceType, cashMoneyWalletTransaction.getAmount());
            dao.registerTransaction(cashMoneyWalletTransaction);
        } catch (CantRegisterCashMoneyWalletTransactionException e) {
            throw new CantRegisterCreditException(CantRegisterCreditException.DEFAULT_MESSAGE, e, "Cant insert transaction record into database", null);
        }
    }

}
