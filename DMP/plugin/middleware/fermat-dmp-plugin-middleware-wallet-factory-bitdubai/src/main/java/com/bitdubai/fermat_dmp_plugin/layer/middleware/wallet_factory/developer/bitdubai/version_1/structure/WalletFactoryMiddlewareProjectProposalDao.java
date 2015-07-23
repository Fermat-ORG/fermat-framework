package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantUpdateWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProjectNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
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
public class WalletFactoryMiddlewareProjectProposalDao implements DealsWithPluginDatabaseSystem {

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
    public WalletFactoryMiddlewareProjectProposalDao(PluginDatabaseSystem pluginDatabaseSystem) {
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

                UUID id = record.getUUIDValue(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
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
        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){
            throw new CantDeleteWalletFactoryProjectProposalException(CantDeleteWalletFactoryProjectProposalException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
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
