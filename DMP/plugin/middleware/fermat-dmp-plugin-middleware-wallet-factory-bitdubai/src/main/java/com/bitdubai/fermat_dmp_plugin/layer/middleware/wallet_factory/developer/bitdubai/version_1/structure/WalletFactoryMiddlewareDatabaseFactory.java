package   com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.Wallet FactoryMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletFactoryMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;

            /**
             * Create Project table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.PROJECT_FIRST_KEY_COLUMN);

            /**
             * Create Project Proposal table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FIRST_KEY_COLUMN);

            /**
             * Create Project Skin table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_FIRST_KEY_COLUMN);

            /**
             * Create Project Resource table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_TABLE_NAME);

            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_RESOURCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_PROJECT_SKIN_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletFactoryMiddlewareDatabaseConstants.PROJECT_RESOURCE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                ((DatabaseFactory) database).createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}