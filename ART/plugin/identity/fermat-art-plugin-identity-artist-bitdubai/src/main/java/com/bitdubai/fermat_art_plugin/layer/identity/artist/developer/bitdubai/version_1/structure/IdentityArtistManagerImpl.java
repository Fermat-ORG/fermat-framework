package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database.ArtistIdentityDao;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class IdentityArtistManagerImpl implements DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem {
    /**
     * IdentityAssetIssuerManagerImpl member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

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

    private ArtistManager artistManager;

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     *
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityArtistManagerImpl(ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, DeviceUserManager deviceUserManager, ArtistManager artistManager){
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.artistManager = artistManager;
    }

    private ArtistIdentityDao getArtistIdentityDao() throws CantInitializeArtistIdentityDatabaseException {
        return new ArtistIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    public List<Artist> getIdentityArtistFromCurrentDeviceUser() throws CantListArtistIdentitiesException {

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

    public Artist getIdentityArtist() throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist();
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }

    public Artist getIdentityArtist(String publicKey) throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist(publicKey);
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }

    public Artist createNewIdentityArtist(
            String alias,
            byte[] profileImage,
            UUID externalIdentityID,
            String externalUsername,
            ArtExternalPlatform artExternalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType acceptConnectionsType) throws CantCreateArtistIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            final String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getArtistIdentityDao().createNewUser(
                    alias,
                    publicKey,
                    privateKey,
                    loggedUser,
                    profileImage,
                    exposureLevel,
                    acceptConnectionsType,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUsername);


            Thread registerToAns = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        registerIdentitiesANS(publicKey);
                    }catch (Exception e){

                    }
                }},"Artist Identity register ANS");
            registerToAns.start();
            return new ArtistIdentityImp(
                    alias,
                    publicKey,
                    profileImage,
                    externalIdentityID,
                    pluginFileSystem,
                    pluginId,
                    artExternalPlatform,
                    exposureLevel ,
                    acceptConnectionsType,
                    externalUsername);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    public void updateIdentityArtist(
            String alias,
            String publicKey,
            byte[] profileImage,
            UUID externalIdentityID,
            String externalUsername,
            ArtExternalPlatform artExternalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType acceptConnectionsType) throws CantUpdateArtistIdentityException {

        try {
            getArtistIdentityDao().updateIdentityArtistUser(
                    publicKey,
                    alias,
                    profileImage,
                    exposureLevel,
                    acceptConnectionsType,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUsername);
            HashMap<ArtExternalPlatform,String> externalInformation = new HashMap<>();
            externalInformation.put(artExternalPlatform,externalUsername);
            List data = new ArrayList();
            data.add(profileImage);
            data.add(externalInformation);
            final ArtistExposingData artistExposingData = new ArtistExposingData(
                    publicKey,
                    alias,
                    XMLParser.parseObject(data));

            Thread registerToAns = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        artistManager.updateIdentity(artistExposingData);
                    }catch (Exception e){

                    }
                }},"Artist Identity update ANS");
            registerToAns.start();

        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
    }

    public void registerIdentitiesANS(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        try {
            Artist artist = getIdentityArtist(publicKey);
            HashMap<ArtExternalPlatform,String> externalInformation = new HashMap<>();
            externalInformation.put(artist.getExternalPlatform(),artist.getExternalUsername());
            List data = new ArrayList();
            data.add(artist.getProfileImage());
            data.add(externalInformation);
            ArtistExposingData artistExposingData = new ArtistExposingData(
                    artist.getPublicKey(),
                    artist.getAlias(),
                    XMLParser.parseObject(data));
            artistManager.exposeIdentity(artistExposingData);
        } catch (CantGetArtistIdentityException | CantExposeIdentityException e) {
            e.printStackTrace();
        }
    }


}
