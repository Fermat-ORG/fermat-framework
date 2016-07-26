package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CurrencyPairAndProvider;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletAssociatedSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletProviderSettingImpl;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Module for the Crypto Broker Wallet
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 05/11/2015
 */
@PluginInfo(createdBy = "nelsonalfo", maintainerMail = "nelsonalfo@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.CRYPTO_BROKER)
public class CryptoBrokerWalletModulePluginRoot extends AbstractModule<CryptoBrokerWalletPreferenceSettings, ActiveActorIdentityInformation> implements LogManagerForDevelopers {

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
    CryptoWalletManager cryptoWalletManager;

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
    CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.INTRA_WALLET_USER)
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    /**
     * Logging level for this plugin
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    private CryptoBrokerWalletModuleManager moduleManager;


    public CryptoBrokerWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("CryptoBrokerWalletModulePluginRoot");

        return returnedClasses;
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

    @Override
    public void start() throws CantStartPluginException {
        super.start();
    }

    @Override
    public CryptoBrokerWalletModuleManager getModuleManager() throws CantGetModuleManagerException {
        if (moduleManager == null)
            moduleManager = new CryptoBrokerWalletModuleCryptoBrokerWalletManager(walletManagerManager,
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
                    cryptoWalletManager,
                    cryptoBrokerActorManager,
                    customerOnlinePaymentManager,
                    customerOfflinePaymentManager,
                    customerAckOnlineMerchandiseManager,
                    customerAckOfflineMerchandiseManager,
                    brokerAckOfflinePaymentManager,
                    brokerAckOnlinePaymentManager,
                    brokerSubmitOfflineMerchandiseManager,
                    brokerSubmitOnlineMerchandiseManager,
                    intraWalletUserIdentityManager,
                    matchingEngineManager,
                    customerBrokerCloseManager,
                    cryptoCustomerActorConnectionManager,
                    pluginFileSystem,
                    pluginId
            );

        return moduleManager;
    }

    @SuppressWarnings("unused")
    private void preConfigureWallet() {
        try {
            final String brokerWalletPublicKey = "crypto_broker_wallet";
            moduleManager = getModuleManager();

            if (!moduleManager.isWalletConfigured(brokerWalletPublicKey)) {
                // IDENTITY
                moduleManager.createIdentity("Crypto Broker", "", new byte[0]);
                final CryptoBrokerIdentity cryptoBrokerIdentity = moduleManager.getListOfIdentities().get(0);
                moduleManager.associateIdentity(cryptoBrokerIdentity, brokerWalletPublicKey);

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
                moduleManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // MERCHANDISES -> Cash USD
                installedWallet = getInstalledWallet(Platforms.CASH_PLATFORM);
                assert installedWallet != null;
                if (!moduleManager.cashMoneyWalletExists(installedWallet.getWalletPublicKey()))
                    moduleManager.createCashMoneyWallet(installedWallet.getWalletPublicKey(), FiatCurrency.US_DOLLAR);

                associatedWalletSetting = new CryptoBrokerWalletAssociatedSettingImpl();
                associatedWalletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.CASH_ON_HAND);
                associatedWalletSetting.setMerchandise(FiatCurrency.US_DOLLAR);
                moduleManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // MERCHANDISES -> Bank ARG
                installedWallet = getInstalledWallet(Platforms.BANKING_PLATFORM);
                assert installedWallet != null;
                List<BankAccountNumber> accounts = moduleManager.getAccounts(installedWallet.getWalletPublicKey());
                BankAccountNumber bankAccountNumber;
                if (!accounts.isEmpty()) {
                    bankAccountNumber = accounts.get(0);
                } else {
                    bankAccountNumber = moduleManager.newEmptyBankAccountNumber("Mercantil", BankAccountType.CHECKING, "Test 1", "987654321", FiatCurrency.VENEZUELAN_BOLIVAR);
                    moduleManager.addNewAccount(bankAccountNumber, installedWallet.getWalletPublicKey());

                    bankAccountNumber = moduleManager.newEmptyBankAccountNumber("Mercantil", BankAccountType.CHECKING, "Pre-configured Bank Wallet", "123456789", FiatCurrency.ARGENTINE_PESO);
                    moduleManager.addNewAccount(bankAccountNumber, installedWallet.getWalletPublicKey());
                }

                associatedWalletSetting = new CryptoBrokerWalletAssociatedSettingImpl();
                associatedWalletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                associatedWalletSetting.setId(UUID.randomUUID());
                associatedWalletSetting.setWalletPublicKey(installedWallet.getWalletPublicKey());
                associatedWalletSetting.setPlatform(installedWallet.getPlatform());
                associatedWalletSetting.setMoneyType(MoneyType.BANK);
                associatedWalletSetting.setMerchandise(bankAccountNumber.getCurrencyType());
                associatedWalletSetting.setBankAccount(bankAccountNumber.getAccount());
                moduleManager.saveWalletSettingAssociated(associatedWalletSetting, brokerWalletPublicKey);

                // EARNINGS -> BTC/USD - Earning Wallet: Cash USD
                moduleManager.addEarningsPairToEarningSettings(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR,
                        WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode(), brokerWalletPublicKey);

                // EARNINGS -> BTC/ARG - Earning Wallet: Bank ARG
                moduleManager.addEarningsPairToEarningSettings(FiatCurrency.ARGENTINE_PESO, CryptoCurrency.BITCOIN,
                        WalletsPublicKeys.BNK_BANKING_WALLET.getCode(), brokerWalletPublicKey);

                // EARNINGS -> ARG/USD - Earning Wallet: Cash USD
                moduleManager.addEarningsPairToEarningSettings(FiatCurrency.US_DOLLAR, FiatCurrency.ARGENTINE_PESO,
                        WalletsPublicKeys.CSH_MONEY_WALLET.getCode(), brokerWalletPublicKey);

                // PROVIDERS -> BTC/USD
                final List<CurrencyPairAndProvider> providers = new ArrayList<>();
                providers.addAll(moduleManager.getProviderReferencesFromCurrencyPair(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR));
                CurrencyPairAndProvider provider = providers.get(0);

                CryptoBrokerWalletProviderSettingImpl providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(CryptoCurrency.BITCOIN.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.US_DOLLAR.getCode());
                moduleManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // PROVIDERS -> BTC/ARG
                providers.clear();
                providers.addAll(moduleManager.getProviderReferencesFromCurrencyPair(CryptoCurrency.BITCOIN, FiatCurrency.ARGENTINE_PESO));
                provider = providers.get(0);

                providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(CryptoCurrency.BITCOIN.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.ARGENTINE_PESO.getCode());
                moduleManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // PROVIDERS -> USD/ARG
                providers.clear();
                providers.addAll(moduleManager.getProviderReferencesFromCurrencyPair(FiatCurrency.US_DOLLAR, FiatCurrency.ARGENTINE_PESO));
                provider = providers.get(0);

                providerSetting = new CryptoBrokerWalletProviderSettingImpl();
                providerSetting.setBrokerPublicKey(brokerWalletPublicKey);
                providerSetting.setDescription(provider.getProviderName());
                providerSetting.setId(provider.getProviderId());
                providerSetting.setPlugin(provider.getProviderId());
                providerSetting.setCurrencyFrom(FiatCurrency.US_DOLLAR.getCode());
                providerSetting.setCurrencyTo(FiatCurrency.ARGENTINE_PESO.getCode());
                moduleManager.saveCryptoBrokerWalletProviderSetting(providerSetting, brokerWalletPublicKey);

                // OTHER SETTINGS -> Spread and Automatic Restock
                final CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
                walletSetting.setId(null);
                walletSetting.setBrokerPublicKey(brokerWalletPublicKey);
                walletSetting.setSpread(20);
                walletSetting.setRestockAutomatic(true);
                moduleManager.saveWalletSetting(walletSetting, brokerWalletPublicKey);

                // Locacions
                moduleManager.createNewLocation("C.C. Sambil Chacao, Edo. Miranda, Venezuela", "");
                moduleManager.createNewLocation("C.C. Metrocenter, Caracas, Venezuela", "");


            }

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    private InstalledWallet getInstalledWallet(Platforms platform) throws CantListWalletsException {
        final List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
        for (InstalledWallet wallet : installedWallets) {
            if (wallet.getPlatform().equals(platform))
                return wallet;
        }
        return null;
    }

    /*private void universalTimeTest(){
        try{
            Date date=UniversalTime.getLocatedUniversalTime();
            System.out.println("UNIVERSAL TIME - local: "+date);
            date=UniversalTime.getUTC();
            System.out.println("UNIVERSAL TIME - UTC: "+date);
            String stringDate = UniversalTime.getUTCDateStringFromExternalURL();
            System.out.println("UNIVERSAL TIME - String: "+stringDate);
            date = UniversalTime.getLocalDateFromUTCDateString(stringDate);
            System.out.println("UNIVERSAL TIME - Local String parse: "+date);
            date = UniversalTime.getUTCDateFromUTCDateString(stringDate);
            System.out.println("UNIVERSAL TIME - UTC String parse: "+date);
        } catch (Exception e){
            System.out.println("UNIVERSAL TIME: "+e);
        }
    }*/
}