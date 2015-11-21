package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionSummaryBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWallet;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletBalance;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyBalanceRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionSummary;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.database.BankMoneyWalletDao;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.structure.ImplementBankMoney;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Yordin Alayn on 21.09.15.
 */

public class WalletBankMoneyPluginRoot implements  DealsWithErrors,
        DealsWithLogger,
        LogManagerForDevelopers,
        Service,
        Plugin,
        BankMoneyWallet,
        BankMoneyWalletBalance {

    /**
     *
     */
    ErrorManager errorManager;
    /**
     *
     */
    UUID pluginId;

    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    /**
     *
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    /**
     *
     */
    private BankMoneyWalletDao bankMoneyWalletDao;

    /**
     *
     * @return
     */
    ImplementBankMoney implementBankMoney = new ImplementBankMoney();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.WalletBankMoneyPluginRoot");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                if (WalletBankMoneyPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    WalletBankMoneyPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    WalletBankMoneyPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    WalletBankMoneyPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            // this.errorManager.reportUnexpectedPluginException(Plugins., UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }

    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.bankMoneyWalletDao = new BankMoneyWalletDao(pluginDatabaseSystem);
            bankMoneyWalletDao.initializeDatabase(pluginId);
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }

    @Override
    public FermatManager getManager() {
        return null;
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return WalletBankMoneyPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public BankMoneyWalletBalance getBookBalance() throws CantTransactionBankMoneyException {
        return null;
    }

    @Override
    public BankMoneyWalletBalance getAvailableBalance() throws CantTransactionBankMoneyException {
        return null;
    }

    @Override
    public List<BankMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset) throws CantTransactionBankMoneyException {
        return implementBankMoney.getTransactions(balanceType, max, offset);
    }

    @Override
    public BankMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryBankMoneyException {
        return null;
    }

    @Override
    public double getBalance() throws CantCalculateBalanceException {
        return implementBankMoney.getBalance();
    }

    @Override
    public void debit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterDebitException {
            implementBankMoney.debit(BankMoneyBalanceRecord,balanceType);
    }

    @Override
    public void credit(BankMoneyBalanceRecord BankMoneyBalanceRecord, BalanceType balanceType) throws CantRegisterCreditException {
        implementBankMoney.credit(BankMoneyBalanceRecord,balanceType);
    }
}