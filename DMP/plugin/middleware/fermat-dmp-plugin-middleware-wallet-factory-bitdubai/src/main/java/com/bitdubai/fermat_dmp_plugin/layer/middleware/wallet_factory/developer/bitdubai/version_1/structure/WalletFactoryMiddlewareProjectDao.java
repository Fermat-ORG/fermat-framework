package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectDao implements DealsWithPluginDatabaseSystem {

    /**
     *  Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent de Database where i will be working with
     */
    Database database;


    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletFactoryMiddlewareProjectDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId plugin id
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
                   * We create the new database
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
     *  @return All Wallet Factory Projects who belongs to a developer.
     */
    public List<WalletFactoryProject> findAll(String developerPublicKey) throws CantGetWalletFactoryProjectsException {

        List<WalletFactoryProject> walletFactoryProjectList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, developerPublicKey, DatabaseFilterType.EQUAL);
            projectTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);

                WalletFactoryProject walletFactoryProject = new WalletFactoryMiddlewareProject(id, name, developerPublicKey);

                walletFactoryProjectList.add(walletFactoryProject);
            }
            database.closeDatabase();
            return walletFactoryProjectList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     *  @return All Wallet Factory Projects who belongs to a developer.
     */
    public List<WalletFactoryProject> findAllScrolling(String developerPublicKey, Integer max, Integer offset) throws CantGetWalletFactoryProjectsException {

        List<WalletFactoryProject> walletFactoryProjectList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, developerPublicKey, DatabaseFilterType.EQUAL);
            projectTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectTable.setFilterTop(max.toString());
            projectTable.setFilterOffSet(offset.toString());
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);

                WalletFactoryProject walletFactoryProject = new WalletFactoryMiddlewareProject(id, name, developerPublicKey);

                walletFactoryProjectList.add(walletFactoryProject);
            }
            database.closeDatabase();
            return walletFactoryProjectList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectsException(CantGetWalletFactoryProjectsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @param name
     * @return Wallet Factory Projects matches with the given name.
     * @throws CantGetWalletFactoryProjectException
     */
    public WalletFactoryProject findByName(String name, String developerPublicKey) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {

        List<WalletFactoryProject> walletFactoryProjectList = new ArrayList<>();

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
                database.closeDatabase();
                return new WalletFactoryMiddlewareProject(id, name, developerPublicKey);
            } else {
                database.closeDatabase();
                throw new ProjectNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that name that belongs to the user.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectException(CantGetWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     *  @param walletFactoryProject WalletFactoryProject to create.
     */
    public void create(WalletFactoryProject walletFactoryProject) throws CantCreateWalletFactoryProjectException {

        if (walletFactoryProject == null && walletFactoryProject.getId() != null && walletFactoryProject.getName() != null && walletFactoryProject.getDeveloperPublicKey() != null){
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, name and developer public key.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectTable.getEmptyRecord();
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, walletFactoryProject.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletFactoryProject.getName());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, walletFactoryProject.getDeveloperPublicKey());

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(projectTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that update a WalletFactoryProject in the database.
     *
     *  @param walletFactoryProject WalletFactoryProject to update.
     */
    public void update(WalletFactoryProject walletFactoryProject) throws CantUpdateWalletFactoryProjectException, ProjectNotFoundException {

        if (walletFactoryProject == null && walletFactoryProject.getId() != null && walletFactoryProject.getName() != null){
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, null, "The entity is required, can not be null.", "Check id or name values.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, walletFactoryProject.getId(), DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> databaseTableRecordList = projectTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);
                if (walletFactoryProject.getName() != null) {
                    record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletFactoryProject.getName());
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
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantUpdateWalletFactoryProjectException(CantUpdateWalletFactoryProjectException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that delete a entity in the database.
     *
     *  @param id UUID project id.
     * */
    public void delete(UUID id) throws CantDeleteWalletFactoryProjectException, ProjectNotFoundException {

        if (id == null){
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
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
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
