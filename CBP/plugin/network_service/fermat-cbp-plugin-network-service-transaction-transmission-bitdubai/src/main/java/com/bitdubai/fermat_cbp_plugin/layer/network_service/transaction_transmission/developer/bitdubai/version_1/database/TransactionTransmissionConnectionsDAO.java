package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantGetTransactionTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.TransactionTransmissionConnectionRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 * Based on CryptoTransmissionNetworkServiceDatabaseFactory by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 05/10/15.
 */
public class TransactionTransmissionConnectionsDAO {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public TransactionTransmissionConnectionsDAO(final PluginDatabaseSystem pluginDatabaseSystem,
                                                 final UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    public void initialize() throws CantInitializeDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    this.pluginId.toString()
            );

        } catch (DatabaseNotFoundException e) {

            try {

                TransactionTransmissionDatabaseFactory databaseFactory = new TransactionTransmissionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        pluginId.toString()
                );

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


    public void saveCryptoTransmissionConnection(TransactionTransmissionConnectionRecord transactionTransmissionConnectionRecord) throws CantUpdateRecordDataBaseException {

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(entityRecord, transactionTransmissionConnectionRecord);

            addressExchangeRequestTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException e) {

            throw new CantUpdateRecordDataBaseException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.", "");
        } catch (Exception e) {

            throw new CantUpdateRecordDataBaseException(CantInitializeDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }


    public TransactionTransmissionConnectionRecord getPendingRequest(UUID transmissionId) throws CantGetTransactionTransmissionException,
            PendingRequestNotFoundException {

        if (transmissionId == null)
            throw new CantGetTransactionTransmissionException("", null, "requestId, can not be null", "");

        try {

            DatabaseTable metadataTable = database.getTable(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);

            metadataTable.addUUIDFilter(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            metadataTable.loadToMemory();

            List<DatabaseTableRecord> records = metadataTable.getRecords();


            if (!records.isEmpty())
                return buildTransactionTransmissionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, new StringBuilder().append("RequestID: ").append(transmissionId).toString(), "Can not find an address exchange request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetTransactionTransmissionException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetTransactionTransmissionException("", exception, "Check the cause.", "");
        } catch (Exception e) {

            throw new CantGetTransactionTransmissionException(CantGetTransactionTransmissionException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }


    private TransactionTransmissionConnectionRecord buildTransactionTransmissionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID id = record.getUUIDValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME);
        String actorPublicKey = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
        String ipkNetworkService = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME);
        String lastConnection = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME);

        return new TransactionTransmissionConnectionRecord(
                id,
                actorPublicKey,
                ipkNetworkService,
                lastConnection
        );
    }


    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    TransactionTransmissionConnectionRecord transactionTransmissionConnectionRecord) {

        record.setUUIDValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, transactionTransmissionConnectionRecord.getId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME, transactionTransmissionConnectionRecord.getActorPublicKey());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME, transactionTransmissionConnectionRecord.getIpkNetworkService());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME, transactionTransmissionConnectionRecord.getLastConnection());
        return record;
    }

}
