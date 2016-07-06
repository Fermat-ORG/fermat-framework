package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalogTransaction;

import org.apache.commons.codec.binary.Base64;

import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_TABLE_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ActorsCatalogTransactionDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class ActorsCatalogTransactionDao extends AbstractBaseDao<ActorsCatalogTransaction> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ActorsCatalogTransactionDao(Database dataBase) {
        this(
                dataBase,
                ACTOR_CATALOG_TRANSACTION_TABLE_NAME
        );
    }

    public ActorsCatalogTransactionDao(Database dataBase, String tableName) {

        super(
                dataBase,
                tableName,
                ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME
        );
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ActorsCatalogTransaction getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        ActorsCatalogTransaction actorsCatalogTransaction = new ActorsCatalogTransaction();

        try {

            actorsCatalogTransaction.setHashId(record.getStringValue(ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME));
            actorsCatalogTransaction.setIdentityPublicKey(record.getStringValue(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransaction.setName(record.getStringValue(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME));
            actorsCatalogTransaction.setAlias(record.getStringValue(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME));
            actorsCatalogTransaction.setActorType(record.getStringValue(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME));
            actorsCatalogTransaction.setPhoto(Base64.decodeBase64(record.getStringValue(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME)));
            actorsCatalogTransaction.setThumbnail(Base64.decodeBase64(record.getStringValue(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME)));
            actorsCatalogTransaction.setLastLocation(record.getDoubleValue(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME), record.getDoubleValue(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME));
            actorsCatalogTransaction.setExtraData(record.getStringValue(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME));
            actorsCatalogTransaction.setHostedTimestamp(getTimestampFromLongValue(record.getLongValue(ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME)));
            actorsCatalogTransaction.setLastConnection(getTimestampFromLongValue(record.getLongValue(ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME)));
            actorsCatalogTransaction.setNodeIdentityPublicKey(record.getStringValue(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransaction.setClientIdentityPublicKey(record.getStringValue(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalogTransaction.setTransactionType(record.getStringValue(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME));
            actorsCatalogTransaction.setGenerationTime(getTimestampFromLongValue(record.getLongValue(ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME)));

        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }

        return actorsCatalogTransaction;
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

        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_HASH_ID_COLUMN_NAME                   , entity.getHashId());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_IDENTITY_PUBLIC_KEY_COLUMN_NAME       , entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_NAME_COLUMN_NAME                      , entity.getName());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_ALIAS_COLUMN_NAME                     , entity.getAlias());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_ACTOR_TYPE_COLUMN_NAME                , entity.getActorType());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_PHOTO_COLUMN_NAME                     , Base64.encodeBase64String(entity.getPhoto()));
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_THUMBNAIL_COLUMN_NAME                 , Base64.encodeBase64String(entity.getThumbnail()));

        if (entity.getLastLocation() != null) {
            databaseTableRecord.setDoubleValue(ACTOR_CATALOG_TRANSACTION_LAST_LATITUDE_COLUMN_NAME             , entity.getLastLocation().getLatitude());
            databaseTableRecord.setDoubleValue(ACTOR_CATALOG_TRANSACTION_LAST_LONGITUDE_COLUMN_NAME            , entity.getLastLocation().getLongitude());
        }

        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_EXTRA_DATA_COLUMN_NAME                , entity.getExtraData());
        databaseTableRecord.setLongValue  (ACTOR_CATALOG_TRANSACTION_HOSTED_TIMESTAMP_COLUMN_NAME          , getLongValueFromTimestamp(entity.getHostedTimestamp()));
        databaseTableRecord.setLongValue  (ACTOR_CATALOG_TRANSACTION_LAST_CONNECTION_COLUMN_NAME           , getLongValueFromTimestamp(entity.getLastConnection()));
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME  , entity.getNodeIdentityPublicKey());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getClientIdentityPublicKey());
        databaseTableRecord.setStringValue(ACTOR_CATALOG_TRANSACTION_TRANSACTION_TYPE_COLUMN_NAME          , entity.getTransactionType());
        databaseTableRecord.setLongValue  (ACTOR_CATALOG_TRANSACTION_GENERATION_TIME_COLUMN_NAME           , getLongValueFromTimestamp(entity.getGenerationTime()));

        return databaseTableRecord;
    }
}
