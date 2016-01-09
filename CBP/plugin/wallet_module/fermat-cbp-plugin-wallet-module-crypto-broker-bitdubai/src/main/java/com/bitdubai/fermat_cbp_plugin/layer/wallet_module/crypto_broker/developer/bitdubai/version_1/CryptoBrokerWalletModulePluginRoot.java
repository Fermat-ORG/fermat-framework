package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerWalletManager;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Module for the Crypto Broker Wallet
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/2015
 */
public class CryptoBrokerWalletModulePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        CryptoBrokerWalletModuleManager {

    private CryptoBrokerWalletManager walletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWalletManager bankMoneyWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.BANK_MONEY_RESTOCK)
    BankMoneyRestockManager bankMoneyRestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_RESTOCK)
    CashMoneyRestockManager  cashMoneyRestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CRYPTO_MONEY_RESTOCK)
    CryptoMoneyRestockManager cryptoMoneyRestockManager;

    @NeededPluginReference(platform = Platforms.CASH_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_CSH_WALLET_CASH_MONEY)
    CashMoneyWalletManager cashMoneyWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.BANK_MONEY_DESTOCK)
    BankMoneyDestockManager bankMoneyDestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_DESTOCK)
    CashMoneyDestockManager cashMoneyDestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CRYPTO_MONEY_DESTOCK)
    CryptoMoneyDestockManager cryptoMoneyDestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.CONTRACT, plugin = Plugins.CONTRACT_SALE)
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.BITDUBAI_CER_PROVIDER_FILTER)
    CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_BROKER)
    CryptoBrokerIdentityManager cryptoBrokerIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_UPDATE)
    CustomerBrokerUpdateManager customerBrokerUpdateManager;

    public CryptoBrokerWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Logging level for this plugin
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();


    @Override
    public CryptoBrokerWalletManager getCryptoBrokerWallet(String walletPublicKey) throws CantGetCryptoBrokerWalletException {
        try {
            if (walletManager == null)
                walletManager = new CryptoBrokerWalletModuleCryptoBrokerWalletManager(walletManagerManager,
                        cryptoBrokerWalletManager,
                        bankMoneyWalletManager,
                        customerBrokerSaleNegotiationManager,
                        bankMoneyRestockManager,
                        cashMoneyRestockManager,
                        cryptoMoneyRestockManager,
                        cashMoneyWalletManager,
                        bankMoneyDestockManager,
                        cashMoneyDestockManager,
                        cryptoMoneyDestockManager,
                        customerBrokerContractSaleManager,
                        currencyExchangeProviderFilterManager,
                        cryptoBrokerIdentityManager,
                        customerBrokerUpdateManager);

            return walletManager;
        } catch (Exception e) {
            throw new CantGetCryptoBrokerWalletException(FermatException.wrapException(e));
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        // I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            // if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (CryptoBrokerWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoBrokerWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoBrokerWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoBrokerWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className the class name
     * @return the log level for this class
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return CryptoBrokerWalletModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public SettingsManager<FermatSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}