package com.bitdubai.android_core.app.common.version_1.util.system;

import com.bitdubai.android_core.app.FermatApplication;
import com.bitdubai.android_core.app.common.version_1.communication.client_system_broker.exceptions.CantCreateProxyException;
import com.bitdubai.android_core.app.common.version_1.provisory.P2PAppsRuntimeManager;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetImageResourceException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetLanguageFileException;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetSkinFileException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenOrientation;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.AppManager;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManager;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManagerSettings;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.SubAppManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.UUID;

/**
 * Created by mati on 2016.01.12..
 */
public class FermatSystemUtils {

//    /**
//     * Get the application manager
//     *
//     * @return
//     */
//
//    public static FermatAppsManager getFermatAppManager(){
//        return FermatApplication.getInstance().getFermatAppsManager();
//    }


    /**
     * Return an instance of module manager
     *
     * @param pluginVersionReference
     * @return
     */
    public static ModuleManager getModuleManager(PluginVersionReference pluginVersionReference) {
        try {
            return FermatApplication.getInstance().getFermatSystem().getModuleManager(pluginVersionReference);
        } catch (ModuleManagerNotFoundException | CantGetModuleManagerException e) {
            System.err.println(e.getMessage());
            System.err.println(e.toString());
            return null;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }


    /**
     * Get ErrorManager from the fermat platform
     *
     * @return reference of ErrorManager
     */

    public static ErrorManager getErrorManager() {
        try {
            return FermatApplication.getInstance().getFermatSystem().getErrorManager(new AddonVersionReference(Platforms.PLUG_INS_PLATFORM, Layers.PLATFORM_SERVICE, Addons.ERROR_MANAGER, Developers.BITDUBAI, new Version()));
        } catch (ErrorManagerNotFoundException |
                CantGetErrorManagerException e) {

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletRuntimeManager from the fermat platform
     *
     * @return reference of WalletRuntimeManager
     */

    public static WalletRuntimeManager getWalletRuntimeManager() {

        try {
            return (WalletRuntimeManager) FermatApplication.getInstance().getFermatSystem().getRuntimeManager(
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

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }


    /**
     * Get WalletManager from the fermat platform
     *
     * @return reference of WalletManagerManager
     */

    public static WalletManager getWalletManager() {

        try {
            return (WalletManager) FermatApplication.getInstance().getFermatSystem().getPlugin(
                    new PluginVersionReference(
                            Platforms.CRYPTO_CURRENCY_PLATFORM,
                            Layers.DESKTOP_MODULE,
                            Plugins.WALLET_MANAGER,
                            Developers.BITDUBAI,
                            new Version()
                    )
            );
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletManager from the fermat platform
     *
     * @return reference of WalletManagerManager
     */

    public static SubAppManager getSubAppManager() {

        try {
            return (SubAppManager) FermatApplication.getInstance().getFermatSystem().getModuleManager(
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

//            System.err.println(e.getMessage());
//            System.err.println(e.toString());

//            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get WalletResourcesProvider
     */
    public static WalletResourcesProviderManager getWalletResourcesProviderManager() {
        try {
            return (WalletResourcesProviderManager) FermatApplication.getInstance().getFermatSystem().getResourcesManager(
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

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get SubAppResourcesProvider
     */
    public static SubAppResourcesProviderManager getSubAppResourcesProviderManager() {

        try {
            return (SubAppResourcesProviderManager) FermatApplication.getInstance().getFermatSystem().getResourcesManager(
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

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get NotificationManager
     */
    public static NotificationManagerMiddleware getNotificationManager() {
        try {
            return (NotificationManagerMiddleware) FermatApplication.getInstance().getFermatSystem().getPlugin(
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

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * Get DesktopRuntimeManager
     */
    public static DesktopRuntimeManager getDesktopRuntimeManager() {

        try {
            return (DesktopRuntimeManager) FermatApplication.getInstance().getFermatSystem().getRuntimeManager(
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

            System.err.println(e.getMessage());
            System.err.println(e.toString());

            return null;
        } catch (Exception e) {

            System.err.println(e.toString());

            return null;
        }
    }

    /**
     * return Instance of cloud client
     *
     * @return
     */

    public static WsCommunicationsCloudClientManager getCloudClient() {
        try {
            return (WsCommunicationsCloudClientManager) FermatApplication.getInstance().getFermatSystem().getPlugin(
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
    public static BlockchainManager getNetwork() {
        try {
            return (BlockchainManager) FermatApplication.getInstance().getFermatSystem().getPlugin(
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


    /**
     * Get Android core module from the fermat platform
     *
     * @return reference of AndroidCoreModule
     */

    private static AndroidCoreModule androidCoreModule;

    public static AndroidCoreModule getAndroidCoreModule() throws CantCreateProxyException {
        try {
            if (androidCoreModule == null) {
                androidCoreModule = (AndroidCoreModule) FermatApplication.getInstance().getServicesHelpers().getClientSideBrokerServiceAIDL().getModuleManager(
                        new PluginVersionReference(
                                Platforms.PLUG_INS_PLATFORM,
                                Layers.SUB_APP_MODULE,
                                Plugins.ANDROID_CORE,
                                Developers.BITDUBAI,
                                new Version()
                        )
                );
            }
            return androidCoreModule;
        } catch (Exception e) {
            throw e;
        }
//
//        try {
//            return (AndroidCoreModule) FermatApplication.getInstance().getFermatSystem().getModuleManager(
//                    new PluginVersionReference(
//                            Platforms.PLUG_INS_PLATFORM,
//                            Layers.SUB_APP_MODULE,
//                            Plugins.ANDROID_CORE,
//                            Developers.BITDUBAI,
//                            new Version()
//                    )
//            );
//        } catch (CantGetModuleManagerException e) {
//
//            System.err.println(e.getMessage());
//            System.err.println(e.toString());
//
//            return null;
//        } catch (Exception e) {
//
//            System.err.println(e.toString());
//
//            return null;
//        }
    }


    //TODO: hay que crear este plugin en el futuro
    public static AppManager getDesktopManager() {
        return new DesktopManager() {

            @Override
            public SettingsManager<DesktopManagerSettings> getSettingsManager() {
                return null;
            }

            @Override
            public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
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

            @Override
            public FermatApp getApp(String publicKey) {
                return null;
            }
        };
    }

    public static RuntimeManager getP2PApssRuntimeManager() {
        return new P2PAppsRuntimeManager();
    }


    public static ResourceProviderManager getAppResources() {
        return new ResourceProviderManager() {
            @Override
            public UUID getResourcesId() {
                return null;
            }

            @Override
            public Skin getSkinFile(UUID skinId, String walletPublicKey) throws CantGetSkinFileException, CantGetResourcesException {
                return null;
            }

            @Override
            public String getLanguageFile(UUID skinId, String walletPublicKey, String fileName) throws CantGetLanguageFileException {
                return null;
            }

            @Override
            public byte[] getImageResource(String imageName, UUID skinId, String walletPublicKey) throws CantGetImageResourceException {
                return new byte[0];
            }

            @Override
            public byte[] getVideoResource(String videoName, UUID skinId) throws CantGetResourcesException {
                return new byte[0];
            }

            @Override
            public byte[] getSoundResource(String soundName, UUID skinId) throws CantGetResourcesException {
                return new byte[0];
            }

            @Override
            public String getFontStyle(String styleName, UUID skinId) {
                return null;
            }

            @Override
            public String getLayoutResource(String layoutName, ScreenOrientation orientation, UUID skinId, String walletPublicKey) throws CantGetResourcesException {
                return null;
            }
        };
    }
}
