package com.bitdubai.fermat_art_plugin.layer.sub_app_module.music_player.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fanatic;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/05/16.
 */
public class ActiveActorIdentityInformationRecord implements ActiveActorIdentityInformation {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;

    public ActiveActorIdentityInformationRecord(Fanatic fanatic){
        if(fanatic!=null){
            this.publicKey = fanatic.getPublicKey();
            this.actorType = Actors.ART_FAN;
            this.alias = fanatic.getAlias();
            this.image = fanatic.getProfileImage();
        } else {
            this.publicKey = null;
            this.actorType = Actors.ART_FAN;
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
