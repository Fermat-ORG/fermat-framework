/*
 * @#CheckedInClientDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInClient;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.CheckedInClientDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckedInClientDao  extends AbstractBaseDao<CheckedInClient> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public CheckedInClientDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CheckedInClient getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CheckedInClient checkedInClient = new CheckedInClient();

        try{

            checkedInClient.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_UUID_COLUMN_NAME));
            checkedInClient.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedInClient.setLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LATITUDE_COLUMN_NAME));
            checkedInClient.setLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LONGITUDE_COLUMN_NAME));
            checkedInClient.setDeviceType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_DEVICE_TYPE_COLUMN_NAME));
            checkedInClient.setCheckedInTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_CHECKED_IN_TIMESTAMP_COLUMN_NAME)));

        }catch (Exception e){
            return null;
        }

        return checkedInClient;

    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(CheckedInClient entity) {

         /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_UUID_COLUMN_NAME,entity.getUuid());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LATITUDE_COLUMN_NAME, entity.getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_LONGITUDE_COLUMN_NAME, entity.getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_DEVICE_TYPE_COLUMN_NAME, entity.getDeviceType());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_CHECKED_IN_TIMESTAMP_COLUMN_NAME, entity.getCheckedInTimestamp().getTime());

        return databaseTableRecord;
    }
}

