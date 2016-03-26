package com.bitdubai.fermat_art_api.layer.sub_app_module.identity;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.enums.ExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;

import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public interface ArtistIdentityManagerModule extends FermatManager {

    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     * @throws CantListArtistIdentitiesException
     */
    List<Artist> listIdentitiesFromCurrentDeviceUser() throws CantListArtistIdentitiesException;

    /**
     * Through the method <code>createArtistIdentity</code> you can create a new artist identity.
     * @param alias
     * @param imageBytes
     * @return
     * @throws
     */
    Artist createArtistIdentity(
            final String alias,
            final byte[] imageBytes) throws
            CantCreateArtistIdentityException,
            ArtistIdentityAlreadyExistsException;

    /**
     *
     * @param alias
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
            String alias,String publicKey, byte[] profileImage,
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
    Artist getArtistIdentity(String publicKey) throws
            CantGetArtistIdentityException,
            IdentityNotFoundException;

    /**
     * The method <code>publishIdentity</code> is used to publish a Artist identity.
     * @param publicKey
     *
     * @throws CantPublishIdentityException
     * @throws IdentityNotFoundException
     */
    void publishIdentity(String publicKey) throws
            CantPublishIdentityException,
            IdentityNotFoundException;

    /**
     * The method <code>hideIdentity</code> is used to publish a Artist identity
     * @param publicKey
     *
     * @throws CantHideIdentityException
     * @throws IdentityNotFoundException
     */
    void hideIdentity(String publicKey) throws
            CantHideIdentityException,
            IdentityNotFoundException;
}
