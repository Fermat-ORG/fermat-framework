package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantUninstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationInformation;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerMiddlewarePluginRoot implements DealsWithErrors,DealsWithLogger,LogManagerForDevelopers, Plugin, Service, WalletManagerManager {

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
    @Override
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException {

    }

    @Override
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException {
        return null;
    }

    @Override
    public void installLanguage(UUID walletIdInThisDevice, UUID languageId) throws CantInstallLanguageException {

    }

    @Override
    public void installSkin(UUID walletIdInThisDevice, UUID skinId) throws CantInstallSkinException {

    }

    @Override
    public WalletInstallationProcess installWallet() {
        return null;
    }


    @Override
    public void uninstallLanguage(UUID walletIdInThisDevice, UUID languageId) throws CantUninstallLanguageException {

    }

    @Override
    public void uninstallSkin(UUID walletIdInThisDevice, UUID skinId) throws CantUninstallSkinException {

    }

    @Override
    public void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException {

    }

    @Override
    public void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException {

    }

    @Override
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException {

    }
}
