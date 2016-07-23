package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;

import java.util.UUID;

/**
 * Created by angel on 19/11/15.
 */

public class BrokerIdentityWalletRelationshipInformation implements BrokerIdentityWalletRelationship {

    // TODO: Cambiar los numeros primos
    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final UUID relationshipId;
    private final String identity;
    private final String wallet;

    public BrokerIdentityWalletRelationshipInformation(final UUID relationshipId, final String identity, final String Wallet) {
        this.identity = identity;
        this.wallet = Wallet;
        this.relationshipId = relationshipId;
    }

    @Override
    public UUID getRelationshipId() {
        return this.relationshipId;
    }

    @Override
    public String getCryptoBroker() {
        return this.identity;
    }

    @Override
    public String getWallet() {
        return this.wallet;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BrokerIdentityWalletRelationship))
            return false;

        BrokerIdentityWalletRelationship compare = (BrokerIdentityWalletRelationship) o;

        if (!this.identity.equals(compare.getCryptoBroker()))
            return false;
        if (!this.wallet.equals(compare.getWallet()))
            return false;
        return this.relationshipId.equals(compare.getRelationshipId());

    }

    @Override
    public int hashCode() {
        int c = 0;
        c += identity.hashCode();
        c += wallet.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
