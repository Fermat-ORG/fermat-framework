package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 21/04/16.
 */
public class ChatActorConnectionDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate the database factory.
     */
    public ChatActorConnectionDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Through the method <code>createDatabase</code> we can create an actor connection database.
     *
     * @param ownerId      the owner id.
     * @param databaseName the database name.
     * @return Database
     * @throws CantCreateDatabaseException if something goes wrong.
     */
    protected Database createDatabase(final UUID ownerId,
                                      final String databaseName) throws CantCreateDatabaseException {

        Database database;

        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

        } catch (final CantCreateDatabaseException e) {

            throw new CantCreateDatabaseException(
                    e,
                    new StringBuilder().append("databaseName: ").append(databaseName).toString(),
                    "Exception not handled by the plugin, There is a problem and i cannot create the database."
            );
        }

        try {

            final DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Actor Connections table.
             */
            DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME);

            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);
            table.addColumn(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 10, Boolean.FALSE);
            table.addColumn(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_COUNTRY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATE_LOCALITY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CITY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);

            table.addIndex(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);

            } catch (final CantCreateTableException e) {

                throw new CantCreateDatabaseException(
                        e,
                        new StringBuilder().append("tableName: ").append(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME).toString(),
                        "Exception not handled by the plugin, There is a problem and i cannot create the table."
                );
            }

            return database;

        } catch (final InvalidOwnerIdException e) {

            throw new CantCreateDatabaseException(
                    e,
                    new StringBuilder().append("databaseName: ").append(databaseName).toString(),
                    "There is a problem with the ownerId of the database."
            );
        }
    }
}
