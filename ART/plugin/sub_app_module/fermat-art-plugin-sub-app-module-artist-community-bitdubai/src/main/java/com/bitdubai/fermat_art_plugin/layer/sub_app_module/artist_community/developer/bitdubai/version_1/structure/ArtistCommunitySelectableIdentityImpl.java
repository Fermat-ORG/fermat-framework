package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.interfaces.ArtistCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;

import java.io.Serializable;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public final class ArtistCommunitySelectableIdentityImpl implements
        ArtistCommunitySelectableIdentity,
        Serializable {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;

    ArtistCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image){
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
    }

    ArtistCommunitySelectableIdentityImpl(final Artist ArtistIdentity) {

        this.alias     = ArtistIdentity.getAlias()       ;
        this.publicKey = ArtistIdentity.getPublicKey()   ;
        this.actorType = Actors.ART_ARTIST              ;
        this.image     = ArtistIdentity.getProfileImage();
    }

    ArtistCommunitySelectableIdentityImpl(final Fanatic FanaticIdentity) {

        this.alias     = FanaticIdentity.getAlias()       ;
        this.publicKey = FanaticIdentity.getPublicKey()   ;
        this.actorType = Actors.ART_FAN              ;
        this.image     = FanaticIdentity.getProfileImage();
    }


    @Override
    public void select() throws CantSelectIdentityException {

    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getImage() {
        return image;
    }
}
