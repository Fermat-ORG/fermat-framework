package com.bitdubai.fermat_tky_api.layer.identity.Artist.interfaces;

import java.util.List;

/**
 * Created by alexander on 3/15/16.
 */
public interface ArtistIdentityManager {
    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     */
    List<Artist> listIdentitiesFromCurrentDeviceUser();

    /**
     * Through the method <code>createArtistIdentity</code> you can create a new artist identity.
     * @param alias
     * @param imageBytes
     * @return
     * @throws
     */
    Artist createArtistIdentity(
            final String alias,
            final byte[] imageBytes) ;

    /**
     * This method returns a Artist identity
     * @param publicKey
     * @return

     */
    Artist getArtistIdentity(String publicKey) ;

    /**
     * The method <code>publishIdentity</code> is used to publish a Artist identity.
     * @param publicKey
     *
     */
    void publishIdentity(String publicKey) ;

    /**
     * The method <code>hideIdentity</code> is used to publish a Artist identity
     * @param publicKey
     *
     */
    void hideIdentity(String publicKey);
}
