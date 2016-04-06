package com.bitdubai.fermat_art_plugin.layer.actor_connection.fan.developer.bitdubai.version1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/04/16.
 */
public class FanActorConnectionDeveloperDatabaseFactory extends
        ActorConnectionDeveloperDatabaseFactory {

    /**
     * Default constructor with parameters.
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public FanActorConnectionDeveloperDatabaseFactory(
            final PluginDatabaseSystem pluginDatabaseSystem,
            final UUID pluginId) {
        super(
                pluginDatabaseSystem,
                pluginId
        );
    }
}