package com.bitdubai.fermat_art_api.layer.actor_network_service.util;

import com.bitdubai.fermat_art_api.all_definition.enums.ARTConnectionState;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;


/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 11/03/16.
 */
public class ArtistActorImp extends ArtistActorNetworkServiceRecord {

    private ARTConnectionState artConnectionState;

    /**
     * Constructor for Artist Actor interfaces implementation.
     * @param artist
     */
    public ArtistActorImp(Artist artist){
       super(artist);

    }

    public ArtistActorImp(String publicKey, String alias, byte[] imageProfile){
        super(alias,publicKey,imageProfile);
    }

    public ARTConnectionState getArtConnectionState() {
        return artConnectionState;
    }

    public void setArtConnectionState(ARTConnectionState artConnectionState) {
        this.artConnectionState = artConnectionState;
    }

}
