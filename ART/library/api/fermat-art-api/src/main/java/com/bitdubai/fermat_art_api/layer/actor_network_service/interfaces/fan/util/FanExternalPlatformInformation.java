package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ArtActorInfo;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ExternalPlatformInformation;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 28/04/16.
 */
public class FanExternalPlatformInformation implements ArtActorInfo {

    private final HashMap<ArtExternalPlatform,String> externalPlatformInformationMap;

    /**
     * Constructor with parameters
     * @param externalPlatformInformationMap
     */
    public FanExternalPlatformInformation(
            HashMap<ArtExternalPlatform, String> externalPlatformInformationMap) {
        this.externalPlatformInformationMap = externalPlatformInformationMap;
    }

    /**
     * Constructor with parameters
     * @param externalPlatformInformation
     */
    public FanExternalPlatformInformation(
            ExternalPlatformInformation externalPlatformInformation){
        this.externalPlatformInformationMap = externalPlatformInformation.
                getLinkedExternalPlatformInformation();
    }

    /**
     * This method returns the hash map with the linked external platform information.
     * @return
     */
    public HashMap<ArtExternalPlatform,String> getExternalPlatformInformationMap(){
        return this.externalPlatformInformationMap;
    }

}
