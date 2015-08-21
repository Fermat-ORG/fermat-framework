package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletDescriptorFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.DescriptorFactoryMiddlewareProject;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletDescriptorFactoryMiddlewareProject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.database.WalletFactoryMiddlewareDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareDao implements DealsWithPluginDatabaseSystem {

    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletFactoryMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletFactoryMiddlewareDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletFactoryMiddlewareDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletFactoryMiddlewareDatabaseException(CantInitializeWalletFactoryMiddlewareDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletFactoryMiddlewareDatabaseFactory walletFactoryMiddlewareDatabaseFactory = new WalletFactoryMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create then new database
                   */
                database = walletFactoryMiddlewareDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletFactoryMiddlewareDatabaseException(CantInitializeWalletFactoryMiddlewareDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Factory Projects who belongs to a developer.
     */
    public List<WalletDescriptorFactoryProject> findAll(String developerPublicKey) throws CantGetWalletFactoryProjectsException {

        List<WalletDescriptorFactoryProject> WalletDescriptorFactoryProjectList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, developerPublicKey, DatabaseFilterType.EQUAL);
            projectTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            for (DatabaseTableRecord record : records) {

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));
                String path = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME);
                String description = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME));
                String publisherIdentityKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME);
                DescriptorFactoryProjectType descriptorFactoryProjectType = DescriptorFactoryProjectType.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME));
                WalletDescriptorFactoryMiddlewareProject walletFactoryProject = new WalletDescriptorFactoryMiddlewareProject(id, name, developerPublicKey, walletType, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);

                WalletDescriptorFactoryProjectList.add(walletFactoryProject);
            }
            database.closeDatabase();
            return WalletDescriptorFactoryProjectList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (Exception exception)  {
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "Exception unchecked", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Factory Projects who belongs to a developer.
     */
    public List<WalletDescriptorFactoryProject> findAllScrolling(String developerPublicKey, Integer max, Integer offset) throws CantGetWalletFactoryProjectsException {

        List<WalletDescriptorFactoryProject> walletDescriptorFactoryProjectList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, developerPublicKey, DatabaseFilterType.EQUAL);
            projectTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectTable.setFilterTop(max.toString());
            projectTable.setFilterOffSet(offset.toString());
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            for (DatabaseTableRecord record : records) {

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));
                String path = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME));
                String description = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
                String publisherIdentityKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME);
                DescriptorFactoryProjectType descriptorFactoryProjectType = DescriptorFactoryProjectType.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME));

                WalletDescriptorFactoryMiddlewareProject walletDescriptorFactoryProject = new WalletDescriptorFactoryMiddlewareProject(id, name, developerPublicKey, walletType, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);

                walletDescriptorFactoryProjectList.add(walletDescriptorFactoryProject);
            }
            database.closeDatabase();
            return walletDescriptorFactoryProjectList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (Exception exception)  {
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "Exception unchecked", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @param name
     * @return Wallet Factory Projects matches with the given name.
     * @throws CantGetWalletFactoryProjectException
     */
    public WalletDescriptorFactoryProject findByName(String name, String developerPublicKey) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, name, DatabaseFilterType.EQUAL);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, developerPublicKey, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));
                String path = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME));
                String description = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
                String publisherIdentityKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME);
                DescriptorFactoryProjectType descriptorFactoryProjectType = DescriptorFactoryProjectType.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME));

                database.closeDatabase();
                return new WalletDescriptorFactoryMiddlewareProject(id, name, developerPublicKey, walletType, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that name that belongs to the user.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }catch (Exception exception)  {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "Exception unchecked", "Check the cause.");
        }
    }

    public WalletDescriptorFactoryProject findProjectById(UUID id) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
                String developerPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME);
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));
                String path = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME));
                String description = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
                String publisherIdentityKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME);
                DescriptorFactoryProjectType descriptorFactoryProjectType = DescriptorFactoryProjectType.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME));

                database.closeDatabase();
                return new WalletDescriptorFactoryMiddlewareProject(id, name, developerPublicKey, walletType, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that name that belongs to the user.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        } catch (Exception exception)  {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "Exception unchecked", "Check the cause.");
        }
    }

    public List<WalletDescriptorFactoryProject> findProjectByState(FactoryProjectState factoryProjectState) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {

        List<WalletDescriptorFactoryProject> walletDescriptorFactoryProjectList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStateFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, factoryProjectState, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();
            for (DatabaseTableRecord record : records) {

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
                String developerPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME);
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));
                String path = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME);
                String description = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME);
                String publisherIdentityKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME);
                DescriptorFactoryProjectType descriptorFactoryProjectType = DescriptorFactoryProjectType.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME));

                WalletDescriptorFactoryMiddlewareProject walletFactoryProject = new WalletDescriptorFactoryMiddlewareProject(id, name, developerPublicKey, walletType, path, factoryProjectState, description, publisherIdentityKey, descriptorFactoryProjectType);

                walletDescriptorFactoryProjectList.add(walletFactoryProject);
            }
            database.closeDatabase();
            return walletDescriptorFactoryProjectList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }catch (Exception exception)  {
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "Exception unchecked", "Check the cause.");
        }
    }

    public void setProjectState(UUID id, FactoryProjectState state) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException {

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);
                if (id != null) {
                    record.setStateValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, state);
                }

                DatabaseTransaction transaction = database.newTransaction();
                transaction.addRecordToUpdate(projectTable, record);
                database.executeTransaction(transaction);
                database.closeDatabase();
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that name" + state.value());
            }
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void closeProject(String name) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException {

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, name, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);
                if (name != null) {
                    record.setStateValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, FactoryProjectState.CLOSED);
                }

                DatabaseTransaction transaction = database.newTransaction();
                transaction.addRecordToUpdate(projectTable, record);
                database.executeTransaction(transaction);
                database.closeDatabase();
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that name " + name);
            }
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     * @param walletDescriptorFactoryProject DescriptorFactoryProject to create.
     */
    public void create(WalletDescriptorFactoryProject walletDescriptorFactoryProject) throws CantCreateWalletDescriptorFactoryProjectException {

        if (walletDescriptorFactoryProject == null && walletDescriptorFactoryProject.getId() != null && walletDescriptorFactoryProject.getName() != null && walletDescriptorFactoryProject.getDeveloperPublicKey() != null && walletDescriptorFactoryProject.getWalletType() != null && walletDescriptorFactoryProject.getDescription() != null && walletDescriptorFactoryProject.getDescriptorProjectType() != null) {
            throw new CantCreateWalletDescriptorFactoryProjectException(CantCreateWalletDescriptorFactoryProjectException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, name, type and developer public key.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectTable.getEmptyRecord();
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, walletDescriptorFactoryProject.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletDescriptorFactoryProject.getName());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, walletDescriptorFactoryProject.getDeveloperPublicKey());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME, walletDescriptorFactoryProject.getWalletType().getCode());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME, walletDescriptorFactoryProject.getPath());
            entityRecord.setStateValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_STATE_COLUMN_NAME, walletDescriptorFactoryProject.getState());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PUBLISHER_IDENTITY_KEY_COLUMN_NAME, walletDescriptorFactoryProject.getPublisherIdentityKey());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTION_COLUMN_NAME, walletDescriptorFactoryProject.getDescription());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DESCRIPTOR_FACTORY_PROJECT_COLUMN_NAME, walletDescriptorFactoryProject.getDescriptorProjectType().getCode());

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(projectTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateWalletDescriptorFactoryProjectException(CantCreateWalletDescriptorFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantCreateWalletDescriptorFactoryProjectException(CantCreateWalletDescriptorFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that update a DescriptorFactoryProject in the database.
     *
     * @param walletDescriptorFactoryProject DescriptorFactoryProject to update.
     */
    public void update(WalletDescriptorFactoryProject walletDescriptorFactoryProject) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException {

        if (walletDescriptorFactoryProject == null && walletDescriptorFactoryProject.getId() != null && walletDescriptorFactoryProject.getName() != null) {
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, null, "The entity is required, can not be null.", "Check id or name values.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, walletDescriptorFactoryProject.getId(), DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> databaseTableRecordList = projectTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);
                if (walletDescriptorFactoryProject.getName() != null) {
                    record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletDescriptorFactoryProject.getName());
                }
                if (walletDescriptorFactoryProject.getPath() != null) {
                    record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PATH_COLUMN_NAME, walletDescriptorFactoryProject.getPath());
                }

                DatabaseTransaction transaction = database.newTransaction();
                transaction.addRecordToUpdate(projectTable, record);
                database.executeTransaction(transaction);
                database.closeDatabase();
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that id");
            }

        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that delete a entity in the database.
     *
     * @param id UUID project id.
     */
    public void delete(UUID id) throws CantDeleteWalletFactoryProjectException, ProjectNotFoundException {

        if (id == null) {
            throw new CantDeleteWalletFactoryProjectException(CantDeleteWalletFactoryProjectException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                projectTable.deleteRecord(record);
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that id");
            }
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectException(CantDeleteWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectException(CantDeleteWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantDeleteWalletFactoryProjectException(CantDeleteWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
