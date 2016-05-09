package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantCreateUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.exceptions.CantGetUnholdTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces.CashUnholdTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.database.UnholdCashMoneyTransactionDao;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantInitializeUnholdCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.unhold.developer.bitdubai.version_1.exceptions.CantUpdateUnholdTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/20/2015.
 */
public class CashMoneyTransactionUnholdManager implements CashUnholdTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;

    private UnholdCashMoneyTransactionDao dao;

    public CashMoneyTransactionUnholdManager(final CashMoneyWalletManager cashMoneyWalletManager, final PluginDatabaseSystem pluginDatabaseSystem,
                                             final UUID pluginId, final ErrorManager errorManager) throws CantStartPluginException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;

        this.dao = new UnholdCashMoneyTransactionDao(pluginDatabaseSystem, pluginId, errorManager);
        try {
            dao.initialize();
        } catch (CantInitializeUnholdCashMoneyTransactionDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_UNHOLD);
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }


    /*
     * Methods to be used by the ProcessorAgent
     */
    public List<CashUnholdTransaction> getAcknowledgedTransactionList() throws CantGetUnholdTransactionException {
        return dao.getAcknowledgedTransactionList();
    }

    public void setTransactionStatusToPending(UUID transactionId) throws CantUpdateUnholdTransactionException {
        dao.updateCashUnholdTransactionStatus(transactionId, CashTransactionStatus.PENDING);
    }

    public void setTransactionStatusToConfirmed(UUID transactionId) throws CantUpdateUnholdTransactionException {
        dao.updateCashUnholdTransactionStatus(transactionId, CashTransactionStatus.CONFIRMED);
    }

    public void setTransactionStatusToRejected(UUID transactionId) throws CantUpdateUnholdTransactionException {
        dao.updateCashUnholdTransactionStatus(transactionId, CashTransactionStatus.REJECTED);
    }


    /*
     * CashUnholdTransactionManager interface implementation
     */
    @Override
    public CashUnholdTransaction createCashUnholdTransaction(CashUnholdTransactionParameters unholdParameters) throws CantCreateUnholdTransactionException {
        return dao.createCashUnholdTransaction(unholdParameters);
    }

    @Override
    public CashTransactionStatus getCashUnholdTransactionStatus(UUID transactionId) throws CantGetUnholdTransactionException {
        return dao.getCashUnholdTransaction(transactionId).getStatus();
    }


    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        try {
            CashUnholdTransaction transaction = dao.getCashUnholdTransaction(transactionId);
            if (transaction.equals(transaction.getTransactionId()))
                return true;
        } catch (Exception e) {}
        return false;
    }

}