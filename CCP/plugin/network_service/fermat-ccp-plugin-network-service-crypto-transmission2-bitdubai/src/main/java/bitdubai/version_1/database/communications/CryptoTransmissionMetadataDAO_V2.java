package bitdubai.version_1.database.communications;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.exceptions.CantReadRecordDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import bitdubai.version_1.exceptions.CantGetCryptoTransmissionMetadataException;
import bitdubai.version_1.exceptions.CantSaveCryptoTransmissionMetadatatException;
import bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import bitdubai.version_1.structure.structure.CryptoTransmissionMessageType;
import bitdubai.version_1.structure.structure.CryptoTransmissionMetadataRecord;
import bitdubai.version_1.structure.structure.CryptoTransmissionResponseMessage;

/**
 * Created by Matias Furszyfer on 2015.10.04..
 */
public class CryptoTransmissionMetadataDAO_V2 {
 ;
    private final String tableType;
    private Database database;

    public CryptoTransmissionMetadataDAO_V2(
                                            final Database database,
                                            String tableType) {

        this.database = database;
        this.tableType = tableType;
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
        return getDataBase().getTable(tableType);
    }


    public boolean saveCryptoTransmissionMetadata(CryptoTransmissionMetadataRecord cryptoTransmissionMetadata) throws CantSaveCryptoTransmissionMetadatatException {

        boolean success = false;
        try {

            if(!existMetadata(cryptoTransmissionMetadata.getTransactionId()))
            {
                DatabaseTable addressExchangeRequestTable = getDatabaseTable();
                DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

                entityRecord = buildDatabaseRecord(entityRecord, cryptoTransmissionMetadata);

                addressExchangeRequestTable.insertRecord(entityRecord);

                success = true;
            }


        } catch (CantInsertRecordException e) {

            throw new CantSaveCryptoTransmissionMetadatatException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }  catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantSaveCryptoTransmissionMetadatatException("",e, "Cant Get Crypto Transmission Metadata Exception","");

        }
        return success;
    }

    public void saveCryptoTransmissionResponse(CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage ) throws CantSaveCryptoTransmissionMetadatatException {
        try {

            if(!existMetadata(cryptoTransmissionResponseMessage.getTransactionId())) {
                DatabaseTable addressExchangeRequestTable = getDatabaseTable();
                DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();

                entityRecord = buildDatabaseRecordResponseMessage(entityRecord, cryptoTransmissionResponseMessage);

                addressExchangeRequestTable.insertRecord(entityRecord);
            }


        } catch (CantInsertRecordException e) {

            throw new CantSaveCryptoTransmissionMetadatatException("",e, "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.","");
        }  catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantSaveCryptoTransmissionMetadatatException("",e, "Cant Get Crypto Transmission Metadata Exception","");

        }
    }




    public CryptoTransmissionMetadataRecord getMetadata(UUID transmissionId) throws CantGetCryptoTransmissionMetadataException,
            PendingRequestNotFoundException {

        if (transmissionId == null)
            throw new CantGetCryptoTransmissionMetadataException("",null, "TransmissionID, can not be null","");

        try {

            DatabaseTable metadataTable = getDatabaseTable();

            metadataTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            metadataTable.loadToMemory();

            List<DatabaseTableRecord> records = metadataTable.getRecords();


            if (!records.isEmpty())
                return buildCryptoTransmissionRecord(records.get(0));
            else
                throw new PendingRequestNotFoundException(null, "TransmissionID: "+transmissionId, "Can not find a transmission metadata with the given transmission id.");


        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetCryptoTransmissionMetadataException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        } catch (InvalidParameterException exception) {

            throw new CantGetCryptoTransmissionMetadataException("",exception, "Check the cause.","");
        }
    }



    public boolean existMetadata(UUID transmissionId) throws CantGetCryptoTransmissionMetadataException{

          try {

            DatabaseTable metadataTable = getDatabaseTable();

            metadataTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, transmissionId, DatabaseFilterType.EQUAL);

            metadataTable.loadToMemory();

            List<DatabaseTableRecord> records = metadataTable.getRecords();


            if (!records.isEmpty())
                return true;
            else
               return false;

        } catch (CantLoadTableToMemoryException exception) {

            throw new CantGetCryptoTransmissionMetadataException("",exception, "Exception not handled by the plugin, there is a problem in database and i cannot load the table.","");
        }
    }
    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>TemplateNetworkServiceDatabaseConstants</code>
     *
     * @return All FermatMessage.
     * @throws CantReadRecordDataBaseException
     * @see CryptoTransmissionNetworkServiceDatabaseConstants
     */
    public List<CryptoTransmissionMetadataRecord> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<CryptoTransmissionMetadataRecord> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable templateTable = getDatabaseTable();
            templateTable.addStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            templateTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 3 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  FermatMessage
                 */
                CryptoTransmissionMetadataRecord outGoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(outGoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " +tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
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
    public List<CryptoTransmissionMetadataRecord> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<CryptoTransmissionMetadataRecord> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 4 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  FermatMessage
                 */
                CryptoTransmissionMetadataRecord outgoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
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
    public List<CryptoTransmissionMetadata> findAllMetadata(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<CryptoTransmissionMetadata> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 4 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  FermatMessage
                 */
                CryptoTransmissionMetadata outgoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
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
    public List<CryptoTransmissionMetadataRecord> findAllNotDone(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<CryptoTransmissionMetadataRecord> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {


            /*
             * 1- Prepare the filters
             */
            DatabaseTable templateTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = templateTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            //NOT EQUALS TO DONE
            templateTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME,CryptoTransmissionProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);


            /*
             * 2 - load the data base to memory with filters
             */
            templateTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            templateTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 4 - Create a list of FermatMessage objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into FermatMessage objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  FermatMessage
                 */
                CryptoTransmissionMetadataRecord outgoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(outgoingTemplateNetworkServiceMessage);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (InvalidParameterException e) {
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, e, "", "invalid parameter");
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }




    /**
     * Method that update an CryptoTransmissionMetadataRecord in the data base.
     *
     * @throws CantUpdateRecordDataBaseException
     */
    public void changeCryptoTransmissionProtocolState(UUID transmission_id, CryptoTransmissionProtocolState cryptoTransmissionProtocolState) throws CantUpdateRecordDataBaseException {

        if (transmission_id == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = getMetadata(transmission_id);
            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(cryptoTransmissionProtocolState);


            DatabaseTable addressExchangeRequestTable = getDatabaseTable();
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord cryptoTransmissionMetadataRecord = buildDatabaseRecord(entityRecord,cryptoTransmissionMetadata);

            /*
             * 2.- Create a new transaction and execute
             */
            //set filter by id
            DatabaseTable transmissionTable =  getDatabaseTable();
            transmissionTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME,cryptoTransmissionMetadata.getTransactionId(),DatabaseFilterType.EQUAL);

            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(transmissionTable, cryptoTransmissionMetadataRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantGetCryptoTransmissionMetadataException e) {
            e.printStackTrace();
        }

    }

    public CryptoTransmissionMetadataRecord changeCryptoTransmissionProtocolStateAndNotificationState(UUID transaction_id, CryptoTransmissionProtocolState cryptoTransmissionProtocolState, CryptoTransmissionMetadataState cryptoTransmissionMetadataState) throws CantUpdateRecordDataBaseException, PendingRequestNotFoundException {
        if (transaction_id == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadata = getMetadata(transaction_id);
            cryptoTransmissionMetadata.changeCryptoTransmissionProtocolState(cryptoTransmissionProtocolState);
            cryptoTransmissionMetadata.changeMetadataState(cryptoTransmissionMetadataState);

            DatabaseTable addressExchangeRequestTable = getDatabaseTable();
            DatabaseTableRecord entityRecord = addressExchangeRequestTable.getEmptyRecord();
            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord cryptoTransmissionMetadataRecord = buildDatabaseRecord(entityRecord,cryptoTransmissionMetadata);

            /*
             * 2.- Create a new transaction and execute
             */
            //set filter by id
            DatabaseTable transmissionTable =  getDatabaseTable();
            transmissionTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, cryptoTransmissionMetadata.getTransactionId(), DatabaseFilterType.EQUAL);

            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(transmissionTable, cryptoTransmissionMetadataRecord);
            getDataBase().executeTransaction(transaction);


            return cryptoTransmissionMetadata;

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Table Name: " + tableType);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (PendingRequestNotFoundException e) {
            throw new PendingRequestNotFoundException(e,"not found","");
        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw new PendingRequestNotFoundException(e,"not found","");
        }


    }


    public void changeTransactionStateAndProtocolState(UUID transactionId,CryptoTransmissionMetadataState cryptoTransmissionMetadataState, CryptoTransmissionProtocolState cryptoTransmissionProtocolState) {
        try {
            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = getMetadata(transactionId);
            cryptoTransmissionMetadataRecord.changeCryptoTransmissionProtocolState(cryptoTransmissionProtocolState);
            cryptoTransmissionMetadataRecord.changeMetadataState(cryptoTransmissionMetadataState);
            update(cryptoTransmissionMetadataRecord);
        } catch (CantGetCryptoTransmissionMetadataException e) {
            e.printStackTrace();
        } catch (PendingRequestNotFoundException e) {
            e.printStackTrace();
        } catch (CantUpdateRecordDataBaseException e) {
            e.printStackTrace();
        }
    }


    public void changeSentNumber(UUID transmission_id, int sentCount) throws CantUpdateRecordDataBaseException {

        try {

            DatabaseTable cryptoTransmissiontTable = getDatabaseTable();

            cryptoTransmissiontTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, transmission_id, DatabaseFilterType.EQUAL);

            cryptoTransmissiontTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoTransmissiontTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                record.setIntegerValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENT_COUNT_COLUMN_NAME, sentCount);


                cryptoTransmissiontTable.updateRecord(record);
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Cant delete record exception.");
        }

    }


    public void delete(UUID transmission_id) throws CantDeleteRecordDataBaseException {

        try {

            DatabaseTable cryptoTransmissiontTable = getDatabaseTable();

            cryptoTransmissiontTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, transmission_id, DatabaseFilterType.EQUAL);

            cryptoTransmissiontTable.loadToMemory();

            List<DatabaseTableRecord> records = cryptoTransmissiontTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                cryptoTransmissiontTable.deleteRecord(record);
            }

        } catch (CantLoadTableToMemoryException e) {

            throw new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (CantDeleteRecordException e) {
            throw new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Cant delete record exception.");
        }

    }


    /**
     * Method that update an entity in the data base.
     *.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord) throws CantUpdateRecordDataBaseException {

        if (cryptoTransmissionMetadataRecord == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = buildDatabaseRecord(databaseTableRecord,cryptoTransmissionMetadataRecord);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTable cryptoTransmissionMetadataTable = getDatabaseTable();

            cryptoTransmissionMetadataTable.addUUIDFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, cryptoTransmissionMetadataRecord.getTransactionId(), DatabaseFilterType.EQUAL);

            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }

    }

    //TODO: actualiza el estado de el flag de visto a true.
    public void confirmReception(UUID transactionID) throws CantUpdateRecordDataBaseException, PendingRequestNotFoundException, CantGetCryptoTransmissionMetadataException {
        try {

            CryptoTransmissionMetadataRecord cryptoTransmissionMetadataRecord = getMetadata(transactionID);

            cryptoTransmissionMetadataRecord.confirmRead();

            update(cryptoTransmissionMetadataRecord);



        } catch (CantGetCryptoTransmissionMetadataException e) {
            throw new CantGetCryptoTransmissionMetadataException("",e, "Check the cause.","");
        } catch (PendingRequestNotFoundException e) {
            throw new PendingRequestNotFoundException(null, "RequestID: "+transactionID.toString(), "Can not find an address exchange request with the given request id.");
        } catch (CantUpdateRecordDataBaseException e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CryptoTransmissionNetworkServiceDatabaseConstants.DATABASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantUpdateRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        }
    }


    public List<CryptoTransmissionMetadataRecord> getPendings() throws CantReadRecordDataBaseException {
        return findAll(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME,"false");
    }



    public  List<CryptoTransmissionMetadataRecord> getNotSentRecord() throws CantReadRecordDataBaseException {

        try {

            List<CryptoTransmissionMetadataRecord> list = new ArrayList<>();

            DatabaseTable cryptoTransmissiontTable = getDatabaseTable();

            cryptoTransmissiontTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            cryptoTransmissiontTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);

            cryptoTransmissiontTable.loadToMemory();


            for (DatabaseTableRecord record : cryptoTransmissiontTable.getRecords()) {
                CryptoTransmissionMetadataRecord outgoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);
                list.add(outgoingTemplateNetworkServiceMessage);
            }

            return list;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Cant get crypto transmission record data.");

        }

    }

    public List<CryptoTransmissionMetadataRecord> getNotSentRecord(String remoteIdentityPublicKey) throws CantReadRecordDataBaseException {
        try {

            List<CryptoTransmissionMetadataRecord> list = new ArrayList<>();

            DatabaseTable cryptoTransmissiontTable =getDatabaseTable();

            cryptoTransmissiontTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME,remoteIdentityPublicKey,DatabaseFilterType.EQUAL);
            cryptoTransmissiontTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.PRE_PROCESSING_SEND.getCode(), DatabaseFilterType.NOT_EQUALS);
            cryptoTransmissiontTable.addStringFilter(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, CryptoTransmissionProtocolState.DONE.getCode(), DatabaseFilterType.NOT_EQUALS);

            cryptoTransmissiontTable.loadToMemory();


            for (DatabaseTableRecord record : cryptoTransmissiontTable.getRecords()) {
                CryptoTransmissionMetadataRecord outgoingTemplateNetworkServiceMessage = buildCryptoTransmissionRecord(record);
                list.add(outgoingTemplateNetworkServiceMessage);
            }

            return list;

        } catch (CantLoadTableToMemoryException e) {

            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");

        } catch (InvalidParameterException e) {
            throw new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE,e, "", "Cant get crypto transmission record data.");

        }
    }

    public void doneTransaction(UUID transactionId) throws CantUpdateRecordDataBaseException, PendingRequestNotFoundException {
        changeCryptoTransmissionProtocolStateAndNotificationState(
                transactionId,
                CryptoTransmissionProtocolState.DONE,
                CryptoTransmissionMetadataState.CREDITED_IN_DESTINATION_WALLET);
    }


    private CryptoTransmissionMetadataRecord buildCryptoTransmissionRecord(DatabaseTableRecord record) throws InvalidParameterException {

        UUID   transactionId                       = record.getUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME);
        UUID   requestId                  = record.getUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME);
        String cryptoCurrencyString    = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME);
        long amount     = record.getLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME);
        String sender     = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME);
        String destination      = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME);
        String associatedHash  = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME);
        String description = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME  );
        String state = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME);
        String metadataNotificationState = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_NOTIFICATION_DESCRIPTOR_COLUMN_NAME);

        String type = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TYPE_COLUMN_NAME);
        String pending = record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME);
        long timestamp = record.getLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TIMESTAMP_COLUMN_NAME);
        int sentCount = record.getIntegerValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENT_COUNT_COLUMN_NAME);

        CryptoTransmissionMessageType cryptoTransmissionMessageType =  CryptoTransmissionMessageType.getByCode(record.getStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_MESSAGE_TYPE_COLUMN_NAME));


        CryptoCurrency cryptoCurrency = CryptoCurrency                     .getByCode(cryptoCurrencyString);

        CryptoTransmissionProtocolState cryptoTransmissionProtocolState = CryptoTransmissionProtocolState.getByCode(state);

        CryptoTransmissionMetadataType cryptoTransmissionType = CryptoTransmissionMetadataType.getByCode(type);

        CryptoTransmissionMetadataState notificationState = CryptoTransmissionMetadataState.getByCode(metadataNotificationState);


        return new CryptoTransmissionMetadataRecord(
                transactionId,
                cryptoTransmissionMessageType,
                requestId,
                cryptoCurrency,
                amount,
                sender,
                destination,
                associatedHash,
                description,
                cryptoTransmissionProtocolState,
                cryptoTransmissionType,
                timestamp,
                Boolean.getBoolean(pending),
                sentCount,
                notificationState


        );

    }

    private DatabaseTableRecord buildDatabaseRecordResponseMessage(DatabaseTableRecord record, CryptoTransmissionResponseMessage cryptoTransmissionResponseMessage) {


        record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, cryptoTransmissionResponseMessage.getTransactionId());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME, cryptoTransmissionResponseMessage.getSenderPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME , cryptoTransmissionResponseMessage.getDestinationPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, cryptoTransmissionResponseMessage.getCryptoTransmissionProtocolState().getCode());
        //record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataStates().getCode());

        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TYPE_COLUMN_NAME, cryptoTransmissionResponseMessage.getCryptoTransmissionMetadataType().getCode());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, String.valueOf(cryptoTransmissionResponseMessage.isPendigToRead()));
        record.setIntegerValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENT_COUNT_COLUMN_NAME, cryptoTransmissionResponseMessage.getSentCount());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_MESSAGE_TYPE_COLUMN_NAME,cryptoTransmissionResponseMessage.getCryptoTransmissionMessageType().getCode());



        return record;
    }


    private DatabaseTableRecord buildDatabaseRecord(DatabaseTableRecord                                 record,
                                                    CryptoTransmissionMetadataRecord cryptoTransmissionMetadata) {

        record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TRANSMISSION_ID_COLUMN_NAME, cryptoTransmissionMetadata.getTransactionId());
        if(cryptoTransmissionMetadata.getRequestId()!=null) record.setUUIDValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_REQUEST_ID_COLUMN_NAME,  cryptoTransmissionMetadata.getRequestId());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_CURRENCY_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoCurrency().getCode());
        record.setLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_CRYPTO_AMOUNT_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoAmount());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENDER_PUBLIC_KEY_COLUMN_NAME, cryptoTransmissionMetadata.getSenderPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_DESTINATION_PUBLIC_KEY_COLUMN_NAME , cryptoTransmissionMetadata.getDestinationPublicKey());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_ASSOCIATED_CRYPTO_TRANSACTION_HASH_COLUMN_NAME   , cryptoTransmissionMetadata.getAssociatedCryptoTransactionHash());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PAYMENT_DESCRIPTION_COLUMN_NAME  , cryptoTransmissionMetadata.getPaymentDescription());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_STATUS_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoTransmissionProtocolState().getCode());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_NOTIFICATION_DESCRIPTOR_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoTransmissionMetadataStates().getCode());

        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TYPE_COLUMN_NAME, cryptoTransmissionMetadata.getCryptoTransmissionMetadataType().getCode());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_PENDING_FLAG_COLUMN_NAME, String.valueOf(cryptoTransmissionMetadata.isPendigToRead()));
        record.setLongValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_TIMESTAMP_COLUMN_NAME, cryptoTransmissionMetadata.getTimestamp());
        record.setIntegerValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_SENT_COUNT_COLUMN_NAME, cryptoTransmissionMetadata.getSentCount());
        record.setStringValue(CryptoTransmissionNetworkServiceDatabaseConstants.CRYPTO_TRANSMISSION_METADATA_MESSAGE_TYPE_COLUMN_NAME,cryptoTransmissionMetadata.getCryptoTransmissionMessageType().getCode());


        return record;
    }



}
