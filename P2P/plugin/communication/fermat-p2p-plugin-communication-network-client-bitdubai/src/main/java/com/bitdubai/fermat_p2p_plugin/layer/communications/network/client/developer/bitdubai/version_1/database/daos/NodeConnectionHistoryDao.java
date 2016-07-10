package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.location_system.NetworkNodeCommunicationDeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.database.NetworkClientP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.entities.NodeConnectionHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Class <code>NodeConnectionHistoryDao</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 07/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeConnectionHistoryDao extends AbstractBaseDao<NodeConnectionHistory> {

    public NodeConnectionHistoryDao(final Database dataBase) {

        super(
                dataBase                                ,
                NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_TABLE_NAME      ,
                NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_FIRST_KEY_COLUMN
        );
    }

    public final List<NodeProfile> findAllNodeProfilesNearestTo(final Integer  max   ,
                                                                final Integer  offset,
                                                                final Location point ) throws CantReadRecordDataBaseException {

        try {

            // Prepare the filters
            final DatabaseTable table = getDatabaseTable();

            table.setFilterTop(max.toString());
            table.setFilterOffSet(offset.toString());

            table.addNearbyLocationOrder(
                    NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME,
                    NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME,
                    point,
                    DatabaseFilterOrder.ASCENDING,
                    "NOT_NECESSARY"
            );

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<NodeProfile> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getNodeProfileFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + super.getTableName(), "The data no exist");
        }
    }

    protected NodeProfile getNodeProfileFromDatabaseTableRecord(final DatabaseTableRecord record) {

        try {

            NodeProfile nodeProfile = new NodeProfile();
            nodeProfile.setIdentityPublicKey(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            nodeProfile.setIp(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME));
            nodeProfile.setDefaultPort(record.getIntegerValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME));
            Double latitude = Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME));
            Double longitude = Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME));

            nodeProfile.setLocation(
                    new NetworkNodeCommunicationDeviceLocation(
                          latitude ,
                          longitude,
                          0.0      ,
                          0        ,
                          0.0      ,
                          0        ,
                          LocationSource.UNKNOWN
                    )
            );

            return nodeProfile;

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param record with values from the table
     *
     * @return NodeConnectionHistory setters the values from table
     */
    protected NodeConnectionHistory getEntityFromDatabaseTableRecord(final DatabaseTableRecord record) {

        try {

            NodeConnectionHistory NodeConnectionHistory = new NodeConnectionHistory(
                    record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME),
                    record.getIntegerValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME),
                    Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME)),
                    Double.valueOf(record.getStringValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME)),
                    new Timestamp(record.getLongValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME))
            );

            return NodeConnectionHistory;

        } catch (Exception e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }
    }

    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(final NodeConnectionHistory nodeConnectionHistory) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue (NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME      , nodeConnectionHistory.getIdentityPublicKey()                );
        entityRecord.setStringValue (NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_IP_COLUMN_NAME                       , nodeConnectionHistory.getIp()                               );
        entityRecord.setIntegerValue(NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_DEFAULT_PORT_COLUMN_NAME             , nodeConnectionHistory.getDefaultPort()                      );
        entityRecord.setDoubleValue (NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LATITUDE_COLUMN_NAME                 , nodeConnectionHistory.getLatitude()                         );
        entityRecord.setDoubleValue (NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LONGITUDE_COLUMN_NAME                , nodeConnectionHistory.getLongitude()                        );
        entityRecord.setLongValue   (NetworkClientP2PDatabaseConstants.NODE_CONNECTION_HISTORY_LAST_CONNECTION_TIMESTAMP_COLUMN_NAME, nodeConnectionHistory.getLastConnectionTimestamp().getTime());

        /*
         * return the new table record
         */
        return entityRecord;

    }


}
