package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 23/06/16.
 */

public class ChatActorCommunitySelectableIdentityImpl implements ChatActorCommunitySelectableIdentity {

    public final String publicKey;
    public final Actors actorType;
    public final String alias;
    public final byte[] image;
    public final String status;
    private String country;
    private String state;
    private String city;
    private String connectionState;
    private long accuracy;
    private GeoFrequency frequency;

    ChatActorCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias,
                                             byte[] image, String status, String country,
                                             String state, String city, String connectionState,
                                             long accuracy, GeoFrequency frequency) {
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
        this.status = status;
        this.country = country;
        this.state = state;
        this.city = city;
        this.connectionState = connectionState;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    ChatActorCommunitySelectableIdentityImpl(final ChatIdentity chatIdentity) {

        this.alias = chatIdentity.getAlias();
        this.publicKey = chatIdentity.getPublicKey();
        this.actorType = Actors.CHAT;
        this.image = chatIdentity.getImage();
        this.status = chatIdentity.getConnectionState();
        this.country = chatIdentity.getCountry();
        this.state = chatIdentity.getState();
        this.city = chatIdentity.getCity();
        this.connectionState = chatIdentity.getConnectionState();
        this.accuracy = chatIdentity.getAccuracy();
        this.frequency = chatIdentity.getFrecuency();
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

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getConnectionState() {
        return connectionState;
    }

    public long getAccuracy() {
        return accuracy;
    }


    public GeoFrequency getFrequency() {
        return frequency;
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
                .append(", country='").append(country)
                .append('\'')
                .append(", state='").append(state)
                .append('\'')
                .append(", city='").append(city)
                .append('\'')
                .append(", connectionState='").append(connectionState)
                .append('\'')
                .append(", accuracy='").append(accuracy)
                .append('\'')
                .append(", frequency='").append(frequency != null)
                .append('}').toString();
    }
}
