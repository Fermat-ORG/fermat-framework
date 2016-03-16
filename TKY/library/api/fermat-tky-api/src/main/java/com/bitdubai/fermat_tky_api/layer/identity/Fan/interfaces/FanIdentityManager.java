package com.bitdubai.fermat_tky_api.layer.identity.Fan.interfaces;

import java.util.List;

/**
 * Created by alexander on 3/15/16.
 */
public interface FanIdentityManager {
    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     */
    List<Fan> listIdentitiesFromCurrentDeviceUser();

    /**
     * Through the method <code>createArtistIdentity</code> you can create a new artist identity.
     * @param alias
     * @param imageBytes
     * @return
     * @throws
     */
    Fan createArtistIdentity(
            final String alias,
            final byte[] imageBytes);
    /**
     * This method updates the fan identity
     * @param alias
     * @param publicKey
     * @param imageProfile
     */
    void updateFanIdentity(
            String alias,
            String publicKey,
            byte[] imageProfile,
            String external) ;

    /**
     * This method returns a Fan identity
     * @param publicKey
     * @return
     */
    Fan getFanIdentity(String publicKey);

    /**
     * The method <code>publishIdentity</code> is used to publish a Artist identity.
     * @param publicKey
     *

     */
    void publishIdentity(String publicKey);


}
