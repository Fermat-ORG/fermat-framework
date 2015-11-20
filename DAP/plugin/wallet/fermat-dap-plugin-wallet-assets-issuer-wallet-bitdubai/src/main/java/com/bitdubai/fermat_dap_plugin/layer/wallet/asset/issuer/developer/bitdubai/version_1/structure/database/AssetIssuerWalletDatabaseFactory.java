package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by franklin on 27/09/15.
 */
public class AssetIssuerWalletDatabaseFactory implements DealsWithPluginDatabaseSystem {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());
            createAssetIssuerWalletTable(ownerId, database.getDatabaseFactory());
            createAssetIssuerWalletBalancesTable(ownerId, database.getDatabaseFactory());
            //insertInitialBalancesRecord(database);
            database.closeDatabase();
            return database;
        } catch(CantCreateTableException exception){
        //catch(CantCreateTableException | CantInsertRecordException exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch(CantCreateDatabaseException exception){
            throw exception;
        } catch(Exception exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createAssetIssuerWalletTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException{
        try{
            DatabaseTableFactory tableFactory = createAssetIssuerWalletTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private void createAssetIssuerWalletBalancesTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException{
        try{
            DatabaseTableFactory tableFactory = createAssetIssuerWalletBalanceTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        }catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private DatabaseTableFactory createAssetIssuerWalletTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_NAME);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, false );
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_VERIFICATION_ID_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_FROM_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_ACTOR_TO_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TIME_STAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 200, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TRANSACTION_HASH_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);


        return table;
    }

    private DatabaseTableFactory createAssetIssuerWalletBalanceTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 255, true);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 150, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);
        table.addColumn(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, false);

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
