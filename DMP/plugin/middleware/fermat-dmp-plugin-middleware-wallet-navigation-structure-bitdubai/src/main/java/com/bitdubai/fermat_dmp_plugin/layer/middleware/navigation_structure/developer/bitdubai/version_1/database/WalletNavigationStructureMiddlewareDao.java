package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.database;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_navigation_structure.exceptions.CantListNavigationStructuresException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantInitializeWalletNavigationStructureMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * @param pluginDatabaseSystem
     */
    public WalletNavigationStructureMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        //database = openDatabase();
        //database.closeDatabase();
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletNavigationStructureMiddlewareDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletNavigationStructureMiddlewareDatabaseException{

        try{
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        }catch(CantOpenDatabaseException exception){
            throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE,exception, "Trying to open the database","Check the cause");
        }catch(DatabaseNotFoundException exception){

            WalletNavigationStructureMiddlewareDatabaseFactory walletNavigationStructureMiddlewareDatabaseFactory=new WalletNavigationStructureMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try{

                this.database=walletNavigationStructureMiddlewareDatabaseFactory.createDatabase(ownerId,databaseName);
                this.database.closeDatabase();

            }catch(CantCreateDatabaseException cantCreateDatabaseException){

                throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(cantCreateDatabaseException.DEFAULT_MESSAGE,cantCreateDatabaseException,"Trying to create the tadabase","Check the cause");

            }

        }

    }

    public List<WalletNavigationStructure> findAllNavigationStructuresByActivity(String activity) throws CantListNavigationStructuresException{

        List<WalletNavigationStructure> walletNavigationStructures =new ArrayList<>();
        try{

            this.database.openDatabase();
            DatabaseTable projectNavigationStructureTable=this.database.getTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME);
            projectNavigationStructureTable.setStringFilter(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, activity, DatabaseFilterType.EQUAL);
            projectNavigationStructureTable.setFilterOrder(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY, DatabaseFilterOrder.ASCENDING);
            projectNavigationStructureTable.loadToMemory();
            List<DatabaseTableRecord> records =projectNavigationStructureTable.getRecords();
            for (DatabaseTableRecord record : records){

                UUID id = record.getUUIDValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
                Activities startActivity= Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY));
                Activities lastActivity=Activities.getValueFromString(record.getStringValue(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY));
                //Map<Activities, Activity> activities=new HashMap<>();
                //activities.put(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY, startActivity);
                WalletNavigationStructure walletNavigationStructure=new WalletNavigationStructure();

            }

        }catch(CantOpenDatabaseException | DatabaseNotFoundException exception){

            throw new CantListNavigationStructuresException(CantListNavigationStructuresException.DEFAULT_MESSAGE,exception,"Trying to open the database","Check the cause");

        } catch (CantLoadTableToMemoryException exception) {

            this.database.closeDatabase();
            throw new CantListNavigationStructuresException(CantLoadTableToMemoryException.DEFAULT_MESSAGE,exception,"Trying to load the database to memory","Check the cause");

        }
        return null;

    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
      //  try {
         //   return pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);
       // } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
        //    throw  new CantExecuteDatabaseOperationException("",exception, "", "Error in database plugin.");
        //}
        return null;
    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable table = database.getTable(tableName);
        return table;
    }



}
