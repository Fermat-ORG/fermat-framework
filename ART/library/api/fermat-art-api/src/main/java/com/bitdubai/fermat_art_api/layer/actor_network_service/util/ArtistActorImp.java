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
        setActorSenderAlias(artist.getAlias());
        setActorSenderPublicKey(artist.getPublicKey());
        setNewProfileImage(artist.getProfileImage());
        setActorSenderExternalUserName(artist.getExternalUsername());
        setActorSenderExternalAccessToken(artist.getExternalAccesToken());
        setActorSenderExternalPlataform(artist.getExternalPlatform());
    }
}
