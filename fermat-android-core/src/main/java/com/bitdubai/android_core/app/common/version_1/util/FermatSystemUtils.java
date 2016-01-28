package com.bitdubai.android_core.app.common.version_1.util;

import android.app.Application;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.android_core.app.common.version_1.provisory.SubAppManagerProvisory;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetErrorManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetResourcesManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetRuntimeManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ErrorManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ResourcesManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.RuntimeManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.SubAppManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by mati on 2016.01.12..
 */
public class FermatSystemUtils {


    /**
     * Get ErrorManager from the fermat platform
     *
     * @return reference of ErrorManager
     */

    public static ErrorManager getErrorManager(Application application) {
        try {
            return ((ApplicationSession) application).getFermatSystem().getErrorManager(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));
        } catch (ErrorManagerNotFoundException |
                CantGetErrorManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get SubAppRuntimeManager from the fermat platform
     *
     * @return reference of SubAppRuntimeManager
     */

    public static SubAppRuntimeManager getSubAppRuntimeMiddleware(Application application) {

        try {
            return (SubAppRuntimeManager) ((ApplicationSession) application).getFermatSystem().getRuntimeManager(
                    new PluginVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.ENGINE,
                            Plugins.SUB_APP_RUNTIME,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (RuntimeManagerNotFoundException |
                CantGetRuntimeManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletRuntimeManager from the fermat platform
     *
     * @return reference of WalletRuntimeManager
     */

    public static WalletRuntimeManager getWalletRuntimeManager(Application application) {

        try {
            return (WalletRuntimeManager) ((ApplicationSession) application).getFermatSystem().getRuntimeManager(
                    new PluginVersionReference(
                            Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION,
                            Layers.ENGINE,
                            Plugins.WALLET_RUNTIME,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (RuntimeManagerNotFoundException |
                CantGetRuntimeManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }


    /**
     * Get WalletManager from the fermat platform
     *
     * @return reference of WalletManagerManager
     */

    public static WalletManager getWalletManager(Application application) {

        try {
            return (WalletManager) ((ApplicationSession) application).getFermatSystem().getModuleManager(
                    new PluginVersionReference(
                            Platforms.CRYPTO_CURRENCY_PLATFORM,
                            Layers.DESKTOP_MODULE,
                            Plugins.WALLET_MANAGER,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ModuleManagerNotFoundException |
                CantGetModuleManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletManager from the fermat platform
     *
     * @return reference of WalletManagerManager
     */

    public static SubAppManager getSubAppManager(Application application) {

        try {
            return (SubAppManager) ((ApplicationSession) application).getFermatSystem().getModuleManager(
                    new PluginVersionReference(
                            Platforms.CRYPTO_CURRENCY_PLATFORM,
                            Layers.DESKTOP_MODULE,
                            Plugins.SUB_APP_MANAGER,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ModuleManagerNotFoundException |
                CantGetModuleManagerException e) {

            //TODO: Provisory
            return new SubAppManagerProvisory();

//            System.out.println(e.getMessage());
//            System.out.println(e.toString());

//            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletResourcesProvider
     */
    public static WalletResourcesProviderManager getWalletResourcesProviderManager(Application application) {
        try {
            return (WalletResourcesProviderManager) ((ApplicationSession) application).getFermatSystem().getResourcesManager(
                    new PluginVersionReference(
                            Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION,
                            Layers.NETWORK_SERVICE,
                            Plugins.WALLET_RESOURCES,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ResourcesManagerNotFoundException |
                CantGetResourcesManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get SubAppResourcesProvider
     */
    public static SubAppResourcesProviderManager getSubAppResourcesProviderManager(Application application) {

        try {
            return (SubAppResourcesProviderManager) ((ApplicationSession) application).getFermatSystem().getResourcesManager(
                    new PluginVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.NETWORK_SERVICE,
                            Plugins.SUB_APP_RESOURCES,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (ResourcesManagerNotFoundException |
                CantGetResourcesManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get NotificationManager
     */
    public static NotificationManagerMiddleware getNotificationManager(Application application) {
        try {
            return (NotificationManagerMiddleware) ((ApplicationSession) application).getFermatSystem().startAndGetPluginVersion(
                    new PluginVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.SUB_APP_MODULE,
                            Plugins.NOTIFICATION,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (VersionNotFoundException |
                CantStartPluginException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     * Get DesktopRuntimeManager
     */
    public static DesktopRuntimeManager getDesktopRuntimeManager(Application application) {

        try {
            return (DesktopRuntimeManager) ((ApplicationSession) application).getFermatSystem().getRuntimeManager(
                    new PluginVersionReference(
                            Platforms.PLUG_INS_PLATFORM,
                            Layers.ENGINE,
                            Plugins.DESKTOP_RUNTIME,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (RuntimeManagerNotFoundException |
                CantGetRuntimeManagerException e) {

            System.out.println(e.getMessage());
            System.out.println(e.toString());

            return null;
        } catch (Exception e) {

            System.out.println(e.toString());

            return null;
        }
    }

    /**
     *  return Instance of cloud client
     * @return
     */

    public static WsCommunicationsCloudClientManager getCloudClient(Application application) {
        try {
            return (WsCommunicationsCloudClientManager) ((ApplicationSession)application).getFermatSystem().startAndGetPluginVersion(
                    new PluginVersionReference(
                            Platforms.COMMUNICATION_PLATFORM,
                            Layers.COMMUNICATION,
                            Plugins.WS_CLOUD_CLIENT,
                            Developers.BITDUBAI,
                            new Version()
                    ));
        } catch (VersionNotFoundException e) {
            e.printStackTrace();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return instance of the bitcoin network
     */
    public static BitcoinNetworkManager getNetwork(Application application) {
        try {
            return (BitcoinNetworkManager) ((ApplicationSession)application).getFermatSystem().startAndGetPluginVersion(
                    new PluginVersionReference(
                            Platforms.BLOCKCHAINS,
                            Layers.CRYPTO_NETWORK,
                            Plugins.BITCOIN_NETWORK,
                            Developers.BITDUBAI,
                            new Version()
                    ));
        } catch (VersionNotFoundException e) {
            e.printStackTrace();
        } catch (CantStartPluginException e) {
            e.printStackTrace();
        }
        return null;
    }


}
