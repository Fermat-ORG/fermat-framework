package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantChangeActorConnectionStateException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;

import java.util.List;
import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 * Edited by Miguel Rincon on 19/04/2016
 */
public class ChatActorConnectionDao extends ActorConnectionDao<ChatActorConnection> {

    private ErrorManager errorManager;
    private final UUID pluginId;

    public ChatActorConnectionDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginDatabaseSystem, pluginFileSystem, pluginId);
        this.pluginId = pluginId;
    }

    public void changeIdAndConnectionState(final UUID oldConnectionId,
                                           final UUID newConnectionId,
                                           final ConnectionState connectionState) throws CantChangeActorConnectionStateException {

        if (oldConnectionId == null)
            throw new CantChangeActorConnectionStateException("", "The oldConnectionId is required, can not be null");

        if (newConnectionId == null)
            throw new CantChangeActorConnectionStateException("", "The newConnectionId is required, can not be null");

        if (connectionState == null)
            throw new CantChangeActorConnectionStateException("", "The connectionState is required, can not be null");

        try {

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            actorConnectionsTable.addUUIDFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, oldConnectionId, DatabaseFilterType.EQUAL);

            final DatabaseTableRecord record = actorConnectionsTable.getEmptyRecord();

            record.setFermatEnum(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME, connectionState);
            record.setUUIDValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, newConnectionId);

            actorConnectionsTable.updateRecord(record);

        } catch (final CantUpdateRecordException e) {

            throw new CantChangeActorConnectionStateException(
                    e,
                    "oldConnectionId: " + oldConnectionId+" | newConnectionId: " + newConnectionId+" | connectionState: " + connectionState,
                    "Exception not handled by the plugin, there is a problem in database and i cannot update the record."
            );
        }
    }

    public void changeId(final UUID oldConnectionId,
                         final UUID newConnectionId) throws CantChangeActorConnectionStateException {

        if (oldConnectionId == null)
            throw new CantChangeActorConnectionStateException("", "The oldConnectionId is required, can not be null");


        if (newConnectionId == null)
            throw new CantChangeActorConnectionStateException("", "The newConnectionId is required, can not be null");

        try {

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            actorConnectionsTable.addUUIDFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, oldConnectionId, DatabaseFilterType.EQUAL);

            final DatabaseTableRecord record = actorConnectionsTable.getEmptyRecord();

            record.setUUIDValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME, newConnectionId);

            actorConnectionsTable.updateRecord(record);

        } catch (final CantUpdateRecordException e) {

            throw new CantChangeActorConnectionStateException(
                    e,
                    "oldConnectionId: " + oldConnectionId+" | newConnectionId: " + newConnectionId,
                    "Exception not handled by the plugin, there is a problem in database and i cannot update the record."
            );
        }
    }

    public void updateChatActorConnection(final ChatActorConnection chatActorConnection){
        final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

        try {

            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, chatActorConnection.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.loadToMemory();

            final DatabaseTableRecord record = actorConnectionsTable.getRecords().get(0);
            record.setStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME, chatActorConnection.getAlias());
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATUS_COLUMN_NAME, chatActorConnection.getStatus());
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATUS_COLUMN_NAME, chatActorConnection.getStatus());

            actorConnectionsTable.updateRecord(record);
        }
        catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
        }

    }

    public void updateChatActorConnectionRequest(final ChatActorConnection chatActorConnection){
        final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

        try {

            actorConnectionsTable.addStringFilter(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME, chatActorConnection.getPublicKey(), DatabaseFilterType.EQUAL);
            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();

            if(records!=null && !records.isEmpty()){
                final DatabaseTableRecord record = records.get(0);
                buildDatabaseRecord(record, chatActorConnection);
                actorConnectionsTable.updateRecord(record);
            }
        }
        catch (CantLoadTableToMemoryException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordException e) {
            e.printStackTrace();
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
        String status = record.getStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATUS_COLUMN_NAME);

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
                city,
                status
        );
    }


    protected ChatActorConnectionDatabaseFactory getActorConnectionDatabaseFactory() {

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
            record.setStringValue(ChatActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_STATUS_COLUMN_NAME, actorConnection.getStatus());

            return record;
        } catch (final Exception e) {

            // TODO add better error management, "throws CantBuildDatabaseRecordException".

            System.err.println("error trying to persist image:" + e.getMessage());
            return null;
        }
    }


}
