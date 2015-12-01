package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantGetNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionConnectionRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 * Based on CryptoTransmissionNetworkServiceDatabaseFactory by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 05/10/15.
 */
public class NegotiationTransmissionNetworkServiceConnectionsDatabaseDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public NegotiationTransmissionNetworkServiceConnectionsDatabaseDao(final PluginDatabaseSystem pluginDatabaseSystem,final UUID pluginId){

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        
    }

    public void initialize() throws CantInitializeDatabaseException {

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId,this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CommunicationNetworkServiceDatabaseFactory databaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId,pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeDatabaseException(CantInitializeDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeDatabaseException(CantInitializeDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }

    }

    public void saveCryptoTransmissionConnection(NegotiationTransmissionConnectionRecord transactionTransmissionConnectionRecord) throws CantRegisterSendNegotiationTransmissionException {

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            entityRecord = buildDatabaseRecord(entityRecord, transactionTransmissionConnectionRecord);
            addressExchangeRequestTable.insertRecord(entityRecord);
            
        } catch (CantInsertRecordException e) {
            throw new CantRegisterSendNegotiationTransmissionException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }

    }

    public NegotiationTransmissionConnectionRecord getPendingRequest(UUID transmissionId) throws CantGetNegotiationTransmissionException,
            PendingRequestNotFoundException {
        if (transmissionId == null)
            throw new CantGetNegotiationTransmissionException("",null, "requestId, can not be null","");
        try {

            DatabaseTable metadataTable = database.getTable(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_TABLE_NAME);
            metadataTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);
            metadataTable.loadToMemory();
            List<DatabaseTableRecord> records = metadataTable.getRecords();
            if (!records.isEmpty())
                return buildTransactionTransmissionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, "RequestID: "+transmissionId, "Can not find an address exchange request with the given request id.");

        } catch (CantLoadTableToMemoryException exception) {
            throw new CantGetNegotiationTransmissionException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {
            throw new CantGetNegotiationTransmissionException("",exception , "Check the cause." ,""                                                                              );
        }
    }

    private NegotiationTransmissionConnectionRecord buildTransactionTransmissionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID id = record.getUUIDValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_ID_COLUMN_NAME);
        String actorPublicKey = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String ipkNetworkService = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_IPK_COLUMN_NAME);
        String lastConnection = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_LAST_CONNECTION_COLUMN_NAME);
        return new NegotiationTransmissionConnectionRecord(id,actorPublicKey,ipkNetworkService,lastConnection);

    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,NegotiationTransmissionConnectionRecord transactionTransmissionConnectionRecord){
        
        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_ID_COLUMN_NAME, transactionTransmissionConnectionRecord.getId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME, transactionTransmissionConnectionRecord.getActorPublicKey());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_IPK_COLUMN_NAME, transactionTransmissionConnectionRecord.getIpkNetworkService());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_DETAILS_LAST_CONNECTION_COLUMN_NAME, transactionTransmissionConnectionRecord.getLastConnection());
        return record;
        
    }

}
