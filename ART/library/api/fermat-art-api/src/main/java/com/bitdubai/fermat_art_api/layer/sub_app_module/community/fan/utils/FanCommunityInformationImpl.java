package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.AbstractArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.actor_connection.fan.utils.FanActorConnection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/5/16.
 */
public class FanCommunityInformationImpl
        extends AbstractArtCommunityInformation
        implements FanCommunityInformation, Serializable {

    public FanCommunityInformationImpl(final String publicKey,
                                                          final String alias,
                                                          final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.connectionState = null;
        this.connectionId = null;
    }

    public FanCommunityInformationImpl(final String publicKey,
                                                          final String alias,
                                                          final byte[] image,
                                                          final ConnectionState connectionState,
                                                          final UUID connectionId) {

        this.publicKey          = publicKey      ;
        this.alias              = alias          ;
        this.image              = image          ;
        this.connectionState    = connectionState;
        this.connectionId       = connectionId   ;
    }

    public FanCommunityInformationImpl(final FanActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();

    }

    public FanCommunityInformationImpl(final FanExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = null;
        this.connectionId = null;
        this.artExternalPlatform = getArtExternalPlatform(
                exposingData.getFanExternalPlatformInformation());
    }

    /**
     * This method returns the Art External Platform.
     * @return
     */
    public ArtExternalPlatform getArtExternalPlatform(
            FanExternalPlatformInformation fanExternalPlatformInformation){
        HashMap<ArtExternalPlatform,String> fanExternalPlatformStringHashMap =
                fanExternalPlatformInformation.getExternalPlatformInformationMap();
        //We should return the default external platform.
        if(fanExternalPlatformStringHashMap.containsKey(
                ArtExternalPlatform.getDefaultExternalPlatform())){
            return ArtExternalPlatform.getDefaultExternalPlatform();
        } else{
            Set<ArtExternalPlatform> keySet = fanExternalPlatformStringHashMap.keySet();
            for(ArtExternalPlatform key : keySet){
                //In this version we going to return the first platform that we find.
                return key;
            }
            return ArtExternalPlatform.UNDEFINED;
        }
    }

}
