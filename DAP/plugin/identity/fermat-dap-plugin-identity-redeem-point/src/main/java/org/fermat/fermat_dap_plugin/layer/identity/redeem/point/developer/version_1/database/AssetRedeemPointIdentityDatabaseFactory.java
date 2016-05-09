package org.fermat.fermat_dap_plugin.layer.identity.redeem.point.developer.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class AssetRedeemPointIdentityDatabaseFactory implements DealsWithErrors, DealsWithPluginDatabaseSystem {


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    public AssetRedeemPointIdentityDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
            database = this.pluginDatabaseSystem.createDatabase(pluginId, AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DB_NAME);
            /**
             * Next, I will add the needed tables.
             */
            DatabaseTableFactory table;
            /**
             * Asset Issuer Identity table.
             */
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, true);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_CONTACT_INFORMATION_COLUMN_NAME    , DatabaseDataType.STRING, 150, false);

            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_COUNTRY_NAME_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_PROVINCE_NAME_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_CITY_NAME_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_POSTAL_CODE_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_STREET_NAME_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);
            table.addColumn(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ADDRESS_HOUSE_NUMBER_COLUMN_NAME    , DatabaseDataType.STRING, 100, false);


            table.addIndex(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_FIRST_KEY_COLUMN);

            databaseFactory.createTable(table);

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            String message = CantCreateDatabaseException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateDatabaseException.getCause();
            String context = "Asset Redeem Point Identity DataBase_Factory: " + cantCreateDatabaseException.getContext();
            String possibleReason = "The exception is thrown the Create Database Asset Issuer Identity 'this.platformDatabaseSystem.createDatabase(\"AssetIssuerIdentity\")'" + cantCreateDatabaseException.getPossibleReason();

            throw new CantCreateDatabaseException(message, cause, context, possibleReason);

        } catch (CantCreateTableException cantCreateTableException) {

            String message = CantCreateTableException.DEFAULT_MESSAGE;
            FermatException cause = cantCreateTableException.getCause();
            String context = "Create Table Asset Redeem Point Identity" + cantCreateTableException.getContext();
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

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }
}
