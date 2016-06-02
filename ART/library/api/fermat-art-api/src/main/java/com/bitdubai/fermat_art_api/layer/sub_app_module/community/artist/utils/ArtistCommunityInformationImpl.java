package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils.ArtistActorConnection;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.AbstractArtCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunityInformation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class ArtistCommunityInformationImpl
        extends AbstractArtCommunityInformation
        implements ArtistCommunityInformation, Serializable {

    public ArtistCommunityInformationImpl(
            final String publicKey,
            final String alias,
            final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.connectionState = null;
        this.connectionId = null;
    }

    public ArtistCommunityInformationImpl(
            final String publicKey,
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

    public ArtistCommunityInformationImpl(final ArtistActorConnection actorConnection) {

        this.publicKey = actorConnection.getPublicKey();
        this.alias     = actorConnection.getAlias()    ;
        this.image     = actorConnection.getImage()    ;
        this.connectionState = actorConnection.getConnectionState();
        this.connectionId = actorConnection.getConnectionId();
    }

    public ArtistCommunityInformationImpl(final ArtistExposingData exposingData) {

        this.publicKey = exposingData.getPublicKey();
        this.alias     = exposingData.getAlias()    ;
        this.image     = exposingData.getImage()    ;
        this.connectionState = null;
        this.connectionId = null;
        this.artExternalPlatform = getArtExternalPlatform(
                exposingData.getArtistExternalPlatformInformation());
    }

    @Override
    public List listArtistWallets() {
        return null;
    }

    /**
     * This method returns the Art External Platform.
     * @return
     */
    private ArtExternalPlatform getArtExternalPlatform(
            ArtistExternalPlatformInformation artistExternalPlatformInformation){
        HashMap<ArtExternalPlatform,String> artExternalPlatformStringHashMap =
                artistExternalPlatformInformation.getExternalPlatformInformationMap();
        //We should return the default external platform.
        if(artExternalPlatformStringHashMap.containsKey(
                ArtExternalPlatform.getDefaultExternalPlatform())){
            return ArtExternalPlatform.getDefaultExternalPlatform();
        } else{
            Set<ArtExternalPlatform> keySet = artExternalPlatformStringHashMap.keySet();
            for(ArtExternalPlatform key : keySet){
                //In this version we going to return the first platform that we find.
                return key;
            }
            return ArtExternalPlatform.UNDEFINED;
        }
    }
}
