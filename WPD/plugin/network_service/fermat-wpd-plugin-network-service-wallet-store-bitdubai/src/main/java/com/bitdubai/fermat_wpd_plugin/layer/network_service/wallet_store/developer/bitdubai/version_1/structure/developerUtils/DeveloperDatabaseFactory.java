package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants;

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
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletStoreCatalogDatabaseConstants.WALLET_STORE_DATABASE, databaseId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Designer table columns
         */
        List<String> designerTableColumns = new ArrayList<String>();
        designerTableColumns.add(WalletStoreCatalogDatabaseConstants.DESIGNER_ID_COLUMN_NAME);
        designerTableColumns.add(WalletStoreCatalogDatabaseConstants.DESIGNER_NAME_COLUMN_NAME);
        designerTableColumns.add(WalletStoreCatalogDatabaseConstants.DESIGNER_PUBLICKEY_COLUMN_NAME);

        /**
         * Designer table
         */
        DeveloperDatabaseTable designerTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.DESIGNER_TABLE_NAME, designerTableColumns);
        tables.add(designerTable);

        /**
         * Developer table columns
         */
        List<String> developerTableColumns = new ArrayList<String>();
        developerTableColumns.add(WalletStoreCatalogDatabaseConstants.DEVELOPER_ID_COLUMN_NAME);
        developerTableColumns.add(WalletStoreCatalogDatabaseConstants.DEVELOPER_NAME_COLUMN_NAME);
        developerTableColumns.add(WalletStoreCatalogDatabaseConstants.DEVELOPER_PUBLICKEY_COLUMN_NAME);

        /**
         * Developer table
         */
        DeveloperDatabaseTable developerTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.DEVELOPER_TABLE_NAME, developerTableColumns);
        tables.add(developerTable);

        /**
         * item  columns
         */
        List<String> itemTableColumns = new ArrayList<String>();
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_ID_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_NAME_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_CATEGORY_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_DESCRIPTION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_SIZE_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_VERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMINITIALVERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_PLATFORMFINALVERSION_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_DEVELOPER_ID_COLUMN_NAME);
        itemTableColumns.add(WalletStoreCatalogDatabaseConstants.ITEM_PUBLISHER_WEB_SITE_URL_COLUMN_NAME);

        /**
         * item table
         */
        DeveloperDatabaseTable itemTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.ITEM_TABLE_NAME, itemTableColumns);
        tables.add(itemTable);

        /**
         * translator columns
         */
        List<String> translatorTableColumns = new ArrayList<String>();
        translatorTableColumns.add(WalletStoreCatalogDatabaseConstants.TRANSLATOR_ID_COLUMN_NAME);
        translatorTableColumns.add(WalletStoreCatalogDatabaseConstants.TRANSLATOR_NAME_COLUMN_NAME);
        translatorTableColumns.add(WalletStoreCatalogDatabaseConstants.TRANSLATOR_PUBLICKEY_COLUMN_NAME);

        /**
         * translator table
         */
        DeveloperDatabaseTable translatorTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.TRANSLATOR_TABLE_NAME, translatorTableColumns);
        tables.add(translatorTable);

        /**
         * Walletlanguage columns
         */
        List<String> walletLanguageTableColumns = new ArrayList<String>();
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_NAME_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_LABEL_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_VERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETINITIALVERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_WALLETFINALVERSION_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_FILESIZE_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TRANSLATORID_COLUMN_NAME);
        walletLanguageTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_ISDEFAULT_COLUMN_NAME);

        /**
         * walletLanguage table
         */
        DeveloperDatabaseTable walletLanguageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETLANGUAGE_TABLE_NAME, walletLanguageTableColumns);
        tables.add(walletLanguageTable);

        /**
         * WalletSkin columns
         */
        List<String> walletSkinTableColumns = new ArrayList<String>();
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_NAME_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_VERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETINITIALVERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_WALLETFINALVERSION_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SIZE_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_DESIGNERID_COLUMN_NAME);
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_ISDEFAULT_COLUMN_NAME);
        //New column on 06/08/2015
        walletSkinTableColumns.add(WalletStoreCatalogDatabaseConstants.WALLETSKIN_SCREEN_SIZE);

        /**
         * walletSkin table
         */
        DeveloperDatabaseTable walletSkinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletStoreCatalogDatabaseConstants.WALLETSKIN_TABLE_NAME, walletSkinTableColumns);
        tables.add(walletSkinTable);

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
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
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
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
