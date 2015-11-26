/*
 * @#CheckedActorsHistoryDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import android.util.Base64;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedActorsHistory;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.CheckedActorsHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckedActorsHistoryDao extends AbstractBaseDao<CheckedActorsHistory>  {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public CheckedActorsHistoryDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CheckedActorsHistory getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CheckedActorsHistory checkedActorsHistory = new CheckedActorsHistory();

        try{

            checkedActorsHistory.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_UUID_COLUMN_NAME));
            checkedActorsHistory.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedActorsHistory.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_NAME_COLUMN_NAME));
            checkedActorsHistory.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ALIAS_COLUMN_NAME));
            checkedActorsHistory.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ACTOR_TYPE_COLUMN_NAME));
            checkedActorsHistory.setPhoto(Base64.decode(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_PHOTO_COLUMN_NAME), Base64.DEFAULT));
            checkedActorsHistory.setLastLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LATITUDE_COLUMN_NAME));
            checkedActorsHistory.setLastLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LONGITUDE_COLUMN_NAME));
            checkedActorsHistory.setClientIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CLIENT_IDENTITY_PUBLICKEY_COLUMN_NAME));
            checkedActorsHistory.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_EXTRA_DATA_COLUMN_NAME));
            checkedActorsHistory.setCheckedTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME)));
            checkedActorsHistory.setCheckType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECK_TYPE_COLUMN_NAME));

        }catch (Exception e){

            return null;
        }

        return checkedActorsHistory;

    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(CheckedActorsHistory entity) {
         /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_UUID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_ACTOR_TYPE_COLUMN_NAME,entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_PHOTO_COLUMN_NAME, Base64.encodeToString(entity.getPhoto(), Base64.DEFAULT));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LATITUDE_COLUMN_NAME, entity.getLastLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CLIENT_IDENTITY_PUBLICKEY_COLUMN_NAME, entity.getClientIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_EXTRA_DATA_COLUMN_NAME, entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME, entity.getCheckedTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_CHECK_TYPE_COLUMN_NAME,entity.getCheckType());

        return databaseTableRecord;
    }
}
