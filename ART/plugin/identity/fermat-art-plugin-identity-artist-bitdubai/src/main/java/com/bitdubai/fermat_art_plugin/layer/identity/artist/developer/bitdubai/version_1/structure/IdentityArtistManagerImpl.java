package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.ArtistIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.database.ArtistIdentityDao;
import com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions.CantInitializeArtistIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class IdentityArtistManagerImpl implements ArtistIdentityManager {
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

    private ArtistManager artistManager;

    private TokenlyArtistIdentityManager tokenlyArtistIdentityManager;


    /**
     * Constructor
     *
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityArtistManagerImpl(
            LogManager logManager,
            PluginDatabaseSystem pluginDatabaseSystem,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            DeviceUserManager deviceUserManager,
            ArtistManager artistManager,
            TokenlyArtistIdentityManager tokenlyArtistIdentityManager){
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.artistManager = artistManager;
        this.tokenlyArtistIdentityManager = tokenlyArtistIdentityManager;
    }

    private ArtistIdentityDao getArtistIdentityDao() throws CantInitializeArtistIdentityDatabaseException {
        return new ArtistIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
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
    public HashMap<ArtExternalPlatform, HashMap<UUID, String>> listExternalIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        /*
            We'll return a HashMap based on the external platform containing another hashmap with the user and the id to that platform
         */
        HashMap<ArtExternalPlatform, HashMap<UUID,String>> externalArtistIdentities = new HashMap<>();
        HashMap<UUID,String> externalArtist = new HashMap<>();
        for (ArtExternalPlatform externalPlatform:
                ArtExternalPlatform.values()) {
            //Future platform will need to be added manually to the switch
            switch (externalPlatform){
                case TOKENLY:
                    try {
                        final List<com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist> tokenlyArtists = tokenlyArtistIdentityManager.listIdentitiesFromCurrentDeviceUser();

                        for (com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist artist:
                                tokenlyArtists) {
                            externalArtist.put(artist.getId(),artist.getUsername());
                        }
                        if(externalArtist.size()>0)
                            externalArtistIdentities.put(externalPlatform,externalArtist);
                    } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException e) {
                        e.printStackTrace();
                        //errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                    }
                    break;
                default:
                    break;
            }
        }
        return externalArtistIdentities;
    }

    @Override
    public ArtIdentity getLinkedIdentity(String publicKey) {
        ArtIdentity artIdentity = null;
        try {
            Artist artist = getIdentityArtist(publicKey);
            if(artist != null){
                for (ArtExternalPlatform externalPlatform:
                        ArtExternalPlatform.values()) {
                    //Future platform will need to be added manually to the switch
                    switch (externalPlatform){
                        case TOKENLY:
                            final com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist tokenlyArtist = tokenlyArtistIdentityManager.getArtistIdentity(artist.getExternalIdentityID());
                            if(tokenlyArtist != null){
                                artIdentity = new ArtistIdentityImp(
                                        tokenlyArtist.getPublicKey(),
                                        tokenlyArtist.getProfileImage(),
                                        tokenlyArtist.getUsername(),
                                        tokenlyArtist.getId(),
                                        externalPlatform,
                                        tokenlyArtist.getUsername());
                            }
                            break;
                    }
                }
            }
        } catch (CantGetArtistIdentityException e) {
            //TODO: report error
            //errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException e) {
            //TODO: report error
            //errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException e) {
            //TODO: report error
            //errorManager.reportUnexpectedPluginException(Plugins.ARTIST_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return artIdentity;
    }

    @Override
    public Artist createArtistIdentity(
            String alias,
            byte[] imageBytes,
            String externalUsername,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType acceptConnectionsType,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {
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
                    imageBytes,
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
            return new ArtistRecord(
                    alias,
                    publicKey,
                    imageBytes,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUsername,
                    exposureLevel ,
                    acceptConnectionsType);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateArtistIdentityException("CAN'T CREATE NEW ARTIST IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void updateArtistIdentity(
            String alias,
            String publicKey,
            byte[] profileImage,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType acceptConnectionsType,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUserName) throws CantUpdateArtistIdentityException {
        try {
            getArtistIdentityDao().updateIdentityArtistUser(
                    publicKey,
                    alias,
                    profileImage,
                    exposureLevel,
                    acceptConnectionsType,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUserName);
            HashMap<ArtExternalPlatform,String> externalInformation = new HashMap<>();
            externalInformation.put(artExternalPlatform,externalUserName);
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

    @Override
    public Artist getArtistIdentity(String publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist(publicKey);
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
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

    @Override
    public void hideIdentity(String publicKey) throws CantHideIdentityException, IdentityNotFoundException {
        //TODO: to implement
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

    public Artist getIdentityArtist(String publicKey) throws CantGetArtistIdentityException {
        Artist artist = null;
        try {
            artist = getArtistIdentityDao().getIdentityArtist(publicKey);
        } catch (CantInitializeArtistIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return artist;
    }

}
