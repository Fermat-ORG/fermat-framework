package com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_csh_plugin.layer.wallet.cash_money.developer.bitdubai.version_1.structure.CashMoneyWalletManagerImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/17/2015
 */

public class WalletCashMoneyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {
    //CashMoneyWallet, CashMoneyWalletBalance, CashMoneyWalletManager {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;


    CashMoneyWalletManagerImpl cashMoneyWalletManagerImpl;


    /*
     * PluginRoot Constructor
     */
    public WalletCashMoneyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public void start() throws CantStartPluginException {
        try {
            cashMoneyWalletManagerImpl = new CashMoneyWalletManagerImpl(pluginDatabaseSystem, pluginId, errorManager);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }

        serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return null;
    }






/*
    TODO: Legacy code, review and decide fate.


    /*
    @Override
    public void start() throws CantStartPluginException {
        try {
            this.cashMoneyWalletDao = new CashMoneyWalletDao(pluginDatabaseSystem);
            cashMoneyWalletDao.initializeDatabase(pluginId);
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }


    @Override
    public double getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        return implementCashMoney.getBookBalance(balanceType);
    }

    @Override
    public double getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException {
        return implementCashMoney.getAvailableBalance(balanceType);
    }

    @Override
    public List<CashMoneyWalletTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionCashMoneyException {
        return implementCashMoney.getTransactions(balanceType,max,offset);
    }

    @Override
    public CashMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryCashMoneyException {
        return null;
    }

    @Override
    public double getBalance() throws CantGetBalanceException {
        return implementCashMoney.getBalance();
    }

    @Override
    public void debit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
        implementCashMoney.debit(cashMoneyBalanceRecord,balanceType);
    }

    @Override
    public void credit(CashMoneyBalanceRecord cashMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {
        implementCashMoney.credit(cashMoneyBalanceRecord,balanceType);
    }

    @Override
    public List<CashMoneyWallet> getTransactionsCashMoney() throws CantTransactionCashMoneyException {
        try {
            return cashMoneyWalletDao.getTransactionsCashMoney();
        } catch (CantCreateCashMoneyException e) {
            throw new CantTransactionCashMoneyException(
                    CantTransactionCashMoneyException.DEFAULT_MESSAGE,
                    e,
                    "Cant Transaction CashMoneyManagerImp Exception",
                    "Cant Transaction CashMoneyManagerImp Exception"
            );
        }
    }

    @Override
    public CashMoneyWallet registerCashMoney(
            String cashTransactionId,
            String publicKeyActorFrom,
            String publicKeyActorTo,
            String status,
            String balanceType,
            String transactionType,
            double amount,
            String cashCurrencyType,
            String cashReference,
            long runningBookBalance,
            long runningAvailableBalance,
            long timestamp,
            String memo) throws CantCreateCashMoneyException {



        return null;
    }

    @Override
    public CashMoneyWallet loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException {

        return null;
    }

    @Override
    public void createCashMoney(String walletPublicKey) throws CantCreateCashMoneyException {

    }

    */
}