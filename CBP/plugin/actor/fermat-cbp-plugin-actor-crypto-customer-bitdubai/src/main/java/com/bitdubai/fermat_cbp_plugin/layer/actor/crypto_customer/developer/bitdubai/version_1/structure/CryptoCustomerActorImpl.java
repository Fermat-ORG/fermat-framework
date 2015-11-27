package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;

/**
 * Created by jorge on 30-10-2015.
 * Modified by Yordin Alayn 11.11.2015
 */
public class CryptoCustomerActorImpl implements CryptoCustomerActor {

    private String actorPublicKey;
    private String actorName;
    private byte[] actorPhoto;
    private String actorPrivateKey;

    public CryptoCustomerActorImpl(String actorPublicKey, String actorPrivateKey, String actorName, byte[] actorPhoto) {

        this.actorPublicKey     = actorPublicKey;
        this.actorPrivateKey    = actorPrivateKey;
        this.actorName          = actorName;
        this.actorPhoto         = actorPhoto.clone();

    }

    public CryptoCustomerActorImpl(String actorPublicKey, String actorPrivateKey, String actorName) {

        this.actorPublicKey     = actorPublicKey;
        this.actorPrivateKey    = actorPrivateKey;
        this.actorName          = actorName;
        this.actorPhoto         = new byte[0];

    }

    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }

    @Override
    public String getActorName() {
        return this.actorName;
    }

    @Override
    public Actors getActorType() {
        return Actors.CBP_CRYPTO_CUSTOMER;
    }

    @Override
    public byte[] getActorPhoto() {
        return this.actorPhoto.clone();
    }

    @Override
    public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
        try {
            return AsymmetricCryptography.createMessageSignature(message, this.actorPrivateKey);
        } catch (Exception e) {
            throw new CantSignExtraUserMessageException("Fatal Error Signed message", e, "", "");
        }
    }

    @Override
    public ActorIdentity getIdentity() { return null; }

}
