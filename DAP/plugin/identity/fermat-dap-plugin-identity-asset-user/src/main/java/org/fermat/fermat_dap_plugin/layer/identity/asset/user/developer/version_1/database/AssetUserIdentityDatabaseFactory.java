package org.fermat.fermat_dap_plugin.layer.identity.asset.user.developer.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;

import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class AssetUserIdentityDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    public AssetUserIdentityDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * <p>Method that create a new database and her tables.
     *
     * @return Object database.
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID pluginId) throws CantCreateDatabaseException {
        Database database;
        /**
         * I will create the database where I am going to store the information of this User.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(pluginId, AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_DB_NAME);
            /**
             * Next, I will add the needed tables.
             */
            DatabaseTableFactory table;
            /**
             * Asset Issuer Identity table.
             */
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_TABLE_NAME);

            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, true);
            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, false);

            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_COUNTRY_KEY_COLUMN, DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_CITY_KEY_COLUMN, DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_STATE_KEY_COLUMN, DatabaseDataType.STRING, 100, false);

            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_ACCURACY_KEY_COLUMN, DatabaseDataType.INTEGER, 20, false);
            table.addColumn(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FREQUENCY_KEY_COLUMN, DatabaseDataType.STRING, 20, false);

            table.addIndex(AssetUserIdentityDatabaseConstants.ASSET_USER_IDENTITY_FIRST_KEY_COLUMN);

            databaseFactory.createTable(table);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateDatabaseException.getCause();
            String context = "Asset Issuer Identity DataBase_Factory: " + cantCreateDatabaseException.getContext();
            String possibleReason = "The exception is thrown the Create Database Asset Issuer Identity 'this.platformDatabaseSystem.createDatabase(\"AssetIssuerIdentity\")'" + cantCreateDatabaseException.getPossibleReason();

            throw new CantCreateDatabaseException(message, cause, context, possibleReason);

        } catch (CantCreateTableException cantCreateTableException) {

            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateTableException.getCause();
            String context = "Create Table Asset Issuer Identity" + cantCreateTableException.getContext();
            String possibleReason = "The exception is generated when creating the table Asset Issuer Identity - DatabaseFactory.createTable(table) " + cantCreateTableException.getPossibleReason();

            throw new CantCreateDatabaseException(message, cause, context, possibleReason);

        } catch (Exception exception) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
        return database;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
