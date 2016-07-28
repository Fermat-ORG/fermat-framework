package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantGetTransactionTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionMetadataRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 * Updated by Gabriel Araujo (gabe_512@hotmail.com) on 10/02/16.
 */
public class TransactionTransmissionContractHashDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;

    private Database database;

    public TransactionTransmissionContractHashDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                                  final UUID pluginId,
                                                  final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
        this.database = database;
    }

    public void initialize() throws CantInitializeNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                TransactionTransmissionDatabaseFactory databaseFactory = new TransactionTransmissionDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME
                );

            } catch (CantCreateDatabaseException f) {

                throw new CantInitializeNetworkServiceDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, f, "", "There is a problem and i cannot create the database.");
            } catch (Exception z) {

                throw new CantInitializeNetworkServiceDatabaseException(CantInitializeNetworkServiceDatabaseException.DEFAULT_MESSAGE, z, "", "Generic Exception.");
            }

        } catch (CantOpenDatabaseException e) {

            throw new CantInitializeNetworkServiceDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (Exception e) {

            throw new CantInitializeNetworkServiceDatabaseException(CantInitializeNetworkServiceDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Exception.");
        }
    }

    /**
     * Return the Database
     *
     * @return Database
     */
    Database getDataBase() {
        return database;
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);
    }

    public void saveBusinessTransmissionRecord(BusinessTransactionMetadata businessTransactionMetadata) throws CantInsertRecordDataBaseException {

        try {

            DatabaseTable databaseTable = getDatabaseTable();
            DatabaseTableRecord databaseTableEmptyRecord = databaseTable.getEmptyRecord();

            databaseTableEmptyRecord = buildDatabaseRecord(databaseTableEmptyRecord, businessTransactionMetadata);

            databaseTable.insertRecord(databaseTableEmptyRecord);

        } catch (CantInsertRecordException e) {

            throw new CantInsertRecordDataBaseException(CantInsertRecordException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.", "");
        } catch (Exception e) {
            throw new CantInsertRecordDataBaseException(CantInsertRecordException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.", "");
        }
    }

    public void saveBusinessTransmissionRecord(BusinessTransactionMetadata businessTransactionMetadata, UUID transmissionId) throws CantInsertRecordDataBaseException {

        try {

            DatabaseTable databaseTable = getDatabaseTable();
            DatabaseTableRecord databaseTableEmptyRecord = databaseTable.getEmptyRecord();

            databaseTableEmptyRecord = buildDatabaseRecord(databaseTableEmptyRecord, businessTransactionMetadata, transmissionId);

            databaseTable.insertRecord(databaseTableEmptyRecord);

        } catch (CantInsertRecordException e) {

            throw new CantInsertRecordDataBaseException(CantInsertRecordException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.", "");
        } catch (Exception e) {
            throw new CantInsertRecordDataBaseException(CantInsertRecordException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.", "");
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    BusinessTransactionMetadata businessTransactionMetadata) {

        record.setUUIDValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, businessTransactionMetadata.getTransactionId());
        //if(businessTransactionMetadata.getRequestId()!=null) record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME,  businessTransactionMetadata.getRequestId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME, businessTransactionMetadata.getContractHash());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME, businessTransactionMetadata.getContractTransactionStatus().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME, businessTransactionMetadata.getContractId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME, businessTransactionMetadata.getSenderId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME, businessTransactionMetadata.getSenderType().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME, businessTransactionMetadata.getReceiverId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME, businessTransactionMetadata.getReceiverType().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME, businessTransactionMetadata.getNegotiationId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME, businessTransactionMetadata.getType().getCode());
        record.setLongValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME, businessTransactionMetadata.getTimestamp());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME, businessTransactionMetadata.getState().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, Boolean.toString(businessTransactionMetadata.isPendingToRead()));
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_REMOTE_BUSINESS_TRANSACTION, businessTransactionMetadata.getRemoteBusinessTransaction().getCode());

        return record;
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    BusinessTransactionMetadata businessTransactionMetadata,
                                                    UUID transmissionId) {

        record.setUUIDValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, transmissionId);
        //if(businessTransactionMetadata.getRequestId()!=null) record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME,  businessTransactionMetadata.getRequestId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME, businessTransactionMetadata.getContractHash());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME, businessTransactionMetadata.getContractTransactionStatus().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME, businessTransactionMetadata.getContractId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME, businessTransactionMetadata.getSenderId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME, businessTransactionMetadata.getSenderType().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME, businessTransactionMetadata.getReceiverId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME, businessTransactionMetadata.getReceiverType().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME, businessTransactionMetadata.getNegotiationId());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME, businessTransactionMetadata.getType().getCode());
        record.setLongValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME, businessTransactionMetadata.getTimestamp());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME, businessTransactionMetadata.getState().getCode());
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_PENDING_FLAG_COLUMN_NAME, Boolean.toString(businessTransactionMetadata.isPendingToRead()));
        record.setStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_REMOTE_BUSINESS_TRANSACTION, businessTransactionMetadata.getRemoteBusinessTransaction().getCode());

        return record;
    }

    /**
     * Method that update an CryptoTransmissionMetadata in the data base.
     *
     * @throws CantUpdateRecordDataBaseException
     */
    public void changeState(UUID transaction_id, TransactionTransmissionStates transactionTransmissionStates) throws CantUpdateRecordDataBaseException {

        if (transaction_id == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            BusinessTransactionMetadata businessTransactionMetadata;

            try {
                businessTransactionMetadata = getMetadata(transaction_id);
            } catch (Exception e) {
                System.out.println(new StringBuilder().append("Cannot find ").append(transaction_id).toString());
                return;
            }

            businessTransactionMetadata.setState(transactionTransmissionStates);


            DatabaseTable addressExchangeRequestTable = database.getTable(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord cryptoTransmissionMetadataRecord = buildDatabaseRecord(entityRecord, businessTransactionMetadata);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), cryptoTransmissionMetadataRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Table Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = contextBuilder.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } /*catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetTransactionTransmissionException e) {
            e.printStackTrace();
        }*/ catch (Exception e) {
            e.printStackTrace();
            throw new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and I cannot update the record.", "");
        }

    }

    public BusinessTransactionMetadata getMetadata(UUID transmissionId) throws CantGetTransactionTransmissionException,
            PendingRequestNotFoundException {

        if (transmissionId == null)
            throw new CantGetTransactionTransmissionException("", null, "requestId, can not be null", "");

        try {

            DatabaseTable databaseTable = getDatabaseTable();

            databaseTable.addUUIDFilter(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();


            if (!records.isEmpty())
                return buildBusinessTransactionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, new StringBuilder().append("RequestID: ").append(transmissionId).toString(), "Cannot find an address exchange request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetTransactionTransmissionException("", exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        } catch (InvalidParameterException exception) {

            throw new CantGetTransactionTransmissionException("", exception, "Check the cause.", "");
        } catch (Exception e) {

            throw new CantGetTransactionTransmissionException(CantGetTransactionTransmissionException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }

    private BusinessTransactionMetadata buildBusinessTransactionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID transactionId = record.getUUIDValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME);
        String contractHash = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME);
        String contractStatus = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME);
        String contractId = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME);
        String senderPublicKey = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String senderType = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME);
        String receiverPublicKey = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        String receiverType = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME);
        String negotiationId = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME);
        String type = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME);
        long timestamp = record.getLongValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME);
        String state = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME);

        ContractTransactionStatus recordContractStatus = ContractTransactionStatus.getByCode(contractStatus);
        PlatformComponentType recordReceiverType = PlatformComponentType.getByCode(receiverType);
        PlatformComponentType recordSenderType = PlatformComponentType.getByCode(senderType);
        BusinessTransactionTransactionType recordTransactionType = BusinessTransactionTransactionType.getByCode(type);
        TransactionTransmissionStates transactionTransmissionStates = TransactionTransmissionStates.getByCode(state);
        Plugins remoteBusinessTransaction;
        String pluginCode = record.getStringValue(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_REMOTE_BUSINESS_TRANSACTION);
        if (pluginCode == null || pluginCode.isEmpty()) {
            //For now, I'll put transaction transmission
            remoteBusinessTransaction = Plugins.TRANSACTION_TRANSMISSION;
        } else {
            try {
                remoteBusinessTransaction = Plugins.getByCode(pluginCode);
            } catch (InvalidParameterException exception) {
                //If the code is invalid, I''l set the Default.
                remoteBusinessTransaction = Plugins.TRANSACTION_TRANSMISSION;
            }
        }


        return new BusinessTransactionMetadataRecord(
                contractHash,
                recordContractStatus,
                senderPublicKey,
                recordReceiverType,
                receiverPublicKey,
                recordSenderType,
                contractId,
                negotiationId,
                recordTransactionType,
                timestamp,
                transactionId,
                transactionTransmissionStates,
                remoteBusinessTransaction
        );

    }

    public void confirmReception(UUID transactionID) throws CantUpdateRecordDataBaseException, PendingRequestNotFoundException, CantGetTransactionTransmissionException {
        try {
            System.out.print(new StringBuilder().append("\n2)transactionId: ").append(transactionID).append("\n").toString());
            BusinessTransactionMetadata businessTransactionMetadata = getMetadata(transactionID);

            businessTransactionMetadata.confirmRead();

            update(businessTransactionMetadata);

        } catch (PendingRequestNotFoundException e) {
            throw new PendingRequestNotFoundException(null, new StringBuilder().append("RequestID: ").append(transactionID.toString()).toString(), "Can not find an address exchange request with the given request id.");
        } catch (CantUpdateRecordDataBaseException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Database Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = stringBuilder.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (CantGetTransactionTransmissionException e) {
            CantGetTransactionTransmissionException cantGetTransactionTransmissionException = new CantGetTransactionTransmissionException(CantGetTransactionTransmissionException.DEFAULT_MESSAGE, e, "", "Exception that the plugin was unable to handle");
            throw cantGetTransactionTransmissionException;
        } catch (Exception e) {

            throw new CantGetTransactionTransmissionException(CantGetTransactionTransmissionException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }
    }

    public void update(BusinessTransactionMetadata businessTransactionMetadata) throws CantUpdateRecordDataBaseException {

        if (businessTransactionMetadata == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

//            DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();
//
//            DatabaseTableRecord entityRecord = buildDatabaseRecord(databaseTableRecord, businessTransactionMetadata);
//            DatabaseTableFilter filter = getDatabaseTable().getEmptyTableFilter();
//            filter.setType(DatabaseFilterType.EQUAL);
//            filter.setValue(businessTransactionMetadata.getTransactionId().toString());
//            filter.setColumn(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_FIRST_KEY_COLUMN);
//
//            System.out.print("\n3)transactionId: "+ businessTransactionMetadata.getTransactionId().toString()+". COLUMN: "+TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_FIRST_KEY_COLUMN+"\n");
//            /*
//             * 2.- Create a new transaction and execute
//             */
//            DatabaseTransaction transaction = getDataBase().newTransaction();
//            getDatabaseTable().addStringFilter(filter.getColumn(), filter.getValue(), filter.getType());
//            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
//            getDataBase().executeTransaction(transaction);

            //TODO YORDIN: se cambio el filter, ya que estaba editando todas las transacciones
            DatabaseTable incomingNotificationtable = getDatabaseTable();

            DatabaseTableRecord emptyRecord = getDatabaseTable().getEmptyRecord();

            DatabaseTableRecord entityRecord = buildDatabaseRecord(emptyRecord, businessTransactionMetadata);

            DatabaseTransaction transaction = database.newTransaction();
            incomingNotificationtable.addUUIDFilter(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, businessTransactionMetadata.getTransactionId(), DatabaseFilterType.EQUAL);

            transaction.addRecordToUpdate(incomingNotificationtable, entityRecord);
            database.executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Database Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuilder.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (Exception e) {

            throw new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }

    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @return List<FermatMessage>
     * @throws CantReadRecordDataBaseException
     */
    public List<BusinessTransactionMetadata> findAllToSend() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME,
                TransactionTransmissionStates.PRE_PROCESSING_SEND.getCode());
        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<BusinessTransactionMetadata> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();
            List<DatabaseTableRecord> records = templateTable.getRecords();

            list = new ArrayList<>();
            list.clear();

            for (DatabaseTableRecord record : records) {


                BusinessTransactionMetadata outgoingTemplateNetworkServiceMessage = buildBusinessTransactionRecord(record);

                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Table Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = contextBuilder.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        } catch (Exception e) {

            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }

        return list;
    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<FermatMessage>
     * @throws CantReadRecordDataBaseException
     */
    public List<BusinessTransactionMetadata> findAllToReceive(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<BusinessTransactionMetadata> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();
            List<DatabaseTableRecord> records = templateTable.getRecords();

            list = new ArrayList<>();
            list.clear();

            for (DatabaseTableRecord record : records) {


                BusinessTransactionMetadata outgoingTemplateNetworkServiceMessage = buildBusinessTransactionRecord(record);

                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Table Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = stringBuilder.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        } catch (Exception e) {

            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }

        return list;
    }

    public void changeState(BusinessTransactionMetadata businessTransactionMetadata) throws CantUpdateRecordDataBaseException {

        if (businessTransactionMetadata == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTable addressExchangeRequestTable = database.getTable(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord cryptoTransmissionMetadataRecord = buildDatabaseRecord(entityRecord, businessTransactionMetadata);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), cryptoTransmissionMetadataRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuilder contextBuilder = new StringBuilder();
            contextBuilder.append("Table Name: ").append(TransactionTransmissionNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = contextBuilder.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (Exception e) {

            throw new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.", "");
        }

    }


}
