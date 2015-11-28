/*
 * @#CheckedInActorDao.java - 2015
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.CheckedInActorDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CheckedInActorDao  extends AbstractBaseDao<CheckedInActor> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public CheckedInActorDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CheckedInActor getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CheckedInActor checkedInActor = new CheckedInActor();

        try{

            checkedInActor.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_UUID_COLUMN_NAME));
            checkedInActor.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedInActor.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME));
            checkedInActor.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME));
            checkedInActor.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME));
            checkedInActor.setPhoto(Base64.decode(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME), Base64.DEFAULT));
            checkedInActor.setLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME));
            checkedInActor.setLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME));
            checkedInActor.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME));
            checkedInActor.setCheckedInTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME)));
            checkedInActor.setNsIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME));

        }catch (Exception e){
            return null;

        }

        return checkedInActor;

    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(CheckedInActor entity) {

         /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_UUID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME,entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME, Base64.encodeToString(entity.getPhoto(), Base64.DEFAULT));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME, entity.getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME, entity.getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME,entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME, entity.getCheckedInTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getNsIdentityPublicKey());

        return databaseTableRecord;
    }
}

