package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantGetTokenlyArtistIdentityPrivateKeyException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantGetTokenlyArtistIdentityProfileImageException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyArtistIdentityDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyArtistIdentityImp;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyArtistIdentityRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class TokenlyArtistIdentityDao implements DealsWithPluginDatabaseSystem {
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

    public TokenlyArtistIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeTokenlyArtistIdentityDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeTokenlyArtistIdentityDatabaseException e) {
            throw new CantInitializeTokenlyArtistIdentityDatabaseException(e.getMessage());
        }
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeTokenlyArtistIdentityDatabaseException
     */
    private void initializeDatabase() throws CantInitializeTokenlyArtistIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeTokenlyArtistIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            TokenlyArtistIdentityDatabaseFactory tokenlyArtistIdentityDatabaseFactory = new TokenlyArtistIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = tokenlyArtistIdentityDatabaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeTokenlyArtistIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeTokenlyArtistIdentityDatabaseException(e.getMessage());

        }
    }

    /**
     * first i persist private key on a file
     * second i insert the record in database
     * third i save the profile image file
     *
     * @param user
     * @param id
     * @param privateKey
     * @param deviceUser
     * @param profileImage
     * @throws CantCreateNewDeveloperException
     */
    public void createNewUser(User user, UUID id,String publicKey, String privateKey, DeviceUser deviceUser, byte[] profileImage,String password, ExternalPlatform externalPlatform,
                              ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists(user.getUsername())) {
                throw new CantCreateNewDeveloperException("Cant create new Redeem Point Identity, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setUUIDValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME, id);
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_ID_COLUMN_NAME, user.getTokenlyId());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME, user.getApiSecretKey());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PASSWORD_COLUMN_NAME,  CryptoHasher.performSha256(password));
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_USER_NAME_COLUMN_NAME, user.getUsername());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACCESS_TOKEN_COLUMN_NAME, user.getApiToken());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EMAIL_COLUMN_NAME, user.getEmail());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME, externalPlatform.getCode());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME, exposureLevel.getCode());
            record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACEEPTS_CONNECTIONS_TYPE_COLUMN_NAME, artistAcceptConnectionsType.getCode());


            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "Redeem Point Identity", "Cant create new Redeem Point, insert database problems.");

        } catch (CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateNewDeveloperException(e.getMessage(), e, "ARedeem Point Identity", "Cant create new Redeem Point, persist private key error.");

        } catch (Exception e) {
            // Failure unknown.

            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Identity", "Cant create new Redeem Point, unknown failure.");
        }
    }

    public void updateIdentityArtistUser(User user, String password, UUID id,String publickey, byte[] profileImage, ExternalPlatform externalPlatform,
                                         ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Redeem Point Identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addUUIDFilter(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setUUIDValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME, id);
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_ID_COLUMN_NAME, user.getTokenlyId());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publickey);
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME, user.getApiSecretKey());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PASSWORD_COLUMN_NAME,  CryptoHasher.performSha256(password));
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_USER_NAME_COLUMN_NAME, user.getUsername());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACCESS_TOKEN_COLUMN_NAME, user.getApiToken());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EMAIL_COLUMN_NAME, user.getEmail());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME, externalPlatform.getCode());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME, exposureLevel.getCode());
                record.setStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACEEPTS_CONNECTIONS_TYPE_COLUMN_NAME, artistAcceptConnectionsType.getCode());

                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publickey, profileImage);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateArtistIdentityException(e.getMessage(), e, "Redeem Point Identity", "Cant update Redeem Point Identity, database problems.");
        } catch (CantPersistProfileImageException e) {
            throw new CantUpdateArtistIdentityException(e.getMessage(), e, "Redeem Point Identity", "Cant update Redeem Point Identity, persist image error.");
        } catch (Exception e) {
            throw new CantUpdateArtistIdentityException(e.getMessage(), FermatException.wrapException(e), "Redeem Point Identity", "Cant update Redeem Point Identity, unknown failure.");
        }
    }

    public List<Artist> getIdentityArtistsFromCurrentDeviceUser(DeviceUser deviceUser) throws CantListArtistIdentitiesException {


        // Setup method.
        List<Artist> list = new ArrayList<>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        // Get Redeem Point identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.");
            }


            // 2) Find the Redeem Point.
            table.addStringFilter(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Redeem Point.
            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                /*list.add(new IdentityAssetRedeemPointImpl(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        getArtistIdentityPrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME)),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId), );*/
                list.add(new TokenlyArtistIdentityRecord(record.getUUIDValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_USER_NAME_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACCESS_TOKEN_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PASSWORD_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EMAIL_COLUMN_NAME),
                        ExternalPlatform.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME)),
                        ExposureLevel.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME)),
                        ArtistAcceptConnectionsType.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACEEPTS_CONNECTIONS_TYPE_COLUMN_NAME))

                        ));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListArtistIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListArtistIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Issuer identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    public Artist getIdentityArtist() throws CantGetArtistIdentityException {

        // Setup method.
        Artist artist = null;
        DatabaseTable table; // Intra User table.

        // Get Asset Issuers identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.");
            }


            // 2) Find the Identity Issuers.

//            table.addStringFilter(AssetIssuerIdentityDatabaseConstants.ASSET_ISSUER_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Identity Issuers.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                /*artist = new IdentityAssetRedeemPointImpl(
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME)));*/
                artist = new  TokenlyArtistIdentityImp(record.getUUIDValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_USER_NAME_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACCESS_TOKEN_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        ExternalPlatform.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME)),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EMAIL_COLUMN_NAME),
                        ExposureLevel.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME)),
                        ArtistAcceptConnectionsType.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACEEPTS_CONNECTIONS_TYPE_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId);

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetArtistIdentityException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetArtistIdentityException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Redeem Point identity list, unknown failure.");
        }

        // Return the list values.
        return artist;
    }
    public Artist getIdentityArtist(UUID id) throws CantGetArtistIdentityException {

        // Setup method.
        Artist artist = null;
        DatabaseTable table; // Intra User table.

        // Get Asset Issuers identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException("Cant get Asset Issuer identity list, table not found.");
            }


            // 2) Find the Identity Issuers.

            table.addUUIDFilter(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME, id, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            // 3) Get Identity Issuers.

            for (DatabaseTableRecord record : table.getRecords()) {

                // Add records to list.
                /*artist = new IdentityAssetRedeemPointImpl(
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME)));*/
                artist = new  TokenlyArtistIdentityRecord(
                        record.getUUIDValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_ID_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_USER_NAME_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACCESS_TOKEN_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_PASSWORD_COLUMN_NAME),
                        record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EMAIL_COLUMN_NAME),
                        ExternalPlatform.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME)),
                        ExposureLevel.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME)),
                        ArtistAcceptConnectionsType.getByCode(record.getStringValue(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_ACEEPTS_CONNECTIONS_TYPE_COLUMN_NAME)));

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetArtistIdentityException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetArtistIdentityException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Redeem Point identity list, unknown failure.");
        }

        // Return the list values.
        return artist;
    }

    public byte[] getArtistProfileImagePrivateKey(String publicKey) throws CantGetTokenlyArtistIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TokenlyArtistIdentityPluginRoot.TOKENLY_ARTIST_IDENTITY_PROFILE_IMAGE + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetTokenlyArtistIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetTokenlyArtistIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
        }

        return profileImage;
    }

    /**
     * Private Methods
     */

    private void persistNewUserPrivateKeysFile(String publicKey, String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TokenlyArtistIdentityPluginRoot.TOKENLY_ARTIST_IDENTITY_PRIVATE_KEY + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);
        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        } catch (Exception e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }
    }

    private void persistNewUserProfileImage(String publicKey, byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TokenlyArtistIdentityPluginRoot.TOKENLY_ARTIST_IDENTITY_PROFILE_IMAGE + "_" + publicKey,
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

    /**
     * <p>Method that check if alias exists.
     *
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists(String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists");
            }

            table.addStringFilter(TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_SECRET_KEY_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException(em.getMessage(), em, "Asset Issuer  Identity", "Cant load " + TokenlyArtistIdentityDatabaseConstants.TOKENLY_ARTIST_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException(e.getMessage(), FermatException.wrapException(e), "Asset Issuer  Identity", "unknown failure.");
        }
    }

    public String getArtistIdentityPrivateKey(String publicKey) throws CantGetTokenlyArtistIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    TokenlyArtistIdentityPluginRoot.TOKENLY_ARTIST_IDENTITY_PRIVATE_KEY + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetTokenlyArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetTokenlyArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetTokenlyArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }

        return privateKey;
    }
}
