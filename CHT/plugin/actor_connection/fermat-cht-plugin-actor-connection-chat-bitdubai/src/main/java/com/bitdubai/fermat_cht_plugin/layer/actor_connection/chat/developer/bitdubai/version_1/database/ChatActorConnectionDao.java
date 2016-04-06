package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatActorConnection;
import com.bitdubai.fermat_cht_api.layer.actor_connection.utils.ChatLinkedActorIdentity;

import java.util.UUID;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatActorConnectionDao extends ActorConnectionDao<ChatLinkedActorIdentity, ChatActorConnection> {

    public ChatActorConnectionDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginDatabaseSystem, pluginFileSystem, pluginId);
    }

    protected ChatActorConnection buildActorConnectionNewRecord(DatabaseTableRecord record) throws InvalidParameterException {
        UUID   connectionId                  = record.getUUIDValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME             );
        String linkedIdentityPublicKey       = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        String linkedIdentityActorTypeString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME);
        String publicKey                     = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME                );
        String alias                         = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME                     );
        String connectionStateString         = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME          );
        long   creationTime                  = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME);
        long   updateTime                    = record.getLongValue  (ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME               );

        ConnectionState connectionState         = ConnectionState.getByCode(connectionStateString);

        Actors linkedIdentityActorType = Actors         .getByCode(linkedIdentityActorTypeString);

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
            throw new InvalidParameterException(
                    e,
                    "",
                    "Problem trying to get the profile image."
            );
        }

        return new ChatActorConnection(
                connectionId   ,
                actorIdentity  ,
                publicKey      ,
                alias          ,
                profileImage   ,
                connectionState,
                creationTime   ,
                updateTime
        );
    }

}
