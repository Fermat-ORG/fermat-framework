package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantGetTransactionTransmissionException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServiceDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure.BusinessTransactionRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class TransactionTransmissionContractHashDao {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId            ;

    private Database database;

    public TransactionTransmissionContractHashDao(final PluginDatabaseSystem pluginDatabaseSystem,
                                         final UUID                 pluginId           ,
                                         final Database database) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
        this.database = database;
    }

    public void initialize() throws CantInitializeNetworkServiceDatabaseException {
        try {

            database = this.pluginDatabaseSystem.openDatabase(
                    this.pluginId,
                    CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME
            );

        } catch (DatabaseNotFoundException e) {

            try {

                CommunicationNetworkServiceDatabaseFactory databaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);
                database = databaseFactory.createDatabase(
                        pluginId,
                        CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME
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
        return getDataBase().getTable(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);
    }

    public void saveBusinessTransmissionRecord(BusinessTransaction businessTransaction) throws CantInsertRecordDataBaseException {

        try {

            DatabaseTable databaseTable = getDatabaseTable();
            DatabaseTableRecord databaseTableEmptyRecord = databaseTable.getEmptyRecord();

            databaseTableEmptyRecord = buildDatabaseRecord(databaseTableEmptyRecord, businessTransaction);

            databaseTable.insertRecord(databaseTableEmptyRecord);

        } catch (CantInsertRecordException e) {

            throw new CantInsertRecordDataBaseException(CantInsertRecordException.DEFAULT_MESSAGE,e, "Exception not handled by the plugin, there is a problem in database and I cannot insert the record.","");
        }
    }

    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord record,
                                                    BusinessTransaction businessTransaction) {

        record.setUUIDValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, businessTransaction.getTransactionId());
        //if(businessTransaction.getRequestId()!=null) record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME,  businessTransaction.getRequestId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME, businessTransaction.getContractHash());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME, businessTransaction.getContractStatus().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME, businessTransaction.getContractId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME , businessTransaction.getSenderId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME, businessTransaction.getSenderType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME, businessTransaction.getReceiverId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME, businessTransaction.getReceiverType().getCode());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME, businessTransaction.getNegotiationId());
        record.setStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME , businessTransaction.getType().getCode());
        record.setLongValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME, businessTransaction.getTimestamp());

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

            BusinessTransaction businessTransaction = getMetadata(transaction_id);
            businessTransaction.setState(transactionTransmissionStates);


            DatabaseTable addressExchangeRequestTable = database.getTable(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord cryptoTransmissionMetadataRecord = buildDatabaseRecord(entityRecord,businessTransaction);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), cryptoTransmissionMetadataRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetTransactionTransmissionException e) {
            e.printStackTrace();
        }

    }

    public BusinessTransaction getMetadata(UUID transmissionId) throws CantGetTransactionTransmissionException,
            PendingRequestNotFoundException {

        if (transmissionId == null)
            throw new CantGetTransactionTransmissionException("",null, "requestId, can not be null","");

        try {

            DatabaseTable databaseTable = getDatabaseTable();

            databaseTable.setUUIDFilter(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();


            if (!records.isEmpty())
                return buildBusinessTransactionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, "RequestID: "+transmissionId, "Cannot find an address exchange request with the given request id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetTransactionTransmissionException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {

            throw new CantGetTransactionTransmissionException("",exception, "Check the cause.","");
        }
    }

    private BusinessTransaction buildBusinessTransactionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID transactionId = record.getUUIDValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSMISSION_ID_COLUMN_NAME);
        String contractHash = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_HASH_COLUMN_NAME);
        String contractStatus = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_STATUS_COLUMN_NAME);
        String contractId = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_CONTRACT_ID_COLUMN_NAME);
        String senderPublicKey = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String senderType = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_SENDER_TYPE_COLUMN_NAME);
        String receiverPublicKey = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_PUBLIC_KEY_COLUMN_NAME);
        String receiverType = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_RECEIVER_TYPE_COLUMN_NAME);
        String negotiationId = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_NEGOTIATION_ID_COLUMN_NAME);
        String type = record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TRANSACTION_TYPE_COLUMN_NAME);
        long timestamp = record.getLongValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TIMESTAMP_COLUMN_NAME);
        String state= record.getStringValue(CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_STATE_COLUMN_NAME);

        ContractStatus recordContractStatus=ContractStatus.getByCode(contractStatus);
        PlatformComponentType recordReceiverType=PlatformComponentType.getByCode(receiverType);
        PlatformComponentType recordSenderType=PlatformComponentType.getByCode(senderType);
        BusinessTransactionTransactionType recordTransactionType=BusinessTransactionTransactionType.getByCode(type);
        TransactionTransmissionStates transactionTransmissionStates=TransactionTransmissionStates.getByCode(state);

        return new BusinessTransactionRecord(
                contractHash,
                recordContractStatus,
                receiverPublicKey,
                recordReceiverType,
                senderPublicKey,
                recordSenderType,
                contractId,
                negotiationId,
                recordTransactionType,
                timestamp,
                transactionId,
                transactionTransmissionStates
                );

    }

    public void confirmReception(UUID transactionID) throws CantUpdateRecordDataBaseException, PendingRequestNotFoundException, CantGetTransactionTransmissionException {
        try {

            BusinessTransaction businessTransaction = getMetadata(transactionID);

            businessTransaction.confirmRead();

            update(businessTransaction);

        } catch (PendingRequestNotFoundException e) {
            throw new PendingRequestNotFoundException(null, "RequestID: "+transactionID.toString(), "Can not find an address exchange request with the given request id.");
        } catch (CantUpdateRecordDataBaseException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }
    }

    public void update(BusinessTransaction businessTransaction) throws CantUpdateRecordDataBaseException {

        if (businessTransaction == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

            DatabaseTableRecord entityRecord = buildDatabaseRecord(databaseTableRecord,businessTransaction);

            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }

    }

    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<FermatMessage>
     * @throws CantReadRecordDataBaseException
     */
    public List<BusinessTransaction> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<BusinessTransaction> list = null;
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


                BusinessTransaction outgoingTemplateNetworkServiceMessage = buildBusinessTransactionRecord(record);

                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + CommunicationNetworkServiceDatabaseConstants.TRANSACTION_TRANSMISSION_HASH_TABLE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        }

        return list;
    }

}
