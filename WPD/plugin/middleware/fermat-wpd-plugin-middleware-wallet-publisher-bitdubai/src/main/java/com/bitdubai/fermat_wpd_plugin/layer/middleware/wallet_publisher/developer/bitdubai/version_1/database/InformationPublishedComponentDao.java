/*
 * @#InformationPublishedComponentDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.enums.InformationPublishedComponentType;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantDeleteRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInsertRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ComponentVersionDetailMiddlewareImpl;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageMiddlewareImpl;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.InformationPublishedComponentMiddlewareImpl;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
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
public class InformationPublishedComponentDao {


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
    public InformationPublishedComponentDao(Database dataBase, ImageManager imageManager) {
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
    DatabaseTable getDatabaseInformationPublishedComponentsTable() {
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_TABLE_NAME);
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseComponetVersionsDetailsTable() {
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_TABLE_NAME);
    }

    /**
     * Return the DatabaseTable
     *
     * @return DatabaseTable
     */
    DatabaseTable getDatabaseScreenShotsComponentsTable() {
        return getDataBase().getTable(WalletPublisherMiddlewareDatabaseConstants.SCREENS_SHOTS_COMPONENTS_TABLE_NAME);
    }

    /**
     * Method that find an InformationPublishedComponentMiddlewareImpl by id in the data base.
     *
     * @param id Long id.
     * @return InformationPublishedComponentMiddlewareImpl found.
     * @throws CantReadRecordDataBaseException
     */
    public InformationPublishedComponent findById(UUID id) throws CantReadRecordDataBaseException {

        if (id == null) {
            throw new IllegalArgumentException("The id is required, can not be null");
        }

        InformationPublishedComponentMiddlewareImpl walletPublishedMiddlewareInformation = null;

        try {

            /*
             * 1 - load the data base to memory with filter
             */
            getDataBase().openDatabase();
            DatabaseTable incomingMessageTable = getDatabaseInformationPublishedComponentsTable();
            incomingMessageTable.setStringFilter(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_FIRST_KEY_COLUMN, id.toString(), DatabaseFilterType.EQUAL);
            incomingMessageTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = incomingMessageTable.getRecords();


            /*
             * 3 - Convert into InformationPublishedComponentMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 3.1 - Create and configure a  InformationPublishedComponentMiddlewareImpl
                 */
                walletPublishedMiddlewareInformation = constructInformationPublishedComponentMiddlewareFrom(record);
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
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
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
     * @return All InformationPublishedComponentMiddlewareImpl.
     * @throws CantReadRecordDataBaseException
     */
    public List<InformationPublishedComponent> findAll() throws CantReadRecordDataBaseException {
        List<InformationPublishedComponent> list = null;
        try {
            /*
             * 1 - load the data base to memory
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseInformationPublishedComponentsTable();
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of InformationPublishedComponentMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into InformationPublishedComponentMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  InformationPublishedComponentMiddlewareImpl
                 */
                InformationPublishedComponentMiddlewareImpl walletPublishedMiddlewareInformation = constructInformationPublishedComponentMiddlewareFrom(record);

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
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);
            throw cantReadRecordDataBaseException;

        } catch (CantOpenDatabaseException e) {

            // Register the failure.
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
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
     * @return All InformationPublishedComponentMiddlewareImpl.
     * @throws CantReadRecordDataBaseException
     * @see WalletPublisherMiddlewareDatabaseConstants
     */
    public List<InformationPublishedComponent> findAll(String columnName, String columnValue) throws CantReadRecordDataBaseException {

        if (columnName == null ||
                columnName.isEmpty() ||
                columnValue == null ||
                columnValue.isEmpty()) {

            throw new IllegalArgumentException("The filter are required, can not be null or empty");
        }


        List<InformationPublishedComponent> list = null;

        try {

            /*
             * 1 - load the data base to memory with filters
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseInformationPublishedComponentsTable();
            walletPublishedMiddlewareInformationTable.setStringFilter(columnName, columnValue, DatabaseFilterType.EQUAL);
            walletPublishedMiddlewareInformationTable.loadToMemory();

            /*
             * 2 - read all records
             */
            List<DatabaseTableRecord> records = walletPublishedMiddlewareInformationTable.getRecords();

            /*
             * 3 - Create a list of InformationPublishedComponentMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 4 - Convert into InformationPublishedComponentMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 4.1 - Create and configure a  InformationPublishedComponentMiddlewareImpl
                 */
                InformationPublishedComponentMiddlewareImpl walletPublishedMiddlewareInformation = constructInformationPublishedComponentMiddlewareFrom(record);

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
     * @return List<InformationPublishedComponentMiddlewareImpl>
     * @throws CantReadRecordDataBaseException
     */
    public List<InformationPublishedComponent> findAll(Map<String, Object> filters) throws CantReadRecordDataBaseException {

        if (filters == null ||
                filters.isEmpty()) {

            throw new IllegalArgumentException("The filters are required, can not be null or empty");
        }

        List<InformationPublishedComponent> list = null;
        List<DatabaseTableFilter> filtersTable = new ArrayList<>();

        try {

            /*
             * 1- Prepare the filters
             */
            getDataBase().openDatabase();
            DatabaseTable walletPublishedMiddlewareInformationTable = getDatabaseInformationPublishedComponentsTable();

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
             * 4 - Create a list of InformationPublishedComponentMiddlewareImpl objects
             */
            list = new ArrayList<>();
            list.clear();

            /*
             * 5 - Convert into InformationPublishedComponentMiddlewareImpl objects
             */
            for (DatabaseTableRecord record : records) {

                /*
                 * 5.1 - Create and configure a  InformationPublishedComponentMiddlewareImpl
                 */
                InformationPublishedComponentMiddlewareImpl walletPublishedMiddlewareInformation = constructInformationPublishedComponentMiddlewareFrom(record);

                /*
                 * 5.2 - Add to the list
                 */
                list.add(walletPublishedMiddlewareInformation);

            }

        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {

            cantLoadTableToMemory.printStackTrace();

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The data no exist";
            CantReadRecordDataBaseException cantReadRecordDataBaseException = new CantReadRecordDataBaseException(CantReadRecordDataBaseException.DEFAULT_MESSAGE, cantLoadTableToMemory, context, possibleCause);
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
     * Method that create a new entity in the data base.
     *
     * @param informationPublishedComponentMiddleware to create.
     * @param componentVersionDetailMiddleware        to create.
     * @param images                                  to create.
     * @throws CantInsertRecordDataBaseException
     */
    public void create(InformationPublishedComponentMiddlewareImpl informationPublishedComponentMiddleware, ComponentVersionDetailMiddlewareImpl componentVersionDetailMiddleware, List<ImageMiddlewareImpl> images) throws CantInsertRecordDataBaseException {

        if (informationPublishedComponentMiddleware == null) {
            throw new IllegalArgumentException("The entities are required, can not be null");
        }

        try {

            /*
             * 1- Create the records
             */
            getDataBase().openDatabase();
            DatabaseTransaction transaction = getDataBase().newTransaction();

            DatabaseTableRecord informationPublishedComponentRecord = constructFrom(informationPublishedComponentMiddleware);
            transaction.addRecordToInsert(getDatabaseInformationPublishedComponentsTable(), informationPublishedComponentRecord);

            DatabaseTableRecord componentVersionDetailRecord = constructFrom(componentVersionDetailMiddleware);
            transaction.addRecordToInsert(getDatabaseComponetVersionsDetailsTable(), componentVersionDetailRecord);

            for (ImageMiddlewareImpl imageMiddleware : images) {
                DatabaseTableRecord imageMiddlewareRecord = constructFrom(imageMiddleware);
                transaction.addRecordToInsert(getDatabaseScreenShotsComponentsTable(), imageMiddlewareRecord);
            }

            /*
             * 2.- Execute the transaction
             */
            getDataBase().executeTransaction(transaction);

            /*
             * 3.- Serialize the objects images into the xml file
             */
            imageManager.saveImageFile((ImageMiddlewareImpl) informationPublishedComponentMiddleware.getIconImg());
            imageManager.saveImageFile((ImageMiddlewareImpl) informationPublishedComponentMiddleware.getMainScreenShotImg());
            for (ImageMiddlewareImpl imageMiddleware : images) {

                imageManager.saveImageFile(imageMiddleware);
            }


        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "The Template Database triggered an unexpected problem that wasn't able to solve by itself";

            throw new CantInsertRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);

        } catch (CantPersistFileException cantPersistFileException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "Can not update the file image";

            throw new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, cantPersistFileException, context, possibleCause);

        } catch (CantCreateFileException cantCreateFileException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "Can not update the file image";

            throw new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, cantCreateFileException, context, possibleCause);
        } catch (DatabaseNotFoundException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "The data base no exist";

            throw new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);

        } catch (CantOpenDatabaseException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);
            String context = contextBuffer.toString();
            String possibleCause = "The data base no exist";

            throw new CantInsertRecordDataBaseException(CantInsertRecordDataBaseException.DEFAULT_MESSAGE, e, context, possibleCause);

        } finally {

            if (getDataBase() != null) {
                getDataBase().closeDatabase();
            }
        }

    }

    /**
     * Method that update an entity in the data base.
     *
     * @param entity InformationPublishedComponentMiddlewareImpl to update.
     * @throws CantUpdateRecordDataBaseException
     */
    public void update(InformationPublishedComponentMiddlewareImpl entity) throws CantUpdateRecordDataBaseException {

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
            transaction.addRecordToUpdate(getDatabaseInformationPublishedComponentsTable(), entityRecord);
            getDataBase().executeTransaction(transaction);

            /*
             * 3.- Serialize the objects images into the xml file
             */
            imageManager.saveImageFile((ImageMiddlewareImpl) entity.getIconImg());
            imageManager.saveImageFile((ImageMiddlewareImpl) entity.getMainScreenShotImg());

        } catch (DatabaseTransactionFailedException databaseTransactionFailedException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The record do not exist";
            CantUpdateRecordDataBaseException cantUpdateRecordDataBaseException = new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, databaseTransactionFailedException, context, possibleCause);
            throw cantUpdateRecordDataBaseException;

        } catch (CantPersistFileException cantPersistFileException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Can not update the file image";
            throw new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantPersistFileException, context, possibleCause);

        } catch (CantCreateFileException cantCreateFileException) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "Can not update the file image";
            throw new CantUpdateRecordDataBaseException(CantDeleteRecordDataBaseException.DEFAULT_MESSAGE, cantCreateFileException, context, possibleCause);

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
     * Construct a InformationPublishedComponentMiddlewareImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return InformationPublishedComponentMiddlewareImpl setters the values from table
     */
    private InformationPublishedComponentMiddlewareImpl constructInformationPublishedComponentMiddlewareFrom(DatabaseTableRecord record) throws InvalidParameterException, MalformedURLException, FileNotFoundException, CantCreateFileException {

        InformationPublishedComponentMiddlewareImpl informationPublishedComponent = new InformationPublishedComponentMiddlewareImpl();

        informationPublishedComponent.setId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ID_COLUMN_NAME)));
        informationPublishedComponent.setWalletFactoryProjectId(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_ID_COLUMN_NAME));
        informationPublishedComponent.setWalletFactoryProjectName(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_NAME_COLUMN_NAME));
        informationPublishedComponent.setType(InformationPublishedComponentType.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME)));
        informationPublishedComponent.setDescriptions(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_DESCRIPTIONS_COLUMN_NAME));
        informationPublishedComponent.setVideoUrl(new URL(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_VIDEO_URL_COLUMN_NAME)));
        informationPublishedComponent.setStatus(ComponentPublishedInformationStatus.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_COLUMN_NAME)));
        informationPublishedComponent.setStatusTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_TIMESTAMP_COLUMN_NAME)));
        informationPublishedComponent.setPublicationTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME)));
        informationPublishedComponent.setPublisherIdentityPublicKey(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME));
        informationPublishedComponent.setSignature(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_SIGNATURE_COLUMN_NAME));

        /*
         * Image File deserialize into the object image
         */
        String fileIdIconImg = record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ICON_IMG_COLUMN_NAME);
        String fileIdMainScreenShot = record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_MAIN_SCREEN_SHOT_IMG_COLUMN_NAME);
        informationPublishedComponent.setIconImg(constructImageMiddlewareFrom(fileIdIconImg, informationPublishedComponent.getId()));
        informationPublishedComponent.setMainScreenShotImg(constructImageMiddlewareFrom(fileIdMainScreenShot, informationPublishedComponent.getId()));

        return informationPublishedComponent;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a intraUserNetworkServiceMessage pass
     * by parameter
     *
     * @param walletPublishedMiddlewareInformation the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(InformationPublishedComponentMiddlewareImpl walletPublishedMiddlewareInformation) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseInformationPublishedComponentsTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ID_COLUMN_NAME, walletPublishedMiddlewareInformation.getId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_ID_COLUMN_NAME, walletPublishedMiddlewareInformation.getWalletFactoryProjectId());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_WFP_NAME_COLUMN_NAME, walletPublishedMiddlewareInformation.getWalletFactoryProjectName());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_COMPONENT_TYPE_COLUMN_NAME, walletPublishedMiddlewareInformation.getType().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_DESCRIPTIONS_COLUMN_NAME, walletPublishedMiddlewareInformation.getDescriptions());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_ICON_IMG_COLUMN_NAME, ((ImageMiddlewareImpl) walletPublishedMiddlewareInformation.getIconImg()).getFileId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_MAIN_SCREEN_SHOT_IMG_COLUMN_NAME, ((ImageMiddlewareImpl) walletPublishedMiddlewareInformation.getMainScreenShotImg()).getFileId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_VIDEO_URL_COLUMN_NAME, walletPublishedMiddlewareInformation.getVideoUrl().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_COLUMN_NAME, walletPublishedMiddlewareInformation.getStatus().getCode());
        entityRecord.setLongValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_STATUS_TIMESTAMP_COLUMN_NAME, walletPublishedMiddlewareInformation.getStatusTimestamp().getTime());

        if (walletPublishedMiddlewareInformation.getPublicationTimestamp() == null) {
            entityRecord.setLongValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME, 0);
        } else {
            entityRecord.setLongValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLICATION_TIMESTAMP_COLUMN_NAME, walletPublishedMiddlewareInformation.getPublicationTimestamp().getTime());
        }

        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_PUBLISHER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, walletPublishedMiddlewareInformation.getPublisherIdentityPublicKey());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.INFORMATION_PUBLISHED_COMPONENTS_SIGNATURE_COLUMN_NAME, walletPublishedMiddlewareInformation.getSignature());

        /*
         * return the new table record
         */
        return entityRecord;

    }

    /**
     * Construct a ComponentVersionDetailImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return ComponentVersionDetailImpl setters the values from table
     */
    private ComponentVersionDetailMiddlewareImpl constructComponentVersionDetailMiddlewareFrom(DatabaseTableRecord record) throws InvalidParameterException {

        ComponentVersionDetailMiddlewareImpl componentVersionDetailImpl = new ComponentVersionDetailMiddlewareImpl();

        componentVersionDetailImpl.setId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME)));
        componentVersionDetailImpl.setScreenSize(ScreenSize.getByCode(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME)));
        componentVersionDetailImpl.setVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME)));
        componentVersionDetailImpl.setVersionTimestamp(new Timestamp(record.getLongValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME)));
        componentVersionDetailImpl.setInitialWalletVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME)));
        componentVersionDetailImpl.setFinalWalletVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME)));
        componentVersionDetailImpl.setInitialPlatformVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME)));
        componentVersionDetailImpl.setFinalPlatformVersion(new Version(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME)));
        componentVersionDetailImpl.setObservations(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME));
        componentVersionDetailImpl.setCatalogId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME)));
        componentVersionDetailImpl.setComponentId(UUID.fromString(record.getStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME)));

        return componentVersionDetailImpl;
    }

    /**
     * Construct a DatabaseTableRecord whit the values of the a componentVersionDetailImpl pass
     * by parameter
     *
     * @param componentVersionDetailImpl the contains the values
     * @return DatabaseTableRecord whit the values
     */
    private DatabaseTableRecord constructFrom(ComponentVersionDetailMiddlewareImpl componentVersionDetailImpl) {

        /*
         * Create the record to the entity
         */
        DatabaseTableRecord entityRecord = getDatabaseInformationPublishedComponentsTable().getEmptyRecord();

        /*
         * Set the entity values
         */
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_ID_COLUMN_NAME, componentVersionDetailImpl.getId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_SCREEN_SIZE_COLUMN_NAME, componentVersionDetailImpl.getScreenSize().getCode());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_COLUMN_NAME, componentVersionDetailImpl.getVersion().toString());
        entityRecord.setLongValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_VERSION_TIMESTAMP_COLUMN_NAME, componentVersionDetailImpl.getVersionTimestamp().getTime());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_WALLET_VERSION_COLUMN_NAME, componentVersionDetailImpl.getInitialWalletVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_WALLET_VERSION_COLUMN_NAME, componentVersionDetailImpl.getFinalWalletVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_INITIAL_PLATFORM_VERSION_COLUMN_NAME, componentVersionDetailImpl.getInitialPlatformVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_FINAL_PLATFORM_VERSION_COLUMN_NAME, componentVersionDetailImpl.getFinalPlatformVersion().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_OBSERVATIONS_COLUMN_NAME, componentVersionDetailImpl.getObservations().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_CATALOG_ID_COLUMN_NAME, componentVersionDetailImpl.getCatalogId().toString());
        entityRecord.setStringValue(WalletPublisherMiddlewareDatabaseConstants.COMPONENT_VERSIONS_DETAILS_COMPONENT_ID_COLUMN_NAME, componentVersionDetailImpl.getComponentId().toString());

        /*
         * return the new table record
         */
        return entityRecord;

    }

    /**
     * Construct a ImageMiddlewareImpl whit the values of the table record pass
     * by parameter
     *
     * @param record with values from the table
     * @return ImageMiddlewareImpl setters the values from table
     */
    private ImageMiddlewareImpl constructImageMiddlewareFrom(DatabaseTableRecord record) throws FileNotFoundException, CantCreateFileException {

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
        DatabaseTableRecord entityRecord = getDatabaseInformationPublishedComponentsTable().getEmptyRecord();

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


    /**
     * Construct a ImageMiddlewareImpl whit the values of the pass parameters
     *
     * @param fileId
     * @param componentId
     * @return
     */
    private ImageMiddlewareImpl constructImageMiddlewareFrom(String fileId, UUID componentId) throws FileNotFoundException, CantCreateFileException {

        /*
         * Construct object
         */
        ImageMiddlewareImpl imageMiddleware = new ImageMiddlewareImpl();
        imageMiddleware.setFileId(UUID.fromString(fileId));
        imageMiddleware.setComponentId(componentId);
        imageMiddleware.setData(imageManager.loadImageFile(imageMiddleware.getFileId().toString()));


        return imageMiddleware;
    }


}