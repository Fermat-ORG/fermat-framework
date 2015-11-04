package com.bitdubai.android_core.app.common.version_1.managers;


import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_core.CorePlatformContext;

/**
 * Created by Matias Furszyfer on 2015.10.19..
 */

public class ManagerFactory {

    private CorePlatformContext corePlatformContext;


    public ManagerFactory(CorePlatformContext corePlatformContext) {
        this.corePlatformContext = corePlatformContext;
    }

    public ModuleManager getModuleManagerFactory(SubApps subApps) {
        ModuleManager moduleManager = null;

        switch (subApps) {
            case CWP_WALLET_MANAGER:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);
                break;
            case CWP_WALLET_FACTORY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_FACTORY_SUB_APP_MODULE);
                break;
            case CWP_DEVELOPER_APP:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DEVELOPER_MODULE);
                break;
            case CWP_WALLET_STORE:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_STORE_SUB_APP_MODULE);
                break;
            case CCP_INTRA_USER_COMMUNITY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_INTRA_USER_FACTORY_MODULE);
                break;
            case CWP_WALLET_PUBLISHER:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_SUB_APP_MODULE);
                break;
            case CWP_INTRA_USER_IDENTITY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CCP_INTRA_WALLET_USER_IDENTITY);
                break;
            case DAP_ASSETS_FACTORY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_ASSET_FACTORY_MODULE);
                break;
            case DAP_ASSETS_COMMUNITY_USER:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_USER_COMMUNITY_SUB_APP_MODULE);
                break;
            case DAP_ASSETS_COMMUNITY_ISSUER:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_COMMUNITY_SUB_APP_MODULE);
                break;
            case DAP_ASSETS_COMMUNITY_REDEEM_POINT:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_REDEEM_POINT_COMMUNITY_SUB_APP_MODULE);
                break;
            case CBP_CRYPTO_BROKER_IDENTITY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY_SUB_APP_MODULE);
                break;
            case CBP_CRYPTO_CUSTOMER_IDENTITY:
                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CBP_CRYPTO_CUSTOMER_IDENTITY_SUB_APP_MODULE);
                break;
//            case BITDUBAI_CRYPTO_WALLET_WALLET_MODULE:
//                moduleManager = (ModuleManager)corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);
//                break;
//            case BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE:
//                moduleManager = (ModuleManager)corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE);
//                break;
            default:
                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory");
                break;


        }

        return moduleManager;
    }

    public ModuleManager getModuleManagerFactory(Platforms platformType, WalletCategory walletCategory, WalletType walletType) {
        ModuleManager moduleManager = null;


        switch (platformType) {

            case CRYPTO_CURRENCY_PLATFORM:

                switch (walletCategory) {

                    case REFERENCE_WALLET:

                        switch (walletType) {

                            case REFERENCE:
                                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);
                                break;
                            default:
                                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE");
                                break;

                        }

                    default:
                        System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE_WALLET");
                        break;

                }
                break;

            case CRYPTO_COMMODITY_MONEY:

                break;
            case DIGITAL_ASSET_PLATFORM:


                switch (walletCategory) {

                    case REFERENCE_WALLET:

                        switch (walletType) {

                            case REFERENCE:

                                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_DAP_ASSET_ISSUER_WALLET_MODULE);
                                break;
                            default:
                                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE");
                                break;


                        }

                    default:
                        System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE_WALLET");
                        break;

                }

                break;

            case CRYPTO_BROKER_PLATFORM:

                switch (walletCategory) {

                    case REFERENCE_WALLET:

                        switch (walletType) {

                            case REFERENCE:
                                moduleManager = (ModuleManager) corePlatformContext.getPlugin(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE);
                                break;
                            default:
                                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE");
                                break;


                        }

                    default:
                        System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En el tipo REFERENCE_WALLET");
                        break;


                }

                break;

            default:
                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory. En la PLATAFORMA CORRESPONDIENTE");
                break;
        }

        return moduleManager;
    }

}

