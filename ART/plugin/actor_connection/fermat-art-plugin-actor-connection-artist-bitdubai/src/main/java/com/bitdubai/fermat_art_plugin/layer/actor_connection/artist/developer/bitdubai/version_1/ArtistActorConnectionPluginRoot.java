package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.event_handler.ArtistConnectionRequestNewsEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.event_handler.ArtistConnectionRequestUpdatesEventHandler;
import com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure.ActorConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/03/16.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM, maintainerMail = "darkpriestrelative@gmail.com", createdBy = "darkestpriest", layer = Layers.ACTOR_CONNECTION, platform = Platforms.ART_PLATFORM, plugin = Plugins.ARTIST_ACTOR_CONNECTION)
public class ArtistActorConnectionPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE, addon  = Addons .EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_FILE_SYSTEM    )
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;

    @NeededPluginReference(platform = Platforms.ART_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin =  Plugins.ARTIST)
    private ArtistManager artistActorNetworkServiceManager;

    /**
     * Represents the plugin manager
     */
    private com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure.ActorConnectionManager actorConnectionManager;

    /**
     * This represents the list of FermatEventListener used in this plugin.
     */
    private List<FermatEventListener> listenersAdded;

    public ArtistActorConnectionPluginRoot() {
        super(new PluginVersionReference(new Version()));
        listenersAdded = new ArrayList<>();
    }

    @Override
    public FermatManager getManager() {
        return actorConnectionManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            final com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDao artistActorConnectionDao = new com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDao(
                    pluginDatabaseSystem,
                    pluginFileSystem,
                    pluginId
            );
            artistActorConnectionDao.initializeDatabase();
            final com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure.ActorConnectionEventsActions eventActions = new com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.structure.ActorConnectionEventsActions(
                    artistActorNetworkServiceManager,
                    artistActorConnectionDao,
                    errorManager,
                    eventManager,
                    broadcaster,
                    this.getPluginVersionReference()
            );
            FermatEventListener newsListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_NEWS);
            newsListener.setEventHandler(new ArtistConnectionRequestNewsEventHandler(
                    eventActions,
                    this));
            eventManager.addListener(newsListener);
            listenersAdded.add(newsListener);
            FermatEventListener updatesListener = eventManager.getNewListener(
                    EventType.ARTIST_CONNECTION_REQUEST_UPDATES);
            updatesListener.setEventHandler(new ArtistConnectionRequestUpdatesEventHandler(
                    eventActions,
                    this));
            eventManager.addListener(updatesListener);
            listenersAdded.add(updatesListener);
            actorConnectionManager = new ActorConnectionManager(
                    artistActorNetworkServiceManager,
                    artistActorConnectionDao,
                    errorManager,
                    this.getPluginVersionReference()
            );
            super.start();
        } catch (final CantInitializeActorConnectionDatabaseException cantInitializeActorConnectionDatabaseException) {
            errorManager.reportUnexpectedPluginException(
                    getPluginVersionReference(),
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
        return new com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase) {
        return new com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(
            DeveloperObjectFactory developerObjectFactory,
            DeveloperDatabase developerDatabase,
            DeveloperDatabaseTable developerDatabaseTable) {
        return new com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database.ArtistActorConnectionDeveloperDatabaseFactory(
                pluginDatabaseSystem,
                pluginId).getDatabaseTableContent(developerObjectFactory,  developerDatabaseTable);
    }
}
