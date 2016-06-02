package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public class AssetRedeemPointWalletDatabaseFactory {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    public AssetRedeemPointWalletDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());
            createAssetRedeemPointWalletTable(ownerId, database.getDatabaseFactory());
            createAssetRedeemPointWalletBalancesTable(ownerId, database.getDatabaseFactory());
            createStatisticsTable(ownerId, database.getDatabaseFactory());
            //insertInitialBalancesRecord(database);

            return database;
        } catch (CantCreateTableException exception) {
            //catch(CantCreateTableException | CantInsertRecordException exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch (CantCreateDatabaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createAssetRedeemPointWalletTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException {
        try {
            DatabaseTableFactory tableFactory = createAssetRedeemPointWalletTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch (InvalidOwnerIdException exception) {
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private void createStatisticsTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException {
        try {
            DatabaseTableFactory tableFactory = createStatisticsTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch (InvalidOwnerIdException exception) {
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private void createAssetRedeemPointWalletBalancesTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException {
        try {
            DatabaseTableFactory tableFactory = createAssetRedeemPointWalletBalanceTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch (InvalidOwnerIdException exception) {
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private DatabaseTableFactory createAssetRedeemPointWalletTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException {
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_NAME);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_VERIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_FROM_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_ACTOR_TO_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TIME_STAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 200, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);


        return table;
    }

    private DatabaseTableFactory createAssetRedeemPointWalletBalanceTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException {
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, true);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);

        return table;
    }


    private DatabaseTableFactory createStatisticsTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException {
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_TABLE_NAME);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, true);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_POINT_STATISTIC_GENESIS_TRANSACTION_KEY_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_USER_PUBLICKEY_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletRedeemPointDatabaseConstant.ASSET_WALLET_REDEEM_POINT_STATISTIC_REDEMPTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        return table;
    }

//    private void insertInitialBalancesRecord(final Database database) throws CantInsertRecordException{
//        DatabaseTable balancesTable = database.getTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
//        DatabaseTableRecord initialRecord = constructBalanceInitialRecord(balancesTable);
//        balancesTable.insertRecord(initialRecord);
//    }

//    private DatabaseTableRecord constructBalanceInitialRecord(final DatabaseTable balancesTable){
//        DatabaseTableRecord balancesRecord = balancesTable.getEmptyRecord();
//        UUID balanceRecordId = UUID.randomUUID();
//        balancesRecord.setStringValue(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, "");
//        balancesRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, 0);
//        balancesRecord.setLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, 0);
//        return balancesRecord;
//    }
}
