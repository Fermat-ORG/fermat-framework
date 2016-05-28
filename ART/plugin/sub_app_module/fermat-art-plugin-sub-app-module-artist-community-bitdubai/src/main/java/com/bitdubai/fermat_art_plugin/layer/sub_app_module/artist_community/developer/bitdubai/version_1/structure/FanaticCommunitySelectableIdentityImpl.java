package com.bitdubai.fermat_art_plugin.layer.sub_app_module.artist_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;

import java.io.Serializable;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class FanaticCommunitySelectableIdentityImpl implements FanCommunitySelectableIdentity, Serializable {

    String publicKey;
    Actors actorType;
    String alias;
    byte[] image;

    FanaticCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image) {
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
    }
    @Override
    public void select() throws CantSelectIdentityException {

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
