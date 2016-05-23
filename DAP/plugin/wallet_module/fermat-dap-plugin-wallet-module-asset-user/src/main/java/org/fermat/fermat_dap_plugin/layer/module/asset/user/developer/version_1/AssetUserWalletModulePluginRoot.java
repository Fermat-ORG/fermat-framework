package org.fermat.fermat_dap_plugin.layer.module.asset.user.developer.version_1;

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
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.interfaces.AssetBuyerManager;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.interfaces.AssetSellerManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.exceptions.CantGetUserWalletModuleException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.interfaces.AssetTransferManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.interfaces.UserRedemptionManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_plugin.layer.module.asset.user.developer.version_1.structure.AssetUserWalletModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TODO ADD A LITTLE EXPLANATION ABOUT THE MAIN FUNCTIONALITY OF THE PLUG-IN
 * <p/>
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_USER)
public class AssetUserWalletModulePluginRoot extends AbstractModule<AssetUserSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

//    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
//    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.ASSET_USER)
    private AssetUserWalletManager assetUserWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_APPROPRIATION)
    private AssetAppropriationManager assetAppropriationManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.USER_REDEMPTION)
    private UserRedemptionManager userRedemptionManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_TRANSFER)
    private AssetTransferManager assetTransferManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_USER)
    private IdentityAssetUserManager identityAssetUserManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    private AssetFactoryManager assetFactoryManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.FUNDS_TRANSACTION, plugin = Plugins.ASSET_SELLER)
    private AssetSellerManager assetSellerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.FUNDS_TRANSACTION, plugin = Plugins.ASSET_BUYER)
    private AssetBuyerManager assetBuyerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    private AssetUserWalletSubAppModuleManager assetUserWalletModule;

    public AssetUserWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

//    @Override
//    public void start() throws CantStartPluginException {
//        try {
//            System.out.println("******* Asset User Wallet Module Init ******");
//            this.serviceStatus = ServiceStatus.STARTED;
//        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//            throw new CantStartPluginException(exception);
//        }
//    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetUserWalletModulePluginRoot");
        returnedClasses.add("AssetUserWalletModule");

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
            if (AssetUserWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetUserWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetUserWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetUserWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return AssetUserWalletModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
//    @moduleManagerInterfacea(moduleManager = AssetUserWalletModule.class)
    public ModuleManager<AssetUserSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        try {
//            logManager.log(AssetUserWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetUser Wallet Module instantiation started...", null, null);

            if (assetUserWalletModule == null) {
                assetUserWalletModule = new AssetUserWalletModule(
                        assetUserWalletManager,
                        assetAppropriationManager,
                        userRedemptionManager,
                        identityAssetUserManager,
                        assetTransferManager,
                        errorManager,
                        pluginId,
                        pluginFileSystem,
                        broadcaster,
                        eventManager,
                        actorAssetRedeemPointManager,
                        walletMiddlewareManager,
                        assetFactoryManager,
                        actorAssetUserManager,
                        assetBuyerManager,
                        assetSellerManager,
                        bitcoinWalletManager,
                        this);
            }

//            logManager.log(AssetUserWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetUser Wallet Module instantiation finished successfully.", null, null);

            return assetUserWalletModule;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException(CantGetUserWalletModuleException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }
}
