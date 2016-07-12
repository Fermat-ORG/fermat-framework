package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.IdentityBrokerPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_identity.developer.bitdubai.version_1.structure.CryptoBrokerIdentityModuleManagerImpl;


/**
 * Created by Angel 16/10/2015
 */


@PluginInfo(createdBy = "vlzangel", maintainerMail = "vlzangel91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.CRYPTO_BROKER_IDENTITY)
public class CryptoBrokerIdentitySubAppModulePluginRoot extends AbstractModule<IdentityBrokerPreferenceSettings, ActiveActorIdentityInformation> {

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CRYPTO_BROKER)
    CryptoBrokerIdentityManager identityManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    LocationManager locationManager;

    private CryptoBrokerIdentityModuleManager moduleManager;


    public CryptoBrokerIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public CryptoBrokerIdentityModuleManager getModuleManager() throws CantGetModuleManagerException {
        if (moduleManager == null) {
            moduleManager = new CryptoBrokerIdentityModuleManagerImpl(
                    identityManager,
                    pluginFileSystem,
                    pluginId,
                    this,
                    locationManager
            );

            this.serviceStatus = ServiceStatus.STARTED;
        }

        return moduleManager;
    }

}