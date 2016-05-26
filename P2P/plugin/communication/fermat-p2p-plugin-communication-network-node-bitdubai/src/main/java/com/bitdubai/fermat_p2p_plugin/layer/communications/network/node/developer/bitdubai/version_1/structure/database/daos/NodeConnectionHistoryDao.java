/*
 * @#NodeConnectionHistoryDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.NodeConnectionHistory;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.NodeConnectionHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeConnectionHistoryDao extends AbstractBaseDao<NodeConnectionHistory> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public NodeConnectionHistoryDao(Database dataBase) {
        super(dataBase, CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_TABLE_NAME, CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected NodeConnectionHistory getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        NodeConnectionHistory entity = new NodeConnectionHistory();

        entity.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME));
        entity.setConnectionTimestamp(getTimestampFromLongValue(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME)));
        entity.setDefaultPort(record.getIntegerValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_DEFAULT_PORT_COLUMN_NAME));
        entity.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
        entity.setIp(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IP_COLUMN_NAME));
        entity.setLastLocation(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME), record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME));
        entity.setStatus(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME));

        return entity;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NodeConnectionHistory entity) {

        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_UUID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getConnectionTimestamp()));
        databaseTableRecord.setIntegerValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_DEFAULT_PORT_COLUMN_NAME, entity.getDefaultPort());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_IP_COLUMN_NAME, entity.getIp());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME, entity.getLastLocation().getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLocation().getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME, entity.getStatus());

        return databaseTableRecord;
    }
}

