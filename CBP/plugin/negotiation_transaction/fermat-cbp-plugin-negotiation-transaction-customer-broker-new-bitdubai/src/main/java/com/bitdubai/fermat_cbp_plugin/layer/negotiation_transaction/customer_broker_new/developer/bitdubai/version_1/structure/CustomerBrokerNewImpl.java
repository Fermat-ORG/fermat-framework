package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_new.interfaces.CustomerBrokerNew;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;

import java.util.UUID;

/**
 * Created by yordin on 23/11/15.
 */
public class CustomerBrokerNewImpl implements CustomerBrokerNew{

    private static final int HASH_PRIME_NUMBER_PRODUCT = 4259;
    private static final int HASH_PRIME_NUMBER_ADD = 3089;

    private UUID transactionId;
    private UUID negotiationId;
    private String publicKeyBroker;
    private String publicKeyCustomer;
    private NegotiationStatus status;
    private long timestamp;

    public CustomerBrokerNewImpl(UUID transactionId, UUID negotiationId, String publicKeyBroker, String publicKeyCustomer, NegotiationStatus status, long timestamp){
        this.transactionId = transactionId;
        this.negotiationId = negotiationId;
        this.publicKeyBroker = publicKeyBroker;
        this.publicKeyCustomer = publicKeyCustomer;
        this.status = status;
        this.timestamp = timestamp;
    }

    public UUID getTransactionId(){ return this.transactionId; }

    public UUID getNegotiationId(){ return this.negotiationId; }

    public String getPublicKeyBroker(){ return this.publicKeyBroker; }

    public String getPublicKeyCustomer(){ return this.publicKeyCustomer; }

    public NegotiationStatus getStatusTransaction(){ return this.status; }

    public long getTimestamp(){ return this.timestamp; }

    public boolean equals(Object o){
        if(!(o instanceof CustomerBrokerNewImpl))
            return false;
        CustomerBrokerNewImpl compare = (CustomerBrokerNewImpl) o;
        return publicKeyBroker.equals(compare.getPublicKeyBroker()) && publicKeyCustomer.equals(compare.getPublicKeyCustomer());
    }

    @Override
    public int hashCode(){
        int c = 0;
        c += publicKeyBroker.hashCode();
        c += publicKeyCustomer.hashCode();
        return 	HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }
}
