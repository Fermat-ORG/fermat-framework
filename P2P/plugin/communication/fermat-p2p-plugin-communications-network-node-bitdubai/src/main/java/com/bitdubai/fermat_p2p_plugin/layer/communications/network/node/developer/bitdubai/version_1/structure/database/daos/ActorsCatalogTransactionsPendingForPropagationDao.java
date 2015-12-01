/*
 * @#ActorsCatalogTransactionsPendingForPropagation.java - 2015
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransactionsPendingForPropagation;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ActorsCatalogTransactionsPendingForPropagationDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorsCatalogTransactionsPendingForPropagationDao extends AbstractBaseDao<ActorsCatalogTransactionsPendingForPropagation> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ActorsCatalogTransactionsPendingForPropagationDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ActorsCatalogTransactionsPendingForPropagation getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {
        ActorsCatalogTransactionsPendingForPropagation actorsCatalogTransactionsPendingForPropagation = new ActorsCatalogTransactionsPendingForPropagation();

        try{

            actorsCatalogTransactionsPendingForPropagation.setHashId(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ALIAS_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ACTOR_TYPE_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setPhoto(Base64.decode(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_PHOTO_COLUMN_NAME), Base64.DEFAULT));
            actorsCatalogTransactionsPendingForPropagation.setLastLatitude(Double.valueOf(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME)));
            actorsCatalogTransactionsPendingForPropagation.setLastLongitude(Double.valueOf(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME)));
            actorsCatalogTransactionsPendingForPropagation.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_EXTRA_DATA_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setHostedTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HOSTED_TIMESTAMP_COLUMN_NAME)));
            actorsCatalogTransactionsPendingForPropagation.setNodeIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setTransactionType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME));


        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }

        return actorsCatalogTransactionsPendingForPropagation;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(ActorsCatalogTransactionsPendingForPropagation entity) {
       /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME,entity.getHashId());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ACTOR_TYPE_COLUMN_NAME,entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_PHOTO_COLUMN_NAME, Base64.encodeToString(entity.getPhoto(), Base64.DEFAULT));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME,entity.getLastLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_EXTRA_DATA_COLUMN_NAME,entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HOSTED_TIMESTAMP_COLUMN_NAME, entity.getHostedTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getNodeIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME,entity.getTransactionType());

        return databaseTableRecord;

    }
}
