package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database.AssetUserActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database.AssetUserActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserConnectionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 */
public class AssetUserActorDao implements Serializable {

    String ASSET_USER_PROFILE_IMAGE_FILE_NAME = "assetUserActorProfileImage";

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

    public AssetUserActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
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
     * @throws CantInitializeAssetUserActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetUserActorDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME);
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetUserActorDatabaseException(CantInitializeAssetUserActorDatabaseException.DEFAULT_MESSAGE, cantOpenDatabaseException, "", "Exception not handled by the plugin, there is a problem and i cannot open the database.");
        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetUserActorDatabaseFactory databaseFactory = new AssetUserActorDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = databaseFactory.createDatabase(this.pluginId, AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetUserActorDatabaseException(CantInitializeAssetUserActorDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "There is a problem and i cannot create the database.");
            }
        } catch (Exception e) {
            throw new CantInitializeAssetUserActorDatabaseException(CantInitializeAssetUserActorDatabaseException.DEFAULT_MESSAGE, e, "", "Exception not handled by the plugin, there is a unknown problem and i cannot open the database.");
        }
    }

    public void createNewAssetUser(String assetUserLinkedInPublicKey, String assetUserToAddName, String assetUserToAddPublicKey, byte[] profileImage, Genders genders, String age, CryptoAddress cryptoAddress, ConnectionState connectionState) throws CantAddPendingAssetUserException {

        try {
            /**
             * if Asset User exist on table
             * change status
             */
            if (assetUserExists(assetUserToAddPublicKey)) {
                this.updateAssetUserConnectionState(assetUserLinkedInPublicKey, assetUserToAddPublicKey, connectionState);
            } else {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();
                Location location = new DeviceLocation();
                String locationLatitude, locationLongitude;
                if (location.getLatitude() == null && location.getLongitude() == null) {
                    locationLatitude = "-";
                    locationLongitude = "-";
                } else {
                    locationLatitude = location.getLatitude().toString();
                    locationLongitude = location.getLongitude().toString();
                }

                DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLinkedInPublicKey);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserToAddPublicKey);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_NAME_COLUMN_NAME, assetUserToAddName);

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_LOCATION_LATITUDE_COLUMN_NAME, locationLatitude);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_LOCATION_LONGITUDE_COLUMN_NAME, locationLongitude);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_GENDER_COLUMN_NAME, genders.getCode());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_AGE_COLUMN_NAME, age);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetUserProfileImage(assetUserToAddPublicKey, profileImage);

                database.closeDatabase();
            }
        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", e, "", "Cant create new ASSET USER, insert database problems.");

        } catch (CantUpdateAssetUserConnectionException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant update exist ASSET USER state, unknown failure.");

        } catch (Exception e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
    }

    public void updateAssetUserConnectionState(String assetUserLinkedInPublicKey, String assetUserPublicKey, ConnectionState connectionState) throws CantUpdateAssetUserConnectionException {

        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User actor list, table not found.", "Asset User Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLinkedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Asset User record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");

        } catch (CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "asset User Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    public void createNewAssetUserRegisterInNetworkService(String assetUserPublicKey, String assetUserName, byte[] profileImage, Location location) throws CantAddPendingAssetUserException {
        try {
            /**
             * if Asset User exist on table
             * change status
             */
            if (assetUserExists(assetUserPublicKey)) {
                this.updateAssetUserConnectionStateActorNetworService(assetUserPublicKey, ConnectionState.CONNECTED);
            } else {
                /**
                 * Get actual date
                 */
                Date d = new Date();
                long milliseconds = d.getTime();
                String locationLatitude, locationLongitude;
                if (location.getLatitude() == null || location.getLongitude() == null) {
                    locationLatitude = "-";
                    locationLongitude = "-";
                } else {
                    locationLatitude = location.getLatitude().toString();
                    locationLongitude = location.getLongitude().toString();
                }

                DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_NAME_COLUMN_NAME, assetUserName);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_LOCATION_LATITUDE_COLUMN_NAME, locationLatitude);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_LOCATION_LONGITUDE_COLUMN_NAME, locationLongitude);
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_GENDER_COLUMN_NAME, "-");
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_AGE_COLUMN_NAME, "-");
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_CRYPTO_ADDRESS_COLUMN_NAME, "-");
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_CRYPTO_CURRENCY_COLUMN_NAME, "-");
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_REGISTRATION_DATE_COLUMN_NAME, milliseconds);
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetUserProfileImage(assetUserPublicKey, profileImage);

                database.closeDatabase();
            }
        } catch (CantInsertRecordException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");

        } catch (CantUpdateAssetUserConnectionException e) {
            database.closeDatabase();
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");

        } catch (Exception e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
    }


    public void updateAssetUserConnectionStateActorNetworService(String assetUserPublicKey, ConnectionState connectionState) throws CantUpdateAssetUserConnectionException {

        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User actor list, table not found.", "Asset User Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Asset User record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_MODIFIED_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");

        } catch (CantUpdateRecordException e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            database.closeDatabase();
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "asset User Actor", "Cant get developer identity list, unknown failure.");
        }
    }


    public List<ActorAssetUser> getAllAssetUsers(String assetUserLoggedInPublicKey, int max, int offset) throws CantGetAssetUsersListException {

        // Setup method.
        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset User identity list, table not found.", "Plugin Identity", "Cant get Asset Usuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list. AssetUserActorRecord
                list.add(new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_NAME_COLUMN_NAME),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME),
                        getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME),
                        Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_GENDER_COLUMN_NAME)),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_AGE_COLUMN_NAME)));
            }
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetUser> getAllAssetUsers(String assetUserLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetAssetUsersListException {

        // Setup method.
        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by state.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                list.add(new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_NAME_COLUMN_NAME),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME),
                        getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME),
                        Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_GENDER_COLUMN_NAME)),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_AGE_COLUMN_NAME)));
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    /**
     * Private Methods
     */
    private void persistNewAssetUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_USER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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

    private byte[] getAssetUserProfileImagePrivateKey(String publicKey) throws CantGetAssetUserActorProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ASSET_USER_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetUserActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetAssetUserActorProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting Asset User Actor private keys file.", null);
        } catch (Exception e) {
            throw new CantGetAssetUserActorProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }
        return profileImage;
    }

    /**
     * <p>Method that check if Asset User public key exists.
     *
     * @param assetUserExistsToAddPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean assetUserExists(String assetUserExistsToAddPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not found.", "Asset User Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserExistsToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_DATABASE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    public ActorAssetUser getAssetUserActor() throws CantGetAssetUsersListException {
//        public List<ActorAssetUser> getAllAssetUserActor(String assetUserToAddPublicKey) throws CantGetAssetUsersListException {
        // Setup method.
//        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.
        com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord assetUserActorRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }//TODO Filtro de Busqueda en Tabla no colocado para que traiga toda la informacion que contiene
            // 2) Find  Asset Users by public Key.
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, "CTC", DatabaseFilterType.EQUAL);
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // 3) Get Asset Users Recorod.
            for (DatabaseTableRecord record : table.getRecords()) {
                // Add records to list.
                assetUserActorRecord = new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_NAME_COLUMN_NAME),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME),
                        getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME)),
                        record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_REGISTRATION_DATE_COLUMN_NAME),
                        Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_GENDER_COLUMN_NAME)),
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_USER_AGE_COLUMN_NAME));
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            database.closeDatabase();
            // Failure unknown.
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            database.closeDatabase();
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the list values.
        return assetUserActorRecord;
    }
}
