package com.bitdubai.fermat_cbp_api.layer.middleware.customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;

import java.util.UUID;

/**
 * Created by natalia on 17/09/15.
 */

/**
 * The interface <code>BrokerCustomerRelationship</code>
 * provides the method to extract information about an crypto customer identity.
 */

public interface BrokerCustomerRelationship {

    /**
     * @return
     */
    UUID getRelationshipId();

    /**
     * @return
     */
    CryptoBrokerIdentity getCryptoBroker();

    /**
     * @return
     */
    CryptoCustomerIdentity getCryptoCustomer();

    /**
     * @return
     */
    CustomerType getCustomerType();
}
