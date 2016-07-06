package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.data.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.entities.NetworkServiceQuery;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.RecordNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryStatus;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.enums.QueryTypes;

import java.util.UUID;

import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_BROADCAST_CODE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_EXECUTION_TIME_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_STATUS_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_TABLE_NAME;
import static com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.constants.NetworkServiceDatabaseConstants.QUERIES_TYPE_COLUMN_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.daos.QueriesDao</code>
 * <p/>
 * Created  by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class QueriesDao extends AbstractBaseDao<NetworkServiceQuery> {

    public QueriesDao(final Database dataBase) {

        super(
                dataBase              ,
                QUERIES_TABLE_NAME    ,
                QUERIES_ID_COLUMN_NAME
        );
    }

    @Override
    protected NetworkServiceQuery getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID                     id                       = UUID.fromString(record.getStringValue(QUERIES_ID_COLUMN_NAME));
        String                   broadcastCode            = record.getStringValue(QUERIES_BROADCAST_CODE_COLUMN_NAME);
        DiscoveryQueryParameters discoveryQueryParameters = DiscoveryQueryParameters.parseContent(record.getStringValue(QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME));
        long                     executionTime            = record.getLongValue(QUERIES_EXECUTION_TIME_COLUMN_NAME);
        QueryTypes               type                     = QueryTypes.getByCode(record.getStringValue(QUERIES_TYPE_COLUMN_NAME));
        QueryStatus              status                   = QueryStatus.getByCode(record.getStringValue(QUERIES_STATUS_COLUMN_NAME));

        return new NetworkServiceQuery(
                id                      ,
                broadcastCode           ,
                discoveryQueryParameters,
                executionTime           ,
                type                    ,
                status
        );
    }

    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(NetworkServiceQuery entity) {

        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        entityRecord.setStringValue(QUERIES_ID_COLUMN_NAME                    , entity.getId().toString());
        entityRecord.setStringValue(QUERIES_BROADCAST_CODE_COLUMN_NAME        , entity.getBroadcastCode());
        entityRecord.setStringValue(QUERIES_DISCOVERY_QUERY_PARAMS_COLUMN_NAME, entity.getDiscoveryQueryParameters().toJson());
        entityRecord.setLongValue  (QUERIES_EXECUTION_TIME_COLUMN_NAME        , entity.getExecutionTime());
        entityRecord.setFermatEnum (QUERIES_TYPE_COLUMN_NAME                  , entity.getType());
        entityRecord.setFermatEnum (QUERIES_STATUS_COLUMN_NAME                , entity.getStatus());

        return entityRecord;
    }

    public void markAsDone(NetworkServiceQuery query) throws CantUpdateRecordDataBaseException, RecordNotFoundException {

        if (query == null)
            throw new IllegalArgumentException("The query is required, can not be null");

        query.setExecutionTime(System.currentTimeMillis());
        query.setStatus(QueryStatus.DONE);
        update(query);

    }

}
