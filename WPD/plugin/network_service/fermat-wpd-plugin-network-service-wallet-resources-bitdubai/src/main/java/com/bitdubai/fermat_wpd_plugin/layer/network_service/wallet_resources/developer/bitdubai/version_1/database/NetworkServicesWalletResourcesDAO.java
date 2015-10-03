package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.CantCreateRepositoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.*;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.08.03..
 */
public class NetworkServicesWalletResourcesDAO {


    /**
     *  Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Database connection
     */
    Database database;


    public NetworkServicesWalletResourcesDAO(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId plugin id
     * @param databaseName database name
     * @throws
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeNetworkServicesWalletResourcesDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesWalletResourcesDatabaseException(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesWalletResourcesDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            NetworkserviceswalletresourcesDatabaseFactory networkserviceswalletresourcesDatabaseFactory = new NetworkserviceswalletresourcesDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = networkserviceswalletresourcesDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesWalletResourcesDatabaseException(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesWalletResourcesDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }



    /**
     * Method that create a new entity in the database.
     *
     *  @param repository Repository to create.
     */
    public void createRepository(Repository repository,UUID skinId) throws CantCreateRepositoryException {

        if (repository == null ){
            throw new CantCreateRepositoryException(CantCreateRepositoryException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, name, type and developer public key.");
        }

        if ( repository.getPath() == null && repository.getSkinName() == null && repository.getNavigationStructureVersion() == null){
            throw new CantCreateRepositoryException(CantCreateRepositoryException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, name, type and developer public key.");
        }

        try {
            database.openDatabase();
            DatabaseTable RepositoryTable = getRepositoriesTable();
            DatabaseTableRecord entityRecord = RepositoryTable.getEmptyRecord();

            entityRecord.setStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_ID_COLUMN_NAME, UUID.randomUUID().toString());
            entityRecord.setUUIDValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_SKIN_ID_COLUMN_NAME, skinId);
            entityRecord.setStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_NAME_COLUMN_NAME, repository.getSkinName());
            entityRecord.setStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_NAVIGATION_STRUCTURE_VERSION_COLUMN_NAME, repository.getNavigationStructureVersion());
            entityRecord.setStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_PATH_TO_REPO_COLUMN_NAME, repository.getPath());

            RepositoryTable.insertRecord(entityRecord);

            database.closeDatabase();
        }
        catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantCreateRepositoryException(CantCreateRepositoryException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
        catch(Exception exception){
            throw new CantCreateRepositoryException(CantCreateRepositoryException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that delete a entity in the database.
     *
     *  @param skinId UUID skin id.
     *  @param repositoryName String repository name
     * */

    public void delete(UUID skinId,String repositoryName) throws CantDeleteRepositoryException {

        if (skinId == null || repositoryName==null){
            throw new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException.DEFAULT_MESSAGE, null, "", "The id amd the name is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable repositoryTable = getRepositoriesTable();
            repositoryTable.deleteRecord(getRepositoryDatabaseTableRecord(repositoryTable, skinId, repositoryName));
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.CantDeleteRepositoryException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");
        } catch (RepositoryNotFoundException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteRepositoryException(CantDeleteRepositoryException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, repository not found.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteRepositoryException(CantDeleteRepositoryException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantDeleteRepositoryException(CantDeleteRepositoryException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /*
    Get Repository data
     */
    public Repository getRepository(UUID skinId) throws CantGetRepositoryPathRecordException {
        try
        {
            Repository repository = null;

            DatabaseTable repoTable= database.getTable(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_TABLE_NAME);

            repoTable.setUUIDFilter(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);

            repoTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = repoTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                String pathToRepo = record.getStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_PATH_TO_REPO_COLUMN_NAME);
                String navigationStructureVersion = record.getStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_NAVIGATION_STRUCTURE_VERSION_COLUMN_NAME);
                String repoName = record.getStringValue(NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_NAME_COLUMN_NAME);

                repository = new Repository(repoName,navigationStructureVersion,pathToRepo);
            }


            return repository;

        } catch (CantLoadTableToMemoryException e) {
            throw  new CantGetRepositoryPathRecordException("CAN'T GET REPOSITORY PATH",e,"","Error loading table");
        }
        catch (Exception e) {
            throw  new CantGetRepositoryPathRecordException("CAN'T GET REPOSITORY PATH",e,"","unknown error");
        }

    }

    /**
     *  get the repository record
     *
     * @param repositoryTable
     * @param skinId
     * @param repositoryName
     * @return
     * @throws CantLoadTableToMemoryException
     * @throws com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.RepositoryNotFoundException
     */
    private DatabaseTableRecord getRepositoryDatabaseTableRecord(DatabaseTable repositoryTable,UUID skinId,String repositoryName) throws CantLoadTableToMemoryException, com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.RepositoryNotFoundException {
        repositoryTable.setUUIDFilter(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);
        repositoryTable.setStringFilter(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_NAME_COLUMN_NAME, repositoryName, DatabaseFilterType.EQUAL);
        repositoryTable.loadToMemory();
        List<DatabaseTableRecord> databaseTableRecordList = repositoryTable.getRecords();

        if (!databaseTableRecordList.isEmpty()) {
            DatabaseTableRecord record = databaseTableRecordList.get(0);
            return record;

        } else {
            database.closeDatabase();
            throw new com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.RepositoryNotFoundException(ProjectNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a repository with that id and name");
        }
    }




    private DatabaseTableRecord getRepositoryDatabaseTableRecord(String repositoryName,UUID skinId) throws com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions.RepositoryNotFoundException, CantGetRepositoryPathRecordException {
        try {
            database.openDatabase();
            DatabaseTable repositoryTable = getRepositoriesTable();
            return getRepositoryDatabaseTableRecord(repositoryTable,skinId,repositoryName);

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetRepositoryPathRecordException(CantGetRepositoryPathRecordException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetRepositoryPathRecordException(CantGetRepositoryPathRecordException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    private DatabaseTable getRepositoriesTable(){
        return database.getTable(com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants.REPOSITORIES_TABLE_NAME);
    }

}
