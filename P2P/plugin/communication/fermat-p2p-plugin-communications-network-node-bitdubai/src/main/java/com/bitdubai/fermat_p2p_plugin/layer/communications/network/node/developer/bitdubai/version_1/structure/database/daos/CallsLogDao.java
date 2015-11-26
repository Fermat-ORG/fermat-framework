/*
 * @#CallsLogDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.CallsLog;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.CallsLogDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CallsLogDao extends AbstractBaseDao<CallsLog> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public CallsLogDao(Database dataBase) {
        super(dataBase,
                CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_TABLE_NAME,
                CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected CallsLog getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        CallsLog callsLog = new CallsLog();

        try{

            callsLog.setCallId(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_ID_COLUMN_NAME));
            callsLog.setCallTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_TIMESTAMP_COLUMN_NAME)));
            callsLog.setStartTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_START_TIMESTAMP_COLUMN_NAME)));
            callsLog.setFinishTimestamp(new Timestamp(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_FINISH_TIMESTAMP_COLUMN_NAME)));
            callsLog.setStep(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_STEP_COLUMN_NAME));

        }catch (Exception e){
            //e.printStackTrace();
            return null;
        }

        return callsLog;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(CallsLog entity) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_ID_COLUMN_NAME, entity.getCallId());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_CALL_TIMESTAMP_COLUMN_NAME, entity.getCallTimestamp().getTime());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_START_TIMESTAMP_COLUMN_NAME, entity.getStartTimestamp().getTime());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_FINISH_TIMESTAMP_COLUMN_NAME, entity.getFinishTimestamp().getTime());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_STEP_COLUMN_NAME,entity.getStep());

        return databaseTableRecord;
    }
}
