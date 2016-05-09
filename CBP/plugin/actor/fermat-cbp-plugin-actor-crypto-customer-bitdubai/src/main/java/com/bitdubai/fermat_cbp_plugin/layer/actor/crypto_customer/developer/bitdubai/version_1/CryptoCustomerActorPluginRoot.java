package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.interfaces.CryptoBrokerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.event_handlers.CryptoBrokerNewConnectionEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.event_handlers.CryptoCustomerExtraDataEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.ActorCustomerExtraDataEventActions;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoBrokerExtraDataUpdateAgent;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CustomerActorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO ADD A DESCRIPTION OF THE PLUG-IN
 *
 * Created by Angel on 19-11-2015.
 */
@PluginInfo(createdBy = "yalayn", maintainerMail = "y.alayn@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.CRYPTO_CUSTOMER_ACTOR)
public class CryptoCustomerActorPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM  , layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_BROKER )
    private CryptoBrokerManager cryptoBrokerANSManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_CONNECTION     , plugin = Plugins.CRYPTO_BROKER     )
    private CryptoBrokerActorConnectionManager cryptoBrokerActorConnectionManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    private List<FermatEventListener> listenersAdded  = new ArrayList<>();
    private CryptoCustomerActorDao cryptoCustomerActorDao;
    private CryptoBrokerExtraDataUpdateAgent agente;

    public CryptoCustomerActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

        /*
            Plugin Interface implementation.
        */

            @Override
            public void start() throws CantStartPluginException {

                try {

                    FermatEventListener fermatEventListener;
                    FermatEventHandler fermatEventHandler;

                    this.cryptoCustomerActorDao = new CryptoCustomerActorDao(pluginDatabaseSystem, pluginFileSystem, pluginId);
                    this.cryptoCustomerActorDao.initializeDatabase();

                    fermatManager = new CustomerActorManager(this.cryptoCustomerActorDao, cryptoBrokerANSManager, errorManager, getPluginVersionReference());

                    ActorCustomerExtraDataEventActions handlerAction = new ActorCustomerExtraDataEventActions(cryptoBrokerANSManager, cryptoCustomerActorDao, cryptoBrokerActorConnectionManager);

                    fermatEventListener = eventManager.getNewListener(EventType.CRYPTO_BROKER_QUOTES_REQUEST_UPDATES);
                    fermatEventHandler = new CryptoCustomerExtraDataEventHandler(handlerAction, this);
                    fermatEventListener.setEventHandler(fermatEventHandler);
                    eventManager.addListener(fermatEventListener);
                    listenersAdded.add(fermatEventListener);

                    fermatEventListener = eventManager.getNewListener(EventType.CRYPTO_BROKER_ACTOR_CONNECTION_NEW_CONNECTION);
                    fermatEventHandler = new CryptoBrokerNewConnectionEventHandler(handlerAction, this);
                    fermatEventListener.setEventHandler(fermatEventHandler);
                    eventManager.addListener(fermatEventListener);
                    listenersAdded.add(fermatEventListener);


                    agente = new CryptoBrokerExtraDataUpdateAgent(cryptoBrokerANSManager, cryptoCustomerActorDao, errorManager, getPluginVersionReference());
                    agente.start();
                    this.serviceStatus = ServiceStatus.STARTED;

                } catch (CantStartAgentException | CantInitializeCryptoCustomerActorDatabaseException e) {
                    errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                    throw new CantStartPluginException(e, this.getPluginVersionReference());
                }

            }

            @Override
            public FermatManager getManager() {
                return fermatManager;
            }

            private FermatManager fermatManager;

            @Override
            public void stop() {
                super.stop();
                for (FermatEventListener fermatEventListener : listenersAdded){
                    eventManager.removeListener(fermatEventListener);
                }
                listenersAdded.clear();

                agente.stop();
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
