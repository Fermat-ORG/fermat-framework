package com.bitdubai.fermat_art_api.layer.actor_connection.artist.utils;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.structure_abstract_classes.ActorConnection;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/03/16.
 */
public class ArtistActorConnection extends ActorConnection<ArtistLinkedActorIdentity> {

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
    public ArtistActorConnection(final UUID connectionId   ,
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

}
