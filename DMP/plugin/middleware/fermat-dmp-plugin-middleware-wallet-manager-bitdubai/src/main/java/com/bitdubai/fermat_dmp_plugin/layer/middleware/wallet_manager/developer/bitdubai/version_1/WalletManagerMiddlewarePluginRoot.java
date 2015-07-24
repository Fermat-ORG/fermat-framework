package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantFindProcessException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerInstallationProcess;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * That plugin produces the specific installation of the wallet on the user device
 * It is responsible for binding all parties to the new Reference Niche Wallet | Niche Wallet is available for use.
 *
 * It also allows other administrative tasks on the wallets installed ( for example uninstall, change skin and language, etc. )
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerMiddlewarePluginRoot implements DealsWithErrors,DealsWithLogger,LogManagerForDevelopers, Plugin, Service, WalletManagerManager {

    private List<InstalledWallet> installedWallets = null;
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
    }
    @Override
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * DealWithErrors Interface implementation. 
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * Plugin methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }




    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.WalletManagerMiddlewarePluginRoot");
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
            if (WalletManagerMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /*
     * WalletManagerManager interface methods implementation
     */

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException{

    }

    /**
     * This method returns the list of installed wallets in the device
     *
     */
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException{
        return installedWallets;
    }

    /**

     * This method starts the process of installing a new language for an specific wallet
     *
     */
    public void installLanguage(UUID walletCatalogueId, UUID languageId, Languages language, String label, Version version) throws CantInstallLanguageException{

    }

    /**
     * This method starts the process of installing a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet we want to install the skin to
     * @param skinId the identifier of the skin
     * @param alias the alias (name) of the skin
     * @param Preview the name of the preview image of the skin
     * @param version the version of the skin
     * @throws CantInstallSkinException
     */
    public void installSkin(UUID walletCatalogueId, UUID skinId, String alias, String Preview, Version version) throws CantInstallSkinException{

    }

    /**
     *
     * This method returns the interface responsible of the installation process of a niche wallet
     *
     * @param walletCategory The category of the wallet to install
     * @param walletPlatformIdentifier an string that encodes the wallet identifier in the platform
     *                                 We are usign the term platform to identify the software installed
     *                                 in the device and not the network.
     * @return an interface to manage the installation of a new wallet
     * @throws CantFindProcessException
     */
    public WalletInstallationProcess installWallet(WalletCategory walletCategory, String walletPlatformIdentifier) throws CantFindProcessException{

        return new WalletManagerInstallationProcess();
    }



    /**
     * This method starts the process of uninstalling a new language for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet where we want to uninstall the language
     * @param languageId the identifier of the language to uninstall
     */
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantUninstallLanguageException{

    }

    /**
     * This method starts the process of uninstalling a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet in which we want to uninstall the language
     * @param skinId the identifier of the skin
     */
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantUninstallSkinException{

    }

    /**
     * This method starts the uninstalling process of a walled
     *
     * @param walletIdInThisDevice the id of the wallet to uninstall
     */
    public void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException{

    }

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws CantRemoveWalletException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException{

    }

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws CantRenameWalletException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException{

    }
}
