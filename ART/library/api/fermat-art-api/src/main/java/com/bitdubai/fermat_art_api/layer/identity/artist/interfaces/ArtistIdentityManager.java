package com.bitdubai.fermat_art_api.layer.identity.artist.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHideIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface ArtistIdentityManager extends FermatManager{

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
     * @param imageProfile
     */
    void updateCryptoBrokerIdentity(
            String alias,
            String publicKey,
            byte[] imageProfile,
            String external) throws
            CantUpdateArtistIdentityException;

    /**
     *
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
