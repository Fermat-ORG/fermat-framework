package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWallet;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.database.DepositCashMoneyTransactionDao;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.version_1.exceptions.CantInitializeDepositCashMoneyTransactionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/20/2015.
 */
public class CashMoneyTransactionDepositManager implements CashDepositTransactionManager {
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final ErrorManager errorManager;
    private final CashMoneyWalletManager cashMoneyWalletManager;

    private DepositCashMoneyTransactionDao dao;

    public CashMoneyTransactionDepositManager(final CashMoneyWalletManager cashMoneyWalletManager, final PluginDatabaseSystem pluginDatabaseSystem,
                                              final UUID pluginId, final ErrorManager errorManager) throws CantStartPluginException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.cashMoneyWalletManager = cashMoneyWalletManager;

        this.dao = new DepositCashMoneyTransactionDao(pluginDatabaseSystem, pluginId, errorManager);
        try {
            dao.initialize();
        } catch (CantInitializeDepositCashMoneyTransactionDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT);
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }




    /*
     * CashDepositTransactionManager interface implementation
     */

    @Override
    public CashDepositTransaction createCashDepositTransaction(CashTransactionParameters depositParameters) throws CantCreateDepositTransactionException {
        CashMoneyWallet wallet;
        try{
            wallet = cashMoneyWalletManager.loadCashMoneyWallet(depositParameters.getPublicKeyWallet());

        }catch (CantLoadCashMoneyWalletException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateDepositTransactionException(CantCreateDepositTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionDepositManager", "Failed to load Cash Money Wallet");
        }

        try{
            // TODO - Revisar el parametro transactionId en este metodo:
            // wallet.getAvailableBalance().credit(depositParameters.getTransactionId(), depositParameters.getPublicKeyActor(), depositParameters.getPublicKeyPlugin(), depositParameters.getAmount(), depositParameters.getMemo());
            // TODO - Revisar el parametro transactionId en este metodo porque esta dado excepcion debido a que es el mismo que el de la instruccion de mas arriba:
            // wallet.getBookBalance().credit(depositParameters.getTransactionId(), depositParameters.getPublicKeyActor(), depositParameters.getPublicKeyPlugin(), depositParameters.getAmount(), depositParameters.getMemo());

            // TODO - Se le esta colocando un random ID pra que sea unico. Por favor revisar esto
            wallet.getAvailableBalance().credit(UUID.randomUUID(), depositParameters.getPublicKeyActor(), depositParameters.getPublicKeyPlugin(), depositParameters.getAmount(), depositParameters.getMemo());
            // TODO - Se le esta colocando un random ID pra que sea unico. Por favor revisar esto
            wallet.getBookBalance().credit(UUID.randomUUID(), depositParameters.getPublicKeyActor(), depositParameters.getPublicKeyPlugin(), depositParameters.getAmount(), depositParameters.getMemo());

        } catch (CantGetCashMoneyWalletBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateDepositTransactionException(CantCreateDepositTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionDepositManager", "Failed to load Cash Money Wallet Balance");
        } catch (CantRegisterCreditException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateDepositTransactionException(CantCreateDepositTransactionException.DEFAULT_MESSAGE, e, "CashMoneyTransactionDepositManager", "Failed to register credit in Wallet Balance");
        }

        return dao.createCashDepositTransaction(depositParameters);

    }
}