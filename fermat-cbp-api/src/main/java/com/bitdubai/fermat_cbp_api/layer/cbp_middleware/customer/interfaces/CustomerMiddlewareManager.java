package com.bitdubai.fermat_cbp_api.layer.cbp_middleware.customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CustomerType;
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
     CryptoBrokerIdentityInformation createCustomerRelationship(String brokerPublicKey,String customerPublicKey,String customerName, byte[] profileImage, CustomerType customerType) throws CantCreateCustomerRelationshipException;

    /**
     * The method <code>getAllBrokerCustomers</code> returns the list of all broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     * @throws CantListCustomersException
     */
    List<CustomerInformation> getAllBrokerCustomers(String brokerPublicKey) throws CantListCustomersException;

    /**
     * The method <code>getSporadicBrokerCustomers</code> returns the list of sporadic broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     * @throws CantListSporadicCustomersException
     */

    List<CustomerInformation> getSporadicBrokerCustomers(String brokerPublicKey) throws CantListSporadicCustomersException;


    /**
     * The method <code>getFrequentBrokerCustomers</code> returns the list of frequent broker's customers
     *
     * @param brokerPublicKey the Broker public key
     * @return the list of broker's customers information
     */
    List<CustomerInformation> getFrequentBrokerCustomers(String brokerPublicKey) throws CantListFrequentCustomersException;

    /**
     * The method <code>searchBrokerCustomerByName</code> returns the list of broker's customers searching by name
     *
     * @param brokerPublicKey the Broker public key
     * @param customerName  the customer alias or name to search
     * @return the list of broker's customers information
     */
     List<CustomerInformation> searchBrokerCustomerByName(String brokerPublicKey,String customerName) throws CantSearchCustomersException;

}
