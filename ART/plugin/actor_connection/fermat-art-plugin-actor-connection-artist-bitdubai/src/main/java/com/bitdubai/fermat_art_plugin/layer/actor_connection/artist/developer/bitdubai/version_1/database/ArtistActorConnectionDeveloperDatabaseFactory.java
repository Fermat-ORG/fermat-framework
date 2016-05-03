package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ArtistActorConnectionDeveloperDatabaseFactory extends
        ActorConnectionDeveloperDatabaseFactory {

    /**
     * Constructor with parameters.
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public ArtistActorConnectionDeveloperDatabaseFactory(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId) {
        super(
                pluginDatabaseSystem,
                pluginId
        );
    }
}