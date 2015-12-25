/*
 * @#ClientsConnectionHistoryDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsConnectionHistory;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ClientsConnectionHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsConnectionHistoryDao extends AbstractBaseDao<ClientsConnectionHistory> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ClientsConnectionHistoryDao(Database dataBase) {
        super(dataBase, CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_TABLE_NAME, CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ClientsConnectionHistory getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        ClientsConnectionHistory entity = new ClientsConnectionHistory();

        entity.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_FIRST_KEY_COLUMN));
        entity.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
        entity.setConnectionTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME)));
        entity.setDeviceType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_DEVICE_TYPE_COLUMN_NAME));
        entity.setLastLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME));
        entity.setLastLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME));
        entity.setStatus(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME));

        return entity;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(ClientsConnectionHistory entity) {

        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_FIRST_KEY_COLUMN, entity.getUuid());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_CONNECTION_TIMESTAMP_COLUMN_NAME, entity.getConnectionTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_DEVICE_TYPE_COLUMN_NAME, entity.getDeviceType());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LATITUDE_COLUMN_NAME, entity.getLastLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_STATUS_COLUMN_NAME, entity.getStatus());

        return databaseTableRecord;
    }
}

