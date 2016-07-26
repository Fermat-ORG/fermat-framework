package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.actor_connection.common.database_abstract_classes.ActorConnectionDao;
import com.bitdubai.fermat_api.layer.actor_connection.common.database_common_classes.ActorConnectionDatabaseConstants;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantGetProfileImageException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_broker.utils.CryptoBrokerLinkedActorIdentity;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerActorConnectionDao</code>
 * contains all the methods that interact with the database.
 * Manages the actor connections by storing them on a Database Table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerActorConnectionDao extends ActorConnectionDao<CryptoBrokerLinkedActorIdentity, CryptoBrokerActorConnection> {

    public CryptoBrokerActorConnectionDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                          final PluginFileSystem pluginFileSystem,
                                          final UUID pluginId) {

        super(
                pluginDatabaseSystem,
                pluginFileSystem,
                pluginId
        );
    }

    protected CryptoBrokerActorConnection buildActorConnectionNewRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        try {
            UUID connectionId = record.getUUIDValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_ID_COLUMN_NAME);
            String linkedIdentityPublicKey = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
            String linkedIdentityActorTypeString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME);
            String publicKey = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME);
            String alias = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALIAS_COLUMN_NAME);
            String connectionStateString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME);
            long creationTime = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CREATION_TIME_COLUMN_NAME);
            long updateTime = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_UPDATE_TIME_COLUMN_NAME);

            //Location data
            Double latitude = record.getDoubleValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LATITUDE);
            Double longitude = record.getDoubleValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LONGITUDE);
            //long accuracy = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ACCURACY);
            Long time = record.getLongValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TIME);
            Double altitude = record.getDoubleValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALTITUDE);
            String locationSourceString = record.getStringValue(ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LOCATION_SOURCE);
            LocationSource locationSource;
            if (locationSourceString == null || locationSourceString.isEmpty()) {
                //I'll set a default value in this case
                locationSource = LocationSource.UNKNOWN;
            } else {
                try {
                    locationSource = LocationSource.getByCode(locationSourceString);
                } catch (InvalidParameterException ex) {
                    //There was an error, I'll set a default value
                    locationSource = LocationSource.UNKNOWN;
                }
            }
            DeviceLocation deviceLocation = new DeviceLocation(
                    latitude,
                    longitude,
                    time,
                    altitude,
                    locationSource);


            ConnectionState connectionState = ConnectionState.getByCode(connectionStateString);

            Actors linkedIdentityActorType = Actors.getByCode(linkedIdentityActorTypeString);

            CryptoBrokerLinkedActorIdentity actorIdentity = new CryptoBrokerLinkedActorIdentity(
                    linkedIdentityPublicKey,
                    linkedIdentityActorType
            );

            byte[] profileImage;

            try {
                profileImage = getProfileImage(publicKey);
            } catch (FileNotFoundException e) {
                profileImage = new byte[0];
            }

            return new CryptoBrokerActorConnection(
                    connectionId,
                    actorIdentity,
                    publicKey,
                    alias,
                    profileImage,
                    connectionState,
                    creationTime,
                    updateTime,
                    deviceLocation

            );

        } catch (final CantGetProfileImageException e) {

            throw new InvalidParameterException(
                    e,
                    "",
                    "Problem trying to get the profile image."
            );
        }
    }

    public final void persistLocation(
            CryptoBrokerActorConnection actorConnection) throws CantUpdateRecordException {

        Location location = actorConnection.getLocation();
        if (location == null) {
            //In this version I'll can't handle with this situation
            return;
        }
        try {
            /*boolean connectionExists = actorConnectionExists(
                    actorConnection.getLinkedIdentity(),
                    actorConnection.getPublicKey());

            if(!connectionExists){
                throw new CantGetActorConnectionException(
                        "Actor Connection alias: "+actorConnection.getAlias(),
                        "The actor connection with "+actorConnection.getConnectionId()+" is not persisted in database");
            }*/

            final DatabaseTable actorConnectionsTable = getActorConnectionsTable();

            CryptoBrokerLinkedActorIdentity linkedActorIdentity = actorConnection.getLinkedIdentity();

            actorConnectionsTable.addStringFilter(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                    linkedActorIdentity.getPublicKey(),
                    DatabaseFilterType.EQUAL);
            actorConnectionsTable.addFermatEnumFilter(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LINKED_IDENTITY_ACTOR_TYPE_COLUMN_NAME,
                    linkedActorIdentity.getActorType(),
                    DatabaseFilterType.EQUAL);
            actorConnectionsTable.addStringFilter(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_PUBLIC_KEY_COLUMN_NAME,
                    actorConnection.getPublicKey(),
                    DatabaseFilterType.EQUAL);
            /*actorConnectionsTable.addStringFilter(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_CONNECTION_STATE_COLUMN_NAME,
                    ConnectionState.CONNECTED.getCode(),
                    DatabaseFilterType.EQUAL);*/

            actorConnectionsTable.loadToMemory();

            final List<DatabaseTableRecord> records = actorConnectionsTable.getRecords();
            if (records.isEmpty()) {
                return;
            }
            DatabaseTableRecord record = records.get(0);

            //Latitude
            Double latitude = location.getLatitude();
            if (latitude == null) {
                latitude = 0.0;
            }
            record.setDoubleValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LATITUDE,
                    latitude);
            //Longitude
            Double longitude = location.getLongitude();
            if (longitude == null) {
                longitude = 0.0;
            }
            record.setDoubleValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LONGITUDE,
                    longitude);
            //Time
            Long time = location.getTime();
            if (time == null) {
                time = 0L;
            }
            record.setLongValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_TIME,
                    time);
            //Longitude
            Double altitude = location.getAltitude();
            if (altitude == null) {
                altitude = 0.0;
            }
            record.setDoubleValue(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_ALTITUDE,
                    altitude);
            //Location source
            LocationSource locationSource = location.getSource();
            if (locationSource == null) {
                locationSource = LocationSource.UNKNOWN;
            }
            record.setFermatEnum(
                    ActorConnectionDatabaseConstants.ACTOR_CONNECTIONS_LOCATION_SOURCE,
                    locationSource);
            //Updating record
            actorConnectionsTable.updateRecord(record);

        } /*catch (CantGetActorConnectionException e) {
            throw new CantUpdateRecordException(
                    e,
                    "Updating actor connection record",
                    "Cannot get actor connection from database");
        }*/ catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateRecordException(
                    e,
                    "Updating actor connection record",
                    "Cannot database table");
        }
    }
}
