package com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartLanguageInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartSkinInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DealsWithWalletStoreNetworkService;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
//import java.util.logging.Logger;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author ciencias
 * Created by ciencias on 30.12.14.
 */

/**
 * This plugin manages the information about all wallets published only at the level needed for showing it at the wallet
 * store sub app.
 * * *
 */

public class WalletStoreModulePluginRoot implements DealsWithErrors, DealsWithWalletManager, DealsWithEvents, DealsWithLogger, DealsWithWalletStoreMiddleware, DealsWithWalletStoreNetworkService, WalletStoreModuleManager, LogManagerForDevelopers, Plugin, Service {

    /**
     * WalletStoreModulePluginRoot member variables
     */
    com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager walletStoreModuleManager;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithWalletStoreNetworkService interface member variable
     */
    WalletStoreManager walletStoreManagerNetworkService;

    /**
     * DealsWithWEalletStoreMiddleware interface member variable
     */
    com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManagerMiddleware;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }    }

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

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
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
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager");
        /**
         * I return the values.
         */
        return returnedClasses;
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
            return WalletStoreModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception exception) {
            System.err.println("CantGetLogLevelByClass: " + exception.getMessage());
            return LogLevel.MINIMAL_LOGGING;
        }
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
            if (WalletStoreModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * DealsWithWalletStoreMiddleware interface implementation
     *
     * @param walletStoreManager
     */
    @Override
    public void setWalletStoreManager(com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManager) {
        this.walletStoreManagerMiddleware = walletStoreManager;
    }

    /**
     * DEalswithWalletStoreNetworkService interface implementation
     *
     * @param walletStoreManager
     */
    @Override
    public void setWalletStoreManager(WalletStoreManager walletStoreManager) {
        this.walletStoreManagerNetworkService = walletStoreManager;
    }

    /**
     * WalletStoreModule manager interface implementation
     */

    public com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager getWalletStoreModuleManager() {
        if (walletStoreModuleManager == null)
            walletStoreModuleManager = new com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager(errorManager, logManager, walletStoreManagerMiddleware, walletStoreManagerNetworkService);

        //TEST
        walletStoreModuleManager.setWalletManagerManager(walletManagerManager);
        return walletStoreModuleManager;
    }

    @Override
    public WalletStoreCatalogue getCatalogue() throws CantGetRefinedCatalogException {
        return getWalletStoreModuleManager().getCatalogue();
    }

    @Override
    public void installLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartLanguageInstallationException {
        getWalletStoreModuleManager().installLanguage(walletCatalogueId, languageId);
    }

    @Override
    public void installSkin(UUID walletCatalogueId, UUID skinId) throws CantStartSkinInstallationException {
        getWalletStoreModuleManager().installSkin(walletCatalogueId, skinId);
    }

    @Override
    public void installWallet(WalletCategory walletCategory, UUID skinId, UUID languageId, UUID walletCatalogueId, Version version) throws CantStartInstallationException {
        //Logger LOG = Logger.getGlobal();
        //LOG.info("MA_WALLET_STORE_PLUGIN_ROOT:" + getWalletStoreModuleManager());
        getWalletStoreModuleManager().installWallet(walletCategory, skinId, languageId, walletCatalogueId, version);
    }

    @Override
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantStartUninstallLanguageException {
        getWalletStoreModuleManager().uninstallLanguage(walletCatalogueId, languageId);
    }

    @Override
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantStartUninstallSkinException {
        getWalletStoreModuleManager().uninstallSkin(walletCatalogueId, skinId);
    }

    @Override
    public void uninstallWallet(UUID walletCatalogueId) throws CantStartUninstallWalletException {
        getWalletStoreModuleManager().uninstallWallet(walletCatalogueId);
    }

    @Override
    public DetailedCatalogItem getCatalogItemDetails(UUID walletCatalogId) throws CantGetWalletsCatalogException {
        try {
            return walletStoreManagerNetworkService.getDetailedCatalogItem(walletCatalogId);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));

            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        }

    }
    /**
     * DealsWithWalletManager interface variable and implementation
     * Added by Manuel perez on 02/09/2015
     * This implementation sets the WalletManagerManager, fixing the installation process
     */
    WalletManagerManager walletManagerManager;
    @Override
    public void setWalletManagerManager(WalletManagerManager walletManagerManager) {
        this.walletManagerManager = walletManagerManager;
        Logger LOG = Logger.getGlobal();
        LOG.info("MAP_ROOT_SETWMM:"+this.walletManagerManager);
    }
}
