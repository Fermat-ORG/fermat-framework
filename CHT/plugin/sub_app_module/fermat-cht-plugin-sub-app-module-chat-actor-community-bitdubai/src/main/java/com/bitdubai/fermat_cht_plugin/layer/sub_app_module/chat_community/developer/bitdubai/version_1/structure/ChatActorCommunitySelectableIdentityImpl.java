package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public class ChatActorCommunitySelectableIdentityImpl implements ChatActorCommunitySelectableIdentity {

    public final String publicKey;
    public final Actors actorType;
    public final String alias;
    public final byte[] image;
    public final String status;

    ChatActorCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image, String status){
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
        this.status= status;
    }

    ChatActorCommunitySelectableIdentityImpl(final ChatIdentity chatIdentity) {

        this.alias     = chatIdentity.getAlias()       ;
        this.publicKey = chatIdentity.getPublicKey()   ;
        this.actorType = Actors.CHAT         ;
        this.image     = chatIdentity.getImage();
        this.status    = chatIdentity.getConnectionState();
    }


    @Override
    public void select() throws CantGetSelectedActorException {}

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
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

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ChatActorCommunitySelectableIdentityImpl{" +
                "publicKey='" + publicKey + '\'' +
                ", actorType=" + actorType +
                ", alias='" + alias + '\'' +
                ", image=" + (image != null) +
                '}';
    }
}
