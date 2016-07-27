package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;


/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public class ChatActorCommunitySelectableIdentityImpl implements ChatActorCommunitySelectableIdentity {

    public final String publicKey;
    public final Actors actorType;
    public final String alias;
    public final byte[] image;

    ChatActorCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image) {
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
    }

    ChatActorCommunitySelectableIdentityImpl(final ChatIdentity chatIdentity) {

        this.alias = chatIdentity.getAlias();
        this.publicKey = chatIdentity.getPublicKey();
        this.actorType = Actors.CHAT;
        this.image = chatIdentity.getImage();
    }


    @Override
    public void select() throws CantGetSelectedActorException {
    }

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

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ChatActorCommunitySelectableIdentityImpl{")
                .append("publicKey='").append(publicKey)
                .append('\'')
                .append(", actorType=").append(actorType)
                .append(", alias='").append(alias)
                .append('\'')
                .append(", image=").append(image != null)
                .append('}').toString();
    }
}
