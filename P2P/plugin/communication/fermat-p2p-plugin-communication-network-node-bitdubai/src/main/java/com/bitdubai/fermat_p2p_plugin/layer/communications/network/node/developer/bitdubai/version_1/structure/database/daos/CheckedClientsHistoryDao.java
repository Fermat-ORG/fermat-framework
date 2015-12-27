/*
 * @#CheckedClientsHistoryDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedClientsHistory;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.CheckedClientsHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckedClientsHistoryDao  extends AbstractBaseDao<CheckedClientsHistory> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public CheckedClientsHistoryDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CheckedClientsHistory getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CheckedClientsHistory checkedClientsHistory = new CheckedClientsHistory();

        try{

            checkedClientsHistory.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_UUID_COLUMN_NAME));
            checkedClientsHistory.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedClientsHistory.setLastLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LATITUDE_COLUMN_NAME));
            checkedClientsHistory.setLastLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LONGITUDE_COLUMN_NAME));
            checkedClientsHistory.setDeviceType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_DEVICE_TYPE_COLUMN_NAME));
            checkedClientsHistory.setCheckedTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME)));
            checkedClientsHistory.setCheckType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECK_TYPE_COLUMN_NAME));

        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }

        return checkedClientsHistory;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(CheckedClientsHistory entity) {

         /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_UUID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LATITUDE_COLUMN_NAME, entity.getLastLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_DEVICE_TYPE_COLUMN_NAME, entity.getDeviceType());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME, entity.getCheckedTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_CLIENTS_HISTORY_CHECK_TYPE_COLUMN_NAME, entity.getCheckType().toString());

        return databaseTableRecord;

    }
}

