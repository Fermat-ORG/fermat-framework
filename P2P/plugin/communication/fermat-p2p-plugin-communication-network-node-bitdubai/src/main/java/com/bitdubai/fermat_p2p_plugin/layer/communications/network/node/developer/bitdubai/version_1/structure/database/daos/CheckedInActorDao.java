/*
 * @#CheckedInActorDao.java - 2015
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
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CheckedInActor;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CheckedInActor getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CheckedInActor checkedInActor = new CheckedInActor();

        try{

            checkedInActor.setIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedInActor.setName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME));
            checkedInActor.setAlias(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME));
            checkedInActor.setActorType(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME));
            checkedInActor.setPhoto(Base64.decodeBase64(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME)));
            checkedInActor.setLatitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME));
            checkedInActor.setLongitude(record.getDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME));
            checkedInActor.setExtraData(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME));
            checkedInActor.setCheckedInTimestamp(getTimestampFromLongValue(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME)));
            checkedInActor.setNsIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
            checkedInActor.setClientIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME));


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

        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NAME_COLUMN_NAME,entity.getName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ALIAS_COLUMN_NAME,entity.getAlias());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_ACTOR_TYPE_COLUMN_NAME,entity.getActorType());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_PHOTO_COLUMN_NAME, Base64.encodeBase64String(entity.getPhoto()));
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME, entity.getLatitude());
        databaseTableRecord.setDoubleValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME, entity.getLongitude());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_EXTRA_DATA_COLUMN_NAME, entity.getExtraData());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CHECKED_IN_TIMESTAMP_COLUMN_NAME, getLongValueFromTimestamp(entity.getCheckedInTimestamp()));
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_NS_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getNsIdentityPublicKey());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getClientIdentityPublicKey());

        return databaseTableRecord;
    }

    public final List<CheckedInActor> findAllNearestTo(final Map<String, Object> filters,
                                                      final Integer             max    ,
                                                      final Integer             offset ,
                                                      final Location point  ) throws CantReadRecordDataBaseException {

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
                    CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LATITUDE_COLUMN_NAME,
                    CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_LONGITUDE_COLUMN_NAME,
                    point,
                    DatabaseFilterOrder.ASCENDING,
                    "NOT_NECESSARY"
            );

            table.loadToMemory();

            final List<DatabaseTableRecord> records = table.getRecords();

            final List<CheckedInActor> list = new ArrayList<>();

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
}

