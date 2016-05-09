package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.settings.CryptoCustomerCommunitySettings;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure.CryptoCustomerCommunityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

@PluginInfo(createdBy = "lnacosta", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.CRYPTO_CUSTOMER_COMMUNITY)
public class CryptoCustomerCommunitySubAppModulePluginRoot extends AbstractModule<CryptoCustomerCommunitySettings, ActiveActorIdentityInformation> {

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE     , addon  = Addons .ERROR_MANAGER     )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM               , addon  = Addons .PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_CUSTOMER   )
    private CryptoCustomerManager cryptoCustomerNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY             , plugin = Plugins.CRYPTO_BROKER     )
    private CryptoBrokerIdentityManager cryptoBrokerIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY             , plugin = Plugins.CRYPTO_CUSTOMER   )
    private CryptoCustomerIdentityManager cryptoCustomerIdentityManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_CONNECTION     , plugin = Plugins.CRYPTO_CUSTOMER     )
    private CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;

    CryptoCustomerCommunityManager moduleManager;


    public CryptoCustomerCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * PlatformService Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {

        try {
            moduleManager = new CryptoCustomerCommunityManager(
                    cryptoBrokerIdentityManager,
                    cryptoCustomerActorConnectionManager,
                    cryptoCustomerNetworkServiceManager,
                    cryptoCustomerIdentityManager,
                    errorManager,
                    pluginFileSystem,
                    pluginId,
                    this.getPluginVersionReference());

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (Exception exception) {

            throw new CantStartPluginException(
                    exception,
                    null,
                    null
            );
        }
    }

    @Override
    public CryptoCustomerCommunityManager getModuleManager() throws CantGetModuleManagerException {
        return moduleManager;
    }
}