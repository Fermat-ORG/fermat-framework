package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;


/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class CryptoCustomerCommunitySelectableIdentityImpl implements CryptoCustomerCommunitySelectableIdentity {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;
    private GeoFrequency frequency;
    private long accuracy;

    CryptoCustomerCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image, GeoFrequency frequency, long accuracy) {
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
        this.frequency = frequency;
        this.accuracy = accuracy;
    }

    CryptoCustomerCommunitySelectableIdentityImpl(final CryptoBrokerIdentity cryptoBrokerIdentity) {

        this.alias = cryptoBrokerIdentity.getAlias();
        this.publicKey = cryptoBrokerIdentity.getPublicKey();
        this.actorType = Actors.CBP_CRYPTO_BROKER;
        this.image = cryptoBrokerIdentity.getProfileImage();
        this.frequency = cryptoBrokerIdentity.getFrequency();
        this.accuracy = cryptoBrokerIdentity.getAccuracy();
    }

    @Override
    public void select() throws CantSelectIdentityException {
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public GeoFrequency getFrequency() {
        return frequency;
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
                .append("CryptoCustomerCommunitySelectableIdentityImpl{")
                .append("publicKey='").append(publicKey)
                .append('\'')
                .append(", actorType=").append(actorType)
                .append(", alias='").append(alias)
                .append('\'')
                .append(", image=").append(image != null)
                .append('}').toString();
    }
}
