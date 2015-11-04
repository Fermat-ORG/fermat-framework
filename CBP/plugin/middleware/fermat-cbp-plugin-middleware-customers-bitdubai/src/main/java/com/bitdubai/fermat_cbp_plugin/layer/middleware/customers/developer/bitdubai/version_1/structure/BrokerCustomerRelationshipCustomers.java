package com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.interfaces.BrokerCustomerRelationship;

import java.util.UUID;

/**
 * Created by angel on 30/9/15.
 */
public class BrokerCustomerRelationshipCustomers implements BrokerCustomerRelationship {

    private UUID relationshipId;
    private CryptoBrokerIdentity cryptoBroker;
    private CryptoCustomerIdentity cryptoCustomer;
    private CustomerType customerType;

    public BrokerCustomerRelationshipCustomers(UUID relationshipId, CryptoBrokerIdentity cryptoBroker, CryptoCustomerIdentity cryptoCustomer, CustomerType customerType){
        this.relationshipId = relationshipId;
        this.cryptoBroker = cryptoBroker;
        this.cryptoCustomer = cryptoCustomer;
        this.customerType = customerType;
    }

    public UUID getRelationshipId() {
        return relationshipId;
    }

    public CryptoBrokerIdentity getCryptoBroker() {
        return cryptoBroker;
    }

    public CryptoCustomerIdentity getCryptoCustomer() {
        return cryptoCustomer;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }
}
