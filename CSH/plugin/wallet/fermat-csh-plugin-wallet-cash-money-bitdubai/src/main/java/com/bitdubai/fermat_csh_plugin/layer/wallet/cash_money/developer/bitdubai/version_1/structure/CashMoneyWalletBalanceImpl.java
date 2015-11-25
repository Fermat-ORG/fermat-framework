package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletBalance;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.database.CashMoneyWalletDao;
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
    public double getBalance() throws CantGetBalanceException {
        //TODO: ask dao for balance
        return 0;
    }

    @Override
    public void debit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterDebitException {
        //TODO: dao to make transaction

    }

    @Override
    public void credit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterCreditException {
        //TODO: dao to make transaction

    }

    @Override
    public void hold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterHoldException {
        //TODO: dao to make transaction

    }

    @Override
    public void unhold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterUnholdException {
        //TODO: dao to make transaction

    }
}
