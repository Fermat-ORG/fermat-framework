package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;

public final class SelectableIdentity implements ChatActorCommunitySelectableIdentity {

    private final String alias;
    private final String publicKey;

    private final byte[] image;

    public SelectableIdentity(final String alias,
                              final String publicKey,

                              final byte[] image) {

        this.alias = alias;
        this.publicKey = publicKey;

        this.image = image;
    }

    public SelectableIdentity(final ChatActorCommunitySelectableIdentity chatActorIdentity) {

        this.alias = chatActorIdentity.getAlias();
        this.publicKey = chatActorIdentity.getPublicKey();

        this.image = chatActorIdentity.getProfileImage();
    }


    @Override
    public final String getAlias() {
        return alias;
    }

    @Override
    public final String getPublicKey() {
        return publicKey;
    }

    @Override
    public Actors getActorType() {
        return null;
    }

    @Override
    public final byte[] getImage() {
        return image;
    }


    @Override
    public String toString() {
        return "SelectableIdentity{" +
                "alias='" + alias + '\'' +
                ", publicKey='" + publicKey + '\'' +

                ", image=" + (image != null) +
                '}';
    }

    @Override
    public void select() throws CantGetSelectedActorException {
        // TODO implement
    }

    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }
}

