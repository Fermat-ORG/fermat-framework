package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantReadRecordDataBaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/03/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */

public final class CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp {

    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID                 pluginId            ;

    private Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem database system plug-in reference
     * @param pluginId             plug-in id reference
     */
    public CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp(final PluginDatabaseSystem pluginDatabaseSystem,
                                                                    final UUID                 pluginId            ) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId             = pluginId            ;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCommunicationsNetworkNodeP2PDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CommunicationsNetworkNodeP2PDatabaseFactory communicationsNetworkNodeP2PDatabaseFactory = new CommunicationsNetworkNodeP2PDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = communicationsNetworkNodeP2PDatabaseFactory.createDatabase(pluginId, CommunicationsNetworkNodeP2PDatabaseConstants.DATA_BASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCommunicationsNetworkNodeP2PDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<String> getTableList() {

        List<String> tables = new ArrayList<>();

        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTOR_CATALOG_TRANSACTION_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.ACTORS_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CALLS_LOG_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_ACTOR_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_CLIENTS_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_IN_NETWORK_SERVICE_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_ACTORS_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_REGISTRATION_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CHECKED_NETWORK_SERVICE_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.CLIENTS_CONNECTIONS_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CONNECTIONS_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTION_TABLE_NAME);
        tables.add(CommunicationsNetworkNodeP2PDatabaseConstants.NODES_CATALOG_TRANSACTIONS_PENDING_FOR_PROPAGATION_TABLE_NAME);

        return tables;
    }

    public List<DatabaseTableRecord> getTableContent(final String tableName) {

        try {

            this.initializeDatabase();

            final DatabaseTable selectedTable = database.getTable(tableName);

            selectedTable.loadToMemory();

            final List<DatabaseTableRecord> records = selectedTable.getRecords();

            return records;

        } catch (final CantLoadTableToMemoryException                 |
                       CantInitializeCommunicationsNetworkNodeP2PDatabaseException e) {

            System.err.println(e);

            return new ArrayList<>();

        } catch (final Exception e){

            return new ArrayList<>();
        }
    }

    public final List<DatabaseTableRecord> getTableContent(final String tableName, final Integer offset, final Integer max ){

        try {

            this.initializeDatabase();
            final DatabaseTable table =  database.getTable(tableName);
            table.setFilterOffSet(offset.toString());
            table.setFilterTop(max.toString());
            table.loadToMemory();

            return table.getRecords();

        } catch (final Exception e){

            return new ArrayList<>();
        }
    }

    public final long count(final String tableName){

        try {

            DatabaseTable table =  database.getTable(tableName);
            return table.getCount();

        } catch (final Exception e){

            return 0;
        }
    }


}