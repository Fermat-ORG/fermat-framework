package com.bitdubai.fermat_art_api.layer.identity.fan.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantHandleNewsEventException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_art_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface FanaticIdentityManager extends FermatManager {

    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the artist
     * identities linked to the current logged device user.
     * @return
     * @throws CantListFanIdentitiesException
     */
    List<Fanatic> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    /**
     * Through the method <code>listExternalIdentitiesFromCurrentDeviceUser</code> we can get all the external artist
     * identities linked to the current logged device user.
     * @return
     * @throws CantListArtistIdentitiesException
     */
    HashMap<ArtExternalPlatform,HashMap<UUID,String>> listExternalIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    /**
     * Return an Object with the basic data from the linked identity and its respectible
     * @param publicKey
     * @return
     */
    ArtIdentity getLinkedIdentity(String publicKey);

    /**
     * Through the method <code>createFanaticIdentity</code> you can create a new Fan identity.
     * @param alias
     * @param imageBytes
     * @param externalIdentityID
     * @return
     * @throws
     */
    Fanatic createFanaticIdentity(
            final String alias,
            final byte[] imageBytes,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException;

    /**
     * This method updates the fan identity
     * @param alias
     * @param publicKey
     * @param imageProfile
     * @param externalIdentityID
     */
    void updateFanIdentity(
            String alias,
            String publicKey,
            byte[] imageProfile,
            UUID externalIdentityID,
            ArtExternalPlatform artExternalPlatform,
            String externalUsername) throws
            CantUpdateFanIdentityException;

    /**
     * This method returns a Fan identity
     * @param publicKey
     * @return
     * @throws CantGetFanIdentityException
     * @throws IdentityNotFoundException
     */
    Fanatic getFanIdentity(String publicKey) throws
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

    /**
     * This method check if any new connection to add to the Identities.
     * @throws CantHandleNewsEventException
     */
    void checkAllConnections() throws CantHandleNewsEventException;
}
