package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 19-11-2015.
 */

public class CryptoCustomerActorPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM  , layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_BROKER )
    private CryptoBrokerManager cryptoBrokerANSManager;

    private CryptoCustomerActorDao cryptoCustomerActorDao;

    public CryptoCustomerActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

        /*
            Plugin Interface implementation.
        */

            @Override
            public void start() throws CantStartPluginException {
                try {
                    this.cryptoCustomerActorDao = new CryptoCustomerActorDao(pluginDatabaseSystem, pluginId);
                    this.cryptoCustomerActorDao.initializeDatabase();
                    this.serviceStatus = ServiceStatus.STARTED;
                } catch (CantInitializeCryptoCustomerActorDatabaseException cantInitializeExtraUserRegistryException) {
                    errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
                    throw new CantStartPluginException(cantInitializeExtraUserRegistryException, this.getPluginVersionReference());
                }
            }

            @Override
            public FermatManager getManager() {
                return new ActorManager(this.cryptoCustomerActorDao, cryptoBrokerANSManager);
            }

        /*
            DatabaseManagerForDevelopers Interface implementation.
        */

            @Override
            public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
                CryptoCustomerActorDeveloperDatabaseFactory dbFactory = new CryptoCustomerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                return dbFactory.getDatabaseList(developerObjectFactory);
            }

            @Override
            public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
                CryptoCustomerActorDeveloperDatabaseFactory dbFactory = new CryptoCustomerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                return dbFactory.getDatabaseTableList(developerObjectFactory);
            }

            @Override
            public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
                try {
                    CryptoCustomerActorDeveloperDatabaseFactory dbFactory = new CryptoCustomerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                    dbFactory.initializeDatabase();
                    return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
                } catch (CantInitializeCryptoCustomerActorDatabaseException e) {
                    this.errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
                return new ArrayList<>();
            }
}
