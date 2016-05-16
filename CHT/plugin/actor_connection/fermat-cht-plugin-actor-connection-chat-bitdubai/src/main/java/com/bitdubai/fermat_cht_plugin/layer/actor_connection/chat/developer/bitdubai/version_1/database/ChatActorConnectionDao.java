package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionAlreadyExistsException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetActorConnectionException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantInitializeActorConnectionDatabaseException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantRegisterActorConnectionException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatActorConnectionDao extends ActorConnectionDao<ChatLinkedActorIdentity, ChatActorConnection> {

    private ErrorManager errorManager;
    private final UUID pluginId;

    public ChatActorConnectionDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginDatabaseSystem, pluginFileSystem, pluginId);
        this.pluginId = pluginId;
    }

    public ChatActorConnection chatActorConnectionExists(ChatLinkedActorIdentity linkedIdentity,
                                         final String publicKey) throws CantGetActorConnectionException {

        if (linkedIdentity == null)
            throw new CantGetActorConnectionException(null, "", "The linkedIdentity is required, can not be null");

        if (publicKey == null)
            throw new CantGetActorConnectionException(null, "", "The publicKey is required, can not be null");

        try {

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, linkedIdentity.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.addFermatEnumFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, linkedIdentity.getActorType(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            ChatActorConnection chatActorConnection = null;

            if(records != null && !records.isEmpty())
                chatActorConnection = buildActorConnectionNewRecord(records.get(0));

            return chatActorConnection;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: - publicKey: " + publicKey,
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException e) {
            throw new CantGetActorConnectionException(
                    e,
                    "linkedIdentity: - publicKey: " + publicKey,
                    "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }
    }

    public final ChatActorConnection registerChatActorConnection(final ChatActorConnection actorConnection) throws CantRegisterActorConnectionException,
            ActorConnectionAlreadyExistsException {

        try {

            ChatActorConnection oldActorConnection = chatActorConnectionExists(actorConnection.getLinkedIdentity(), actorConnection.getPublicKey());

            if (oldActorConnection != null && oldActorConnection.getConnectionState().equals(ConnectionState.CONNECTED)) {

                throw new ActorConnectionAlreadyExistsException(
                        "actorConnection: " + actorConnection,
                        "The connection already exists..."
                );
            }

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            DatabaseTableRecord entityRecord = actorConnectionsTable.getEmptyRecord();
            entityRecord = buildDatabaseRecord(
                    entityRecord,
                    actorConnection
            );

            if (oldActorConnection != null) {
                DatabaseTableRecord entityRecordOld = actorConnectionsTable.getEmptyRecord();
                entityRecordOld = buildDatabaseRecord(
                        entityRecordOld,
                        oldActorConnection
                );
                actorConnectionsTable.deleteRecord(entityRecordOld);
            }
            actorConnectionsTable.insertRecord(entityRecord);

            return buildActorConnectionNewRecord(entityRecord);

        } catch (final CantInsertRecordException e) {

            throw new CantRegisterActorConnectionException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (final InvalidParameterException e) {

            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to build an instance of the actor connection.");
        } catch (final CantGetActorConnectionException e) {

            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to find if the actor connection exists.");
//        } catch (CantUpdateRecordException e) {
//
//            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to update the actor connection");
        } catch (CantDeleteRecordException e) {
            throw new CantRegisterActorConnectionException(e, "", "There was an error trying to delete the actor.");
        }
    }

    protected ChatActorConnection buildActorConnectionNewRecord(DatabaseTableRecord record) throws InvalidParameterException {
        UUID connectionId = record.getUUIDValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME);
        String linkedIdentityPublicKey = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String linkedIdentityActorTypeString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME);
        String publicKey = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME);
        String alias = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME);
        String connectionStateString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME);
        long creationTime = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME);
        long updateTime = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME);
        String country = record.getStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_COUNTRY_COLUMN_NAME);
        String state = record.getStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATE_LOCALITY_COLUMN_NAME);
        String city = record.getStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CITY_COLUMN_NAME);

        ConnectionState connectionState = ConnectionState.getByCode(connectionStateString);

        Actors linkedIdentityActorType = Actors.getByCode(linkedIdentityActorTypeString);

        ChatLinkedActorIdentity actorIdentity = new ChatLinkedActorIdentity(
                linkedIdentityPublicKey,
                linkedIdentityActorType
        );

        byte[] profileImage;

        try {
            profileImage = getProfileImage(publicKey);
        } catch (FileNotFoundException e) {
            profileImage = new byte[0];
        } catch (CantGetProfileImageException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new InvalidParameterException(
                    e,
                    "",
                    "Problem trying to get the profile image."
            );
        }

        return new ChatActorConnection(
                connectionId,
                actorIdentity,
                publicKey,
                alias,
                profileImage,
                connectionState,
                creationTime,
                updateTime,
                country,
                state,
                city
        );
    }

    @Override
    public void initializeDatabase() throws CantInitializeActorConnectionDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    pluginId,
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME
            );

        } catch (final CantOpenDatabaseException cantOpenDatabaseException) {

            throw new CantInitializeActorConnectionDatabaseException(
                    cantOpenDatabaseException,
                    "databaseName: " + ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME,
                    "There was an error trying to open database."
            );

        } catch (final DatabaseNotFoundException databaseNotFoundException) {

            ChatActorConnectionDatabaseFactory actorConnectionActorConnectionDatabaseFactory = this.getChatActorConnectionDatabaseFactory();

            try {

                database = actorConnectionActorConnectionDatabaseFactory.createDatabase(
                        pluginId,
                        ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME
                );

            } catch (final CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeActorConnectionDatabaseException(
                        cantCreateDatabaseException,
                        "databaseName: " + ActorConnectionDatabaseConstants.ACTOR_CONNECTION_DATABASE_NAME,
                        "There was an error trying to create database."
                );
            }
        }
    }

    protected ChatActorConnectionDatabaseFactory getChatActorConnectionDatabaseFactory() {

        return new ChatActorConnectionDatabaseFactory(pluginDatabaseSystem);
    }

    @Override
    protected DatabaseTableRecord buildDatabaseRecord(final DatabaseTableRecord record,
                                                      final ChatActorConnection actorConnection) {

        try {

            record.setUUIDValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, actorConnection.getConnectionId());
            record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, actorConnection.getLinkedIdentity().getPublicKey());
            record.setFermatEnum(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME, actorConnection.getLinkedIdentity().getActorType());
            record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, actorConnection.getPublicKey());
            record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME, actorConnection.getAlias());
            record.setFermatEnum(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME, actorConnection.getConnectionState());
            record.setLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME, actorConnection.getCreationTime());
            record.setLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME, actorConnection.getUpdateTime());
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_COUNTRY_COLUMN_NAME, actorConnection.getCountry());
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATE_LOCALITY_COLUMN_NAME, actorConnection.getState());
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CITY_COLUMN_NAME, actorConnection.getCity());

            if (actorConnection.getImage() != null && actorConnection.getImage().length > 0)
                persistNewUserProfileImage(actorConnection.getPublicKey(), actorConnection.getImage());

            return record;
        } catch (final Exception e) {

            // TODO add better error management, "throws CantBuildDatabaseRecordException".

            System.err.println("error trying to persist image:" + e.getMessage());
            return null;
        }
    }


}
