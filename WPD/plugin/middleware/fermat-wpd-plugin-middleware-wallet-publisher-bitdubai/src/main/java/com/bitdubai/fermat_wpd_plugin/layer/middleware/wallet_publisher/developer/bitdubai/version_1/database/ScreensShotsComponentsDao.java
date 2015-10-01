/*
 * @#InformationPublishedComponentDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageMiddlewareImpl;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.InformationPublishedComponentDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ScreensShotsComponentsDao {


    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the imageManager
     */
    private ImageManager imageManager;

    /**
     * Constructor with parameters
     *
     * @param dataBase
     */
    public ScreensShotsComponentsDao(Database dataBase, ImageManager imageManager) {
        super();
        this.dataBase = dataBase;
        this.imageManager = imageManager;
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
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_TABLE_NAME);
    }

    /**
     * Method that find an ImageMiddlewareImpl by id in the data base.
     *
     * @param id Long id.
     * @return ImageMiddlewareImpl found.
     * @throws CantReadRecordDataBaseException
     */
    public Image findById(String id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        ImageMiddlewareImpl walletPublishedMiddlewareInformation = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            getDataBase().openDatabase();
            DatabaseTable incomingMessageTable = getDatabaseTable();
            incomingMessageTable.setStringFilter(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FIRST_KEY_COLUMN, id, DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into ImageMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  ImageMiddlewareImpl
                 */
                walletPublishedMiddlewareInformation = constructFrom(record);
            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (DatabaseNotFoundException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (Exception e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Other problems";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

        return walletPublishedMiddlewareInformation;
    }

    ;

    /**
     * Method that list the all entities on the data base.
     *
     * @return All ImageMiddlewareImpl.
     * @throws CantReadRecordDataBaseException
     */
    public List<Image> findAll() throws CantReadRecordDataBaseException {


        List<Image> list = null;

        try {

            /*
             * 1 - load the data base to memory
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseTable();
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ImageMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ImageMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  ImageMiddlewareImpl
                 */
                ImageMiddlewareImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (DatabaseNotFoundException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (Exception e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Other problems";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

        /*
         * return the list
         */
        return list;
    }

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the column name are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     * @return All ImageMiddlewareImpl.
     * @throws CantReadRecordDataBaseException
     * @see WalletPublisherMiddlewareDatabaseConstants
     */
    public List<Image> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<Image> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseTable();
            walletPublishedMiddlewareInformationTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of ImageMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into ImageMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  ImageMiddlewareImpl
                 */
                ImageMiddlewareImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 4.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (DatabaseNotFoundException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (Exception e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Other problems";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

        /*
         * return the list
         */
        return list;
    }

    ;


    /**
     * Method that list the all entities on the data base. The valid value of
     * the key are the att of the <code>WalletPublisherMiddlewareDatabaseConstants</code>
     *
     * @param filters
     * @return List<ImageMiddlewareImpl>
     * @throws CantReadRecordDataBaseException
     */
    public List<Image> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<Image> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {

            /*
             * 1- Prepare the filters
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseTable();

            for (String key : filters.keySet()) {

                DatabaseTableFilter newFilter = walletPublishedMiddlewareInformationTable.getEmptyTableFilter();
                newFilter.setType(DatabaseFilterType.EQUAL);
                newFilter.setColumn(key);
                newFilter.setValue((String) filters.get(key));

                filtersTable.add(newFilter);
            }

            /*
             * 2 - load the data base to memory with filters
             */
            walletPublishedMiddlewareInformationTable.setFilterGroup(filtersTable, null, DatabaseFilterOperator.OR);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 3 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 4 - Create a list of ImageMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into ImageMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  ImageMiddlewareImpl
                 */
                ImageMiddlewareImpl walletPublishedMiddlewareInformation = constructFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
            throw cantReadRecordDataBaseException;
        } catch (DatabaseNotFoundException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (Exception e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Other problems";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

        /*
         * return the list
         */
        return list;
    }

    ;

    /**
     * Method that update an entity in the data base.
     *
     * @param entity ImageMiddlewareImpl to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(ImageMiddlewareImpl entity) throws CantUpdateRecordDataBaseException {

        if (entity == null) {
            throw new IllegalArgumentException("The entity is required, can not be null");
        }

        try {

            /*
             * 1- Create the record to the entity
             */
            getDataBase().openDatabase();
            DatabaseTableRecord entityRecord = constructFrom(entity);

            /*
             * 2.- Create a new transaction and execute
             */
            DatabaseTransaction transaction = getDataBase().newTransaction();
            transaction.addRecordToUpdate(getDatabaseTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (DatabaseNotFoundException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database not exist";
            throw new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);

        } catch (CantOpenDatabaseException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database not exist";
            throw new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
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
            getDataBase().openDatabase();
            DatabaseTransaction transaction = getDataBase().newTransaction();

            //TODO: falta configurar la llamada para borrar la entidad

            getDataBase().executeTransaction(transaction);

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        } catch (DatabaseNotFoundException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The database do not exist";
            CantDeleteRecordDataBaseException cantDeleteRecordDataBaseException = new CantDeleteRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantDeleteRecordDataBaseException;

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

    }


    /**
     * Construct a ImageMiddlewareImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return ImageMiddlewareImpl setters the values from table
     */
    private ImageMiddlewareImpl constructFrom(DatabaseTableRecord record) throws FileNotFoundException, CantCreateFileException {

        /*
         * Construct object
         */
        ImageMiddlewareImpl imageMiddleware = new ImageMiddlewareImpl();
        imageMiddleware.setFileId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME)));
        imageMiddleware.setComponentId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME)));
        imageMiddleware.setData(imageManager.loadImageFile(imageMiddleware.getFileId().toString()));

        return imageMiddleware;

    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a imageImpl pass
     * by parameter
     *
     * @param imageImpl the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ImageMiddlewareImpl imageImpl) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_FILE_ID_COLUMN_NAME, imageImpl.getFileId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_COMPONENT_ID_COLUMN_NAME, imageImpl.getComponentId().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }


}