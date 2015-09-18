package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantUpdateSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.enums.SkinState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantCreateEmptyWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantDeleteWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantGetWalletSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.exceptions.CantListWalletSkinsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.WalletSkin;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.exceptions.CantInitializeWalletSkinMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.structure.WalletSkinMiddlewareWalletSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.database.WalletSkinMiddlewareDao</code>
 * has all methods related with database access.<p/>
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletSkinMiddlewareDao implements DealsWithPluginDatabaseSystem {

    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public WalletSkinMiddlewareDao(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with
     *
     * @param ownerId      plugin id
     * @param databaseName database name
     * @throws CantInitializeWalletSkinMiddlewareDatabaseException
     */
    public void initializeDatabase(UUID ownerId, String databaseName) throws CantInitializeWalletSkinMiddlewareDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(ownerId, databaseName);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeWalletSkinMiddlewareDatabaseException(CantInitializeWalletSkinMiddlewareDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletSkinMiddlewareDatabaseFactory walletFactoryMiddlewareDatabaseFactory = new WalletSkinMiddlewareDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = walletFactoryMiddlewareDatabaseFactory.createDatabase(ownerId, databaseName);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletSkinMiddlewareDatabaseException(CantInitializeWalletSkinMiddlewareDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        }
    }

    /**
     * Method that list the all entities on the database.
     *
     * @return All Wallet Factory Projects Proposals who belongs to a developer.
     */
    public List<WalletSkin> findAllSkinsByDesigner(String designerPublicKey) throws CantListWalletSkinsException, InvalidParameterException {

        List<WalletSkin> walletSkins = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setStringFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME, designerPublicKey, DatabaseFilterType.EQUAL);
            projectSkinTable.setFilterOrder(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME, DatabaseFilterOrder.ASCENDING);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            for (DatabaseTableRecord record : records) {
                UUID id = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME);
                UUID skinId = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
                String alias = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ALIAS_COLUMN_NAME);
                String path = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_PATH_COLUMN_NAME);
                SkinState state = SkinState.getByCode(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME));
                Version version = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME));

                Version initialVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_INITIAL_COLUMN_NAME));
                Version finalVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_FINAL_COLUMN_NAME));
                VersionCompatibility versionCompatibility;
                try {
                    versionCompatibility = new VersionCompatibility(initialVersion, finalVersion);
                } catch (InvalidParameterException e) {
                    throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, e, "Cannot instance Version Compatibility", "");
                }
                WalletSkinMiddlewareWalletSkin walletSkin = new WalletSkinMiddlewareWalletSkin(id, skinId, name, alias, path, state, designerPublicKey, version, versionCompatibility);

                walletSkins.add(walletSkin);
            }
            database.closeDatabase();
            return walletSkins;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletSkin findSkinBySkinIdAndVersion(UUID skinId, Version version) throws CantGetWalletSkinException, SkinNotFoundException, InvalidParameterException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);
            projectSkinTable.setStringFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME, version.toString(), DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                UUID id = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
                String alias = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ALIAS_COLUMN_NAME);
                String path = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_PATH_COLUMN_NAME);
                SkinState state = SkinState.getByCode(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME));
                String designerPublicKey = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);

                Version initialVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_INITIAL_COLUMN_NAME));
                Version finalVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_FINAL_COLUMN_NAME));
                VersionCompatibility versionCompatibility;
                try {
                    versionCompatibility = new VersionCompatibility(initialVersion, finalVersion);
                } catch (InvalidParameterException e) {
                    throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, e, "Cannot instance Version Compatibility", "");
                }

                database.closeDatabase();
                return new WalletSkinMiddlewareWalletSkin(id, skinId, name, alias, path, state, designerPublicKey, version, versionCompatibility);
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet skin with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public WalletSkin findSkinById(UUID id) throws CantGetWalletSkinException, SkinNotFoundException, InvalidParameterException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                UUID skinId = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
                String alias = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ALIAS_COLUMN_NAME);
                String path = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_PATH_COLUMN_NAME);
                SkinState state = SkinState.getByCode(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME));
                String designerPublicKey = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);
                Version version = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME));

                Version initialVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_INITIAL_COLUMN_NAME));
                Version finalVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_FINAL_COLUMN_NAME));
                VersionCompatibility versionCompatibility;
                try {
                    versionCompatibility = new VersionCompatibility(initialVersion, finalVersion);
                } catch (InvalidParameterException e) {
                    throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, e, "Cannot instance Version Compatibility", "");
                }

                database.closeDatabase();
                return new WalletSkinMiddlewareWalletSkin(id, skinId, name, alias, path, state, designerPublicKey, version, versionCompatibility);
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet skin with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantGetWalletSkinException(CantGetWalletSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public List<WalletSkin> findAllSkinsBySkinId(UUID skinId) throws CantListWalletSkinsException, InvalidParameterException {

        List<WalletSkin> walletSkins = new ArrayList<>();

        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            for (DatabaseTableRecord record : records) {
                UUID id = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME);
                String name = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
                String alias = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ALIAS_COLUMN_NAME);
                String path = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_PATH_COLUMN_NAME);
                SkinState state = SkinState.getByCode(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME));
                String designerPublicKey = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);

                Version version = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME));

                Version initialVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_INITIAL_COLUMN_NAME));
                Version finalVersion = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COMPATIBILTY_FINAL_COLUMN_NAME));
                VersionCompatibility versionCompatibility;
                try {
                    versionCompatibility = new VersionCompatibility(initialVersion, finalVersion);
                } catch (InvalidParameterException e) {
                    throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, e, "Cannot instance Version Compatibility", "");
                }
                WalletSkinMiddlewareWalletSkin walletSkin = new WalletSkinMiddlewareWalletSkin(id, skinId, name, alias, path, state, designerPublicKey, version, versionCompatibility);

                walletSkins.add(walletSkin);
            }
            database.closeDatabase();
            return walletSkins;

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantListWalletSkinsException(CantListWalletSkinsException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void createSkin(WalletSkin walletSkin) throws CantCreateEmptyWalletSkinException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);

            DatabaseTableRecord record = projectSkinTable.getEmptyRecord();

            record.setUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME, walletSkin.getId());
            record.setUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME, walletSkin.getSkinId());
            record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME, walletSkin.getName());
            record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_PATH_COLUMN_NAME, walletSkin.getPath());
            record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME, walletSkin.getState().getCode());
            record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME, walletSkin.getDesignerPublicKey().toString());
            record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME, walletSkin.getVersion().toString());

            try {
                projectSkinTable.insertRecord(record);
                database.closeDatabase();
            } catch (CantInsertRecordException e) {
                database.closeDatabase();
                throw new CantCreateEmptyWalletSkinException(CantCreateEmptyWalletSkinException.DEFAULT_MESSAGE, e, "Cannot insert the wallet skin", "");
            }
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantCreateEmptyWalletSkinException(CantCreateEmptyWalletSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void updateSkin(WalletSkin walletSkin) throws CantUpdateSkinException, SkinNotFoundException, InvalidParameterException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME, walletSkin.getId(), DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                UUID skinId = record.getUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME);
                if (!skinId.equals(walletSkin.getSkinId()))
                    record.setUUIDValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_ID_COLUMN_NAME, walletSkin.getSkinId());

                String name = record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME);
                if (!name.equals(walletSkin.getName()))
                    record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_NAME_COLUMN_NAME, walletSkin.getName());

                SkinState state = SkinState.getByCode(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME));
                if (!state.getCode().equals(walletSkin.getState().getCode()))
                    record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_SKIN_STATE_COLUMN_NAME, walletSkin.getState().getCode());

                Version version = new Version(record.getStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME));
                if (!version.equals(walletSkin.getVersion()))
                    record.setStringValue(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_VERSION_COLUMN_NAME, walletSkin.getVersion().toString());

                try {
                    projectSkinTable.updateRecord(record);
                    database.closeDatabase();
                } catch (CantUpdateRecordException e) {
                    database.closeDatabase();
                    throw new CantUpdateSkinException(CantUpdateSkinException.DEFAULT_MESSAGE, e, "Cannot update the wallet skin", "");
                }
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet skin with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantUpdateSkinException(CantUpdateSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantUpdateSkinException(CantUpdateSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    public void deleteSkin(UUID id) throws CantDeleteWalletSkinException, SkinNotFoundException {
        try {
            database.openDatabase();
            DatabaseTable projectSkinTable = database.getTable(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_TABLE_NAME);
            projectSkinTable.setUUIDFilter(WalletSkinMiddlewareDatabaseConstants.WALLET_SKIN_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            projectSkinTable.loadToMemory();

            List<DatabaseTableRecord> records = projectSkinTable.getRecords();

            if (!records.isEmpty()) {
                DatabaseTableRecord record = records.get(0);

                try {
                    projectSkinTable.deleteRecord(record);
                    database.closeDatabase();
                } catch (CantDeleteRecordException e) {
                    database.closeDatabase();
                    throw new CantDeleteWalletSkinException(CantDeleteWalletSkinException.DEFAULT_MESSAGE, e, "Cannot delete the wallet skin", "");
                }
            } else {
                database.closeDatabase();
                throw new SkinNotFoundException(SkinNotFoundException.DEFAULT_MESSAGE, null, "", "Cannot find a wallet skin with this name.");
            }

        } catch (CantLoadTableToMemoryException e) {
            // Register the failure.
            database.closeDatabase();
            throw new CantDeleteWalletSkinException(CantDeleteWalletSkinException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a problem in database and i cannot load the table.");
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantDeleteWalletSkinException(CantDeleteWalletSkinException.DEFAULT_MESSAGE, exception, "", "Check the cause.");
        }
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
