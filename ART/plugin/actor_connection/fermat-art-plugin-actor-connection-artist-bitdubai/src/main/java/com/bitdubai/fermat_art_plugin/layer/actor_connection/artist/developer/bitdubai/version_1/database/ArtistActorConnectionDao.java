package com.bitdubai.fermat_art_plugin.layer.actor_connection.artist.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistLinkedActorIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 31/03/16.
 */
public class ArtistActorConnectionDao extends ActorConnectionDao<ArtistLinkedActorIdentity, ArtistActorConnection> {

    /**
     * Constructor with parameters
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     * @param pluginId
     */
    public ArtistActorConnectionDao(
            PluginDatabaseSystem pluginDatabaseSystem,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) {
        super(
                pluginDatabaseSystem,
                pluginFileSystem,
                pluginId);
    }

    /**
     * This method returns an ArtistActorConnection from data stored from database.
     * @param record
     * @return
     * @throws InvalidParameterException
     */
    @Override
    protected ArtistActorConnection buildActorConnectionNewRecord(
            DatabaseTableRecord record) throws InvalidParameterException {
        try{
            UUID   connectionId = record.getUUIDValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME);
            String linkedIdentityPublicKey = record.getStringValue(
                    ActorConnectionDatabaseConstants.
                            ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
            String linkedIdentityActorTypeString = record.getStringValue(
                    ActorConnectionDatabaseConstants.
                            ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME);
            String publicKey = record.getStringValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME);
            String alias = record.getStringValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME);
            String connectionStateString = record.getStringValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME);
            long creationTime = record.getLongValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME);
            long updateTime = record.getLongValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME);
            ConnectionState connectionState = ConnectionState.getByCode(
                    connectionStateString);
            Actors linkedIdentityActorType = Actors.getByCode(
                    linkedIdentityActorTypeString);
            ArtistLinkedActorIdentity actorIdentity = new ArtistLinkedActorIdentity(
                    linkedIdentityPublicKey,
                    linkedIdentityActorType
            );
            byte[] profileImage;

            try {
                profileImage = getProfileImage(publicKey);
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new ArtistActorConnection(
                    connectionId,
                    actorIdentity,
                    publicKey,
                    alias,
                    profileImage,
                    connectionState,
                    creationTime,
                    updateTime
            );
        } catch (final CantGetProfileImageException e) {
            throw new InvalidParameterException(e,
                    "Creating a ArtistActorConnection from Database",
                    "Problem trying to get the profile image.");
        }
    }

    public ArtistActorConnection registerConnection(
            ArtistActorConnection artistActorConnection) throws
            CantRegisterActorConnectionException  , ActorConnectionAlreadyExistsException {
        try {

            boolean connectionExists = actorConnectionExists(
                    artistActorConnection.getLinkedIdentity(),
                    artistActorConnection.getPublicKey(),
                    artistActorConnection.getConnectionId());

            if (connectionExists)
                throw new ActorConnectionAlreadyExistsException(
                        "actorConnection: "+artistActorConnection,
                        "This Connecion is waiting for a response"
                );
            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();
            DatabaseTableRecord entityRecord = actorConnectionsTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(
                    entityRecord   ,
                    artistActorConnection
            );

            actorConnectionsTable.insertRecord(entityRecord);

            return buildActorConnectionNewRecord(entityRecord);

        } catch (final CantInsertRecordException e) {

            throw new CantRegisterActorConnectionException(
                    e,
                    "",
                    "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.");
        } catch (final InvalidParameterException e) {

            throw new CantRegisterActorConnectionException(
                    e,
                    "",
                    "There was an error trying to build an instance of the actor connection.");
        } catch (CantGetActorConnectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actorConnectionExists(ArtistLinkedActorIdentity artistLinkedActorIdentity, String publickey, UUID id) throws CantGetActorConnectionException {
        if (artistLinkedActorIdentity == null)
            throw new CantGetActorConnectionException(
                    null,
                    "",
                    "The artistLinkedActorIdentity is required, can not be null");

        if (publickey == null)
            throw new CantGetActorConnectionException(
                    null,
                    "",
                    "The publicKey is required, can not be null");

        if(id == null)
            throw new CantGetActorConnectionException(
                    null,
                    "",
                    "The connection id is required, can not be null");
        try {

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, artistLinkedActorIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.addFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, artistLinkedActorIdentity.getActorType(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, publickey, DatabaseFilterType.EQUAL);
            actorConnectionsTable.addUUIDFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            for (DatabaseTableRecord record:
                 records) {
                if(!Validate.isObjectNull(record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME))){
                    ConnectionState connectionState = ConnectionState.getByCode(record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME));
                    for (ConnectionState state :
                            ConnectionState.values()) {
                        if (connectionState.equals(state) && !connectionState.equals(ConnectionState.PENDING_REMOTELY_ACCEPTANCE))
                            return true;
                    }
                }

            }
            return !records.isEmpty();

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: "+artistLinkedActorIdentity + " - publicKey: "+publickey,
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method checks if an actor connection exists.
     * @param linkedIdentityPublicKey
     * @param linkedIdentityActorType
     * @param actorPublicKey
     * @return
     * @throws CantGetActorConnectionException
     */
    public List<ArtistActorConnection> getRequestActorConnections(
            String linkedIdentityPublicKey,
            Actors linkedIdentityActorType,
            String actorPublicKey) throws CantGetActorConnectionException {
        if (linkedIdentityPublicKey == null)
            throw new CantGetActorConnectionException(
                null,
                "",
                "The linkedIdentity public key is required, can not be null");

                if(linkedIdentityActorType == null)
                    throw new CantGetActorConnectionException(
                            null,
                            "",
                            "The linkedIdentityActorType is required, can not be null");

                if (actorPublicKey == null)
                    throw new CantGetActorConnectionException(
                        null,
                        "",
                        "The publicKey is required, can not be null");

                try {

                    final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

                    actorConnectionsTable.addStringFilter(
                            ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                            linkedIdentityPublicKey,
                            DatabaseFilterType.EQUAL);
                    actorConnectionsTable.addFermatEnumFilter(

                            ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME,
                            linkedIdentityActorType,
                            DatabaseFilterType.EQUAL);
                    actorConnectionsTable.addStringFilter(
                            ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME,
                            actorPublicKey,
                            DatabaseFilterType.EQUAL);

                    actorConnectionsTable.loadToMemory();

                    final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();
                    List<ArtistActorConnection> actorRecords = new ArrayList<>();
                    for(DatabaseTableRecord record : records){
                        actorRecords.add(buildActorConnectionNewRecord(record));
                    }
                    return actorRecords;
                } catch (final CantLoadTableToMemoryException e) {
                    throw new CantGetActorConnectionException(
                            e,
                            "linkedIdentity: "+linkedIdentityPublicKey + " - publicKey: "+actorPublicKey,
                            "Exception not handled by the plugin, there is a problem in database and I cannot load the table.");
                } catch (InvalidParameterException e) {
                    throw new CantGetActorConnectionException(
                            e,
                            "linkedIdentity: "+linkedIdentityPublicKey + " - publicKey: "+actorPublicKey,
                            "Exception not handled by the plugin, there is a problem getting a value from an enum.");
                }
        }
}
