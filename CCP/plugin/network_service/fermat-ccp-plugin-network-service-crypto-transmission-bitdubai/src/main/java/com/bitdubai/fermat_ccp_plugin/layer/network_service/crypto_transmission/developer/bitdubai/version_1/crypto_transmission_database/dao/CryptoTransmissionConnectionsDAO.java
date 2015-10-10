package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao;

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
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.CryptoTransmissionNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.*;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantSaveCryptoTransmissionMetadatatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionConnectionRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.04..
 */
public class CryptoTransmissionConnectionsDAO {



        private final PluginDatabaseSystem pluginDatabaseSystem;
        private final UUID pluginId            ;

        private Database database;

        public CryptoTransmissionConnectionsDAO(final PluginDatabaseSystem pluginDatabaseSystem,
                                             final UUID                 pluginId            ) {

            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.pluginId             = pluginId            ;
        }

        public void initialize() throws CantInitializeCryptoTransmissionNetworkServiceDatabaseException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(
                        this.pluginId,
                        this.pluginId.toString()
                );

            } catch (DatabaseNotFoundException e) {

                try {

                    CryptoTransmissionNetworkServiceDatabaseFactory databaseFactory = new CryptoTransmissionNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                    database = databaseFactory.createDatabase(
                            pluginId,
                            pluginId.toString()
                    );

                } catch (CantCreateDatabaseException f) {

                    throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
                } catch (Exception z) {

                    throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(CantInitializeCryptoTransmissionNetworkServiceDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
                }

            } catch (CantOpenDatabaseException e) {

                throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
            } catch (Exception e) {

                throw new CantInitializeCryptoTransmissionNetworkServiceDatabaseException(CantInitializeCryptoTransmissionNetworkServiceDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
            }
        }


        public void saveCryptoTransmissionConnection(CryptoTransmissionConnectionRecord cryptoTransmissionConnectionRecord) throws CantSaveCryptoTransmissionMetadatatException {

            try {

                DatabaseTable addressExchangeRequestTable = database.getTable(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);
                DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

                entityRecord = buildDatabaseRecord(entityRecord, cryptoTransmissionConnectionRecord);

                addressExchangeRequestTable.insertRecord(entityRecord);

            } catch (CantInsertRecordException e) {

                throw new CantSaveCryptoTransmissionMetadatatException("", e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
            }
        }


        public CryptoTransmissionConnectionRecord getPendingRequest(UUID transmissionId) throws CantGetCryptoTransmissionMetadataException,
                PendingRequestNotFoundException {

            if (transmissionId == null)
                throw new CantGetCryptoTransmissionMetadataException("",null, "requestId, can not be null","");

                try {

                    DatabaseTable metadataTable = database.getTable(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);

                    metadataTable.setUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

                    metadataTable.loadToMemory();

                    List<DatabaseTableRecord> records = metadataTable.getRecords();


                    if (!records.isEmpty())
                        return buildCryptoTransmissionRecord(records.get(0));
                    else
                        throw new PendingRequestNotFoundException(null, "RequestID: "+transmissionId, "Can not find an address exchange request with the given request id.");


                } catch (CantLoadTableToMemoryException exception) {

                    throw new CantGetCryptoTransmissionMetadataException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
                } catch (InvalidParameterException exception) {

                    throw new CantGetCryptoTransmissionMetadataException("",exception , "Check the cause." ,""                                                                              );
                }
        }




        private CryptoTransmissionConnectionRecord buildCryptoTransmissionRecord(DatabaseTableRecord record) throws InvalidParameterException {

            UUID   id                       = record.getUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME);
            String   actorPublicKey                  = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME);
            String ipkNetworkService    = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME);
            String lastConnection     = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME);

            return new CryptoTransmissionConnectionRecord(
                    id,
                    actorPublicKey,
                    ipkNetworkService,
                    lastConnection
            );
        }


        private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                                 record,
                                                        CryptoTransmissionConnectionRecord cryptoTransmissionConnectionRecord) {

            record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, cryptoTransmissionConnectionRecord.getId());
            record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ACTOR_PUBLIC_KEY_COLUMN_NAME, cryptoTransmissionConnectionRecord.getActorPublicKey());
            record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_IPK_COLUMN_NAME , cryptoTransmissionConnectionRecord.getIpkNetworkService());
            record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_LAST_CONNECTION_COLUMN_NAME   , cryptoTransmissionConnectionRecord.getLastConnection());
            return record;
        }



}
