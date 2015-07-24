package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
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
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareDao</code> have
 * all methods implementation to access the data base (CRUD)
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareDao implements DealsWithPluginDatabaseSystem {

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
    public WalletFactoryMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
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
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));

                WalletFactoryMiddlewareProject walletFactoryProject = new WalletFactoryMiddlewareProject(id, name, developerPublicKey, walletType);

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
                Wallets walletType = Wallets.getByCode(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME));

                WalletFactoryProject walletFactoryProject = new WalletFactoryMiddlewareProject(id, name, developerPublicKey, walletType);

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
                database.closeDatabase();
                return new WalletFactoryMiddlewareProject(id, name, developerPublicKey, walletType);
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

    public WalletFactoryProject findProjectById(UUID id) throws CantGetWalletFactoryProjectException, ProjectNotFoundException {
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

                database.closeDatabase();
                return new WalletFactoryMiddlewareProject(id, name, developerPublicKey, walletType);
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

        if (walletFactoryProject == null && walletFactoryProject.getId() != null && walletFactoryProject.getName() != null && walletFactoryProject.getDeveloperPublicKey() != null && walletFactoryProject.getType() != null){
            throw new CantCreateWalletFactoryProjectException(CantCreateWalletFactoryProjectException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, name, type and developer public key.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectTable.getEmptyRecord();
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, walletFactoryProject.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, walletFactoryProject.getName());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME, walletFactoryProject.getDeveloperPublicKey());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME, walletFactoryProject.getType().getCode());

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
     * Method that list the all entities on the database.
     *
     *  @return All Wallet Factory Projects Proposals who belongs to a developer.
     */
    public List<WalletFactoryProjectProposal> findAllProposalByProject(WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectProposalsException {

        List<WalletFactoryProjectProposal> walletFactoryProjectProposalList = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectProposalTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            projectProposalTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME, walletFactoryProject.getId(), DatabaseFilterType.EQUAL);
            projectProposalTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectProposalTable.loadToMemory();

            List<DatabaseTableRecord> records = projectProposalTable.getRecords();

            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME);
                String alias = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.fromValue(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME));

                WalletFactoryProjectProposal walletFactoryProjectProposal = new WalletFactoryMiddlewareProjectProposal(id, alias, state, walletFactoryProject);

                walletFactoryProjectProposalList.add(walletFactoryProjectProposal);
            }
            database.closeDatabase();
            return walletFactoryProjectProposalList;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectProposalsException(CantGetWalletFactoryProjectProposalsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectProposalsException(CantGetWalletFactoryProjectProposalsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletFactoryProjectProposal findProposalById(UUID id) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                WalletFactoryProject walletFactoryMiddlewareProject;
                String alias = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.fromValue(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME));
                try {
                    UUID projectId = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME);
                    walletFactoryMiddlewareProject = findProjectById(projectId);
                } catch (CantGetWalletFactoryProjectException|ProjectNotFoundException e) {
                    throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "Can't find the project associated to this proposal.", "");
                }
                database.closeDatabase();
                return new WalletFactoryMiddlewareProjectProposal(id, alias, state, walletFactoryMiddlewareProject);
            } else {
                database.closeDatabase();
                throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project proposal with this alias.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @param alias
     * @param walletFactoryProject
     * @return Wallet Factory Projects Proposals of a project that matches with the given name.
     * @throws CantGetWalletFactoryProjectException
     */
    public WalletFactoryProjectProposal findProposalByNameAndProject(String alias, WalletFactoryProject walletFactoryProject) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            projectTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME, walletFactoryProject.getId(), DatabaseFilterType.EQUAL);
            projectTable.setStringFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            projectTable.loadToMemory();

            List<DatabaseTableRecord> records = projectTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
                FactoryProjectState state = FactoryProjectState.fromValue(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME));

                database.closeDatabase();
                return new WalletFactoryMiddlewareProjectProposal(id, alias, state, walletFactoryProject);
            } else {
                database.closeDatabase();
                throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project proposal with this alias.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that create a new entity in the data base.
     *
     *  @param walletFactoryProjectProposal WalletFactoryProjectProposal to create.
     */
    public void createProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCreateWalletFactoryProjectProposalException {

        if (walletFactoryProjectProposal == null &&
                walletFactoryProjectProposal.getId() != null &&
                walletFactoryProjectProposal.getAlias() != null &&
                walletFactoryProjectProposal.getState() != null &&
                walletFactoryProjectProposal.getProject().getId() != null){
            throw new CantCreateWalletFactoryProjectProposalException(CantCreateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, alias, state and get project id.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectProposalTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectProposalTable.getEmptyRecord();
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectProposal.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME, walletFactoryProjectProposal.getAlias());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME, walletFactoryProjectProposal.getState().value());
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME, walletFactoryProjectProposal.getProject().getId());

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(projectProposalTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateWalletFactoryProjectProposalException(CantCreateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantCreateWalletFactoryProjectProposalException(CantCreateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that update a WalletFactoryProjectProposal in the database.
     *
     *  @param walletFactoryProjectProposal WalletFactoryProjectProposal to update.
     */
    public void updateProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantUpdateWalletFactoryProjectProposalException, ProposalNotFoundException {

        if (walletFactoryProjectProposal == null && walletFactoryProjectProposal.getId() != null){
            throw new CantUpdateWalletFactoryProjectProposalException(CantUpdateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, null, "The entity is required, can not be null.", "Check id value.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectProposalTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            projectProposalTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectProposal.getId(), DatabaseFilterType.EQUAL);
            projectProposalTable.loadToMemory();

            List<DatabaseTableRecord> databaseTableRecordList = projectProposalTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);
                if (walletFactoryProjectProposal.getAlias() != null)
                    record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME, walletFactoryProjectProposal.getAlias());
                if (walletFactoryProjectProposal.getState() != null)
                    record.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME, walletFactoryProjectProposal.getState().value());

                DatabaseTransaction transaction = database.newTransaction();
                transaction.addRecordToUpdate(projectProposalTable, record);
                database.executeTransaction(transaction);
                database.closeDatabase();
            } else {
                database.closeDatabase();
                throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project with that id");
            }

        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectProposalException(CantUpdateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateWalletFactoryProjectProposalException(CantUpdateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantUpdateWalletFactoryProjectProposalException(CantUpdateWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that delete a entity in the database.
     *
     *  @param id UUID project proposal id.
     * */
    public void deleteProposal(UUID id) throws CantDeleteWalletFactoryProjectProposalException, ProposalNotFoundException {

        if (id == null){
            throw new CantDeleteWalletFactoryProjectProposalException(CantDeleteWalletFactoryProjectProposalException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable projectProposalTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);
            projectProposalTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectProposalTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectProposalTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                projectProposalTable.deleteRecord(record);
            } else {
                database.closeDatabase();
                throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project proposal with that id");
            }
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectProposalException(CantDeleteWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectProposalException(CantDeleteWalletFactoryProjectProposalException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantDeleteWalletFactoryProjectProposalException(CantDeleteWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     *  @return All Wallet Factory Projects Proposals who belongs to a developer.
     */
    public List<WalletFactoryProjectSkin> findAllSkinsByProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectSkinsException {

        List<WalletFactoryProjectSkin> walletFactoryProjectSkins = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectProposal.getId(), DatabaseFilterType.EQUAL);
            projectSkinTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME);
                String designerPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);
                Version version = new Version(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_VERSION_COLUMN_NAME));

                WalletFactoryProjectSkin walletFactoryProjectSkin = new WalletFactoryMiddlewareProjectSkin(id, name, designerPublicKey, version, walletFactoryProjectProposal);

                walletFactoryProjectSkins.add(walletFactoryProjectSkin);
            }
            database.closeDatabase();
            return walletFactoryProjectSkins;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectSkinsException(CantGetWalletFactoryProjectSkinsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectSkinsException(CantGetWalletFactoryProjectSkinsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletFactoryProjectSkin findSkinById(UUID id) throws CantGetWalletFactoryProjectSkinException, SkinNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                WalletFactoryProjectProposal walletFactoryProjectProposal;

                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME);
                String designerPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);
                Version version = new Version(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_VERSION_COLUMN_NAME));

                try {
                    UUID proposalId = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME);
                    walletFactoryProjectProposal = findProposalById(proposalId);
                } catch (CantGetWalletFactoryProjectProposalException|ProposalNotFoundException e) {
                    throw new CantGetWalletFactoryProjectSkinException(CantGetWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "Can't find the project associated to this proposal.", "");
                }
                database.closeDatabase();
                return new WalletFactoryMiddlewareProjectSkin(id, name, designerPublicKey, version, walletFactoryProjectProposal);
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project proposal with this alias.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectSkinException(CantGetWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectSkinException(CantGetWalletFactoryProjectSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void createSkin(WalletFactoryProjectSkin walletFactoryProjectSkin) throws CantCreateEmptyWalletFactoryProjectSkinException {

        if (walletFactoryProjectSkin == null &&
                walletFactoryProjectSkin.getId() != null &&
                walletFactoryProjectSkin.getDesignerPublicKey() != null &&
                walletFactoryProjectSkin.getName() != null &&
                walletFactoryProjectSkin.getVersion() != null &&
                walletFactoryProjectSkin.getWalletFactoryProjectProposal() != null){
            throw new CantCreateEmptyWalletFactoryProjectSkinException(CantCreateEmptyWalletFactoryProjectSkinException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, alias, state and get proposal id.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectSkinTable.getEmptyRecord();

            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME, walletFactoryProjectSkin.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME, walletFactoryProjectSkin.getName());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME, walletFactoryProjectSkin.getDesignerPublicKey());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_VERSION_COLUMN_NAME, walletFactoryProjectSkin.getVersion().toString());
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectSkin.getWalletFactoryProjectProposal().getId());

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(projectSkinTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateEmptyWalletFactoryProjectSkinException(CantCreateEmptyWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantCreateEmptyWalletFactoryProjectSkinException(CantCreateEmptyWalletFactoryProjectSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void deleteSkin(UUID id) throws CantDeleteWalletFactoryProjectSkinException, SkinNotFoundException {

        if (id == null){
            throw new CantDeleteWalletFactoryProjectSkinException(CantDeleteWalletFactoryProjectSkinException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectSkinTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                projectSkinTable.deleteRecord(record);
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project skin with that id");
            }
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectSkinException(CantDeleteWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectSkinException(CantDeleteWalletFactoryProjectSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantDeleteWalletFactoryProjectSkinException(CantDeleteWalletFactoryProjectSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     *  @return All Wallet Factory Projects Proposals who belongs to a developer.
     */
    public List<WalletFactoryProjectLanguage> findAllLanguagesByProposal(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantGetWalletFactoryProjectLanguagesException {

        List<WalletFactoryProjectLanguage> walletFactoryProjectLanguages = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectProposal.getId(), DatabaseFilterType.EQUAL);
            projectLanguageTable.setFilterOrder(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_NAME_COLUMN_NAME);
                String translatorPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME);
                Languages type = Languages.fromValue(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME));
                Version version = new Version(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_VERSION_COLUMN_NAME));

                WalletFactoryProjectLanguage walletFactoryProjectLanguage = new WalletFactoryMiddlewareProjectLanguage(id, name, type, version, translatorPublicKey, walletFactoryProjectProposal);

                walletFactoryProjectLanguages.add(walletFactoryProjectLanguage);
            }
            database.closeDatabase();
            return walletFactoryProjectLanguages;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectLanguagesException(CantGetWalletFactoryProjectLanguagesException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectLanguagesException(CantGetWalletFactoryProjectLanguagesException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletFactoryProjectLanguage findLanguageById(UUID id) throws CantGetWalletFactoryProjectLanguageException, LanguageNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectLanguageTable.loadToMemory();

            List<DatabaseTableRecord> records = projectLanguageTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);
                WalletFactoryProjectProposal walletFactoryProjectProposal;

                String name = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_NAME_COLUMN_NAME);
                String translatorPublicKey = record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME);
                Languages type = Languages.fromValue(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME));
                Version version = new Version(record.getStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_VERSION_COLUMN_NAME));

                try {
                    UUID proposalId = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME);
                    walletFactoryProjectProposal = findProposalById(proposalId);
                } catch (CantGetWalletFactoryProjectProposalException|ProposalNotFoundException e) {
                    throw new CantGetWalletFactoryProjectLanguageException(CantGetWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "Can't find the project associated to this proposal.", "");
                }
                database.closeDatabase();
                return new WalletFactoryMiddlewareProjectLanguage(id, name, type, version, translatorPublicKey, walletFactoryProjectProposal);
            } else {
                database.closeDatabase();
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project language with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletFactoryProjectLanguageException(CantGetWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantGetWalletFactoryProjectLanguageException(CantGetWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void createLanguage(WalletFactoryProjectLanguage walletFactoryProjectLanguage) throws CantCreateEmptyWalletFactoryProjectLanguageException {

        if (walletFactoryProjectLanguage == null &&
                walletFactoryProjectLanguage.getId() != null &&
                walletFactoryProjectLanguage.getTranslatorPublicKey() != null &&
                walletFactoryProjectLanguage.getName() != null &&
                walletFactoryProjectLanguage.getVersion() != null &&
                walletFactoryProjectLanguage.getWalletFactoryProjectProposal() != null){
            throw new CantCreateEmptyWalletFactoryProjectLanguageException(CantCreateEmptyWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, null, "The entity is required, can not be null", "Check the id, alias, state and get proposal id.");
        }

        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TABLE_NAME);
            DatabaseTableRecord entityRecord = projectLanguageTable.getEmptyRecord();

            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_ID_COLUMN_NAME, walletFactoryProjectLanguage.getId());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_NAME_COLUMN_NAME, walletFactoryProjectLanguage.getName());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME, walletFactoryProjectLanguage.getTranslatorPublicKey());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME, walletFactoryProjectLanguage.getType().value());
            entityRecord.setStringValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_VERSION_COLUMN_NAME, walletFactoryProjectLanguage.getVersion().toString());
            entityRecord.setUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_PROJECT_PROPOSAL_ID_COLUMN_NAME, walletFactoryProjectLanguage.getWalletFactoryProjectProposal().getId());

            DatabaseTransaction transaction = database.newTransaction();
            transaction.addRecordToInsert(projectLanguageTable, entityRecord);
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantCreateEmptyWalletFactoryProjectLanguageException(CantCreateEmptyWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot insert the record.");
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantCreateEmptyWalletFactoryProjectLanguageException(CantCreateEmptyWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void deleteLanguage(UUID id) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException {

        if (id == null){
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, null, "", "The id is required, can not be null");
        }

        try {
            database.openDatabase();
            DatabaseTable projectLanguageTable = database.getTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TABLE_NAME);
            projectLanguageTable.setUUIDFilter(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectLanguageTable.loadToMemory();
            List<DatabaseTableRecord> databaseTableRecordList = projectLanguageTable.getRecords();

            if (!databaseTableRecordList.isEmpty()) {
                DatabaseTableRecord record = databaseTableRecordList.get(0);

                projectLanguageTable.deleteRecord(record);
            } else {
                database.closeDatabase();
                throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a project language with that id");
            }
            database.closeDatabase();
        } catch (CantDeleteRecordException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot delete the record.");

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
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
