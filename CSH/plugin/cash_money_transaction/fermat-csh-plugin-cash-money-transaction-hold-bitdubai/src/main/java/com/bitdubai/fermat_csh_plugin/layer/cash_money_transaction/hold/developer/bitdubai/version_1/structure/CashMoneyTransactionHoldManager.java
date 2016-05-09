package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces.CashHoldTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database.HoldCashMoneyTransactionDao;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantInitializeHoldCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.exceptions.CantUpdateHoldTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/20/2015.
 */
public class CashMoneyTransactionHoldManager implements CashHoldTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;

    private HoldCashMoneyTransactionDao dao;

    public CashMoneyTransactionHoldManager(final CashMoneyWalletManager cashMoneyWalletManager, final PluginDatabaseSystem pluginDatabaseSystem,
                                           final UUID pluginId, final ErrorManager errorManager) throws CantStartPluginException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;

        this.dao = new HoldCashMoneyTransactionDao(pluginDatabaseSystem, pluginId, errorManager);
        try {
            dao.initialize();
        } catch (CantInitializeHoldCashMoneyTransactionDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD);
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }



    /*
     * Methods to be used by the ProcessorAgent
     */
    public List<CashHoldTransaction> getAcknowledgedTransactionList() throws CantGetHoldTransactionException {
        return dao.getAcknowledgedTransactionList();
    }
    public void setTransactionStatusToPending(UUID transactionId) throws CantUpdateHoldTransactionException {
        dao.updateCashHoldTransactionStatus(transactionId, CashTransactionStatus.PENDING);
    }
    public void setTransactionStatusToConfirmed(UUID transactionId) throws CantUpdateHoldTransactionException {
        dao.updateCashHoldTransactionStatus(transactionId, CashTransactionStatus.CONFIRMED);
    }
    public void setTransactionStatusToRejected(UUID transactionId) throws CantUpdateHoldTransactionException {
        dao.updateCashHoldTransactionStatus(transactionId, CashTransactionStatus.REJECTED);
    }



    /*
     * CashHoldTransactionManager interface implementation
     */
    @Override
    public CashHoldTransaction createCashHoldTransaction(CashHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException {
        return dao.createCashHoldTransaction(holdParameters);
    }

    @Override
    public CashTransactionStatus getCashHoldTransactionStatus(UUID transactionId) throws CantGetHoldTransactionException {
        return dao.getCashHoldTransaction(transactionId).getStatus();
    }

    @Override
    public boolean isTransactionRegistered(UUID transactionId) {
        try{
            CashHoldTransaction transaction = dao.getCashHoldTransaction(transactionId);
            if(transaction.equals(transaction.getTransactionId()))
                return true;
        }catch (Exception e){}
        return false;
    }

}