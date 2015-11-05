/*
 * @#OutgoingMessageDataAccessObject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.database.communications;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.DigitalAssetMetadataTransactionImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingMessageDao</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gn¡mail.com) on 15/10/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DigitalAssetMetaDataTransactionDao {

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public DigitalAssetMetaDataTransactionDao(Database dataBase) {
        super();
        this.dataBase = dataBase;
    }

    /**
     * Return the Database
     *
     * @return Database
     */
    Database getDataBase() {
        return dataBase;
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseTable() {
        return getDataBase().getTable(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_TABLE_NAME);
    }

    /**
     * Method that find an DigitalAssetMetadataTransactionImpl by id in the data base.
     *
     * @param id Long id.
     * @return DigitalAssetMetadataTransactionImpl found.
     * @throws CantReadRecordDataBaseException
     */
    public DigitalAssetMetadataTransactionImpl findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.setStringFilter(CommunicationNetworkServiceDatabaseConstants.OUTGOING_MESSAGES_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into DigitalAssetMetadataTransactionImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  DigitalAssetMetadataTransactionImpl
                 */
                digitalAssetMetadataTransactionImpl = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        return digitalAssetMetadataTransactionImpl;
    }

    /**
     * Method that list the all entities on the data base.
     *
     * @return All DigitalAssetMetadataTransactionImpl.
     * @throws CantReadRecordDataBaseException
     */
    public List<DigitalAssetMetadataTransactionImpl> findAll() throws CantReadRecordDataBaseException {


        List<DigitalAssetMetadataTransactionImpl> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            DatabaseTable networkIntraUserTable = getDatabaseTable();
            networkIntraUserTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = networkIntraUserTable.getRecords();

            /*
             * 3 - Create a list of DigitalAssetMetadataTransactionImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into DigitalAssetMetadataTransactionImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  DigitalAssetMetadataTransactionImpl
                 */
                DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(digitalAssetMetadataTransactionImpl);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @return All DigitalAssetMetadataTransactionImpl.
     * @throws CantReadRecordDataBaseException
     * @see CommunicationNetworkServiceDatabaseConstants
     */
    public List<DigitalAssetMetadataTransactionImpl> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<DigitalAssetMetadataTransactionImpl> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            DatabaseTable templateTable = getDatabaseTable();
            templateTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            templateTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = templateTable.getRecords();

            /*
             * 3 - Create a list of DigitalAssetMetadataTransactionImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into DigitalAssetMetadataTransactionImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  DigitalAssetMetadataTransactionImpl
                 */
                DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(digitalAssetMetadataTransactionImpl);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>CommunicationNetworkServiceDatabaseConstants</code>
     *
     * @param filters
     * @return List<DigitalAssetMetadataTransactionImpl>
     * @throws CantReadRecordDataBaseException
     */
    public List<DigitalAssetMetadataTransactionImpl> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<DigitalAssetMetadataTransactionImpl> list = null;
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
             * 4 - Create a list of DigitalAssetMetadataTransactionImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into DigitalAssetMetadataTransactionImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  DigitalAssetMetadataTransactionImpl
                 */
                DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(digitalAssetMetadataTransactionImpl);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        }

        /*
         * return the list
         */
        return list;
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param entity DigitalAssetMetadataTransactionImpl to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(DigitalAssetMetadataTransactionImpl entity) throws CantInsertRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToInsert(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {


            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";
            CantInsertRecordDataBaseException cantInsertRecordDataBaseException = new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantInsertRecordDataBaseException;

        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity DigitalAssetMetadataTransactionImpl to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(DigitalAssetMetadataTransactionImpl entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
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
     * Method that delete a entity in the data base.
     *
     * @param id Long id.
     * @throws CantDeleteRecordDataBaseException
     */
    public void delete(Long id) throws CantDeleteRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required can not be null");
        }

        try {

            /*
             * Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();

            //falta configurar la llamada para borrar la entidad

            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        }

    }


    /**
     * Create a instance of DigitalAssetMetadataTransactionImpl from the DatabaseTableRecord
     *
     * @param record with values from the table
     * @return DigitalAssetMetadataTransactionImpl setters the values from table
     */
    private DigitalAssetMetadataTransactionImpl constructFrom(DatabaseTableRecord record) {

        DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl = new DigitalAssetMetadataTransactionImpl();

        try {

            digitalAssetMetadataTransactionImpl.setTransactionId(UUID.fromString(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_FIRST_KEY_COLUMN)));
            digitalAssetMetadataTransactionImpl.setGenesisTransaction(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_GENESIS_TRANSACTION_COLUMN_NAME));
            digitalAssetMetadataTransactionImpl.setSenderId(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_ID_COLUMN_NAME));
            digitalAssetMetadataTransactionImpl.setSenderType(PlatformComponentType.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_TYPE_COLUMN_NAME)));
            digitalAssetMetadataTransactionImpl.setReceiverId(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_ID_COLUMN_NAME));
            digitalAssetMetadataTransactionImpl.setReceiverType(PlatformComponentType.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_TYPE_COLUMN_NAME)));
            digitalAssetMetadataTransactionImpl.setDigitalAssetMetadata((DigitalAssetMetadata) XMLParser.parseXML(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_META_DATA_XML_COLUMN_NAME), DigitalAssetMetadata.class));
            digitalAssetMetadataTransactionImpl.setType(DigitalAssetMetadataTransactionType.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_TYPE_COLUMN_NAME)));
            digitalAssetMetadataTransactionImpl.setDistributionStatus(DistributionStatus.getByCode(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_DISTRIBUTION_STATUS_COLUMN_NAME)));
            digitalAssetMetadataTransactionImpl.setTimestamp(record.getLongValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_TIMESTAMP_COLUMN_NAME));
            digitalAssetMetadataTransactionImpl.setProcessed(record.getStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_PROCESSED_COLUMN_NAME));

        } catch (InvalidParameterException e) {
            //this should not happen, but if it happens return null
            e.printStackTrace();
            return null;
        }

        return digitalAssetMetadataTransactionImpl;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a DigitalAssetMetadataTransactionImpl pass
     * by parameter
     *
     * @param digitalAssetMetadataTransactionImpl the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(DigitalAssetMetadataTransactionImpl digitalAssetMetadataTransactionImpl) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_FIRST_KEY_COLUMN,                digitalAssetMetadataTransactionImpl.getTransactionId().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_GENESIS_TRANSACTION_COLUMN_NAME, digitalAssetMetadataTransactionImpl.getGenesisTransaction());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_ID_COLUMN_NAME,           digitalAssetMetadataTransactionImpl.getSenderId());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_SENDER_TYPE_COLUMN_NAME,         digitalAssetMetadataTransactionImpl.getSenderType().getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_ID_COLUMN_NAME,         digitalAssetMetadataTransactionImpl.getReceiverId());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_RECEIVER_TYPE_COLUMN_NAME,       digitalAssetMetadataTransactionImpl.getReceiverType().getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_META_DATA_XML_COLUMN_NAME,       digitalAssetMetadataTransactionImpl.getDigitalAssetMetadata().toString());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_TYPE_COLUMN_NAME,                digitalAssetMetadataTransactionImpl.getType().getCode());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_DISTRIBUTION_STATUS_COLUMN_NAME, digitalAssetMetadataTransactionImpl.getDistributionStatus().getCode());
        entityRecord.setLongValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_TIMESTAMP_COLUMN_NAME,             digitalAssetMetadataTransactionImpl.getTimestamp());
        entityRecord.setStringValue(CommunicationNetworkServiceDatabaseConstants.DIGITAL_ASSET_METADATA_TRANSACTION_PROCESSED_COLUMN_NAME,           digitalAssetMetadataTransactionImpl.getProcessed());

        /*
         * return the new table record
         */
        return entityRecord;

    }

}
