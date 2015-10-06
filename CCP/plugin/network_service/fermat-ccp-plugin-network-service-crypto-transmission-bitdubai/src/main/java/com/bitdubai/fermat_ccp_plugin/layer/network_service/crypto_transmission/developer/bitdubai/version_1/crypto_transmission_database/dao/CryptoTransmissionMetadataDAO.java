package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.dao;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
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
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_database.exceptions.CantInitializeCryptoTransmissionNetworkServiceDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure.CryptoTransmissionMetadataRecord;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.04..
 */
public class CryptoTransmissionMetadataDAO {


    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public CryptoTransmissionMetadataDAO(final PluginDatabaseSystem pluginDatabaseSystem,
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


    public void saveCryptoTransmissionMetadata(CryptoTransmissionMetadata cryptoTransmissionMetadata) throws CantSaveCryptoTransmissionMetadatatException {

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

            entityRecord = buildDatabaseRecord(entityRecord, cryptoTransmissionMetadata);

            addressExchangeRequestTable.insertRecord(entityRecord);

        } catch (CantInsertRecordException e) {

            //throw new CantSaveCryptoTransmissionMetadatatException(e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }
    }


    public CryptoTransmissionMetadata getPendingRequest(UUID transmissionId) throws CantGetCryptoTransmissionMetadataException,
            PendingRequestNotFoundException {

        if (transmissionId == null)
            //throw new CantGetCryptoTransmissionMetadataException(null,"", "requestId, can not be null");

        try {

            DatabaseTable metadataTable = database.getTable(CryptoTransmissionNetworkServiceDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);

            metadataTable.setUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            metadataTable.loadToMemory();

            List<DatabaseTableRecord> records = metadataTable.getRecords();


            if (!records.isEmpty())
                return buildCryptoTransmissionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, "RequestID: "+transmissionId, "Can not find an address exchange request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            //throw new CantGetCryptoTransmissionMetadataException(exception, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (InvalidParameterException exception) {

            //throw new CantGetCryptoTransmissionMetadataException(exception, "", "Check the cause."                                                                                );
        }
        //TODO: cambiar esto con excepciones
        return null;
    }




    private CryptoTransmissionMetadata buildCryptoTransmissionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   transactionId                       = record.getUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME);
        UUID   requestId                  = record.getUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME);
        String cryptoCurrencyString    = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME);
        long amount     = record.getLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME);
        String sender     = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLICK_KEY_COLUMN_NAME);
        String destination      = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        String associatedHash  = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME);
        String description = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME  );

        CryptoCurrency cryptoCurrency = CryptoCurrency                     .getByCode(cryptoCurrencyString              );
        return new CryptoTransmissionMetadataRecord(
                associatedHash,
                amount,
                cryptoCurrency,
                destination,
                description,
                requestId,
                sender,
                transactionId
        );
    }


    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                                 record,
                                                    CryptoTransmissionMetadata cryptoTransmissionMetadata) {

        record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, cryptoTransmissionMetadata.getTransmissionId());
        record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME, cryptoTransmissionMetadata.getRequestId());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoCurrency().getCode());
        record.setLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoAmount());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLICK_KEY_COLUMN_NAME, cryptoTransmissionMetadata.getSenderPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME , cryptoTransmissionMetadata.getDestinationPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME   , cryptoTransmissionMetadata.getAssociatedCryptoTransactionHash());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME  , cryptoTransmissionMetadata.getPaymentDescription());
        return record;
    }

}
