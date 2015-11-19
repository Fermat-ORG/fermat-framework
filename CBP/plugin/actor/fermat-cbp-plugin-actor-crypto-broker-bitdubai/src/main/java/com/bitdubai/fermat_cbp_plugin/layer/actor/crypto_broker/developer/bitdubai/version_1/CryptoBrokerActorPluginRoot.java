package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
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
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantCreateNewBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.exceptions.CantGetListBrokerIdentityWalletRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerActorDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Angel on 19-11-2015.
 */

public class CryptoBrokerActorPluginRoot extends AbstractPlugin implements CryptoBrokerActorManager, DatabaseManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private LogManager logManager;
    private UUID pluginId;
    private ServiceStatus status;

    private static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    private CryptoBrokerActorDao customerBrokerPurchaseNegotiationDao;


    public CryptoBrokerActorPluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        this.status = ServiceStatus.STARTED;
        try {
            this.customerBrokerPurchaseNegotiationDao = new CryptoBrokerActorDao(pluginDatabaseSystem, pluginId);
            this.customerBrokerPurchaseNegotiationDao.initializeDatabase();
        } catch (CantInitializeCryptoBrokerActorDatabaseException cantInitializeExtraUserRegistryException) {
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DESIGNER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeExtraUserRegistryException);
            // throw new CantStartPluginException(cantInitializeExtraUserRegistryException, Plugins.BITDUBAI_ACTOR_DEVELOPER);
        }
    }

    @Override
    public void pause() {
        this.status = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.status = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.status = ServiceStatus.STOPPED;
    }

    // Metodos del Manager

        @Override
        public BrokerIdentityWalletRelationship createNewBrokerIdentityWalletRelationship(ActorIdentity identity, UUID wallet) throws CantCreateNewBrokerIdentityWalletRelationshipException {
            return this.customerBrokerPurchaseNegotiationDao.createNewBrokerIdentityWalletRelationship(identity, wallet);
        }

        @Override
        public Collection<BrokerIdentityWalletRelationship> getAllBrokerIdentityWalletRelationship() throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.customerBrokerPurchaseNegotiationDao.getAllBrokerIdentityWalletRelationship();
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByIdentity(ActorIdentity identity) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.customerBrokerPurchaseNegotiationDao.getBrokerIdentityWalletRelationshipByIdentity(identity);
        }

        @Override
        public BrokerIdentityWalletRelationship getBrokerIdentityWalletRelationshipByWallet(UUID wallet) throws CantGetListBrokerIdentityWalletRelationshipException {
            return this.customerBrokerPurchaseNegotiationDao.getBrokerIdentityWalletRelationshipByWallet(wallet);
        }

    /*CryptoBrokerIdentityManager Interface implementation.*/

        @Override
        public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
            CryptoBrokerActorDeveloperDatabaseFactory dbFactory = new CryptoBrokerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
            CryptoBrokerActorDeveloperDatabaseFactory dbFactory = new CryptoBrokerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            return dbFactory.getDatabaseTableList(developerObjectFactory);
        }

        @Override
        public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
            try {
                CryptoBrokerActorDeveloperDatabaseFactory dbFactory = new CryptoBrokerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
                dbFactory.initializeDatabase();
                return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
            } catch (CantInitializeCryptoBrokerActorDatabaseException e) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CBP_CRYPTO_BROKER_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
            return new ArrayList<>();
        }
}
