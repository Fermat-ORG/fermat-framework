package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreNetworkServiceDatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 7/22/15.
 */
public class DeveloperDatabaseFactory {
    String databaseId;

    public DeveloperDatabaseFactory(String databaseId) {
        this.databaseId = databaseId;
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletStoreNetworkServiceDatabaseConstants.WALLET_STORE_DATABASE, databaseId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Designer table columns
         */
        List<String> designerTableColumns = new ArrayList<String>();
        designerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_ID_COLUMN_NAME);
        designerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_NAME_COLUMN_NAME);
        designerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME);

        /**
         * Designer table
         */
        DeveloperDatabaseTable  designerTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DESIGNER_TABLE_NAME, designerTableColumns);
        tables.add(designerTable);


        /**
         * Developer table columns
         */
        List<String> developerTableColumns = new ArrayList<String>();
        developerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_ID_COLUMN_NAME);
        developerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME);
        developerTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME);

        /**
         * Developer table
         */
        DeveloperDatabaseTable  developerTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.DEVELOPER_TABLE_NAME, developerTableColumns);
        tables.add(developerTable);

        /**
         * item  columns
         */
        List<String> itemTableColumns = new ArrayList<String>();
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_ID_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_NAME_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_SIZE_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_VERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME);




        /**
         * item table
         */
        DeveloperDatabaseTable  itemTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.ITEM_TABLE_NAME, itemTableColumns );
        tables.add(itemTable );


        /**
         * translator columns
         */
        List<String> translatorTableColumns = new ArrayList<String>();
        translatorTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME);
        translatorTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME);
        translatorTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME);




        /**
         * translator table
         */
        DeveloperDatabaseTable  translatorTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.TRANSLATOR_TABLE_NAME, translatorTableColumns);
        tables.add(translatorTable);


        /**
         * Walletlanguage columns
         */
        List<String> walletLanguageTableColumns = new ArrayList<String>();
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_URL_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME);





        /**
         * walletLanguage table
         */
        DeveloperDatabaseTable  walletLanguageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, walletLanguageTableColumns);
        tables.add(walletLanguageTable );


        /**
         * WalletSkin columns
         */
        List<String> walletSkinTableColumns = new ArrayList<String>();
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_URL_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME);

        walletSkinTableColumns.add(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_URL_COLUMN_NAME);


        /**
         * walletSkin table
         */
        DeveloperDatabaseTable  walletSkinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreNetworkServiceDatabaseConstants.WALLETSKIN_TABLE_NAME, walletLanguageTableColumns);
        tables.add(walletSkinTable);

        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,  Database database, DeveloperDatabaseTable developerDatabaseTable) {
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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        for (DatabaseTableRecord row: records){
            /**
             * for each row in the table list
             */
            List<String> developerRow = new ArrayList<String>();
            for (DatabaseRecord field : row.getValues()){
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));

        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }
}
