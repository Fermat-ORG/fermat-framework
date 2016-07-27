package com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_customer_community.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.utils.CryptoCustomerActorConnection;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */

public class LinkedCryptoCustomerIdentityImpl implements LinkedCryptoCustomerIdentity {

    private final UUID connectionId;
    private final String publicKey;
    private final String alias;
    private final byte[] image;

    public LinkedCryptoCustomerIdentityImpl(final UUID connectionId,
                                            final String publicKey,
                                            final String alias,
                                            final byte[] image) {

        this.connectionId = connectionId;
        this.publicKey = publicKey;
        this.alias = alias;
        this.image = image;
    }

    public LinkedCryptoCustomerIdentityImpl(final CryptoCustomerActorConnection actorConnection) {

        this.connectionId = actorConnection.getConnectionId();
        this.publicKey = actorConnection.getPublicKey();
        this.alias = actorConnection.getAlias();
        this.image = actorConnection.getImage();
    }

    @Override
    public UUID getConnectionId() {
        return this.connectionId;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    @Override
    public List listCryptoCustomerWallets() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!LinkedCryptoCustomerIdentity.class.isAssignableFrom(obj.getClass())) return false;

        final LinkedCryptoCustomerIdentityImpl other = (LinkedCryptoCustomerIdentityImpl) obj;

        return !((this.publicKey == null) ? (other.publicKey != null) : !this.publicKey.equals(other.publicKey));

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.publicKey != null ? this.publicKey.hashCode() : 0);
        return hash;
    }
}
