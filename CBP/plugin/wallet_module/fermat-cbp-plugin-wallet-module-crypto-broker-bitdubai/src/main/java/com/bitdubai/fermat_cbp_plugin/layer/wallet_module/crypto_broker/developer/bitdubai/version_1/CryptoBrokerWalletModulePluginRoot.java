package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletAssociatedSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletProviderSettingImpl;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Module for the Crypto Broker Wallet
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/2015
 */
public class CryptoBrokerWalletModulePluginRoot extends AbstractPlugin implements LogManagerForDevelopers, CryptoBrokerWalletModuleManager {

    private CryptoBrokerWalletManager walletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET, plugin = Plugins.CRYPTO_BROKER_WALLET)
    com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @NeededPluginReference(platform = Platforms.BANKING_PLATFORM, layer = Layers.WALLET, plugin = Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET)
    BankMoneyWalletManager bankMoneyWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_SALE)
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.BANK_MONEY_RESTOCK)
    BankMoneyRestockManager bankMoneyRestockManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.STOCK_TRANSACTIONS, plugin = Plugins.CASH_MONEY_RESTOCK)
    CashMoneyRestockManager cashMoneyRestockManager;

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

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.FILTER)
    CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_BROKER)
    CryptoBrokerIdentityManager cryptoBrokerIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_UPDATE)
    CustomerBrokerUpdateManager customerBrokerUpdateManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.CRYPTO_BROKER_ACTOR)
    CryptoBrokerActorManager cryptoBrokerActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ONLINE_PAYMENT)
    CustomerOnlinePaymentManager customerOnlinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_OFFLINE_PAYMENT)
    CustomerOfflinePaymentManager customerOfflinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE)
    CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.CUSTOMER_ACK_OFFLINE_MERCHANDISE)
    CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_ACK_OFFLINE_PAYMENT)
    BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_ACK_ONLINE_PAYMENT)
    BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_SUBMIT_OFFLINE_MERCHANDISE)
    BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.BUSINESS_TRANSACTION, plugin = Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE)
    BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.MATCHING_ENGINE)
    MatchingEngineManager matchingEngineManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION_TRANSACTION, plugin = Plugins.CUSTOMER_BROKER_CLOSE)
    CustomerBrokerCloseManager customerBrokerCloseManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_CONNECTION, plugin = Plugins.CRYPTO_CUSTOMER)
    private CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;

    public CryptoBrokerWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Logging level for this plugin
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private SettingsManager<CryptoBrokerWalletPreferenceSettings> settingsManager;


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
                        customerBrokerUpdateManager,
                        bitcoinWalletManager,
                        cryptoBrokerActorManager,
                        customerOnlinePaymentManager,
                        customerOfflinePaymentManager,
                        customerAckOnlineMerchandiseManager,
                        customerAckOfflineMerchandiseManager,
                        brokerAckOfflinePaymentManager,
                        brokerAckOnlinePaymentManager,
                        brokerSubmitOfflineMerchandiseManager,
                        brokerSubmitOnlineMerchandiseManager,
                        matchingEngineManager,
                        customerBrokerCloseManager,
                        cryptoCustomerActorConnectionManager);

            return walletManager;
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
     *
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
    public SettingsManager<CryptoBrokerWalletPreferenceSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        cryptoBrokerIdentityManager.createCryptoBrokerIdentity(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    @Override
    public void start() throws CantStartPluginException {
        super.start();
        preConfigureWallet();
    }

    private void preConfigureWallet() {
        try {
            final String brokerWalletPublicKey = "crypto_broker_wallet";
            walletManager = getCryptoBrokerWallet(brokerWalletPublicKey);

            if (!walletManager.isWalletConfigured(brokerWalletPublicKey)) {
                // IDENTITY
                createIdentity("Crypto Broker", "", new byte[0]);
                final CryptoBrokerIdentity cryptoBrokerIdentity = walletManager.getListOfIdentities().get(0);
                walletManager.associateIdentity(cryptoBrokerIdentity, brokerWalletPublicKey);

                // MERCHANDISES -> Crypto BTC
                InstalledWallet installedWallet = getInstalledWallet(Platforms.CRYPTO_CURRENCY_PLATFORM);
                assert installedWallet != null;

                CryptoBrokerWalletAssociatedSettingImpl associatedWalletSetting = new CryptoBrokerWalletAssociatedSettingImpl();
                associatedWalletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.CRYPTO);
                associatedWalletSetting.setMerchandise(CryptoCurrency.BITCOIN);
                walletManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // MERCHANDISES -> Cash USD
                installedWallet = getInstalledWallet(Platforms.CASH_PLATFORM);
                assert installedWallet != null;
                if (!walletManager.cashMoneyWalletExists(installedWallet.getWalletPublicKey()))
                    walletManager.createCashMoneyWallet(installedWallet.getWalletPublicKey(), FiatCurrency.US_DOLLAR);

                associatedWalletSetting = new CryptoBrokerWalletAssociatedSettingImpl();
                associatedWalletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.CASH_ON_HAND);
                associatedWalletSetting.setMerchandise(FiatCurrency.US_DOLLAR);
                walletManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // MERCHANDISES -> Bank ARG
                installedWallet = getInstalledWallet(Platforms.BANKING_PLATFORM);
                assert installedWallet != null;
                List<BankAccountNumber> accounts = walletManager.getAccounts(installedWallet.getWalletPublicKey());
                BankAccountNumber bankAccountNumber;
                if (!accounts.isEmpty()) {
                    bankAccountNumber = accounts.get(0);
                } else {
                    bankAccountNumber = walletManager.newEmptyBankAccountNumber("Mercantil", BankAccountType.CHECKING, "Test 1", "987654321", FiatCurrency.VENEZUELAN_BOLIVAR);
                    walletManager.addNewAccount(bankAccountNumber, installedWallet.getWalletPublicKey());

                    bankAccountNumber = walletManager.newEmptyBankAccountNumber("Mercantil", BankAccountType.CHECKING, "Pre-configured Bank Wallet", "123456789", FiatCurrency.ARGENTINE_PESO);
                    walletManager.addNewAccount(bankAccountNumber, installedWallet.getWalletPublicKey());
                }

                associatedWalletSetting = new CryptoBrokerWalletAssociatedSettingImpl();
                associatedWalletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.BANK);
                associatedWalletSetting.setMerchandise(bankAccountNumber.getCurrencyType());
                associatedWalletSetting.setBankAccount(bankAccountNumber.getAccount());
                walletManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // EARNINGS -> BTC/USD - Earning Wallet: Cash USD
                String earningWalletPublicKey = "cash_wallet";
                walletManager.addEarningsPairToEarningSettings(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR, earningWalletPublicKey, brokerWalletPublicKey);

                // EARNINGS -> BTC/ARG - Earning Wallet: Bank ARG
                earningWalletPublicKey = "banking_wallet";
                walletManager.addEarningsPairToEarningSettings(CryptoCurrency.BITCOIN, FiatCurrency.ARGENTINE_PESO, earningWalletPublicKey, brokerWalletPublicKey);

                // EARNINGS -> ARG/USD - Earning Wallet: Cash USD
                earningWalletPublicKey = "cash_wallet";
                walletManager.addEarningsPairToEarningSettings(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, earningWalletPublicKey, brokerWalletPublicKey);

                // PROVIDERS -> BTC/USD
                final List<CurrencyExchangeRateProviderManager> providers = new ArrayList<>();
                providers.addAll(walletManager.getProviderReferencesFromCurrencyPair(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR));
                CurrencyExchangeRateProviderManager provider = providers.get(0);

                CryptoBrokerWalletProviderSettingImpl providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(CryptoCurrency.BITCOIN.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.US_DOLLAR.getCode());
                walletManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // PROVIDERS -> BTC/ARG
                providers.clear();
                providers.addAll(walletManager.getProviderReferencesFromCurrencyPair(CryptoCurrency.BITCOIN, FiatCurrency.ARGENTINE_PESO));
                provider = providers.get(0);

                providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(CryptoCurrency.BITCOIN.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.ARGENTINE_PESO.getCode());
                walletManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // PROVIDERS -> USD/ARG
                providers.clear();
                providers.addAll(walletManager.getProviderReferencesFromCurrencyPair(FiatCurrency.US_DOLLAR, FiatCurrency.ARGENTINE_PESO));
                provider = providers.get(0);

                providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(FiatCurrency.US_DOLLAR.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.ARGENTINE_PESO.getCode());
                walletManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // OTHER SETTINGS -> Spread and Automatic Restock
                final CryptoBrokerWalletSettingSpread walletSetting = walletManager.newEmptyCryptoBrokerWalletSetting();
                walletSetting.setId(null);
                walletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                walletSetting.setSpread(20);
                walletSetting.setRestockAutomatic(true);
                walletManager.saveWalletSetting(walletSetting, brokerWalletPublicKey);

                // Locacions
                walletManager.createNewLocation("C.C. Sambil Chacao, Edo. Miranda, Venezuela", "");
                walletManager.createNewLocation("C.C. Metrocenter, Caracas, Venezuela", "");


            }

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private InstalledWallet getInstalledWallet(Platforms platform) throws CantListWalletsException {
        final List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
        for (InstalledWallet wallet : installedWallets) {
            if (wallet.getPlatform().equals(platform))
                return wallet;
        }
        return null;
    }
}