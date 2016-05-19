package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ArtActorInfo;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public interface ArtArtistExtraData <T extends ArtActorInfo> {

    /**
     * This method returns the request id.
     * @return
     */
    UUID getRequestId();

    /**
     * This method returns the requester public key.
     * @return
     */
    String getRequesterPublicKey();

    /**
     * This method returns the requester actor type.
     * @return
     */
    PlatformComponentType getRequesterActorType();

    /**
     * This method returns the artist public key.
     * @return
     */
    String getArtistPublicKey();

    /**
     * This method returns the last update time
     * @return
     */
    long getUpdateTime();

    /**
     * This method returns the list information.
     * @return
     */
    List<T> listInformation();

}
