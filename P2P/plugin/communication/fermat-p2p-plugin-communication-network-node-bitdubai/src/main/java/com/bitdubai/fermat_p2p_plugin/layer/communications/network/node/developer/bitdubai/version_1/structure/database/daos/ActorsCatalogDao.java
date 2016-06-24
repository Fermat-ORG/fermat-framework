/*
 * @#ActorsCatalogDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ActorsCatalog;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>DatabaseConstants</code>
     *
     * @param max     number of records to bring
     * @param offset  pointer to start bringing records.
     * @param point   coordinates
     *
     * @return All entities filtering by the parameters specified.
     *
     * @throws CantReadRecordDataBaseException if something goes wrong.
     *
     */
    public final List<ActorsCatalog> findAllNearestTo(final Map<String, Object> filters,
                                                      final Integer             max    ,
                                                      final Integer             offset ,
                                                      final Location            point  ) throws CantReadRecordDataBaseException {

        if (filters == null || filters.isEmpty())
            throw new IllegalArgumentException("The filters are required, can not be null or empty.");

        try {

            // Prepare the filters
            final DatabaseTable table = getDatabaseTable();

            table.setFilterTop(max.toString());
            table.setFilterOffSet(offset.toString());

            final List<DatabaseTableFilter> tableFilters = new ArrayList<>();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = table.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                tableFilters.add(newFilter);
            }


            // load the data base to memory with filters
            table.setFilterGroup(tableFilters, null, DatabaseFilterOperator.OR);

            table.addNearbyLocationOrder(
                    CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME,
                    CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME,
                    point,
                    DatabaseFilterOrder.ASCENDING,
                    "NOT_NECESSARY"
            );

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<ActorsCatalog> list = new ArrayList<>();

            // Convert into entity objects and add to the list.
            for (DatabaseTableRecord record : records)
                list.add(getEntityFromDatabaseTableRecord(record));

            return list;

        } catch (final CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + super.getTableName(), "The data no exist");
        } catch (final InvalidParameterException e) {

            throw new CantReadRecordDataBaseException(e, "Table Name: " + super.getTableName(), "Invalid parameter found, maybe the enum is wrong.");
        }
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
            actorsCatalog.setPhoto(Base64.decodeBase64(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_PHOTO_COLUMN_NAME)));
            actorsCatalog.setLastLocation(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME), record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME));
            actorsCatalog.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME));
            actorsCatalog.setHostedTimestamp(getTimestampFromLongValue(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME)));
            actorsCatalog.setNodeIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            actorsCatalog.setClientIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME));

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
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_ACTOR_TYPE_COLUMN_NAME, entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_PHOTO_COLUMN_NAME, Base64.encodeBase64String(entity.getPhoto()));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LATITUDE_COLUMN_NAME, entity.getLastLocation().getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLocation().getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_EXTRA_DATA_COLUMN_NAME,entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_HOSTED_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getHostedTimestamp()));
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_NODE_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getNodeIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME,entity.getClientIdentityPublicKey());

        return databaseTableRecord;
    }

}
