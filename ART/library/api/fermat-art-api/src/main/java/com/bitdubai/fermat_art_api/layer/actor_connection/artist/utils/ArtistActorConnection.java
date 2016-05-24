package com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;
import com.bitdubai.fermat_art_api.all_definition.enums.ArtExternalPlatform;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public final class ArtistActorConnection
        extends ActorConnection<ArtistLinkedActorIdentity>
        implements Serializable {

    private ArtExternalPlatform artExternalPlatform;

    /**
     * Constructor with parameters
     * @param connectionId
     * @param linkedIdentity
     * @param publicKey
     * @param alias
     * @param image
     * @param connectionState
     * @param creationTime
     * @param updateTime
     */
    public ArtistActorConnection(
           final UUID connectionId   ,
           final ArtistLinkedActorIdentity linkedIdentity ,
           final String                    publicKey      ,
           final String                    alias          ,
           final byte[]                    image          ,
           final ConnectionState           connectionState,
           final long                      creationTime   ,
           final long                      updateTime     ) {

        super(
                connectionId   ,
                linkedIdentity ,
                publicKey      ,
                alias          ,
                image          ,
                connectionState,
                creationTime   ,
                updateTime
        );
    }

    /**
     * This method sets the ArtExternalPlatform.
     * @param artExternalPlatform
     */
    public void setArtExternalPlatform(ArtExternalPlatform artExternalPlatform){
        this.artExternalPlatform = artExternalPlatform;
    }

    /**
     * This method returns the ArtExternalPlatform
     * @return
     */
    public ArtExternalPlatform getArtExternalPlatform() {
        if(this.artExternalPlatform==null){
            return ArtExternalPlatform.UNDEFINED;
        }
        return this.artExternalPlatform;
    }

}
