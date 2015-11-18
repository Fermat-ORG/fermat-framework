package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CustomerIdentityWalletRelationship;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

/**
 * Created by Yordin Alayn on 16.11.15.
 */
public class CryptoCustomerIdentityWalletRelationshipImpl implements CustomerIdentityWalletRelationship{

    //TODO: CAMBIAR NUMEROS PRIMOS
    private static final int HASH_PRIME_NUMBER_PRODUCT = 3061;
    private static final int HASH_PRIME_NUMBER_ADD = 7213;

    private String walletPublicKey;
    private String identityPublicKey;
    public CryptoCustomerIdentityWalletRelationshipImpl(String walletPublicKey, String identityPublicKey){
        this.walletPublicKey = walletPublicKey;
        this.identityPublicKey = identityPublicKey;
    }

    public String getWalletPublicKey(){ return this.walletPublicKey; }

    public String getIdentityPublicKey(){ return this.identityPublicKey; }

    public boolean equals(Object o){
        if(!(o instanceof CustomerIdentityWalletRelationship))
            return false;
        CustomerIdentityWalletRelationship compare = (CustomerIdentityWalletRelationship) o;
        return walletPublicKey.equals(compare.getWalletPublicKey()) && identityPublicKey.equals(compare.getIdentityPublicKey());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += walletPublicKey.hashCode();
        c += identityPublicKey.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}