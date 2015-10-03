package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantDeleteWalletSkinException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantGetInstalledWalletsException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletLanguageException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletSkinException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantUpdateWalletNameException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledLanguage;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledSkin;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledWallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 04/08/15.
 */
public class WalletManagerMiddlewareDao {

    /**
     * WalletManagerMiddlewareDatabaseDao member variables
     */
    UUID pluginId;
    Database database;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     */
    public WalletManagerMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantExecuteDatabaseOperationException {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        database = openDatabase();
        database.closeDatabase();
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        DatabaseTable table = database.getTable(tableName);
        return table;
    }

    public List<InstalledWallet> getInstalledWallets() throws CantGetInstalledWalletsException {
        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {

                InstalledWallet installedWallet = new WalletManagerMiddlewareInstalledWallet(WalletCategory.getByCode(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME)),
                        getInstalledSkins(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        getInstalledLanguages(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME),
                        new Version(1, 0, 0),
                        WalletType.valueOf(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME),
                        record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME)
                );

                lstInstalledWallet.add(installedWallet);

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED WALLETS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED WALLETS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return lstInstalledWallet;
    }

    public  InstalledWallet getInstalledWallet(String walletPublicKey) throws CantGetInstalledWalletsException {
        InstalledWallet installedWallet = null;
        try{
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);

            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME,walletPublicKey,DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();




            List<DatabaseTableRecord> records = databaseTable.getRecords();

            if(!records.isEmpty()) {

                DatabaseTableRecord databaseTableRecord = records.get(0);


                installedWallet= new WalletManagerMiddlewareInstalledWallet(WalletCategory.getByCode(databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME)),
                        getInstalledSkins(databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        getInstalledLanguages(databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME),
                        new Version(1,0,0),
                        WalletType.valueOf(databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME)),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME),
                        databaseTableRecord.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME),
                        databaseTableRecord.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME));
            }


            return installedWallet;

        } catch (CantLoadTableToMemoryException e1) {
            e1.printStackTrace();
        } catch (InvalidParameterException e1) {
            e1.printStackTrace();
        } catch (CantExecuteDatabaseOperationException e1) {
            e1.printStackTrace();
        }

        database.closeDatabase();


        return installedWallet;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, null, "Error in database plugin.");
        }
    }

    public InstalledWallet getInstalledWallet(UUID walletIdInThisDevice) throws CantGetInstalledWalletsException {

        InstalledWallet installedWallet = null;
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletIdInThisDevice.toString(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            if (records.isEmpty()) {

                database.closeDatabase();
                throw new CantGetInstalledWalletsException(CantGetInstalledWalletsException.DEFAULT_MESSAGE, null, "Can't find the walled with ID:" + walletIdInThisDevice, "Please, check the cause");

            }
            for (DatabaseTableRecord record : records) {

                installedWallet = new WalletManagerMiddlewareInstalledWallet(WalletCategory.getByCode(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME)),
                        getInstalledSkins(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        getInstalledLanguages(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME),
                        new Version(1, 0, 0),
                        WalletType.valueOf(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME),
                        record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME)
                );
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED WALLETS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED WALLETS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return installedWallet;
    }

    public InstalledWallet getInstalledWalletByCatalogueId(UUID walletCatalogueId) throws CantGetInstalledWalletsException {

        InstalledWallet installedWallet = null;
        //Logger LOG = Logger.getGlobal();
        //LOG.info("DENTRO DEL DAO:...");
        try {
            database = openDatabase();

            //LOG.info("DB:"+database.toString());
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            //LOG.info("TABLE:" + databaseTable.isTableExists());
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString(), DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            //LOG.info("TABLE loaded:");
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            //LOG.info("Records:"+records.size());
            if (records.isEmpty()) {

                database.closeDatabase();
                throw new CantGetInstalledWalletsException(CantGetInstalledWalletsException.DEFAULT_MESSAGE, null, "Can't find the walled with ID:" + walletCatalogueId, "Please, check the cause");

            }
            for (DatabaseTableRecord record : records) {

                installedWallet = new WalletManagerMiddlewareInstalledWallet(WalletCategory.getByCode(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME)),
                        getInstalledSkins(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        getInstalledLanguages(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME),
                        new Version(1, 0, 0),
                        WalletType.valueOf(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME),
                        record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME)
                );
                //LOG.info("record:"+record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INTALLEd WALLETS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INTALLEd WALLETS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }
        return installedWallet;
    }


    public void persistWallet(String walletPublicKey, String walletPrivateKey, String deviceUserPublicKey, WalletCategory walletCategory, String walletName, String walletIconName, String walletIdentifier, UUID walletCatalogueId, Version walletVersion, String developerName, String screenSize, String navigationVersion) throws CantPersistWalletException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletPublicKey);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PRIVATE_KEY_COLUMN_NAME, walletPrivateKey);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUserPublicKey);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME, walletCategory.getCode());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME, walletName);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME, walletIdentifier);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME, walletIconName);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_VERSION_COLUMN_NAME, String.valueOf(walletVersion.getMajor()));
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME, developerName);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME, screenSize);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME, navigationVersion);
            //Logger LOG = Logger.getGlobal();
            //LOG.info("RECORD:" + record);
            databaseTable.insertRecord(record);
            //TODO: BORRAR
            /*databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME,walletPublicKey,DatabaseFilterType.EQUAL);
            databaseTable.loadToMemory();
            List<DatabaseTableRecord> records = databaseTable.getRecords();
            LOG.info("RECORDs:"+records.size());
            LOG.info("LEIDO:"+records.get(0));*/

            database.closeDatabase();

        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantPersistWalletException("ERROR PERSISTING WALLET", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantPersistWalletException("ERROR PERSISTING WALLET", FermatException.wrapException(exception), null, null);
        }
    }

    public void persistWalletSkin(UUID walletCatalogueId, UUID skinId, String alias, String Preview, Version version) throws CantPersistWalletSkinException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME, skinId.toString());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME, alias);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME, Preview);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_VERSION_COLUMN_NAME, String.valueOf(version.getMajor()));


            databaseTable.insertRecord(record);

            database.closeDatabase();

        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantPersistWalletSkinException("ERROR PERSISTING WALLET SKIN", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantPersistWalletSkinException("ERROR PERSISTING WALLET SKIN", FermatException.wrapException(exception), null, null);
        }
    }

    public void persistWalletLanguage(UUID walletCatalogueId, UUID languageId, Languages language, String label, Version version) throws CantPersistWalletLanguageException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME);
            DatabaseTableRecord record = databaseTable.getEmptyRecord();
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME, languageId.toString());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_NAME_COLUMN_NAME, language.value());
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME, label);
            record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_VERSION_COLUMN_NAME, String.valueOf(version.getMajor()));


            databaseTable.insertRecord(record);

            database.closeDatabase();

        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantPersistWalletLanguageException("ERROR PERSISTING WALLET LANGUAGE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantPersistWalletLanguageException("ERROR PERSISTING WALLET LANGUAGE", FermatException.wrapException(exception), null, null);
        }
    }

    public void deleteWalletLanguage(UUID walletCatalogueId, UUID languageId) throws CantDeleteWalletLanguageException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME, languageId.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                databaseTable.deleteRecord(record);
            }

            database.closeDatabase();

        } catch (CantLoadTableToMemoryException | CantDeleteRecordException e) {
            database.closeDatabase();
            throw new CantDeleteWalletLanguageException("ERROR DELETING WALLET LANGUAGE OFF TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantDeleteWalletLanguageException("ERROR DELETING WALLET LANGUAGE OFF TABLE", FermatException.wrapException(exception), null, null);
        }
    }

    public void deleteWalletSkin(UUID walletCatalogueId, UUID skinId) throws CantDeleteWalletSkinException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString(), DatabaseFilterType.EQUAL);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME, skinId.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                databaseTable.deleteRecord(record);
            }
            database.closeDatabase();

        } catch (CantLoadTableToMemoryException | CantDeleteRecordException e) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET SKIN OFF TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET SKIN OFF TABLE", FermatException.wrapException(exception), null, null);
        }
    }

    public void deleteWallet(UUID walletId) throws CantDeleteWalletSkinException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletId.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                databaseTable.deleteRecord(record);
                /**
                 * Delete wallet Skins and Languages
                 */
                deleteWalletSkin(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME));
                deleteWalletLanguage(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME));

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException | CantDeleteWalletSkinException | CantDeleteWalletLanguageException | CantDeleteRecordException e) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET OFF TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET OFF TABLE", FermatException.wrapException(exception), null, null);
        }
    }

    public void changeWalletName(UUID walletIdInTheDevice, String newName) throws CantUpdateWalletNameException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME, walletIdInTheDevice.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                record.setStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME, newName);

                databaseTable.updateRecord(record);
            }

            database.closeDatabase();

        } catch (CantLoadTableToMemoryException | CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateWalletNameException("ERROR CHANGING WALLET NAME ON TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantUpdateWalletNameException("ERROR CHANGING WALLET NAME ON TABLE", FermatException.wrapException(exception), null, null);
        }
    }

    public InstalledSkin getInstalledSkin(String skinId) throws CantGetInstalledWalletsException {
        InstalledSkin installedSkin = null;
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {

                installedSkin = new WalletManagerMiddlewareInstalledSkin(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME),
                        new Version(1, 0, 0)
                );

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET SKINS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET SKINS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return installedSkin;
    }

    public InstalledLanguage getInstalledLanguage(String languageId) throws CantGetInstalledWalletsException {
        InstalledLanguage installedLanguage = null;
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, languageId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {

                installedLanguage = new WalletManagerMiddlewareInstalledLanguage(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME),
                        Languages.valueOf(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME),
                        new Version(1, 0, 0)
                );

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return installedLanguage;
    }

    /**
     * Private methods
     */

    private List<InstalledSkin> getInstalledSkins(String walletCatalogId) throws CantGetInstalledWalletsException {
        List<InstalledSkin> lstInstalledSkin = new ArrayList<InstalledSkin>();
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {

                InstalledSkin installedSkin = new WalletManagerMiddlewareInstalledSkin(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME),
                        new Version(1, 0, 0)
                );


                lstInstalledSkin.add(installedSkin);

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return lstInstalledSkin;
    }

    private List<InstalledLanguage> getInstalledLanguages(String walletCatalogId) throws CantGetInstalledWalletsException {
        List<InstalledLanguage> lstInstalledLanguage = new ArrayList<InstalledLanguage>();
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogId, DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {

                InstalledLanguage installedLanguage = new WalletManagerMiddlewareInstalledLanguage(record.getUUIDValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME),
                        Languages.valueOf(record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_NAME_COLUMN_NAME)),
                        record.getStringValue(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME),
                        new Version(1, 0, 0)
                );

                lstInstalledLanguage.add(installedLanguage);

            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantGetInstalledWalletsException("ERROR GET INSTALLED SKINS FROM DATABASE", FermatException.wrapException(exception), null, null);
        }

        return lstInstalledLanguage;
    }

    private void deleteWalletSkin(UUID walletCatalogueId) throws CantDeleteWalletSkinException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                databaseTable.deleteRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException | CantDeleteRecordException e) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET SKIN OFF TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantDeleteWalletSkinException("ERROR DELETING WALLET SKIN OFF TABLE", FermatException.wrapException(exception), null, null);
        }
    }

    private void deleteWalletLanguage(UUID walletCatalogueId) throws CantDeleteWalletLanguageException {
        try {
            database = openDatabase();
            DatabaseTable databaseTable = getDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME);
            databaseTable.setStringFilter(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME, walletCatalogueId.toString(), DatabaseFilterType.EQUAL);

            databaseTable.loadToMemory();

            List<DatabaseTableRecord> records = databaseTable.getRecords();
            for (DatabaseTableRecord record : records) {
                databaseTable.deleteRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException | CantDeleteRecordException e) {
            database.closeDatabase();
            throw new CantDeleteWalletLanguageException("ERROR DELETING WALLET LANGUAGE OFF TABLE", e, null, null);
        } catch (Exception exception) {
            database.closeDatabase();
            throw new CantDeleteWalletLanguageException("ERROR DELETING WALLET LANGUAGE OFF TABLE", FermatException.wrapException(exception), null, null);
        }
    }
}
