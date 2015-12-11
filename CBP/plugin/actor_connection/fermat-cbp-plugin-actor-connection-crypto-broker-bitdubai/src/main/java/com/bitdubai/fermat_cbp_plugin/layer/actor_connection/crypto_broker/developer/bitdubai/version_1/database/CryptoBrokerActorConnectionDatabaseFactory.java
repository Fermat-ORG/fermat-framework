package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorConnectionDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Contains all the basic functionality for an actor connection database factory.
 * 
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerActorConnectionDatabaseFactory extends ActorConnectionDatabaseFactory {

    public CryptoBrokerActorConnectionDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        super(pluginDatabaseSystem);
    }

}
