package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;
import com.bitdubai.fermat_art_api.layer.identity.artist.interfaces.Artist;

import java.io.Serializable;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/5/16.
 */
public class FanCommunitySelectableIdentityImpl implements FanCommunitySelectableIdentity, Serializable {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;

    FanCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image){
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
    }

    FanCommunitySelectableIdentityImpl(final Artist artistIdentity) {

        this.alias     = artistIdentity.getAlias()       ;
        this.publicKey = artistIdentity.getPublicKey()   ;
        this.actorType = Actors.ART_ARTIST        ;
        this.image     = artistIdentity.getProfileImage();
    }

    FanCommunitySelectableIdentityImpl(final Fanatic fanIdentity) {

        this.alias     = fanIdentity.getAlias()       ;
        this.publicKey = fanIdentity.getPublicKey()   ;
        this.actorType = Actors.ART_FAN   ;
        this.image     = fanIdentity.getProfileImage();
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
