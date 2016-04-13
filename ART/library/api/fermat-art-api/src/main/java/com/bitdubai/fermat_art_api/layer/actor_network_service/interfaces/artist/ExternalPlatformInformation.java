package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public interface ExternalPlatformInformation {

    /**
     * This method returns the hash map with the linked external platform information.
     * @return
     */
    HashMap<ArtExternalPlatform,String> getLinkedExternalPlatformInformation();
}
