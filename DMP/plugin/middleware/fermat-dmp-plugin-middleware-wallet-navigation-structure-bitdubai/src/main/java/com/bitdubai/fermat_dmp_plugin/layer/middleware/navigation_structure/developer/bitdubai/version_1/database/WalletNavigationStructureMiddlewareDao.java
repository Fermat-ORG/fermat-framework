package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantCreateEmptyWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantDeleteNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantListNavigationStructuresException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantUpdateNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.NavigationStructureNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.interfaces.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantGetWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantInitializeWalletNavigationStructureMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.structure.WalletNavigationStructureMiddleware;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 04/08/15.
 * Modified by Manuel Perez on 12/08/2015
 */
public class WalletNavigationStructureMiddlewareDao {

    /**
     * WalletManagerMiddlewareDatabaseDao member variables
     */
    //private UUID pluginId;
    private Database database;
    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     */
    public WalletNavigationStructureMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        //database = openDatabase();
        //database.closeDatabase();
    }

    public void createNavigationStructure(WalletNavigationStructure walletNavigationStructure) throws CantCreateEmptyWalletNavigationStructureException {
        try {
            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            DatabaseTableRecord databaseTableRecord = projectNavigationStructureTable.getEmptyRecord();

            databaseTableRecord.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, walletNavigationStructure.getPublicKey());
            databaseTableRecord.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, walletNavigationStructure.getActivity().toString());
            databaseTableRecord.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY, walletNavigationStructure.getStartActivity().toString());
            databaseTableRecord.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY, walletNavigationStructure.getLastActivity().toString());

            projectNavigationStructureTable.insertRecord(databaseTableRecord);
            this.database.closeDatabase();

        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantCreateEmptyWalletNavigationStructureException(CantCreateEmptyWalletNavigationStructureException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Check the cause");
        } catch (CantInsertRecordException exception) {
            this.database.closeDatabase();
            throw new CantCreateEmptyWalletNavigationStructureException(CantInsertRecordException.DEFAULT_MESSAGE, exception, "Trying to insert the record in the table", "Check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantCreateEmptyWalletNavigationStructureException(CantCreateEmptyWalletNavigationStructureException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }

    }

    public void deleteNavigationStructure(String publicKey) throws CantDeleteNavigationStructureException, NavigationStructureNotFoundException {
        try {
            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, publicKey, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.loadToMemory();

            List<DatabaseTableRecord> records = projectNavigationStructureTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                projectNavigationStructureTable.deleteRecord(record);
                this.database.closeDatabase();

            } else {
                this.database.closeDatabase();
                throw new NavigationStructureNotFoundException(NavigationStructureNotFoundException.DEFAULT_MESSAGE, null, "Cannot find this WalletNavigationStructure with " + publicKey + " id", "Please, check the cause");
            }
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantDeleteNavigationStructureException(CantDeleteNavigationStructureException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Please, check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantDeleteNavigationStructureException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to load the database to memory", "Please, check the cause");
        } catch (CantDeleteRecordException exception) {
            this.database.closeDatabase();
            throw new CantDeleteNavigationStructureException(CantDeleteRecordException.DEFAULT_MESSAGE, exception, "Cannot delete the WalletNavigationStructure", "Please, check the cause");
        } catch (Exception exception) {
            throw new CantDeleteNavigationStructureException(CantDeleteRecordException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletNavigationStructureMiddlewareDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletNavigationStructureMiddlewareDatabaseException {
        try {
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException exception) {
            throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Check the cause");
        } catch (DatabaseNotFoundException exception) {

            WalletNavigationStructureMiddlewareDatabaseFactory walletNavigationStructureMiddlewareDatabaseFactory = new WalletNavigationStructureMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try {
                this.database = walletNavigationStructureMiddlewareDatabaseFactory.createDatabase(ownerId, databaseName);
                this.database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(cantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "Trying to create the tadabase", "Check the cause");
            }
        }
    }

    public List<WalletNavigationStructure> findAllNavigationStructuresByActivity(String activity) throws CantListNavigationStructuresException {
        List<WalletNavigationStructure> walletNavigationStructures = new ArrayList<>();
        try {
            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, activity, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.setFilterOrder(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, DatabaseFilterOrder.ASCENDING);
            projectNavigationStructureTable.loadToMemory();
            List<DatabaseTableRecord> records = projectNavigationStructureTable.getRecords();
            for (DatabaseTableRecord record : records) {

                String id = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
                Activities navigationStructureMiddlewareActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY));
                Activities startActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY));
                Activities lastActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY));

                WalletNavigationStructureMiddleware walletNavigationStructureMiddleware = new WalletNavigationStructureMiddleware(id);
                Activity walletNavigationStructureMiddlewareActivity = new Activity();
                Activity walletNavigationStructureMiddlewareStartActivity = new Activity();
                Activity walletNavigationStructureMiddlewareLastActivity = new Activity();
                walletNavigationStructureMiddleware.setActivity(walletNavigationStructureMiddlewareActivity, navigationStructureMiddlewareActivity);
                walletNavigationStructureMiddleware.setLastActivity(walletNavigationStructureMiddlewareLastActivity, lastActivity);
                walletNavigationStructureMiddleware.setStartActivity(walletNavigationStructureMiddlewareStartActivity, startActivity);

                walletNavigationStructures.add(walletNavigationStructureMiddleware);
            }

            this.database.closeDatabase();
            return walletNavigationStructures;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantListNavigationStructuresException(CantListNavigationStructuresException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Please, check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantListNavigationStructuresException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to load the database to memory", "Please, check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantListNavigationStructuresException(CantListNavigationStructuresException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }
    }

    public WalletNavigationStructure findWalletNavigationStructureById(String id) throws CantGetWalletNavigationStructureException, NavigationStructureNotFoundException {

        try {

            database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_FIRST_KEY_COLUMN, id, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.loadToMemory();
            List<DatabaseTableRecord> records = projectNavigationStructureTable.getRecords();
            if (!records.isEmpty()) {

                DatabaseTableRecord record = records.get(0);

                String navigationStructureId = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
                Activities navigationStructureMiddlewareActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY));
                Activities startActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY));
                Activities lastActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY));

                WalletNavigationStructureMiddleware walletNavigationStructureMiddleware = new WalletNavigationStructureMiddleware(navigationStructureId);
                Activity walletNavigationStructureMiddlewareActivity = new Activity();
                Activity walletNavigationStructureMiddlewareStartActivity = new Activity();
                Activity walletNavigationStructureMiddlewareLastActivity = new Activity();
                walletNavigationStructureMiddleware.setActivity(walletNavigationStructureMiddlewareActivity, navigationStructureMiddlewareActivity);
                walletNavigationStructureMiddleware.setLastActivity(walletNavigationStructureMiddlewareLastActivity, lastActivity);
                walletNavigationStructureMiddleware.setStartActivity(walletNavigationStructureMiddlewareStartActivity, startActivity);

                this.database.closeDatabase();
                return walletNavigationStructureMiddleware;
            } else {
                this.database.closeDatabase();
                throw new NavigationStructureNotFoundException(NavigationStructureNotFoundException.DEFAULT_MESSAGE, null, "Cannot find this WalletNavigationStructure with " + id + " id", "Please, check the cause");
            }
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletNavigationStructureException(CantGetWalletNavigationStructureException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Please, check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantGetWalletNavigationStructureException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to load the database into the memory", "Please, check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantGetWalletNavigationStructureException(CantGetWalletNavigationStructureException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }
    }

    public List<WalletNavigationStructure> findAllNavigationStructuresById(String id) throws CantListNavigationStructuresException {
        List<WalletNavigationStructure> walletNavigationStructures = new ArrayList<>();
        try {
            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, id, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.setFilterOrder(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, DatabaseFilterOrder.ASCENDING);
            projectNavigationStructureTable.loadToMemory();
            List<DatabaseTableRecord> records = projectNavigationStructureTable.getRecords();
            for (DatabaseTableRecord record : records) {

                String walletNavigationStructureId = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
                Activities navigationStructureMiddlewareActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY));
                Activities startActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY));
                Activities lastActivity = Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY));

                WalletNavigationStructureMiddleware walletNavigationStructureMiddleware = new WalletNavigationStructureMiddleware(walletNavigationStructureId);
                Activity walletNavigationStructureMiddlewareActivity = new Activity();
                Activity walletNavigationStructureMiddlewareStartActivity = new Activity();
                Activity walletNavigationStructureMiddlewareLastActivity = new Activity();
                walletNavigationStructureMiddleware.setActivity(walletNavigationStructureMiddlewareActivity, navigationStructureMiddlewareActivity);
                walletNavigationStructureMiddleware.setLastActivity(walletNavigationStructureMiddlewareLastActivity, lastActivity);
                walletNavigationStructureMiddleware.setStartActivity(walletNavigationStructureMiddlewareStartActivity, startActivity);

                walletNavigationStructures.add(walletNavigationStructureMiddleware);
            }
            this.database.closeDatabase();
            return walletNavigationStructures;
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantListNavigationStructuresException(CantListNavigationStructuresException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Please, check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantListNavigationStructuresException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to load the database to memory", "Please, check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantListNavigationStructuresException(CantListNavigationStructuresException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }
    }

    public void updateWalletNavigationStructure(WalletNavigationStructure walletNavigationStructure) throws NavigationStructureNotFoundException, CantUpdateNavigationStructureException {
        try {
            String navigationStructurePublicKey = walletNavigationStructure.getPublicKey();
            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable = this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, navigationStructurePublicKey, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.loadToMemory();

            List<DatabaseTableRecord> records = projectNavigationStructureTable.getRecords();
            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                String recordPublicKey = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
                if (recordPublicKey.equals(navigationStructurePublicKey)) {
                    record.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY, navigationStructurePublicKey);
                }

                String activity = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY);
                if (!activity.equals(walletNavigationStructure.getActivity().toString())) {
                    record.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, walletNavigationStructure.getActivity().toString());
                }

                String lastActivity = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY);
                if (!lastActivity.equals(walletNavigationStructure.getLastActivity().toString())) {
                    record.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY, walletNavigationStructure.getLastActivity().toString());
                }

                String startActivity = record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY);
                if (!startActivity.equals(walletNavigationStructure.getActivity().toString())) {
                    record.setStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY, walletNavigationStructure.getStartActivity().toString());
                }

                projectNavigationStructureTable.updateRecord(record);
            } else {
                database.closeDatabase();
                throw new NavigationStructureNotFoundException(NavigationStructureNotFoundException.DEFAULT_MESSAGE, null, "Cannot find this WalletNavigationStructure with " + navigationStructurePublicKey + " id", "Please, check the cause");
            }
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateNavigationStructureException(CantUpdateNavigationStructureException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Please, check the cause");
        } catch (CantLoadTableToMemoryException exception) {
            this.database.closeDatabase();
            throw new CantUpdateNavigationStructureException(CantLoadTableToMemoryException.DEFAULT_MESSAGE, exception, "Trying to load the database to memory", "Please, check the cause");
        } catch (CantUpdateRecordException e) {
            this.database.closeDatabase();
            throw new NavigationStructureNotFoundException(NavigationStructureNotFoundException.DEFAULT_MESSAGE, null, "Cannot update the WalletNavigationStructure", "Please, check the cause");
        } catch (Exception exception) {
            this.database.closeDatabase();
            throw new CantUpdateNavigationStructureException(CantUpdateNavigationStructureException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Unexpected failure", "Please, check the cause");
        }
    }


    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        //  try {
        //   return pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);
        // } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
        //    throw  new CantExecuteDatabaseOperationException("",exception, "", "Error in database plugin.");
        //}
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        DatabaseTable table = database.getTable(tableName);
        return table;
    }


}