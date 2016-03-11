package com.bitdubai.fermat_art_api.layer.actor_network_service.util;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtistActor;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;


/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 11/03/16.
 */
public class ArtistActorImp extends ArtistActorNetworkServiceRecord implements ArtistActor {

    /**
     * Constructor for Artist Actor interfaces implementation.
     * @param artist
     */
    public ArtistActorImp(Artist artist){
       super(artist.getAlias(),artist.getPublicKey(),artist.getProfileImage(),artist.getExternalUsername(),artist.getExternalAccesToken(),artist.getExternalPlatform());
    }
}
