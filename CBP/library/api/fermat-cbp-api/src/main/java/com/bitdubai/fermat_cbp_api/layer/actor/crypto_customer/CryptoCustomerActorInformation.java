package com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_customer.interfaces.CryptoCustomerActor;

/**
 * Created by Angel on 31/01/2016.
 */

public class CryptoCustomerActorInformation implements CryptoCustomerActor {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1117;
    private static final int HASH_PRIME_NUMBER_ADD = 3001;

    private final ActorIdentity identity;

    public CryptoCustomerActorInformation(final ActorIdentity identity) {
        this.identity = identity;
    }

    @Override
    public ActorIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof CryptoCustomerActor))
            return false;
        CryptoCustomerActor compare = (CryptoCustomerActor) o;
        return this.identity.equals(compare.getIdentity());
    }

    @Override
    public int hashCode() {
        int c = 0;
        c += identity.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
