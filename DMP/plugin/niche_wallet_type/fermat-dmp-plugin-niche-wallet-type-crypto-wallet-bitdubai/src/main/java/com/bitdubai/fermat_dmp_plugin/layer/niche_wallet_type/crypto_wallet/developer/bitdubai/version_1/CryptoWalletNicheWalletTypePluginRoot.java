package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.NicheWalletType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 27/05/15.a
 */
public class CryptoWalletNicheWalletTypePluginRoot implements CryptoWalletManager, DealsWithActorAddressBook, DealsWithBitcoinWallet, DealsWithCryptoVault, DealsWithLogger, LogManagerForDevelopers, DealsWithErrors, DealsWithExtraUsers, DealsWithOutgoingExtraUser, DealsWithWalletContacts, DealsWithWalletAddressBook, NicheWalletType, Plugin, Service {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithActorAddressBook Interface member variables.
     */
    ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithBitcoinWallet Interface member variables.
     */
    BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault Interface member variables.
     */
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithExtraUsers Interface member variables.
     */
    ExtraUserManager extraUserManager;

    /**
     * DealsWithOutgoingExtraUser Interface member variables.
     */
    OutgoingExtraUserManager outgoingExtraUserManager;

    /**
     * DealsWithWalletAddressBook Interface member variables.
     */
    WalletAddressBookManager walletAddressBookManager;

    /**
     * DealsWithWalletContacts Interface member variables.
     */
    WalletContactsManager walletContactsManager;

    /**
     * Plugin Interface member variables.
     */
    //UUID pluginId;



    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {
        this.serviceStatus = ServiceStatus.STARTED;
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
        return this.serviceStatus;
    }


    @Override
    public CryptoWallet getCryptoWallet() throws CantGetCryptoWalletException {
        NicheWalletTypeCryptoWallet nicheWalletTypeCryptoWallet = new NicheWalletTypeCryptoWallet();
        nicheWalletTypeCryptoWallet.setActorAddressBookManager(actorAddressBookManager);
        nicheWalletTypeCryptoWallet.setBitcoinWalletManager(bitcoinWalletManager);
        nicheWalletTypeCryptoWallet.setCryptoVaultManager(cryptoVaultManager);
        nicheWalletTypeCryptoWallet.setExtraUserManager(extraUserManager);
        nicheWalletTypeCryptoWallet.setErrorManager(errorManager);
        nicheWalletTypeCryptoWallet.setOutgoingExtraUserManager(outgoingExtraUserManager);
        nicheWalletTypeCryptoWallet.setWalletAddressBookManager(walletAddressBookManager);
        nicheWalletTypeCryptoWallet.setWalletContactsManager(walletContactsManager);

        try {
            nicheWalletTypeCryptoWallet.initialize();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetCryptoWalletException();
        }

        return nicheWalletTypeCryptoWallet;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWallet");

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
            if (CryptoWalletNicheWalletTypePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                CryptoWalletNicheWalletTypePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                CryptoWalletNicheWalletTypePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                CryptoWalletNicheWalletTypePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoWalletNicheWalletTypePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public void setActorAddressBookManager(ActorAddressBookManager actorAddressBookManager) {
        this.actorAddressBookManager = actorAddressBookManager;
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
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
    }

    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }

    @Override
    public void setId(UUID pluginId) {
        //this.pluginId = pluginId;
    }
}
