package org.fermat.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.moduleManagerInterfacea;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentityManager;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.exceptions.CantGetRedeemPointWalletModuleException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import org.fermat.fermat_dap_plugin.layer.module.wallet.redeem.point.developer.version_1.structure.AssetRedeemPointWalletModuleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * TODO ADD HERE A LITTLE EXPLANATION ABOUT THE FUNCIONALITY OF THE PLUG-IN
 * <p/>
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.WALLET_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.REDEEM_POINT)
public class AssetRedeemPointWalletModulePluginRoot extends AbstractModule<RedeemPointSettings, ActiveActorIdentityInformation> implements
        LogManagerForDevelopers {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.REDEEM_POINT)
    AssetRedeemPointWalletManager assetRedeemPointWalletManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.REDEEM_POINT)
    RedeemPointIdentityManager redeemPointIdentityManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    // TODO MAKE USE OF THE ERROR MANAGER

    private AssetRedeemPointWalletModuleManager assetRedeemPointWalletModuleManager;

    public AssetRedeemPointWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
//    @Override
//    public void start() throws CantStartPluginException {
//        try {
//            System.out.println("******* Asset Redeem Point Wallet Module Init ******");
//            this.serviceStatus = ServiceStatus.STARTED;
//        } catch (Exception exception) {
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_REDEEM_POINT_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
//            throw new CantStartPluginException(exception);
//        }
//    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("AssetRedeemPointWalletModulePluginRoot");
        returnedClasses.add("AssetRedeemPointWalletModuleManager");

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
            if (AssetRedeemPointWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetRedeemPointWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetRedeemPointWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetRedeemPointWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            return AssetRedeemPointWalletModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    @moduleManagerInterfacea(moduleManager = AssetRedeemPointWalletSubAppModule.class)
    public ModuleManager<RedeemPointSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        try {
            logManager.log(AssetRedeemPointWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetRedeem Wallet Module instantiation started...", null, null);

            if (assetRedeemPointWalletModuleManager == null) {
                assetRedeemPointWalletModuleManager = new AssetRedeemPointWalletModuleManager(
                        assetRedeemPointWalletManager,
                        redeemPointIdentityManager,
                        pluginId,
                        pluginFileSystem,
                        errorManager,
                        eventManager,
                        broadcaster,
                        this);
            }

            logManager.log(AssetRedeemPointWalletModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetRedeem Wallet Module instantiation finished successfully.", null, null);

            return assetRedeemPointWalletModuleManager;

        } catch (final Exception e) {
            throw new CantGetModuleManagerException(CantGetRedeemPointWalletModuleException.DEFAULT_MESSAGE, FermatException.wrapException(e).toString());
        }
    }
}
