package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces.NegotiationTransmission;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.database.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions.CantRegisterSendNegotiationTransmissionException;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 26.11.15.
 */
public class NegotiationTransmissionNetworkServiceDatabaseDao {

    private PluginDatabaseSystem pluginDatabaseSystem;

    private UUID pluginId;

    public NegotiationTransmissionNetworkServiceDatabaseDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    Database database;

    /*INITIALIZE DATABASE*/
    public void initialize() throws CantInitializeDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, this.pluginId.toString());
        } catch (DatabaseNotFoundException e) {
            try {
                CommunicationNetworkServiceDatabaseFactory databaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException f) {
                throw new CantInitializeDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {
                throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }
        } catch (CantOpenDatabaseException e) {
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {
            throw new CantInitializeDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /*REGISTER NEW SEND NEGOTIATION TRANSMISSION*/
    public void registerSendNegotiatioTransmission(NegotiationTransmission negotiationTransmission) throws CantRegisterSendNegotiationTransmissionException{
        try {
            DatabaseTable table = this.database.getTable(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();
            loadRecordAsSendNegotiatioTransmission(record,negotiationTransmission);
            table.insertRecord(record);
        } catch (CantInsertRecordException e){
            throw new CantRegisterSendNegotiationTransmissionException ("CAN'T REGISTER IN DATABSE A NEGOTIATION TRANSMISSION", e, "ERROR SEND CONFIRM TO CRYPTO BROKER", "");
        } catch (Exception e){
            throw new CantRegisterSendNegotiationTransmissionException(e.getMessage(), FermatException.wrapException(e), "CAN'T CREATE REGISTER NEGOTIATION TRANSMISSION TO CRYPTO BROKER", "ERROR SEND CONFIRM TO CRYPTO BROKER, UNKNOWN FAILURE.");
        }
    }

    /*PRIVATE*/
    private void loadRecordAsSendNegotiatioTransmission(DatabaseTableRecord record, NegotiationTransmission negotiationTransmission) {
        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_ID_COLUMN_NAME, negotiationTransmission.getTransmissionId());
        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSACTION_ID_COLUMN_NAME, negotiationTransmission.getTransactionId());
        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_ID_COLUMN_NAME, negotiationTransmission.getNegotiationId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_NEGOTIATION_TRANSACTION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_SEND_COLUMN_NAME, negotiationTransmission.getPublicKeyActorSend());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_SEND_TYPE_COLUMN_NAME, negotiationTransmission.getActorSendType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_PUBLIC_KEY_ACTOR_RECEIVE_COLUMN_NAME, negotiationTransmission.getPublicKeyActorReceive());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_ACTOR_RECEIVE_TYPE_COLUMN_NAME, negotiationTransmission.getActorReceiveType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_TYPE_COLUMN_NAME, negotiationTransmission.getTransmissionType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TRANSMISSION_STATE_COLUMN_NAME, negotiationTransmission.getTransmissionState().getCode());
        record.setLongValue(CommunicationNetworkServiceDatabaseConstants.NEGOTIATION_TRANSMISSION_NETWORK_SERVICE_TIMESTAMP_COLUMN_NAME, negotiationTransmission.getTimestamp());
    }



}