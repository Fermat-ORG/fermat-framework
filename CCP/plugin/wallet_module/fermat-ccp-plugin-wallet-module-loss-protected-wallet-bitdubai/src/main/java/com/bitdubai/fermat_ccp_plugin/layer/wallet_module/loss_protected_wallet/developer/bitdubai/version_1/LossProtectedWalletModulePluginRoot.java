package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededIndirectPluginReferences;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.interfaces.TransferIntraWalletUsersManager;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure.LossProtectedWalletModuleManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created Natalia Cortez on 07/03/2016.
 */
@NeededIndirectPluginReferences(indirectReferences = {
        @NeededPluginReference(platform = Platforms.BLOCKCHAINS             , layer = Layers.MIDDLEWARE , plugin = Plugins.CRYPTO_ADDRESSES   ),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_EXTRA_USER),
        @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION, plugin = Plugins.INCOMING_INTRA_USER)

})

@PluginInfo(createdBy = "Natalia Cortez", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class LossProtectedWalletModulePluginRoot  extends AbstractModule<LossProtectedWalletSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers{

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET    , plugin = Plugins.LOSS_PROTECTED_WALLET)
    private BitcoinLossProtectedWalletManager bitcoinWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS             , layer = Layers.CRYPTO_VAULT    , plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API     , layer = Layers.SYSTEM          , addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR           , plugin = Plugins.EXTRA_WALLET_USER)
    private ExtraUserManager extraUserManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.REQUEST         , plugin = Plugins.CRYPTO_PAYMENT_REQUEST)
    private CryptoPaymentManager cryptoPaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION     , plugin = Plugins.OUTGOING_INTRA_ACTOR)
    private OutgoingIntraActorManager outgoingIntraActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE , plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesNSManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR           , plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserActorManager intraWalletUserActorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY        , plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION     , plugin = Plugins.OUTGOING_EXTRA_USER)
    private OutgoingExtraUserManager outgoingExtraUserManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS             , layer = Layers.CRYPTO_MODULE   , plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE      , plugin = Plugins.WALLET_CONTACTS)
    private WalletContactsManager walletContactsManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE , plugin = Plugins.CRYPTO_TRANSMISSION)
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CURRENCY_EXCHANGE_RATE_PLATFORM, layer = Layers.SEARCH, plugin = Plugins.FILTER)
    private CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    WalletManagerManager walletManagerManager;

   @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION    , plugin = Plugins.TRANSFER_INTRA_WALLET)
    private TransferIntraWalletUsersManager transferIntraWalletUsersManager;


    private String appPublicKey;


    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public LossProtectedWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

   /* @Override
    public LossProtectedWallet getCryptoWallet() throws CantGetCryptoLossProtectedWalletException {

        try {

            logManager.log(LossProtectedWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "LossProtectedWallet instantiation started...", null, null);

            LossProtectedWalletModuleManager walletModuleLossProtectedWallet = new LossProtectedWalletModuleManager(
                    bitcoinWalletManager          ,
                    cryptoAddressBookManager      ,
                    cryptoAddressesNSManager      ,
                    cryptoPaymentManager          ,
                    cryptoVaultManager            ,
                    getErrorManager()                  ,
                    extraUserManager              ,
                    intraWalletUserActorManager   ,
                    intraWalletUserIdentityManager,
                    outgoingExtraUserManager      ,
                    outgoingIntraActorManager     ,
                    walletContactsManager,
                    exchangeProviderFilterManagerproviderFilter,
                    walletManagerManager,
                    transferIntraWalletUsersManager
            );

            walletModuleLossProtectedWallet.initialize();

            logManager.log(LossProtectedWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoWallet instantiation finished successfully.", null, null);

            return walletModuleLossProtectedWallet;
        } catch (final Exception e) {

            throw new CantGetCryptoLossProtectedWalletException(CantGetCryptoLossProtectedWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("LossProtectedWalletModulePluginRoot");
        returnedClasses.add("LossProtectedWalletModuleManager");

        /**
         * I return the values.
         */
     //   return returnedClasses;
   // }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (LossProtectedWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                LossProtectedWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                LossProtectedWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                LossProtectedWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return LossProtectedWalletModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    private SettingsManager<LossProtectedWalletSettings> settingsManager;

    LossProtectedWalletModuleManager walletModuleLossProtectedWallet = null;
    @Override
    public ModuleManager<LossProtectedWalletSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        try {

            logManager.log(LossProtectedWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "LossProtectedWallet instantiation started...", null, null);

            if(walletModuleLossProtectedWallet == null) {
                walletModuleLossProtectedWallet = new LossProtectedWalletModuleManager(
                        bitcoinWalletManager          ,
                        cryptoAddressBookManager      ,
                        cryptoAddressesNSManager      ,
                        cryptoPaymentManager          ,
                        cryptoVaultManager            ,
                        getErrorManager()                  ,
                        extraUserManager              ,
                        intraWalletUserActorManager   ,
                        intraWalletUserIdentityManager,
                        outgoingExtraUserManager      ,
                        outgoingIntraActorManager     ,
                        walletContactsManager,
                        exchangeProviderFilterManagerproviderFilter,
                        walletManagerManager,
                        transferIntraWalletUsersManager,
                        pluginId,
                        pluginFileSystem
                );
            }


            walletModuleLossProtectedWallet.initialize();

            logManager.log(LossProtectedWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoWallet instantiation finished successfully.", null, null);

            return walletModuleLossProtectedWallet;
        } catch (final Exception e) {

            throw new CantGetModuleManagerException(CantGetCryptoWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }

   /* @Override
    public SettingsManager<LossProtectedWalletSettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }




    */

}
