package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerManager;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.interfaces.CryptoCustomerManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorConnectionDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.event_handlers.CryptoBrokerConnectionRequestNewsEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.event_handlers.CryptoBrokerConnectionRequestUpdatesEventHandler;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionEventActions;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.structure.ActorConnectionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.CryptoCustomerActorConnectionPluginRoot</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(createdBy = "lnacosta", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_CONNECTION, plugin = Plugins.CRYPTO_CUSTOMER)
public class CryptoCustomerActorConnectionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_BROKER)
    private CryptoBrokerManager cryptoBrokerManagerNetworkService;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_CUSTOMER)
    private CryptoCustomerManager cryptoCustomerManagerNetworkService;

    public CryptoCustomerActorConnectionPluginRoot() {
        super(new PluginVersionReference(new Version()));

        listenersAdded = new ArrayList<>();
    }

    private ActorConnectionManager fermatManager;

    private List<FermatEventListener> listenersAdded;

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            final CryptoCustomerActorConnectionDao dao = new CryptoCustomerActorConnectionDao(
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId
            );

            dao.initializeDatabase();

            final ActorConnectionEventActions eventActions = new ActorConnectionEventActions(
                    cryptoBrokerManagerNetworkService,
                    dao,
                    this,
                    broadcaster,
                    this.getPluginVersionReference(),
                    cryptoCustomerManagerNetworkService
            );

            FermatEventListener updatesListener = eventManager.getNewListener(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES);
            updatesListener.setEventHandler(new CryptoBrokerConnectionRequestUpdatesEventHandler(eventActions, this));
            eventManager.addListener(updatesListener);
            listenersAdded.add(updatesListener);

            FermatEventListener newListener = eventManager.getNewListener(EventType.CRYPTO_BROKER_CONNECTION_REQUEST_NEWS);
            newListener.setEventHandler(new CryptoBrokerConnectionRequestNewsEventHandler(eventActions, this));
            eventManager.addListener(newListener);
            listenersAdded.add(newListener);

            fermatManager = new ActorConnectionManager(
                    cryptoBrokerManagerNetworkService,
                    dao,
                    this,
                    this.getPluginVersionReference()
            );

            super.start();

        } catch (final CantInitializeActorConnectionDatabaseException cantInitializeActorConnectionDatabaseException) {

            reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantInitializeActorConnectionDatabaseException
            );

            throw new CantStartPluginException(
                    cantInitializeActorConnectionDatabaseException,
                    "Crypto Customer Actor Connection.",
                    "Problem initializing database of the plug-in."
            );
        }
    }

    @Override
    public void stop() {

        // remove all listeners from the event manager and from the plugin.
        for (FermatEventListener listener : listenersAdded)
            eventManager.removeListener(listener);

        listenersAdded.clear();

        super.stop();

    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoCustomerActorConnectionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CryptoCustomerActorConnectionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new CryptoCustomerActorConnectionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }
}
