package com.bitdubai.fermat_art_api.layer.sub_app_module.community;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/05/16.
 */
public abstract class AbstractArtCommunityInformation implements ArtCommunityInformation{

    public String publicKey;
    public String alias    ;
    public byte[] image    ;
    public ConnectionState connectionState;
    public UUID connectionId;
    public Actors actorType;
    public ArtExternalPlatform artExternalPlatform;

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }

    @Override
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    @Override
    public UUID getConnectionId() {
        return this.connectionId;
    }

    /**
     * This method return the actor type.
     * @return
     */
    @Override
    public Actors getActorType() {
        return this.actorType;
    }

    /**
     * This method sets the Actor type.
     * @param actorType
     */
    public void setActorType(Actors actorType){
        this.actorType = actorType;
    }

    @Override
    public ArtExternalPlatform getArtExternalPlatform() {
        if(this.artExternalPlatform==null){
            return ArtExternalPlatform.UNDEFINED;
        }
        return this.artExternalPlatform;
    }

    /**
     * This method sets the Art External Platform.
     */
    public void setArtExternalPlatform(ArtExternalPlatform artExternalPlatform){
        this.artExternalPlatform = artExternalPlatform;
    }

}
