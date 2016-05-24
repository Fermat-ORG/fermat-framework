package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/05/16.
 */
public class ActiveActorIdentityInformationRecord implements ActiveActorIdentityInformation, Serializable {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;

    public ActiveActorIdentityInformationRecord(Artist artist){
        if(artist!=null){
            this.publicKey = artist.getPublicKey();
            this.actorType = Actors.ART_ARTIST;
            this.alias = artist.getAlias();
            this.image = artist.getProfileImage();
        } else {
            this.publicKey = null;
            this.actorType = Actors.ART_ARTIST;
            this.alias = null;
            this.image = null;
        }
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public Actors getActorType() {
        return this.actorType;
    }

    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }
}

