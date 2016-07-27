package com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.actor_connection.actor_connection.developer.bitdubai.version_1.database.ActorConnectionActorConnectionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/15.
 * Updated by Manuel Perez on 07/05/2016
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */

public abstract class ActorConnectionDeveloperDatabaseFactory {

    protected final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    protected Database database;

    /**
     * Constructor with params.
     *
     * @param pluginDatabaseSystem reference.
     * @param pluginId             of the actor connection plug-in.
     */
    public ActorConnectionDeveloperDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem,
                                                   final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeActorConnectionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeActorConnectionDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    pluginId,
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME
            );

        } catch (final CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeActorConnectionDatabaseException(
                    cantOpenDatabaseException,
                    new StringBuilder().append("databaseName: ").append(ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME).toString(),
                    "There was an error trying to open database."
            );

        } catch (final DatabaseNotFoundException databaseNotFoundException) {

            ActorConnectionDatabaseFactory actorConnectionActorConnectionDatabaseFactory = this.getActorConnectionDatabaseFactory();

            try {

                database = actorConnectionActorConnectionDatabaseFactory.createDatabase(
                        pluginId,
                        ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME
                );

            } catch (final CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeActorConnectionDatabaseException(
                        cantCreateDatabaseException,
                        new StringBuilder().append("databaseName: ").append(ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME).toString(),
                        "There was an error trying to create database."
                );
            }
        }
    }

    protected ActorConnectionDatabaseFactory getActorConnectionDatabaseFactory() {

        return new ActorConnectionDatabaseFactory(pluginDatabaseSystem);
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(
                developerObjectFactory.getNewDeveloperDatabase(
                        ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME,
                        this.pluginId.toString()
                )
        );
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(final DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Table Actor Connections columns.
         */
        List<String> actorConnectionsColumns = new ArrayList<>();

        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME);
        //Location fields
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LATITUDE);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LONGITUDE);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACCURACY);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TIME);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALTITUDE);
        actorConnectionsColumns.add(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LOCATION_SOURCE);

        /**
         * Table Actor Connections addition.
         */
        DeveloperDatabaseTable actorConnectionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME, actorConnectionsColumns);
        tables.add(actorConnectionsTable);

        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(final DeveloperObjectFactory developerObjectFactory,
                                                                      final DeveloperDatabaseTable developerDatabaseTable) {

        try {

            this.initializeDatabase();

            List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<>();

            final DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());

            selectedTable.loadToMemory();

            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            List<String> developerRow;

            for (final DatabaseTableRecord row : records) {

                developerRow = new ArrayList<>();

                for (final DatabaseRecord field : row.getValues())
                    developerRow.add(field.getValue());

                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }

            return returnedRecords;

        } catch (final CantLoadTableToMemoryException |
                CantInitializeActorConnectionDatabaseException e) {

            System.err.println(e);

            return new ArrayList<>();

        } catch (final Exception e) {

            return new ArrayList<>();
        }
    }
}