package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.ArtistIdentityPluginRoot;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantGetArtistIdentityPrivateKeyException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantGetArtistIdentityProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure.ArtistIdentityImp;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class ArtistIdentityDao implements DealsWithPluginDatabaseSystem {
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

    public ArtistIdentityDao(
            PluginDatabaseSystem pluginDatabaseSystem,
            PluginFileSystem pluginFileSystem,
            UUID pluginId) throws CantInitializeArtistIdentityDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            throw new CantInitializeArtistIdentityDatabaseException(e.getMessage());
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
     * @throws CantInitializeArtistIdentityDatabaseException
     */
    private void initializeDatabase() throws CantInitializeArtistIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeArtistIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            ArtistIdentityDatabaseFactory artistIdentityDatabaseFactory = new ArtistIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = artistIdentityDatabaseFactory.createDatabase(pluginId);

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeArtistIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeArtistIdentityDatabaseException(e.getMessage());

        }
    }

    /**
     * first I persist private key on a file
     * second I insert the record in database
     * third I save the profile image file
     *
     * @param alias
     * @param publicKey
     * @param privateKey
     * @param deviceUser
     * @param profileImage
     * @param exposureLevel
     * @param acceptConnectionsType
     * @param externalIdentityID
     * @throws CantCreateArtistIdentityException
     */
    public void createNewUser(
            String alias,
            String publicKey,
            String privateKey,
            DeviceUser deviceUser,
            byte[] profileImage,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType acceptConnectionsType,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantCreateArtistIdentityException {

        try {
            if (aliasExists(alias)) {
                throw new CantCreateArtistIdentityException(
                        "Cant create new Artist Identity, alias exists.",
                        "Artist Identity",
                        "Cant create new Artist Identity, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(
                    ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                    publicKey);
            record.setStringValue(
                    ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME,
                    alias);
            record.setStringValue(
                    ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME,
                    deviceUser.getPublicKey());
            //External Id
            if(externalIdentityID!=null){
                record.setUUIDValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_IDENTITY_ID_COLUMN_NAME,
                        externalIdentityID);
            }
            //External platform
            if(artExternalPlatform==null){
                record.setStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME,
                        artExternalPlatform.UNDEFINED.getCode());
            } else {
                record.setStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME,
                        artExternalPlatform.getCode());
            }
            //external username
            record.setStringValue(
                    ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_USERNAME_COLUMN_NAME,
                    externalUsername);

            record.setFermatEnum(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME,exposureLevel);
            record.setFermatEnum(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ACCEPT_CONNECTIONS_TYPE_COLUMN_NAME,acceptConnectionsType);


            table.insertRecord(record);

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e) {
            // Cant insert record.
            throw new CantCreateArtistIdentityException(
                    e.getMessage(),
                    e,
                    "Artist Identity",
                    "Cant create new Artist identity, insert database problems.");

        } catch (CantPersistPrivateKeyException e) {
            // Cant insert record.
            throw new CantCreateArtistIdentityException(
                    e.getMessage(),
                    e, "Artist Identity Identity",
                    "Cant create new Artist, persist private key error.");

        } catch (Exception e) {
            // Failure unknown.

            throw new CantCreateArtistIdentityException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "Cant create new Artist Identity, unknown failure.");
        }
    }

    public void updateIdentityArtistUser(
            String publicKey,
            String alias,
            byte[] profileImage,
            ExposureLevel exposureLevel, ArtistAcceptConnectionsType acceptConnectionsType, UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,String externalUsername) throws CantUpdateArtistIdentityException {
        try {
            /**
             * 1) Get the table.
             */
            DatabaseTable table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantUpdateArtistIdentityException(
                        "Cant get Artist Identity list, table not found.",
                        "Artist Identity",
                        "Cant get Artist identity list, table not found.");
            }

            // 2) Find the Intra users.
            table.addStringFilter(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Intra users.
            for (DatabaseTableRecord record : table.getRecords()) {
                //set new values
                record.setStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME,
                        publicKey);
                record.setStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME,
                        alias);
                //External platform
                if(artExternalPlatform==null){
                    record.setStringValue(
                            ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME,
                            artExternalPlatform.UNDEFINED.getCode());
                } else {
                    record.setStringValue(
                            ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME,
                            artExternalPlatform.getCode());
                }
                if(externalIdentityID!= null)
                    record.setUUIDValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_IDENTITY_ID_COLUMN_NAME, externalIdentityID);

                record.setStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_USERNAME_COLUMN_NAME,
                        externalUsername);

                record.setFermatEnum(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME,exposureLevel);
                record.setFermatEnum(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ACCEPT_CONNECTIONS_TYPE_COLUMN_NAME,acceptConnectionsType);

                table.updateRecord(record);
            }

            if (profileImage != null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantUpdateRecordException e) {
            throw new CantUpdateArtistIdentityException(
                    e.getMessage(),
                    e,
                    "Artist Identity",
                    "Cant update Artist Identity, database problems.");
        } catch (CantPersistProfileImageException e) {
            throw new CantUpdateArtistIdentityException(
                    e.getMessage(),
                    e,
                    "Artist Identity",
                    "Cant update Artist Identity, persist image error.");
        } catch (Exception e) {
            throw new CantUpdateArtistIdentityException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "Cant update Artist Identity, unknown failure.");
        }
    }

    public List<Artist> getIdentityArtistsFromCurrentDeviceUser(DeviceUser deviceUser) throws CantListArtistIdentitiesException {


        // Setup method.
        List<Artist> list = new ArrayList<>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantUpdateArtistIdentityException(
                        "Cant get Artist identity list, table not found.",
                        "Artist Identity",
                        "Cant get Artist identity list, table not found.");
            }



            table.addStringFilter(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();



            for (DatabaseTableRecord record : table.getRecords()) {


                UUID externalIdentityId;
                String externalIdentityString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_IDENTITY_ID_COLUMN_NAME);
                        if(externalIdentityString==null || externalIdentityString.isEmpty()){
                            externalIdentityId=null;
                        } else {
                            externalIdentityId = UUID.fromString(externalIdentityString);
                        }
                //External platform
                ArtExternalPlatform externalPlatform;
                String externalPlatformString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME);
                        if(externalPlatformString==null || externalPlatformString.isEmpty()){
                            externalPlatform = ArtExternalPlatform.UNDEFINED;
                        } else{
                            externalPlatform = ArtExternalPlatform.getByCode(externalPlatformString);
                        }
                //External username
                String externalUsername = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_USERNAME_COLUMN_NAME);
                //Exposure level
                ExposureLevel exposureLevel;
                String exposureLevelString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME);
                if(exposureLevelString==null || exposureLevelString.isEmpty()){
                    exposureLevel = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
                } else{
                    exposureLevel = ExposureLevel.getByCode(exposureLevelString);
                }
                //
                ArtistAcceptConnectionsType artistAcceptConnectionsType;
                String artistAcceptConnectionsTypeString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ACCEPT_CONNECTIONS_TYPE_COLUMN_NAME);
                if(artistAcceptConnectionsTypeString==null || artistAcceptConnectionsTypeString.isEmpty()){
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE;
                } else{
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.getByCode(artistAcceptConnectionsTypeString);
                }
                list.add(new ArtistIdentityImp(record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        externalIdentityId,
                        pluginFileSystem,
                        pluginId,
                        externalPlatform,
                        exposureLevel,
                        artistAcceptConnectionsType,
                        externalUsername));

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListArtistIdentitiesException(
                    e.getMessage(),
                    e,
                    "Artist Identity",
                    "Cant load " + ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantListArtistIdentitiesException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "Cant get Artist identity list, unknown failure.");
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
            table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantUpdateArtistIdentityException(
                        "Cant get Artist identity list, table not found.",
                        "Artist Identity",
                        "Cant get Artist identity list, table not found.");
            }


            table.loadToMemory();


            for (DatabaseTableRecord record : table.getRecords()) {

                UUID externalIdentityId;
                String externalIdentityString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_IDENTITY_ID_COLUMN_NAME);
                if(externalIdentityString==null || externalIdentityString.isEmpty()){
                    externalIdentityId=null;
                } else {
                    externalIdentityId = UUID.fromString(externalIdentityString);
                }
                //External platform
                ArtExternalPlatform externalPlatform;
                String externalPlatformString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME);
                if(externalPlatformString==null || externalPlatformString.isEmpty()){
                    externalPlatform = ArtExternalPlatform.UNDEFINED;
                } else{
                    externalPlatform = ArtExternalPlatform.getByCode(externalPlatformString);
                }
                //External username
                String externalUsername = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_USERNAME_COLUMN_NAME);
                //Exposure level
                ExposureLevel exposureLevel;
                String exposureLevelString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME);
                if(exposureLevelString==null || exposureLevelString.isEmpty()){
                    exposureLevel = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
                } else{
                    exposureLevel = ExposureLevel.getByCode(exposureLevelString);
                }
                //
                ArtistAcceptConnectionsType artistAcceptConnectionsType;
                String artistAcceptConnectionsTypeString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ACCEPT_CONNECTIONS_TYPE_COLUMN_NAME);
                if(artistAcceptConnectionsTypeString==null || artistAcceptConnectionsTypeString.isEmpty()){
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE;
                } else{
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.getByCode(artistAcceptConnectionsTypeString);
                }
                artist = new ArtistIdentityImp(
                        record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        externalIdentityId,
                        pluginFileSystem,
                        pluginId,
                        externalPlatform,
                        exposureLevel,
                        artistAcceptConnectionsType,
                        externalUsername);

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetArtistIdentityException(
                    e.getMessage(),
                    e, "Artist Identity",
                    "Cant load " + ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetArtistIdentityException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "Cant get Artist identity list, unknown failure.");
        }

        // Return the list values.
        return artist;
    }
    public Artist getIdentityArtist(String publicKey) throws CantGetArtistIdentityException {

        // Setup method.
        Artist artist = null;
        DatabaseTable table; // Intra User table.

        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantUpdateArtistIdentityException("Cant get Asset Issuer identity list, table not found.", "Asset IssuerIdentity", "Cant get Intra User identity list, table not found.");
            }



            table.addStringFilter(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);
            table.loadToMemory();


            for (DatabaseTableRecord record : table.getRecords()) {

                UUID externalIdentityId;
                String externalIdentityString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_IDENTITY_ID_COLUMN_NAME);
                if(externalIdentityString==null || externalIdentityString.isEmpty()){
                    externalIdentityId=null;
                } else {
                    externalIdentityId = UUID.fromString(externalIdentityString);
                }
                //External platform
                ArtExternalPlatform externalPlatform;
                String externalPlatformString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_PLATFORM_COLUMN_NAME);
                if(externalPlatformString==null || externalPlatformString.isEmpty()){
                    externalPlatform = ArtExternalPlatform.UNDEFINED;
                } else{
                    externalPlatform = ArtExternalPlatform.getByCode(externalPlatformString);
                }
                //External username
                String externalUsername = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXTERNAL_USERNAME_COLUMN_NAME);
                //Exposure level
                ExposureLevel exposureLevel;
                String exposureLevelString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_EXPOSURE_LEVEL_COLUMN_NAME);
                if(exposureLevelString==null || exposureLevelString.isEmpty()){
                    exposureLevel = ExposureLevel.DEFAULT_EXPOSURE_LEVEL;
                } else{
                    exposureLevel = ExposureLevel.getByCode(exposureLevelString);
                }
                //
                ArtistAcceptConnectionsType artistAcceptConnectionsType;
                String artistAcceptConnectionsTypeString = record.getStringValue(
                        ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ACCEPT_CONNECTIONS_TYPE_COLUMN_NAME);
                if(artistAcceptConnectionsTypeString==null || artistAcceptConnectionsTypeString.isEmpty()){
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.DEFAULT_ARTIST_ACCEPT_CONNECTION_TYPE;
                } else{
                    artistAcceptConnectionsType = ArtistAcceptConnectionsType.getByCode(artistAcceptConnectionsTypeString);
                }
                artist = new ArtistIdentityImp(
                        record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getArtistProfileImagePrivateKey(record.getStringValue(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        externalIdentityId,
                        pluginFileSystem,
                        pluginId,
                        externalPlatform,
                        exposureLevel,
                        artistAcceptConnectionsType,
                        externalUsername);

            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantGetArtistIdentityException(
                    e.getMessage(),
                    e,
                    "Artist Identity",
                    "Cant load " + ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (Exception e) {
            throw new CantGetArtistIdentityException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "Cant get Artist identity list, unknown failure.");
        }

        // Return the list values.
        return artist;
    }

    public byte[] getArtistProfileImagePrivateKey(String publicKey) throws CantGetArtistIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ArtistIdentityPluginRoot.ARTIST_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetArtistIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetArtistIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", FermatException.wrapException(e), "", "");
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
                    ArtistIdentityPluginRoot.ARTIST_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
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
                    ArtistIdentityPluginRoot.ARTIST_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
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
     * @throws CantCreateArtistIdentityException
     */
    private boolean aliasExists(String alias) throws CantCreateArtistIdentityException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantUpdateArtistIdentityException(
                        "Cant check if alias exists",
                        "Artist Identity", "");
            }

            table.addStringFilter(ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords().size() > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateArtistIdentityException(
                    em.getMessage(),
                    em,
                    "Artist Identity",
                    "Cant load " + ArtistIdentityDatabaseConstants.ARTIST_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateArtistIdentityException(
                    e.getMessage(),
                    FermatException.wrapException(e),
                    "Artist Identity",
                    "unknown failure.");
        }
    }

    public String getArtistIdentityPrivateKey(String publicKey) throws CantGetArtistIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ArtistIdentityPluginRoot.ARTIST_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        } catch (Exception e) {
            throw new CantGetArtistIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", FermatException.wrapException(e), "", "");
        }

        return privateKey;
    }
}
