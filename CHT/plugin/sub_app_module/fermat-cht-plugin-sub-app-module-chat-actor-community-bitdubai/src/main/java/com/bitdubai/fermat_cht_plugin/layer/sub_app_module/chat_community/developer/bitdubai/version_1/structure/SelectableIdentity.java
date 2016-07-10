package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;


/**
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 23/06/16.
 */

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

    @Override
    public GeoFrequency getFrequency() {
        return GeoFrequency.NONE;
    }

    @Override
    public String getConnectionState() {
        return "";
    }

    public String getCountry() {
        return "";
    }

    public String getState() {
        return "";
    }

    public String getCity() {
        return "";
    }

    public long getAccuracy() {
        return 0;
    }

    public String getStatus() {
        return "";
    }

}

