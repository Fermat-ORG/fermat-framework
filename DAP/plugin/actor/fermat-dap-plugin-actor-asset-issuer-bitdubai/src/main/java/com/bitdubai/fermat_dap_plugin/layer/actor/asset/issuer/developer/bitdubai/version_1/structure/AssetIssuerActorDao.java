package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database.AssetIssuerActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantAddPendingAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuerActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantUpdateAssetIssuerConnectionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 */
public class AssetIssuerActorDao implements Serializable {

    String ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME = "assetIssuerActorProfileImage";

    /**
     * Represent the Plugin Database.
     */

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public AssetIssuerActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    /**
     * This method open or creates the database i'll be working with     *
     *
     * @throws CantInitializeAssetIssuerActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetIssuerActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetIssuerActorDatabaseFactory databaseFactory = new AssetIssuerActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeAssetIssuerActorDatabaseException(CantInitializeAssetIssuerActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewAssetIssuer(String assetIssuerLoggedInPublicKey, String assetIssuerToAddName, String assetIssuerToAddPublicKey, byte[] profileImage, ConnectionState connectionState) throws CantAddPendingAssetIssuerException {

        try {

            /**
             * if Asset Issuer exist on table
             * change status
             */
            if (assetIssuerExists(assetIssuerToAddPublicKey)) {

                this.updateAssetIssuerConnectionState(assetIssuerLoggedInPublicKey, assetIssuerToAddPublicKey, connectionState);

            } else {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();

                DatabaseTable table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey);
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_NAME_COLUMN_NAME, assetIssuerToAddName);
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME, connectionState.getCode());
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_LOGGED_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey);
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_MODIFIED_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetIssuerProfileImage(assetIssuerToAddPublicKey, profileImage);

                database.closeDatabase();
            }
        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", e, "", "Cant create new ASSET ISSUER, insert database problems.");

        } catch (CantUpdateAssetIssuerConnectionException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant update exist ASSET ISSUER state, unknown failure.");

        } catch (Exception e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantAddPendingAssetIssuerException("CAN'T INSERT ASSET ISSUER", FermatException.wrapException(e), "", "Cant create new ASSET ISSUER, unknown failure.");
        }
    }


    public void updateAssetIssuerConnectionState(String assetIssuerLoggedInPublicKey, String assetIssuerToAddPublicKey, ConnectionState connectionState) throws CantUpdateAssetIssuerConnectionException {

        DatabaseTable table;

        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer actor list, table not found.", "Asset Issuer Actor", "");
            }

            // 2) Find the Asset Issuer , filter by keys.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_LOGGED_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Asset Issuer record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_MODIFIED_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantUpdateAssetIssuerConnectionException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

        } catch (CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateAssetIssuerConnectionException(e.getMessage(), e, "asset issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantUpdateAssetIssuerConnectionException(e.getMessage(), FermatException.wrapException(e), "asset issuer Actor", "Cant get developer identity list, unknown failure.");
        }
    }


    public List<ActorAssetIssuer> getAllAssetIssuers(String assetIssuerLoggedInPublicKey, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.", "Plugin Identity", "Cant get Asset Issuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_LOGGED_PUBLIC_KEY_COLUMN_NAME, assetIssuerLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list. AssetIssuerActorRecord
                list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_NAME_COLUMN_NAME),
                        record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.valueOf(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME))));
            }
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetIssuer> getAssetIssuers(String aasetUserLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetAssetIssuersListException {

        // Setup method.
        List<ActorAssetIssuer> list = new ArrayList<ActorAssetIssuer>(); // Asset Issuer Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset issuer identity list, table not found.", "Plugin Identity", "Cant get asset issuer identity list, table not found.");
            }
            // 2) Find  Asset Users by state.
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_LOGGED_PUBLIC_KEY_COLUMN_NAME, aasetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                list.add(new AssetIssuerActorRecord(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_NAME_COLUMN_NAME),
                        record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_REGISTRATION_DATE_COLUMN_NAME),
                        ConnectionState.valueOf(record.getStringValue(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_STATE_COLUMN_NAME))));
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetIssuerActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetAssetIssuersListException(e.getMessage(), e, "Asset Issuer Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetAssetIssuersListException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant get Asset Issuer Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    /**
     * Private Methods
     */
    private void persistNewAssetIssuerProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", FermatException.wrapException(e), "", "");
        }
    }

    private byte[] getAssetIssuerProfileImagePrivateKey(String publicKey) throws CantGetAssetIssuerActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_ISSUER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Asset Issuer Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Asset Issuer public key exists.
     *
     * @param assetIssuerToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean assetIssuerExists(String assetIssuerToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not  found.", "Asset Issuer Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_PUBLIC_KEY_COLUMN_NAME, assetIssuerToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer Actor", "Cant load " + AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer Actor", "Cant check if alias exists, unknown failure.");
        }
    }
}
