package com.bitdubai.fermat_cbp_api.layer.middleware.customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantCreateCustomerRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantListCustomersException;
import com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantListFrequentCustomersException;
import com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantListSporadicCustomersException;

import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 17/09/15.
 */

/**
 * The interface <code>CustomerMiddlewareManager</code>
 * provides the methods for manager and classified the Crypto Broker's Customers.
 */
public interface CustomerMiddlewareManager {


    /**
     * The method <code>createCustomerRelationship</code> is used to create a new relationship between a broker and a CustomerIdentity
     *
     * @param brokerIdentity
     * @param customerIdentity
     * @param customerType
     * @return
     * @throws CantCreateCustomerRelationshipException
     */
    BrokerCustomerRelationship createCustomerRelationship(CryptoBrokerIdentity brokerIdentity, CryptoCustomerIdentity customerIdentity, CustomerType customerType) throws CantCreateCustomerRelationshipException;


    /**
     * The method <code>removeCustomerRelationship</code> is used to remove a relationship between a broker and a CustomerIdentity
     *
     * @param RelationshipId Id of relationship
     * @throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantDeleteCustomerRelationshipException
     */
    void removeCustomerRelationship(UUID RelationshipId) throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantDeleteCustomerRelationshipException;

    /**
     * @param brokerCustomerRelationship
     * @throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantDeleteCustomerRelationshipException
     */

    void modifyCustomerRelationship(BrokerCustomerRelationship brokerCustomerRelationship) throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantModifyCustomerRelationshipException;

    /**
     * The method <code>getAllBrokerCustomers</code> returns the list of all  customers to a broker identity
     *
     * @param brokerIdentity
     * @return
     * @throws CantListCustomersException
     */
    List<BrokerCustomerRelationship> getAllBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListCustomersException;

    /**
     * The method <code>getSporadicBrokerCustomers</code> returns the list of sporadic broker's customers
     *
     * @param brokerIdentity
     * @return
     * @throws CantListSporadicCustomersException
     */
    List<BrokerCustomerRelationship> getSporadicBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListSporadicCustomersException;


    /**
     * The method <code>getFrequentBrokerCustomers</code> returns the list of frequent broker's customers
     *
     * @param brokerIdentity
     * @return
     * @throws CantListFrequentCustomersException
     */
    List<BrokerCustomerRelationship> getFrequentBrokerCustomers(CryptoBrokerIdentity brokerIdentity) throws CantListFrequentCustomersException;

    /**
     * The method <code>searchBrokerCustomerByName</code> returns the list of broker's customers searching by name
     *
     * @param brokerIdentity
     * @param customerIdentity
     * @return
     * @throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantSearchCustomersException
     */
    List<BrokerCustomerRelationship> searchBrokerCustomerByName(CryptoBrokerIdentity brokerIdentity, CryptoCustomerIdentity customerIdentity) throws com.bitdubai.fermat_cbp_api.layer.middleware.customer.exceptions.CantSearchCustomersException;

}
