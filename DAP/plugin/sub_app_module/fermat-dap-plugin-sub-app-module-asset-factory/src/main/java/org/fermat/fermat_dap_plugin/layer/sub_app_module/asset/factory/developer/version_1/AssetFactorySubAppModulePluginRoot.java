package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1.structure.AssetFactorySupAppModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.SUB_APP_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_FACTORY)
public final class AssetFactorySubAppModulePluginRoot extends AbstractModule<AssetFactorySettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    private AssetFactoryManager assetFactoryManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_ISSUER)
    IdentityAssetIssuerManager identityAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    CryptoWalletManager cryptoWalletManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    // TODO PLEASE MAKE USE OF THE ERROR MANAGER.

    private AssetFactoryModuleManager assetFactorySupAppModuleManager;

    public AssetFactorySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetFactorySubAppModulePluginRoot");
        returnedClasses.add("AssetFactorySupAppModuleManager");

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
            if (AssetFactorySubAppModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetFactorySubAppModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split(Pattern.quote("$"));
            return AssetFactorySubAppModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

//    @Override
//    public void start() throws CantStartPluginException {
//        try {
//            this.serviceStatus = ServiceStatus.STARTED;
//        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//            throw new CantStartPluginException(exception);
//        }
//    }

//    @Override
//    public IdentityAssetIssuer getLoggedIdentityAssetIssuer() {
//        try {
//            List<IdentityAssetIssuer> identities = assetFactorySupAppModuleManager.getActiveIdentities();
//            return (identities == null || identities.isEmpty()) ? null : assetFactorySupAppModuleManager.getActiveIdentities().get(0);
//        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//            exception.printStackTrace();
//            return null;
//        }
//    }

//    @Override
//    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
//        assetFactory.setNetworkType(getSelectedNetwork());
//        assetFactorySupAppModuleManager.saveAssetFactory(assetFactory);
//    }

//    @Override
//    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
//        assetFactorySupAppModuleManager.removeAssetFactory(publicKey);
//    }

//    @Override
//    public AssetFactory newAssetFactoryEmpty() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
//        return assetFactorySupAppModuleManager.newAssetFactoryEmpty();
//    }

//    @Override
//    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
//        return assetFactorySupAppModuleManager.getAssetFactory(assetPublicKey);
//    }

//    @Override
//    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
//        return assetFactorySupAppModuleManager.getAssetsFactoryByIssuer(issuerIdentityPublicKey);
//    }
//
//    @Override
//    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
//        return assetFactorySupAppModuleManager.getAssetsFactoryByState(state, selectedNetwork);
//    }
//
//    @Override
//    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
//        return assetFactorySupAppModuleManager.getAssetsFactoryAll(selectedNetwork);
//    }
//
//    @Override
//    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
//        return assetFactorySupAppModuleManager.getAssetFactoryResource(resource);
//    }

//    @Override
//    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
//        return assetFactorySupAppModuleManager.getInstallWallets();
//    }

//    @Override
//    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
//        return assetFactorySupAppModuleManager.isReadyToPublish(assetPublicKey);
//    }

//    public List<AssetFactory> test() {
//        List<AssetFactory> assetFactory = null;
//        try {
//            assetFactory = getAssetFactoryAll();
//
//        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//            System.out.println("******* Test Asset Factory Module, Error. Franklin ******");
//            exception.printStackTrace();
//        }
//        return assetFactory;
//    }

    @Override
//    @moduleManagerInterfacea(moduleManager = AssetFactorySupAppModuleManager.class)
    public AssetFactoryModuleManager getModuleManager() throws CantGetModuleManagerException {
        try {
//            logManager.log(AssetFactorySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Factory Module instantiation started...", null, null);

            if (assetFactorySupAppModuleManager == null) {
                assetFactorySupAppModuleManager = new AssetFactorySupAppModuleManager(
                        assetFactoryManager,
                        identityAssetIssuerManager,
                        cryptoWalletManager,
                        errorManager,
                        eventManager,
                        broadcaster,
                        pluginFileSystem,
                        pluginId,
                        this);
            }

//            logManager.log(AssetFactorySubAppModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Factory Module instantiation finished successfully.", null, null);

            return assetFactorySupAppModuleManager;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException(CantGetAssetFactoryException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }
}
