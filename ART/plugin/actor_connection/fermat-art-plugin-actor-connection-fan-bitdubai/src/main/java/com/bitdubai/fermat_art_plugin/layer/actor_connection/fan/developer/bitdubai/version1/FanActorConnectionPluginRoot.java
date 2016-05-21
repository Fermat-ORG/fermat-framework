package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException;
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
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_art_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database.FanActorConnectionDao;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database.FanActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.event_handler.FanaticConnectionRequestNewsEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.event_handler.FanaticConnectionRequestUpdatesEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure.ActorConnectionEventActions;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.structure.ActorConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.ACTOR_CONNECTION, platform = Platforms.ART_PLATFORM, plugin = Plugins.FAN_ACTOR_CONNECTION)
public class FanActorConnectionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM , layer = Layers.PLATFORM_SERVICE, addon  = Addons.ERROR_MANAGER )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM , layer = Layers.PLATFORM_SERVICE, addon  = Addons .EVENT_MANAGER )
    private EventManager eventManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM , addon  = Addons .PLUGIN_FILE_SYSTEM )
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM , addon  = Addons .PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin =  Plugins.ARTIST)
    private ArtistManager artistActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin =  Plugins.FAN)
    private FanManager fanActorNetworkServiceManager;

    /**
     * Represents the plugin manager.
     */
    private ActorConnectionManager actorConnectionManager;

    private List<FermatEventListener> listenersAdded;
    public FanActorConnectionPluginRoot() {
        super(new PluginVersionReference(new Version()));
        listenersAdded = new ArrayList<>();
    }

    /**
     * This method returns the plugin manager
     * @return
     */
    @Override
    public FermatManager getManager() {
        return actorConnectionManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            final FanActorConnectionDao dao = new FanActorConnectionDao(
                    pluginDatabaseSystem,
                    pluginFileSystem    ,
                    pluginId
            );
            dao.initializeDatabase();
            final ActorConnectionEventActions eventActions = new ActorConnectionEventActions(
                    artistActorNetworkServiceManager,
                    fanActorNetworkServiceManager,
                    dao,
                    errorManager,
                    broadcaster,
                    this.getPluginVersionReference()
            );
            FermatEventListener updatesListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
            updatesListener.setEventHandler(
                    new FanaticConnectionRequestUpdatesEventHandler(
                            eventActions,
                            this));
            eventManager.addListener(updatesListener);
            listenersAdded.add(updatesListener);

            FermatEventListener newListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_NEWS);
            newListener.setEventHandler(
                    new FanaticConnectionRequestNewsEventHandler(
                    eventActions,
                    this));
            eventManager.addListener(newListener);
            listenersAdded.add(newListener);

            actorConnectionManager = new ActorConnectionManager(
                    artistActorNetworkServiceManager,
                    dao,
                    errorManager,
                    this.getPluginVersionReference(),
                    fanActorNetworkServiceManager,
                    eventManager
            );
            super.start();
        } catch (final CantInitializeActorConnectionDatabaseException
                cantInitializeActorConnectionDatabaseException) {
            errorManager.reportUnexpectedPluginException(
                    getPluginVersionReference()                           ,
                    UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,
                    cantInitializeActorConnectionDatabaseException
            );
            throw new CantStartPluginException(
                    cantInitializeActorConnectionDatabaseException,
                    "Artist Actor Connection.",
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
        return new FanActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase) {
        return new FanActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase,
            DeveloperDatabaseTable developerDatabaseTable) {
        return new FanActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseTableContent(developerObjectFactory,  developerDatabaseTable);
    }
}
