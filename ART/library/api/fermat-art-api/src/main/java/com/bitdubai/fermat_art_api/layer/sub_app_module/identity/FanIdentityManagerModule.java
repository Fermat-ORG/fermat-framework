package com.bitdubai.fermat_art_api.layer.sub_app_module.identity;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fan;

import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/23/16.
 */
public interface FanIdentityManagerModule extends FermatManager {
    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     * @throws CantListFanIdentitiesException
     */
    List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    /**
     * Through the method <code>createArtistIdentity</code> you can create a new artist identity.
     * @param alias
     * @param imageBytes
     * @return
     * @throws
     */
    Fan createArtistIdentity(
            final String alias,
            final byte[] imageBytes) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException;
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
            String external) throws
            CantUpdateFanIdentityException;

    /**
     * This method returns a Fan identity
     * @param publicKey
     * @return
     * @throws CantGetFanIdentityException
     * @throws IdentityNotFoundException
     */
    Fan getFanIdentity(String publicKey) throws
            CantGetFanIdentityException,
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
}
