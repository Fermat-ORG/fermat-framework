package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistPreferenceSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class ArtistIdentityManager implements TokenlyArtistIdentityManagerModule,Serializable {

    private final ErrorManager errorManager;
    private final TokenlyArtistIdentityManager tokenlyArtistIdentityManager;

    public ArtistIdentityManager(ErrorManager errorManager,
                                 TokenlyArtistIdentityManager tokenlyArtistIdentityManager) {
        this.errorManager = errorManager;
        this.tokenlyArtistIdentityManager = tokenlyArtistIdentityManager;
    }

    @Override
    public List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException {
        return tokenlyArtistIdentityManager.listIdentitiesFromCurrentDeviceUser();
    }

    @Override
    public Artist createArtistIdentity(String username, byte[] profileImage,String password, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {
        return tokenlyArtistIdentityManager.createArtistIdentity(
                username,
                profileImage,
                password,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    @Override
    public void updateArtistIdentity(String username,String password, UUID id,String publicKey, byte[] profileImage, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException {
        tokenlyArtistIdentityManager.updateArtistIdentity(
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

    @Override
    public SettingsManager<TokenlyArtistPreferenceSettings> getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
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
