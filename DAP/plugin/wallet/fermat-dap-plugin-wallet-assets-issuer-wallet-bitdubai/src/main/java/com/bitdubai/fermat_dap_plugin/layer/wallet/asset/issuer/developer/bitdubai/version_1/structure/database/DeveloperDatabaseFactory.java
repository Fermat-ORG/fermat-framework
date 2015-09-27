package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franklin on 27/09/15.
 */
public class DeveloperDatabaseFactory {
    String pluginId;
    List<String> walletsIssuer;

    public DeveloperDatabaseFactory (String pluginId, List<String> walletsIssuer){
        this.pluginId = pluginId;
        this.walletsIssuer = walletsIssuer;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * We have one database for each walletId, so we will return all their names.
         * Remember that a database name in this plug-in is the internal wallet id.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        for(String databaseName : this.walletsIssuer)
            databases.add(developerObjectFactory.getNewDeveloperDatabase(databaseName, this.pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();


        /*
         * We only have one table in each database, let's complete it
         */
        List<String> assetWalletIssuerColumns = new ArrayList<>();
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_FROM_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_FROM_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_TO_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ACTOR_TO_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ADDRESS_FROM_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__ADDRESS_TO_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__AMOUNT_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__BALANCE_TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__RUNNING_BOOK_BALANCE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TIME_STAMP_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TRANSACTION_HASH_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__TYPE_COLUMN_NAME);
        assetWalletIssuerColumns.add(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER__VERIFICATION_ID_COLUMN_NAME);

        /**
         * assetIssuerWalletColumns table
         */
        DeveloperDatabaseTable  cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetWalletIssuerDatabaseConstant.ASSET_WALLET_ISSUER_TABLE_NAME, assetWalletIssuerColumns);
        tables.add(cryptoTransactionsTable);

        return tables;
    }
}
