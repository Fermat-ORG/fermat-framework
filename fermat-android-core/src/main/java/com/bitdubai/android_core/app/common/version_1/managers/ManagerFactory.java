package com.bitdubai.android_core.app.common.version_1.managers;


import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_core.FermatSystem;

/**
 * Created by Matias Furszyfer on 2015.10.19..
 */

public class ManagerFactory {

    private FermatSystem fermatSystem;


    public ManagerFactory(FermatSystem fermatSystem) {
        this.fermatSystem = fermatSystem;
    }

    public ModuleManager getModuleManagerFactory(final SubApps subApps) {

        switch (subApps) {

            case CWP_WALLET_MANAGER:
                return getModuleManager(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.DESKTOP_MODULE, Plugins.WALLET_MANAGER);
            case CWP_INTRA_USER_IDENTITY:
                return getModuleManager(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.IDENTITY, Plugins.INTRA_WALLET_USER);
            case CCP_INTRA_USER_COMMUNITY:
                return getModuleManager(Platforms.CRYPTO_CURRENCY_PLATFORM, Layers.SUB_APP_MODULE, Plugins.INTRA_WALLET_USER);
            case CWP_DEVELOPER_APP:
                return getModuleManager(Platforms.PLUG_INS_PLATFORM, Layers.SUB_APP_MODULE, Plugins.DEVELOPER);
            case CWP_WALLET_FACTORY:
                return getModuleManager(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE, Plugins.WALLET_FACTORY);
            case CWP_WALLET_STORE:
                return getModuleManager(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE, Plugins.WALLET_STORE);
            case CWP_WALLET_PUBLISHER:
                return getModuleManager(Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION, Layers.SUB_APP_MODULE, Plugins.WALLET_PUBLISHER);
            case DAP_ASSETS_IDENTITY_ISSUER:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.IDENTITY, Plugins.ASSET_ISSUER);
            case DAP_ASSETS_IDENTITY_USER:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.IDENTITY, Plugins.ASSET_USER);
            case DAP_REDEEM_POINT_IDENTITY:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.IDENTITY, Plugins.REDEEM_POINT);
            case DAP_ASSETS_FACTORY:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE, Plugins.ASSET_FACTORY);
            case DAP_ASSETS_COMMUNITY_USER:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE, Plugins.ASSET_USER_COMMUNITY);
            case DAP_ASSETS_COMMUNITY_ISSUER:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE, Plugins.ASSET_ISSUER_COMMUNITY);
            case DAP_ASSETS_COMMUNITY_REDEEM_POINT:
                return getModuleManager(Platforms.DIGITAL_ASSET_PLATFORM, Layers.SUB_APP_MODULE, Plugins.REDEEM_POINT_COMMUNITY);
            case CBP_CRYPTO_BROKER_IDENTITY:
                return getModuleManager(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_BROKER_IDENTITY);
            case CBP_CRYPTO_CUSTOMER_IDENTITY:
                return getModuleManager(Platforms.CRYPTO_BROKER_PLATFORM, Layers.SUB_APP_MODULE, Plugins.CRYPTO_CUSTOMER_IDENTITY);

            default:
                System.out.println("NO se encuentra el modulo seleccionado, ingresarlo en el managerFactory");
                return null;
        }
    }

    private ModuleManager getModuleManager(final Platforms platform,
                                           final Layers layer,
                                           final Plugins plugin) {

        PluginVersionReference pvr = new PluginVersionReference(
                platform,
                layer,
                plugin,
                Developers.BITDUBAI,
                new Version()
        );

        try {

            return this.fermatSystem.getModuleManager(pvr);

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
}

