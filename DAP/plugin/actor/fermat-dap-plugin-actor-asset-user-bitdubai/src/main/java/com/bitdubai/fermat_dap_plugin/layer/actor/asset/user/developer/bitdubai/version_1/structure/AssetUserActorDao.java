package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
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
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
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

    public AssetUserActorDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetUserActorDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        initializeDatabase();
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
    private void initializeDatabase() throws CantInitializeAssetUserActorDatabaseException {
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

    //    public void createNewAssetUser(String assetUserLinkedInPublicKey, String assetUserToAddName, String assetUserPublicKey, byte[] profileImage, Genders genders, String age, CryptoAddress cryptoAddress, ConnectionState connectionState) throws CantAddPendingAssetUserException {
    public void createNewAssetUser(ActorAssetUser actorAssetUserRecord) throws CantAddPendingAssetUserException {

        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET ASSET USER ACTOR, TABLE NOT FOUND.", " ASSET USER ACTOR", "");
            } else {
                table.loadToMemory();
                if (table.getRecords().size() == 0) {
                    DatabaseTableRecord record = table.getEmptyRecord();
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, actorAssetUserRecord.getPublicKey());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME, actorAssetUserRecord.getName());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME, actorAssetUserRecord.getAge());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME, actorAssetUserRecord.getGender().getCode());

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, actorAssetUserRecord.getConnectionState().getCode());

                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLatitude());
                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLongitude());

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getAddress());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getCryptoCurrency().getCode());

                    record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTRATION_DATE_COLUMN_NAME, actorAssetUserRecord.getRegistrationDate());
                    record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME, actorAssetUserRecord.getLastConnectionDate());

                    table.insertRecord(record);
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetUserProfileImage(actorAssetUserRecord.getPublicKey(), actorAssetUserRecord.getProfileImage());
                }
            }
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", e, "", "Cant create new ASSET USER, insert database problems.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T CREATE ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void updateAssetUserConnectionState(String assetUserPublicKey, ConnectionState connectionState) throws CantUpdateAssetUserConnectionException {

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
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLinkedInPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
            Date d = new Date();
            long milliseconds = d.getTime();

            // 3) Get Asset User record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "Asset User Actor", "Cant load Table: " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "Asset User Actor", "Cant Update Record of Table: " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    //    public void createNewAssetUserRegisterInNetworkService(String assetUserPublicKey, String assetUserName, byte[] profileImage, Location location, CryptoAddress cryptoAddress) throws CantAddPendingAssetUserException {
    public void createNewAssetUserRegisterInNetworkService(ActorAssetUser actorAssetUserRecord) throws CantAddPendingAssetUserException {
        try {
            /**
             * if Asset User exist on table
             * change status
             */
            if (assetUserExists(actorAssetUserRecord.getPublicKey())) {
                this.updateAssetUserConnectionStateActorNetworService(actorAssetUserRecord.getPublicKey(), actorAssetUserRecord.getConnectionState());
            } else {
                /**
                 * Get actual date
                 */
//                Date d = new Date();
//                long milliseconds = d.getTime();
//                String locationLatitude, locationLongitude;
//                if (location.getLatitude() == null || location.getLongitude() == null) {
//                    locationLatitude = "-";
//                    locationLongitude = "-";
//                } else {
//                    locationLatitude = location.getLatitude().toString();
//                    locationLongitude = location.getLongitude().toString();
//                }

                DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);
                DatabaseTableRecord record = table.getEmptyRecord();

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetUserRecord.getPublicKey());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME, actorAssetUserRecord.getName());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, actorAssetUserRecord.getAge());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, actorAssetUserRecord.getGender().getCode());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, actorAssetUserRecord.getConnectionState().getCode());

                if(actorAssetUserRecord.getLocationLatitude() != null)
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, actorAssetUserRecord.getAge());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, actorAssetUserRecord.getGender().getCode());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, actorAssetUserRecord.getConnectionState().getCode());

                if(actorAssetUserRecord.getLocationLatitude() != null)
                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLatitude());
                if(actorAssetUserRecord.getLocationLongitude() != null)
                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLongitude());

                if(actorAssetUserRecord.getLocationLatitude() != null) {
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getAddress());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getCryptoCurrency().getCode());
                }
                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetUserProfileImage(actorAssetUserRecord.getPublicKey(), actorAssetUserRecord.getProfileImage());

                database.closeDatabase();
            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void createNewAssetUserRegisterInNetworkServiceByList(List<ActorAssetUser> actorAssetUserRecord) throws CantAddPendingAssetUserException {
        try {
            /**
             * if Asset User exist on table
             * change status
             */
            for (ActorAssetUser actorAssetUser : actorAssetUserRecord) {

                if (assetUserExists(actorAssetUser.getPublicKey())) {
                    this.updateAssetUserConnectionStateActorNetworService(actorAssetUser.getPublicKey(), actorAssetUser.getConnectionState());
                } else {
                    /**
                     * Get actual date
                     */
//                Date d = new Date();
//                long milliseconds = d.getTime();
//                String locationLatitude, locationLongitude;
//                if (location.getLatitude() == null || location.getLongitude() == null) {
//                    locationLatitude = "-";
//                    locationLongitude = "-";
//                } else {
//                    locationLatitude = location.getLatitude().toString();
//                    locationLongitude = location.getLongitude().toString();
//                }

                    DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);
                    DatabaseTableRecord record = table.getEmptyRecord();

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetUser.getPublicKey());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME, actorAssetUser.getName());

                    if(actorAssetUser.getLocationLatitude() != null)
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, actorAssetUser.getAge());

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, actorAssetUser.getGender().getCode());

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, actorAssetUser.getConnectionState().getCode());

                    if(actorAssetUser.getLocationLatitude() != null)
                        record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUser.getLocationLatitude());
                    if(actorAssetUser.getLocationLongitude() != null)
                        record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUser.getLocationLongitude());

                    if(actorAssetUser.getCryptoAddress() != null) {
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUser.getCryptoAddress().getAddress());
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUser.getCryptoAddress().getCryptoCurrency().getCode());
                    }
                    record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, actorAssetUser.getRegistrationDate());
                    record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                    table.insertRecord(record);
                    /**
                     * Persist profile image on a file
                     */
                    persistNewAssetUserProfileImage(actorAssetUser.getPublicKey(), actorAssetUser.getProfileImage());
                }
            }
            database.closeDatabase();
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        } finally {
            database.closeDatabase();
        }
    }

    public void updateAssetUserConnectionStateActorNetworService(String assetUserPublicKey, ConnectionState connectionState) throws CantUpdateAssetUserConnectionException {

        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User actor list, table not found.", "Asset User Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            /**
             * Get actual date
             */
//            Date d = new Date();
//            long milliseconds = d.getTime();

            // 3) Get Asset User record and update state.
            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant Update " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "Asset User REGISTERED Actor", "Cant get developer identity list, unknown failure.");
        } finally {
            database.closeDatabase();
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
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, ConnectionState.CONNECTED.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetUser> getAllAssetUsers(String assetUserLoggedInPublicKey, ConnectionState connectionState, int max, int offset) throws CantGetAssetUsersListException {

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
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, assetUserLoggedInPublicKey, DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, connectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public ActorAssetUser getActorAssetUser() throws CantGetAssetUsersListException {

        ActorAssetUser assetUserActorRecord = new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord();
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
            table.loadToMemory();
            // 3) Get Asset Users Record.
            assetUserActorRecord = this.addRecords(table.getRecords());
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the values.
        return assetUserActorRecord;
    }

    public List<ActorAssetUser> getAllAssetUserActorRegistered() throws CantGetAssetUsersListException {
        List<ActorAssetUser> list = new ArrayList<>(); // Asset User Actor list.

        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }//TODO Filtro de Busqueda en Tabla NO colocado para que traiga toda la informacion que contiene
            // 2) Find  Asset Users by public Key.
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_STATE_COLUMN_NAME, "CTC", DatabaseFilterType.EQUAL);
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserToAddPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();
            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());
            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUsersListException {
        List<ActorAssetUser> list = new ArrayList<>(); // Asset User Actor list.
        DatabaseTable table;
        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, "-", DatabaseFilterType.GRATER_THAN);
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_USER_PUBLIC_KEY_COLUMN_NAME, assetUserToAddPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());

            database.closeDatabase();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        } finally {
            database.closeDatabase();
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
     * @param assetUserExistsPublicKey
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean assetUserExists(String assetUserExistsPublicKey) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists, table not found.", "Asset User Actor", "Cant check if alias exists, table not found.");
            }
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserExistsPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    private ActorAssetUser addRecords(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetUserActorProfileImageException {
        com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord actorAssetUser = null;
        for (DatabaseTableRecord record : records) {

            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME),
                    CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME)));

            actorAssetUser = new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME),
                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME),
                    cryptoAddress,
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME)));
        }
        return actorAssetUser;
    }

    private void addRecordsTableRegisteredToList(List<ActorAssetUser> list, List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetUserActorProfileImageException {

        for (DatabaseTableRecord record : records) {

            CryptoAddress cryptoAddress = new CryptoAddress(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME),
                    CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME)));

            list.add(new com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME),
                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME)),
                    ConnectionState.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    cryptoAddress,
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME))));
        }
    }
}
