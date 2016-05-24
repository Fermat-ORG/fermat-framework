package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.ClientsRegistrationHistory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationResult;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationType;

import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_DETAIL_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_DEVICE_TYPE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_LAST_LATITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_LAST_LONGITUDE_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_RESULT_COLUMN_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_TABLE_NAME;
import static com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_TYPE_COLUMN_NAME;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.ClientsRegistrationHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 * Updated by Leon Acosta - (laion.cj91@gmail.com) on 15/04/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientsRegistrationHistoryDao extends AbstractBaseDao<ClientsRegistrationHistory> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public ClientsRegistrationHistoryDao(Database dataBase) {
        super(
                dataBase,
                CLIENTS_REGISTRATION_HISTORY_TABLE_NAME,
                CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME
        );
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected ClientsRegistrationHistory getEntityFromDatabaseTableRecord(final DatabaseTableRecord record) throws InvalidParameterException {

        try {

            return new ClientsRegistrationHistory(
                    record.getUUIDValue(CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME),
                    record.getStringValue(CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                    record.getDoubleValue(CLIENTS_REGISTRATION_HISTORY_LAST_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(CLIENTS_REGISTRATION_HISTORY_LAST_LONGITUDE_COLUMN_NAME),
                    record.getStringValue(CLIENTS_REGISTRATION_HISTORY_DEVICE_TYPE_COLUMN_NAME),
                    getTimestampFromLongValue(record.getLongValue(CLIENTS_REGISTRATION_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME)),
                    RegistrationType.getByCode(record.getStringValue(CLIENTS_REGISTRATION_HISTORY_TYPE_COLUMN_NAME)),
                    RegistrationResult.getByCode(record.getStringValue(CLIENTS_REGISTRATION_HISTORY_RESULT_COLUMN_NAME)),
                    record.getStringValue(CLIENTS_REGISTRATION_HISTORY_DETAIL_COLUMN_NAME)
            );

        } catch (Exception e){
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(final ClientsRegistrationHistory entity) {

        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CLIENTS_REGISTRATION_HISTORY_ID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setStringValue(CLIENTS_REGISTRATION_HISTORY_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getIdentityPublicKey());

        if (entity.getLastLatitude() != null) {
            databaseTableRecord.setDoubleValue(CLIENTS_REGISTRATION_HISTORY_LAST_LATITUDE_COLUMN_NAME, entity.getLastLatitude());
        }

        if (entity.getLastLongitude() != null) {
            databaseTableRecord.setDoubleValue(CLIENTS_REGISTRATION_HISTORY_LAST_LONGITUDE_COLUMN_NAME, entity.getLastLongitude());
        }

        databaseTableRecord.setStringValue(CLIENTS_REGISTRATION_HISTORY_DEVICE_TYPE_COLUMN_NAME        , entity.getDeviceType()                );
        databaseTableRecord.setLongValue  (CLIENTS_REGISTRATION_HISTORY_CHECKED_TIMESTAMP_COLUMN_NAME  , getLongValueFromTimestamp(entity.getCheckedTimestamp()));
        databaseTableRecord.setFermatEnum (CLIENTS_REGISTRATION_HISTORY_TYPE_COLUMN_NAME               , entity.getType()                      );
        databaseTableRecord.setFermatEnum (CLIENTS_REGISTRATION_HISTORY_RESULT_COLUMN_NAME             , entity.getResult()                    );
        databaseTableRecord.setStringValue(CLIENTS_REGISTRATION_HISTORY_DETAIL_COLUMN_NAME             , entity.getDetail()                    );

        return databaseTableRecord;

    }
}

