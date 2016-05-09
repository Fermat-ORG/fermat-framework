package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.database.WithdrawalCashMoneyTransactionDao;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.withdrawal.developer.bitdubai.version_1.exceptions.CantInitializeWithdrawalCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/20/2015.
 */
public class CashMoneyTransactionWithdrawalManager implements CashWithdrawalTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;

    private WithdrawalCashMoneyTransactionDao dao;

    public CashMoneyTransactionWithdrawalManager(final CashMoneyWalletManager cashMoneyWalletManager, final PluginDatabaseSystem pluginDatabaseSystem,
                                                 final UUID pluginId, final ErrorManager errorManager) throws CantStartPluginException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;

        this.dao = new WithdrawalCashMoneyTransactionDao(pluginDatabaseSystem, pluginId, errorManager);
        try {
            dao.initialize();
        } catch (CantInitializeWithdrawalCashMoneyTransactionDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL);
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }




    /*
     * CashWithdrawalTransactionManager interface implementation
     */

    @Override
    public CashWithdrawalTransaction createCashWithdrawalTransaction(CashTransactionParameters withdrawalParameters) throws CantCreateWithdrawalTransactionException, CashMoneyWalletInsufficientFundsException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(withdrawalParameters.getPublicKeyWallet());

        }catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWithdrawalTransactionException(CantCreateWithdrawalTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionWithdrawalManager", "Failed to load Cash Money Wallet");
        }

        try{
            // TODO - Se le esta colocando un random ID pra que sea unico. Por favor revisar esto
            wallet.getAvailableBalance().debit(UUID.randomUUID(), withdrawalParameters.getPublicKeyActor(), withdrawalParameters.getPublicKeyPlugin(), withdrawalParameters.getAmount(), withdrawalParameters.getMemo());
            // TODO - Se le esta colocando un random ID pra que sea unico. Por favor revisar esto
            wallet.getBookBalance().debit(UUID.randomUUID(), withdrawalParameters.getPublicKeyActor(), withdrawalParameters.getPublicKeyPlugin(), withdrawalParameters.getAmount(), withdrawalParameters.getMemo());

        } catch (CantGetCashMoneyWalletBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWithdrawalTransactionException(CantCreateWithdrawalTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionWithdrawalManager", "Failed to load Cash Money Wallet Balance");
        } catch (CantRegisterDebitException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_WITHDRAWAL, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWithdrawalTransactionException(CantCreateWithdrawalTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionWithdrawalManager", "Failed to register debit in Wallet Balance");
        }

        return dao.createCashWithdrawalTransaction(withdrawalParameters);

    }
}