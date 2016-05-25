package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.external_api.exceptions.CantGetUserException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyArtistIdentityDatabaseException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class TokenlyIdentityArtistManagerImpl implements TokenlyArtistIdentityManager {
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    //ErrorManager errorManager;

    /**
     * DealsWithLogger interface mmeber variables
     */
    LogManager logManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem interface member variables
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithDeviceUsers Interface member variables.
     */
    private DeviceUserManager deviceUserManager;

    private TokenlyApiManager tokenlyApiManager;

    /**
     * Constructor
     *
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public TokenlyIdentityArtistManagerImpl(
            LogManager logManager,
            PluginDatabaseSystem pluginDatabaseSystem,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            DeviceUserManager deviceUserManager,
            TokenlyApiManager tokenlyApiManager){
        //this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    private TokenlyArtistIdentityDao getArtistIdentityDao() throws CantInitializeTokenlyArtistIdentityDatabaseException {
        return new TokenlyArtistIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    public Artist getIdentitArtist() throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist();
        } catch (CantInitializeTokenlyArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        try {
            List<Artist> artists;

            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            artists = getArtistIdentityDao().getIdentityArtistsFromCurrentDeviceUser(loggedUser);

            return artists;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListArtistIdentitiesException("CAN'T GET ASSET NEW ARTIST IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListArtistIdentitiesException("CAN'T GET ASSET NEW ARTIST IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public Artist createArtistIdentity(
            String userName,
            byte[] profileImage,
            String password,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType)
            throws
            CantCreateArtistIdentityException,
            ArtistIdentityAlreadyExistsException,
            WrongTokenlyUserCredentialsException {
        try {
            DeviceUser deviceUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            UUID id = UUID.randomUUID();
            String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();
            User user=null;
            try{
                if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                    user = tokenlyApiManager.validateTokenlyUser(userName, password);
            } catch (CantGetUserException | InterruptedException | ExecutionException e) {
                throw new CantCreateArtistIdentityException(
                        e,
                        "Validating Tokenly User",
                        "Cannot create Tokenly User");
            }
            getArtistIdentityDao().createNewUser(
                    user,
                    id,
                    publicKey,
                    privateKey,
                    deviceUser,
                    profileImage,
                    password,
                    externalPlatform,
                    exposureLevel,
                    artistAcceptConnectionsType);

            return new TokenlyArtistIdentityRecord(
                    user,
                    id,
                    publicKey,
                    profileImage,
                    externalPlatform,
                    exposureLevel,
                    artistAcceptConnectionsType);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public Artist updateArtistIdentity(
            String username,
            String password,
            UUID id,
            String publicKey,
            byte[] profileImage,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType)
            throws CantUpdateArtistIdentityException, WrongTokenlyUserCredentialsException {
        try {
            User user=null;
            try{
                if(externalPlatform == ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM)
                    user = tokenlyApiManager.validateTokenlyUser(username, password);
            } catch (CantGetUserException | InterruptedException | ExecutionException e) {
                throw new CantUpdateArtistIdentityException(
                        e,
                        "Validating Tokenly User",
                        "Cannot create Tokenly User");
            }
            getArtistIdentityDao().updateIdentityArtistUser(
                    user,
                    password,
                    id,
                    publicKey,
                    profileImage,
                    externalPlatform,
                    exposureLevel,
                    artistAcceptConnectionsType);
            return getArtistIdentityDao().getIdentityArtist(id);
        } catch (CantInitializeTokenlyArtistIdentityDatabaseException e) {
            e.printStackTrace();
        } catch (CantGetArtistIdentityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Artist getArtistIdentity(UUID publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist(publicKey);
        } catch (CantInitializeTokenlyArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }
}
