package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatActorConnectionDeveloperDatabaseFactory extends ActorConnectionDeveloperDatabaseFactory {
    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem reference.
     * @param pluginId             of the actor connection plug-in.
     */
    public ChatActorConnectionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        super(pluginDatabaseSystem, pluginId);
    }
}
