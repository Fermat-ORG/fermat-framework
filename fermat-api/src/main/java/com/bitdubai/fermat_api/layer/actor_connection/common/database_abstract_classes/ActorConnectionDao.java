package com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connections by storing them on a Database Table.
 * <p/>
 * This class contains all the basic functionality of an Actor Connections Dao.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class ActorConnectionDao<Z extends ActorIdentity, T extends ActorConnection<Z>> {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    public ActorConnectionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                              final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
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
                    "databaseName: "+ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME,
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
                        "databaseName: "+ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME,
                        "There was an error trying to create database."
                );
            }
        }
    }

    protected abstract ActorConnectionDatabaseFactory getActorConnectionDatabaseFactory();

    public final T registerActorConnection(final T               actorConnection,
                                           final ConnectionState connectionState) throws CantRegisterActorConnectionException  ,
                                                                                         ActorConnectionAlreadyExistsException {

        try {

            boolean connectionExists = actorConnectionExists(actorConnection.getLinkedIdentity(), actorConnection.getPublicKey(), actorConnection.getActorType());

            if (connectionExists)
                throw new ActorConnectionAlreadyExistsException(
                        "actorConnection: "+actorConnection+ " - "+ "connectionState to register: "+connectionState,
                        "The connection already exists..."
                );

            final DatabaseTable actorConnectionsTable = database.getTable(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME);

            DatabaseTableRecord entityRecord = actorConnectionsTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(
                    entityRecord   ,
                    actorConnection,
                    connectionState
            );

            actorConnectionsTable.insertRecord(entityRecord);

            return buildActorConnectionNewRecord(entityRecord);

        } catch (final CantInsertRecordException e) {

            throw new CantRegisterActorConnectionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (final InvalidParameterException e) {

            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to build an instance of the actor connection.");
        } catch (final CantGetActorConnectionException e) {

            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to find if the actor connection exists.");
        }
    }

    public void changeConnectionState(final Z               linkedIdentity ,
                                      final String          publicKey      ,
                                      final Actors          actorType      ,
                                      final ConnectionState connectionState) throws CantGetActorConnectionException  ,
                                                                                    ActorConnectionNotFoundException {

        if (linkedIdentity == null)
            throw new CantGetActorConnectionException("", "The linkedIdentity is required, can not be null");

        if (publicKey == null)
            throw new CantGetActorConnectionException("", "The publicKey is required, can not be null");

        if (actorType == null)
            throw new CantGetActorConnectionException("", "The actorType is required, can not be null");

        try {

            final DatabaseTable actorConnectionsTable = database.getTable(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME);

            actorConnectionsTable.setStringFilter    (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, linkedIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.setFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, linkedIdentity.getActorType(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.setStringFilter    (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                , publicKey                    , DatabaseFilterType.EQUAL);
            actorConnectionsTable.setFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACTOR_TYPE_COLUMN_NAME                , actorType                    , DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            if (!records.isEmpty()) {

                final DatabaseTableRecord record = records.get(0);

                record.setFermatEnum(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME, connectionState);

                actorConnectionsTable.updateRecord(record);

            } else
                throw new ActorConnectionNotFoundException(
                        "linkedIdentity: "+linkedIdentity + " - publicKey: "+publicKey+" - actorType: "+actorType,
                        "Cannot find an actor connection request with that requestId."
                );

        } catch (final CantUpdateRecordException e) {

            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: "+linkedIdentity + " - publicKey: "+publicKey+" - actorType: "+actorType,
                    "Exception not handled by the plugin, there is a problem in database and i cannot update the record."
            );
        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: "+linkedIdentity + " - publicKey: "+publicKey+" - actorType: "+actorType
                    , "Exception not handled by the plugin, there is a problem in database and i cannot load the table."
            );
        }
    }

    public boolean actorConnectionExists(final Z      linkedIdentity,
                                         final String publicKey     ,
                                         final Actors actorType     ) throws CantGetActorConnectionException {

        if (linkedIdentity == null)
            throw new CantGetActorConnectionException(null, "", "The linkedIdentity is required, can not be null");

        if (publicKey == null)
            throw new CantGetActorConnectionException(null, "", "The publicKey is required, can not be null");

        if (actorType == null)
            throw new CantGetActorConnectionException(null, "", "The actorType is required, can not be null");

        try {

            final DatabaseTable actorConnectionsTable = database.getTable(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TABLE_NAME);

            actorConnectionsTable.setStringFilter    (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, linkedIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.setFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, linkedIdentity.getActorType(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.setStringFilter    (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                , publicKey                    , DatabaseFilterType.EQUAL);
            actorConnectionsTable.setFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACTOR_TYPE_COLUMN_NAME                , actorType                    , DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: "+linkedIdentity + " - publicKey: "+publicKey+" - actorType: "+actorType,
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    protected DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord record         ,
                                                      final ActorConnection     actorConnection) {

        record.setUUIDValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME             , actorConnection.getConnectionId()                  );
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, actorConnection.getLinkedIdentity() .getPublicKey());
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, actorConnection.getLinkedIdentity() .getActorType());
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                , actorConnection.getPublicKey()                     );
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACTOR_TYPE_COLUMN_NAME                , actorConnection.getActorType()                     );
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME                     , actorConnection.getAlias()                         );
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME          , actorConnection.getConnectionState()               );
        record.setLongValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME             , actorConnection.getCreationTime()                  );
        record.setLongValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME               , actorConnection.getUpdateTime()                    );

        return record;
    }

    protected DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord record         ,
                                                      final ActorConnection     actorConnection,
                                                      final ConnectionState     connectionState) {

        record.setUUIDValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME             , actorConnection.getConnectionId()                  );
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, actorConnection.getLinkedIdentity() .getPublicKey());
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, actorConnection.getLinkedIdentity() .getActorType());
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                , actorConnection.getPublicKey()                     );
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACTOR_TYPE_COLUMN_NAME                , actorConnection.getActorType()                     );
        record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME                     , actorConnection.getAlias()                         );
        record.setFermatEnum (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME          , connectionState                                    );
        record.setLongValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME             , actorConnection.getCreationTime()                  );
        record.setLongValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME               , actorConnection.getUpdateTime()                    );

        return record;
    }

    protected abstract T buildActorConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException;

}
