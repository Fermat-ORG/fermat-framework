package com.bitdubai.fermat_tky_api.layer.sub_app_module.artist.interfaces;

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

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/17/16.
 */
public interface TokenlyArtistIdentityManagerModule {



    /**
     *
     * @param alias
     * @param profileImage
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @return
     * @throws CantCreateArtistIdentityException
     * @throws ArtistIdentityAlreadyExistsException
     */
    Artist createArtistIdentity(
            String alias, byte[] profileImage,
            String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantCreateArtistIdentityException,
            ArtistIdentityAlreadyExistsException;

    /**
     *
     * @param alias
     * @param id
     * @param publicKey
     * @param profileImage
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @param exposureLevel
     * @param artistAcceptConnectionsType
     * @throws CantUpdateArtistIdentityException
     */
    void updateArtistIdentity(
            String alias, UUID id,String publicKey, byte[] profileImage,
            String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform,
            ExposureLevel exposureLevel, ArtistAcceptConnectionsType artistAcceptConnectionsType) throws
            CantUpdateArtistIdentityException;

    /**
     * This method returns a Artist identity
     * @param publicKey
     * @return
     * @throws CantGetArtistIdentityException
     * @throws IdentityNotFoundException
     */
    Artist getArtistIdentity(UUID publicKey) throws
            CantGetArtistIdentityException,
            IdentityNotFoundException;
}
