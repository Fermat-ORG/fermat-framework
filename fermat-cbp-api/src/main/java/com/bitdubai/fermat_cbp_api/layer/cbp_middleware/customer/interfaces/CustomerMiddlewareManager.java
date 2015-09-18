package com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantCreateCustomerRelationshipException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantListCustomersException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantListFrequentCustomersException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantListSporadicCustomersException;
import com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.exceptions.CantSearchCustomersException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;


import java.util.List;

/**
 * Created by natalia on 17/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.interfaces.CustomerMiddlewareManager</code>
 * provides the methods for manager and classified the Crypto Broker's Customers.
 */
public interface CustomerMiddlewareManager {


    /**
    *
     * The method <code>createCustomerRelationship</code> is used to create a new relationship between a broker and a Customer
     *
     * @param brokerPublicKey the Broker public key
     * @param customerPublicKey the customer public key
     * @param customerName the customer name or alias
     * @param profileImage the profile image
     * @param customerType the customer type
     * @return CryptoBrokerIdentityInformation object
     * @throws CantCreateCustomerRelationshipException
     */
     BrokerCustomerRelationship createCustomerRelationship(CryptoBrokerIdentity brokerIdentity, CryptoCustomerIdentity customerIdentity, CustomerType customerType) throws CantCreateCustomerRelationshipException;

    /**
     * The method <code>getAllBrokerCustomers</code> returns the list of all broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     * @throws CantListCustomersException
     */
    List<BrokerCustomerRelationship> getAllBrokerCustomers(String brokerPublicKey) throws CantListCustomersException;

    /**
     * The method <code>getSporadicBrokerCustomers</code> returns the list of sporadic broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     * @throws CantListSporadicCustomersException
     */
    List<BrokerCustomerRelationship> getSporadicBrokerCustomers(String brokerPublicKey) throws CantListSporadicCustomersException;


    /**
     * The method <code>getFrequentBrokerCustomers</code> returns the list of frequent broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     */
    List<BrokerCustomerRelationship> getFrequentBrokerCustomers(String brokerPublicKey) throws CantListFrequentCustomersException;

    /**
     * The method <code>searchBrokerCustomerByName</code> returns the list of broker's customers searching by name
     *
     * @param brokerPublicKey the Broker public key
     * @param customerName  the customer alias or name to search
     * @return the list of broker's customers information
     */
     List<BrokerCustomerRelationship> searchBrokerCustomerByName(String brokerPublicKey,String customerName) throws CantSearchCustomersException;

}
