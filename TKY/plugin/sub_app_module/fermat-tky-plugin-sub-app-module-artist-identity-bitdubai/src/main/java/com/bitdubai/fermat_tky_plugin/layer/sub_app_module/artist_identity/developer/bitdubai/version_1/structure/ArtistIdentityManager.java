package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.enums.TokenlyAPIStatus;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TokenlyAPINotAvailableException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.TokenlyApiManager;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.ArtistIdentitiesList;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistPreferenceSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class ArtistIdentityManager
        extends ModuleManagerImpl<TokenlyArtistPreferenceSettings>
        implements TokenlyArtistIdentityManagerModule,Serializable {

    private final TokenlyArtistIdentityManager tokenlyArtistIdentityManager;
    private final TokenlyApiManager tokenlyApiManager;

    /**
     * Default constructor with parameters.
     * @param tokenlyArtistIdentityManager
     * @param tokenlyApiManager
     */
    public ArtistIdentityManager(TokenlyArtistIdentityManager tokenlyArtistIdentityManager,
                                 TokenlyApiManager tokenlyApiManager,
                                 PluginFileSystem pluginFileSystem,
                                 UUID pluginId) {
        super(pluginFileSystem, pluginId);
        this.tokenlyArtistIdentityManager = tokenlyArtistIdentityManager;
        this.tokenlyApiManager = tokenlyApiManager;
    }

    @Override
    public ArtistIdentitiesList listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        List<Artist> artistList = tokenlyArtistIdentityManager.listIdentitiesFromCurrentDeviceUser();
        ArtistIdentitiesList artistIdentitiesList = new ArtistIdentitiesListRecord(artistList);
        return artistIdentitiesList;
    }

    @Override
    public Artist createArtistIdentity(
            String username,
            byte[] profileImage,
            String password,
            ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel,
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException, WrongTokenlyUserCredentialsException {
        return tokenlyArtistIdentityManager.createArtistIdentity(
                username,
                profileImage,
                password,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
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
            ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException, WrongTokenlyUserCredentialsException {
        return tokenlyArtistIdentityManager.updateArtistIdentity(
                username,
                password,
                id,
                publicKey,
                profileImage,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    @Override
    public Artist getArtistIdentity(UUID publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return tokenlyArtistIdentityManager.getArtistIdentity(publicKey);
    }

    /**
     * This method checks if the Tokenly Music API is available.
     * @return
     * @throws TokenlyAPINotAvailableException
     */
    @Override
    public TokenlyAPIStatus getMusicAPIStatus() throws TokenlyAPINotAvailableException {
        return tokenlyApiManager.getMusicAPIStatus();
    }

    /*@Override
    public SettingsManager<TokenlyArtistPreferenceSettings> getSettingsManager() {
        return null;
    }*/

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity()
            throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try{
            List<Artist> artistList = tokenlyArtistIdentityManager.listIdentitiesFromCurrentDeviceUser();
            ActiveActorIdentityInformation activeActorIdentityInformation;
            Artist artist;
            if(artistList!=null||!artistList.isEmpty()){
                artist = artistList.get(0);
                activeActorIdentityInformation = new ActiveActorIdentityInformationRecord(artist);
                return activeActorIdentityInformation;
            } else {
                //If there's no Identity created, in this version, I'll return an empty activeActorIdentityInformation
                activeActorIdentityInformation = new ActiveActorIdentityInformationRecord(null);
                return activeActorIdentityInformation;
            }
        } catch (CantListArtistIdentitiesException e) {
            throw new CantGetSelectedActorIdentityException(
                    e,
                    "Getting the ActiveActorIdentityInformation",
                    "Cannot get the selected identity");
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
