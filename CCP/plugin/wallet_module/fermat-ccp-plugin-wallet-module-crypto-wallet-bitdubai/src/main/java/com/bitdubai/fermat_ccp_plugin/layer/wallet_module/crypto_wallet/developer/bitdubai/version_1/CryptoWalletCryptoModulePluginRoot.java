package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.DealsWithCCPActorIntraWalletUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.DealsWithCryptoAddressesNetworkService;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;

import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.DealsWithCryptoPayment;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.DealsWithOutgoingIntraActor;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by loui on 27/05/15.a
 */
public class CryptoWalletCryptoModulePluginRoot extends AbstractPlugin implements
        CryptoWalletManager,
        DealsWithBitcoinWallet,//
        DealsWithCryptoAddressBook,
        DealsWithCryptoAddressesNetworkService,//
        DealsWithCryptoPayment,//
        DealsWithCryptoTransmissionNetworkService,//
        DealsWithCCPActorIntraWalletUsers,//
        DealsWithCryptoVault,
        DealsWithErrors,
        DealsWithExtraUsers,//
        DealsWithLogger,
        DealsWithOutgoingExtraUser,//
        DealsWithOutgoingIntraActor,//
        DealsWithWalletContacts,//
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET   , plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS        , layer = Layers.CRYPTO_VAULT   , plugin = Plugins.BITCOIN_VAULT)
    private CryptoVaultManager cryptoVaultManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR   , plugin = Plugins.EXTRA_WALLET_USER)
    private ExtraUserManager extraUserManager;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.REQUEST  , plugin = Plugins.CRYPTO_PAYMENT_REQUEST)
    private CryptoPaymentManager cryptoPaymentManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION   , plugin = Plugins.OUTGOING_INTRA_ACTOR)
    private OutgoingIntraActorManager outgoingIntraActorManager;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE   , plugin = Plugins.CRYPTO_ADDRESSES)
    private CryptoAddressesManager cryptoAddressesNSManager;


    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY   , plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserActorManager intraWalletUserManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.TRANSACTION   , plugin = Plugins.OUTGOING_EXTRA_USER)
    private OutgoingExtraUserManager outgoingExtraUserManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.CRYPTO_MODULE   , plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE   , plugin = Plugins.WALLET_CONTACTS)
    private WalletContactsManager walletContactsManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.NETWORK_SERVICE   , plugin = Plugins.CRYPTO_TRANSMISSION)
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;


    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    public CryptoWalletCryptoModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public CryptoWallet getCryptoWallet() throws CantGetCryptoWalletException {
        try {

            logManager.log(CryptoWalletCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoWallet instantiation started...", null, null);

            CryptoWalletWalletModuleManager walletModuleCryptoWallet = new CryptoWalletWalletModuleManager();

            walletModuleCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
            walletModuleCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
            walletModuleCryptoWallet.setExtraUserManager(extraUserManager);
            walletModuleCryptoWallet.setErrorManager(errorManager);
            walletModuleCryptoWallet.setOutgoingExtraUserManager(outgoingExtraUserManager);
            walletModuleCryptoWallet.setCryptoAddressBookManager(cryptoAddressBookManager);
            walletModuleCryptoWallet.setWalletContactsManager(walletContactsManager);
            walletModuleCryptoWallet.setIntraWalletUserIdentityManager(intraWalletUserManager);
            walletModuleCryptoWallet.setCryptoTransmissionNetworkService(cryptoTransmissionNetworkServiceManager);
//            walletModuleCryptoWallet.setActorIntraUserManager(this.intraUserManager);
            walletModuleCryptoWallet.setCryptoPaymentManager(this.cryptoPaymentManager);
            walletModuleCryptoWallet.setOutgoingIntraActorManager(this.outgoingIntraActorManager);
            walletModuleCryptoWallet.setCryptoAddressesManager(this.cryptoAddressesNSManager);
            walletModuleCryptoWallet.initialize();

            logManager.log(CryptoWalletCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoWallet instantiation finished successfully.", null, null);

            return walletModuleCryptoWallet;
        } catch (Exception e) {
            throw new CantGetCryptoWalletException(CantGetCryptoWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("CryptoWalletCryptoModulePluginRoot");
        returnedClasses.add("CryptoWalletWalletModuleManager");

        /**
         * I return the values.
         */
        return returnedClasses;
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
            if (CryptoWalletCryptoModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoWalletCryptoModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoWalletCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoWalletCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return CryptoWalletCryptoModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager) {
        this.extraUserManager = extraUserManager;
    }

    @Override
    public void setOutgoingExtraUserManager(OutgoingExtraUserManager outgoingExtraUserManager) {
        this.outgoingExtraUserManager = outgoingExtraUserManager;
    }

    @Override
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }

    @Override
    public void setCryptoTransmissionNetworkService(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }

    @Override
    public void setCryptoPaymentManager(CryptoPaymentManager cryptoPaymentManager) {
        this.cryptoPaymentManager = cryptoPaymentManager;
    }

    @Override
    public void setOutgoingIntraActorManager(OutgoingIntraActorManager outgoingIntraActorManager) {
        this.outgoingIntraActorManager = outgoingIntraActorManager;
    }

    @Override
    public void setCryptoAddressesManager(CryptoAddressesManager cryptoAddressesNetworkServiceManager) {
        this.cryptoAddressesNSManager = cryptoAddressesNetworkServiceManager;
    }

    @Override
    public void setIntraWalletUserIdentityManager(IntraWalletUserActorManager intraWalletUserManager) {
        this.intraWalletUserManager = intraWalletUserManager;
    }

}
