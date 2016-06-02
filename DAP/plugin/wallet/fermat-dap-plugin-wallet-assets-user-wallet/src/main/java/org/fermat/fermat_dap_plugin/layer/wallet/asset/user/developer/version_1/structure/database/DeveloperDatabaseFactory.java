package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franklin on 05/10/15.
 */
public class DeveloperDatabaseFactory {
    String pluginId;
    List<String> walletsIssuer;

    public DeveloperDatabaseFactory(String pluginId, List<String> walletsIssuer) {
        this.pluginId = pluginId;
        this.walletsIssuer = walletsIssuer;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for each walletId, so we will return all their names.
         * Remember that a database name in this plug-in is the internal wallet id.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        for (String databaseName : this.walletsIssuer)
            databases.add(developerObjectFactory.getNewDeveloperDatabase(databaseName, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();


        /*
         * We only have one table in each database, let's complete it
         */
        List<String> assetWalletIssuerColumns = new ArrayList<>();
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_ID_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_PUBLIC_KEY_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ASSET_CRYPTO_ADDRESS_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_AMOUNT_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_VERIFICATION_ID_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_FROM_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESS_TO_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_FROM_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ACTOR_TO_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TIME_STAMP_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_MEMO_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TRANSACTION_HASH_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_RUNNING_BOOK_BALANCE_COLUMN_NAME);


        /**
         * assetIssuerWalletColumns table
         */
        DeveloperDatabaseTable cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_TABLE_NAME, assetWalletIssuerColumns);
        tables.add(cryptoTransactionsTable);

        /**
         * Added new table AssetIssuerWalletTotalBalances
         */
        List<String> assetIssuerWalletTotalBalancesColumns = new ArrayList<>();
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_ASSET_PUBLIC_KEY_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_DESCRIPTION_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_BOOK_BALANCE_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_AVAILABLE_BALANCE_COLUMN_NAME);
        assetIssuerWalletTotalBalancesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_QUANTITY_BOOK_BALANCE_COLUMN_NAME);

        /**
         * AssetIssuerWalletTotalBalanceColumns table
         */
        DeveloperDatabaseTable assetIssuerWalletWalletTotalBalances = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_BALANCE_TABLE_NAME, assetIssuerWalletTotalBalancesColumns);
        tables.add(assetIssuerWalletWalletTotalBalances);

        /**
         * Added new table AssetIssuerWalletTotalBalances
         */
        List<String> metadataLockColumns = new ArrayList<>();
        metadataLockColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_METADATA_ID_COLUMN_NAME);
        metadataLockColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_GENESIS_TX_COLUMN_NAME);
        metadataLockColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_STATUS_COLUMN_NAME);
        /**
         * AssetIssuerWalletTotalBalanceColumns table
         */
        DeveloperDatabaseTable metadataLockTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_METADATA_LOCK_TABLE_NAME, metadataLockColumns);
        tables.add(metadataLockTable);

        List<String> addressesColumns = new ArrayList<>();
        addressesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_ID_COLUMN_NAME);
        addressesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_ASSET_PUBLICKEY_COLUMN_NAME);
        addressesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME);
        addressesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME);
        addressesColumns.add(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_AVAILABLE_COLUMN_NAME);

        DeveloperDatabaseTable addressesTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetUserWalletDatabaseConstant.ASSET_WALLET_USER_ADDRESSES_TABLE_NAME, addressesColumns);
        tables.add(addressesTable);
        return tables;
    }

    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()) {
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e) {
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
