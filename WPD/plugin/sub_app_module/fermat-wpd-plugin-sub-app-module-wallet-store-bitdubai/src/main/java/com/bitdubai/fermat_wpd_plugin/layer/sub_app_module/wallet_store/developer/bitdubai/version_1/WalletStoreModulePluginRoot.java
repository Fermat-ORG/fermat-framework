package com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.basic_classes.BasicWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantGetRefinedCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartLanguageInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartSkinInstallationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.CantStartUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

public class WalletStoreModulePluginRoot extends AbstractPlugin implements
        WalletStoreModuleManager,
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER         )
    WalletManagerManager walletManagerManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION   , layer = Layers.NETWORK_SERVICE, plugin = Plugins.WALLET_STORE         )
    WalletStoreManager walletStoreManagerNetworkService;

    @NeededPluginReference(platform = Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION   , layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_STORE         )
    com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager walletStoreManagerMiddleware;

    /** WalletStoreModulePluginRoot member variables */
    com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager walletStoreModuleManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    SettingsManager<BasicWalletSettings> settingsManager;


    public WalletStoreModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

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
     * WalletStoreModule manager interface implementation
     */

    public com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager getWalletStoreModuleManager() {
        if (walletStoreModuleManager == null)
            walletStoreModuleManager = new com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreModuleManager(errorManager, logManager, walletStoreManagerMiddleware, walletStoreManagerNetworkService, walletManagerManager);

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
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,FermatException.wrapException(e));

            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        }
    }

    @Override
    public SettingsManager<BasicWalletSettings> getSettingsManager() {
        if (settingsManager != null)
            return settingsManager;

        settingsManager = new SettingsManager<>(pluginFileSystem, pluginId);
        return settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
