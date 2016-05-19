package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.FanManager;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanaticIdentityManager;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.database.FanaticIdentityDao;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions.CantInitializeFanaticIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gabriel Araujo 10/03/16.
 */
public class IdentityFanaticManagerImpl implements FanaticIdentityManager {
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

    private FanManager fanManager;

    private TokenlyFanIdentityManager tokenlyFanIdentityManager;


    /**
     * Constructor
     *
     * @param logManager
     * @param pluginDatabaseSystem
     * @param pluginFileSystem
     */
    public IdentityFanaticManagerImpl(
            LogManager logManager,
            PluginDatabaseSystem pluginDatabaseSystem,
            PluginFileSystem pluginFileSystem,
            UUID pluginId,
            DeviceUserManager deviceUserManager,
            FanManager fanManager,
            TokenlyFanIdentityManager tokenlyFanIdentityManager){

        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.deviceUserManager = deviceUserManager;
        this.fanManager = fanManager;
        this.tokenlyFanIdentityManager = tokenlyFanIdentityManager;
    }

    private FanaticIdentityDao getFanaticIdentityDao() throws CantInitializeFanaticIdentityDatabaseException {
        return new FanaticIdentityDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);
    }

    public List<Fanatic> getIdentityArtistFromCurrentDeviceUser() throws CantListFanIdentitiesException {

        try {

            List<Fanatic> artists;


            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();
            artists = getFanaticIdentityDao().getIdentityFanaticsFromCurrentDeviceUser(loggedUser);


            return artists;

        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantListFanIdentitiesException("CAN'T GET ASSET NEW Fanatic IDENTITIES", e, "Error get logged user device", "");
        } catch (Exception e) {
            throw new CantListFanIdentitiesException("CAN'T GET ASSET NEW Fanatic IDENTITIES", FermatException.wrapException(e), "", "");
        }
    }

    public Fanatic getIdentityFanatic(String publicKey) throws CantGetFanIdentityException {
        Fanatic Fanatic = null;
        try {
            Fanatic = getFanaticIdentityDao().getIdentityFanatic(publicKey);
        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
        return Fanatic;
    }

    /**
     * This method creates a new Fan Identity
     * @param alias
     * @param profileImage
     * @param externalIdentityID
     * @param artExternalPlatform
     * @return
     * @throws CantCreateFanIdentityException
     */
    public Fanatic createNewIdentityFanatic(
            String alias,
            byte[] profileImage,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantCreateFanIdentityException {
        try {
            DeviceUser loggedUser = deviceUserManager.getLoggedInDeviceUser();

            ECCKeyPair keyPair = new ECCKeyPair();
            final String publicKey = keyPair.getPublicKey();
            String privateKey = keyPair.getPrivateKey();

            getFanaticIdentityDao().createNewUser(
                    alias,
                    publicKey,
                    privateKey,
                    loggedUser,
                    profileImage,
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

            return new FanRecord(
                    alias,
                    publicKey,
                    profileImage,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUsername);
        } catch (CantGetLoggedInDeviceUserException e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW Fanatic IDENTITY", e, "Error getting current logged in device user", "");
        } catch (Exception e) {
            throw new CantCreateFanIdentityException("CAN'T CREATE NEW Fanatic IDENTITY", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * This method updates the Fan identity
     * @param alias
     * @param publicKey
     * @param profileImage
     * @param externalIdentityID
     * @param artExternalPlatform
     * @throws CantUpdateFanIdentityException
     */
    public void updateIdentityFanatic(
            String alias,
            String publicKey,
            byte[] profileImage,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantUpdateFanIdentityException {
        try {
            getFanaticIdentityDao().updateIdentityFanaticUser(
                    publicKey,
                    alias,
                    profileImage,
                    externalIdentityID,
                    artExternalPlatform,
                    externalUsername);
            List extraDataList = new ArrayList();
            extraDataList.add(profileImage);
            HashMap<ArtExternalPlatform,String> externalPlatformInformationMap = new HashMap<>();
            externalPlatformInformationMap.put(artExternalPlatform, externalUsername);
            extraDataList.add(externalPlatformInformationMap);
            String extraDataString = XMLParser.parseObject(extraDataList);
            final FanExposingData fanExposingData = new FanExposingData(publicKey,alias,extraDataString);
            Thread updateToAns = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fanManager.updateIdentity(fanExposingData);
                    }catch (Exception e){

                    }
                }},"Artist Identity update ANS");
            updateToAns.start();

        } catch (CantInitializeFanaticIdentityDatabaseException e) {
            e.printStackTrace();
        }
    }

    public void registerIdentitiesANS(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {
        try {
            Fanatic fanatic = getIdentityFanatic(publicKey);
            List extraDataList = new ArrayList();
            extraDataList.add(fanatic.getProfileImage());
            HashMap<ArtExternalPlatform,String> externalPlatformInformationMap = new HashMap<>();
            externalPlatformInformationMap.put(fanatic.getExternalPlatform(),fanatic.getExternalUsername());
            extraDataList.add(externalPlatformInformationMap);
            String extraDataString = XMLParser.parseObject(extraDataList);
            FanExposingData fanExposingData = new FanExposingData(
                    fanatic.getPublicKey(),
                    fanatic.getAlias(),
                    extraDataString);
            fanManager.exposeIdentity(fanExposingData);
        } catch (CantGetFanIdentityException | CantExposeIdentityException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Fanatic> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return getIdentityArtistFromCurrentDeviceUser();
    }

    @Override
    public HashMap<ArtExternalPlatform, HashMap<UUID, String>> listExternalIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {

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
                        final List<Fan> tokenlyArtists = tokenlyFanIdentityManager.listIdentitiesFromCurrentDeviceUser();

                        for (Fan Fanatic:
                                tokenlyArtists) {
                            externalArtist.put(Fanatic.getId(),Fanatic.getUsername());
                        }
                        if(externalArtist.size()>0)
                            externalArtistIdentities.put(externalPlatform,externalArtist);
                    } catch (com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException e) {
                        e.printStackTrace();
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
            Fanatic Fanatic = getIdentityFanatic(publicKey);
            if(Fanatic != null){
                for (ArtExternalPlatform externalPlatform:
                        ArtExternalPlatform.values()) {
                    //Future platform will need to be added manually to the switch
                    switch (externalPlatform){
                        case TOKENLY:
                            final Fan tokenlyArtist = tokenlyFanIdentityManager.getFanIdentity(Fanatic.getExternalIdentityID());
                            if(tokenlyArtist != null){
                                artIdentity = new FanRecord(
                                        tokenlyArtist.getUsername(),
                                        tokenlyArtist.getPublicKey(),
                                        tokenlyArtist.getProfileImage(),
                                        tokenlyArtist.getId(),
                                        externalPlatform,
                                        tokenlyArtist.getUsername());
                            }
                            break;
                    }
                }
            }
        } catch (CantGetFanIdentityException e) {
            //TODO:TO report
            //errorManager.reportUnexpectedPluginException(Plugins.FANATIC_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException e) {
            //TODO:TO report
            //errorManager.reportUnexpectedPluginException(Plugins.FANATIC_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        } catch (com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException e) {
            //TODO:TO report
            //errorManager.reportUnexpectedPluginException(Plugins.FANATIC_IDENTITY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return artIdentity;
    }

    /**
     * Through the method <code>createFanaticIdentity</code> you can create a new Fan identity.
     * @param alias
     * @param imageBytes
     * @param externalIdentityId
     * @param artExternalPlatform
     * @return
     * @throws CantCreateFanIdentityException
     */
    @Override
    public Fanatic createFanaticIdentity(
            String alias,
            byte[] imageBytes,
            UUID externalIdentityId,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantCreateFanIdentityException {
        return createNewIdentityFanatic(
                alias,
                imageBytes,
                externalIdentityId,
                artExternalPlatform,
                externalUsername);
    }

    /**
     * This method updates the fan identity
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     * @param artExternalPlatform
     * @throws CantUpdateFanIdentityException
     */
    @Override
    public void updateFanIdentity(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws CantUpdateFanIdentityException {
        updateIdentityFanatic(
                alias,
                publicKey,
                imageProfile,
                externalIdentityID,
                artExternalPlatform,
                externalUsername);
    }

    @Override
    public Fanatic getFanIdentity(String publicKey)
            throws CantGetFanIdentityException, IdentityNotFoundException {
        return getIdentityFanatic(publicKey);
    }

    @Override
    public void publishIdentity(String publicKey)
            throws CantPublishIdentityException, IdentityNotFoundException {
        registerIdentitiesANS(publicKey);
    }
}
