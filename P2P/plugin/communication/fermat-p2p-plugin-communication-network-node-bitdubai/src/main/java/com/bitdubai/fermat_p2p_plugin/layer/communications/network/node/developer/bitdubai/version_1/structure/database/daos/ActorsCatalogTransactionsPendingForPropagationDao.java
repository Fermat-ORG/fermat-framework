package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

import org.apache.commons.codec.binary.Base64;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ActorsCatalogTransactionsPendingForPropagationDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorsCatalogTransactionsPendingForPropagationDao extends AbstractBaseDao<ActorsCatalogTransaction> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ActorsCatalogTransactionsPendingForPropagationDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ActorsCatalogTransaction getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {
        ActorsCatalogTransaction actorsCatalogTransactionsPendingForPropagation = new ActorsCatalogTransaction();

        try{

            actorsCatalogTransactionsPendingForPropagation.setHashId(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ALIAS_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ACTOR_TYPE_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setPhoto(Base64.decodeBase64(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_PHOTO_COLUMN_NAME)));
            actorsCatalogTransactionsPendingForPropagation.setLastLocation(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME), record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_EXTRA_DATA_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setHostedTimestamp(getTimestampFromLongValue(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HOSTED_TIMESTAMP_COLUMN_NAME)));
            actorsCatalogTransactionsPendingForPropagation.setNodeIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransactionsPendingForPropagation.setClientIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
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
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(ActorsCatalogTransaction entity) {
       /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HASH_ID_COLUMN_NAME,entity.getHashId());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_ACTOR_TYPE_COLUMN_NAME, entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_PHOTO_COLUMN_NAME, Base64.encodeBase64String(entity.getPhoto()));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LATITUDE_COLUMN_NAME, entity.getLastLocation().getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLocation().getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_EXTRA_DATA_COLUMN_NAME,entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_HOSTED_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getHostedTimestamp()));
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getNodeIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getClientIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TRANSACTION_TYPE_COLUMN_NAME,entity.getTransactionType());

        return databaseTableRecord;

    }
}
