package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database.AssetUserActorDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database.AssetUserActorDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserActorProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUserGroupTableExcepcion;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserConnectionException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateAssetUserGroupException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 06/10/15.
 */
public class AssetUserActorDao implements Serializable {

    private static final Boolean USER_EXIST_TO_GROUP = true;
    private static final Boolean USER_NOT_EXIST_TO_GROUP = false;
    String ASSET_USER_PROFILE_IMAGE_FILE_NAME = "assetUserActorProfileImage";
    private static final boolean GROUP_EXIST = true;
    private static final boolean GROUP_NOT_EXIST = false;

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

    //    public void createNewAssetUser(String assetUserLinkedInPublicKey, String assetUserToAddName, String assetUserPublicKey, byte[] profileImage, Genders genders, String age, CryptoAddress cryptoAddress, DAPConnectionState connectionState) throws CantAddPendingAssetUserException {
    public void createNewAssetUser(ActorAssetUser actorAssetUserRecord) throws CantAddPendingAssetUserException {

        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("CANT GET ASSET USER ACTOR, TABLE NOT FOUND.", " ASSET USER ACTOR", "");
            } else {
                if (assetUserExists(actorAssetUserRecord.getActorPublicKey())) {
                    this.updateAssetUserDAPConnectionStateOrCrpytoAddress(actorAssetUserRecord.getActorPublicKey(), actorAssetUserRecord.getDapConnectionState(), actorAssetUserRecord.getCryptoAddress());
                } else {

                    table.loadToMemory();

                    if (table.getRecords().size() == 0) {
                        DatabaseTableRecord record = table.getEmptyRecord();
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, "-");

                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, actorAssetUserRecord.getActorPublicKey());
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME, actorAssetUserRecord.getName());

                        if (actorAssetUserRecord.getAge() != null)
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME, actorAssetUserRecord.getAge());

                        if (actorAssetUserRecord.getGenders() != null)
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME, actorAssetUserRecord.getGenders().getCode());

                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, actorAssetUserRecord.getDapConnectionState().getCode());

                        if (actorAssetUserRecord.getLocationLatitude() != null)
                            record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLatitude());
                        if (actorAssetUserRecord.getLocationLongitude() != null)
                            record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLongitude());

                        if (actorAssetUserRecord.getCryptoAddress() != null) {
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getAddress());
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUserRecord.getCryptoAddress().getCryptoCurrency().getCode());
                        }

                        record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTRATION_DATE_COLUMN_NAME, actorAssetUserRecord.getRegistrationDate());
                        record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME, actorAssetUserRecord.getLastConnectionDate());

                        table.insertRecord(record);
                        /**
                         * Persist profile image on a file
                         */
                        persistNewAssetUserProfileImage(actorAssetUserRecord.getActorPublicKey(), actorAssetUserRecord.getProfileImage());
                    }
                }
            }

        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", e, "", "Cant create new ASSET USER, insert database problems.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T CREATE ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
    }

    public void updateAssetUserDAPConnectionStateOrCrpytoAddress(String assetUserPublicKey, DAPConnectionState dapDAPConnectionState, CryptoAddress cryptoAddress) throws CantUpdateAssetUserConnectionException {

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
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode());

                if (cryptoAddress != null) {
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
                }

                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME, milliseconds);
                table.updateRecord(record);
            }


        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "Asset User Actor", "Cant load Table: " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "Asset User Actor", "Cant Update Record of Table: " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    //    public void createNewAssetUserRegisterInNetworkService(String assetUserPublicKey, String assetUserName, byte[] profileImage, Location location, CryptoAddress cryptoAddress) throws CantAddPendingAssetUserException {
    public void createNewAssetUserRegisterInNetworkService(ActorAssetUser actorAssetUserRecord, DAPConnectionState dapConnectionState, CryptoAddress cryptoAddress) throws CantAddPendingAssetUserException {
        try {
            /**
             * if Asset User exist on table
             * change status
             */
            if (assetUserRegisteredExists(actorAssetUserRecord.getActorPublicKey())) {
                this.updateAssetUserDAPConnectionStateActorNetworService(actorAssetUserRecord.getActorPublicKey(), dapConnectionState, cryptoAddress);
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

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetUserRecord.getActorPublicKey());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME, actorAssetUserRecord.getName());

                if (actorAssetUserRecord.getAge() != null)
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, actorAssetUserRecord.getAge());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, actorAssetUserRecord.getGenders().getCode());

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, actorAssetUserRecord.getDapConnectionState().getCode());

                if (actorAssetUserRecord.getLocationLatitude() != null)
                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLatitude());
                if (actorAssetUserRecord.getLocationLongitude() != null)
                    record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUserRecord.getLocationLongitude());

                if (cryptoAddress != null) {
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
                }

                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, actorAssetUserRecord.getRegistrationDate());
                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                table.insertRecord(record);
                /**
                 * Persist profile image on a file
                 */
                persistNewAssetUserProfileImage(actorAssetUserRecord.getActorPublicKey(), actorAssetUserRecord.getProfileImage());


            }
        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
    }

    public int createNewAssetUserRegisterInNetworkServiceByList(List<ActorAssetUser> actorAssetUserRecord) throws CantAddPendingAssetUserException {
        int recordInsert = 0;
        try {
            /**
             * if Asset User exist on table
             * change status
             */

            for (ActorAssetUser actorAssetUser : actorAssetUserRecord) {

                if (compareRegisterTables(actorAssetUser)) {
                    if (assetUserRegisteredExists(actorAssetUser.getActorPublicKey())) {
                        this.updateAssetUserDAPConnectionStateActorNetworService(actorAssetUser.getActorPublicKey(), actorAssetUser.getDapConnectionState(), actorAssetUser.getCryptoAddress());
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
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetUser.getActorPublicKey());
                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME, actorAssetUser.getName());

                        if (actorAssetUser.getAge() != null)
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, actorAssetUser.getAge());

                        record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, actorAssetUser.getGenders().getCode());

                        if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME).isEmpty()) {
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.REGISTERED_ONLINE.getCode());//actorAssetUser.getDAPConnectionState().getCode());
                        } else {
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.CONNECTED_ONLINE.getCode());//actorAssetUser.getDAPConnectionState().getCode());
                        }

                        if (actorAssetUser.getLocationLatitude() != null)
                            record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, actorAssetUser.getLocationLatitude());
                        if (actorAssetUser.getLocationLongitude() != null)
                            record.setDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, actorAssetUser.getLocationLongitude());

                        if (actorAssetUser.getCryptoAddress() != null) {
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, actorAssetUser.getCryptoAddress().getAddress());
                            record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, actorAssetUser.getCryptoAddress().getCryptoCurrency().getCode());
                        }

                        record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, actorAssetUser.getRegistrationDate());
                        record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());

                        table.insertRecord(record);
                        recordInsert = recordInsert + 1;
                        /**
                         * Persist profile image on a file
                         */
                        persistNewAssetUserProfileImage(actorAssetUser.getActorPublicKey(), actorAssetUser.getProfileImage());
                    }
                }
            }
            if (actorAssetUserRecord.isEmpty()) {
                this.updateActorAssetUserActorNetworService(DAPConnectionState.REGISTERED_OFFLINE);
            }

        } catch (CantInsertRecordException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", e, "", "Cant create new ASSET USER REGISTERED IN ACTOR NETWORK SERVICE, insert database problems.");
        } catch (CantUpdateAssetUserConnectionException e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER REGISTERED IN ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "Cant update exist ASSET USER REGISTERED IN ACTOR NETWORK SERVICE state, unknown failure.");
        } catch (Exception e) {
            throw new CantAddPendingAssetUserException("CAN'T INSERT ASSET USER", FermatException.wrapException(e), "", "Cant create new ASSET USER, unknown failure.");
        }
        return recordInsert;
    }

    //TODO Metodo para actualizar estado de los Actores retornados por A.N.S que se han desconectado
    public void updateActorAssetUserActorNetworService(DAPConnectionState dapConnectionState) throws CantUpdateAssetUserConnectionException {
        DatabaseTable table;

        try {
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User actor list, table not found.", "Asset User Actor", "");
            }

            // 2) Find the Asset User , filter by keys.
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            if (table.getRecords().size() > 0) {
                // 3) Get Asset User record and update state.
                for (DatabaseTableRecord record : table.getRecords()) {
                    if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME) != null) {
                        dapConnectionState = DAPConnectionState.CONNECTED_OFFLINE;
                    }

                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());

                    record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                    table.updateRecord(record);
                }
            }

        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant Update " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "Asset User REGISTERED Actor", "Cant get developer identity list, unknown failure.");
        }
    }

    public void updateAssetUserDAPConnectionStateActorNetworService(String assetUserPublicKey, DAPConnectionState dapConnectionState, CryptoAddress cryptoAddress) throws CantUpdateAssetUserConnectionException {

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
                if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME) == null) {
                    dapConnectionState = DAPConnectionState.REGISTERED_ONLINE;
                }

                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, dapConnectionState.getCode());

                if (cryptoAddress != null) {
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, cryptoAddress.getCryptoCurrency().getCode());
                }

                record.setLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, System.currentTimeMillis());
                table.updateRecord(record);
            }


        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), e, "asset User REGISTERED Actor", "Cant Update " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserConnectionException(e.getMessage(), FermatException.wrapException(e), "Asset User REGISTERED Actor", "Cant get developer identity list, unknown failure.");
        }
    }


    //    public List<ActorAssetUser> getAssetUserRegistered(String actorAssetPublicKey, int max, int offset) throws CantGetAssetUsersListException {
    public List<ActorAssetUser> getAssetUserRegistered(String actorAssetPublicKey) throws CantGetAssetUsersListException {

        // Setup method.
        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.
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
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset User identity list, table not found.", "Plugin Identity", "Cant get Asset Usuer identity list, table not found.");
            }

            // 2) Find all Asset Users.
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetPublicKey, DatabaseFilterType.EQUAL);
//            table.setFilterOffSet(String.valueOf(offset));
//            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());


        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public List<ActorAssetUser> getAllAssetUsers(String assetUserLoggedInPublicKey, DAPConnectionState dapDAPConnectionState, int max, int offset) throws CantGetAssetUsersListException {

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
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, dapDAPConnectionState.getCode(), DatabaseFilterType.EQUAL);
            table.setFilterOffSet(String.valueOf(offset));
            table.setFilterTop(String.valueOf(max));
            table.loadToMemory();

            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());


        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the list values.
        return list;
    }

    public ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException {

        ActorAssetUser assetUserActorRecord = null;
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

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
        // Return the values.
        return assetUserActorRecord;
    }

    public ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException {
        ActorAssetUser assetUserActorRecord = null;
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();
            // 3) Get Asset Users Record.
            assetUserActorRecord = this.addRecords(table.getRecords());


        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
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

            table.loadToMemory();
            // 3) Get Asset Users Record.
            this.addRecordsTableRegisteredToList(list, table.getRecords());

        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
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
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            // 2) Find  Asset Users by Connection State.
            //TODO Actor Asset User en Tabla REGISTERED con DAPConnectionState CONNECTED_ONLINE indicara que tiene CryptoAddress
//            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DAPConnectionState.CONNECTED_ONLINE.getCode(), DatabaseFilterType.EQUAL);

            table.loadToMemory();

            this.addRecordsTableRegisteredToList(list, table.getRecords());


        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
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

    private boolean assetUserExists(String assetUserExistsPublicKey) throws CantCreateNewDeveloperException {

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
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, assetUserExistsPublicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    private boolean assetUserRegisteredExists(String assetUserExistsPublicKey) throws CantCreateNewDeveloperException {

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
        AssetUserActorRecord actorAssetUser = null;

        for (DatabaseTableRecord record : records) {
            CryptoAddress cryptoAddress = null;
            if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME) != null) {
                cryptoAddress = new CryptoAddress(
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME),
                        CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME)));
            }

            actorAssetUser = new AssetUserActorRecord(
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME),
                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME)),
                    DAPConnectionState.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME)),
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
            CryptoAddress cryptoAddress = null;
            if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME) != null) {
                cryptoAddress = new CryptoAddress(
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME),
                        CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME)));
            }

            list.add(new AssetUserActorRecord(
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME),
                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME)),
                    DAPConnectionState.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    cryptoAddress,
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME))));
        }
    }

    private boolean compareRegisterTables(ActorAssetUser actorAssetUserRecord) throws CantCreateNewDeveloperException {

        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */
        try {
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);
            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check Table(s), tables not found.", "Asset User Actor", "Cant check table(s) not found.");
            }
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, actorAssetUserRecord.getActorPublicKey(), DatabaseFilterType.EQUAL);
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME, actorAssetUserRecord.getName(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() == 0;

        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant check if alias exists, unknown failure.");
        }
    }

    public ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException {
        String context = "Group Name: " + groupName;
        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (table == null) {
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            } else {
                if (existsAssetUserGroup(groupName)) {
                    throw new ActorAssetUserGroupAlreadyExistException(null, context, "You already created this group, stop it!");
                } else {
                    table.loadToMemory();
                    String groupId = UUID.randomUUID().toString();
                    DatabaseTableRecord record = table.getEmptyRecord();
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME, groupId);
                    record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME, groupName);
                    table.insertRecord(record);
                    return new AssetUserGroupRecord(groupId, groupName);
                }
            }
        } catch (CantInsertRecordException e) {
            throw new CantCreateAssetUserGroupException("CAN'T INSERT USER GROUP", e, "", "Cant create new GROUP, insert database problems.");
        } catch (Exception e) {
            throw new CantCreateAssetUserGroupException("CAN'T CREATE USER GROUP", FermatException.wrapException(e), "", "Cant create new GROUP, unknown failure.");
        }
    }

    public void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException {
        String context = "ActorAssetUserGroup: " + assetUserGroup;
        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            }

            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME, assetUserGroup.getGroupId(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "There is no group with this ID.");
            }

            for (DatabaseTableRecord record : table.getRecords()) {
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME, assetUserGroup.getGroupName());
                table.updateRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantUpdateAssetUserGroupException(e.getMessage(), e, context, "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (CantUpdateRecordException e) {
            throw new CantUpdateAssetUserGroupException(e.getMessage(), e, context, "Cant Update " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantUpdateAssetUserGroupException(e.getMessage(), FermatException.wrapException(e), context, "Cant update Asset User Group, unknown failure.");
        }
    }

    public void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException {
        String context = "Group ID:" + assetUserGroupId;
        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            }

            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME, assetUserGroupId, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            if (table.getRecords().isEmpty()) {
                throw new RecordsNotFoundException(null, context, "There is no group with this ID.");
            }

            for (DatabaseTableRecord record : table.getRecords()) {
                table.deleteRecord(record);
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantDeleteAssetUserGroupException(e.getMessage(), e, context, "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (CantDeleteRecordException e) {
            throw new CantDeleteAssetUserGroupException(e.getMessage(), e, context, "Cant Update " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantDeleteAssetUserGroupException(e.getMessage(), FermatException.wrapException(e), context, "Cant update Asset User Group, unknown failure.");
        }
    }

    public void createAssetUserGroupMember(ActorAssetUserGroupMember assetUserGroupMemberRecord) throws CantCreateAssetUserGroupException {
        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME);

            if (table == null) {
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP MEMBER", "");
            } else {
                if (existsAssetUserGroupMember(assetUserGroupMemberRecord))
                    throw new CantGetAssetUserGroupTableExcepcion("User is already added to the group.", " ASSET USER GROUP MEMBER", "");
                table.loadToMemory();
                DatabaseTableRecord record = table.getEmptyRecord();
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME, assetUserGroupMemberRecord.getGroupId());
                record.setStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserGroupMemberRecord.getActorPublicKey());
                table.insertRecord(record);
            }

        } catch (CantInsertRecordException e) {
            throw new CantCreateAssetUserGroupException("CAN'T INSERT USER TO GROUP", e, "", "Cant create new GROUP MEMBER, insert database problems.");
        } catch (Exception e) {
            throw new CantCreateAssetUserGroupException("CAN'T INSERT USER TO GROUP", FermatException.wrapException(e), "", "Cant create new GROUP MEMBER, unknown failure.");
        }
    }

    public void deleteAssetUserGroupMember(ActorAssetUserGroupMember assetUserGroupMemberRecord) throws CantCreateAssetUserGroupException, RecordsNotFoundException {
        String context = "ActorAssetUserGroupMember: " + assetUserGroupMemberRecord;
        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME);

            if (table == null) {
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            } else {
                table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME, assetUserGroupMemberRecord.getGroupId(), DatabaseFilterType.EQUAL);
                table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserGroupMemberRecord.getActorPublicKey(), DatabaseFilterType.EQUAL);
                table.loadToMemory();

                if (table.getRecords().isEmpty()) {
                    throw new RecordsNotFoundException(null, context, "There is no member with this ID.");
                }

                for (DatabaseTableRecord record : table.getRecords()) {
                    table.deleteRecord(record);
                }

            }

        } catch (CantDeleteRecordException e) {
            throw new CantCreateAssetUserGroupException("CAN'T DELETE USER FROM GROUP", e, context, "Cant delete USER FROM GROUP, insert database problems.");
        } catch (Exception e) {
            throw new CantCreateAssetUserGroupException("CAN'T DELETE USER FROM GROUP", FermatException.wrapException(e), context, "Cant USER FROM GROUP, unknown failure.");
        }
    }

    private Boolean existsAssetUserGroupMember(ActorAssetUserGroupMember assetUserGroupMemberRecord) throws CantCreateAssetUserGroupException {
        try {
            DatabaseTable table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME);

            if (table == null) {
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            } else {

                table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME, assetUserGroupMemberRecord.getGroupId(), DatabaseFilterType.EQUAL);
                table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, assetUserGroupMemberRecord.getActorPublicKey(), DatabaseFilterType.EQUAL);
                table.loadToMemory();

                return table.getRecords().isEmpty();
            }

        } catch (Exception e) {
            throw new CantCreateAssetUserGroupException("CAN'T DELETE USER FROM GROUP", FermatException.wrapException(e), "", "Cant USER FROM GROUP, unknown failure.");
        }
    }

    public List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupExcepcion {
        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            }

            table.loadToMemory();

            return createAssetUserGroupsList(new ArrayList<ActorAssetUserGroup>(), table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), e, "asset User Group", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), FermatException.wrapException(e), "Asset User Group", "Cant Get Asset User Group, unknown failure.");
        }
    }

    private List<ActorAssetUserGroup> createAssetUserGroupsList(List<ActorAssetUserGroup> assetUserGroupRecordList, List<DatabaseTableRecord> records) {
        for (DatabaseTableRecord record : records) {
            AssetUserGroupRecord assetUserGroupRecord = new AssetUserGroupRecord();
            assetUserGroupRecord.setGroupId(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME));
            assetUserGroupRecord.setGroupName(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME));
            assetUserGroupRecordList.add(assetUserGroupRecord);
        }
        return assetUserGroupRecordList;
    }

    private boolean existsAssetUserGroup(String groupName) throws CantGetAssetUserGroupExcepcion {
        DatabaseTable table;
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            }

            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME, groupName, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return !table.getRecords().isEmpty();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), e, "asset User Group", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), FermatException.wrapException(e), "Asset User Group", "Cant get Asset User Group, unknown failure.");
        }
    }

    public List<ActorAssetUser> getListActorAssetUserByGroups(String groupName) throws CantGetAssetUsersListException {
        DatabaseTable tableGroup;
        DatabaseTable tableGroupMember;
        String groupId = "";
        String actorAssetUserPublicKey = "";
        List<ActorAssetUser> actorAssetUserList = new ArrayList<ActorAssetUser>();

        try {
            /**
             * 1) Get the table.
             */
            tableGroup = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (tableGroup == null) {
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP MEMBER, TABLE NOT FOUND.", " ASSET USER GROUP MEMBER", "");
            }
            tableGroup.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME, groupName, DatabaseFilterType.EQUAL);
            tableGroup.loadToMemory();
            for (DatabaseTableRecord record : tableGroup.getRecords()) {
                groupId = record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME);
            }

            tableGroupMember = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME);

            if (tableGroupMember == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            tableGroupMember.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME, groupId, DatabaseFilterType.EQUAL);
            tableGroupMember.loadToMemory();
            for (DatabaseTableRecord record : tableGroupMember.getRecords()) {
                actorAssetUserPublicKey = record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME);
                ActorAssetUser actorAssetUser = getActorAssetUserRegisteredByPublicKey(actorAssetUserPublicKey);
                actorAssetUserList.add(actorAssetUser);
            }

            // Return the list values.
            return actorAssetUserList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUsersListException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUsersListException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
    }

    public List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupExcepcion {
        DatabaseTable tableGroupMember;
        String groupId = "";
        List<ActorAssetUserGroup> assetUserGroupRecordList = new ArrayList<ActorAssetUserGroup>();
        try {
            tableGroupMember = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_TABLE_NAME);

            if (tableGroupMember == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP MEMBER, TABLE NOT FOUND.", " ASSET USER GROUP MEMBER", "");
            }
            tableGroupMember.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorAssetUserPublicKey, DatabaseFilterType.EQUAL);
            tableGroupMember.loadToMemory();
            for (DatabaseTableRecord record : tableGroupMember.getRecords()) {
                groupId = record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_MEMBER_GROUP_ID_COLUMN_NAME);
                ActorAssetUserGroup actorAssetUserGroup = getAssetUserGroup(groupId);
                assetUserGroupRecordList.add(actorAssetUserGroup);
            }

            return assetUserGroupRecordList;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), e, "asset User Group", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), FermatException.wrapException(e), "Asset User Group", "Cant Get Asset User Group, unknown failure.");
        }
    }

    public ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupExcepcion {
        AssetUserGroupRecord actorAssetUserGroup = new AssetUserGroupRecord();
        try {
            DatabaseTable tableGroup = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME);

            if (tableGroup == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetAssetUserGroupTableExcepcion("CANT GET ASSET USER GROUP, TABLE NOT FOUND.", " ASSET USER GROUP", "");
            }
            tableGroup.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME, groupId, DatabaseFilterType.EQUAL);
            tableGroup.loadToMemory();
            for (DatabaseTableRecord record : tableGroup.getRecords()) {
                actorAssetUserGroup.setGroupId(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_ID_COLUMN_NAME));
                actorAssetUserGroup.setGroupName(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_GROUP_NAME_COLUMN_NAME));
            }
            return actorAssetUserGroup;
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), e, "asset User Group", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_GROUP_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetAssetUserGroupExcepcion(e.getMessage(), FermatException.wrapException(e), "Asset User Group", "Cant Get Asset User Group, unknown failure.");
        }
    }

    private ActorAssetUser getActorAssetUserRegisteredByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException {
        DatabaseTable table;

        // Get Asset Users identities list.
        try {
            /**
             * 1) Get the table.
             */
            table = this.database.getTable(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant get asset User identity list, table not found.", "Plugin Identity", "Cant get asset user identity list, table not found.");
            }
            table.setStringFilter(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, actorPublicKey, DatabaseFilterType.EQUAL);

            table.loadToMemory();

            // Return the values.
            return addRecordsTableRegisteredToActorAssetUser(table.getRecords());
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Cant load " + AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetUserActorProfileImageException e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), e, "Asset User Actor", "Can't get profile ImageMiddleware.");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException(e.getMessage(), FermatException.wrapException(e), "Asset User Actor", "Cant get Asset User Actor list, unknown failure.");
        }
    }

    private ActorAssetUser addRecordsTableRegisteredToActorAssetUser(List<DatabaseTableRecord> records) throws InvalidParameterException, CantGetAssetUserActorProfileImageException {
        ActorAssetUser actorAssetUser = null;
        for (DatabaseTableRecord record : records) {
            CryptoAddress cryptoAddress = null;
            if (record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME) != null) {
                cryptoAddress = new CryptoAddress(
                        record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME),
                        CryptoCurrency.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME)));
            }

            actorAssetUser = new AssetUserActorRecord(
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME),
                    record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME),
                    Genders.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME)),
                    DAPConnectionState.getByCode(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME)),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    record.getDoubleValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME),
                    cryptoAddress,
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME),
                    record.getLongValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME),
                    getAssetUserProfileImagePrivateKey(record.getStringValue(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME)));
        }

        return actorAssetUser;
    }

}
