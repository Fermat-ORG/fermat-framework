package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.TokenlyArtistIdentityManager;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces.TokenlyArtistIdentityManagerModule;

import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class ArtistIdentityManager implements TokenlyArtistIdentityManagerModule {

    private final ErrorManager errorManager;
    private final TokenlyArtistIdentityManager tokenlyArtistIdentityManager;

    public ArtistIdentityManager(ErrorManager errorManager,
                                 TokenlyArtistIdentityManager tokenlyArtistIdentityManager) {
        this.errorManager = errorManager;
        this.tokenlyArtistIdentityManager = tokenlyArtistIdentityManager;
    }

    @Override
    public Artist createArtistIdentity(String alias, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantCreateArtistIdentityException, ArtistIdentityAlreadyExistsException {
        return tokenlyArtistIdentityManager.createArtistIdentity(
                alias,
                profileImage,
                externalUserName,
                externalAccessToken,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    @Override
    public void updateArtistIdentity(String alias, UUID id, String publicKey, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform, ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws CantUpdateArtistIdentityException {
        tokenlyArtistIdentityManager.updateArtistIdentity(
                alias,
                id,
                publicKey,
                profileImage,
                externalUserName,
                externalAccessToken,
                externalPlatform,
                exposureLevel,
                artistAcceptConnectionsType);
    }

    @Override
    public Artist getArtistIdentity(UUID publicKey) throws CantGetArtistIdentityException, IdentityNotFoundException {
        return tokenlyArtistIdentityManager.getArtistIdentity(publicKey);
    }
}
