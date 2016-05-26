package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/04/16.
 */
public interface ArtActorInfo {

    HashMap<ArtExternalPlatform,String> getExternalPlatformInformationMap();

}
