package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDatabaseFactory;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDeveloperDatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorConnectionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/12/15.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoBrokerActorConnectionDeveloperDatabaseFactory extends ActorConnectionDeveloperDatabaseFactory {

    public CryptoBrokerActorConnectionDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                               final UUID                 pluginId            ) {

       super(
               pluginDatabaseSystem,
               pluginId
       );

    }

    protected ActorConnectionDatabaseFactory getActorConnectionDatabaseFactory() {

        return new CryptoBrokerActorConnectionDatabaseFactory(pluginDatabaseSystem);
    }

}