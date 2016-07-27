package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;


/**
 * Created by Alejandro Bicelis on 26/1/2016.
 */
public final class CryptoBrokerCommunitySelectableIdentityImpl implements CryptoBrokerCommunitySelectableIdentity {

    private final String publicKey;
    private final Actors actorType;
    private final String alias;
    private final byte[] image;
    private long accuracy;
    private GeoFrequency frequency;


    CryptoBrokerCommunitySelectableIdentityImpl(String publicKey, Actors actorType, String alias, byte[] image, long accuracy, GeoFrequency frequency) {
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias = alias;
        this.image = image;
        this.accuracy = accuracy;
        this.frequency = frequency;
    }

    CryptoBrokerCommunitySelectableIdentityImpl(final CryptoBrokerIdentity cryptoBrokerIdentity) {

        this.alias = cryptoBrokerIdentity.getAlias();
        this.publicKey = cryptoBrokerIdentity.getPublicKey();
        this.actorType = Actors.CBP_CRYPTO_BROKER;
        this.image = cryptoBrokerIdentity.getProfileImage();
        this.accuracy = cryptoBrokerIdentity.getAccuracy();
        this.frequency = cryptoBrokerIdentity.getFrequency();
    }

    CryptoBrokerCommunitySelectableIdentityImpl(final CryptoCustomerIdentity cryptoCustomerIdentity) {

        this.alias = cryptoCustomerIdentity.getAlias();
        this.publicKey = cryptoCustomerIdentity.getPublicKey();
        this.actorType = Actors.CBP_CRYPTO_CUSTOMER;
        this.image = cryptoCustomerIdentity.getProfileImage();
        this.accuracy = cryptoCustomerIdentity.getAccuracy();
        this.frequency = cryptoCustomerIdentity.getFrequency();
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

    public long getAccuracy() {
        return accuracy;
    }

    public GeoFrequency getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("CryptoBrokerCommunitySelectableIdentityImpl{")
                .append("publicKey='").append(publicKey)
                .append('\'')
                .append(", actorType=").append(actorType)
                .append(", alias='").append(alias)
                .append('\'')
                .append(", image=").append(image != null)
                .append(", accuracy=").append(accuracy)
                .append(", frequency='").append(frequency != null)
                .append('}').toString();
    }
}
