/*
 * @#ActorsCatalogDao.java - 2015
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ActorsCatalogDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorsCatalogDao extends AbstractBaseDao<ActorsCatalog> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ActorsCatalogDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ActorsCatalog getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        ActorsCatalog actorsCatalog = new ActorsCatalog();

        try{

            actorsCatalog.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalog.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NAME_COLUMN_NAME));
            actorsCatalog.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ALIAS_COLUMN_NAME));
            actorsCatalog.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME));
            actorsCatalog.setPhoto(Base64.decode(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_PHOTO_COLUMN_NAME), Base64.DEFAULT));
            actorsCatalog.setLastLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME));
            actorsCatalog.setLastLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME));
            actorsCatalog.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME));
            actorsCatalog.setHostedTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME)));
            actorsCatalog.setNodeIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME));


        }catch (Exception e){
            //e.printStackTrace();

            return null;
        }

        return actorsCatalog;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(ActorsCatalog entity) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME,entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_PHOTO_COLUMN_NAME,Base64.encodeToString(entity.getPhoto(), Base64.DEFAULT));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME, entity.getLastLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME,entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME, entity.getHostedTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getNodeIdentityPublicKey());

        return databaseTableRecord;
    }


}
