package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces.BrokerIdentityWalletRelationship;

import java.util.UUID;

/**
 * Created by angel on 19/11/15.
 */
public class BrokerIdentityWalletRelationshipInformation implements BrokerIdentityWalletRelationship {

    // TODO: Cambiar los numeros primos
    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final UUID relationshipId;
    private final ActorIdentity identity;
    private final UUID wallet;

    public BrokerIdentityWalletRelationshipInformation(final UUID relationshipId, final ActorIdentity identity, final UUID Wallet){
        this.identity = identity;
        this.wallet = Wallet;
        this.relationshipId = relationshipId;
    }

    @Override
    public UUID getRelationshipId() {
        return null;
    }

    @Override
    public ActorIdentity getCryptoBroker() {
        return null;
    }

    @Override
    public UUID getWallet() {
        return null;
    }

    @Override
    public boolean equals(final Object o){
        if(!(o instanceof BrokerIdentityWalletRelationship))
            return false;

        BrokerIdentityWalletRelationship compare = (BrokerIdentityWalletRelationship) o;

        if( !this.identity.equals(compare.getCryptoBroker()) )
            return false;
        if( !this.wallet.equals(compare.getWallet()) )
            return false;
        if( !this.relationshipId.equals(compare.getRelationshipId()) )
            return false;

        return true;
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += identity.hashCode();
        c += wallet.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
