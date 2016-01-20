package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;

import java.util.Arrays;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.structure.SelectableIdentity</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public final class SelectableIdentity implements CryptoBrokerCommunitySelectableIdentity {

    private final String alias    ;
    private final String publicKey;
    private final Actors actorType;
    private final byte[] image    ;

    public SelectableIdentity(final String alias    ,
                              final String publicKey,
                              final Actors actorType,
                              final byte[] image    ) {

        this.alias     = alias    ;
        this.publicKey = publicKey;
        this.actorType = actorType;
        this.image     = image    ;
    }

    public SelectableIdentity(final CryptoBrokerIdentity cryptoBrokerIdentity) {

        this.alias     = cryptoBrokerIdentity.getAlias()       ;
        this.publicKey = cryptoBrokerIdentity.getPublicKey()   ;
        this.actorType = Actors.CBP_CRYPTO_BROKER              ;
        this.image     = cryptoBrokerIdentity.getProfileImage();
    }

    public SelectableIdentity(final CryptoCustomerIdentity cryptoCustomerIdentity) {

        this.alias     = cryptoCustomerIdentity.getAlias()       ;
        this.publicKey = cryptoCustomerIdentity.getPublicKey()   ;
        this.actorType = Actors.CBP_CRYPTO_CUSTOMER              ;
        this.image     = cryptoCustomerIdentity.getProfileImage();
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
    public final byte[] getImage() {
        return image;
    }

    @Override
    public final Actors getActorType() {
        return actorType;
    }

    @Override
    public String toString() {
        return "SelectableIdentity{" +
                "alias='" + alias + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", actorType=" + actorType +
                ", profileImage=" + Arrays.toString(image) +
                '}';
    }

    @Override
    public void select() throws CantSelectIdentityException {
        // TODO implement
    }
}
